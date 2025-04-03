/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.test;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author yasmi
 */
public class HomeMenuPanel extends JPanel{
    public HomeMenuPanel(HomePage homePage) {
        setLayout(new GridLayout(3, 2, 20, 20));
        
        JButton penduButton = new JButton("Jeu du Pendu");
        penduButton.addActionListener(e -> homePage.showPage("Pendu"));
        
        JButton adminButton = new JButton("Administration");
        adminButton.addActionListener(e -> homePage.showPage("Admin"));
        
        JButton adminLoginButton = new JButton("Connexion Admin");
        adminLoginButton.addActionListener(e -> homePage.showPage("AdminLogin"));
        
        JButton coloriageButton = new JButton("Jeu de Coloriage");
        coloriageButton.addActionListener(e -> homePage.showPage("Coloriage"));
        
        JButton calculButton = new JButton("Activité de Calcul");
        calculButton.addActionListener(e -> homePage.showPage("Calcul"));
        
        add(penduButton);
        add(adminButton);
        add(adminLoginButton);
        add(coloriageButton);
        add(calculButton);
    }
}



