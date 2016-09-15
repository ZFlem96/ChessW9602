package Model.testSuite;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;
import Model.pieces.*;
import static Model.pieces.Color.*;

public class wazirTests {
    //move up
    @Test
    public void up() {
        chessGame newGame = new chessGame();
        Pieces wazir = new wazir(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = wazir;
        newGame.movePieceTo(wazir, 4, 3);

        assertEquals(wazir, newGame.chess.board[4][3]);
    }

    //move down
    @Test
    public void down() {
        chessGame newGame = new chessGame();
        Pieces wazir = new wazir(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = wazir;
        newGame.movePieceTo(wazir, 2, 3);

        assertEquals(wazir, newGame.chess.board[2][3]);
    }

    //move right
    @Test
    public void right() {
        chessGame newGame = new chessGame();
        Pieces wazir = new wazir(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = wazir;
        newGame.movePieceTo(wazir, 3, 4);

        assertEquals(wazir, newGame.chess.board[3][4]);
    }

    //move left
    @Test
    public void left() {
        chessGame newGame = new chessGame();
        Pieces wazir = new wazir(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = wazir;
        newGame.movePieceTo(wazir, 3, 2);

        assertEquals(wazir, newGame.chess.board[3][2]);
    }

    //move up and right
    @Test
    public void testIllegalMove() {
        chessGame newGame = new chessGame();
        Pieces wazir = new wazir(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = wazir;
        newGame.movePieceTo(wazir, 4, 4);

        assertEquals(wazir, newGame.chess.board[3][3]);
    }


    //test for destination = initial location
    @Test
    public void sameLocation() {
        chessGame newGame = new chessGame();
        Pieces wazir = new wazir(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = wazir;
        newGame.movePieceTo(wazir, 3, 3);

        assertEquals(wazir, newGame.chess.board[3][3]);
    }

    //test if destination is in board bounds
    @Test
    public void isInBounds() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.isInBounds(5, 8));
    }

    //test for moving a king to a piece of same color
    @Test
    public void isDestinationOnSamePieceColor() {
        chessGame newGame = new chessGame();
        Pieces wazir = new wazir(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][3] = wazir;
        assertFalse(newGame.isDestinationOnSamePieceColor(newGame.chess.board[2][3], 1, 3));
    }

    //test for killing a piece by king
    @Test
    public void canKill() {
        chessGame newGame = new chessGame();
        Pieces wazir = new king(3, 5, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][5] = wazir;
        newGame.movePieceTo(wazir, 3, 6);

        assertEquals(wazir, newGame.chess.board[3][6]);
    }
}
