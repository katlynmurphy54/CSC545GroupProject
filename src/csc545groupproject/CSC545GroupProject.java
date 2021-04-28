/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545groupproject;

import csc545groupproject.Views.MainJFrame;
import javax.swing.UIManager;

/**
 *
 * @author Jadon
 */
public class CSC545GroupProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            System.out.println("Using default LaF");
        }
        
        MainJFrame mainJF = new MainJFrame();
        
        mainJF.setTitle("Recipe Manager");
        mainJF.setVisible(true);
    }
    
}
