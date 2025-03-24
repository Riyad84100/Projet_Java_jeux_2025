/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject3;

import javax.swing.JFrame;

/**
 *
 * @author riyad
 */
public class Mavenproject3 {

  public static void main(String[] args) {
        JFrame fenêtre = new JFrame("Activité de Calcul");
        fenêtre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenêtre.setSize(600, 300); // Taille initiale de la fenêtre

        // Activer le mode plein écran
        fenêtre.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Mettre à true pour le niveau facile, false pour le niveau difficile
        ActiviteCalcul activitéCalcul = new ActiviteCalcul(false);
        fenêtre.add(activitéCalcul);

        fenêtre.setVisible(true);
    }
}