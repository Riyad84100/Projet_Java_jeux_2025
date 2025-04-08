package com.mycompany.test_finale;
import javax.swing.*;
import java.awt.*;

public class DessinPanel extends JPanel {
    private Tableau tableau;
    private BoutonBarBis boutonBar;
    private String difficulty;
    private BackgroundPanel backgroundPanel;

    public DessinPanel(String difficulty) {
        this.difficulty = difficulty;
        initUI();
    }

    public void setDifficulty(String difficulty) {
        if (!this.difficulty.equals(difficulty)) {
            this.difficulty = difficulty;
            updateButtonBar();
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Panneau de fond avec l'image
        backgroundPanel = new BackgroundPanel("src/images/shrek3.png");
        backgroundPanel.setLayout(new BorderLayout());

        // Zone de dessin
        tableau = new Tableau();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int tableauWidth = screenSize.width / 2;
        int tableauHeight = screenSize.height / 2;
        tableau.setPreferredSize(new Dimension(tableauWidth, tableauHeight));

        // Centrer la zone de dessin
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(tableau);

        // Créer la barre de boutons initiale
        boutonBar = new BoutonBarBis(tableau, difficulty);

        // Ajouter les composants
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        backgroundPanel.add(boutonBar, BorderLayout.SOUTH);

        add(backgroundPanel, BorderLayout.CENTER);
    }

    private void updateButtonBar() {
        // Supprimer l'ancienne barre de boutons
        backgroundPanel.remove(boutonBar);
        
        // Créer une nouvelle barre avec la nouvelle difficulté
        boutonBar = new BoutonBarBis(tableau, difficulty);
        
        // Ajouter la nouvelle barre
        backgroundPanel.add(boutonBar, BorderLayout.SOUTH);
        
        // Rafraîchir l'affichage
        revalidate();
        repaint();
    }

    // Classe interne BackgroundPanel inchangée
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