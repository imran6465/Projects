import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeleteReview {
    private JFrame frame;
    private JTextField drugNameTextField;
    private JButton searchButton;
    private JTextArea resultTextArea;
    private JButton backButton;
    private JButton exitButton;

    public DeleteReview() {
        frame = new JFrame("Delete Review");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("Drug Name:"));
        drugNameTextField = new JTextField(20);
        inputPanel.add(drugNameTextField);

        JPanel buttonPanel = new JPanel();
        searchButton = new JButton("Search");
        backButton = new JButton("Back");
        exitButton = new JButton("Exit");
        buttonPanel.add(searchButton);
        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        resultTextArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Reviews"));
        resultTextArea.setBackground(new Color(224, 236, 255));

        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        createListeners();

        frame.pack();
        frame.setVisible(true);
    }

    private void createListeners() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String drugName = drugNameTextField.getText();

                if (drugName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a drug name.");
                    return;
                }

                try {
                    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "sys as sysdba", "vasavi");

                    // Check if review exists for the specified drug and user
                    String loggedInUserQuery = "SELECT * FROM user_details WHERE isloggedin = 'Y'";
                    PreparedStatement loggedInUserStatement = connection.prepareStatement(loggedInUserQuery);
                    ResultSet loggedInUserResult = loggedInUserStatement.executeQuery();
                    String loggedInUser = "";
                    if (loggedInUserResult.next()) {
                        loggedInUser = loggedInUserResult.getString("USERNAME");
                    }

                    String reviewQuery = "SELECT * FROM review WHERE USERNAME = ?";
                    PreparedStatement reviewStatement = connection.prepareStatement(reviewQuery);
                    reviewStatement.setString(1, loggedInUser);
                    ResultSet reviewResult = reviewStatement.executeQuery();

                    StringBuilder reviews = new StringBuilder();
                    while (reviewResult.next()) {
                        int reviewID = reviewResult.getInt("REVIEW_ID");
                        String reviewText = reviewResult.getString("REVIEW_TEXT");
                        reviews.append("Review ID: ").append(reviewID).append("\n")
                                .append("Review Text: ").append(reviewText).append("\n\n");
                    }

                    if (reviews.length() > 0) {
                        resultTextArea.setText(reviews.toString());
                    } else {
                        resultTextArea.setText("No reviews found for the specified drug.");
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error retrieving reviews. Please try again.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to navigate back to the previous screen (LoginSignUpScreen.java)
                frame.dispose();
                new LoginSignUpScreen();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DeleteReview();
            }
        });
    }
}
