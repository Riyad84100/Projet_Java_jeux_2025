/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.admin;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class AdminFrame extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> wordList;
    private ArrayList<String> words;
    private final String MOTS_PATH = "C:\\Users\\yasmi\\Downloads\\mots.txt";

    public AdminFrame() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Administration - Gestion de la Liste de Mots");

        listModel = new DefaultListModel<>();
        wordList = new JList<>(listModel);
        words = new ArrayList<>();

        loadDictionary();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(wordList), BorderLayout.CENTER);

        AjouterPanel ajouter = new AjouterPanel(words, listModel);
        ModifierPanel modifier = new ModifierPanel(words, listModel);
        EnregistrerPanel enregistrer = new EnregistrerPanel(words);
        SupprimerPanel supprimer = new SupprimerPanel(words, listModel); // Passer le modèle de liste

        JPanel panelBas = new JPanel(new GridLayout(1, 3));
        panelBas.add(ajouter);
        panelBas.add(modifier);
        panelBas.add(supprimer);
        panelBas.add(enregistrer);
        mainPanel.add(panelBas, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MOTS_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
                listModel.addElement(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la liste de mots.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
