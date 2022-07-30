package com.gmail.ibmesp1.bp.utils.backpacks;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.utils.DataManager;

public class Checkers {

    private Backpacks plugin;
    private DataManager bpcm;
    private int maxBP;
    private int rowsBP;

    public Checkers(Backpacks plugin, DataManager bpcm, int maxBP, int rowsBP) {
        this.plugin = plugin;
        this.bpcm = bpcm;
        this.maxBP = maxBP;
        this.rowsBP = rowsBP;
    }

    public int getRowsBP() {
        return rowsBP;
    }

    public void checkSize(){
        int smallSize = bpcm.getConfig().getInt("smallSize");
        int mediumSize = bpcm.getConfig().getInt("mediumSize");
        int largeSize = bpcm.getConfig().getInt("largeSize");

        if(smallSize > 6 || smallSize < 1){
            if(smallSize < 1){
                bpcm.getConfig().set("smallSize",1);
                bpcm.saveConfig();
                return;
            }
            bpcm.getConfig().set("smallSize", 6);
            bpcm.saveConfig();
        }

        if(mediumSize > 6 || mediumSize < 1){
            if(mediumSize < 1){
                bpcm.getConfig().set("mediumSize",1);
                bpcm.saveConfig();
                return;
            }
            bpcm.getConfig().set("mediumSize",6);
            bpcm.saveConfig();
        }

        if (largeSize > 6|| largeSize < 1){
            if(largeSize < 1){
                bpcm.getConfig().set("largeSize",1);
                bpcm.saveConfig();
                return;
            }
            bpcm.getConfig().set("largeSize",6);
            bpcm.saveConfig();
        }

        if(smallSize >= mediumSize){
            if(mediumSize == 1){
                bpcm.getConfig().set("smallSize",1);
                bpcm.getConfig().set("mediumSize",2);
                bpcm.saveConfig();
                return;
            }

            while (smallSize >= mediumSize){
                smallSize--;
                bpcm.getConfig().set("smallSize",smallSize);
                bpcm.saveConfig();
            }
        }
        if(largeSize <= mediumSize){
            if(mediumSize == 6){
                bpcm.getConfig().set("mediumSize",5);
                bpcm.getConfig().set("largeSize",6);
                bpcm.saveConfig();
                return;
            }

            if(largeSize <= 2){
                bpcm.getConfig().set("smallSize",1);
                bpcm.getConfig().set("mediumSize",2);
                bpcm.getConfig().set("largeSize",3);
                bpcm.saveConfig();
                return;
            }

            while (largeSize <= mediumSize) {
                mediumSize--;
                bpcm.getConfig().set("mediumSize", mediumSize);
                bpcm.saveConfig();
            }
        }
    }
    public void checkMaxBP() {
        maxBP = plugin.getConfig().getInt("maxBP");
        if(maxBP > 36 || maxBP < 1){
            if(maxBP < 1){
                maxBP = 1;
                plugin.getConfig().set("maxBP",1);
                plugin.saveConfig();
            }else{
                maxBP = 36;
                plugin.getConfig().set("maxBP", 36);
                plugin.saveConfig();
            }
        }

        if(maxBP % 9 != 0){
            rowsBP = maxBP / 9 + 1;
        }else{
            rowsBP = maxBP / 9;
        }
    }
}
