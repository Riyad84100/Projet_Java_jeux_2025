/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projet_jeux;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author yasmi
 */


public class ImagePanel extends JPanel {
    private int erreurs = 0;
    private Image[] images = new Image[7]; 

    
    public ImagePanel() {

        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.WHITE);

        // Chargement des images depuis des fichiers.
        try {
            images[0] = ImageIO.read(new File("C:\\Users\\yasmi\\Downloads\\erreur1.png")); 
            images[1] = ImageIO.read(new File("C:\\Users\\yasmi\\Downloads\\erreur2.png"));
            images[2] = ImageIO.read(new File("C:\\Users\\yasmi\\Downloads\\erreur3.png"));
            images[3] = ImageIO.read(new File("C:\\Users\\yasmi\\Downloads\\erreur4.png"));
            images[4] = ImageIO.read(new File("C:\\Users\\yasmi\\Downloads\\erreur5.png"));
            images[5] = ImageIO.read(new File("C:\\Users\\yasmi\\Downloads\\erreur6.png"));
            images[6] = ImageIO.read(new File("C:\\Users\\yasmi\\Downloads\\erreur7.png"));
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des images : " + e.getMessage());
        }
    }

    public void incrementerErreurs() {
        if (erreurs < images.length) {
            erreurs++;
            repaint();
        }

    }
    public void resetErreurs() {
        erreurs = 0;
        revalidate();
        repaint();
    }

    public int getErreurs() {
        return erreurs;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessine le nombre d'erreurs (texte pour le débogage, optionnel)
        g.setColor(Color.BLACK);
        g.drawString("Tu as fait " + erreurs + " erreurs. Attention il te reste " + (6-erreurs) + " chances!", 600, 600);
        // Affiche l'image correspondant au nombre d'erreur
            Image img = images[erreurs];
            if (img != null) {
                g.drawImage(img, 475, 115, 500, 400, this); // Ajustez les positions et dimensions si nécessaire
            }

    }
}

