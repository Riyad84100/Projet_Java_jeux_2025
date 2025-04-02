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
    private String selectedGame = null; // Stocke le jeu sélectionné
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
        JMenuItem calculGameMenuItem = new JMenuItem("Jeu de calcul");
        JMenuItem penduMenuItem = new JMenuItem("Jeux du pendu");

        // Gestion des clics sur les options de jeux
        magicSlateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedGame = "Jeux ardoise magique";
                checkAndLaunchGame();
            }
        });

        calculGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedGame = "Jeu de calcul";
                checkAndLaunchGame();
            }
        });

        penduMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedGame = "Jeu du pendu";
                setVisible(false); // Hide the HomePage
                new PenduFrame(HomePage.this).setVisible(true); // Pass the HomePage instance to PenduFrame
            }
        });

        // Ajouter les options au menu des jeux
        gamesMenu.add(magicSlateMenuItem);
        gamesMenu.add(calculGameMenuItem);
        gamesMenu.add(penduMenuItem);

        // Menu pour le mode administrateur
        JMenu adminMenu = new JMenu("Admin");
        adminMenu.setFont(new Font("Arial", Font.BOLD, 16));

        // Option pour ouvrir le mode admin
        JMenuItem adminLoginMenuItem = new JMenuItem("Mode Administrateur");
        adminLoginMenuItem.addActionListener(e -> {
            setVisible(false); // Hide the HomePage
            AdminLoginFrame adminLoginFrame = new AdminLoginFrame(this); // Pass the HomePage instance to AdminLoginFrame
            adminLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application exits when admin closes
        });

        // Ajouter l'option au menu admin
        adminMenu.add(adminLoginMenuItem);

        // Ajouter les menus à la barre de menus
        menuBar.add(difficultyMenu);
        menuBar.add(gamesMenu);
        menuBar.add(adminMenu);

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
     * Vérifie les conditions et lance le jeu sélectionné
     */
    private void checkAndLaunchGame() {
        if ("Jeu du pendu".equals(selectedGame)) {
            // Allow launching "Jeu du pendu" without difficulty selection
            launchGame(null);
        } else if (selectedDifficulty == null) {
            JOptionPane.showMessageDialog(HomePage.this, "Veuillez choisir une difficulté !", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            launchGame(selectedDifficulty);
        }
    }

    /**
     * Initialise JavaFX pour lire la vidéo en arrière-plan.
     *
     * @param fxPanel Le JFXPanel où la vidéo sera affichée.
     */
    private void initFX(JFXPanel fxPanel) {
        // Chemin de la vidéo
        String videoPath = new File("C:\\Users\\LORDI\\OneDrive\\Documents\\essai_projet2\\essai_projet\\src\\video\\puss.mp4").toURI().toString();

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

        // Créer et afficher la fenêtre de jeu appropriée
        if ("Jeux ardoise magique".equals(selectedGame)) {
            DisplayPanel gameFrame = new DisplayPanel(difficulty, this); // Passer la référence de HomePage
            gameFrame.setVisible(true);
        } else if ("Jeu de calcul".equals(selectedGame)) {
            ActiviteCalcul gameFrame = new ActiviteCalcul(difficulty, this); // Passer la référence de HomePage
            gameFrame.setVisible(true);
        } else if ("Jeu du pendu".equals(selectedGame)) {
            PenduFrame gameFrame = new PenduFrame(this); // Créer une nouvelle instance du jeu du pendu
            gameFrame.setVisible(true);
        }
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