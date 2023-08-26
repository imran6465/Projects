import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class EditProfile extends JFrame {

    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl"; // Update with your database connection URL
    private static final String USERNAME = "sys as sysdba"; // Replace with your database username
    private static final String PASSWORD = "vasavi"; // Replace with your database password

    private JButton homeButton;
    private JButton reviewButton;
    private JButton profileButton;
    private JButton logoutButton;
    private JButton exitButton;

    private JLabel usernameValueLabel;
    private JTextField passwordTextField;
    private JTextField fullNameTextField;
    private JTextField phoneTextField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditProfile::new);
    }

    public EditProfile() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Edit Profile");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create the layout components
        JPanel topPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        // Create the buttons
        homeButton = new JButton("Home");
        reviewButton = new JButton("Review");
        profileButton = new JButton("Profile");
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Exit");
        JButton submitButton = new JButton("Submit");

        // Set the button colors
         Color buttonColor = new Color(17, 30, 108);
        Color buttonHoverColor = new Color(102, 204, 255);
        setButtonStyles(homeButton, buttonColor, buttonHoverColor);
        setButtonStyles(reviewButton, buttonColor, buttonHoverColor);
        setButtonStyles(profileButton, buttonColor, buttonHoverColor);
        setButtonStyles(logoutButton, buttonColor, buttonHoverColor);
        setButtonStyles(exitButton, buttonColor, buttonHoverColor);

        JLabel editProfileLabel = new JLabel("<html><body><font size='6'>Edit Profile</font></body></html>");

        // Set the layouts
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        infoPanel.setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout with 2 columns and 10px spacing
        titlePanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Set background color for panels
        topPanel.setBackground(new Color(224, 236, 255));
        infoPanel.setBackground(new Color(224, 236, 255));
        titlePanel.setBackground(new Color(224, 236, 255)); // Light blue color
        buttonPanel.setBackground(new Color(224, 236, 255));

        // Add components to their respective containers
        topPanel.add(homeButton);
        topPanel.add(reviewButton);
        topPanel.add(profileButton);
        topPanel.add(logoutButton);
        topPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to right-align the exit button
        topPanel.add(exitButton);

        titlePanel.add(editProfileLabel, BorderLayout.NORTH);

        // Add labels and text fields to infoPanel
        infoPanel.add(new JLabel("Username:"));
        usernameValueLabel = new JLabel();
        infoPanel.add(usernameValueLabel);
        infoPanel.add(new JLabel("Password:"));
        passwordTextField = new JTextField();
        infoPanel.add(passwordTextField);
        infoPanel.add(new JLabel("Full Name:"));
        fullNameTextField = new JTextField();
        infoPanel.add(fullNameTextField);
        infoPanel.add(new JLabel("Phone:"));
        phoneTextField = new JTextField();
        infoPanel.add(phoneTextField);

        // Set label and text field properties
        usernameValueLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        usernameValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameValueLabel.setOpaque(true);
        usernameValueLabel.setBackground(new Color(230, 230, 230)); // Light gray color
        usernameValueLabel.setForeground(Color.BLACK);

        passwordTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        fullNameTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        phoneTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Add submit button to buttonPanel
        buttonPanel.add(submitButton);

        // Add panels to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(titlePanel, BorderLayout.CENTER);
        getContentPane().add(infoPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        exitButton.addActionListener(e -> dispose());

        logoutButton.addActionListener(e -> {
            dispose();
            LoginSignUpScreen loginSignUpScreen = new LoginSignUpScreen();
            loginSignUpScreen.setVisible(true);
        });

        // Add action listeners to the buttons
        homeButton.addActionListener(e -> {
            dispose();
            HomePage homepage = new HomePage();
            homepage.createAndShowGUI();
        });

        reviewButton.addActionListener(e -> {
            dispose();
            Review review = new Review();
            review.createAndShowGUI();
        });

        profileButton.addActionListener(e -> {
            dispose();
            Profile profile = new Profile();
            profile.setVisible(true);
        });

        fetchUserDetails(); // Fetch user details from the database

        submitButton.addActionListener(e -> {
            updateProfile();
        });

        setVisible(true);
    }

    private void setButtonStyles(JButton button, Color buttonColor, Color buttonHoverColor) {
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addMouseListener(new ButtonHoverListener(button, buttonColor, buttonHoverColor));
    }

    private void fetchUserDetails() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_details WHERE isloggedin = 'Y'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                String password = resultSet.getString("PASSWORD");
                String fullName = resultSet.getString("FULLNAME");
                String phone = resultSet.getString("PHONE");

                usernameValueLabel.setText(username);
                passwordTextField.setText(password);
                fullNameTextField.setText(fullName);
                phoneTextField.setText(phone);
            } else {
                JOptionPane.showMessageDialog(this, "User details not found!");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch user details from the database!");
        }
    }

    private void updateProfile() {
        try {
            String password = passwordTextField.getText();
            String fullName = fullNameTextField.getText();
            String phone = phoneTextField.getText();

            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user_details SET password=?, fullname = ?, phone = ? WHERE isloggedin = 'Y'");
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, fullName);
            preparedStatement.setString(3, phone);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile!");
            }

            preparedStatement.close();
            connection.close();
            } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update profile!");
        }
    }

    private static class ButtonHoverListener extends MouseAdapter {
        private final JButton button;
        private final Color originalColor;
        private final Color hoverColor;

        public ButtonHoverListener(JButton button, Color originalColor, Color hoverColor) {
            this.button = button;
            this.originalColor = originalColor;
            this.hoverColor = hoverColor;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(hoverColor);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(originalColor);
        }
    }
}
