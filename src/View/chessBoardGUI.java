package View;

import Model.core.chessBoard;
import Model.core.chessGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class handles the GUI for the board and the pieces on it.
 * The inspiration for this setup was taken from this easy youtube tutorial with certain changes:
 * CITATION: https://www.youtube.com/watch?v=peU0xIhkBws
 */
public class chessBoardGUI {

    private final static Dimension FRAME_DIMENSION = new Dimension(600, 600); //top-level window dimensions
    private final static Dimension BOARD_DIMENSION = new Dimension(400, 350);
    private final static Dimension PIECE_DIMENSION = new Dimension(10, 10); //dimension of each piece which will sit on the board
    private final Color lightPieceColor = Color.decode("#ffce9e");
    private final Color darkPieceColor = Color.decode("#d18b47");
    private final chessGame game;

    /**
     * Class constructor.
     */
    private chessBoardGUI() {
        //first setup the top-level window and initialize and add the panels
        JFrame gameFrame = new JFrame("Chess Board");
        gameFrame.setSize(FRAME_DIMENSION);
        gameFrame.setLayout(new BorderLayout());
        game = new chessGame();
        chessBoardGUI.boardPanel boardPanel = new boardPanel();
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
    }


    /**
     * This class sets up the board handle on the window.
     */
    private class boardPanel extends JPanel {
        final List<piecePanel> boardPieces; //pieces to be added on the board

        /**
         * Class constructor
         */
        boardPanel() {
            //initialize the board and setup the pieces on it.
            super(new GridLayout(8,8));
            this.boardPieces = new ArrayList<>();
            for(int i = chessBoard.files - 1; i>=0; i--) {
                for(int j=0; j < chessBoard.ranks; j++) {
                    final piecePanel pieces = new piecePanel(i, j);
                    this.boardPieces.add(pieces);
                    this.add(pieces);
                }
            }
            setPreferredSize(BOARD_DIMENSION);
            validate();
        }
    }

    /**
     * This class places the pieces on 8x8 board dimension.
     */
    private class piecePanel extends JPanel {
        private final int coordinateX;
        private final int coordinateY;

        /**
         * Class constructor
         * @param coordinateX : x coordinate of piece to be placed.
         * @param coordinateY : y coordinate of piece to be placed.
         */
        piecePanel(final int coordinateX, final int coordinateY) {
            super(new GridBagLayout());
            this.coordinateX = coordinateX;
            this.coordinateY = coordinateY;
            setPreferredSize(PIECE_DIMENSION);
            assignPieceColor();
            assignPieceImage(game.chess);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    System.out.println(game.chess.board[coordinateX][coordinateY] + " pressed at: " + "(" +
                            coordinateX + ", " + coordinateY + ")" + ".");
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });
            validate();
        }

        /**
         * This function assigns each piece tile a color based on its positioning
         */
        private void assignPieceColor() {
            if(this.coordinateX % 2 == 1) {
                setSingleRowPieceColor(lightPieceColor, darkPieceColor);
            } else {
                setSingleRowPieceColor(darkPieceColor, lightPieceColor);
            }
        }

        private void setSingleRowPieceColor(Color darkPieceColor, Color lightPieceColor) {
            if (this.coordinateY % 2 == 0) {
                setBackground(darkPieceColor);
            } else {
                setBackground(lightPieceColor);
            }
        }

        /**
         * This function assigns each piece tile an image based on the piece tile's location
         * @param gameBoard : The gameBoard on which the pieces are placed.
         */
        private void assignPieceImage(final chessBoard gameBoard) {
            this.removeAll();
            if(gameBoard.board[this.coordinateX][this.coordinateY] != null) {
                try {
                    final BufferedImage image = ImageIO.read(getClass().getResource("Assets/" + gameBoard.board[coordinateX][coordinateY] + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Main function to test the GUI setup.
     * @param args
     */
    public static void main(String[] args) {
        chessBoardGUI board = new chessBoardGUI();
    }
}
