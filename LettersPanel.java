package com.mycompany.test_finale;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

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
                    char lettre = button.getText().charAt(0);
                    boolean correct = motsPanel.devinerLettre(lettre);
                    button.setEnabled(false);
                
                    if (correct) {
                        button.setBackground(Color.GREEN);
                    } else {
                        button.setBackground(Color.RED);
                        imagePanel.incrementerErreurs();
                    }
                
                    // Appeler la vérification via le PenduPanel parent
                    Container parent = getParent();
                    while (parent != null && !(parent instanceof PenduPanel)) {
                        parent = parent.getParent();
                    }
                    if (parent != null) {
                        ((PenduPanel) parent).verifier();
                    }
                });

                linePanel.add(button); // Ajouter le bouton à la ligne
            }

            this.add(linePanel); // Ajouter la ligne au panneau principal
        }

        // Fixer une hauteur minimale pour éviter que le panneau s'étire trop
        this.setPreferredSize(new Dimension(600, 200));
        
    }
    public void resetBoutons() {
        for (Component comp : this.getComponents()) {
            if (comp instanceof JPanel) {
                // Parcourir les composants de chaque ligne (JPanel)
                for (Component buttonComp : ((JPanel) comp).getComponents()) {
                    if (buttonComp instanceof JButton) {
                        JButton button = (JButton) buttonComp;
                        button.setEnabled(true); // Réactiver le bouton
                        button.setBackground(Color.WHITE); // Réinitialiser la couleur à blanc
                    }
                }
            }
        }
    }

}
