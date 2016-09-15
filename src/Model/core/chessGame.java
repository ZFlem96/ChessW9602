package Model.core;

import Model.pieces.Pieces;
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
    private boolean isBlackTurn = false;

    /**
     * Class constructor
     */
    public chessGame() {
        chess = new chessBoard();
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
        if(piece.getPieceColor() == BLACK && !isBlackTurn) {
            System.out.println("MOVE ERROR: It's white piece's turn.");
            return false;
        } else if(piece.getPieceColor() == WHITE && !isWhiteTurn) {
            System.out.println("MOVE ERROR: It's black piece's turn.");
            return false;
        }

        //if destination is same as current coordinate, render move as invalid
        if(destinationX == piece.boardCoordinates.x && destinationY == piece.boardCoordinates.y) {
            System.out.println("MOVE ERROR: current and final coordinates are same.");
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
        if(piece.getPieceType() == Knight || piece .getPieceType() == King
                || piece.getPieceType() == Wazir || piece.getPieceType() == Ferz) return true;

        int[][] moveList = piece.getMoveList(destinationX, destinationY);

        //if any square on the board, in the moveList of piece is not null, leaping is involved
        for(int i=0; i<moveList[0].length; i++){
            if(chess.board[moveList[0][i]][moveList[1][i]] != null){
                System.out.println("MOVE ERROR: invalid leap attempt.");
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
        //make the piece coordinates on the board = null
        chess.board[piece.boardCoordinates.x][piece.boardCoordinates.y] = null;

        Pieces whiteKing = chess.getKing(WHITE);
        Pieces blackKing = chess.getKing(BLACK);

        //check if the move places king in check, if it does, don't move!
        if((piece.getPieceColor() == WHITE && isKingInCheck(whiteKing, whiteKing.boardCoordinates.x, whiteKing.boardCoordinates.y)) ||
                (piece.getPieceColor() == BLACK && isKingInCheck(blackKing, blackKing.boardCoordinates.x, blackKing.boardCoordinates.y))) {
            System.out.printf("MOVE ERROR: Your move places you king in check.");
            chess.board[piece.boardCoordinates.x][piece.boardCoordinates.y] = piece;
            return;
        }

        //if it's the players first move, toggle it to false
        if(piece.getPieceType() == Pawn && piece.isFirstTurn) {
            piece.isFirstTurn = false;
        }

        //toggle turns of black and white Model.pieces
        isWhiteTurn = !isWhiteTurn;
        isBlackTurn = !isBlackTurn;

        //remove the player which is being killed from the appropriate arrayLists
        if(piece.getPieceColor() == WHITE) {
            chess.player1.playerPieces.remove(piece);
            chess.player2.opponentPieces.remove(piece);
        } else {
            chess.player2.playerPieces.remove(piece);
            chess.player1.opponentPieces.remove(piece);
        }

        piece.boardCoordinates.x = destinationX;
        piece.boardCoordinates.y = destinationY;

        chess.board[destinationX][destinationY] = piece;

        System.out.println("FATALITY!");
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
//        if(coordinateX < 0 || coordinateY < 0) return false;
//        if(coordinateX >= chess.files || coordinateY >= chess.ranks) return false;

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
