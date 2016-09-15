package Model.testSuite;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;
import Model.pieces.*;
import static Model.pieces.Color.*;

public class pawnTests {

    //move up by one step
    @Test
    public void  upOneStep() {
        chessGame newGame = new chessGame();
        Pieces pawn = newGame.chess.board[1][0];
        newGame.movePieceTo(pawn, 2, 0);
        assertEquals(newGame.chess.board[2][0], pawn);
    }

    //move down by one step
    @Test
    public void downOneStep() {
        chessGame newGame = new chessGame();
        Pieces pawn = new pawn(3, 0, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][0] = pawn;
        newGame.movePieceTo(pawn, 2, 0);

        assertEquals(newGame.chess.board[3][0], pawn);
    }

    //move up by two steps
    @Test
    public void upTwoSteps() {
        chessGame newGame = new chessGame();
        Pieces pawn = newGame.chess.board[1][0];
        newGame.movePieceTo(pawn, 3, 0);

        assertEquals(newGame.chess.board[3][0], pawn);
    }

    //test when it's not the first turn, a pawn cannot move up by two steps
    @Test
    public void cannotMoveUpTwoSteps() {
        chessGame newGame = new chessGame();
        Pieces pawn = newGame.chess.board[1][0];
        pawn.isFirstTurn = false;
        newGame.movePieceTo(pawn, 3, 0);

        assertEquals(newGame.chess.board[1][0], pawn);
    }

    //test movement diagonally by killing other piece
    @Test
    public void canKillDiagonally() {
        chessGame newGame = new chessGame();
        Pieces bishop = new bishop(2, 1, BLACK, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][1] = bishop;
        Pieces pawn = newGame.chess.board[1][0];
        newGame.movePieceTo(pawn, 2, 1);

        assertEquals(newGame.chess.board[2][1], pawn);
    }

    //test movement diagonally when pawn is not killing other piece
    @Test
    public void cannotKillDiagonally() {
        chessGame newGame = new chessGame();
        Pieces pawn = newGame.chess.board[1][0];
        newGame.movePieceTo(pawn, 2, 1);

        assertEquals(newGame.chess.board[1][0], pawn);
    }

    //test destination=initial location
    @Test
    public void sameLocation() {
        chessGame newGame = new chessGame();
        Pieces pawn = newGame.chess.board[1][0];
        newGame.movePieceTo(pawn, 1, 0);

        assertEquals(newGame.chess.board[1][0], pawn);
    }

    //test if pawn can leap
    @Test
    public void canLeap() {
        chessGame newGame = new chessGame();
        Pieces pawn = newGame.chess.board[1][0];
        Pieces knight = new knight(2, 0, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][0] = knight;
        newGame.movePieceTo(pawn, 3, 0);

        assertEquals(newGame.chess.board[1][0], pawn);
    }

    //test if destination is in board bounds
    @Test
    public void isInBounds() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.isInBounds(9, 9));
    }

    //test if destination is on a piece of same color
    @Test
    public void isDestinationOnSamePieceColor() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(2, 0, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][0] = knight;

        assertFalse(newGame.isDestinationOnSamePieceColor(newGame.chess.board[1][0], 2, 0));
    }
}
