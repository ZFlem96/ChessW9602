package Model.testSuite;


import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;


/*
*Initial setup the board should look like this, with VERTICAL axis being treated as x (rows) and HORIZONTAL as y (columns)
*   0  1  2  3  4  5  6  7
*   -----------------------
* 7 br bn bb bq bk bb bn br
* 6 bp bp bp bp bp bp bp bp
* 5
* 4
* 3
* 2
* 1 wp wp wp wp wp wp wp wp
* 0 wr wn wb wq wk wb wn wr
*   -----------------------
*
* b - BLACK, w - WHITE, r - ROOK, n - KNIGHT, b - BISHOP, q - QUEEN, k - KING, p
*/

public class boardSetupTests {

    //first test if board is setup correctly
    @Test
    public void isBoardSetupCorrect() {
        System.out.println("EXPECTED:\n");

        System.out.println("| bRook |\t| bKnight |\t| bBishop |\t| bKing |\t| bQueen |\t| bBishop |\t| bKnight |\t| bRook |");
        System.out.println("| bPawn |\t| bPawn |\t| bPawn |\t| bPawn |\t| bPawn |\t| bPawn |\t| bPawn |\t| bPawn |");
        System.out.println("| null |\t| null |\t| null |\t| null |\t| null |\t| null |\t| null |\t| null |");
        System.out.println("| null |\t| null |\t| null |\t| null |\t| null |\t| null |\t| null |\t| null |");
        System.out.println("| null |\t| null |\t| null |\t| null |\t| null |\t| null |\t| null |\t| null |");
        System.out.println("| null |\t| null |\t| null |\t| null |\t| null |\t| null |\t| null |\t| null |");
        System.out.println("| wPawn |\t| wPawn |\t| wPawn |\t| wPawn |\t| wPawn |\t| wPawn |\t| wPawn |\t| wPawn |");
        System.out.println("| wRook |\t| wKnight |\t| wBishop |\t| wKing |\t| wQueen |\t| wBishop |\t| wKnight |\t| wRook |");

        System.out.println();

        System.out.println("ACTUAL RESULT:\n");

        chessGame newGame = new chessGame(1, false);

        newGame.chess.printBord();
    }

    //test if black can make the first move (it cannot)
    @Test
    public void blackCannotTakeFirstTurn() {
        chessGame newGame = new chessGame(1, false);
        assertFalse(newGame.checkMoveConstraints(newGame.chess.board[6][0], 5, 0));
    }
}
