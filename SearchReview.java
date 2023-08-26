import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class SearchReview extends JFrame {

    private JButton homeButton;
    private JButton reviewButton;
    private JButton profileButton;
    private JButton logoutButton;
    private JButton exitButton;
    private JButton submitButton;
    private JButton backButton;
    private JTextField drugNameTextField;
    private JTextArea resultTextArea;
    private JScrollPane resultScrollPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchReview::new);
    }

    public SearchReview() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Search Review");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create the layout components
        JPanel topPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel resultPanel = new JPanel();
        JPanel exitPanel = new JPanel();

        // Create the buttons
        homeButton = new JButton("Home");
        reviewButton = new JButton("Review");
        profileButton = new JButton("Profile");
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Exit");
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        // Set the button colors
        Color buttonColor = new Color(17, 30, 108);
        Color buttonHoverColor = new Color(102, 204, 255);
        setButtonStyles(homeButton, buttonColor, buttonHoverColor);
        setButtonStyles(reviewButton, buttonColor, buttonHoverColor);
        setButtonStyles(profileButton, buttonColor, buttonHoverColor);
        setButtonStyles(logoutButton, buttonColor, buttonHoverColor);
        setButtonStyles(exitButton, buttonColor, buttonHoverColor);
        setButtonStyles(submitButton, buttonColor, buttonHoverColor);
        setButtonStyles(backButton, buttonColor, buttonHoverColor);

        submitButton.setPreferredSize(new Dimension(1, submitButton.getPreferredSize().height));
        // Set the layouts
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    leftPanel.setLayout(new GridLayout(0, 1, 0, 10));
    rightPanel.setLayout(new BorderLayout());
    buttonPanel.setLayout(new GridLayout(4, 1, 0, 10));
    resultPanel.setLayout(new BorderLayout());
    exitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    // Set background color for panels
    topPanel.setBackground(Color.WHITE);
    leftPanel.setBackground(Color.WHITE);
    rightPanel.setBackground(Color.WHITE);
    buttonPanel.setBackground(Color.WHITE);
    resultPanel.setBackground(Color.WHITE);
    exitPanel.setBackground(Color.WHITE);

    // Add components to their respective containers
    topPanel.add(homeButton);
    topPanel.add(reviewButton);
    topPanel.add(profileButton);
    topPanel.add(logoutButton);
    topPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to right-align the exit button
    topPanel.add(exitButton); // Add the exit button to the top panel

    JLabel titleLabel = new JLabel("Search Drug Review");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
   // leftPanel.add(titleLabel);
    leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    drugNameTextField = new JTextField();
    buttonPanel.add(titleLabel);
    buttonPanel.add(drugNameTextField);
    buttonPanel.add(submitButton);
    buttonPanel.add(backButton);
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    resultTextArea = new JTextArea();
    resultTextArea.setEditable(false);
    resultScrollPane = new JScrollPane(resultTextArea);
    resultScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    resultPanel.add(resultScrollPane, BorderLayout.CENTER);
    resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search Result"));

    // Add panels to the frame
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(topPanel, BorderLayout.NORTH);
    getContentPane().add(leftPanel, BorderLayout.WEST);
    getContentPane().add(rightPanel, BorderLayout.CENTER);
    getContentPane().add(exitPanel, BorderLayout.SOUTH);
    rightPanel.add(buttonPanel, BorderLayout.NORTH);
    rightPanel.add(resultPanel, BorderLayout.CENTER);


        // Register event listeners
        submitButton.addActionListener(e -> {
            String drugName = drugNameTextField.getText();

            // Clear previous results
            resultTextArea.setText("");

            // Database connection details
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "sys as sysdba";
            String password = "vasavi";

            try {
                // Establish a database connection
                Connection connection = DriverManager.getConnection(url, username, password);

                // Query to retrieve drug information
                String drugQuery = "SELECT * FROM drug WHERE DRUG_NAME = ?";
                PreparedStatement drugStatement = connection.prepareStatement(drugQuery);
                drugStatement.setString(1, drugName);
                ResultSet drugResult = drugStatement.executeQuery();

                // Display drug information
            
                if(!drugResult.next()) {
                    resultTextArea.append("No drug information found for the given name.");
                }else{
                displayDrugInfo(drugResult);
                // Query to retrieve doctor information
                String doctorQuery = "SELECT * FROM doctor WHERE DOC_ID IN (SELECT DOC_ID FROM prescription WHERE DRUG_ID IN (SELECT DRUG_ID FROM drug WHERE DRUG_NAME = ?))";
                PreparedStatement doctorStatement = connection.prepareStatement(doctorQuery);
                doctorStatement.setString(1, drugName);
                ResultSet doctorResult = doctorStatement.executeQuery();

                // Display doctor information
                displayDoctorInfo(doctorResult);

                // Query to retrieve prescription information
                String prescriptionQuery = "SELECT * FROM prescription WHERE DRUG_ID IN (SELECT DRUG_ID FROM drug WHERE DRUG_NAME = ?)";
                PreparedStatement prescriptionStatement = connection.prepareStatement(prescriptionQuery);
                prescriptionStatement.setString(1, drugName);
                ResultSet prescriptionResult = prescriptionStatement.executeQuery();

                // Display prescription information
                displayPrescriptionInfo(prescriptionResult);

                // Query to retrieve review information
                String reviewQuery = "SELECT * FROM review WHERE DRUG_ID IN (SELECT DRUG_ID FROM drug WHERE DRUG_NAME = ?)";
                PreparedStatement reviewStatement = connection.prepareStatement(reviewQuery);
                reviewStatement.setString(1, drugName);
                ResultSet reviewResult = reviewStatement.executeQuery();

                // Display review information
                displayReviewInfo(reviewResult);

                // Query to retrieve side effects information
                String sideEffectsQuery = "SELECT * FROM side_effects WHERE DRUG_ID IN (SELECT DRUG_ID FROM drug WHERE DRUG_NAME = ?)";
                PreparedStatement sideEffectsStatement = connection.prepareStatement(sideEffectsQuery);
                sideEffectsStatement.setString(1, drugName);
                ResultSet sideEffectsResult = sideEffectsStatement.executeQuery();

                // Display side effects information
                displaySideEffectsInfo(sideEffectsResult);

                // Close the database connection
                connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                resultTextArea.append("Error occurred while retrieving data from the database.");
            }
        });


        backButton.addActionListener(e -> {
            dispose();
            Review review = new Review();
            review.createAndShowGUI();
        });

        exitButton.addActionListener(e -> dispose());

        logoutButton.addActionListener(e -> {
            dispose();
            LoginSignUpScreen loginSignUpScreen = new LoginSignUpScreen();
            loginSignUpScreen.setVisible(true);
        });

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

        setVisible(true);
    }


    private void displayDrugInfo(ResultSet drugResult) throws SQLException {
        resultTextArea.append("Drug Information:\n");
        resultTextArea.append("Drug ID: " + drugResult.getInt("DRUG_ID") + "\n");
        resultTextArea.append("Drug Name: " + drugResult.getString("DRUG_NAME") + "\n");
        resultTextArea.append("Description: " + drugResult.getString("DRUG_DESCRIPTION") + "\n");
        resultTextArea.append("Price: " + drugResult.getDouble("PRICE") + "\n");
        resultTextArea.append("Category: " + drugResult.getString("CATEGORY") + "\n");
        resultTextArea.append("Date Added: " + drugResult.getDate("DATE_ADDED") + "\n");
        resultTextArea.append("\n");
    }


    private void displayDoctorInfo(ResultSet doctorResult) throws SQLException {
        resultTextArea.append("Doctor Information:\n");
        while (doctorResult.next()) {
            resultTextArea.append("Doctor ID: " + doctorResult.getInt("DOC_ID") + "\n");
            resultTextArea.append("Doctor Name: " + doctorResult.getString("DOC_NAME") + "\n");
            resultTextArea.append("Specialization: " + doctorResult.getString("SPECIALIZATION") + "\n");
            resultTextArea.append("Contact Details: " + doctorResult.getString("CONTACT_DETAILS") + "\n");
            resultTextArea.append("\n");
        }
    }

    private void displayPrescriptionInfo(ResultSet prescriptionResult) throws SQLException {
        resultTextArea.append("Prescription Information:\n");
        while (prescriptionResult.next()) {
            resultTextArea.append("Prescription ID: " + prescriptionResult.getInt("PRES_ID") + "\n");
            resultTextArea.append("Username: " + prescriptionResult.getString("USERNAME") + "\n");
            resultTextArea.append("Doctor ID: " + prescriptionResult.getInt("DOC_ID") + "\n");
            resultTextArea.append("Drug ID: " + prescriptionResult.getInt("DRUG_ID") + "\n");
            resultTextArea.append("Diagnosis: " + prescriptionResult.getString("DIAGNOSIS") + "\n");
            resultTextArea.append("Date Added: " + prescriptionResult.getDate("DATE_ADDED") + "\n");
            resultTextArea.append("\n");
        }
    }

    private void displayReviewInfo(ResultSet reviewResult) throws SQLException {
        resultTextArea.append("Review Information:\n");
        while (reviewResult.next()) {
            resultTextArea.append("Review ID: " + reviewResult.getInt("REVIEW_ID") + "\n");
            resultTextArea.append("Drug ID: " + reviewResult.getInt("DRUG_ID") + "\n");
            resultTextArea.append("Username: " + reviewResult.getString("USERNAME") + "\n");
            resultTextArea.append("Review Text: " + reviewResult.getString("REVIEW_TEXT") + "\n");
            resultTextArea.append("Overall Rating: " + reviewResult.getInt("OVERALL_RATING") + "\n");
            resultTextArea.append("Recommend: " + reviewResult.getString("RECOMMEND") + "\n");
            resultTextArea.append("Review Date: " + reviewResult.getDate("REVIEW_DATE") + "\n");
            resultTextArea.append("\n");
        }
    }

    private void displaySideEffectsInfo(ResultSet sideEffectsResult) throws SQLException {
        resultTextArea.append("Side Effects Information:\n");
        while (sideEffectsResult.next()) {
            resultTextArea.append("Side Effect ID: " + sideEffectsResult.getInt("SIDE_EFFECT_ID") + "\n");
            resultTextArea.append("Drug ID: " + sideEffectsResult.getInt("DRUG_ID") + "\n");
            resultTextArea.append("Username: " + sideEffectsResult.getString("USERNAME") + "\n");
            resultTextArea.append("Date Added: " + sideEffectsResult.getDate("DATE_ADDED") + "\n");
            resultTextArea.append("Side Effects Description: " + sideEffectsResult.getString("SIDE_EFFECTS_DESCRIPTION") + "\n");
            resultTextArea.append("Symptom: " + sideEffectsResult.getString("SYMPTOM") + "\n");
            resultTextArea.append("\n");
        }
    }


    private void setButtonStyles(JButton button, Color bgColor, Color hoverColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }
}
