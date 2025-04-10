package com.mycompany.test_finale;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author yasmi
 */
public class SupprimerPanel extends JPanel {
    private JList<String> wordList;
    private JButton deleteButton;
    private ArrayList<String> words;
    private DefaultListModel<String> listModel; // Ajout du modèle de liste
    private final String DICTIONARY_PATH = "src/mot/mots.txt";

    @SuppressWarnings("unused")
    public SupprimerPanel(ArrayList<String> words, DefaultListModel<String> listModel) {
        this.words = words;
        this.listModel = listModel; // Initialisation du modèle de liste

        setLayout(new BorderLayout(0, 10));

        wordList = new JList<>(words.toArray(new String[0]));
        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(wordList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Supprimer le mot sélectionné");
        deleteButton.setBackground(new Color(59, 89, 182));
        deleteButton.setForeground(Color.WHITE);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        deleteButton.addActionListener(e -> deleteSelectedWord());
    }

    private void deleteSelectedWord() {
        String selectedWord = wordList.getSelectedValue();
        if (selectedWord == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un mot à supprimer.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        words.remove(selectedWord);
        listModel.removeElement(selectedWord); // Mettre à jour le modèle de liste

        wordList.setListData(words.toArray(new String[0]));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DICTIONARY_PATH))) {
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Mot supprimé avec succès!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du mot.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
