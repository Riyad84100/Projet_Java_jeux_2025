/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.admin;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author yasmi
 */
public class AjouterPanel extends JPanel {
    private JTextField textField;
    private JButton addButton;
    private ArrayList<String> words;
    private DefaultListModel<String> listModel;

    public AjouterPanel(ArrayList<String> words, DefaultListModel<String> listModel) {
        this.words = words;
        this.listModel = listModel;

        this.setLayout(new FlowLayout());
        textField = new JTextField(10);
        
        addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(59, 89, 182)); // Couleur de fond
        addButton.setForeground(Color.WHITE); // Couleur du texte 
        
        this.add(new JLabel("Ajouter mot:"));
        this.add(textField);
        this.add(addButton);

        addButton.addActionListener(e -> addWord());
    }

    private void addWord() {
        String newWord = textField.getText().trim().toUpperCase();        
        if (!newWord.isEmpty() && !words.contains(newWord) && newWord.chars().allMatch(Character::isLetter)) {
            words.add(newWord);
            listModel.addElement(newWord);
            textField.setText("");
            JOptionPane.showMessageDialog(this, "Mot ajouté avec succès !");
        } else {
            JOptionPane.showMessageDialog(this, "Le mot est vide, déjà présent ou n'est pas un mot.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

