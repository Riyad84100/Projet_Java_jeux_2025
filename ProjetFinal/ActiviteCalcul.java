import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class ActiviteCalcul extends JFrame {
    private JLabel labelCalcul;
    private JTextField champRéponse;
    private JButton boutonVérifier;
    private JButton boutonSolution;
    private JButton boutonNouveau;
    private JButton boutonRetour;
    private JLabel labelRésultat;
    private int nombre1, nombre2, résultat;
    private String opérateur;
    private boolean niveauFacile;
    @SuppressWarnings("unused")
    private HomePage homePage;

    @SuppressWarnings("unused")
    public ActiviteCalcul(String difficulty, HomePage homePage) {
        this.niveauFacile = difficulty.equals("facile");
        this.homePage = homePage;

        setTitle("Jeu de Calcul - " + difficulty);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Bouton Retour
        boutonRetour = new JButton();
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/src/images/fleche.png"));
            Image resizedImage = originalIcon.getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH);
            boutonRetour.setIcon(new ImageIcon(resizedImage));
        } catch (Exception e) {
            boutonRetour.setText("Retour");
        }

        boutonRetour.setPreferredSize(new Dimension(120, 40));
        boutonRetour.setContentAreaFilled(false);
        boutonRetour.setBorderPainted(false);
        boutonRetour.setFocusPainted(false);

        boutonRetour.addActionListener(e -> {
            dispose();
            homePage.showHomePage();
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(boutonRetour);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        JPanel panelHaut = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelHaut.setOpaque(false);

        labelCalcul = new JLabel();
        labelCalcul.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        labelCalcul.setForeground(Color.WHITE);
        panelHaut.add(labelCalcul);

        champRéponse = new JTextField(15);
        champRéponse.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        panelHaut.add(champRéponse);

        mainPanel.add(panelHaut, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        panelBoutons.setOpaque(false);

        boutonVérifier = new JButton("Vérifier");
        boutonSolution = new JButton("Solution");
        boutonNouveau = new JButton("Nouveau");
        loadButtonIcon(boutonVérifier, "/src/images/check.png", 60,60);
        loadButtonIcon(boutonSolution, "/src/images/lampe.png", 60,60);
        loadButtonIcon(boutonNouveau, "/src/images/plus.png", 60,60);

        panelBoutons.add(boutonVérifier);
        panelBoutons.add(boutonSolution);
        panelBoutons.add(boutonNouveau);
        mainPanel.add(panelBoutons, BorderLayout.SOUTH);

        labelRésultat = new JLabel("", SwingConstants.CENTER);
        labelRésultat.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        labelRésultat.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(labelRésultat, BorderLayout.NORTH);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/src/images/marais.png");
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        
        add(backgroundPanel);
        générerNouveauCalcul();

        boutonVérifier.addActionListener(e -> vérifierRéponse());
        
        boutonSolution.addActionListener(e -> {
            afficherSolution();
            // Désactiver seulement après avoir montré la solution
            champRéponse.setEnabled(false);
            boutonVérifier.setEnabled(false);
            boutonSolution.setEnabled(false);
        });
        
        boutonNouveau.addActionListener(e -> {
            générerNouveauCalcul();
            // Tout réactiver pour un nouveau calcul
            champRéponse.setEnabled(true);
            boutonVérifier.setEnabled(true);
            boutonSolution.setEnabled(true);
        });
    }

    private void générerNouveauCalcul() {
        Random random = new Random();
        if (niveauFacile) {
            nombre1 = random.nextInt(10);
            nombre2 = random.nextInt(10);
            opérateur = random.nextBoolean() ? "+" : "-";
            résultat = opérateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            while (résultat < 0) {
                nombre1 = random.nextInt(10);
                nombre2 = random.nextInt(10);
                résultat = opérateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            }
        } else {
            int choixOpération = random.nextInt(3);
            if (choixOpération < 2) {
                nombre1 = random.nextInt(1000);
                nombre2 = random.nextInt(1000);
                opérateur = (choixOpération == 0) ? "+" : "-";
                résultat = opérateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            } else {
                nombre1 = random.nextInt(10);
                nombre2 = random.nextInt(10);
                opérateur = "x";
                résultat = nombre1 * nombre2;
            }
        }
        labelCalcul.setText(nombre1 + " " + opérateur + " " + nombre2 + " = ?");
        champRéponse.setText("");
        labelRésultat.setText("");
        labelRésultat.setForeground(Color.WHITE);
        champRéponse.requestFocus();
    }

    private void vérifierRéponse() {
        try {
            int réponseUtilisateur = Integer.parseInt(champRéponse.getText().trim());
            labelRésultat.setText(réponseUtilisateur == résultat ? "BRAVOOO ! Tu es aussi fort qu'un ogre" : "Essaie encore ! C'est pas grave...");
        } catch (NumberFormatException e) {

            labelRésultat.setText("Il faut entrer un nombre...  !");
            
        }
    }

    private void afficherSolution() {
        labelRésultat.setText("La solution est " + résultat);
    }

    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    
    private void loadButtonIcon(JButton button, String imagePath, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(resizedImage));
            button.setPreferredSize(new Dimension(width, height));
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            button.setText("Image manquante");
        }
    }
}