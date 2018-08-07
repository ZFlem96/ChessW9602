package Model.core;

import Model.pieces.*;
import static Model.pieces.Color.*;
import static Model.pieces.pieceType.King;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.sun.swing.internal.plaf.basic.resources.basic;

/**
 * This class does as the name suggests, setting up the chess board. It sets up
 * the Model.pieces, then populates the opponent and player arrays of
 * Model.pieces for player1 and player2. Initial setup the board would look like
 * this, with VERTICAL axis being treated as x (rows) and HORIZONTAL as y
 * (columns) 0 1 2 3 4 5 6 7 ----------------------- 7 br bn bb bq bk bb bn br 6
 * bp bp bp bp bp bp bp bp 5 4 3 2 1 wp wp wp wp wp wp wp wp 0 wr wn wb wq wk wb
 * wn wr -----------------------
 *
 * b - BLACK, w - WHITE, r - ROOK, n - KNIGHT, b - BISHOP, q - QUEEN, k - KING,
 * p - PAWN
 */

public class chessBoard {
	// change the number of files and ranks (package-private ints) to alter the
	// board dimensions
	public static final int files = 8;
	public static final int ranks = 8;
	public Pieces[][] board;
	public Player player1 = new Player(); // by default, p1 is WHITE
	public Player player2 = new Player(); // and p2 is BLACK
	private int isAlternatePieces;
	private boolean is960Game = false;

	public boolean getIs960Game() {
		return is960Game;
	}

	public void setIs960Game(boolean is960Game) {
		this.is960Game = is960Game;
	}

	/**
	 * Class constructor
	 * 
	 * @param isAlternatePiece
	 *            : if we have to play with fairy pieces or not, 0 for yes, 1
	 *            for no
	 */
	public chessBoard(int isAlternatePiece, boolean is960Game) {
		this.isAlternatePieces = isAlternatePiece;
		this.is960Game = is960Game;
		board = new Pieces[files][ranks];
		setupPieces();
		populatePlayerArrays();
	}

	public chessBoard(int isAlternatePiece) {
		this.isAlternatePieces = isAlternatePiece;
		board = new Pieces[files][ranks];
		setupPieces();
		populatePlayerArrays();
	}

	/**
	 * A function to setup all the Model.pieces on the board.
	 */
	private void setupPieces() {
		// setup the pawns
		for (int i = 0; i < 8; i++) {
			board[1][i] = new pawn(1, i, WHITE, this, player1);
			board[6][i] = new pawn(6, i, BLACK, this, player2);
		}
		if (is960Game) {
			setup960Pieces();
		} else {
			// setup the rooks/wazir
			if (isAlternatePieces == 0) {
				board[0][0] = new wazir(0, 0, WHITE, this, player1);
				board[0][7] = new wazir(0, 7, WHITE, this, player1);
				board[7][0] = new wazir(7, 0, BLACK, this, player2);
				board[7][7] = new wazir(7, 7, BLACK, this, player2);
			} else {
				board[0][0] = new rook(0, 0, WHITE, this, player1);
				board[0][7] = new rook(0, 7, WHITE, this, player1);
				board[7][0] = new rook(7, 0, BLACK, this, player2);
				board[7][7] = new rook(7, 7, BLACK, this, player2);
			}

			// setup the knights
			board[0][1] = new knight(0, 1, WHITE, this, player1);
			board[0][6] = new knight(0, 6, WHITE, this, player1);
			board[7][1] = new knight(7, 1, BLACK, this, player2);
			board[7][6] = new knight(7, 6, BLACK, this, player2);

			// setup the bishops/ferz
			if (isAlternatePieces == 0) {
				board[0][2] = new ferz(0, 2, WHITE, this, player1);
				board[0][5] = new ferz(0, 5, WHITE, this, player1);
				board[7][2] = new ferz(7, 2, BLACK, this, player2);
				board[7][5] = new ferz(7, 5, BLACK, this, player2);
			} else {
				board[0][2] = new bishop(0, 2, WHITE, this, player1);
				board[0][5] = new bishop(0, 5, WHITE, this, player1);
				board[7][2] = new bishop(7, 2, BLACK, this, player2);
				board[7][5] = new bishop(7, 5, BLACK, this, player2);
			}

			// setup the queens
			board[0][3] = new queen(0, 3, WHITE, this, player1);
			board[7][3] = new queen(7, 3, BLACK, this, player2);

			// setup the kings
			board[0][4] = new king(0, 4, WHITE, this, player1);
			board[7][4] = new king(7, 4, BLACK, this, player2);

		}
	}

