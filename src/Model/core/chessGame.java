package Model.core;

import Model.pieces.Pieces;
import Model.pieces.coordinates;

import java.util.ArrayList;
import static Model.pieces.Color.*;
import static Model.pieces.pieceType.*;

/**
 * This class is the meat of the game. It handles all the game logic like leaping, checkmate, check conditions etc., it first
 * sets up the board using the chessBoard class and then handles the move logic before actually moving a piece.
 */
public class chessGame {
    public chessBoard chess;
    private boolean isWhiteTurn = true; //initial game condition, white piece always goes first
    public boolean isBlackTurn = false;
    public String errorMessage = null; //error message used to show errors to the players in the GUI
    //the following variables are used alongside the GUI
    public String playerOneName; //name of player one (white)
    public String playerTwoName; //name of player two (black)
    //source piece, used for undo
    private Pieces sourcePiece;
    private coordinates sourcePieceCoordinate;
    //destination piece, user for undo
    private Pieces destinationPiece;
    private coordinates destinationPieceCoordinate;
    public String endGameMessage = null;
    private boolean checkForTurns = true;

    /**
     * Class constructor
     * @param isAlternatePiece : 1 if regular board configuration needed, 0 if fairy piece board configuration needed
     */
    public chessGame(int isAlternatePiece) {
        chess = new chessBoard(isAlternatePiece);
    }

    /**
     * A function to move a piece to the destination. It does the following:
     * 1) Check whether a move is even valid for a piece.
     * 2) If it is, check the move constraints to move from current piece coordinates to the destination.
     * 3) Finally, if everything's good. Change the piece's location.
     * @param piece : The piece to be moved
     * @param destinationX : x coordinate of destination
     * @param destinationY : Y coordinate of destination
     */
    public void movePieceTo(Pieces piece, int destinationX, int destinationY) {
        if (piece.canMove(destinationX, destinationY)) {
            if (checkMoveConstraints(piece, destinationX, destinationY)) {
                 setPieceLocation(piece, destinationX, destinationY);
            }
        } else {
            System.out.println("MOVE ERROR: This is not a valid move for the piece.");
            errorMessage = "This is not a valid move for the piece.";
        }
    }

