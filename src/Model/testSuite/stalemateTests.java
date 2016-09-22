package Model.testSuite;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.core.*;
import Model.pieces.*;
import static Model.pieces.Color.*;

/**
*a stalemate setup of the board would look like this, with VERTICAL axis being treated as x (rows) and HORIZONTAL as y (columns),
* here the black king (wk) is in a stalemate situation.
*   0  1  2  3  4  5  6  7
*   -----------------------
* 7                      wk
* 6                bk
* 5                   bq
* 4
* 3
* 2
* 1
* 0
*   -----------------------
*
* b - BLACK, w - WHITE, r - ROOK, n - KNIGHT, b - BISHOP, q - QUEEN, k - KING, p - PAWN
*/
public class stalemateTests {

    //a stimulation to test the stalemate condition
    @Test
    public void isStalemate() {
        chessGame newGame = new chessGame(1);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newGame.chess.board[i][j] = null;
            }
        }

        newGame.chess.player1.opponentPieces.clear();
        newGame.chess.player1.playerPieces.clear();
        newGame.chess.player2.opponentPieces.clear();
        newGame.chess.player2.playerPieces.clear();

        newGame.chess.board[6][5] = new king(6, 5, BLACK, newGame.chess, newGame.chess.player1);
        newGame.chess.board[5][6] = new queen(5, 6, BLACK, newGame.chess, newGame.chess.player1);
        newGame.chess.board[7][7] = new king(7, 7, WHITE, newGame.chess, newGame.chess.player2);

        newGame.chess.player2.playerPieces.add(0, newGame.chess.board[6][5]);
        newGame.chess.player2.playerPieces.add(1, newGame.chess.board[5][6]);
        newGame.chess.player1.opponentPieces.add(0, newGame.chess.board[6][5]);
        newGame.chess.player1.opponentPieces.add(1, newGame.chess.board[5][6]);

        newGame.chess.player1.playerPieces.add(0, newGame.chess.board[7][7]);
        newGame.chess.player2.opponentPieces.add(0, newGame.chess.board[7][7]);


        assertTrue(newGame.isStalemate(newGame.chess.board[7][7])); //NOTE: the MOVE ERROR on the console show the errors as to why
                                                                    // any piece aren't able to move
    }
}
