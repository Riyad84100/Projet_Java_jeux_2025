import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author
 */
public class PenduPanel extends JPanel {
    private ImagePanel imagePanel;
    private LettersPanel lettersPanel;
    private MotsPanel motsPanel;

    public PenduPanel() {
        initGui();
    }

    private void initGui() {
        this.setLayout(new BorderLayout());

        // Création des panneaux
        imagePanel = new ImagePanel();
        motsPanel = new MotsPanel();
        lettersPanel = new LettersPanel(50, motsPanel, imagePanel); // Taille des boutons fixée à 50
        verifier();

        // Ajout des panneaux au conteneur principal
        this.add(imagePanel, BorderLayout.CENTER);
        this.add(motsPanel, BorderLayout.NORTH);
        this.add(lettersPanel, BorderLayout.PAGE_END);

        // Ajouter un bouton "Réinitialiser" pour recommencer le jeu
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.addActionListener(e -> resetJeu());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(resetButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void verifier() {
    SwingUtilities.invokeLater(() -> {
        if (motsPanel.isMotDevine()) {
            JOptionPane.showMessageDialog(this, "Bravo ! Tu as gagné !");
            resetJeu();
        } else if (imagePanel.getErreurs() >= 6) {
            JOptionPane.showMessageDialog(this, "Perdu ! Le mot était : " + motsPanel.getMotSecret());
            resetJeu();
        }
    });
}

private void resetJeu() {
    imagePanel.resetErreurs(); // Réinitialiser le compteur d'erreurs
    motsPanel.nouveauMot(); // Choisir un nouveau mot
    lettersPanel.resetBoutons(); // Réinitialiser les boutons et leurs couleurs
    revalidate();
    repaint(); // Rafraîchir l'affichage
}
}