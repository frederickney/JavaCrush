/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timer;

import java.util.TimerTask;
import controller.Controller;

/**
 *
 * @author admin_master
 */
public class EndTimer extends TimerTask {

    private final Controller controller;
    /**
     * @brief EndTimer constructor
     * @param c Controller
     * @see  Controller
     */
    public EndTimer(Controller c) {
        this.controller = c;
    }
    /**
     * @brief runnable method
     */
    @Override
    public void run() {
        this.controller.showScore();
        this.controller.disposeMain();
    }
}
