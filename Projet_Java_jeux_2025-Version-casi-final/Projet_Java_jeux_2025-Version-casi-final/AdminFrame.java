import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class AdminFrame extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> wordList;
    private ArrayList<String> words;
    private final String MOTS_PATH = "C:\\Users\\Maxence\\Downloads\\Projet_Java_jeux_2025-Version-casi-final\\Projet_Java_jeux_2025-Version-casi-final\\src\\mot.txt";
    private HomePage homePage; // Reference to HomePage

    public AdminFrame(HomePage homePage) {
        this.homePage = homePage; // Initialize the reference
        initUI();
    }

    @SuppressWarnings("unused")
    private void initUI() {
        this.setTitle("Administration - Gestion de la Liste de Mots");

        listModel = new DefaultListModel<>();
        wordList = new JList<>(listModel);
        words = new ArrayList<>();

        loadDictionary();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(wordList), BorderLayout.CENTER);

        AjouterPanel ajouter = new AjouterPanel(words, listModel);
        ModifierPanel modifier = new ModifierPanel(words, listModel);
        EnregistrerPanel enregistrer = new EnregistrerPanel(words);
        SupprimerPanel supprimer = new SupprimerPanel(words, listModel); // Passer le modèle de liste

        JPanel panelBas = new JPanel(new GridLayout(1, 3));
        panelBas.add(ajouter);
        panelBas.add(modifier);
        panelBas.add(supprimer);
        panelBas.add(enregistrer);
        mainPanel.add(panelBas, BorderLayout.SOUTH);

        JButton boutonRetour = new JButton();
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/src/images/fleche.png"));
        Image resizedImage = originalIcon.getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH);
        boutonRetour.setIcon(new ImageIcon(resizedImage));
        boutonRetour.setPreferredSize(new Dimension(120, 40));
        boutonRetour.setContentAreaFilled(false);
        boutonRetour.setBorderPainted(false);
        boutonRetour.setFocusPainted(false);

        boutonRetour.addActionListener(e -> {
            dispose();
            homePage.showHomePage(); // Show the HomePage
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(boutonRetour);
        this.add(topPanel, BorderLayout.NORTH); // Add the top panel to the frame

        this.add(mainPanel);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MOTS_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
                listModel.addElement(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la liste de mots.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
