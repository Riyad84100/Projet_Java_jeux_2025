import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private String currentDifficulty = "facile";
    private boolean isAdminAuthenticated = false; // Indique si l'utilisateur est authentifié

    @SuppressWarnings("unused")
    public MainFrame() {
        setTitle("Jeux Éducatifs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Création de la barre de menu
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Difficulté
        JMenu difficultyMenu = new JMenu("Difficulté");
        ButtonGroup difficultyGroup = new ButtonGroup();
        
        // Boutons radio pour les niveaux de difficulté
        JRadioButtonMenuItem facileItem = new JRadioButtonMenuItem("Facile");
        facileItem.setSelected(true); // Par défaut, "facile" est sélectionné
        facileItem.addActionListener(e -> {
            currentDifficulty = "facile";
            updateTabsDifficulty();
            System.out.println("Difficulté sélectionnée : " + currentDifficulty);
        });
        
        JRadioButtonMenuItem difficileItem = new JRadioButtonMenuItem("Difficile");
        difficileItem.addActionListener(e -> {
            currentDifficulty = "difficile";
            updateTabsDifficulty();
            System.out.println("Difficulté sélectionnée : " + currentDifficulty);
        });
        
        // Ajouter les boutons au groupe et au menu
        difficultyGroup.add(facileItem);
        difficultyGroup.add(difficileItem);
        difficultyMenu.add(facileItem);
        difficultyMenu.add(difficileItem);
        menuBar.add(difficultyMenu);
        
        // Menu Admin
        JMenu adminMenu = new JMenu("Admin");
        JMenuItem adminItem = new JMenuItem("Connexion Admin");
        adminItem.addActionListener(e -> showAdminTab());
        adminMenu.add(adminItem);
        menuBar.add(adminMenu);
        
        setJMenuBar(menuBar);
        
        // Création des onglets
        tabbedPane = new JTabbedPane();
        
        // Ajout des onglets
        addCalculTab();
        addDessinTab();
        addPenduTab();
        addMusic();
        
        // Ajouter un ChangeListener pour surveiller les changements d'onglets
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex != -1 && "Admin".equals(tabbedPane.getTitleAt(selectedIndex))) {
                if (!isAdminAuthenticated) {
                    // Si l'utilisateur n'est pas authentifié, demander le mot de passe
                    showAdminTab();
                }
            } else {
                // Si l'utilisateur quitte l'onglet Admin, le supprimer
                removeAdminTab();
            }
        });
        
        add(tabbedPane);
    }
    
    private void updateTabsDifficulty() {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component comp = tabbedPane.getComponentAt(i);
            if (comp instanceof CalculPanel) {
                ((CalculPanel) comp).setDifficulty(currentDifficulty); // Appliquer la nouvelle difficulté
            } else if (comp instanceof DessinPanel) {
                ((DessinPanel) comp).setDifficulty(currentDifficulty); // Appliquer la nouvelle difficulté
            } 
        }
    }
    
    private void addCalculTab() {
        CalculPanel calculPanel = new CalculPanel(currentDifficulty);
        tabbedPane.addTab("Calcul", calculPanel);
    }
    
    private void addDessinTab() {
        DessinPanel dessinPanel = new DessinPanel(currentDifficulty);
        tabbedPane.addTab("Dessin", dessinPanel);
    }
    
    private void addPenduTab() {
        PenduPanel penduPanel = new PenduPanel();
        tabbedPane.addTab("Pendu", penduPanel);
    }

    private void addMusic() {
        MusicPlayerPanel musicPlayerPanel = new MusicPlayerPanel();
        tabbedPane.addTab("Musique", musicPlayerPanel);
    }
    
    private void showAdminTab() {
        // Vérifier le mot de passe avant d'afficher l'onglet admin
        AdminLoginDialog loginDialog = new AdminLoginDialog(this);
        loginDialog.setVisible(true);
        
        if (loginDialog.isAuthenticated()) {
            isAdminAuthenticated = true; // Marquer l'utilisateur comme authentifié
            
            // Si l'onglet admin existe déjà, le sélectionner
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (tabbedPane.getTitleAt(i).equals("Admin")) {
                    tabbedPane.setSelectedIndex(i);
                    return;
                }
            }
            
            // Sinon, ajouter un nouvel onglet admin
            AdminPanel adminPanel = new AdminPanel();
            tabbedPane.addTab("Admin", adminPanel);
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        }
    }
    
    private void removeAdminTab() {
        // Supprimer l'onglet Admin si l'utilisateur quitte cet onglet
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if ("Admin".equals(tabbedPane.getTitleAt(i))) {
                tabbedPane.removeTabAt(i);
                isAdminAuthenticated = false; // Réinitialiser l'état d'authentification
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}