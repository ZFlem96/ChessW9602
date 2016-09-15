package Model.pieces;

import Model.core.Player;
import Model.core.chessBoard;

import static Model.pieces.pieceType.King;


public class king extends Pieces {
    /**
     * Class constructor
     * @param initialX: final x-coordinate for the piece
     * @param initialY: final y-coordinate for the piece
     **/
    public king(int initialX, int initialY, Color pieceColor, chessBoard board, Player player) {
        super(initialX, initialY, King, pieceColor, board, "King", player);
    }

    /**
     * Function which tells whether a move is valid for a given piece type
     * @param destinationX: final x-coordinate for the piece
     * @param destinationY: final y-coordinate for the piece
     * @return true or false depending on if the move is valid or not
     **/
    @Override
    public boolean canMove(int destinationX, int destinationY) {
        int differenceX = Math.abs(destinationX - this.boardCoordinates.x);
        int differenceY = Math.abs(destinationY - this.boardCoordinates.y);

        if((differenceX == 1 && differenceY == 1) || (differenceX == 0 && differenceY == 1) || (differenceX == 1 && differenceY == 0)) {
            return true;
        }

        //we return true here even if destination = initial position because we handle sameLocation cases in chessGame.java
        return differenceX == 0 && differenceY == 0;

    }

    /**
     * Function to get the list of moves for the piece
     * @param destinationX: final x-coordinate for the piece
     * @param destinationY: final y-coordinate for the piece
     * @return integer array containing list of moves
     **/
    @Override
    public int[][] getMoveList(int destinationX, int destinationY) {
        return new int[0][0];
    }
}
