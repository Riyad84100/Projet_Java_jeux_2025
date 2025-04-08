package com.mycompany.test_finale;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author yasmi
 */
public class ModifierPanel extends JPanel{
    private JTextField oldWordField;
    private JTextField newWordField;
    private JButton modifyButton;
    private ArrayList<String> words;
    private DefaultListModel<String> listModel;

    @SuppressWarnings("unused")
    public ModifierPanel(ArrayList<String> words, DefaultListModel<String> listModel) {
        this.words = words;
        this.listModel = listModel;

        setLayout(new FlowLayout());
        oldWordField = new JTextField(5);
        newWordField = new JTextField(5);
        
        modifyButton = new JButton("Modifier");
        modifyButton.setBackground(new Color(59, 89, 182)); // Couleur de fond
        modifyButton.setForeground(Color.WHITE); // Couleur du texte 
        
        add(new JLabel("Ancien :"));
        add(oldWordField);
        add(new JLabel("Nouveau :"));
        add(newWordField);
        add(modifyButton);

        modifyButton.addActionListener(e -> modifyWord());
    }

    private void modifyWord() {
        String oldWord = oldWordField.getText().trim();
        String newWord = newWordField.getText().trim();

        if (words.contains(oldWord) && !newWord.isEmpty() && !words.contains(newWord)) {
            words.remove(oldWord);
            words.add(newWord);
            listModel.removeElement(oldWord);
            listModel.addElement(newWord);
            oldWordField.setText("");
            newWordField.setText("");
            JOptionPane.showMessageDialog(this, "Mot modifié avec succès !");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
