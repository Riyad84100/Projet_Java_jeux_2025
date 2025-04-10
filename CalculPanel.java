package com.mycompany.test_finale;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

//Riyad KINNOUS


// Déclaration de la classe qui représente un panneau de calcul
public class CalculPanel extends JPanel {
    // Déclaration des variables utilisées dans toute la classe
    private String difficulty; // Stocke le niveau de difficulté choisi (facile ou difficile)
    private JLabel labelCalcul; 
    private JTextField champReponse; 
    private JButton boutonVerifier; 
    private JButton boutonSolution; 
    private JButton boutonNouveau; 
    private JLabel labelResultat; 
    private int nombre1, nombre2, resultat; 
    private String operateur;

    // Constructeur de la classe qui prend en paramètre la difficulté
    public CalculPanel(String difficulty) {
        this.difficulty = difficulty; // On mémorise la difficulté
        initUI(); // On initialise l'interface graphique
    }

    // Permet de changer la difficulté 
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        genererNouveauCalcul(); // On génère un calcul adapté à cette nouvelle difficulté
        revalidate(); 
        repaint(); 
    }

    // Méthode privée pour  l'interface graphique
    private void initUI() {
        setLayout(new BorderLayout()); // zones (NORTH, SOUTH...)

        // on créée un panel personnalisé avec une image de fond
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/marais.png");
        backgroundPanel.setLayout(new BorderLayout(10, 10)); // Layout pour placer les éléments dans le panel
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Marge intérieure autour 

        // Création du panel en haut avec le calcul et le champ de réponse
        JPanel panelHaut = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelHaut.setOpaque(false); // Pour laisser l'image de fond visible

        labelCalcul = new JLabel(); // Label vide qu'on remplira avec un calcul
        labelCalcul.setForeground(Color.WHITE); 
        labelCalcul.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); 

        champReponse = new JTextField(15); // Champ de texte pour écrire la réponse
        champReponse.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        panelHaut.add(labelCalcul); // On ajoute le label au panel du haut
        panelHaut.add(champReponse); // On ajoute aussi le champ de réponse

        // Création du pannel pour les 3 boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBoutons.setOpaque(false); // Laisser visible le fond aussi ici

        // Initialisation des boutons
        boutonVerifier = new JButton();
        boutonSolution = new JButton();
        boutonNouveau = new JButton();

        // Chargement des images sur les boutons avec taille définie
        loadButtonIcon(boutonVerifier, "src/images/check.png", 60, 60);
        loadButtonIcon(boutonSolution, "src/images/lampe.png", 60, 60);
        loadButtonIcon(boutonNouveau, "src/images/plus.png", 60, 60);

        // Ajout des boutons dans le pannel
        panelBoutons.add(boutonVerifier);
        panelBoutons.add(boutonSolution);
        panelBoutons.add(boutonNouveau);

        // Label pour afficher le message de résultat
        labelResultat = new JLabel("", SwingConstants.CENTER); // Vide au départ
        labelResultat.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        labelResultat.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // On place les 3 panels dans le panel de fond (nord, centre, sud)
        backgroundPanel.add(panelHaut, BorderLayout.NORTH);
        backgroundPanel.add(panelBoutons, BorderLayout.SOUTH);
        backgroundPanel.add(labelResultat, BorderLayout.CENTER);

        // Enfin, on ajoute ce panel de fond dans notre panneau principal
        add(backgroundPanel);

        // On affiche directement un calcul dès le départ
        genererNouveauCalcul();

        // Gestion des clics sur les boutons
        boutonVerifier.addActionListener(e -> verifierReponse());
        boutonSolution.addActionListener(e -> afficherSolution());
        boutonNouveau.addActionListener(e -> genererNouveauCalcul());
    }

    // Cette méthode charge une image pour un bouton et ajuste sa taille
    private void loadButtonIcon(JButton button, String imagePath, int width, int height) {
        try {
            Image originalImage = ImageIO.read(new File(imagePath)); // la lecture du fichier image
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH); // pour redimensionner l'image
            button.setIcon(new ImageIcon(resizedImage)); // on applique  l'image redimensionnée
            button.setPreferredSize(new Dimension(width, height)); // on définit  la taille du bouton
            button.setContentAreaFilled(false); // pour supprimer le fond du bouton (transparent)
            button.setBorderPainted(false); // pour ne  pas dessiner de bordure
            button.setFocusPainted(false); // pour ne pas dessiner le contour quand on clique
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            button.setText("Image manquante"); 
        }
    }

    // Méthode appelée quand on clique sur "Nouveau" ou au début
    private void genererNouveauCalcul() {
        Random random = new Random(); // Pour générer des nombres aléatoires

        if ("facile".equals(difficulty)) { // Si le niveau est facile
            do {
                nombre1 = random.nextInt(10); // Un chiffre aléatoire entre 0 et 9
                nombre2 = random.nextInt(10);
                operateur = random.nextBoolean() ? "+" : "-"; // operateur aléatoire  entre + ou -
                resultat = operateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            } while (resultat < 0); // On évite les résultats négatifs our le niveau facile 
        } else { // Niveau difficile
            int choixOperation = random.nextInt(3); // 0, 1 ou 2
            if (choixOperation < 2) { // Addition ou soustraction
                nombre1 = random.nextInt(1000); // Nombres jusqu'à 3 chiffres
                nombre2 = random.nextInt(1000);
                operateur = (choixOperation == 0) ? "+" : "-";
                resultat = operateur.equals("+") ? nombre1 + nombre2 : nombre1 - nombre2;
            } else { // Multiplication à 1 chiffre
                nombre1 = random.nextInt(10);
                nombre2 = random.nextInt(10);
                operateur = "x";
                resultat = nombre1 * nombre2;
            }
        }

        // Mise à jour de l'affichage avec le nouveau calcul
        labelCalcul.setText(nombre1 + " " + operateur + " " + nombre2 + " = ?");
        champReponse.setText(""); // Vider le champ de réponse
        champReponse.setEnabled(true);
        boutonVerifier.setEnabled(true);
        boutonVerifier.setBackground(null);
        boutonSolution.setEnabled(true);
        boutonSolution.setBackground(null);
        labelResultat.setText("");
        champReponse.requestFocus(); // Pour que l'utilisateur puisse taper sans cliquer
    }

    // Méthode appelée quand on clique sur "Vérifier"
    private void verifierReponse() {
    try {
        int reponseUtilisateur = Integer.parseInt(champReponse.getText().trim()); // On récupère et convertit la réponse de l'utilisateur et le trim permet de supprimer les espaces 
        labelResultat.setForeground(Color.WHITE);
        if (reponseUtilisateur == resultat) {
            labelResultat.setText(" BRAVOOO ! Tu es aussi fort qu'un ogre "); 
        } else {
            labelResultat.setText("C'est pas grave tu y étais presque!");
        }

        boutonVerifier.setEnabled(false); // Désactive le bouton pour éviter de vérifier plusieurs fois
        boutonSolution.setEnabled(false); // Désactive aussi le bouton "Solution"
    } catch (NumberFormatException e) {
        // Si l'utilisateur tape autre chose qu'un nombre 
        labelResultat.setText("Il faut que tu entres un nombre, sinon c'est pas valide  !");
    }
}


    // Méthode appelée quand on clique sur "Solution"
    private void afficherSolution() {
        labelResultat.setForeground(Color.WHITE);
        labelResultat.setText("La solution est " + resultat);
        champReponse.setEnabled(false);
        boutonVerifier.setEnabled(false);
        boutonVerifier.setBackground(Color.RED); // Mettre en rouge pour inciter à cliquer sur  nouveau
        boutonSolution.setEnabled(false);
        boutonSolution.setBackground(Color.RED);
    }

    // Classe interne pour afficher une image en fond
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage(); // On charge l'image d
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image de fond : " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Affiche l'image dans toute la zone
            }
        }
    }
}
