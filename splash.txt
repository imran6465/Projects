import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JWindow {
    private static final int SPLASH_SCREEN_WIDTH = 600;
    private static final int SPLASH_SCREEN_HEIGHT = 400;
    private static final int SPLASH_DURATION = 2000; // In milliseconds

    public SplashScreen() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(52, 152, 219)); // Set background color

        // Center section - Splash screen content
        JLabel splashLabel = new JLabel("Welcome to Drug Performance Analysis", SwingConstants.CENTER);
        splashLabel.setFont(new Font("Arial", Font.BOLD, 24));
        splashLabel.setForeground(Color.WHITE); // Set text color
        contentPane.add(splashLabel, BorderLayout.CENTER);

        setContentPane(contentPane);
        setSize(SPLASH_SCREEN_WIDTH, SPLASH_SCREEN_HEIGHT);
        setLocationRelativeTo(null); // Center the splash screen on the screen
        setOpacity(0.9f); // Set opacity (transparency)
    }

    public void showSplashScreen() {
        setVisible(true);

        Timer timer = new Timer(SPLASH_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeSplashScreen();
                showLoginAndSignUpScreen();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void closeSplashScreen() {
        setVisible(false);
        dispose();
    }

    private void showLoginAndSignUpScreen() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginSignUpScreen loginSignUpScreen = new LoginSignUpScreen();
                loginSignUpScreen.setVisible(true);
            }
        });
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
                
                SplashScreen splashScreen = new SplashScreen();
                splashScreen.showSplashScreen();
            }
        });
    }
}
