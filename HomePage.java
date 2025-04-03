/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.test;

/**
 *
 * @author yasmi
 */
import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ImagePanel imagePanel;
    private LettersPanel lettersPanel;
    private MotsPanel motsPanel;
    private DefaultListModel<String> listModel;
    private JList<String> wordList;
    private JPanel motDePassePanel;
    private String difficulty;
    private Tableau tableau;
    
    public HomePage() {
        setTitle("Application éducative");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Ajouter les différentes sections
        cardPanel.add(new HomeMenuPanel(this), "Home");
        cardPanel.add(createPenduPanel(), "Pendu");
        cardPanel.add(createAdminPanel(), "Admin");
        cardPanel.add(createAdminLoginPanel(), "AdminLogin");
        cardPanel.add(createColoriagePanel(), "Coloriage");
        cardPanel.add(createCalculPanel(), "Calcul");

        // Configuration de la fenêtre
        this.add(cardPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Assure que l'application se ferme bien
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        pack();  // Ajuste la taille pour s’adapter aux composants
        this.setLocationRelativeTo(null); // Centre la fenêtre
        this.setVisible(true);
        }
    public void verifier() {
        if (motsPanel.isMotDevine()) {
            JOptionPane.showMessageDialog(this, "Bravo ! Tu as gagné !");
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
    }
    
    private JPanel createPenduPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        imagePanel = new ImagePanel();
        motsPanel = new MotsPanel();
        lettersPanel = new LettersPanel(50, motsPanel, imagePanel);
        verifier();
        panel.add(imagePanel, BorderLayout.CENTER);
        panel.add(motsPanel, BorderLayout.NORTH);
        panel.add(lettersPanel, BorderLayout.PAGE_END);
        return panel;
    }
    
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        wordList = new JList<>(listModel);
        panel.add(new JScrollPane(wordList), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createAdminLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        motDePassePanel = new JPanel();
        panel.add(motDePassePanel);
        return panel;
    }
    
    private JPanel createColoriagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tableau = new Tableau();
        panel.add(tableau, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createCalculPanel() {
        return new JPanel();
    }
    
    public void showPage(String pageName) {
        cardLayout.show(cardPanel, pageName);
    }
    
    public void showHomePage() {
        showPage("Home");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
    }
}

