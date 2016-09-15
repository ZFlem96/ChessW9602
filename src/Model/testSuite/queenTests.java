package Model.testSuite;


import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;
import Model.pieces.*;
import static Model.pieces.Color.*;


public class queenTests {

    //move up and right
    @Test
    public void upRight() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(2, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][1] = queen;
        newGame.movePieceTo(queen, 5, 4);

        assertEquals(queen, newGame.chess.board[5][4]);
    }

    //move up and left
    @Test
    public void upLeft() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(2, 4, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[2][4] = queen;
        newGame.movePieceTo(queen, 5, 1);

        assertEquals(queen, newGame.chess.board[5][1]);
    }

    //move down and right
    @Test
    public void downRight() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(5, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][1] = queen;
        newGame.movePieceTo(queen, 2, 4);

        assertEquals(queen, newGame.chess.board[2][4]);
    }

    //move down and left
    @Test
    public void downLeft() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(5, 4, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][4] = queen;
        newGame.movePieceTo(queen, 2, 1);

        assertEquals(queen, newGame.chess.board[2][1]);
    }

    //move up
    @Test
    public void up() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = queen;
        newGame.movePieceTo(queen, 5, 3);

        assertEquals(queen, newGame.chess.board[5][3]);
    }

    //move down
    @Test
    public void down() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = queen;
        newGame.movePieceTo(queen, 2, 3);

        assertEquals(queen, newGame.chess.board[2][3]);
    }

    //move left
    @Test
    public void left() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = queen;
        newGame.movePieceTo(queen, 3, 0);

        assertEquals(queen, newGame.chess.board[3][0]);
    }

    //move right
    @Test
    public void right() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(3, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[3][3] = queen;
        newGame.movePieceTo(queen, 3, 7);

        assertEquals(queen, newGame.chess.board[3][7]);
    }

    //test for a move a queen cannot make
    @Test
    public void testIllegalMove() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(5, 4, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][4] = queen;
        newGame.movePieceTo(queen, 3, 5);

        assertEquals(queen, newGame.chess.board[5][4]);
    }

    //test for initial=destination location
    @Test
    public void sameLocation() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(5, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][1] = queen;
        newGame.movePieceTo(queen, 5, 1);

        assertEquals(queen, newGame.chess.board[5][1]);
    }

    //test if queen can leap other Model.pieces
    @Test
    public void canLeap() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(5, 1, WHITE, newGame.chess, newGame.chess.player1);
        Pieces queenToLeap = new queen(4, 2, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][1] = queen;
        newGame.chess.board[4][2] = queenToLeap;

        assertFalse(newGame.canLeap(queen, 3, 3));
    }

    //test if destination is in bounds
    @Test
    public void isInBounds() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.isInBounds(5, 8));
    }

    //test if destination is on a piece of same color
    @Test
    public void isDestinationOnSamePieceColor() {
        chessGame newGame = new chessGame();
        assertFalse(newGame.isDestinationOnSamePieceColor(newGame.chess.board[0][3], 1, 3));
    }

    //test killing of a piece by the queen
    @Test
    public void canKill() {
        chessGame newGame = new chessGame();
        Pieces queen = new queen(4, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[4][1] = queen;
        newGame.movePieceTo(queen, 6, 3);

        assertEquals(queen, newGame.chess.board[6][3]);
    }
}
