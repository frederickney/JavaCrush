/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.concurrent.ThreadLocalRandom;
import model.Cell.CellArray;

/**
 *
 * @author admin_master
 */
public class Grid {
    
    private int height;
    private int width;
    private Cell[][] grid;
    /**
     * 
     * @return Cell[][]
     */
    public Cell[][] getAnimals() {
        return this.grid;
    }
    /**
    * 
    * @return int - height
    */
    public int getHeight() {
        return this.height;
    }
    /**
     * 
     * @return int - width
     */
    public int getWidth() {
        return this.width;
    }
    /**
     * 
     */
    public Grid(){
        this(10, 10);
    }
    /**
     * 
     * @param width int
     * @param height int
     */
    public Grid(int width, int height) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[this.height][this.width];
        
        for(int h = 0; h < this.height; h++) {
            for(int w = 0; w < this.width; w++) {
                int selector = ThreadLocalRandom.current().nextInt(0, 11);
                this.grid[h][w] = new Cell(CellArray.values()[selector]);
            }
        }
    }
    /**
     * 
     * @param w int - width
     * @param h int - height
     * @return Cell
     */
    public Cell get(int w, int h) {
        if((w >= 0) && (w < this.getWidth()) && (h >= 0) && (h < this.getHeight()))
            return this.grid[h][w];
        else 
            return null;
    }
    /**
     * 
     * @param w int - width
     * @param h int - height
     * @param a Cell
     */
    public void set(int w, int h, Cell a) {
        if((w >= 0) && (w < this.getWidth()) && (h >= 0) && (h < this.getHeight()))
            this.grid[h][w] = a;
    }
}
