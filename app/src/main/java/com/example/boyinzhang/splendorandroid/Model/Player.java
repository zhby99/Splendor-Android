package com.example.boyinzhang.splendorandroid.Model;

import com.example.boyinzhang.splendorandroid.Game.Game;
import com.example.boyinzhang.splendorandroid.Model.utils.GemInfo;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.boyinzhang.splendorandroid.Model.utils.GameUtils.*;
import static com.example.boyinzhang.splendorandroid.Model.utils.GemInfo.*;
import static java.lang.Math.max;

/**
 * Created by boyinzhang on 5/25/17.
 */

public class Player implements Serializable {
    final private int id;
    private String name;
    private int score;
    private GemInfo gems;
    int golds;
    GemInfo cards;
    int numCards;
    ArrayList<Card> reserves;
    private Board board;

    public Player(int pid, Board newBoard, String username){
        id = pid;
        name = username;
        score = 0;
        gems = new GemInfo(0);
        golds = 0;
        cards = new GemInfo(0);
        numCards = 0;
        reserves = new ArrayList<Card>();
        board = newBoard;

    }
    public int getPlayerId(){return id;}
    public int getScore() {return score;}
    public String getName() {return name;}
    public GemInfo getGems() {
        return gems;
    }
    public void setCards(int number){
        cards.setGems(number,number,number,number,number);
    }
    public int getGolds() {
        return golds;
    }
    public GemInfo getCards() {
        return cards;
    }
    public int getNumCards() {
        return numCards;
    }
    public ArrayList<Card> getReserves() {
        return reserves;
    }


    public void updateScore(int newScore){ score = newScore;}

    /**
     * add a new card to the player's own cards
     * @param newCard
     */
    public void addNewCard(Card newCard){
        Gem gemType = newCard.getTargetGem();
        String gemName = gemType.getGemName();
        switch (gemName) {
            case "Diamond":
                cards.diamond+=1;
                break;
            case "Emerald":
                cards.emerald+=1;
                break;
            case "Onyx":
                cards.onyx+=1;
                break;
            case "Ruby":
                cards.ruby+=1;
                break;
            case "Sapphire":
                cards.sapphire+=1;
                break;
        }
        this.score += newCard.getCardScore();
    }


    /**
     * Buy a new card
     * @param newCard
     * @param isReserved whether the card is reserved or not
     * @return whether the player can buy the card
     */
    public boolean buyCard(Card newCard, boolean isReserved){
        if(isReserved && !reserves.contains(newCard)){
            return false;
        }
        GemInfo requiredGem = newCard.getDevelopmentCost();
        GemInfo restGem = newCard.getDevelopmentCost();
        //requiredGem is the gem we need to pay for this card
        reduceGems(requiredGem,this.cards);
        //restGem is the number of gems we still need to buy this card when we spend all of our gems
        reduceGems(restGem, this.cards);
        reduceGems(restGem,this.gems);

        //if the restGem is negative, then we have enough this kind of gem, so set it to 0.
        restGem.setGems(max(restGem.diamond, 0), max(restGem.emerald, 0), max(restGem.onyx, 0), max(restGem.ruby, 0), max(restGem.sapphire, 0));
        requiredGem.setGems(max(requiredGem.diamond, 0), max(requiredGem.emerald, 0), max(requiredGem.onyx, 0), max(requiredGem.ruby, 0), max(requiredGem.sapphire, 0));

        //if the restGem is smaller than the number of golds, we can use golds to pay the rest gems.
        if( restGem.diamond + restGem.emerald + restGem.onyx + restGem.ruby + restGem.sapphire <= golds ) {
            reduceGems(this.gems, requiredGem);
            this.golds -= (restGem.diamond + restGem.emerald + restGem.onyx + restGem.ruby + restGem.sapphire);
            board.changeGold(restGem.diamond + restGem.emerald + restGem.onyx + restGem.ruby + restGem.sapphire);
            reduceGems(requiredGem,restGem);
            board.changeGem(requiredGem);
            this.gems.setGems(max(this.gems.diamond, 0), max(this.gems.emerald, 0), max(this.gems.onyx, 0), max(this.gems.ruby, 0), max(this.gems.sapphire, 0));
            if(isReserved){
                if(reserves.contains(newCard)) {
                    reserves.remove(newCard);
                    addNewCard(newCard);
                    return true;
                }
            }
            else {
                addNewCard(newCard);
                return true;
            }
        }
        return false;
    }

