import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BoutonBarBis extends JPanel {
    @SuppressWarnings("unused")
    private Tableau tableau;
    @SuppressWarnings("unused")
    private String difficulty;

    @SuppressWarnings("unused")
    public BoutonBarBis(Tableau tableau, String difficulty) {
        this.tableau = tableau;
        this.difficulty = difficulty;
        setLayout(new FlowLayout());
        setOpaque(false); // Rendre le panel transparent

        // Créer les boutons
        JButton greenButton = new JButton();
        JButton redButton = new JButton();
        JButton blueButton = new JButton();
        JButton rgbButton = new JButton("RGB Color");
        JButton eraserButton = new JButton();
        JButton resetButton = new JButton();
        JButton sizeButton = new JButton(); // Bouton pour régler la largeur du stylo

        // Charger les images pour les boutons
        loadButtonIcon(greenButton, "src/images/vert.png", 60, 60);
        loadButtonIcon(redButton, "src/images/rouge.png", 60, 60);
        loadButtonIcon(blueButton, "src/images/bleu.png", 60, 60);
        loadButtonIcon(eraserButton, "src/images/eraser.png", 60, 60);
        loadButtonIcon(resetButton, "src/images/bin.png", 60, 60);
        loadButtonIcon(rgbButton, "src/images/ciel.png", 60, 60);
        loadButtonIcon(sizeButton, "src/images/stilet.png", 60, 60); // Charger l'image pour le bouton de taille

        // Ajouter les écouteurs d'événements
        greenButton.addActionListener(e -> tableau.setColor(Color.GREEN));
        redButton.addActionListener(e -> tableau.setColor(Color.RED));
        blueButton.addActionListener(e -> tableau.setColor(Color.BLUE));
        eraserButton.addActionListener(e -> tableau.setColor(Color.WHITE));
        resetButton.addActionListener(e -> tableau.reset());

        // Configurer le bouton RGB Color pour ouvrir un JColorChooser
        rgbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.Color selectedAwtColor = JColorChooser.showDialog(
                    BoutonBarBis.this, "Choisir une couleur", tableau.getColor()
                );
                if (selectedAwtColor != null) {
                    tableau.setColor(selectedAwtColor);
                }
            }
        });

        // Configurer le bouton de taille pour afficher un slider
        sizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSizeSlider(sizeButton);
            }
        });

        // Afficher les boutons en fonction de la difficulté
        if (difficulty.equals("facile")) {
            add(greenButton);
            add(redButton);
            add(blueButton);
        } else if (difficulty.equals("difficile")) {
            add(rgbButton);
        }

        // Ajouter la gomme, le reset et le bouton de taille dans tous les cas
        add(eraserButton);
        add(resetButton);
        add(sizeButton);
    }

    /**
     * Charge une image et l'applique à un bouton.
     *
     * @param button      Le bouton auquel appliquer l'image.
     * @param imagePath   Le chemin de l'image.
     * @param width       La largeur souhaitée.
     * @param height      La hauteur souhaitée.
     */
    private void loadButtonIcon(JButton button, String imagePath, int width, int height) {
        try {
            // Charger l'image
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image originalImage = originalIcon.getImage();

            // Redimensionner l'image
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Appliquer l'image redimensionnée au bouton
            button.setIcon(new ImageIcon(resizedImage));
            button.setPreferredSize(new Dimension(width, height));
            button.setContentAreaFilled(false); // Rendre le fond du bouton transparent
            button.setBorderPainted(false);     // Supprimer la bordure du bouton
            button.setFocusPainted(false);      // Supprimer l'effet de focus
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            button.setText("Image manquante"); // Texte de secours si l'image ne charge pas
        }
    }

    /**
     * Affiche un slider pour régler la largeur du stylo.
     *
     * @param parentButton Le bouton qui a déclenché l'affichage du slider.
     */
    private void showSizeSlider(JButton parentButton) {
        // Créer un slider pour régler la largeur du stylo
        JSlider sizeSlider = new JSlider(1, 20, tableau.getStrokeWidth());
        sizeSlider.setMajorTickSpacing(5);
        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);

        // Ajouter un écouteur pour mettre à jour la largeur du stylo
        sizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int newStrokeWidth = sizeSlider.getValue();
                tableau.setStrokeWidth(newStrokeWidth); // Mettre à jour la largeur du stylo
            }
        });

        // Créer un popup pour afficher le slider
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(sizeSlider);

        // Afficher le popup au-dessus du bouton
        popupMenu.show(parentButton, 0, -sizeSlider.getPreferredSize().height);
    }
}