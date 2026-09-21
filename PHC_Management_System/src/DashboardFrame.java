import java.awt.*;
import javax.swing.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("PHC Management System - Dashboard");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton patientsButton = new JButton("Patients");
        JButton doctorsButton = new JButton("Doctors");
        JButton appointmentsButton = new JButton("Appointments");
        JButton medicinesButton = new JButton("Medicines");
        JButton equipmentButton = new JButton("Equipment");
        JButton employeesButton = new JButton("Employees");
        JButton reportsButton = new JButton("Reports");
        JButton logoutButton = new JButton("Logout");

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 30, 40));
        buttonPanel.add(patientsButton);
        buttonPanel.add(doctorsButton);
        buttonPanel.add(appointmentsButton);
        buttonPanel.add(medicinesButton);
        buttonPanel.add(equipmentButton);
        buttonPanel.add(employeesButton);
        buttonPanel.add(reportsButton);
        buttonPanel.add(logoutButton);

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        patientsButton.addActionListener(e -> new PatientFrame().setVisible(true));
        doctorsButton.addActionListener(e -> new DoctorFrame().setVisible(true));
        appointmentsButton.addActionListener(e -> new AppointmentFrame().setVisible(true));
        medicinesButton.addActionListener(e -> new MedicineFrame().setVisible(true));
        equipmentButton.addActionListener(e -> new EquipmentFrame().setVisible(true));
        employeesButton.addActionListener(e -> new EmployeeFrame().setVisible(true));
        reportsButton.addActionListener(e -> new ReportsFrame().setVisible(true));

        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to logout?",
                    "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }
}
