package Model.pieces;

import Model.core.Player;
import Model.core.chessBoard;
import static Model.pieces.pieceType.Queen;

public class queen extends Pieces{
    /**
     * Class constructor
     * @param initialX: final x-coordinate for the piece
     * @param initialY: final y-coordinate for the piece
     **/
    public queen(int initialX, int initialY, Color pieceColor, chessBoard board, Player player) {
        super(initialX, initialY, Queen, pieceColor, board, "Queen", player);
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
        return differenceX == differenceY || (destinationX == this.boardCoordinates.x || destinationY == this.boardCoordinates.y);

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
        int numberOfPossibleMoves = 0;
        int directionX = 0, directionY = 0;
        int[][] moveList;

        if(Math.abs(differenceX) == Math.abs(differenceY)) { //diagonal case
            numberOfPossibleMoves = Math.abs(differenceX);

            directionX = 1;
            directionY = 1;

            if(differenceX < 0) directionX = -1;
            if(differenceY < 0) directionY = -1;

            moveList = new int[2][numberOfPossibleMoves - 1];

            for(int i=0; i<numberOfPossibleMoves - 1; i++) {
                moveList[0][i] = this.boardCoordinates.x + directionX;
                moveList[1][i] = this.boardCoordinates.y + directionY;
            }

            return moveList;
        } else { //vertical or horizontal case
            if(differenceX < 0) {
                directionX = -1;
            } else if(differenceX > 0) {
                directionX = 1;
            }
            if(differenceY < 0) {
                directionY = -1;
            } else if(differenceY > 0) {
                directionY = 1;
            }

            if(destinationY == this.boardCoordinates.y) {
                numberOfPossibleMoves = Math.abs(differenceX);
            } else if(destinationX == this.boardCoordinates.x) {
                numberOfPossibleMoves = Math.abs(differenceY);
            }

            moveList = new int[2][numberOfPossibleMoves - 1];

            for(int i=0; i < numberOfPossibleMoves - 1; i++) {
                moveList[0][i] = this.boardCoordinates.x + directionX;
                moveList[1][i] = this.boardCoordinates.y + directionY;
            }

            return moveList;
        }
    }

}
