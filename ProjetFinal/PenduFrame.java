import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author yasmi
 */
public class PenduFrame extends JFrame {
    private int caseSize = 50; // Taille des boutons
    private ImagePanel imagePanel;
    private LettersPanel lettersPanel;
    private MotsPanel motsPanel;
    private HomePage homePage; // Reference to HomePage

    public PenduFrame(HomePage homePage) {
        this.homePage = homePage; // Initialize the reference
        initGui();
    }

    private void initGui() {
        this.setTitle("Jeu du Pendu");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // open in fullscrenn
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran
        this.setLayout(new BorderLayout());

        JPanel root = new JPanel(new BorderLayout(5, 5));

        // Création des panneaux
        imagePanel = new ImagePanel();
        motsPanel = new MotsPanel();
        lettersPanel = new LettersPanel(caseSize, motsPanel, imagePanel);
        verifier();

        // Ajout des panneaux au conteneur principal
        root.add(imagePanel, BorderLayout.CENTER);
        root.add(motsPanel, BorderLayout.NORTH);
        root.add(lettersPanel, BorderLayout.PAGE_END);

        this.add(root, BorderLayout.CENTER);

        // Add "Retour" button with image
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
            homePage.showHomePage(); // Show the HomePage when returning
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(boutonRetour);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // this.pack(); // Removed to avoid conflicts with full screen
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void verifier() {
        if (motsPanel.isMotDevine()) {
            JOptionPane.showMessageDialog(this, "Bravo ! Tu as gagné !");
            resetJeu();
        }
        if (imagePanel.getErreurs() >= 6) {
            JOptionPane.showMessageDialog(this, "Perdu ! Le mot était: " + motsPanel.getMotSecret());
            resetJeu();
        }
    }

    private void resetJeu() {
        imagePanel.resetErreurs();
        motsPanel.nouveauMot();
        lettersPanel.resetBoutons();
    }
}