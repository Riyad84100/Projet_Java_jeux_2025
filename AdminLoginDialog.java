package com.mycompany.test_finale;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.util.Properties;

/**
 *
 * @author yasmi
 */

public class AdminLoginDialog extends JDialog {
    private JLabel label;
    private JButton loginButton;
    private JPasswordField passwordField;
    private boolean authenticated = false;
    private static final String CONFIG_FILE = "src/config/config.properties";

    public AdminLoginDialog(JFrame parent) {
        super(parent, "Connexion Administrateur", true);
        initUI();
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
    }

    @SuppressWarnings("unused")
    private void initUI() {
        setLayout(new GridBagLayout());
        
        label = new JLabel("Entrez votre mot de passe :");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        
        loginButton = new JButton("Se connecter");
        loginButton.addActionListener(e -> verifyPassword());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);
        
        gbc.gridy = 1;
        add(passwordField, gbc);
        
        gbc.gridy = 2;
        add(loginButton, gbc);
    }

    private void verifyPassword() {
        char[] inputPassword = passwordField.getPassword();
        try {
            String hashedInput = hashPassword(String.valueOf(inputPassword));
            String storedPassword = loadStoredPassword(); 
            
            if (hashedInput.equals(storedPassword)) {
                JOptionPane.showMessageDialog(null, "Accès autorisé ! ✅", "Succès", JOptionPane.INFORMATION_MESSAGE);
                authenticated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur de vérification", "Erreur", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Efface le mot de passe en mémoire
            java.util.Arrays.fill(inputPassword, '\0');
        }
    }
    
    private String loadStoredPassword() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
        }
        return props.getProperty("admin.password", "").trim();
    }
    
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}