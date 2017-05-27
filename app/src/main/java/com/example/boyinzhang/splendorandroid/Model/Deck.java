package com.example.boyinzhang.splendorandroid.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by boyinzhang on 5/25/17.
 */

public class Deck implements Serializable {
    public ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
    }
}
