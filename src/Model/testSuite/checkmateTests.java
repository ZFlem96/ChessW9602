package Model.testSuite;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;
import Model.pieces.*;
import static Model.pieces.Color.*;

/*
*a checkmate setup of the board would look like this, with VERTICAL axis being treated as x (rows) and HORIZONTAL as y (columns),
* here the black king (bk) is in a checkmate situation.
*   0  1  2  3  4  5  6  7
*   -----------------------
* 7 wq             bk
* 6    wr
* 5
* 4
* 3
* 2
* 1
* 0         wk
*   -----------------------
*
* b - BLACK, w - WHITE, r - ROOK, n - KNIGHT, b - BISHOP, q - QUEEN, k - KING, p - PAWN
**/

public class checkmateTests {

    //run a stimulation of a case which puts black king in checkmate
    @Test
    public void checkmate() {
        chessGame newGame = new chessGame(1);
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                newGame.chess.board[i][j] = null;
            }
        }

        newGame.chess.player1.opponentPieces.clear();
        newGame.chess.player1.playerPieces.clear();
        newGame.chess.player2.opponentPieces.clear();
        newGame.chess.player2.playerPieces.clear();

        newGame.chess.board[0][3] = new king(0, 3, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[6][1] = new rook(6, 1, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[7][0] = new queen(7, 0, WHITE, newGame.chess, newGame.chess.player1);
        newGame.chess.board[7][5] = new king(7, 5, BLACK, newGame.chess, newGame.chess.player2);

        newGame.chess.player1.playerPieces.add(0, newGame.chess.board[0][3]);
        newGame.chess.player1.playerPieces.add(1, newGame.chess.board[6][1]);
        newGame.chess.player1.playerPieces.add(2, newGame.chess.board[7][0]);
        newGame.chess.player2.opponentPieces.add(0, newGame.chess.board[0][3]);
        newGame.chess.player2.opponentPieces.add(1, newGame.chess.board[6][1]);
        newGame.chess.player2.opponentPieces.add(2, newGame.chess.board[7][0]);

        newGame.chess.player2.playerPieces.add(0, newGame.chess.board[7][5]);
        newGame.chess.player1.opponentPieces.add(0, newGame.chess.board[7][5]);

        assertTrue(newGame.isKingInCheck(newGame.chess.board[7][5], 7, 5));
    }

    //run a stimulation of a case which is not in checkmate
    @Test
    public void notInCheckmate() {
        chessGame newGame = new chessGame(1);
        assertFalse(newGame.isKingInCheckmate(newGame.chess.board[0][4]));
    }
}
