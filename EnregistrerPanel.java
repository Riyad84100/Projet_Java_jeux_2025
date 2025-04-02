/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.admin;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author yasmi
 */
public class EnregistrerPanel extends JPanel{
    private JButton saveButton;
    private ArrayList<String> words;
    private final String DICTIONARY_PATH = "C:\\Users\\yasmi\\Downloads\\mots.txt";

    public EnregistrerPanel(ArrayList<String> words) {
        this.words = words;

        setLayout(new FlowLayout());
        saveButton = new JButton("Enregistrer");
        saveButton.setBackground(new Color(59, 89, 182)); // Couleur de fond
        saveButton.setForeground(Color.WHITE); // Couleur du texte 
        add(saveButton);

        saveButton.addActionListener(e -> saveDictionary());
    }

    private void saveDictionary() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DICTIONARY_PATH))) {
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Dictionnaire enregistré avec succès !");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
