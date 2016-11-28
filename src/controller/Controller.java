/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;
import model.Cell;
import model.Cell.CellArray;
import model.Grid;
import model.Model;
import checker.RowsChecker;
import checker.ColumnsChecker;
import timer.EndTimer;
import application.Score;
import application.GUI;
import java.io.IOException;

/**
 *
 * @author admin_master
 */
public final class Controller {
    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }
    private static final int POINTS_3 = 50;
    private static final int POINTS_4 = 150;
    private static final int POINTS_5 = 400;
    private static final int POINTS_6 = 750;
    private static final int POINTS_7 = 1000;
    private static final int POINTS_8 = 1500;
    private static final int DEFAULT = 2500;
    private static final int END_GAME_TIMER = 180000;
    private Score score;
    private final GUI gui;
    private final Model model;
    private ColumnsChecker columnsChecker;
    private RowsChecker rowsChecker;
    private Timer timer;
    
    /**
     * @brief Grid getter
     * @return Grid
     */
    public Grid getGrid() { return this.model.getGrid(); }
    
    /**
     * @throws java.io.IOException
     * @brief Controller constructor
     * @param height int
     * @param width  int
     * @see Model
     * @see GUI
     */
    public Controller(int height, int width) throws IOException {
        this.gui = new GUI(this, height, width);
        this.model = new Model(height, width);
        this.model.addObserver(this.gui);
        this.update();
        this.checkGridRecursively();
        this.setTimer();
    }
    /**
     * @brief Timer setter
     * @see Timer
     * @see Date
     */
    private void setTimer() {
        this.timer = new Timer();
        Date d = new Date();
        d.setTime(d.getTime() + END_GAME_TIMER);
        this.timer.schedule(new EndTimer(this), d);
    }
    
    /**
     * @brief Grid checker
     */
    public void checkGridRecursively() {
        boolean r = this.checkGrid();
        if(r) 
            checkGridRecursively();
    }
    
    /**
     * @brief checking if buttons had been destroyed
     * @see ColumnsChecker
     * @see RowsChecker
     * @return boolean
     */
    public boolean checkGrid() {
        this.columnsChecker = new ColumnsChecker(this);
        this.rowsChecker = new RowsChecker(this);
        this.columnsChecker.t = this.rowsChecker;
        this.rowsChecker.t = this.columnsChecker;
        this.rowsChecker.run();
        this.columnsChecker.run();
        return this.columnsChecker.hasDestroyed | this.rowsChecker.hasDestroyed;
    }
    
    /**
     * 
     * @param type
     * @see CellArray
     * @return V
     */
    public String getFilenameFromAnimal(CellArray type) {
        return Cell.getFilenames().get(type);
    }
    
    /**
     * @brief Try to swap butons
     * @param x1 int - x coordinate of the first button
     * @param y1 int - y coordinate of the first button
     * @param x2 int - x coordinate of the second button
     * @param y2 int - y coordinate of the second button
     */
    public void trySwap(int x1, int y1, int x2, int y2) {
        if(this.isSwapable(x1, y1, x2, y2)) {
            this.swap(x1, y1, x2, y2);
            this.checkGridRecursively();
        }
    }
    
    /**
     * 
     * @brief swapping th buttons
     * @param x1 int - x coordinate of the first button
     * @param y1 int - y coordinate of the first button
     * @param x2 int - x coordinate of the second button
     * @param y2 int - y coordinate of the second button
     * @see Model
     */
    private void swap(int x1, int y1, int x2, int y2) {
        Cell[][] grid = this.model.getGrid().getAnimals();
        Cell tmp = grid[y1][x1];
        grid[y1][x1] = grid[y2][x2];
        grid[y2][x2] = tmp;
    }
    
    /**
     * 
     * @brief testing if the buttons could be swapped
     * @param x1 int - x coordinate of the first button
     * @param y1 int - y coordinate of the first button
     * @param x2 int - x coordinate of the second button
     * @param y2 int - y coordinate of the second button
     * @return boolean
     */
    public boolean isSwapable(int x1, int y1, int x2, int y2) {
        int delta_x = Math.abs(x2 - x1);
        int delta_y = Math.abs(y2 - y1);
        this.swap(x1, y1, x2, y2); // Swap one time
        boolean destroyable = this.gridHasDestroyable(); // Look if it's destroyable
        this.swap(x1, y1, x2, y2); // Swap again 
        return destroyable && (((delta_x == 1) && (delta_y == 0)) || ((delta_x == 0) && (delta_y == 1)));
    }
    /**
     * @brief checking if buttons had been destroyed
     * @return boolean
     */
    private boolean gridHasDestroyable() {
        boolean out = false;
        int height = this.getGrid().getHeight();
        int width = this.getGrid().getWidth();
        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                out |= this.isCorrect(3, h, w, Orientation.VERTICAL) || this.isCorrect(3, h, w, Orientation.HORIZONTAL);
                out |= this.isCorrect(4, h, w, Orientation.VERTICAL) || this.isCorrect(4, h, w, Orientation.HORIZONTAL);
                out |= this.isCorrect(5, h, w, Orientation.VERTICAL) || this.isCorrect(5, h, w, Orientation.HORIZONTAL);
            }           
        }
        return out;
    }
    
    /**
     * @brief pulling down buttons
     * @see Grid
     * @see Model
     */
    public void pullDown() {
        Grid grid = this.model.getGrid();
        for(int column = 0; column < grid.getWidth(); column++) {
            for(int h = grid.getHeight() - 1; h >= 0; h--) {
                if(grid.get(column, h).breakable == true){
                    for(int i = h; i >= 0; i--){
                        if(grid.get(column, i).breakable == false){
                            this.swap(column, h, column, i);
                            break;
                        }
                    }
                }
            }
        }
    }
    /**
     * @brief showing score
     * @see Score
     */
    public void showScore() {
        this.score = new Score(this, this.model.getPoints());
    }
    /**
     * @brief disposing main
     * @see Model
     * @see  GUI
     */
    public void disposeMain() {
        this.model.deleteObserver(this.gui);
        this.gui.close();
    }
    
    /**
     * @brief Fill Grid
     * @see  Grid
     * @see Model
     */
    public void fill() {
        Grid grid = this.model.getGrid();
        for(int column = 0; column < grid.getWidth(); column++) {
            for(int h = grid.getHeight() - 1; h >= 0; h--) {
                if(grid.get(column, h).breakable == true) {
                    int selector = ThreadLocalRandom.current().nextInt(0, 11);
                    grid.set(column, h, new Cell(CellArray.values()[selector]));
                    grid.get(column, h).breakable = false;
                }
            }
        }
    }
    
    /**
     * @brief updating Model
     * @see  Model
     */
    public void update() {
        this.model.notifyObservers();
    }
    
    /**
     * 
     * @param n
     * @param h int - height
     * @param w int - width
     * @param orientation
     * @return boolean
     */
    public boolean isCorrect(int n, int h, int w, Orientation orientation) {
        boolean out = true;
        int increment_w = (orientation == Orientation.HORIZONTAL) ? 1 : 0;
        int increment_h = (orientation == Orientation.VERTICAL) ? 1 : 0;
        for(int i = 0; (i < n) && out; i++) {
            Cell current = this.getGrid().get(w, h);
            Cell next = this.getGrid().get(w + (i*increment_w), h + (i*increment_h));
            if((current != null) && (next != null)) {
                out &= (current.getType() == next.getType());
            } else {
                out &= false;
            }
        }
        if(out == true) {
            this.attributePoints(n);
            System.out.println(this.model.getPoints());
        }
        return out;
    }
    
    /**
     * @brief attributing points
     * @see  Model
     * @param n int - number of button destroyed
     */
    private void attributePoints(int n){
        switch(n) {
            case 3:
                this.model.addPoints(this.POINTS_3);
                break;

            case 4:
                this.model.addPoints(this.POINTS_4);
                break;

            case 5:
                this.model.addPoints(this.POINTS_5);
                break;
            case 6:
                this.model.addPoints(this.POINTS_6);
                break;
            case 7:
                this.model.addPoints(this.POINTS_7);
                break;
            case 8:
                this.model.addPoints(this.POINTS_8);
                break;
            default:
                this.model.addPoints(this.DEFAULT);
                break;
        }
    }
}
