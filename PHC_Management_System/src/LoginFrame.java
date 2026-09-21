import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("PHC Management System - Login");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("PHC Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JLabel hintLabel = new JLabel("Default login: admin / admin123", JLabel.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 15, 100));
        bottomPanel.add(loginButton);
        bottomPanel.add(hintLabel);

        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Pressing Enter also clicks the Login button
        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(e -> login());
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.equals(ADMIN_USER) && password.equals(ADMIN_PASS)) {
            new DashboardFrame().setVisible(true);
            dispose(); // close login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
}
