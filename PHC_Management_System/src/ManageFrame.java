import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

// Common screen used by Patients, Doctors, Appointments, Medicines, Equipment and Employees.
// It shows a form (Add / Clear / Delete) and a table. Data is saved in the SQLite database.
public class ManageFrame extends JFrame {

    private String[] columns;      // column names, e.g. ID, Name, Age...
    private String fileName;       // file where data is saved
    private int[] numberColumns;   // positions of columns that must be numbers

    private JTextField[] fields;
    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter; // used for searching
    private JComboBox<String> searchColumn;
    private JTextField searchField;

    public ManageFrame(String title, String[] columns, String fileName, int[] numberColumns) {
        this.columns = columns;
        this.fileName = fileName;
        this.numberColumns = numberColumns;

        setTitle(title);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ----- Form (left side) -----
        JPanel formPanel = new JPanel(new GridLayout(columns.length, 2, 5, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Enter Details"));
        fields = new JTextField[columns.length];
        for (int i = 0; i < columns.length; i++) {
            formPanel.add(new JLabel(columns[i]));
            fields[i] = new JTextField();
            formPanel.add(fields[i]);
        }

        JButton addButton = new JButton("Add");
        JButton clearButton = new JButton("Clear");
        JButton deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(deleteButton);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setPreferredSize(new Dimension(280, 0));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        leftPanel.add(formPanel, BorderLayout.NORTH);
        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        // ----- Table (right side) -----
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // user cannot edit cells directly
            }
        };
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // ----- Search bar (above the table) -----
        String[] searchOptions = new String[columns.length + 1];
        searchOptions[0] = "All Columns";
        for (int i = 0; i < columns.length; i++) {
            searchOptions[i + 1] = columns[i];
        }
        searchColumn = new JComboBox<>(searchOptions);
        searchField = new JTextField(15);
        JButton clearSearchButton = new JButton("Clear Search");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search in:"));
        searchPanel.add(searchColumn);
        searchPanel.add(searchField);
        searchPanel.add(clearSearchButton);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);

        // ----- Load saved data -----
        loadData();

        // ----- Button actions -----
        addButton.addActionListener(e -> addRecord());
        clearButton.addActionListener(e -> clearFields());
        deleteButton.addActionListener(e -> deleteRecord());

        // ----- Search actions (table filters while you type) -----
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applySearch(); }
            public void removeUpdate(DocumentEvent e) { applySearch(); }
            public void changedUpdate(DocumentEvent e) { applySearch(); }
        });
        searchColumn.addActionListener(e -> applySearch());
        clearSearchButton.addActionListener(e -> searchField.setText(""));
    }

    // Show only the rows that contain the search text (not case sensitive)
    private void applySearch() {
        String text = searchField.getText().trim();
        if (text.isEmpty()) {
            sorter.setRowFilter(null); // show everything
            return;
        }
        String pattern = "(?i)" + java.util.regex.Pattern.quote(text);
        int selected = searchColumn.getSelectedIndex();
        if (selected == 0) {
            sorter.setRowFilter(RowFilter.regexFilter(pattern)); // all columns
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(pattern, selected - 1));
        }
    }

    private void addRecord() {
        String[] values = new String[columns.length];

        // Get the values and check that nothing is empty
        for (int i = 0; i < columns.length; i++) {
            values[i] = fields[i].getText().trim().replace(",", " "); // comma would break the CSV file
            if (values[i].isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter " + columns[i] + ".");
                return;
            }
        }

        // Check number fields (Age, Quantity, Price)
        for (int col : numberColumns) {
            if (!isNumber(values[col])) {
                JOptionPane.showMessageDialog(this, columns[col] + " must be a number.");
                return;
            }
        }

        // ID must be unique
        for (int r = 0; r < model.getRowCount(); r++) {
            if (model.getValueAt(r, 0).toString().equals(values[0])) {
                JOptionPane.showMessageDialog(this, "This ID already exists.");
                return;
            }
        }

        model.addRow(values);
        saveData();
        clearFields();
        JOptionPane.showMessageDialog(this, "Record added successfully.");
    }

    private void deleteRecord() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Delete the selected record?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            model.removeRow(table.convertRowIndexToModel(row));
            saveData();
        }
    }

    private void clearFields() {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private boolean isNumber(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Read the file and fill the table
    private void loadData() {
        ArrayList<String[]> rows = FileHelper.readFile(fileName);
        for (String[] row : rows) {
            if (row.length == columns.length) {
                model.addRow(row);
            }
        }
    }

    // Save everything in the table to the file
    private void saveData() {
        ArrayList<String[]> rows = new ArrayList<>();
        for (int r = 0; r < model.getRowCount(); r++) {
            String[] row = new String[columns.length];
            for (int c = 0; c < columns.length; c++) {
                row[c] = model.getValueAt(r, c).toString();
            }
            rows.add(row);
        }
        FileHelper.writeFile(fileName, rows);
    }
}
