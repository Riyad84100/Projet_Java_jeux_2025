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

        // Affiche l'image correspondant au nombre d'erreurs
        Image img = images[erreurs];
        int textY = 20; // Coordonnée Y par défaut pour le texte
        int textX = 10; // Coordonnée X par défaut pour le texte

        if (img != null) {
            // Calculer les coordonnées pour centrer l'image
            int imgWidth = img.getWidth(this);
            int imgHeight = img.getHeight(this);
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int x = (panelWidth - imgWidth) / 2; // Centrer horizontalement
            int y = (panelHeight - imgHeight) / 2; // Centrer verticalement

            g.drawImage(img, x, y, this); // Dessiner l'image centrée

            // Ajuster la position du texte pour qu'il soit sous l'image
            textY = y + imgHeight + 20; // 20 pixels en dessous de l'image

            // Calculer la largeur du texte pour le centrer
            String message = "Tu as fait " + erreurs + " erreurs. Attention il te reste " + (6 - erreurs) + " chances!";
            FontMetrics metrics = g.getFontMetrics();
            int textWidth = metrics.stringWidth(message);
            textX = (panelWidth - textWidth) / 2; // Centrer horizontalement le texte
        }

        // Dessine le message centré sous l'image
        g.setColor(Color.BLACK);
        g.drawString("Tu as fait " + erreurs + " erreurs. Attention il te reste " + (6 - erreurs) + " chances!", textX, textY);
    }
}
