package com.example.boyinzhang.splendorandroid.Model.utils;

import java.util.Iterator;

import static java.lang.Math.max;

/**
 * Created by boyinzhang on 5/25/17.
 */

public class GemInfo implements Iterable<Integer> {

    public int diamond;
    public int emerald;
    public int onyx;
    public int ruby;
    public int sapphire;


    public GemInfo(int numDiamond, int numEmerald,int numOnyx, int numRuby, int numSapphire){
        this.diamond = numDiamond;
        this.emerald = numEmerald;
        this.onyx = numOnyx;
        this.ruby = numRuby;
        this.sapphire = numSapphire;
    }


    public GemInfo(int numberPerGem){
        this(numberPerGem,numberPerGem,numberPerGem,numberPerGem,numberPerGem);
    }

    public int GemTotalNum(){
        return diamond+emerald+onyx+ruby+sapphire;
    }

    public int GemMaxTypeNum(){
        int maxNum = max(diamond,emerald);
        maxNum = max(maxNum,onyx);
        maxNum = max(maxNum,ruby);
        maxNum = max(maxNum,sapphire);
        return maxNum;
    }

    public void updateInfo(int deltaDiamond, int deltaEmerald,int deltaOnyx, int deltaRuby, int deltaSapphire){
        this.diamond += deltaDiamond;
        this.emerald += deltaEmerald;
        this.onyx += deltaOnyx;
        this.ruby += deltaRuby;
        this.sapphire += deltaSapphire;
    }

    public void reset(){
        this.setGems(0,0,0,0,0);
    }

    public void setGems(int newDiamond, int newEmerald,int newOnyx, int newRuby, int newSapphire){
        this.diamond = newDiamond;
        this.emerald = newEmerald;
        this.onyx = newOnyx;
        this.ruby = newRuby;
        this.sapphire = newSapphire;
    }

    public static void combineGems(GemInfo currentGems, GemInfo addGems){
        currentGems.diamond += addGems.diamond;
        currentGems.emerald += addGems.emerald;
        currentGems.onyx += addGems.onyx;
        currentGems.ruby += addGems.ruby;
        currentGems.sapphire += addGems.sapphire;
    }

    public static void reduceGems(GemInfo currentGems, GemInfo addGems){
        currentGems.diamond -= addGems.diamond;
        currentGems.emerald -= addGems.emerald;
        currentGems.onyx -= addGems.onyx;
        currentGems.ruby -= addGems.ruby;
        currentGems.sapphire -= addGems.sapphire;
    }

    public int getByIndex(int index){
        int gemNum = 0;
        switch (index){
            case 1: gemNum = diamond; break;
            case 2: gemNum = emerald; break;
            case 3: gemNum = onyx; break;
            case 4: gemNum = ruby; break;
            case 5: gemNum = sapphire; break;
            default: gemNum = 0; break;
        }
        return gemNum;
    }

    public boolean isSatisfied(GemInfo cards){
        return this.diamond <= cards.diamond
                && this.emerald <= cards.emerald
                && this.onyx <= cards.onyx
                && this.ruby <= cards.ruby
                && this.sapphire <= cards.sapphire;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof GemInfo))return false;
        GemInfo other = (GemInfo)o;

        return (this.diamond == other.diamond && this.emerald == other.emerald
                && this.onyx == other.onyx && this.ruby == other.ruby
                && this.sapphire == other.sapphire);
    }

    public Iterator<Integer> iterator() {
        return new MyIterator();
    }

    class MyIterator implements Iterator<Integer> {

        private int index = 0;

        public boolean hasNext() {
            return index < 5;
        }

        public Integer next() {
            int gemNum = 0;
            getByIndex(index++);
            return gemNum;
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported yet");

        }
    }
}
