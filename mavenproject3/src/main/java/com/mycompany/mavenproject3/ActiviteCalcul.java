/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author riyad
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ActiviteCalcul extends JPanel {
    private JLabel labelCalcul;
    private JTextField champRéponse;
    private JButton boutonVérifier;
    private JButton boutonSolution;
    private JButton boutonNouveau;
    private JLabel labelRésultat;
    private int nombre1, nombre2, résultat;
    private String opérateur;
    private boolean niveauFacile;

    public ActiviteCalcul(boolean niveauFacile) {
        this.niveauFacile = niveauFacile;
        setLayout(new BorderLayout(10, 10)); // Espacement 

        // Panel pour le calcul et la réponse
        JPanel panelHaut = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // marge et  disposition autour du calcul et reponse 

        labelCalcul = new JLabel();
        labelCalcul.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); // taille et police de calcul
        panelHaut.add(labelCalcul);

        champRéponse = new JTextField(15);
        champRéponse.setFont(new Font("Comic Sans MS", Font.PLAIN, 20)); // taille et police pour le champs de réponse
        panelHaut.add(champRéponse);

        add(panelHaut, BorderLayout.CENTER);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // marge et  disposition autour des composants

        boutonVérifier = new JButton("Vérifier");
        boutonVérifier.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); // taille et police du jbutton "verifier"
        panelBoutons.add(boutonVérifier);

        boutonSolution = new JButton("Solution");
        boutonSolution.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); // taille et police du jbutton "solution"
        panelBoutons.add(boutonSolution);

        boutonNouveau = new JButton("Nouveau");
        boutonNouveau.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); // taille et police du jbutton "nouveau"
        panelBoutons.add(boutonNouveau);

        add(panelBoutons, BorderLayout.SOUTH);

        // Panel pour le résultat
        labelRésultat = new JLabel("", SwingConstants.CENTER);
        labelRésultat.setFont(new Font("Comic Sans MS", Font.BOLD, 20)); // taille et poilce du resultat
        labelRésultat.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge autour du texte
        add(labelRésultat, BorderLayout.NORTH);

        // Générer le premier calcul
        générerNouveauCalcul();

        // Ajouter les écouteurs d'événements
        boutonVérifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vérifierRéponse();
            }
        });

        boutonSolution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherSolution();
            }
        });

        boutonNouveau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                générerNouveauCalcul();
            }
        });
    }

    private void générerNouveauCalcul() {
        Random random = new Random();
        if (niveauFacile) {
            nombre1 = random.nextInt(10);
            nombre2 = random.nextInt(10);
            opérateur = random.nextBoolean() ? "+" : "-";
            résultat = opérateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            // S'assurer que le résultat est positif
            while (résultat < 0) {
                nombre1 = random.nextInt(10);
                nombre2 = random.nextInt(10);
                résultat = opérateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            }
        } else {
            // Niveau difficile : addition, soustraction ou multiplication
            int choixOpération = random.nextInt(3); // 0: addition, 1: soustraction, 2: multiplication
            if (choixOpération == 0 || choixOpération == 1) {
                nombre1 = random.nextInt(1000);
                nombre2 = random.nextInt(1000);
                opérateur = (choixOpération == 0) ? "+" : "-";
                résultat = opérateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            } else {
                // Multiplication de nombres à 1 chiffre
                nombre1 = random.nextInt(10);
                nombre2 = random.nextInt(10);
                opérateur = "x";
                résultat = nombre1 * nombre2;
            }
        }
        labelCalcul.setText(nombre1 + " " + opérateur + " " + nombre2 + " = ?");
        champRéponse.setText("");
        labelRésultat.setText("");
        champRéponse.requestFocus();
    }

    private void vérifierRéponse() {
        String réponse = champRéponse.getText().trim();
        if (réponse.isEmpty()) {
            labelRésultat.setText("Essaye de répondre quand même...");
            return;
        }

        try {
            int réponseUtilisateur = Integer.parseInt(réponse);
            if (réponseUtilisateur == résultat) {
                labelRésultat.setText("BRAVOOO ! Tu es aussi fort qu'un ogre !");
            } else {
                labelRésultat.setText("Tu y es presque ! Essaie encore, tu vas y arriver !");
            }
        } catch (NumberFormatException e) {
            labelRésultat.setText("Oh là là ! Tu as sûrement fait une petite erreur. Essaie avec un nombre cette fois, tu vas y arriver ! ");
        }
    }
   
    private void afficherSolution() {
        labelRésultat.setText("La solution est " + résultat);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Activité Calcul");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300); // Taille de la fenêtre ajustée
        frame.add(new ActiviteCalcul(true));
        frame.setVisible(true);
    }
}