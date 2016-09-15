package Model.core;

import Model.pieces.*;
import static Model.pieces.Color.*;
import static Model.pieces.pieceType.King;

/**
* This class does as the name suggests, setting up the chess board. It sets up the Model.pieces, then populates the opponent and player arrays of
* Model.pieces for player1 and player2.
*Initial setup the board would look like this, with VERTICAL axis being treated as x (rows) and HORIZONTAL as y (columns)
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
* b - BLACK, w - WHITE, r - ROOK, n - KNIGHT, b - BISHOP, q - QUEEN, k - KING, p - PAWN
*/

public class chessBoard {
    //change the number of files and ranks (package-private ints) to alter the board dimensions
    public static final int files = 8;
    public static final int ranks = 8;
    public Pieces[][] board;
    public Player player1 = new Player(); //by default, p1 is WHITE
    public Player player2 = new Player(); //and p2 is BLACK

    /**
     * Class constructor
     */
    public chessBoard() {
        board = new Pieces[files][ranks];
        setupPieces();
        populatePlayerArrays();
    }

    /**
     * A function to setup all the Model.pieces on the board.
     */
    private void setupPieces() {
        //setup the pawns
        for(int i=0; i<8; i++) {
            board[1][i] = new pawn(1, i, WHITE, this, player1);
            board[6][i] = new pawn(6, i, BLACK, this, player2);
        }
        //setup the rooks
        board[0][0] = new rook(0, 0, WHITE, this, player1);
        board[0][7] = new rook(0, 7, WHITE, this, player1);
        board[7][0] = new rook(7, 0, BLACK, this, player2);
        board[7][7] = new rook(7, 7, BLACK, this, player2);

        //setup the knights
        board[0][1] = new knight(0, 1, WHITE, this, player1);
        board[0][6] = new knight(0, 6, WHITE, this, player1);
        board[7][1] = new knight(7, 1, BLACK, this, player2);
        board[7][6] = new knight(7, 6, BLACK, this, player2);

        //setup the bishops
        board[0][2] = new bishop(0, 2, WHITE, this, player1);
        board[0][5] = new bishop(0, 5, WHITE, this, player1);
        board[7][2] = new bishop(7, 2, BLACK, this, player2);
        board[7][5] = new bishop(7, 5, BLACK, this, player2);

        //setup the queens
        board[0][3] = new queen(0, 3, WHITE, this, player1);
        board[7][3] = new queen(7, 3, BLACK, this, player2);

        //setup the kings
        board[0][4] = new king(0, 4, WHITE, this, player1);
        board[7][4] = new king(7, 4, BLACK, this, player2);
    }

    /**
     * A function which populate the player arrays, namely, player Model.pieces (player's self Model.pieces) and opponent Model.pieces
     * (player's opponent's Model.pieces).
     */
    private void populatePlayerArrays() {
        //add the pawns
        for (int i = 0; i < files; i++) {
            player1.playerPieces.add(board[1][i]);
            player1.opponentPieces.add(board[6][i]);
            player2.playerPieces.add(board[6][i]);
            player2.opponentPieces.add(board[1][i]);
        }

        //add the remaining Model.pieces
        for (int i = 0; i < files; i++) {
            player1.playerPieces.add(board[0][i]);
            player1.opponentPieces.add(board[7][i]);
            player2.playerPieces.add(board[7][i]);
            player2.opponentPieces.add(board[0][i]);
        }
    }

    /**
     * helper function to print the board
     */
    public void printBord() {
        for(int i = files - 1; i>=0; i--)
        {
            for(int j = 0; j < ranks; j++)
            {
                System.out.print("| ");
                System.out.print(board[i][j]);
                System.out.print(" |\t");
            }
            System.out.println();
        }
    }

    /**
     * A getter to return the king piece of a specific color, heavily used while checking for check, checkmate and stalemate
     * @param color : color of the king to be returned
     * @return : king of the type Pieces
     */
    Pieces getKing(Color color) {
        for(int i = 0; i < files; i++) {
            for(int j = 0; j < ranks; j++) {
                if(board[i][j] != null) {
                    if (board[i][j].getPieceType() == King && board[i][j].getPieceColor() == color) {
                        return board[i][j];
                    }
                }
            }
        }
        return null;
    }
}