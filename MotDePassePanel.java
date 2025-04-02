/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.admin;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.ImageIcon;

/**
 *
 * @author yasmi
 */
public class MotDePassePanel extends JPanel{
    private JPasswordField passwordField;
    private JButton loginButton;
    private static final String PASSWORD_FILE = "C:\\Users\\yasmi\\Downloads\\password.txt";

    public MotDePassePanel() {
        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(50, 100, 50, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Ajouter une image en haut
        ImageIcon icon = new ImageIcon("C:\\Users\\yasmi\\Downloads\\shrek.png"); // Remplace avec ton image
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(icon);
        this.add(imageLabel, gbc);

        gbc.gridy++;
        JLabel label = new JLabel("Entrez le mot de passe :");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(label, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 22));
        passwordField.setPreferredSize(new Dimension(300, 50));
        this.add(passwordField, gbc);

        gbc.gridy++;
        loginButton = new JButton("Se connecter");
        loginButton.setBackground(new Color(59, 89, 182));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 24));
        loginButton.setPreferredSize(new Dimension(250, 60));
        loginButton.setFocusPainted(false);
        this.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] inputPassword = passwordField.getPassword();
                if (verifyPassword(String.valueOf(inputPassword))) {
                    JOptionPane.showMessageDialog(null, "Accès autorisé ! ✅", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    new AdminFrame().setVisible(true);
                    SwingUtilities.getWindowAncestor(MotDePassePanel.this).dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "⛔ Mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }
            }
        });
    }

    private boolean verifyPassword(String password) {
        try {
            String hashedPassword = hashPassword(password);
            String storedPassword = new String(Files.readAllBytes(Paths.get(PASSWORD_FILE))).trim();
            return hashedPassword.equals(storedPassword);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur de lecture du fichier de mot de passe.", "Erreur", JOptionPane.ERROR_MESSAGE);
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
}
