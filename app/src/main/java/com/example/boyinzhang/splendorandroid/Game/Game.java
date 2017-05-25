package com.example.boyinzhang.splendorandroid.Game;

import com.example.boyinzhang.splendorandroid.Model.*;

import java.util.ArrayList;

import static com.example.boyinzhang.splendorandroid.Model.utils.GameUtils.*;

/**
 * Created by boyinzhang on 5/25/17.
 */

public class Game {
    public Player[] players;
    public Player currentPlayer;
    public Board gameBoard;

    public Game(){
        this.gameBoard = new Board(NUM_PLAYER);
        this.gameBoard.initialBoard();
        this.players = new Player[NUM_PLAYER];

        for(int i = 0; i < NUM_PLAYER; i++){
            players[i] = new Player(i+1 , this.gameBoard, null);
        }
        currentPlayer = players[0];
    }

    public Game(ArrayList<String> names){
        this.gameBoard = new Board(NUM_PLAYER);
        this.gameBoard.initialBoard();
        this.players = new Player[NUM_PLAYER];

        for(int i = 0; i < NUM_PLAYER; i++){
            players[i] = new Player(i+1 , this.gameBoard, names.get(i));
        }
        currentPlayer = players[0];
    }

    public Board getGameBoard(){
        return this.gameBoard;
    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public Player[] getPlayers(){return players;}

    /**
     * Used to check if the someone won.
     * @return
     */


    public final int checkEndofGame(){
        int numberOfWining = 0;
        for(Player player: this.players){
            if(player.hasWon()){
                numberOfWining += 1;
            }
        }
        return numberOfWining;
    }

    /**
     * Turn the next player
     */

    public void turnToNextPlayer(){
        int currentId = currentPlayer.getPlayerId();
        if(currentId == NUM_PLAYER){
            currentId = 0;
        }
        currentPlayer = this.players[currentId];
    }


}
