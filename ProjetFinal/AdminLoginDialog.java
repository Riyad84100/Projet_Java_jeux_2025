import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

public class AdminLoginDialog extends JDialog {
    private JPasswordField passwordField;
    private boolean authenticated = false;
    private static final String PASSWORD_FILE = "src/mdp/password.txt"; // Chemin relatif

    public AdminLoginDialog(JFrame parent) {
        super(parent, "Connexion Administrateur", true);
        initUI();
        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unused")
    private void initUI() {
        setLayout(new GridBagLayout());
        
        JLabel label = new JLabel("Entrez le mot de passe :");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JButton loginButton = new JButton("Se connecter");
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
            String storedPassword = Files.readString(Paths.get(PASSWORD_FILE)).trim();
            
            if (hashedInput.equals(storedPassword)) {
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

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}