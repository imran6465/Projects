import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InsertReview {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextField drugNameTextField;
    private JTextField priceTextField;
    private JTextField categoryTextField;
    private JTextArea drugDescriptionTextArea;
    private JTextField docNameTextField;
    private JTextField specializationTextField;
    private JTextField contactDetailsTextField;
    private JTextArea diagnosisTextArea;
    private JTextArea reviewTextArea;
    private JTextField ratingTextField;
    private JCheckBox recommendCheckBox;
    private JTextField effectTextField;
    private JTextField symptomTextField;
    private JButton submitButton;
    private JButton backButton;

    public InsertReview() {
        createGUI();
        createListeners();
    }

    private void createGUI() {
        // Create the main frame
        frame = new JFrame("Insert Review");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        // Create the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(14, 2, 10, 8));

        // Create the form components
        JLabel drugNameLabel = new JLabel("Drug Name:");
        drugNameTextField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        priceTextField = new JTextField(20);
        JLabel categoryLabel = new JLabel("Category:");
        categoryTextField = new JTextField(20);
        JLabel drugDescriptionLabel = new JLabel("Drug Description:");
        drugDescriptionTextArea = new JTextArea(5, 20);
        drugDescriptionTextArea.setLineWrap(true);
        JLabel docNameLabel = new JLabel("Doctor Name:");
        docNameTextField = new JTextField(20);
        JLabel specializationLabel = new JLabel("Specialization:");
        specializationTextField = new JTextField(20);
        JLabel contactDetailsLabel = new JLabel("Contact Details:");
        contactDetailsTextField = new JTextField(20);
        JLabel diagnosisLabel = new JLabel("Diagnosis:");
        diagnosisTextArea = new JTextArea(5, 20);
        diagnosisTextArea.setLineWrap(true);
        JLabel reviewLabel = new JLabel("Review:");
        reviewTextArea = new JTextArea(5, 20);
        reviewTextArea.setLineWrap(true);
        JLabel ratingLabel = new JLabel("Rating:");
        ratingTextField = new JTextField(20);
        JLabel recommendLabel = new JLabel("Recommend:");
        recommendCheckBox = new JCheckBox();
        JLabel effectLabel = new JLabel("Side Effect:");
        effectTextField = new JTextField( 20);
        JLabel symptomLabel = new JLabel("Symptom:");
        symptomTextField= new JTextField( 20);

        // Create the buttons
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        // Add components to the main panel
        mainPanel.add(drugNameLabel);
        mainPanel.add(drugNameTextField);
        mainPanel.add(priceLabel);
        mainPanel.add(priceTextField);
        mainPanel.add(categoryLabel);
        mainPanel.add(categoryTextField);
        mainPanel.add(drugDescriptionLabel);
        mainPanel.add(new JScrollPane(drugDescriptionTextArea));
        mainPanel.add(docNameLabel);
        mainPanel.add(docNameTextField);
        mainPanel.add(specializationLabel);
        mainPanel.add(specializationTextField);
        mainPanel.add(contactDetailsLabel);
        mainPanel.add(contactDetailsTextField);
        mainPanel.add(diagnosisLabel);
        mainPanel.add(new JScrollPane(diagnosisTextArea));
        mainPanel.add(reviewLabel);
        mainPanel.add(new JScrollPane(reviewTextArea));
        mainPanel.add(ratingLabel);
        mainPanel.add(ratingTextField);
        mainPanel.add(recommendLabel);
        mainPanel.add(recommendCheckBox);
        mainPanel.add(effectLabel);
        mainPanel.add(effectTextField);
        mainPanel.add(symptomLabel);
        mainPanel.add(symptomTextField);
        mainPanel.add(submitButton);
        mainPanel.add(backButton);

        mainPanel.setBackground(new Color(224, 236, 255));
        // Add the main panel to the frame
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void createListeners() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values
                String drugName = drugNameTextField.getText();
                String price = priceTextField.getText();
                String category = categoryTextField.getText();
                String drugDescription = drugDescriptionTextArea.getText();
                String docName = docNameTextField.getText();
                String specialization = specializationTextField.getText();
                String contactDetails = contactDetailsTextField.getText();
                String diagnosis = diagnosisTextArea.getText();
                String review = reviewTextArea.getText();
                String rating = ratingTextField.getText();
                boolean recommend = recommendCheckBox.isSelected();
                String effect=effectTextField.getText();
                String symptom=symptomTextField.getText();

                // Insert data into tables
                try {
                    // Create database connection
                    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "sys as sysdba", "vasavi");

                    // Insert into drug table
                    String drugQuery = "INSERT INTO drug (DRUG_NAME, DRUG_DESCRIPTION, PRICE, CATEGORY) VALUES (?, ?, ?, ?)";
                    PreparedStatement drugStatement = connection.prepareStatement(drugQuery);
                    drugStatement.setString(1, drugName);
                    drugStatement.setString(2, drugDescription);
                    drugStatement.setString(3, price);
                    drugStatement.setString(4, category);
                    drugStatement.executeUpdate();

                    // Get the auto-generated drug_id
                    String lastDrugIdQuery = "SELECT MAX(DRUG_ID) FROM drug";
                    PreparedStatement lastDrugIdStatement = connection.prepareStatement(lastDrugIdQuery);
                    int drugId = 0;
                    ResultSet lastDrugIdResult = lastDrugIdStatement.executeQuery();
                    if (lastDrugIdResult.next()) {
                        drugId = lastDrugIdResult.getInt(1);
                    }

                    // Insert into doctor table
                    String doctorQuery = "INSERT INTO doctor (DOC_NAME, SPECIALIZATION, CONTACT_DETAILS) VALUES (?, ?, ?)";
                    PreparedStatement doctorStatement = connection.prepareStatement(doctorQuery);
                    doctorStatement.setString(1, docName);
                    doctorStatement.setString(2, specialization);
                    doctorStatement.setString(3, contactDetails);
                    doctorStatement.executeUpdate();

                    // Get the auto-generated doc_id
                    String lastDocIdQuery = "SELECT MAX(DOC_ID) FROM doctor";
                    PreparedStatement lastDocIdStatement = connection.prepareStatement(lastDocIdQuery);
                    int docId = 0;
                    ResultSet lastDocIdResult = lastDocIdStatement.executeQuery();
                    if (lastDocIdResult.next()) {
                        docId = lastDocIdResult.getInt(1);
                    }

                    // Insert into prescription table
                    String prescriptionQuery = "INSERT INTO prescription (USERNAME, DOC_ID, DRUG_ID, DIAGNOSIS) VALUES (?, ?, ?, ?)";
                    PreparedStatement prescriptionStatement = connection.prepareStatement(prescriptionQuery);
                    prescriptionStatement.setString(1, "username"); // replace with the actual username
                    prescriptionStatement.setInt(2, docId);
                    prescriptionStatement.setInt(3, drugId);
                    prescriptionStatement.setString(4, diagnosis);
                    prescriptionStatement.executeUpdate();

                    // Insert into review table
                    String reviewQuery = "INSERT INTO review (DRUG_ID, USERNAME, REVIEW_TEXT, OVERALL_RATING, RECOMMEND) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement reviewStatement = connection.prepareStatement(reviewQuery);
                    reviewStatement.setInt(1, drugId);
                    reviewStatement.setString(2, "username"); // replace with the actual username
                    reviewStatement.setString(3, review);
                    reviewStatement.setString(4, rating);
                    reviewStatement.setBoolean(5, recommend);
                    reviewStatement.executeUpdate();

                    // Insert into side_effects table
                    String sideEffectsQuery = "INSERT INTO side_effects (DRUG_ID, USERNAME, DATE_ADDED, SIDE_EFFECTS_DESCRIPTION, SYMPTOM) VALUES (?, ?, NOW(), ?, ?)";
                    PreparedStatement sideEffectsStatement = connection.prepareStatement(sideEffectsQuery);
                    sideEffectsStatement.setInt(1, drugId);
                    sideEffectsStatement.setString(2, "username"); // replace with the actual username
                    sideEffectsStatement.setString(3, effect); // replace with the actual side effects description
                    sideEffectsStatement.setString(4, symptom); // replace with the actual symptom
                    sideEffectsStatement.executeUpdate();

                    // Close database connection
                    connection.close();

                    // Show success message
                    JOptionPane.showMessageDialog(frame, "Review inserted successfully!");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Show error message
                    JOptionPane.showMessageDialog(frame, "Error inserting review. Please try again.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to previous screen
                frame.dispose();
                // Call the method or navigate to the previous screen
                Review review = new Review();
                review.createAndShowGUI();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InsertReview();
            }
        });
    }
}
