/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checker;

import controller.Controller;
import model.Grid;

/**
 *
 * @author admin_master
 */
public class RowsChecker extends Thread {
    private final Controller controller;
    private final Grid g;
    public Thread t;
    public boolean hasDestroyed = false;
    
    /**
     * @brief RowsChecker constructor
     * @param controller Controller
     * @see  Controller
     * @see Grid
     */
    public RowsChecker(Controller controller) {
        this.controller = controller;
        g = this.controller.getGrid();
        this.t = new Thread();
    }
    
    /**
     * @brief runnable method
     */
    @Override
    public void run() {
        this.execute();
    }
    
    /**
     * @brief rows checker
     * @see  Controller
     * @see Grid
     */
    private void execute() {
        for(int h = 0; h < g.getHeight(); h++) {
            for(int w = 0; w < g.getWidth(); w++) {
                    for(int j = 5; j >= 3; j--) {
                        if(this.controller.isCorrect(j, h, w, Controller.Orientation.HORIZONTAL)) {
                            this.hasDestroyed |= true;
                            for(int i = 0; i<j; i++)
                                g.get(w + i, h).breakable = true;
                            break;
                        }
                    }
                
            }
        }
    }
}
