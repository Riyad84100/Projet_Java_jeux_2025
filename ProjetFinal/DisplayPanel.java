import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class DisplayPanel extends JFrame {
    @SuppressWarnings("unused")
    private String difficulty;
    @SuppressWarnings("unused")
    private HomePage homePage; // Référence à la page d'accueil

    public DisplayPanel(String difficulty, HomePage homePage) {
        this.difficulty = difficulty;
        this.homePage = homePage; // Stocker la référence à HomePage

        setTitle("Jeu de Coloriage - " + difficulty);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer la fenêtre sans quitter l'application
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Créer un bouton pour revenir à la page principale
        JButton backButton = new JButton();
        try {
            // Charger l'image pour le bouton
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/src/images/fleche.png"));
            Image originalImage = originalIcon.getImage();

            // Redimensionner l'image à 120x40 pixels
            Image resizedImage = originalImage.getScaledInstance(120, 40, Image.SCALE_SMOOTH);

            // Créer un nouvel ImageIcon avec l'image redimensionnée
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            // Appliquer l'image redimensionnée au bouton
            backButton.setIcon(resizedIcon);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image du bouton : " + e.getMessage());
            backButton.setText("Retour"); // Texte de secours si l'image ne charge pas
        }

        backButton.setPreferredSize(new Dimension(120, 40)); // Définir la taille du bouton
        backButton.setContentAreaFilled(false); // Désactiver le remplissage de la zone de contenu
        backButton.setBorderPainted(false);    // Désactiver la bordure du bouton
        backButton.setFocusPainted(false);     // Désactiver l'effet de focus

        // Ajouter l'action pour revenir à la page principale
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fermer la fenêtre actuelle
                homePage.showHomePage(); // Réafficher la page d'accueil
            }
        });

        // Ajouter le bouton en haut à gauche
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false); // Rendre le panel transparent
        topPanel.add(backButton);

        // Créer un panel personnalisé pour le fond d'écran
        BackgroundPanel backgroundPanel = new BackgroundPanel("/src/images/shrek3.png");
        backgroundPanel.setLayout(new BorderLayout());

        // Ajouter le bouton en haut à gauche du panel de fond
        backgroundPanel.add(topPanel, BorderLayout.NORTH);

        // Créer le tableau de dessin
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int tableauWidth = screenSize.width / 2;
        int tableauHeight = screenSize.height / 2;

        Tableau tableau = new Tableau();
        tableau.setPreferredSize(new Dimension(tableauWidth, tableauHeight));

        // Créer la barre d'outils
        BoutonBarBis boutonBarBis = new BoutonBarBis(tableau, difficulty);

        // Centrer le tableau
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Rendre le panel transparent
        centerPanel.add(tableau);

        // Ajouter les composants au panel de fond
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        backgroundPanel.add(boutonBarBis, BorderLayout.SOUTH);

        // Ajouter le panel de fond à la fenêtre
        add(backgroundPanel);

        // Ajouter un écouteur pour fermer l'application lorsque la fenêtre est fermée
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Fermer l'application lorsque l'utilisateur clique sur la croix
                System.exit(0);
            }
        });
    }

    // Classe interne pour le panel avec fond d'écran
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                // Charger l'image de fond
                backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image de fond : " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Redimensionner l'image pour remplir tout le panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}