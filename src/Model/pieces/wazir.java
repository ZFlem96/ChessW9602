package Model.pieces;

import Model.core.Player;
import Model.core.chessBoard;
import static Model.pieces.pieceType.Wazir;

/**
 * A class to implement a special piece called 'wazir'. More info here: https://en.wikipedia.org/wiki/Wazir_(chess)
 * This piece can only move orthogonally, i.e., one space to left/right either horizontally or Vertically.
 */
public class wazir extends Pieces {
    public wazir(int initialX, int initialY, Color pieceColor, chessBoard board, Player player) {
        super(initialX, initialY, Wazir, pieceColor, board, "wazir", player);
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

        return (destinationX == this.boardCoordinates.x && differenceY == 1) ||
                (destinationY == this.boardCoordinates.y && differenceX == 1) ||
                (differenceX == differenceY && differenceX == 0); //we're handling start=finish coordinates separately
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
