package Model.testSuite;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;
import Model.pieces.*;
import static Model.pieces.Color.*;


public class bishopTests {

    //up right movement
    @Test
    public void upRight() {
        chessGame newGame = new chessGame(1, false);
        Pieces bishop = new bishop(2, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][1] = bishop;
        newGame.movePieceTo(bishop, 5, 4);

        assertEquals(bishop, newGame.chess.board[5][4]);
    }

    //up and left movement
    @Test
    public void upLeft() {
        chessGame newGame = new chessGame(1, false);
        Pieces bishop = new bishop(2, 4, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][4] = bishop;
        newGame.movePieceTo(bishop, 5, 1);

        assertEquals(bishop, newGame.chess.board[5][1]);
    }

    //down and right movement
    @Test
    public void downRight() {
        chessGame newGame = new chessGame(1, false);
        Pieces bishop = new bishop(5, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][1] = bishop;
        newGame.movePieceTo(bishop, 2, 4);

        assertEquals(bishop, newGame.chess.board[2][4]);
    }

    //down and left movement
    @Test
    public void downLeft() {
        chessGame newGame = new chessGame(1, false);
        Pieces bishop = new bishop(5, 4, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][4] = bishop;
        newGame.movePieceTo(bishop, 2, 1);

        assertEquals(bishop, newGame.chess.board[2][1]);
    }

    //test for a move a bishop cannot make
    @Test
    public void testIllegalMove() {
        chessGame newGame = new chessGame(1, false);
        Pieces bishop = new bishop(5, 4, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][4] = bishop;
        newGame.movePieceTo(bishop, 3, 4);

        assertEquals(bishop, newGame.chess.board[5][4]);
    }

    //test for destination = current location
    @Test
    public void sameLocation() {
        chessGame newGame = new chessGame(1, false);
        Pieces bishop = new bishop(5, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][1] = bishop;
        newGame.movePieceTo(bishop, 5, 1);

        assertEquals(bishop, newGame.chess.board[5][1]);
    }

    //test for leaping
    @Test
    public void canLeap() {
        chessGame newGame = new chessGame(1, false);
        Pieces bishop = new bishop(5, 1, WHITE, newGame.chess, newGame.chess.player1);
        Pieces bishopToLeap = new bishop(4, 2, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][1] = bishop;
        newGame.chess.board[4][2] = bishopToLeap;

        assertFalse(newGame.canLeap(bishop, 3, 3));
    }

    //test if destination is in board bounds
    @Test
    public void isInBounds() {
        chessGame newGame = new chessGame(1, false);
        assertFalse(newGame.isInBounds(5, 8));
    }

    //test for a destination which has a piece of same color
    @Test
    public void isDestinationOnSamePieceColor() {
        chessGame newGame = new chessGame(1, false);
        assertFalse(newGame.isDestinationOnSamePieceColor(newGame.chess.board[0][2], 1, 3));
    }

    //test killing of another piece by the bishop
    @Test
    public void canKill() {
        chessGame newGame = new chessGame(1, false);
        Pieces bishop = new bishop(4, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[4][1] = bishop;
        newGame.movePieceTo(bishop, 6, 3);

        assertEquals(bishop, newGame.chess.board[6][3]);
    }
}
