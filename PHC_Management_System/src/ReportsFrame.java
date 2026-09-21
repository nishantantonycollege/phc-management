import java.awt.*;
import javax.swing.*;

public class ReportsFrame extends JFrame {

    private JLabel patientsLabel;
    private JLabel doctorsLabel;
    private JLabel medicinesLabel;
    private JLabel employeesLabel;

    public ReportsFrame() {
        setTitle("Reports");
        setSize(380, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("PHC Report", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        patientsLabel = new JLabel();
        doctorsLabel = new JLabel();
        medicinesLabel = new JLabel();
        employeesLabel = new JLabel();

        JPanel countPanel = new JPanel(new GridLayout(4, 1, 5, 10));
        countPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
        countPanel.add(patientsLabel);
        countPanel.add(doctorsLabel);
        countPanel.add(medicinesLabel);
        countPanel.add(employeesLabel);

        JButton refreshButton = new JButton("Refresh");
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        bottomPanel.add(refreshButton);

        add(titleLabel, BorderLayout.NORTH);
        add(countPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadCounts());
        loadCounts();
    }

    private void loadCounts() {
        patientsLabel.setText("Number of Patients: " + FileHelper.countRecords("patients.csv"));
        doctorsLabel.setText("Number of Doctors: " + FileHelper.countRecords("doctors.csv"));
        medicinesLabel.setText("Number of Medicines: " + FileHelper.countRecords("medicines.csv"));
        employeesLabel.setText("Number of Employees: " + FileHelper.countRecords("employees.csv"));
    }
}
