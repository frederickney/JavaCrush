/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checker;

import java.util.logging.Level;
import java.util.logging.Logger;
import controller.Controller;
import controller.Controller.Orientation;
import model.Grid;

/**
 *
 * @author admin_master
 */
public class ColumnsChecker extends Thread {
    
    private final Controller controller;
    private final Grid g;
    public Thread t;
    public boolean hasDestroyed = false;
    
    /**
     * @brief ColumnsChecker constructor
     * @param controller Controller
     * @see Controller
     */
    public ColumnsChecker(Controller controller) {
        this.controller = controller;
        this.g = this.controller.getGrid();
        this.t = new Thread();
    }
    
    /**
     * @brief runnable method
     */
    @Override
    public void run() {
        try {
            t.join(); // Wait when the rows checker finished
            this.execute();
        } catch (InterruptedException ex) {
            Logger.getLogger(ColumnsChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @brief columns checker
     * @see Controller
     * @see Grid
     */
    private void execute() {
        for(int h = 0; h < g.getHeight(); h++) {
            for(int w = 0; w < g.getWidth(); w++) {
                    for(int j = 5; j >= 3; j--) {
                        if(this.controller.isCorrect(j, h, w, Orientation.VERTICAL)) {
                            this.hasDestroyed |= true;
                            for(int i = 0; i<j; i++) 
                                g.get(w, h + i).breakable = true;
                            break;
                        }
                    }
            }
        }
        this.controller.pullDown();
        this.controller.fill();
        this.controller.update();
    }
}
