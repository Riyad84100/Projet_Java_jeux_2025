package com.mycompany.test_finale;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

/**
 *
 * @author yasmina
 */
public class MotDePassePanel extends JPanel {
    private JPasswordField passwordField;
    private JButton loginButton;
    private static final String PASSWORD_FILE = "src/mdp/password.txt";

    private LoginListener loginListener;

    public MotDePassePanel() {
        this.setLayout(new GridBagLayout());
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 100, 50, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Ajouter une image en haut
        ImageIcon icon = new ImageIcon("src/images/shrek.png");
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(icon);
        this.add(imageLabel, gbc);

        // Ajouter le label pour le mot de passe
        gbc.gridy++;
        JLabel label = new JLabel("Entrez le mot de passe :");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(label, gbc);

        // Ajouter le champ de saisie du mot de passe
        gbc.gridy++;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 22));
        passwordField.setPreferredSize(new Dimension(300, 50));
        this.add(passwordField, gbc);

        // Ajouter le bouton de connexion
        gbc.gridy++;
        loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Arial", Font.BOLD, 24));
        loginButton.setPreferredSize(new Dimension(250, 60));
        this.add(loginButton, gbc);

        // Ajouter l'action du bouton
        loginButton.addActionListener(e -> {
            char[] inputPassword = passwordField.getPassword();
            boolean success = verifyPassword(String.valueOf(inputPassword));
            if (loginListener != null) {
                loginListener.onLoginAttempt(success);
            }
        });
    }

    private boolean verifyPassword(String password) {
        try {
            String hashedPassword = hashPassword(password);
            String storedPassword = new String(Files.readAllBytes(Paths.get(PASSWORD_FILE))).trim();
            return hashedPassword.equals(storedPassword);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur de lecture du fichier de mot de passe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage", e);
        }
    }

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public interface LoginListener {
        void onLoginAttempt(boolean success);
    }
}
