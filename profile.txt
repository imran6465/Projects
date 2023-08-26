import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Profile extends JFrame {

    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl"; // Update with your database connection URL
    private static final String USERNAME = "sys as sysdba"; // Replace with your database username
    private static final String PASSWORD = "vasavi"; // Replace with your database password

    private JLabel usernameValueLabel;
    private JLabel fullNameValueLabel;
    private JLabel phoneValueLabel;
    private JLabel lastLoginValueLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Profile::new);
    }

    public Profile() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Profile");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create the layout components
        JPanel topPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel titlePanel = new JPanel();

        // Create the buttons
        JButton homeButton = new JButton("Home");
        JButton reviewButton = new JButton("Review");
        JButton profileButton = new JButton("Profile");
        JButton logoutButton = new JButton("Logout");
        JButton exitButton = new JButton("Exit");
        JButton editProfileButton = new JButton("Edit Profile");

        // Set the button colors
        Color buttonColor = new Color(17, 30, 108);
    Color buttonHoverColor = new Color(175, 238, 238);
        homeButton.setBackground(buttonColor);
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.addMouseListener(new ButtonHoverListener(homeButton, buttonColor, buttonHoverColor));
        reviewButton.setBackground(buttonColor);
        reviewButton.setForeground(Color.WHITE);
        reviewButton.setFocusPainted(false);
        reviewButton.addMouseListener(new ButtonHoverListener(reviewButton, buttonColor, buttonHoverColor));
        profileButton.setBackground(buttonColor);
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.addMouseListener(new ButtonHoverListener(profileButton, buttonColor, buttonHoverColor));
        logoutButton.setBackground(buttonColor);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addMouseListener(new ButtonHoverListener(logoutButton, buttonColor, buttonHoverColor));
        exitButton.setBackground(buttonColor);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addMouseListener(new ButtonHoverListener(exitButton, buttonColor, buttonHoverColor));
        editProfileButton.setBackground(buttonColor);
        editProfileButton.setForeground(Color.WHITE);
        editProfileButton.setFocusPainted(false);
        editProfileButton.setPreferredSize(new Dimension(100, 30)); // Set the preferred size

        JLabel profileLabel = new JLabel("<html><body><font size='6'>Profile</font></body></html>");

        // Create labels for user information
        JLabel usernameLabel = new JLabel("Username:");
        JLabel fullNameLabel = new JLabel("Full Name:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel lastLoginLabel = new JLabel("Last Login:");

        // Set font size for labels
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        usernameLabel.setFont(labelFont);
        fullNameLabel.setFont(labelFont);
        phoneLabel.setFont(labelFont);
        lastLoginLabel.setFont(labelFont);

        // Set font size for values
        Font valueFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);

        // Set the layouts
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        infoPanel.setLayout(new GridLayout(6, 2));
        titlePanel.setLayout(new BorderLayout());

        // Set background color for panels
        topPanel.setBackground(new Color(224, 236, 255));
        infoPanel.setBackground(Color.WHITE);
        titlePanel.setBackground(new Color(224, 236, 255));

        // Add components to their respective containers
        topPanel.add(homeButton);
        topPanel.add(reviewButton);
        topPanel.add(profileButton);
        topPanel.add(logoutButton);
        topPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to right-align the exit button
        topPanel.add(editProfileButton);

        titlePanel.add(profileLabel, BorderLayout.WEST);

        infoPanel.add(usernameLabel);
        usernameValueLabel = new JLabel();
        usernameValueLabel.setFont(valueFont);
        infoPanel.add(usernameValueLabel);
        infoPanel.add(fullNameLabel);
        fullNameValueLabel = new JLabel();
        fullNameValueLabel.setFont(valueFont);
        infoPanel.add(fullNameValueLabel);
        infoPanel.add(phoneLabel);
        phoneValueLabel = new JLabel();
        phoneValueLabel.setFont(valueFont);
        infoPanel.add(phoneValueLabel);
        infoPanel.add(lastLoginLabel);
        lastLoginValueLabel = new JLabel();
        lastLoginValueLabel.setFont(valueFont);
        infoPanel.add(lastLoginValueLabel);
        infoPanel.add(new JLabel()); // Empty label for spacing
        infoPanel.setBackground(new Color(224, 236, 255));

        // Add edit profile button to a separate panel for layout control
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.add(exitButton);

        // Add panels to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(infoPanel, BorderLayout.CENTER);
        getContentPane().add(exitPanel, BorderLayout.SOUTH);

        exitButton.addActionListener(e -> dispose());

        homeButton.addActionListener(e -> {
            dispose();
            HomePage homepage = new HomePage();
            homepage.createAndShowGUI();
        });

        reviewButton.addActionListener(e -> {
            dispose();
            // Create and show the Review screen
            new Review();
        });

        logoutButton.addActionListener(e -> {
            dispose();
            LoginSignUpScreen loginSignUpScreen = new LoginSignUpScreen();
            loginSignUpScreen.setVisible(true);
        });

        profileButton.addActionListener(e -> {
            dispose();
            // Create and show the Profile screen
            Profile profilePage = new Profile();
            profilePage.createAndShowGUI();
        });

        editProfileButton.addActionListener(e -> {
            dispose();
            new EditProfile();
        });

        fetchUserDetails(); // Fetch user details from the database

        setVisible(true);
    }

    private void fetchUserDetails() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_details WHERE isloggedin = 'Y'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                String fullName = resultSet.getString("FULLNAME");
                String phone = resultSet.getString("PHONE");
                Date lastLogin = resultSet.getDate("LAST_LOGIN");

                usernameValueLabel.setText(username);
                fullNameValueLabel.setText(fullName);
                phoneValueLabel.setText(phone);
                lastLoginValueLabel.setText(lastLogin.toString());
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
