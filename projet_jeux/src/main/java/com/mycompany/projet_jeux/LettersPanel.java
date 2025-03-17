/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projet_jeux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
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
                button.setBackground(new Color(15, 180, 110));
                button.setOpaque(true);
                button.setBorderPainted(false);

                // Action sur clic
                button.addActionListener(e -> {
                    boolean correct = motsPanel.devinerLettre(letter);
                    button.setEnabled(false);
                    if (!correct) {
                    imagePanel.incrementerErreurs();
                    }
                });

                linePanel.add(button); // Ajouter le bouton à la ligne
            }

            this.add(linePanel); // Ajouter la ligne au panneau principal
        }

        // Fixer une hauteur minimale pour éviter que le panneau s'étire trop
        this.setPreferredSize(new Dimension(600, 200));
    }
}
