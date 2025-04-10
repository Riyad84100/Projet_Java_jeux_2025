package com.mycompany.test_finale;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author yasmi
 */
public class ImagePanel extends JPanel {
    private int erreurs = 0;
    private Image[] images = new Image[7]; 

    private Image resizeImage(Image originalImage, int width, int height) {
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public ImagePanel() {
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.WHITE);

        // Chargement et redimensionnement des images depuis des fichiers.
        try {
            images[0] = resizeImage(ImageIO.read(new File("src/images/erreur1.png")), 300, 300);
            images[1] = resizeImage(ImageIO.read(new File("src/images/erreur2.png")), 300, 300);
            images[2] = resizeImage(ImageIO.read(new File("src/images/erreur3.png")), 300, 300);
            images[3] = resizeImage(ImageIO.read(new File("src/images/erreur4.png")), 300, 300);
            images[4] = resizeImage(ImageIO.read(new File("src/images/erreur5.png")), 300, 300);
            images[5] = resizeImage(ImageIO.read(new File("src/images/erreur6.png")), 300, 300);
            images[6] = resizeImage(ImageIO.read(new File("src/images/erreur7.png")), 300, 300);
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
        g.drawString("Tu as fait " + erreurs + " erreurs. Attention il te reste " + (6-erreurs) + " chances!", 600, 50);
        // Affiche l'image correspondant au nombre d'erreur
            Image img = images[erreurs];
            if (img != null) {
                g.drawImage(img, 475, 115, 500, 400, this); // Ajustez les positions et dimensions si nécessaire
            }

    }
}
