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
import java.io.BufferedReader;
import java.io.FileReader;
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

    private ArrayList<String> motsList = new ArrayList<>(); // Liste des mots chargés depuis le fichier.

    public MotsPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        chargerMotsDepuisFichier("C:\\Users\\yasmi\\Downloads\\mots.txt"); // Charger les mots depuis un fichier texte
        nouveauMot(); // Démarrer avec un mot
    }

    // Charger les mots depuis un fichier texte
    private void chargerMotsDepuisFichier(String cheminFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                motsList.add(ligne.trim()); // Ajouter chaque mot (trim pour enlever les espaces)
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier des mots : " + e.getMessage());
        }
    }

    // Choisir un mot aléatoire depuis la liste chargée
    private String choisirMot() {
        if (motsList.isEmpty()) {
            System.err.println("La liste des mots est vide !");
            return "";
        }
        return motsList.get(new Random().nextInt(motsList.size()));
    }

    public void nouveauMot() {
        this.motSecret = choisirMot();
        this.motAffiche = new char[motSecret.length()];
        Arrays.fill(motAffiche, '_');
        if (motLabel == null) {
            motLabel = new JLabel(getMotAffiche());
            motLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Agrandir la police du texte
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
