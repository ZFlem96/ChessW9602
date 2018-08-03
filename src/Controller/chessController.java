package Controller;

import Model.core.chessGame;
import Model.pieces.Pieces;
import View.chessBoardGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 * A class which connects the model and the view, the controller class.
 */
public class chessController {
    private chessBoardGUI boardGUI; //the view
    private final chessGame game; //the model

    /**
     * Class constructor
     */
    public chessController(int isAlternatePiece, boolean is960Game) {
    	game = new chessGame(isAlternatePiece, is960Game);
        boardGUI = new chessBoardGUI(game);
        gameLoop();
    }

    /**
     * The game loop which makes the pieces move
     */
    private void gameLoop() {
        ArrayList<View.chessBoardGUI.piecePanel> pieceList = boardGUI.boardPanel.boardPieces;
        final Pieces[] sourcePiece = {null}; //this is the piece user will select to move
        final int[] destinationX = new int[1]; //the destination coordinates of the piece
        final int[] destinationY = new int[1];

        //iterate through the piece panels and add a mouse event to each
        for(chessBoardGUI.piecePanel currentPiece : pieceList) {
            currentPiece.addMouseListener(new MouseListener() {
                //store the piece current coordinate
                int coordinateX = currentPiece.getCoordinateX();
                int coordinateY = currentPiece.getCoordinateY();

                @Override
                public void mouseClicked(final MouseEvent e) {

                    //display green border when a piece is clicked
                    currentPiece.setBorder(BorderFactory.createLineBorder(java.awt.Color.GREEN));

                    //player can undo current piece selection by clicking the right mouse button
                    if (isRightMouseButton(e)) {
                        sourcePiece[0] = null;
                        currentPiece.setBorder(new EmptyBorder(0, 0, 0, 0));
                    } else {
                        //means its the user's first click
                        if (sourcePiece[0] == null) {
                            sourcePiece[0] = game.chess.board[coordinateX][coordinateY];
                        } else {
                            //means it's the user's second click (selecting the destination)
                            destinationX[0] = coordinateX;
                            destinationY[0] = coordinateY;

                            game.movePieceTo(sourcePiece[0], destinationX[0], destinationY[0]);
                            SwingUtilities.invokeLater(() -> boardGUI.boardPanel.drawBoard(game));

                            //show error messages, if any!
                            if (game.errorMessage != null)
                                JOptionPane.showMessageDialog(null, game.errorMessage, "MOVE ERROR", JOptionPane.ERROR_MESSAGE);

                            //show game ending messages, if any!
                            if (game.endGameMessage != null) {
                                String[] options = {"YES", "NO"};
                                JPanel panel = new JPanel();
                                JLabel label = new JLabel(game.endGameMessage);
                                panel.add(label);
                                int selectedOption = JOptionPane.showOptionDialog(null, panel, "GAME OVER",
                                        JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options);

                                //if the user pressed YES, do something
                                if (selectedOption == 0) {
                                    boardGUI.gameFrame.dispose();
                                    new chessController(1,game.chess.getIs960Game());
                                } else if (selectedOption == 1) {
                                    boardGUI.gameFrame.dispose();
                                    System.exit(0);
                                }
                            }
                            sourcePiece[0] = null;
                        }
                    }
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                    currentPiece.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    currentPiece.setBorder(new EmptyBorder(0, 0, 0, 0));
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }
            });
        }
    }
}
