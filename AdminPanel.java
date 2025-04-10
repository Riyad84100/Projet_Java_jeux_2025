package com.mycompany.test_finale;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author yasmi
 */

public class AdminPanel extends JPanel {
    private DefaultListModel<String> listModel;
    private JList<String> wordList;
    private ArrayList<String> words;
    private static final String MOTS_PATH = "src/mot/mots.txt"; // Chemin relatif

    public AdminPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        
        listModel = new DefaultListModel<>();
        wordList = new JList<>(listModel);
        words = new ArrayList<>();

        loadDictionary();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(wordList), BorderLayout.CENTER);

        JPanel panelBas = new JPanel(new GridLayout(1, 4));
        panelBas.add(new AjouterPanel(words, listModel));
        panelBas.add(new ModifierPanel(words, listModel));
        panelBas.add(new SupprimerPanel(words, listModel));
        panelBas.add(new EnregistrerPanel(words));
        
        mainPanel.add(panelBas, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MOTS_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
                listModel.addElement(line);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Fichier introuvable : " + MOTS_PATH, "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la liste de mots : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}