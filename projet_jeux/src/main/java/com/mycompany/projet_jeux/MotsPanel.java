/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projet_jeux;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.List;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author yasmi
 */
public class MotsPanel extends JPanel {
    private String motSecret;
    private char[] motAffiche;
    private JLabel motLabel;

     
    public MotsPanel() {  

        setLayout(new FlowLayout(FlowLayout.CENTER));
        nouveauMot();
    }

    private String choisirMot() {
        ArrayList<String> mots = new ArrayList<>(Arrays.asList(
            "JAVA", "SWING", "PANEL", "PROGRAMMATION", "ORDINATEUR", "PENDU"
        ));
        return mots.get(new Random().nextInt(mots.size()));
    }

    public void nouveauMot() {
        this.motSecret = choisirMot();
        this.motAffiche = new char[motSecret.length()];
        Arrays.fill(motAffiche, '_');
        if (motLabel == null) {
            motLabel = new JLabel(getMotAffiche());
            add(motLabel);
        } else {
            motLabel.setText(getMotAffiche());
        }
        repaint();
    }

    public boolean devinerLettre(char lettre) {
        boolean trouve = false;
        for (int i = 0; i < motSecret.length(); i++) {
            if (motSecret.charAt(i) == lettre) {
                motAffiche[i] = lettre;
                trouve = true;
            }
        }
        motLabel.setText(getMotAffiche());
        return trouve;
    }

    public boolean isMotDevine() {
        for (char c : motAffiche) {
            if (c == '_') {
                return false; // Si un '_' est présent, le mot n'est pas encore deviné.
            }
        }
        return true; // Aucun '_', donc le mot est deviné.
    }

    private String getMotAffiche() {
        return new String(motAffiche).replace("", " ").trim();
    }

    public String getMotSecret() {
        return motSecret;
    }
    
    
}
