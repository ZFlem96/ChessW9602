package Model.testSuite;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;
import Model.pieces.*;
import static Model.pieces.Color.*;

public class ferzTests {
    //up right movement
    @Test
    public void upRight() {
        chessGame newGame = new chessGame();
        Pieces ferz = new ferz(2, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][1] = ferz;
        newGame.movePieceTo(ferz, 3, 2);

        assertEquals(ferz, newGame.chess.board[3][2]);
    }

    //up and left movement
    @Test
    public void upLeft() {
        chessGame newGame = new chessGame();
        Pieces ferz = new ferz(2, 4, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][4] = ferz;
        newGame.movePieceTo(ferz, 3, 3);

        assertEquals(ferz, newGame.chess.board[3][3]);
    }

    //down and right movement
    @Test
    public void downRight() {
        chessGame newGame = new chessGame();
        Pieces ferz = new ferz(5, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][1] = ferz;
        newGame.movePieceTo(ferz, 4, 2);

        assertEquals(ferz, newGame.chess.board[4][2]);
    }

    //down and left movement
    @Test
    public void downLeft() {
        chessGame newGame = new chessGame();
        Pieces ferz = new ferz(5, 4, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][4] = ferz;
        newGame.movePieceTo(ferz, 4, 3);

        assertEquals(ferz, newGame.chess.board[4][3]);
    }

    //test for a move a bishop cannot make
    @Test
    public void testIllegalMove() {
        chessGame newGame = new chessGame();
        Pieces ferz = new ferz(5, 4, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][4] = ferz;
        newGame.movePieceTo(ferz, 3, 2);

        assertEquals(ferz, newGame.chess.board[5][4]);
    }

    //test for destination = current location
    @Test
    public void sameLocation() {
        chessGame newGame = new chessGame();
        Pieces ferz = new ferz(5, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][1] = ferz;
        newGame.movePieceTo(ferz, 5, 1);

        assertEquals(ferz, newGame.chess.board[5][1]);
    }

    //test if destination is in board bounds
    @Test
    public void isInBounds() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.isInBounds(5, 8));
    }

    //test for a destination which has a piece of same color
    @Test
    public void isDestinationOnSamePieceColor() {
        chessGame newGame = new chessGame();
        Pieces ferz = new ferz(2, 2, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][2] = ferz;
        assertFalse(newGame.isDestinationOnSamePieceColor(newGame.chess.board[2][2], 1, 1));
    }

    //test killing of another piece by the bishop
    @Test
    public void canKill() {
        chessGame newGame = new chessGame();
        Pieces ferz = new ferz(5, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][1] = ferz;
        newGame.movePieceTo(ferz, 6, 2);

        assertEquals(ferz, newGame.chess.board[6][2]);
    }
}
