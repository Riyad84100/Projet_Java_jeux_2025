/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projet_jeux;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.lang.Runnable;

/**
 *
 * @author yasmi
 */
public class PenduFrame extends JFrame {
    private int caseSize = 50; // Taille des boutons
    private ImagePanel imagePanel;
    private LettersPanel lettersPanel;
    private MotsPanel motsPanel;

    public PenduFrame() {
        initGui();
    }

    private void initGui() {
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
        
        // Configuration de la fenêtre
        this.add(root);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }


    public void verifier() {
        if (motsPanel.isMotDevine()) {
            JOptionPane.showMessageDialog(this, "Bravo ! Vous avez gagné !");
            resetJeu();
        }if (imagePanel.getErreurs() >= 6) {
            JOptionPane.showMessageDialog(this, "Perdu ! Le mot était: " +  motsPanel.getMotSecret());
            resetJeu();
        }
    }

    private void resetJeu() {
        imagePanel.resetErreurs();
        motsPanel.nouveauMot();
        lettersPanel.resetBoutons();

        //this.revalidate();
        //this.repaint();
    }


}
