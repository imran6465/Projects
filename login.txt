import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginSignUpScreen extends JFrame {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "sys as sysdba";
    private static final String DB_PASSWORD = "vasavi";

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginSignUpScreen() {
        setTitle("Login and Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Create the content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(224, 236, 255)); // Set background color

        // Left section - Logo and title
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(224, 236, 255));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon logoIcon = new ImageIcon("logo.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoPanel.add(logoLabel);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(224, 236, 255));

        JLabel titleLabel = new JLabel("Drug Performance Analysis");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28)); // Increase font size
        titlePanel.add(titleLabel);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(224, 236, 255));
        leftPanel.add(logoPanel, BorderLayout.NORTH);
        leftPanel.add(titlePanel, BorderLayout.CENTER);

        contentPane.add(leftPanel, BorderLayout.WEST);

        // Right section - Login and sign up form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(224, 236, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Sign In");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                verifyLogin(username, password);
            }
        });

        JButton createUserButton = new JButton("Create User");

        createUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreateUserScreen();
            }
        });

        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(5)); // Add separation
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5)); // Add separation
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(20)); // Add separation
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createUserButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rightPanel.setBackground(new Color(224, 236, 255));
        rightPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY)); // Add border on the right side
        rightPanel.add(formPanel);

        contentPane.add(rightPanel, BorderLayout.CENTER);

        setContentPane(contentPane);
    }

    private void verifyLogin(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER_DETAILS WHERE USERNAME = ? AND PASSWORD = ?");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            boolean loginSuccessful = resultSet.next();

            if (loginSuccessful) {
                // Update isLoggedIn column to 'Y' for the logged-in user
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE USER_DETAILS SET isLoggedIn = 'Y' WHERE USERNAME = ?");
                updateStatement.setString(1, username);
                updateStatement.executeUpdate();
                updateStatement.close();

                // Close the LoginSignUpScreen
                dispose();

                // Open the HomePage screen
                HomePage homePage = new HomePage();
                homePage.createAndShowGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set look and feel to the system default
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LoginSignUpScreen loginSignUpScreen = new LoginSignUpScreen();
                loginSignUpScreen.setVisible(true);
            }
        });
    }
}