    public boolean canReserveCard() {
        //exceed the limit for reservation !
        if(this.reserves.size()>= MAX_RESERVED_CARDS){
            //TODO: Exception
            return false;
        }
        //no more gold available
        else if(this.board.getAvailableGolds()<=0) {
            return false;
        }
        //exceed the limit of gems you can hold
        else if(gems.GemTotalNum()+golds >= MAX_HOLD_GEMS){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Reserved a specified card in this round by the player.
     * @param newCard the card chose to be reserved
     */
    public boolean reserveCard(Card newCard){
        //exceed the limit for reservation !
        if(canReserveCard()){
            newCard.setReserved();
            this.reserves.add(newCard);
            this.golds++;
            this.board.reduceAvailableGolds();
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Update the gems of the current player according to the collection in this round.
     * @param collectedGems The gems collected by player in this round
     */
    public boolean collectGems(GemInfo collectedGems){
        //you can select at most three gems at a time
        if(collectedGems.GemTotalNum() > MAX_COLLECT_GEMS)
            return false;


        //player cannot hold more than 10 gems at a time
        if(collectedGems.GemTotalNum() + gems.GemTotalNum()+golds > MAX_HOLD_GEMS){
            return false;
        }

        //you can select 3 distinct gems
        if(collectedGems.GemTotalNum() == MAX_COLLECT_GEMS){
            if(collectedGems.GemMaxTypeNum() >= MAX_SAME_TYPE_GEMS)
                return false;
        }

        //or two same gems if there are more than 4 gems of that type on the board
        if(collectedGems.GemTotalNum() == MAX_SAME_TYPE_GEMS){
            for(int i = 1; i <= 5; i++) {
                if(collectedGems.getByIndex(i) == MAX_SAME_TYPE_GEMS) {
                    if (this.board.availableGem.getByIndex(i) < MIN_SAME_TYPE_GEMS_ON_BOARD)
                        return false;
                }
            }
        }

        //check if there are enough available gems on the board
        for(int i = 1; i <= 5; i++) {
            if(this.board.availableGem.getByIndex(i) <= 0 && collectedGems.getByIndex(i) > 0)
                return false;
        }

        combineGems(this.gems, collectedGems);
        reduceGems(this.board.availableGem, collectedGems);
        return true;
    }


    /**
     * This method is used to recruit all available nobles for a player, and add the scores of the nobles.
     */
    public void recruitAvailableNobles(){
        for(Noble noble : this.board.getNobles()){
            if(noble.satisfied(this.cards) && !noble.getIsRecruited()){
                noble.beRecruited();
                this.score += noble.getScore();
            }
        }
    }

    public String whichOperation(Game game) {
        Card cheapCard = getCheapCard(true);
        int cost = calculateCost(this, cheapCard, true);
        if (cost <= 2) {
            String command = "PURCHASE" + ' ' + String.valueOf(cheapCard.getPosition()[0]) + ' ' + String.valueOf(cheapCard.getPosition()[1])
                    + " 0";
            return command;
        }
        game.turnToNextPlayer();
        Player nextPlayer = game.currentPlayer;
        if (this.canReserveCard()) {
            Card cheapReserveCard = getCheapCard(false);
            int reservecost = calculateCost(this, cheapReserveCard, false);
            if (reservecost <= 1 || (reservecost == 2 && calculateCost(nextPlayer, cheapReserveCard, true) == 0)) {
                String command = "RESERVE" + ' ' + String.valueOf(cheapReserveCard.getPosition()[0]) + ' ' + String.valueOf(cheapReserveCard.getPosition()[1]);
                return command;
            }
        }
        if (cost == 3) {
            String command = "PURCHASE" + ' ' + String.valueOf(cheapCard.getPosition()[0]) + ' ' + String.valueOf(cheapCard.getPosition()[1])
                    + " 0";
            return command;
        }
        if (this.reserves.size() != 0) {
            Card reservedCard = getCheapReservedCard();
            int reservedCardCost = calculateCost(this, reservedCard, true);
            if( reservedCardCost <= 2 ) {
                String command = "PURCHASE" + ' ' + String.valueOf(reservedCard.getPosition()[0]) + ' ' + String.valueOf(reservedCard.getPosition()[1])
                        + " 1";
                return command;
            }
        }
        if (score <= 5 && cost == 4) {
            String command = "PURCHASE" + ' ' + String.valueOf(cheapCard.getPosition()[0]) + ' ' + String.valueOf(cheapCard.getPosition()[1])
                    + " 0";
            return command;
        }
        if (score > 5 && cost == 4 && calculateCost(nextPlayer, cheapCard, true) == 0) {
            String command = "PURCHASE" + ' ' + String.valueOf(cheapCard.getPosition()[0]) + ' ' + String.valueOf(cheapCard.getPosition()[1])
                    + " 0";
            return command;
        }
        if(MAX_HOLD_GEMS - gems.GemTotalNum() - golds == 2){
            GemInfo availableGem = game.gameBoard.getAvailableGem();
            if (availableGem.diamond >= 4) {
                String command = "COLLECT 2 0 0 0 0";
                return command;
            }
            if (availableGem.emerald >= 4) {
                String command = "COLLECT 0 2 0 0 0";
                return command;
            }
            if (availableGem.onyx >= 4) {
                String command = "COLLECT 0 0 2 0 0";
                return command;
            }
            if (availableGem.ruby >= 4) {
                String command = "COLLECT 0 0 0 2 0";
                return command;
            }
            if (availableGem.sapphire >= 4) {
                String command = "COLLECT 0 0 0 0 2";
                return command;
            }
        }
        if(MAX_HOLD_GEMS - gems.GemTotalNum() - golds >= 3) {
            return whichToCollect(game);
        }
        else {
            String command = "PURCHASE" + ' ' + String.valueOf(cheapCard.getPosition()[0]) + ' ' + String.valueOf(cheapCard.getPosition()[1])
                    + " 0";
            return command;
        }
    }

    private String whichToCollect(Game game) {
        int times = 0, threshold = 1;
        GemInfo availableGem = game.gameBoard.getAvailableGem();
        GemInfo collectGem = new GemInfo(0,0,0,0,0);
        while (times <= 3) {
            if (availableGem.diamond >= 1 && this.gems.diamond <= threshold) {
                collectGem.updateInfo(1,0,0,0,0);
                times ++;
                if(times == 3) break;
            }
            if (availableGem.emerald >= 1 && this.gems.emerald <= threshold) {
                collectGem.updateInfo(0,1,0,0,0);
                times ++;
                if(times == 3) break;
            }
            if (availableGem.onyx >= 1 && this.gems.onyx <= threshold) {
                collectGem.updateInfo(0,0,1,0,0);
                times ++;
                if(times == 3) break;
            }
            if (availableGem.ruby >= 1 && this.gems.ruby <= threshold) {
                collectGem.updateInfo(0,0,0,1,0);
                times ++;
                if(times == 3) break;
            }
            if (availableGem.sapphire >= 1 && this.gems.sapphire <= threshold) {
                collectGem.updateInfo(0,0,0,0,1);
                times ++;
                if(times == 3) break;
            }
            threshold ++;
        }
        String command = "COLLECT " + String.valueOf(collectGem.diamond) + ' ' + String.valueOf(collectGem.emerald) + ' '
                + String.valueOf(collectGem.onyx) + ' ' + String.valueOf(collectGem.ruby) + ' '
                + String.valueOf(collectGem.sapphire);
        return command;
    }

    private Card getCheapReservedCard() {
        Card cheapCard = null;
        int minCost = 200; //impossible maximum cost
        for(int i = 0; i < reserves.size(); i++){
            Card currentCard = reserves.get(i);
            int cost = calculateCost(this, currentCard, true);
            if (cost < minCost) {
                minCost = cost;
                cheapCard = currentCard;
                cheapCard.setPosition(id-1,i);
            }
        }
        return cheapCard;
    }

    public Card getCheapCard(boolean buyOrReserve) {
        Card cheapCard = null;
        int minCost = 200; //impossible maximum cost
        for(int i = 0; i < NUM_CARD_RANK; i++){
            for(int j = 0; j < NUM_CARD_PER_RANK; j++){
                int cost = calculateCost(this, this.board.getCards()[i][j], buyOrReserve);
                if (cost < minCost) {
                    minCost = cost;
                    cheapCard = this.board.getCards()[i][j];
                    cheapCard.setPosition(i,j);
                }
            }
        }
        return cheapCard;
    }



    public int calculateCost(Player player, Card card, boolean buyOrReserve) {
        GemInfo requiredGem = card.getDevelopmentCost();
        GemInfo restGem = card.getDevelopmentCost();
        //requiredGem is the gem we need to pay for this card
        reduceGems(requiredGem,player.getCards());
        //restGem is the number of gems we still need to buy this card when we spend all of our gems
        reduceGems(restGem, player.getCards());
        reduceGems(restGem,player.getGems());

        //if the restGem is negative, then we have enough this kind of gem, so set it to 0.
        restGem.setGems(max(restGem.diamond, 0), max(restGem.emerald, 0), max(restGem.onyx, 0), max(restGem.ruby, 0), max(restGem.sapphire, 0));
        requiredGem.setGems(max(requiredGem.diamond, 0), max(requiredGem.emerald, 0), max(requiredGem.onyx, 0), max(requiredGem.ruby, 0), max(requiredGem.sapphire, 0));

        //true means buy
        if(buyOrReserve) {
            //if the restGem is smaller than the number of golds, we can use golds to pay the rest gems.
            if( restGem.GemTotalNum() <= player.getGolds() ) {
                return requiredGem.GemTotalNum();
            }
            else {
                return 100; //means we can't buy this card.
            }
        }
        else {
            if( restGem.GemTotalNum()> player.getGolds() && requiredGem.GemTotalNum() < 5) {
                return restGem.GemTotalNum() - player.getGolds();
            }
            else {
                return 100; //means we can buy this card, so we don't need to reserve it.
            }
        }

    }

    /**
     * Check if the player has won
     * @return true if the player won
     */
    public final boolean hasWon(){
        return this.score >= WINNING_SCORE;
    }
}
