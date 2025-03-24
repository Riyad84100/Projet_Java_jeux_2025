import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HomePage extends JFrame {
    private String selectedDifficulty = null; // Stocke la difficulté sélectionnée
    private MediaView mediaView; // Référence au MediaView pour le redimensionnement
    private JFXPanel fxPanel; // Référence au JFXPanel pour le redimensionnement
    private MediaPlayer mediaPlayer; // Référence au MediaPlayer pour relancer la vidéo

    public HomePage() {
        setTitle("Page d'accueil - Jeux");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiser la fenêtre

        // Initialiser JavaFX
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER); // Ajouter le JFXPanel au centre pour qu'il prenne tout l'espace

        // Charger et lire la vidéo en arrière-plan
        Platform.runLater(() -> initFX(fxPanel));

        // Créer la barre de menus
        JMenuBar menuBar = new JMenuBar();

        // Menu pour choisir la difficulté
        JMenu difficultyMenu = new JMenu("Difficulté");
        difficultyMenu.setFont(new Font("Arial", Font.BOLD, 16));

        // Options de difficulté
        JRadioButtonMenuItem easyMenuItem = new JRadioButtonMenuItem("Facile");
        JRadioButtonMenuItem hardMenuItem = new JRadioButtonMenuItem("Difficile");

        // Grouper les options de difficulté
        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyMenuItem);
        difficultyGroup.add(hardMenuItem);

        // Gestion des clics sur les options de difficulté
        easyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDifficulty = "facile";
            }
        });

        hardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDifficulty = "difficile";
            }
        });

        // Ajouter les options au menu de difficulté
        difficultyMenu.add(easyMenuItem);
        difficultyMenu.add(hardMenuItem);

        // Menu pour choisir un jeu
        JMenu gamesMenu = new JMenu("Jeux");
        gamesMenu.setFont(new Font("Arial", Font.BOLD, 16));

        // Options de jeux
        JMenuItem magicSlateMenuItem = new JMenuItem("Jeux ardoise magique");
        JMenuItem game1MenuItem = new JMenuItem("Jeu de Calcul");
        JMenuItem game2MenuItem = new JMenuItem("Jeu 2 (à venir)");

        // Gestion des clics sur les options de jeux
        magicSlateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedDifficulty == null) {
                    JOptionPane.showMessageDialog(HomePage.this, "Veuillez choisir une difficulté !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Lancer le jeu avec la difficulté sélectionnée
                    launchGame(selectedDifficulty);
                }
            }
        });

        game1MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomePage.this, "Jeu 1 - En développement", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        game2MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomePage.this, "Jeu 2 - En développement", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Ajouter les options au menu des jeux
        gamesMenu.add(magicSlateMenuItem);
        gamesMenu.add(game1MenuItem);
        gamesMenu.add(game2MenuItem);

        // Ajouter les menus à la barre de menus
        menuBar.add(difficultyMenu);
        menuBar.add(gamesMenu);

        // Ajouter la barre de menus à la fenêtre
        setJMenuBar(menuBar);

        // Centrer la fenêtre
        setLocationRelativeTo(null);

        // Redimensionner la vidéo lorsque la fenêtre est redimensionnée
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                Platform.runLater(() -> {
                    if (mediaView != null) {
                        mediaView.setFitWidth(fxPanel.getWidth());
                        mediaView.setFitHeight(fxPanel.getHeight());
                    }
                });
            }
        });
    }

    /**
     * Initialise JavaFX pour lire la vidéo en arrière-plan.
     *
     * @param fxPanel Le JFXPanel où la vidéo sera affichée.
     */
    private void initFX(JFXPanel fxPanel) {
        // Chemin de la vidéo
        String videoPath = new File("src/video/puss2.mp4").toURI().toString();

        // Créer un Media et un MediaPlayer
        Media media = new Media(videoPath);
        mediaPlayer = new MediaPlayer(media);

        // Créer un MediaView pour afficher la vidéo
        mediaView = new MediaView(mediaPlayer);

        // Redimensionner la vidéo pour remplir tout l'espace disponible
        mediaView.setPreserveRatio(false); // Permettre à la vidéo de s'étirer
        mediaView.setFitWidth(fxPanel.getWidth());
        mediaView.setFitHeight(fxPanel.getHeight());

        // Créer une scène JavaFX
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root);

        // Ajouter la scène au JFXPanel
        fxPanel.setScene(scene);

        // Lire la vidéo en boucle
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    /**
     * Lance le jeu avec la difficulté sélectionnée.
     *
     * @param difficulty La difficulté choisie ("facile" ou "difficile").
     */
    private void launchGame(String difficulty) {
        // Masquer la page d'accueil au lieu de la fermer
        setVisible(false);

        // Créer et afficher la fenêtre de jeu
        DisplayPanel gameFrame = new DisplayPanel(difficulty, this); // Passer la référence de HomePage
        gameFrame.setVisible(true);
    }

    /**
     * Réaffiche la page d'accueil.
     */
    public void showHomePage() {
        setVisible(true); // Réafficher la page d'accueil
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
        });
    }
}