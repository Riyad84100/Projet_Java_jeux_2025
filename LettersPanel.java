package com.mycompany.test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 *
 * @author yasmi
 */

public class LettersPanel extends JPanel {
    private int caseSize; // Taille des boutons
    //private ActionListener letterClickListener; // Listener pour gérer les clics des boutons

    public LettersPanel(int caseSize, MotsPanel motsPanel, ImagePanel imagePanel) {
        this.caseSize = caseSize;
        this.setLayout(new GridLayout(3, 10, 5, 5));
        // Créer des lignes de boutons
        String[] rows = {
            "AZERTYUIOP",
            "QSDFGHJKLM",
            "WXCVBN"
        };

        for (String row : rows) {
            JPanel linePanel = new JPanel(); // Panneau pour une ligne
            //linePanel.setBackground(Color.WHITE);

            // Ajouter chaque lettre dans la ligne
            for (char letter : row.toCharArray()) {
                JButton button = new JButton(String.valueOf(letter));
                button.setPreferredSize(new Dimension(50, 40));
                button.setFont(new Font("Arial", Font.BOLD, 14));
                button.setFocusPainted(false);
                button.setBackground(Color.WHITE);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true)); // Le "true" active les coins arrondis

                // Action sur clic
                button.addActionListener(e -> {
                    boolean correct = motsPanel.devinerLettre(letter);
                    if (!correct) {
                        button.setBackground(new Color(225, 100, 110));
                        imagePanel.incrementerErreurs();
                    }
                    else{
                        button.setBackground(new Color(15, 180, 110));
                    }
                    // Appel de la méthode verifier pour vérifier l'état du jeu après chaque tentative
                    SwingUtilities.getWindowAncestor(this).repaint(); // Met à jour l'interface graphique
                    ((HomePage) SwingUtilities.getWindowAncestor(this)).verifier();
                });

                linePanel.add(button); // Ajouter le bouton à la ligne
            }

            this.add(linePanel); // Ajouter la ligne au panneau principal
        }

        // Fixer une hauteur minimale pour éviter que le panneau s'étire trop
        this.setPreferredSize(new Dimension(600, 200));
        
    }
    public void resetBoutons(){
        // Réactivation des boutons désactivés
        for (Component comp : this.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component button : ((JPanel) comp).getComponents()) {
                    if (button instanceof JButton) {
                        button.setBackground(Color.WHITE); // Réactivation du bouton
                    }
                }
            }
        }
    }

}
    