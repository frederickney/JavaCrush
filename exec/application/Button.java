/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;


import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author admin_master
 */
public class Button extends JButton {

    public int x, y;
    private String filename;

    
    /**
     * @brief Button constructor
     * @param y int - y coordinate of the button
     * @param x int - x coordinate of the button
     */
    public Button(int y, int x){
        super();
        this.x = x;
        this.y = y;
    }
    
    /**
     * @brief setting icon image to button
     * @param img Image
     */
    public void settingIcon(Image img) {
        this.setIcon(new ImageIcon(img));
    }
}
