package Model.pieces;

import Model.core.Player;
import Model.core.chessBoard;
import static Model.pieces.pieceType.Bishop;

public class bishop extends Pieces{

    /**
     * Class constructor
     * @param initialX: final x-coordinate for the piece
     * @param initialY: final y-coordinate for the piece
     **/
    public bishop(int initialX, int initialY, Color pieceColor, chessBoard board, Player player) {
        super(initialX, initialY, Bishop, pieceColor, board, "Bishop", player);
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

        return differenceX == differenceY;
    }

    /**
     * Function to get the list of moves for the piece
     * @param destinationX: final x-coordinate for the piece
     * @param destinationY: final y-coordinate for the piece
     * @return integer array containing list of moves
     **/
    @Override
    public int[][] getMoveList(int destinationX, int destinationY) {
        int differenceX = destinationX - this.boardCoordinates.x;
        int differenceY = destinationY - this.boardCoordinates.y;

        int numberOfPossibleMoves = Math.abs(differenceX);

        int directionX = 1, directionY = 1;

        if(differenceX < 0) directionX = -1;
        if(differenceY < 0) directionY = -1;

        int[][] moveList = new int[2][numberOfPossibleMoves - 1];

        for(int i=0; i<numberOfPossibleMoves - 1; i++) {
            moveList[0][i] = this.boardCoordinates.x + directionX;
            moveList[1][i] = this.boardCoordinates.y + directionY;
        }

        return moveList;
    }

}
