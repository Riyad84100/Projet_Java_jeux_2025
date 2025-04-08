package com.mycompany.test_finale;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class CalculPanel extends JPanel {
    private String difficulty;
    private JLabel labelCalcul;
    private JTextField champReponse;
    private JButton boutonVerifier;
    private JButton boutonSolution;
    private JButton boutonNouveau;
    private JLabel labelResultat;
    private int nombre1, nombre2, resultat;
    private String operateur;

    public CalculPanel(String difficulty) {
        this.difficulty = difficulty;
        initUI();
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        genererNouveauCalcul(); // Générer un nouveau calcul avec la nouvelle difficulté
        revalidate();
        repaint();
    }

    @SuppressWarnings("unused")
    private void initUI() {
        setLayout(new BorderLayout());

        // Panneau de fond avec l'image
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/marais.png");
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panneau supérieur avec le calcul et la réponse
        JPanel panelHaut = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelHaut.setOpaque(false); // Rendre transparent pour afficher le fond
        labelCalcul = new JLabel();
        labelCalcul.setForeground(Color.WHITE);
        labelCalcul.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        champReponse = new JTextField(15);
        champReponse.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        panelHaut.add(labelCalcul);
        panelHaut.add(champReponse);

        // Panneau des boutons avec icônes
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBoutons.setOpaque(false); // Rendre transparent pour afficher le fond

        // Initialisation des boutons
        boutonVerifier = new JButton();
        boutonSolution = new JButton();
        boutonNouveau = new JButton();

        // Charger les icônes pour les boutons
        loadButtonIcon(boutonVerifier, "src/images/check.png", 60, 60);
        loadButtonIcon(boutonSolution, "src/images/lampe.png", 60, 60);
        loadButtonIcon(boutonNouveau, "src/images/plus.png", 60, 60);

        // Ajouter les boutons au panneau
        panelBoutons.add(boutonVerifier);
        panelBoutons.add(boutonSolution);
        panelBoutons.add(boutonNouveau);

        // Label pour afficher le résultat
        labelResultat = new JLabel("", SwingConstants.CENTER);
        labelResultat.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        labelResultat.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajouter les composants au panneau de fond
        backgroundPanel.add(panelHaut, BorderLayout.NORTH);
        backgroundPanel.add(panelBoutons, BorderLayout.SOUTH);
        backgroundPanel.add(labelResultat, BorderLayout.CENTER);

        // Ajouter le panneau de fond au CalculPanel
        add(backgroundPanel);

        // Générer un nouveau calcul
        genererNouveauCalcul();

        // Ajouter les actions des boutons
        boutonVerifier.addActionListener(e -> verifierReponse());
        boutonSolution.addActionListener(e -> afficherSolution());
        boutonNouveau.addActionListener(e -> genererNouveauCalcul());
    }

    private void loadButtonIcon(JButton button, String imagePath, int width, int height) {
        try {
        Image originalImage = ImageIO.read(new File(imagePath));
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(resizedImage));
        button.setPreferredSize(new Dimension(width, height));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    } catch (IOException e) {
        System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
        button.setText("Image manquante");
    }
}
    private void showSizeSlider(JButton sourceButton) {
        // Exemple : affiche un slider dans une boîte de dialogue
        JSlider slider = new JSlider(20, 100, 50);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        int result = JOptionPane.showConfirmDialog(
            this,
            slider,
            "Choisis la taille",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            int tailleChoisie = slider.getValue();
            System.out.println("Taille choisie : " + tailleChoisie);
            // Tu peux faire quelque chose avec la taille choisie ici
        }
    }

    private void genererNouveauCalcul() {
        Random random = new Random();
        if ("facile".equals(difficulty)) {
            do {
                nombre1 = random.nextInt(10);
                nombre2 = random.nextInt(10);
                operateur = random.nextBoolean() ? "+" : "-";
                resultat = operateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            } while (resultat < 0);
        } else {
            int choixOperation = random.nextInt(3);
            if (choixOperation < 2) {
                nombre1 = random.nextInt(1000);
                nombre2 = random.nextInt(1000);
                operateur = (choixOperation == 0) ? "+" : "-";
                resultat = operateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            } else {
                nombre1 = random.nextInt(10);
                nombre2 = random.nextInt(10);
                operateur = "x";
                resultat = nombre1 * nombre2;
            }
        }
        labelCalcul.setText(nombre1 + " " + operateur + " " + nombre2 + " = ?");
        champReponse.setText("");
        champReponse.setEnabled(true); // Réactiver le champ de saisie
        boutonVerifier.setEnabled(true); // Réactiver le bouton "Vérifier"
        boutonVerifier.setBackground(null); // Réinitialiser la couleur du bouton
        boutonSolution.setEnabled(true); // Réactiver le bouton "Solution"
        boutonSolution.setBackground(null); // Réinitialiser la couleur du bouton
        labelResultat.setText("");
        champReponse.requestFocus();
    }

    private void verifierReponse() {
        try {
            int reponseUtilisateur = Integer.parseInt(champReponse.getText().trim());
            if (reponseUtilisateur == resultat) {
                labelResultat.setText("<html><span style='color:white;'>Bonne réponse !</span></html>");
            } else {
                labelResultat.setText("<html><span style='color:white;'>Mauvaise réponse !</span></html>");
            }

            // Désactiver les boutons "Vérifier" et "Solution" uniquement si la saisie est valide
            boutonVerifier.setEnabled(false);
            boutonSolution.setEnabled(false);
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si la saisie est invalide
            labelResultat.setText("<html><span style='color:white;'>Veuillez entrer un nombre valide !</span></html>");
        }
    }

    private void afficherSolution() {
        labelResultat.setForeground(Color.WHITE);
        labelResultat.setText("La solution est " + resultat);
        champReponse.setEnabled(false); // Désactiver le champ de saisie
        boutonVerifier.setEnabled(false); // Désactiver le bouton "Vérifier"
        boutonVerifier.setBackground(Color.RED); // Changer la couleur pour inciter à cliquer sur "Nouveau"
        boutonSolution.setEnabled(false); // Désactiver le bouton "Solution"
        boutonSolution.setBackground(Color.RED); // Changer la couleur pour inciter à cliquer sur "Nouveau"
    }

    // Classe interne pour gérer le fond d'écran
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image de fond : " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}