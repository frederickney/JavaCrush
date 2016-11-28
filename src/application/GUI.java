/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import controller.Controller;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Thread.sleep;
import model.Cell;
import model.Cell.CellArray;
import model.Model;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author admin_master
 */
public class GUI extends JFrame implements Observer, ActionListener {
    
    String png = "/res/netbeans.png";
    ImageIcon Icon;
    Image FrameIcon;
    private final String IMAGES_RES = "/res/images/";
    private final Controller controller;
    private final Panel panel;
    private final Button[][] buttons;
    private Map<CellArray, Image> images;
    private int oldX, oldY;
    private int clicks;
    public winExit exhop;
    
    /**
     * 
     * @param controller Controller
     * @throws java.io.IOException
     * @see Controller
     * @param height int
     * @param width  int
     * @see Panel
     * @see Button
     */
    public GUI(Controller controller, int height, int width) throws IOException {
        try {
        this.Icon = new  ImageIcon(ImageIO.read(getClass().getResource(png)));
        this.FrameIcon = Icon.getImage();
        } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        this.controller = controller;
        this.panel = new Panel(this, height, width);
        this.buttons = new Button[height][width];
        this.setButtons(height, width);
        this.loadImagesByType();
        this.setContentPane(this.panel);
        initGUI();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        enableEvents(WindowEvent.WINDOW_CLOSING);
    }
    /**
     * @brief initializing the GUI
     * @function initGUI
     */
    private void initGUI() {
        this.setIconImage(FrameIcon);
        this.setResizable(true);
        this.setSize(600 , 600);
        this.exhop = new winExit(this.controller);
        this.addWindowListener(exhop);
    }
    
    /**
     * @brief Making some setting for buttons
     * @param  height int
     * @param  width int
     * @see Button
     * @see Panel
     */
    private void setButtons(int height, int width) {
        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                this.buttons[h][w] = new Button(h, w);
                this.buttons[h][w].addActionListener(this);
                this.panel.add(this.buttons[h][w]);
            }
        }
    }
    
    /**
     * @brief loading images by CellArray enum
     * @see CellArray
     */
    private void loadImagesByType() {
        this.images = new HashMap<>();
        Map<CellArray, String> m = Cell.getFilenames();
        m.entrySet().stream().forEach((entry) -> {
            Image img;
            try {
                img = ImageIO.read(getClass().getResource(IMAGES_RES + this.controller.getFilenameFromAnimal(entry.getKey())));
                img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                this.images.put(entry.getKey(), img);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * @param  o Observable
     * @param arg Object
     * @brief Override update method of Model
     * @see Observable
     * @see Object
     * @see Model
     * @see Button
     * @see CellArray
     */
    @Override
    public void update(Observable o, Object arg) {
        Model m = (Model) o;
        for(int h = 0; h < m.getGrid().getHeight(); h++) {
            for(int w = 0; w < m.getGrid().getWidth(); w++) {
                CellArray type = m.getGrid().getAnimals()[h][w].getType();  
                if(!(m.getGrid().get(w, h).breakable))
                    this.buttons[h][w].settingIcon(this.images.get(type));
                else 
                    this.buttons[h][w].setIcon(null);
            }
        }
    }

    /**
     * @brief Listener for all buttons
     * @param e ActionEvent
     * @see ActionEvent
     * @see Button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.clicks ++;
        Button btn = (Button) e.getSource();
        if(this.clicks == 1) {
            this.oldX = btn.x;
            this.oldY = btn.y;
        } else if(this.clicks == 2) {
            this.controller.trySwap(this.oldX, this.oldY, btn.x, btn.y);
            this.oldX = 0;
            this.oldY = 0;
            this.clicks = 0;
        }
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
    }

}
/**
 * @class winEx
 * @author admin_master
 */
class winExit extends WindowAdapter {
    static Controller controller;
    /**
     * @description winExit constructor
     * @constructor winExit
     * @param id 
     */
    winExit(Controller c) {
        this.controller = c;
    }
    /**
     * @description windows closing event function (override)
     * @function windowClosing
     * @param we The window closing event.
     * @see java.awt.event.WindowEvent
     */
    @Override
    public void windowClosing(WindowEvent we) {
        this.controller.showScore();
    }
}
