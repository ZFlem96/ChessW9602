package Model.core;

import Model.pieces.Pieces;

import java.util.ArrayList;

/**
 * This class sets up the players for our game.
 */
public class Player {
    public ArrayList<Pieces> playerPieces; //a list to hold all the Model.pieces the player is playing with
    public ArrayList<Pieces> opponentPieces; //a list to hold all the Model.pieces the opponent is playing with

    /**
     * Class constructor
     */
    public Player() {
        playerPieces = new ArrayList<>();
        opponentPieces = new ArrayList<>();
    }
}
