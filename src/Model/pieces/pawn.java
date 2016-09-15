package Model.pieces;

import Model.core.Player;
import Model.core.chessBoard;
import static Model.pieces.pieceType.Pawn;

public class pawn extends Pieces {

    /**
     * Class constructor
     * @param initialX: final x-coordinate for the piece
     * @param initialY: final y-coordinate for the piece
     **/
    public pawn(int initialX, int initialY, Color pieceColor, chessBoard board, Player player) {
        super(initialX, initialY, Pawn, pieceColor, board, "Pawn", player);
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

        if(differenceX == 2 && differenceY == 0) {
            return canMoveTwoSteps(destinationX);
        }

        if(differenceX == 1 && differenceY == 0) {
            return canMoveOneStep(destinationX);
        }

        if(differenceX == 1 && differenceX == differenceY) {
            return canMoveDiagonal(destinationX, destinationY);
        }
        //testing for same final and initial coordinates and returning true because we handle this in chessGame.java explicitly
        return differenceX == 0 && differenceY == 0;

    }

    /**
     * A function to check if a pawn can move one step ahead
     * @param destinationX : x coordinate of destination
     * @return : true if it can, false otherwise
     */
    private boolean canMoveOneStep(int destinationX) {
        int differenceX = destinationX - this.boardCoordinates.x;

        if(this.getPieceColor() == Color.BLACK) {
            if(differenceX > 0) {
                return false;
            }
        } else {
            if(differenceX < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * A function to test if the pawn can move diagonal, it can only if it has to kill a piece of different color
     * @param destinationX : x coordinate of destination
     * @param destinationY : y coordinate of destination
     * @return : true if it can, false otherwise
     */
    private boolean canMoveDiagonal(int destinationX, int destinationY) {
        int differenceX = destinationX - this.boardCoordinates.x;

        if(this.getPieceColor() == Color.BLACK && differenceX > 0) return false;
        if(this.getPieceColor() == Color.WHITE && differenceX < 0) return false;

        Pieces boardPiece = this.gameBoard.board[destinationX][destinationY];

        return boardPiece != null && boardPiece.getPieceColor() != this.getPieceColor();

    }

    /**
     * A function to test if pawn can move two steps ahead, it can only if it's the pawn's first turn in the game
     * @param destinationX : x coordinate of destination
     * @return : true if it can, false otherwise
     */
    private boolean canMoveTwoSteps(int destinationX) {
        int differenceX = destinationX - this.boardCoordinates.x;

        if(!isFirstTurn) {
            System.out.println("MOVE ERROR: This is not the pawn's first move.");
        }

        if(this.getPieceColor() == Color.BLACK) {
            if(!isFirstTurn || differenceX > 0) return false;
        }else {
            if(!isFirstTurn || differenceX < 0) return false;
        }

        return true;
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
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(destinationY - this.boardCoordinates.y);
        int[][] moveList;

        if(absDifferenceX == 2 && absDifferenceY == 0) {
            moveList = new int[2][1];
            if(differenceX > 0) {
                moveList[0][0] = this.boardCoordinates.x + 1;
            } else if(differenceX < 0) {
                moveList[0][0] = this.boardCoordinates.x - 1;
            }
            moveList[1][0] = this.boardCoordinates.y;
        } else {
            moveList = new int[2][0]; //no paths
        }

        return moveList;
    }
}
