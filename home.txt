import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomePage homepage = new HomePage();
            homepage.createAndShowGUI();
        });
    }
    public void createAndShowGUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Project Homepage");
    setSize(800, 600);
    setLocationRelativeTo(null);

    // Create the layout components
    JPanel welcomeInfoImagePanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel infoImagePanel = new JPanel();
    JPanel infoPanel = new JPanel();
    JPanel imagePanel = new JPanel();

    // Create the buttons
    JButton homeButton = new JButton("Home");
    JButton reviewButton = new JButton("Review");
    JButton profileButton = new JButton("Profile");
    JButton logoutButton = new JButton("Logout");
    JButton exitButton = new JButton("Exit");

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

    JLabel welcomeLabel = new JLabel("<html><body><font size='6'>Welcome!</font></body></html>");

    // Create the project info label
    JLabel infoLabel = new JLabel("<html><body><font size='5'>This is a project that allows users to search for medicine reviews, "
            + "insert new reviews, delete existing reviews, and update their own reviews.<br><br>"
            + "Please explore the various functionalities available!</font></body></html>");

    infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

    // Create the image label
    JLabel imageLabel = new JLabel();
    ImageIcon imageIcon = new ImageIcon("image.jpg"); // Replace "path_to_image.jpg" with your image path
    imageIcon.setImage(imageIcon.getImage().getScaledInstance(390, -1, Image.SCALE_SMOOTH)); // Adjust image size
    imageLabel.setIcon(imageIcon);

    // Set the layouts
    welcomeInfoImagePanel.setLayout(new BorderLayout());
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    infoImagePanel.setLayout(new GridLayout(1, 2));
    infoPanel.setLayout(new BorderLayout());
    imagePanel.setLayout(new BorderLayout());

    // Adjust the welcome message panel
    JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    welcomePanel.add(welcomeLabel);
    welcomePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the label

    // Set background color for panels
    topPanel.setBackground(Color.WHITE);
    welcomePanel.setBackground(new Color(224, 236, 255));
    infoPanel.setBackground(Color.WHITE);
    imagePanel.setBackground(new Color(224, 236, 255));

    // Add components to their respective containers
    topPanel.add(homeButton);
    topPanel.add(reviewButton);
    topPanel.add(profileButton);
    topPanel.add(logoutButton);
    topPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to right-align the exit button
    topPanel.add(exitButton);
    topPanel.setBackground(new Color(224, 236, 255));

    welcomeInfoImagePanel.add(topPanel, BorderLayout.NORTH);
    welcomeInfoImagePanel.add(welcomePanel, BorderLayout.CENTER);

    infoPanel.add(infoLabel, BorderLayout.CENTER);
    imagePanel.add(imageLabel, BorderLayout.CENTER);

    infoImagePanel.add(infoPanel);
    infoImagePanel.add(imagePanel);
    infoPanel.setBackground(new Color(224, 236, 255));

    // Add panels to the frame
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(welcomeInfoImagePanel, BorderLayout.NORTH);
    getContentPane().add(infoImagePanel, BorderLayout.CENTER);
    
    exitButton.addActionListener(e -> dispose());

        /*homeButton.addActionListener(e -> {
            dispose();
            // Create and show the Home screen
            HomePage homePage = new HomePage();
            homePage.createAndShowGUI();
        });*/
        reviewButton.addActionListener(e -> {
            dispose();
            // Create and show the Review screen
            Review review=new Review();
            review.createAndShowGUI();
        });
        profileButton.addActionListener(e -> {
            dispose();
            // Create and show the Profile screen
            Profile profilePage = new Profile();
            profilePage.createAndShowGUI();
        });
        logoutButton.addActionListener(e -> {
           dispose();
           LoginSignUpScreen loginSignUpScreen = new LoginSignUpScreen();
           loginSignUpScreen.setVisible(true);
        });



    setVisible(true);
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
