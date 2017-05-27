package com.example.boyinzhang.splendorandroid.Model;

import com.example.boyinzhang.splendorandroid.Model.utils.GemInfo;

import java.io.Serializable;

import static com.example.boyinzhang.splendorandroid.Model.utils.GameUtils.NOBLE_SCORE;

/**
 * Created by boyinzhang on 5/25/17.
 */

public class Noble implements Serializable {
    private int score;
    private GemInfo threshold;
    private boolean isRecruited;

    public Noble(int diamondCost, int emeraldCost, int onyxCost, int rubyCost, int sapphireCost){
        score = NOBLE_SCORE;
        threshold = new GemInfo(diamondCost,emeraldCost,onyxCost,rubyCost,sapphireCost);
        isRecruited = false;
    }

    public boolean getIsRecruited(){
        return this.isRecruited;
    }

    public void beRecruited(){
        this.isRecruited = true;
    }

    public int getScore() {
        return score;
    }

    public GemInfo getThreshold(){
        return threshold;
    }

    public boolean satisfied(GemInfo cards){
        return this.threshold.isSatisfied(cards);
    }
}
