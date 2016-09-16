package Controller;

import Model.core.chessGame;
import Model.pieces.Pieces;
import View.chessBoardGUI;
import javax.swing.*;
import java.util.ArrayList;

public class chessController {
    private chessBoardGUI boardGUI;
    private final chessGame game;

    public chessController() {
        game = new chessGame();
        boardGUI = new chessBoardGUI(game);
        addButtonToPieces();
    }

    private void addButtonToPieces() {
        ArrayList<View.chessBoardGUI.piecePanel> pieceList = boardGUI.boardPanel.boardPieces;
        final Pieces[] sourcePiece = {null};
        final int[] destinationX = new int[1];
        final int[] destinationY = new int[1];

        for (chessBoardGUI.piecePanel currentPiece : pieceList) {
            currentPiece.button.addActionListener(e -> {
                if (e.getSource() == currentPiece.button) {
                    int coordinateX = currentPiece.getCoordinateX();
                    int coordinateY = currentPiece.getCoordinateY();

                    System.out.println(game.chess.board[coordinateX][coordinateY] + " pressed at: " + "(" +
                            coordinateX + ", " + coordinateY + ")" + ".");

                    if(sourcePiece[0] == null) sourcePiece[0] = game.chess.board[coordinateX][coordinateY];
                    else{
                        destinationX[0] = coordinateX;
                        destinationY[0] = coordinateY;
                        game.movePieceTo(sourcePiece[0], destinationX[0], destinationY[0]);
                        SwingUtilities.invokeLater(() -> boardGUI.boardPanel.drawBoard(game));
                        //show error messages, if any!
                        if(game.errorMessage != null)  JOptionPane.showMessageDialog(null, game.errorMessage);
                    }
                }
            });
        }
    }
}