	/*
	 * 960 Placement Rule Random, but The bishops must be placed on
	 * opposite-color squares. The king must be placed on a square between the
	 * rooks. 0 = rooks/wazir, 1 = knight, 2 = bishops/ferz, 3 = queen, 4 = king
	 */
	private void setup960Pieces() {
		ArrayList<Integer> pieces = new ArrayList<>(), usedPieces = new ArrayList<>();
		pieces.add(0);
		pieces.add(1);
		pieces.add(2);
		pieces.add(3);
		pieces.add(4);
		boolean endLoop = false, kingUsed = false, firstBishopOrFerzUsed = false, secondBishopOrFerz = false;
		Random rand = new Random();
		int index = 0;
		int firstBishopOrFerz = -1;
		while (index < 8) {
			int selectedPiece = -1;
			int matches = 0;
			while (!endLoop) {
				if (firstBishopOrFerzUsed && !secondBishopOrFerz) {
					if (firstBishopOrFerz % 2 != 0 && index % 2 != 0) {
						for (int x = 0; x < pieces.size(); x++) {
							if (pieces.get(x) == 3) {
								pieces.remove(x);
								break;
							}
						}
					} else if (firstBishopOrFerz % 2 == 0 && index % 2 == 0) {
						for (int x = 0; x < pieces.size(); x++) {
							if (pieces.get(x) == 3) {
								pieces.remove(x);
								break;
							}
						}
					} else {
						pieces.add(3);
					}
				}
				if (pieces.size() > 2) {
					if (firstBishopOrFerzUsed && !kingUsed && pieces.size() == 3) {
						selectedPiece = pieces.size() - 1;
					} else {
						selectedPiece = rand.nextInt(pieces.size() - 1);
					}
				} else if (pieces.size() == 2) {
					// if (firstBishopOrFerzUsed && kingUsed &&
					// !secondBishopOrFerz) {
					// if (index % 2 != 0 && firstBishopOrFerz % 2 == 0) {
					// for (int x = 0; x < pieces.size(); x++) {
					// if (pieces.get(x) == 2) {
					// selectedPiece = x;
					// break;
					// }
					// }
					// }
					// } else {
					selectedPiece = rand.nextInt(1);
					// }
				} else {
					selectedPiece = pieces.size() - 1;
				}
				if (pieces.get(selectedPiece) == 2) {
					if (!firstBishopOrFerzUsed) {
						endLoop = true;
						firstBishopOrFerzUsed = true;
						firstBishopOrFerz = selectedPiece;
					} else if (firstBishopOrFerzUsed && pieces.get(selectedPiece) == 2 && kingUsed) {
						if (index % 2 == 0 && firstBishopOrFerz % 2 != 0) {
							endLoop = true;
							matches++;
							secondBishopOrFerz = true;
						} else if (index % 2 != 0 && firstBishopOrFerz % 2 == 0) {
							endLoop = true;
							matches++;
							secondBishopOrFerz = true;
						}
					}
				} else if (pieces.get(selectedPiece) == 4) {
					if (!kingUsed && firstBishopOrFerz > 0) {
						kingUsed = true;
						endLoop = true;
					}
				} else {
					endLoop = true;
				}
				for (int x = 0; x < usedPieces.size(); x++) {
					if (pieces.get(selectedPiece) == usedPieces.get(x)) {
						matches++;
					}
				}
			}
			endLoop = pieces.size() < 0;
			if (pieces.get(selectedPiece) == 0) {
				if (isAlternatePieces == 0) {
					board[0][index] = new wazir(0, index, WHITE, this, player1);
					board[7][index] = new wazir(7, index, BLACK, this, player2);
				} else {
					board[0][index] = new rook(0, index, WHITE, this, player1);
					board[7][index] = new rook(7, index, BLACK, this, player2);
				}
				usedPieces.add(selectedPiece);
				if (matches > 0) {
					pieces.remove(selectedPiece);
				}
			} else if (pieces.get(selectedPiece) == 1) {
				board[0][index] = new knight(0, index, WHITE, this, player1);
				board[7][index] = new knight(7, index, BLACK, this, player2);
				usedPieces.add(selectedPiece);
				if (matches > 0) {
					pieces.remove(selectedPiece);
				}
			} else if (pieces.get(selectedPiece) == 2) {
				usedPieces.add(selectedPiece);
				if (isAlternatePieces == 0) {
					board[0][index] = new ferz(0, index, WHITE, this, player1);
					board[7][index] = new ferz(7, index, BLACK, this, player2);
				} else {
					board[0][index] = new bishop(0, index, WHITE, this, player1);
					board[7][index] = new bishop(7, index, BLACK, this, player2);
				}
				if (matches > 0 && firstBishopOrFerzUsed && secondBishopOrFerz) {
					pieces.remove(selectedPiece);
				}
			} else if (pieces.get(selectedPiece) == 3) {
				usedPieces.add(selectedPiece);
				board[0][index] = new queen(0, index, WHITE, this, player1);
				board[7][index] = new queen(7, index, BLACK, this, player2);
				pieces.remove(selectedPiece);
			} else if (pieces.get(selectedPiece) == 4) {
				usedPieces.add(selectedPiece);
				board[0][index] = new king(0, index, WHITE, this, player1);
				board[7][index] = new king(7, index, BLACK, this, player2);
				pieces.remove(selectedPiece);
			}
			index++;
		}
	}

	/**
	 * A function which populate the player arrays, namely, player Model.pieces
	 * (player's self Model.pieces) and opponent Model.pieces (player's
	 * opponent's Model.pieces).
	 */
	private void populatePlayerArrays() {
		// add the pawns
		for (int i = 0; i < files; i++) {
			player1.playerPieces.add(board[1][i]);
			player1.opponentPieces.add(board[6][i]);
			player2.playerPieces.add(board[6][i]);
			player2.opponentPieces.add(board[1][i]);
		}

		// add the remaining Model.pieces
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
		for (int i = files - 1; i >= 0; i--) {
			for (int j = 0; j < ranks; j++) {
				System.out.print("| ");
				System.out.print(board[i][j]);
				System.out.print(" |\t");
			}
			System.out.println();
		}
	}

	/**
	 * A getter to return the king piece of a specific color, heavily used while
	 * checking for check, checkmate and stalemate
	 * 
	 * @param color
	 *            : color of the king to be returned
	 * @return : king of the type Pieces
	 */
	Pieces getKing(Color color) {
		for (int i = 0; i < files; i++) {
			for (int j = 0; j < ranks; j++) {
				if (board[i][j] != null) {
					if (board[i][j].getPieceType() == King && board[i][j].getPieceColor() == color) {
						return board[i][j];
					}
				}
			}
		}
		return null;
	}
}