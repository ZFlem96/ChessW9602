package View;

import Model.core.chessBoard;
import Model.core.chessGame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class handles the GUI for the board and the pieces on it.
 */
public class chessBoardGUI {

    private final static Dimension FRAME_DIMENSION = new Dimension(600, 600); //top-level window dimensions
    private final static Dimension BOARD_DIMENSION = new Dimension(600, 600);
    private final static Dimension PIECE_DIMENSION = new Dimension(10, 10); //dimension of each piece which will sit on the board
    private final Color lightPieceColor = Color.decode("#ffce9e");
    private final Color darkPieceColor = Color.decode("#d18b47");
    public chessBoardGUI.boardPanel boardPanel;

    /**
     * Class constructor.
     * @param game : the current chess game that we're setting up the GUI for
     */
    public chessBoardGUI(chessGame game) {
        JFrame gameFrame = new JFrame("Chess Board"); //first setup the top-level window and initialize and add the panels
        gameFrame.setSize(FRAME_DIMENSION);
        gameFrame.setLayout(new BorderLayout());
        boardPanel = new boardPanel(game);
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
    }


    /**
     * This class sets up the board handle on the window.
     */
    public class boardPanel extends JPanel {
        public final ArrayList<piecePanel> boardPieces; //pieces to be added on the board

        /**
         * Class constructor
         * @param game : the current chess game that we're setting up the GUI for
         */
        boardPanel(chessGame game) {
            //initialize the board and setup the pieces on it.
            super(new GridLayout(8,8));
            this.boardPieces = new ArrayList<>();
            for(int i = chessBoard.files - 1; i>=0; i--) {
                for(int j=0; j < chessBoard.ranks; j++) {
                    final piecePanel pieces = new piecePanel(game, i, j);
                    this.boardPieces.add(pieces);
                    this.add(pieces);
                }
            }
            setPreferredSize(BOARD_DIMENSION);
            validate();
        }

        /**
         * A function which re-draws the board whenever a move is made. Basically, it redraws the pieces again according to new coordinates.
         * @param game : current game
         */
        public void drawBoard(chessGame game) {
            for(final piecePanel piece : boardPieces) {
                piece.drawPiece(game);
                add(piece);
            }
            validate();
            repaint();
        }
    }

    /**
     * This class places the pieces on 8x8 board dimension.
     */
    public class piecePanel extends JPanel {
        private final int coordinateX;
        private final int coordinateY;
        public JButton button;

        /**
         * Class constructor
         * @param game : the current chess game that we're setting up the GUI for
         * @param coordinateX : x coordinate of piece to be placed.
         * @param coordinateY : y coordinate of piece to be placed.
         */
        piecePanel(chessGame game, final int coordinateX, final int coordinateY) {
            super(new GridBagLayout());
            this.removeAll();
            this.coordinateX = coordinateX;
            this.coordinateY = coordinateY;
            setPreferredSize(PIECE_DIMENSION);
            assignPieceColor();
             assignButtonToPiece(game);
            validate();

        }

        /**
         * This function adds a button to each piece and removes its default background and border and places a piece image instead.
         * @param game : current game on which the pieces are being added
         */
        private void assignButtonToPiece(chessGame game) {
            button = new JButton();
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            if(game.chess.board[this.coordinateX][this.coordinateY] != null) {
                try {
                    final BufferedImage image = ImageIO.read(getClass().getResource("Assets/" + game.chess.board[coordinateX][coordinateY] + ".png"));
                    button.setIcon(new ImageIcon(image));
                    button.setBorder(null);
                    button.setBackground(null);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            this.add(button);
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

        /**
         * This function sets the color for each row on the board
         * @param darkPieceColor : dark brown color
         * @param lightPieceColor : light brown color
         */
        private void setSingleRowPieceColor(Color darkPieceColor, Color lightPieceColor) {
            if (this.coordinateY % 2 == 0) {
                setBackground(darkPieceColor);
            } else {
                setBackground(lightPieceColor);
            }
        }

        /**
         * A function which redraws all the pieces whenever a move is made
         * @param game : current game
         */
        private void drawPiece(chessGame game) {
            this.removeAll();
            assignPieceColor();
            assignButtonToPiece(game);
            validate();
            repaint();
        }

        /**
         * getter for the x coordinate of current piece
         * @return x coordinate of piece
         */
        public int getCoordinateX() {
            return coordinateX;
        }

        /**
         * getter for the y coordinate of current piece
         * @return y coordinate of the piece
         */
        public int getCoordinateY() {
            return coordinateY;
        }

    }
}
