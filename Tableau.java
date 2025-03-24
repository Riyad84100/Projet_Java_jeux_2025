import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

class Tableau extends JPanel {
    private Color currentColor = Color.BLACK;
    private int strokeWidth = 5; // Largeur par défaut du stylo
    private final List<Shape> shapes = new ArrayList<>();
    private Point lastPoint = null;

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

    public List<Shape> getShapes() {
        return shapes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
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