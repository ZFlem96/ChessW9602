package Model.pieces;

import Model.core.Player;
import Model.core.chessBoard;


public abstract class Pieces {
    public coordinates boardCoordinates; //board coordinates a piece
    private pieceType type; //type of the piece, (SEE pieceType CLASS)
    private Color pieceColor; //color of the piece
    chessBoard gameBoard;
    public Player player;
    private String pieceName = null; //for printing purposes
    public boolean isFirstTurn = true; //used only for pawns, as they can move two steps on first turn
    public int howManyTimesPlayed = 0; //used only for pawns, as if a move is undone we want to restore the ability to move piece twice again if it was it's first turn

    /**
     * Class constructor
     * @param initialX: final x-coordinate for the piece
     * @param initialY: final y-coordinate for the piece
     * @param type: specific piece type
     **/
    Pieces(int initialX, int initialY, pieceType type, Color pieceColor, chessBoard board, String pieceName, Player player) {
        boardCoordinates = new coordinates(initialX, initialY);
        this.setPieceType(type);
        this.setPieceColor(pieceColor);
        gameBoard = board;
        this.pieceName = pieceName;
        this.player = player;
    }
    
    public Pieces() {}

    /**
     * Function which tells whether a move is valid for a given piece type
     * @param destinationX: final x-coordinate for the piece
     * @param destinationY: final y-coordinate for the piece
     * @return true or false depending on if the move is valid or not
     **/
    public abstract boolean canMove(int destinationX, int destinationY);

    /**
     * Function to get the list of moves for the piece
     * @param destinationX: final x-coordinate for the piece
     * @param destinationY: final y-coordinate for the piece
     * @return integer array containing list of moves
     **/
    public abstract int[][] getMoveList(int destinationX, int destinationY);

    /* setter for piece color */
    private void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
    }

    /* getter for piece color */
    public Color getPieceColor() {
        return pieceColor;
    }

    /* setter for piece type */
    public void setPieceType(pieceType type) {
        this.type = type;
    }

    /* getter for piece type */
    public pieceType getPieceType() {
        return type;
    }

    /* overriding toString method to print out a human readable version of piece object */
    @Override
    public String toString() {
        if(getPieceColor() == Color.WHITE) return "w" + pieceName;
        else return "b" + pieceName;
    }

}
