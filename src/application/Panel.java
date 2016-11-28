/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author admin_master
 */
public class Panel extends JPanel {
    
    private final GUI gui;
    /**
     * @brief Panel constructor
     * @param gui GUI
     * @see GUI
     * @param height int
     * @param width  int
     */
    public Panel(GUI gui, int height, int width) {
        this.gui = gui;
        this.setLayout(new GridLayout(height, width));
    }
    
    /**
     * @brief paintComponent method
     * @param g Graphics component
     */
    @Override
    public void paintComponent(Graphics g) {       
       
    }
    
}
