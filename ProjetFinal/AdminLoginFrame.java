import java.awt.*;
import javax.swing.*;

/**
 *
 * @author yasmi
 */

public class AdminLoginFrame extends JFrame {
    private boolean authenticated = false;
    private MotDePassePanel motDePassePanel;
    @SuppressWarnings("unused")
    private HomePage homePage; // Reference to HomePage

    @SuppressWarnings("unused")
    public AdminLoginFrame(HomePage homePage) {
        this.homePage = homePage; // Initialize the reference
        JButton boutonRetour = new JButton();
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/src/images/fleche.png"));
        Image resizedImage = originalIcon.getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH);
        boutonRetour.setIcon(new ImageIcon(resizedImage));
        boutonRetour.setPreferredSize(new Dimension(120, 40));
        boutonRetour.setContentAreaFilled(false);
        boutonRetour.setBorderPainted(false);
        boutonRetour.setFocusPainted(false);

        boutonRetour.addActionListener(e -> {
            dispose();
            homePage.setVisible(true); // Show the HomePage when returning
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(boutonRetour);
        this.add(topPanel, BorderLayout.NORTH); // Add the top panel to the frame

        this.setTitle("Connexion Administrateur");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran
        this.setLayout(new GridBagLayout()); // Centrage parfait du panel

        motDePassePanel = new MotDePassePanel();
        motDePassePanel.setLoginListener(success -> {
            if (success) {
                authenticated = true;
                JOptionPane.showMessageDialog(this, "Connexion réussie ! ✅", "Succès", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Fermer la fenêtre
            } else {
                JOptionPane.showMessageDialog(this, "Mot de passe incorrect. ⛔", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(motDePassePanel, gbc);

        this.setVisible(true);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage(); // Create an instance of HomePage
            new AdminLoginFrame(homePage);
        });
    }
}
