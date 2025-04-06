import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

class Tableau extends JPanel {
    private Color currentColor = Color.BLACK;
    private int strokeWidth = 5; // Largeur par défaut du stylo
    private final List<Shape> shapes = new ArrayList<>();
    private Point lastPoint = null;
    private BufferedImage backgroundImage = null; // Add a field for the background image
    private String currentFileName = null; // Variable pour stocker le nom du fichier chargé

    public Tableau() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastPoint = null; // Reset lastPoint when mouse is released
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastPoint != null) {
                    shapes.add(new Shape(lastPoint, e.getPoint(), currentColor, strokeWidth));
                    lastPoint = e.getPoint();
                    repaint();
                }
            }
        });
    }

    public void setColor(Color color) {
        currentColor = color;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void reset() {
        shapes.clear();
        repaint();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void saveImage() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        paint(g2d); // Paint the current content of the panel onto the BufferedImage
        g2d.dispose();

        File outputDir = new File("src/dessin");
        if (!outputDir.exists()) {
            outputDir.mkdirs(); // Create the directory if it doesn't exist
        }

        File outputFile;
        if (currentFileName != null) {
            // Utiliser le nom de fichier existant
            outputFile = new File(outputDir, currentFileName);
        } else {
            // Trouver un nouveau nom de fichier si aucun fichier n'est chargé
            int counter = 1;
            do {
                outputFile = new File(outputDir, "drawing_" + counter + ".png");
                counter++;
            } while (outputFile.exists());
            currentFileName = outputFile.getName(); // Mettre à jour le nom du fichier actuel
        }

        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save the image.");
        }
    }

    public void loadImage(String filename) {
        File inputFile = new File("src/dessin/" + filename);
        if (!inputFile.exists()) {
            System.err.println("File not found: " + inputFile.getAbsolutePath());
            return;
        }

        try {
            backgroundImage = ImageIO.read(inputFile); // Charger l'image en fond
            currentFileName = filename; // Mettre à jour le nom du fichier actuel
            repaint(); // Repaint the panel to display the background
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load the image.");
        }
    }

    @SuppressWarnings("unused")
    public String[] listSavedDrawings() {
        File outputDir = new File("src/dessin");
        if (!outputDir.exists() || !outputDir.isDirectory()) {
            return new String[0]; // Return an empty array if the directory doesn't exist
        }

        // List all PNG files in the directory
        return outputDir.list((dir, name) -> name.endsWith(".png"));
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the background image if it exists
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // Draw the shapes on top of the background
        for (Shape s : shapes) {
            g2d.setColor(s.color);
            g2d.setStroke(new BasicStroke(s.strokeWidth)); // Utiliser la largeur du stylo
            g2d.drawLine(s.startPoint.x, s.startPoint.y, s.endPoint.x, s.endPoint.y);
        }
    }

    private static class Shape {
        Point startPoint;
        Point endPoint;
        Color color;
        int strokeWidth;

        Shape(Point startPoint, Point endPoint, Color color, int strokeWidth) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.color = color;
            this.strokeWidth = strokeWidth;
        }
    }

    public Color getColor() {
        return currentColor;
    }
}