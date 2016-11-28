/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Observable;

/**
 *
 * @author admin_master
 */
public class Model extends Observable {
    private final Grid grid;
    private int points;
    /**
     * @brief Grid getter
     * @return Grid
     */
    public Grid getGrid() {
        this.setChanged();
        return this.grid;
    }
    
    /**
     * @brief points getter
     * @return int
     */
    public int getPoints() { return this.points; }
    
    /**
     * @brief Model constructor
     * @param height int
     * @param width int
     */
    public Model(int height, int width) {
        this.grid = new Grid(height, width);
        this.points = 0;
        this.setChanged();
    }
    /**
     * @brief adding points
     * @param p int
     */
    public void addPoints(int p) {
        this.points += p;
        this.setChanged();
    }
}
