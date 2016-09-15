package Model.testSuite;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;
import Model.pieces.*;
import static Model.pieces.Color.*;


public class rookTests {

    //move up
    @Test
    public void up() {
        chessGame newGame = new chessGame();
        Pieces rook = new rook(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = rook;
        newGame.movePieceTo(rook, 5, 3);

        assertEquals(rook, newGame.chess.board[5][3]);
    }

    //move down
    @Test
    public void down() {
        chessGame newGame = new chessGame();
        Pieces rook = new rook(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = rook;
        newGame.movePieceTo(rook, 2, 3);

        assertEquals(rook, newGame.chess.board[2][3]);
    }

    //move left
    @Test
    public void left() {
        chessGame newGame = new chessGame();
        Pieces rook = new rook(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = rook;
        newGame.movePieceTo(rook, 3, 0);

        assertEquals(rook, newGame.chess.board[3][0]);
    }

    //move right
    @Test
    public void right() {
        chessGame newGame = new chessGame();
        Pieces rook = new rook(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = rook;
        newGame.movePieceTo(rook, 3, 7);

        assertEquals(rook, newGame.chess.board[3][7]);
    }

    //test for a move a rook cannot make
    @Test
    public void testIllegalMove() {
        chessGame newGame = new chessGame();
        Pieces rook = new rook(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = rook;
        newGame.movePieceTo(rook, 4, 7);

        assertEquals(rook, newGame.chess.board[3][3]);
    }

    //test for destination=initial location
    @Test
    public void sameLocation() {
        chessGame newGame = new chessGame();
        Pieces rook = new rook(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = rook;
        newGame.movePieceTo(rook, 3, 3);

        assertEquals(rook, newGame.chess.board[3][3]);
    }

    //test if rook can leap other Model.pieces
    @Test
    public void canLeap() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.canLeap(newGame.chess.board[0][0], 2, 0));
    }

    //test if destination is in board bounds
    @Test
    public void isInBounds() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.isInBounds(0, 10));
    }

    //test if destination is on a piece of same color
    @Test
    public void isDestinationOnSamePieceColor() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.isDestinationOnSamePieceColor(newGame.chess.board[0][0], 1, 0));
    }

    //test killing of other Model.pieces by rook
    @Test
    public void canKill() {
        chessGame newGame = new chessGame();
        Pieces rook = new rook(4, 0, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[4][0] = rook;
        newGame.movePieceTo(rook, 6, 0);

        assertEquals(rook, newGame.chess.board[6][0]);
    }
}
