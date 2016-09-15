package Model.testSuite;

import Model.core.chessGame;
import org.junit.Test;
import Model.pieces.Pieces;
import Model.pieces.knight;
import static org.junit.Assert.*;
import static Model.pieces.Color.WHITE;

public class knightTests {

    //move up and right
    @Test
    public void upRight() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(2, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][1] = knight;
        newGame.movePieceTo(knight, 4, 2);

        assertEquals(knight, newGame.chess.board[4][2]);
    }

    //move up and left
    @Test
    public void upLeft() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(2, 2, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][2] = knight;
        newGame.movePieceTo(knight, 4, 1);

        assertEquals(knight, newGame.chess.board[4][1]);
    }

    //move down and right
    @Test
    public void downRight() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(4, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[4][1] = knight;
        newGame.movePieceTo(knight, 2, 2);

        assertEquals(knight, newGame.chess.board[2][2]);
    }

    //move down and left
    @Test
    public void downLeft() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(4, 2, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[4][2] = knight;
        newGame.movePieceTo(knight, 2, 1);

        assertEquals(knight, newGame.chess.board[2][1]);
    }

    //move right and up
    @Test
    public void rightUp() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = knight;
        newGame.movePieceTo(knight, 4, 5);

        assertEquals(knight, newGame.chess.board[4][5]);
    }

    //move right and down
    @Test
    public void rightDown() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = knight;
        newGame.movePieceTo(knight, 2, 5);

        assertEquals(knight, newGame.chess.board[2][5]);
    }

    //move left and up
    @Test
    public void leftUp() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = knight;
        newGame.movePieceTo(knight, 4, 1);

        assertEquals(knight, newGame.chess.board[4][1]);
    }

    //move left and down
    @Test
    public void leftDown() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = knight;
        newGame.movePieceTo(knight, 2, 1);

        assertEquals(knight, newGame.chess.board[2][1]);
    }

    //test for a move a knight cannot make
    @Test
    public void testIllegalMove() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = knight;
        newGame.movePieceTo(knight, 6, 3);

        assertEquals(knight, newGame.chess.board[3][3]);
    }

    //test for destination = initial location
    @Test
    public void sameLocation() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = knight;
        newGame.movePieceTo(knight, 3, 3);

        assertEquals(knight, newGame.chess.board[3][3]);
    }

    //test for knight leaping other Model.pieces
    @Test
    public void canLeap() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        Pieces pawnToLeap = new knight(4, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = knight;
        newGame.chess.board[4][3] = pawnToLeap;
        newGame.movePieceTo(knight, 5, 4);

        assertTrue(newGame.canLeap(knight, 3, 3));
        assertEquals(knight, newGame.chess.board[5][4]);
    }

    //test if destination is in board bounds
    @Test
    public void isInBounds() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.isInBounds(4, 8));
    }

    //test if destination is on a piece of same color
    @Test
    public void isDestinationOnSamePieceColor() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.isDestinationOnSamePieceColor(newGame.chess.board[0][1], 1, 3));
    }

    //test killing of a piece by knight
    @Test
    public void canKill() {
        chessGame newGame = new chessGame();
        Pieces knight = new knight(5, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][3] = knight;
        newGame.movePieceTo(knight, 6, 5);

        assertEquals(knight, newGame.chess.board[6][5]);
    }
}