    /**
     * A function to tests the following constraints on any move:
     * 1) if piece color is black and its NOT black piece's turn, don't move it.
     * 2) if piece color is white and its NOT white piece's turn, don't move it.
     * 3) if destination is same as origin, don't move it.
     * 4) if piece cannot leap over another piece on its path, don't move it.
     * 5) if the destination is not within board bounds, don't move the piece.
     * 6) if destination makes a piece land on another of the same color, don't move it.
     * @param piece : piece to be moved
     * @param destinationX : x coordinate of destination
     * @param destinationY : y coordinate of destination
     * @return : true if piece can be moved, false otherwise
     */
    public boolean checkMoveConstraints(Pieces piece, int destinationX, int destinationY) {
        //check if player turns are correct
        if(checkForTurns) {
            if (piece.getPieceColor() == BLACK && !isBlackTurn) {
                System.out.println("MOVE ERROR: It's white piece's turn.");
                errorMessage = "It's " + playerOneName + "'s turn.";
                return false;
            } else if (piece.getPieceColor() == WHITE && !isWhiteTurn) {
                System.out.println("MOVE ERROR: It's black piece's turn.");
                errorMessage = "It's " + playerTwoName + "'s turn.";
                return false;
            }
        }
        //if destination is same as current coordinate, render move as invalid
        if(destinationX == piece.boardCoordinates.x && destinationY == piece.boardCoordinates.y) {
            System.out.println("MOVE ERROR: current and final coordinates are same.");
            errorMessage = "Current and final coordinates are same.";
            return false;
        }
        else {
            if(canLeap(piece, destinationX, destinationY) && isInBounds(destinationX, destinationY) &&
                    isDestinationOnSamePieceColor(piece, destinationX, destinationY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A function to test if a piece can leap over other piece's on its path.
     * @param piece : piece to be moved
     * @param destinationX : x coordinate of destination
     * @param destinationY : y coordinate of destination
     * @return : true if can leap, false otherwise
     */
    public boolean canLeap(Pieces piece, int destinationX, int destinationY) {
        //Knights are allowed to leap, Kings, Wazir and Ferz have no path so no leaping possibility
        if(piece.getPieceType() == Knight || piece.getPieceType() == King
                || piece.getPieceType() == Wazir || piece.getPieceType() == Ferz) return true;

        int[][] moveList = piece.getMoveList(destinationX, destinationY);

        //if any square on the board, in the moveList of piece is not null, leaping is involved
        for(int i=0; i<moveList[0].length; i++){
            if(chess.board[moveList[0][i]][moveList[1][i]] != null){
                System.out.println("MOVE ERROR: invalid leap attempt.");
                errorMessage = "Invalid leap attempt.";
                return false;
            }
        }

        //squares in the moveList are empty, return true
        return true;
    }

    /**
     * check if destination coordinates are within board bounds.
     * @param destinationX : x coordinate of destination
     * @param destinationY : y coordinate of destination
     * @return : true if within bounds, false otherwise
     */
    public boolean isInBounds(int destinationX, int destinationY) {
        //check for origin
        if(0 <= destinationX && 0 <= destinationY) {
            //check for extreme ends
            if(destinationX < chessBoard.ranks && destinationY < chessBoard.files) {
                return true;
            }

        }
        System.out.println("MOVE ERROR: your move is out of board bounds.");
        errorMessage = "Your move is out of board bounds.";
        return false;
    }

    /**
     * A function which tests whether destination makes a piece move on another of the same color.
     * @param piece : piece to be moved
     * @param destinationX : x coordinate of destination
     * @param destinationY : y coordinate of destination
     * @return : true if it's NOT the same color, false otherwise
     */
    public boolean isDestinationOnSamePieceColor(Pieces piece, int destinationX, int destinationY) {
        //check whether destination is empty or not
        if(chess.board[destinationX][destinationY] != null) {
            //if not, check for color
            if (piece.getPieceColor() == chess.board[destinationX][destinationY].getPieceColor()) {
                System.out.println("MOVE ERROR: your move makes the piece land on another piece of same color.");
                errorMessage = "Your move makes the piece land on another piece of same color.";
                return false;
            }
        }
        return true;
    }

    /**
     * A function which actually changes the location of a piece.
     * @param piece : piece which is being moved
     * @param destinationX : x coordinate of destination
     * @param destinationY : y coordinate of destination
     */
    private void setPieceLocation(Pieces piece, int destinationX, int destinationY) {

        endGameMessage = null;
        //during checks for stalemate, checkmate and check, we turn off the checks for player turns
        checkForTurns = false;

        sourcePiece = piece;
        sourcePieceCoordinate = new coordinates(piece.boardCoordinates.x, piece.boardCoordinates.y);

        //make the piece coordinates on the board = null
        if(piece.getPieceType() != King) chess.board[piece.boardCoordinates.x][piece.boardCoordinates.y] = null;

        Pieces whiteKing = chess.getKing(WHITE);
        Pieces blackKing = chess.getKing(BLACK);

        // ======================================= CHECK IF MOVE PLACES KING IN CHECK ============================================== //
        //check if the move places king in check, if it does, don't move!
        if((piece.getPieceColor() == WHITE && isKingInCheck(whiteKing, whiteKing.boardCoordinates.x, whiteKing.boardCoordinates.y)) ||
                (piece.getPieceColor() == BLACK && isKingInCheck(blackKing, blackKing.boardCoordinates.x, blackKing.boardCoordinates.y))) {
            System.out.println("MOVE ERROR: Your move places your king in check.");
            errorMessage = "Your move places you king in check.";
            chess.board[piece.boardCoordinates.x][piece.boardCoordinates.y] = piece;
            checkForTurns = true;
            return;
        }


        if(piece.getPieceType() == King) {
            int kingCoordinateX = piece.boardCoordinates.x;
            int kingCoordinateY = piece.boardCoordinates.y;

            chess.board[piece.boardCoordinates.x][piece.boardCoordinates.y] = null;
            piece.boardCoordinates.x = destinationX;
            piece.boardCoordinates.y = destinationY;
            chess.board[destinationX][destinationY] = piece;


            //check if move places king in check
            if(isKingInCheck(piece, piece.boardCoordinates.x, piece.boardCoordinates.y)) {
                errorMessage = "Your move places your king in check.";
                System.out.println("Your move places your king in check.");

                //revert back to old configuration
                chess.board[piece.boardCoordinates.x][piece.boardCoordinates.y] = null;
                piece.boardCoordinates.x = kingCoordinateX;
                piece.boardCoordinates.y = kingCoordinateY;
                chess.board[kingCoordinateX][kingCoordinateY] = piece;
                checkForTurns = true;
                return;
            }

            //revert back to old configuration
            chess.board[piece.boardCoordinates.x][piece.boardCoordinates.y] = null;
            piece.boardCoordinates.x = kingCoordinateX;
            piece.boardCoordinates.y = kingCoordinateY;
            chess.board[kingCoordinateX][kingCoordinateY] = piece;

        }


        if(piece.getPieceType() == King) chess.board[piece.boardCoordinates.x][piece.boardCoordinates.y] = null;

        //if it's the players first move, toggle it to false
        if(piece.getPieceType() == Pawn && piece.isFirstTurn) {
            piece.isFirstTurn = false;
            piece.howManyTimesPlayed++;
        }

        //toggle turns of black and white pieces
        toggleTurns();

        //remove the player which is being killed from the appropriate arrayLists
        if(chess.board[destinationX][destinationY] != null) {
            Pieces removePiece = chess.board[destinationX][destinationY];
            if (removePiece.getPieceColor() == WHITE) {
                chess.player1.playerPieces.remove(removePiece);
                chess.player2.opponentPieces.remove(removePiece);
            } else if(removePiece.getPieceColor() == BLACK){
                chess.player2.playerPieces.remove(removePiece);
                chess.player1.opponentPieces.remove(removePiece);
            }
        }

        //update the piece's new coordinates
        piece.boardCoordinates.x = destinationX;
        piece.boardCoordinates.y = destinationY;

        //update destination piece and coordinates for undo functions
        destinationPiece = chess.board[destinationX][destinationY];
        destinationPieceCoordinate = new coordinates(destinationX, destinationY);

        //place the piece at its new location
        chess.board[destinationX][destinationY] = piece;

        //get kings back
        whiteKing = chess.getKing(WHITE);
        blackKing = chess.getKing(BLACK);

        //check for check
        if(isKingInCheck(whiteKing, whiteKing.boardCoordinates.x, whiteKing.boardCoordinates.y)
                && !isKingInCheckmate(whiteKing)) {
            errorMessage = "white king in check";
            System.out.println("ALERT: white king in check");
            checkForTurns = true;
            return;
        } else if(isKingInCheck(blackKing, blackKing.boardCoordinates.x, blackKing.boardCoordinates.y) &&
                !isKingInCheckmate(blackKing)) {
            errorMessage = "black king in check";
            System.out.println("ALERT: black king in check");
            checkForTurns = true;
            return;
        }

        //check for checkmate
        if(isKingInCheckmate(whiteKing)) {
            endGameMessage = "white king is in checkmate, player2 won, do you want to play again?";
            System.out.println("ALERT: white king is in checkmate, player2 won. Do you want to play again?");
            chess.player2.score++;
            checkForTurns = true;
            return;
        } else if(isKingInCheckmate(blackKing)) {
            endGameMessage = "black king is in checkmate, player1 won, do you want to play again?";
            System.out.println("ALERT: black king is in checkmate, player1 won. Do you want to play again?");
            chess.player2.score++;
            chess.player1.score++;
            checkForTurns = true;
            return;
        }

        //we do need to check for player turns, turn it back on
        checkForTurns = true;

        //no errors if piece moved okay!
        errorMessage = null;
    }

    /**
     * A function which toggle's the turns of white and black player
     */
    private void toggleTurns() {
        isWhiteTurn = !isWhiteTurn;
        isBlackTurn = !isBlackTurn;
    }

    /**
     * A function to give the ability to a user to undo his last move.
     */
    public void undoLastMove() {
        //undo piece movements
        chess.board[sourcePieceCoordinate.x][sourcePieceCoordinate.y] = sourcePiece;
        chess.board[sourcePieceCoordinate.x][sourcePieceCoordinate.y].boardCoordinates.x = sourcePieceCoordinate.x;
        chess.board[sourcePieceCoordinate.x][sourcePieceCoordinate.y].boardCoordinates.y = sourcePieceCoordinate.y;

        chess.board[destinationPieceCoordinate.x][destinationPieceCoordinate.y] = destinationPiece;


        //if destination piece was not null, meaning someone was killed, do book-keeping of updating player arrayLists
        if(destinationPiece != null) {
            chess.board[destinationPieceCoordinate.x][destinationPieceCoordinate.y].boardCoordinates.x = destinationPieceCoordinate.x;
            chess.board[destinationPieceCoordinate.x][destinationPieceCoordinate.y].boardCoordinates.y = destinationPieceCoordinate.y;

            if(destinationPiece.getPieceColor() == BLACK) {
                chess.player1.opponentPieces.add(destinationPiece);
                chess.player2.playerPieces.add(destinationPiece);
            } else if(destinationPiece.getPieceColor() == WHITE){
                chess.player1.playerPieces.add(destinationPiece);
                chess.player2.opponentPieces.add(destinationPiece);
            }
        }

        //toggle turns of black and white pieces
        toggleTurns();

        //restore the ability of a pawn to move twice on first turn
        if(sourcePiece.getPieceType() == Pawn && sourcePiece.howManyTimesPlayed == 1) {
            sourcePiece.isFirstTurn = true;
        }
    }

    // ======================================= GAME ENDING CONDITIONS ============================================== //

    /**
     * A function which checks whether king is in check using the following technique:
     * 1) If any opponent piece can move to the king's location by satisfying ALL the move constraints then YES, king is in check
     * @param king : king to be checked for check condition
     * @param coordinateX : x coordinate of king
     * @param coordinateY : y coordinate of king
     * @return : true if king is in check, false otherwise
     */
    public boolean isKingInCheck(Pieces king, int coordinateX, int coordinateY) {
        Player player = king.player;
        ArrayList<Pieces> opponentPieces = player.opponentPieces;

        for (Pieces opponentPiece : opponentPieces) {
            if (opponentPiece.canMove(coordinateX, coordinateY) &&
                    checkMoveConstraints(opponentPiece, coordinateX, coordinateY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A function to check whether a king is in checkmate or not by doing the following:
     * 1) the king HAS TO BE in check
     * 2) If it is, if the king CANNOT move at all AND no ally piece can defend it, its in checkmate.
     * 3) Else, its NOT.
     * @param king : king to be tested
     * @return : true if its in checkmate, false otherwise
     */
    public boolean isKingInCheckmate(Pieces king) {
        //check first if king is in check
        if(isKingInCheck(king, king.boardCoordinates.x, king.boardCoordinates.y)) {
            //now if king cannot move and cannot be defended by any piece of same color then it is in checkmate
            if(!canKingMove(king) && !canBeDefended(king)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function to check whether a king can move to valid locations.
     * @param king : king to be tested
     * @return : true if yes, false otherwise
     */
    private boolean canKingMove(Pieces king) {
        return kingMovementValidator(king, king.boardCoordinates.x + 1, king.boardCoordinates.y + 1) ||
                kingMovementValidator(king, king.boardCoordinates.x + 1, king.boardCoordinates.y) ||
                kingMovementValidator(king, king.boardCoordinates.x + 1, king.boardCoordinates.y - 1) ||
                kingMovementValidator(king, king.boardCoordinates.x, king.boardCoordinates.y - 1) ||
                kingMovementValidator(king, king.boardCoordinates.x, king.boardCoordinates.y + 1) ||
                kingMovementValidator(king, king.boardCoordinates.x - 1, king.boardCoordinates.y - 1) ||
                kingMovementValidator(king, king.boardCoordinates.x - 1, king.boardCoordinates.y) ||
                kingMovementValidator(king, king.boardCoordinates.x - 1, king.boardCoordinates.y + 1);
    }

    /**
     * A function to test if a king can move to the given location by testing:
     * 1) Whether the location is a valid one or not.
     * 2) If it is, check if it satisfies all the move constraints.
     * 3) If yes, check if by moving to that spot does the king go in check or not.
     * @param king : the king to be tested
     * @param coordinateX : x coordinate of destination
     * @param coordinateY : y coordinate of destination
     * @return : true if it can move, false otherwise.
     */
    private boolean kingMovementValidator(Pieces king, int coordinateX, int coordinateY) {
        return king.canMove(coordinateX, coordinateY) && checkMoveConstraints(king, coordinateX, coordinateY)
                && !isKingInCheck(king, coordinateX, coordinateY);
    }

    /**
     * A function to test whether a king in check can be defended by other Model.pieces of the same color by:
     * 1) First, get a list of all the opponent piece's which put the king in check.
     * 2) Now get a list of moves of all these opponent Model.pieces.
     * 3) Iterate  through this move list and check whether any of the player piece can move to any coordinate within that
     * move list.
     * @param king : the king to be tested
     * @return : true if it can defended, false otherwise.
     */
    private boolean canBeDefended(Pieces king) {
        Player player = king.player;
        ArrayList<Pieces> opponentPieces = player.opponentPieces;
        ArrayList<Pieces> playerPieces = player.playerPieces;
        ArrayList<Pieces> attackingPieces = new ArrayList<>();
        int attackingPieceIndex = 0;
        int kingCoordinateX = king.boardCoordinates.x;
        int kingCoordinateY = king.boardCoordinates.y;

        //form the attacking piece array, an arrayList which holds the Model.pieces which are putting the king in check
        for (Pieces opponentPiece : opponentPieces) {
            if (opponentPiece.canMove(kingCoordinateX, kingCoordinateY) &&
                    checkMoveConstraints(opponentPiece, kingCoordinateX, kingCoordinateY)) {
                attackingPieces.add(attackingPieceIndex, opponentPiece);
                attackingPieceIndex++;
            }
        }

        for (Pieces attackingPiece : attackingPieces) {
            int[][] moveList = attackingPiece.getMoveList(kingCoordinateX, kingCoordinateY);
            //iterate through the whole moveList to analyze each (x,y) pair
            for (int j = 0; j < moveList[0].length; j++) {
                //now iterate through the playerPieces and check if any of them have a valid path to (x,y) pair in moveList
                for (Pieces playerPiece : playerPieces) {
                    //if the piece can move to any location within the moveList, it can defend the king
                    if (playerPiece.canMove(moveList[0][j], moveList[1][j]) && checkMoveConstraints(playerPiece, moveList[0][j], moveList[1][j])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * A function which detects a stalemate condition. This is how -
     * A stalemate condition arises when there are no legal moves and the moving-side's king is not in check, so we first check
     * if the king is in check, if it's not then we check if the can king can move, if it cannot and it also cannot be defended by
     * any other piece of the same color, we have a stalemate condition!
     * @param king : king tested for an overall stalemate condition
     * @return : true if stalemate, false otherwise
     */
    public boolean isStalemate(Pieces king) {
        //check first if king is in check
        if(!isKingInCheck(king, king.boardCoordinates.x, king.boardCoordinates.y)) {
            //now if king cannot move and cannot be defended by any piece of same color then we have stalemate
            if(!canKingMove(king) && !canBeDefended(king)) {
                return true;
            }
        }
        return false;
    }
}
