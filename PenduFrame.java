import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PenduFrame extends JFrame {
    private int caseSize = 50; // Taille des boutons
    private ImagePanel imagePanel;
    private LettersPanel lettersPanel;
    private MotsPanel motsPanel;
    private JButton homeButton;
    private HomePage homePage;

    public PenduFrame() {
        initGui();
    }

    private void initGui() {
        JPanel root = new JPanel(new BorderLayout(5, 5));

        // Création des panneaux
        imagePanel = new ImagePanel();
        motsPanel = new MotsPanel();
        lettersPanel = new LettersPanel(caseSize, motsPanel, imagePanel);
        
        // Création du bouton de retour
        homeButton = new JButton();
        homeButton.setPreferredSize(new Dimension(100, 50));
        try {
            Image img = ImageIO.read(new File("src/images/fleche.png"));
            Image scaledImg = img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
            homeButton.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            homeButton.setText("Home");
        }
        
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setContentAreaFilled(false);
        
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Retour à la page d'accueil");
                dispose();
                homePage.showHomePage();
            }
        });

        // Panel pour regrouper le bouton et le mot à trouver
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(homeButton, BorderLayout.WEST);
        topPanel.add(motsPanel, BorderLayout.CENTER);

        // Ajout des composants à la fenêtre
        root.add(imagePanel, BorderLayout.CENTER);
        root.add(topPanel, BorderLayout.NORTH);
        root.add(lettersPanel, BorderLayout.PAGE_END);
        
        // Configuration de la fenêtre
        this.add(root);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void verifier() {
        if (motsPanel.isMotDevine()) {
            JOptionPane.showMessageDialog(this, "Bravo ! Vous avez gagné !");
            resetJeu();
        } if (imagePanel.getErreurs() >= 6) {
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
