import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CreateUserScreen {
    private JFrame frame;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JPasswordField reenterPasswordField; // New field for re-entering password
    private JTextField fullNameTextField;
    private JTextField phoneTextField;
    private JButton createButton;
    private JButton exitButton;
    private JButton backButton; // New back button

    public CreateUserScreen() {
        frame = new JFrame("Create User");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(224, 236, 255)); // Set light blue background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        mainPanel.add(createTitleLabel("Register", SwingConstants.CENTER), gbc); // Add title label

        gbc.gridy = 1;
        mainPanel.add(createLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameTextField = new JTextField(20);
        mainPanel.add(usernameTextField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Re-enter Password:"), gbc);
        gbc.gridx = 1;
        reenterPasswordField = new JPasswordField(20);
        mainPanel.add(reenterPasswordField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        fullNameTextField = new JTextField(20);
        mainPanel.add(fullNameTextField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        mainPanel.add(createLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneTextField = new JTextField(20);
        mainPanel.add(phoneTextField, gbc);

        createButton = new JButton("Create");
        exitButton = new JButton("Exit");
        backButton = new JButton("Back"); // Create back button

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(224, 236, 255)); // Set light blue background color
        buttonPanel.add(createButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(backButton); // Add back button to button panel

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());
                String reenteredPassword = new String(reenterPasswordField.getPassword());
                String fullName = fullNameTextField.getText();
                String phone = phoneTextField.getText();

                if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter all fields.");
                    return;
                }

                if (!password.equals(reenteredPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match.");
                    return;
                }

                try {
                    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "sys as sysdba", "vasavi");

                    String insertQuery = "INSERT INTO user_details (USERNAME, PASSWORD, FULLNAME, PHONE) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    insertStatement.setString(1, username);
                    insertStatement.setString(2, password);
                    insertStatement.setString(3, fullName);
                    insertStatement.setString(4, phone);
                    int rowsInserted = insertStatement.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(frame, "User created successfully.");

                        // Navigate to LoginSignUpScreen
                        frame.dispose();
                        new LoginSignUpScreen();
                    } else {
                        JOptionPane.showMessageDialog(frame, "User creation failed. Please try again.");
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error creating user. Please try again.");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginSignUpScreen loginSignUpScreen = new LoginSignUpScreen();
                loginSignUpScreen.setVisible(true);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size
        return label;
    }

    private JLabel createTitleLabel(String text, int alignment) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        label.setHorizontalAlignment(alignment);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set look and feel to the system default
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new CreateUserScreen();
            }
        });
    }
}
