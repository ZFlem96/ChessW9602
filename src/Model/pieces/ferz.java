package Model.pieces;

import Model.core.Player;
import Model.core.chessBoard;
import static Model.pieces.pieceType.Ferz;

/**
 * A class to implement a special piece called 'wazir'. More info here: https://en.wikipedia.org/wiki/Ferz_(chess)
 * This piece can only move one step further diagonally, basically like a bishop but only one steps in any direction
 */
public class ferz extends Pieces {
    public ferz(int initialX, int initialY, Color pieceColor, chessBoard board, Player player) {
        super(initialX, initialY, Ferz, pieceColor, board, "ferz", player);
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

        return (differenceX == differenceY &&
                (differenceX == 1 ||
                differenceX == 0));
    }

    /**
     * Function to get the list of moves for the piece
     * @param destinationX: final x-coordinate for the piece
     * @param destinationY: final y-coordinate for the piece
     * @return integer array containing list of moves
     **/
    @Override
    public int[][] getMoveList(int destinationX, int destinationY) { return new int[0][0]; }
}
