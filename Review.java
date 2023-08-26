import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Review extends JFrame {

    private JButton homeButton;
    private JButton reviewButton;
    private JButton profileButton;
    private JButton logoutButton;
    private JButton exitButton;
    private JButton searchButton;
    private JButton insertButton;
    private JButton deleteButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Review::new);
    }

    public Review() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Review");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create the layout components
        JPanel topPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel imagePanel = new JPanel();
        JPanel exitPanel = new JPanel();

        // Create the buttons
        homeButton = new JButton("Home");
        reviewButton = new JButton("Review");
        profileButton = new JButton("Profile");
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Exit");
        searchButton = new JButton("Search");
        insertButton = new JButton("Insert");
        deleteButton = new JButton("Delete");

        // Set the button colors
        Color buttonColor = new Color(17, 30, 108);
        Color buttonHoverColor = new Color(102, 204, 255);
        setButtonStyles(homeButton, buttonColor, buttonHoverColor);
        setButtonStyles(reviewButton, buttonColor, buttonHoverColor);
        setButtonStyles(profileButton, buttonColor, buttonHoverColor);
        setButtonStyles(logoutButton, buttonColor, buttonHoverColor);
        setButtonStyles(exitButton, buttonColor, buttonHoverColor);
        setButtonStyles(searchButton, buttonColor, buttonHoverColor);
        setButtonStyles(insertButton, buttonColor, buttonHoverColor);
        setButtonStyles(deleteButton, buttonColor, buttonHoverColor);

        // Set the layouts
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        leftPanel.setLayout(new GridLayout(0, 1, 0, 10)); // Updated GridLayout with vertical alignment and spacing
        rightPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 10)); // Updated GridLayout with vertical alignment and spacing
        imagePanel.setLayout(new BorderLayout());
        exitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Set background color for panels
        topPanel.setBackground(new Color(224, 236, 255));
        leftPanel.setBackground(new Color(224,236,255));
        rightPanel.setBackground(new Color(224,236,255));
        buttonPanel.setBackground(new Color(224,236,255));
        imagePanel.setBackground(new Color(224,236,255));
        exitPanel.setBackground(new Color(224, 236, 255));

        // Add components to their respective containers
        topPanel.add(homeButton);
        topPanel.add(reviewButton);
        topPanel.add(profileButton);
        topPanel.add(logoutButton);
        topPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to right-align the exit button
        exitPanel.add(exitButton);

        JLabel titleLabel = new JLabel("Explore the Effects of Drugs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font style and size if needed
        leftPanel.add(titleLabel);

        leftPanel.add(Box.createVerticalGlue()); // Add vertical glue to align buttons at the top
        buttonPanel.add(searchButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);
        leftPanel.add(buttonPanel);
        leftPanel.add(Box.createVerticalGlue()); // Add vertical glue to align buttons at the bottom

        ImageIcon image = new ImageIcon("image2.jpg"); // Replace with the actual path to your image
        JLabel imageLabel = new JLabel(image);
        image.setImage(image.getImage().getScaledInstance(390, -1, Image.SCALE_SMOOTH));
        imagePanel.add(imageLabel);
        imageLabel.setIcon(image);

        // Add panels to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.CENTER);
        getContentPane().add(exitPanel, BorderLayout.SOUTH);
        rightPanel.add(imagePanel, BorderLayout.CENTER);


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
            profile.createAndShowGUI();
        });

        searchButton.addActionListener(e -> {
            dispose();
            SearchReview search = new SearchReview();
            search.createAndShowGUI();
        });

        insertButton.addActionListener(e -> {
            dispose();
            new InsertReview();
        });

        deleteButton.addActionListener(e -> {
            dispose();
            new DeleteReview();
        });

        setVisible(true);
    }

    private void setButtonStyles(JButton button, Color buttonColor, Color buttonHoverColor) {
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addMouseListener(new ButtonHoverListener(button, buttonColor, buttonHoverColor));
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
