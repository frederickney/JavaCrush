/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author admin_master
 */
public class Cell {
    
    public enum CellArray{
        BIRD,
        CAT,
        CRICKET,
        DOLPHIN,
        DRAGONFLY,
        ELEPHANT,
        GNOMEPANELFISH,
        JELLYFISH,
        BUGBUSTER,
        PENGUIN,
        PIG, 
        NONE
    }
    private CellArray type;
    public boolean breakable;

    /**
     * CellArray getter
     * @return  CellArray
     */
    public CellArray getType() {
        return this.type;
    }
    /**
     * CellArray setter
     * @param t CellArray
     */
    public void setType(CellArray t) {
        this.type = t;
    }
    
    /**
     * @brief default Cell constructor
     */
    public Cell() {
        this(CellArray.BIRD);
    }
    
    /**
     * @brief custom Cell constructor
     * @param type CellArray
     */
    public Cell(CellArray type) {
        this.type = type;
        this.breakable = false;
    }
    
    /** 
     * @brief mapping the images to the Cell name
     * @return  Map<CellArray, String>
     * @see Map
     */
    public static Map<CellArray, String> getFilenames() {
        Map<CellArray, String> filenames;
        filenames = new HashMap<>();
        filenames.put(CellArray.BIRD, "bird.png");
        filenames.put(CellArray.CAT, "cat.png");
        filenames.put(CellArray.CRICKET, "cricket.png");
        filenames.put(CellArray.DOLPHIN, "dolphin.png");
        filenames.put(CellArray.DRAGONFLY, "dragon_fly.png");
        filenames.put(CellArray.ELEPHANT, "elephant.png");
        filenames.put(CellArray.GNOMEPANELFISH, "gnome_panel_fish.png");
        filenames.put(CellArray.JELLYFISH, "jelly_fish.png");
        filenames.put(CellArray.BUGBUSTER, "kbugbuster.png");
        filenames.put(CellArray.PENGUIN, "penguin.png");
        filenames.put(CellArray.PIG, "pig.png");
        filenames.put(CellArray.NONE, "none.png");
        return filenames;
    }
}
