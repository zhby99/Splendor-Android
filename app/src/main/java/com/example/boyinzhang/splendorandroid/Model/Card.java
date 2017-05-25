package com.example.boyinzhang.splendorandroid.Model;

import com.example.boyinzhang.splendorandroid.Model.utils.GemInfo;

/**
 * Created by boyinzhang on 5/25/17.
 */

public class Card {
    final private int cardScore;
    final private GemInfo developmentCost;
    final private Gem targetGem;
    private boolean reserved;
    private int rank;
    private int index;

    public Card(int score, GemInfo developmentCost, Gem targetGem) {
        this.cardScore = score;
        this.developmentCost = developmentCost;
        this.targetGem = targetGem;
        this.reserved = false;
    }

    public void setPosition(int rank, int index){
        this.rank = rank;
        this.index = index;
    }

    public int[] getPosition(){
        return new int[]{rank,index};
    }

    public boolean isReserved(){
        return this.reserved;
    }

    public void setReserved(){
        this.reserved = true;
    }
    public Gem getTargetGem(){
        return targetGem;
    }

    public GemInfo getDevelopmentCost(){
        return new GemInfo(developmentCost.diamond,developmentCost.emerald,
                developmentCost.onyx,developmentCost.ruby,developmentCost.sapphire);
    }

    public int getCardScore(){
        int score;
        score = cardScore;
        return score;
    }
}
