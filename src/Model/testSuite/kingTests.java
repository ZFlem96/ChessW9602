package Model.testSuite;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;
import Model.pieces.*;
import static Model.pieces.Color.*;

public class kingTests {

    //move up
    @Test
    public void up() {
        chessGame newGame = new chessGame(1, false);
        king king = new king(2, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][3] = king;
        newGame.movePieceTo(king, 3, 3);

        assertEquals(king, newGame.chess.board[3][3]);
    }

    //move down
    @Test
    public void down() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = king;
        newGame.movePieceTo(king, 2, 3);

        assertEquals(king, newGame.chess.board[2][3]);
    }

    //move right
    @Test
    public void right() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = king;
        newGame.movePieceTo(king, 3, 4);

        assertEquals(king, newGame.chess.board[3][4]);
    }

    //move left
    @Test
    public void left() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = king;
        newGame.movePieceTo(king, 3, 2);

        assertEquals(king, newGame.chess.board[3][2]);
    }

    //move up and right
    @Test
    public void upRight() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(2, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][3] = king;
        newGame.movePieceTo(king, 3, 4);

        assertEquals(king, newGame.chess.board[3][4]);
    }

    //move up and left
    @Test
    public void upLeft() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(2, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][3] = king;
        newGame.movePieceTo(king, 3, 2);

        assertEquals(king, newGame.chess.board[3][2]);
    }

    //move down and right
    @Test
    public void downRight() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = king;
        newGame.movePieceTo(king, 2, 4);

        assertEquals(king, newGame.chess.board[2][4]);
    }

    //move down and left
    @Test
    public void downLeft() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = king;
        newGame.movePieceTo(king, 2, 2);

        assertEquals(king, newGame.chess.board[2][2]);
    }

    //test for a move a king cannot make
    @Test
    public void testIllegalMove() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = king;
        newGame.movePieceTo(king, 5, 5);

        assertEquals(king, newGame.chess.board[3][3]);
    }

    //test for destination = initial location
    @Test
    public void sameLocation() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = king;
        newGame.movePieceTo(king, 3, 3);

        assertEquals(king, newGame.chess.board[3][3]);
    }

    //test if destination is in board bounds
    @Test
    public void isInBounds() {
        chessGame newGame = new chessGame(1, false);
        assertFalse(newGame.isInBounds(5, 8));
    }

    //test for moving a king to a piece of same color
    @Test
    public void isDestinationOnSamePieceColor() {
        chessGame newGame = new chessGame(1, false);
        assertFalse(newGame.isDestinationOnSamePieceColor(newGame.chess.board[0][4], 1, 4));
    }

    //test for killing a piece by king
    @Test
    public void canKill() {
        chessGame newGame = new chessGame(1, false);
        Pieces king = new king(3, 5, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][5] = king;
        newGame.movePieceTo(king, 3, 6);

        assertEquals(king, newGame.chess.board[3][6]);
    }
}
