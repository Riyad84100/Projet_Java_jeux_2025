package com.mycompany.test_finale;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author yasmi
 */
public class MotsPanel extends JPanel {
    private String motSecret; // Mot à deviner
    private char[] motAffiche; // Lettres devinées ou "_"
    private JLabel motLabel; // Label pour afficher le mot
    private ArrayList<String> motsList = new ArrayList<>(); // Liste des mots chargés depuis le fichier
    private ArrayList<String> motsFaciles = new ArrayList<>();
    private ArrayList<String> motsDifficiles = new ArrayList<>();
    private int erreurs; // Nombre d'erreurs actuelles

    public MotsPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        chargerMotsDepuisFichier("src/mot/mots.txt");
        nouveauMot(); // Démarrer avec un mot
    }

    // Charger les mots depuis un fichier texte
    private void chargerMotsDepuisFichier(String cheminFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String mot = ligne.trim();
                motsList.add(mot);
                if (mot.length() <= 6) {
                    motsFaciles.add(mot);
                } else {
                    motsDifficiles.add(mot);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier des mots : " + e.getMessage());
        }
    }

    // Choisir un mot aléatoire depuis la liste chargée
    private String choisirMot() {
        if (motsList.isEmpty()) {
            System.err.println("La liste des mots est vide !");
            return "";
        }
        return motsList.get(new Random().nextInt(motsList.size()));
    }

    public void nouveauMot() {
        this.motSecret = choisirMot();
        this.motAffiche = new char[motSecret.length()];
        Arrays.fill(motAffiche, '_'); // Initialiser avec des "_"
        erreurs = 0; // Réinitialiser les erreurs
        if (motLabel == null) {
            motLabel = new JLabel(getMotAffiche());
            motLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Agrandir la police du texte
            add(motLabel);
        } else {
            motLabel.setText(getMotAffiche());
        }
        repaint();
    }

    // Deviner une lettre
    public boolean devinerLettre(char lettre) {
        boolean trouve = false;
        for (int i = 0; i < motSecret.length(); i++) {
            if (motSecret.charAt(i) == lettre) {
                motAffiche[i] = lettre; // Révéler la lettre
                trouve = true;
            }
        }
        motLabel.setText(getMotAffiche());
        if (!trouve) {
            erreurs++; // Incrémenter les erreurs si la lettre n'est pas trouvée
        }
        return trouve;
    }

    // Vérifier si le mot est entièrement deviné
    public boolean isMotDevine() {
        for (char c : motAffiche) {
            if (c == '_') {
                return false; // Si un "_" est présent, le mot n'est pas encore deviné
            }
        }
        return true; // Aucun "_", donc le mot est deviné
    }

    // Retourner le mot affiché avec des espaces entre les lettres
    private String getMotAffiche() {
        return new String(motAffiche).replace("", " ").trim();
    }

    // Retourner le nombre d'erreurs actuelles
    public int getErreurs() {
        return erreurs;
    }


    public String getMotSecret() {
        return motSecret; // Retourne le mot secret
    }
}