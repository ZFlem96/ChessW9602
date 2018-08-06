package View;

import Controller.chessController;
import Model.core.chessBoard;
import Model.core.chessGame;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 * This class handles the GUI for the board and the pieces on it.
 */
public class chessBoardGUI {

    private final static Dimension FRAME_DIMENSION = new Dimension(800, 800); //top-level window dimensions
    private final static Dimension BOARD_DIMENSION = new Dimension(800, 800);
    private final static Dimension PIECE_DIMENSION = new Dimension(10, 10); //dimension of each piece which will sit on the board
    private final Color lightPieceColor = Color.decode("#ffce9e");
    private final Color darkPieceColor = Color.decode("#d18b47");
    public chessBoardGUI.boardPanel boardPanel;
    public JFrame gameFrame;
    private boolean gamesIs960 = false;
    private int alternatePieces = 1;
    /**
     * Class constructor.
     * @param game : the current chess game that we're setting up the GUI for
     */
    public chessBoardGUI(chessGame game) {
        //take user input for player names
        game.playerOneName = askName("Player 1's (white pieces) name");
        while(game.playerOneName == null || game.playerOneName.isEmpty()) game.playerOneName = askName("CANNOT LEAVE BLANK, Enter player 1's name");
        game.playerTwoName = askName("Player 2's (black pieces) name");
        while(game.playerTwoName == null || game.playerTwoName.isEmpty()) game.playerTwoName = askName("CANNOT LEAVE BLANK, Enter player 2's name");
        //check for uniqueness of names
        game.chess.setIs960Game(askGameType(game.chess));
        while(game.playerTwoName != null && game.playerTwoName.equals(game.playerOneName)) {
                JOptionPane.showMessageDialog(null, "Player 1 has already taken that name, please choose a different one.", "NAME ERROR", JOptionPane.ERROR_MESSAGE);
                game.playerTwoName = askName("Player 2's (black pieces) name");
        }

        resetBoard(game);
    }

    /**
     * A function to reset the board
     * @param game : current game being played
     */
    private void resetBoard(chessGame game) {
        //first setup the top-level window and initialize and add the panels
        gameFrame = new JFrame("Chess Board");
        gameFrame.setSize(FRAME_DIMENSION);
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        //Warn the user if they try to close the game window
        gameFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int yesOrNo = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to quit the game?", "ALL PROGRESS WILL BE LOST",
                        JOptionPane.YES_NO_OPTION);
                if (yesOrNo == JOptionPane.YES_OPTION) {
                    gameFrame.dispose();
                    System.exit(0);
                }
            }
        });

        //add the name labels to the frame
        gameFrame.add(new JLabel(game.playerOneName, SwingConstants.CENTER), BorderLayout.SOUTH);
        gameFrame.add(new JLabel(game.playerTwoName, SwingConstants.CENTER), BorderLayout.NORTH);

        boardPanel = new boardPanel(game);
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        boardPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        //add the menu bar for extra functionality
        final JMenuBar tableMenuBar = new JMenuBar();
        addMenuBarEntries(game, tableMenuBar);
        gameFrame.setJMenuBar(tableMenuBar);

        //make the window visible
        gameFrame.setVisible(true);
    }

    /**
     * A function which asks the players their names
     * @param title : title of the dialog box
     * @return : name entered by the user
     */
    private String askName(String title) {
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Your name: ");
        JTextField text = new JTextField(10);
        panel.add(label);
        panel.add(text);
        int selectedOption = JOptionPane.showOptionDialog(null, panel, title,
                JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options , options[0]);

        if(selectedOption == 0)
        {
            return text.getText();
        }
        return null;
    }
    
    private boolean askGameType(chessBoard game) {
        String[] options = {"Regular","960"};
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Which Game Type would you prefer?");
        panel.add(label);
        boolean result = false;
         int selectedOption = JOptionPane.showOptionDialog(null, panel, null,
                JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options , options[0]);

        if(selectedOption == 1)
        {
            result = true;
        } 
        return result;
    }

    /**
     * Adds menu options to the menu bar
     * @param tableMenuBar : top-level menu bar on which options are being added
     */
    private void addMenuBarEntries(chessGame game, JMenuBar tableMenuBar) {
        JMenu options = getOptionsMenu(game);
        tableMenuBar.add(options);

        JMenu preferences = getPreferencesMenu(game);
        tableMenuBar.add(preferences);
    }


    /**
     * A method which populates the option menu on the JMenu
     * @param game : current game
     * @return return JMenu 'Options' menu
     */
    private JMenu getOptionsMenu(chessGame game) {
        JMenu options = new JMenu("Options");

        JMenuItem forfeit = new JMenuItem("Forfeit", KeyEvent.VK_ESCAPE);
        forfeit.addActionListener(e -> showDialogBox(game, "FORFEIT", "Are you sure?"));
        options.add(forfeit);

        JMenuItem restart = new JMenuItem("Restart", KeyEvent.VK_R);
        restart.addActionListener(e -> {
            if(game.isBlackTurn) showDialogBox(game, "RESTART", "Is " + game.playerOneName + " sure?");
            else showDialogBox(game, "RESTART", "Is " + game.playerTwoName + " sure?");
        });
        options.add(restart);

        JMenuItem score = new JMenuItem("Score", KeyEvent.VK_S);
        score.addActionListener(e -> {
            JPanel scorePanel = new JPanel();
            JLabel player1Label = new JLabel(game.playerOneName +"'s score is: " + game.chess.player1.score);
            JLabel player2Label = new JLabel(game.playerTwoName + "'s score is: " + game.chess.player2.score);
            scorePanel.add(player1Label);
            scorePanel.add(player2Label);
            JOptionPane.showMessageDialog(null, scorePanel, "SCORE", JOptionPane.INFORMATION_MESSAGE);
        });
        options.add(score);

        JMenuItem undo = new JMenuItem("Undo last move", KeyEvent.VK_U);
        undo.addActionListener(e -> {
            game.undoLastMove();
            SwingUtilities.invokeLater(() -> boardPanel.drawBoard(game));
        });
        options.add(undo);

        return options;
    }


    /**
     * A method which populates the Preferences menu
     * @param game : current game
     * @return : return JMenu 'Preferences' menu
     */
    private JMenu getPreferencesMenu(chessGame game) {
        JMenu preferences = new JMenu("Preferences");
        JMenuItem isAlternatePieces = new JMenuItem("Play with fairy pieces..", KeyEvent.VK_ALT);
        isAlternatePieces.addActionListener(e -> showDialogBox(game, "FAIRY PIECES","Are you sure? Current game progress will be lost."));
        preferences.add(isAlternatePieces);
        JMenuItem is960Game = new JMenuItem("Play 960 style..",KeyEvent.VK_ALT);
        is960Game.addActionListener(e -> showDialogBox(game, "960 STYLE","Are you sure? Current game progress will be lost."));
        preferences.add(is960Game);
        JMenuItem regularGame = new JMenuItem("Play Regular Game",KeyEvent.VK_ALT);
        regularGame.addActionListener(e -> showDialogBox(game, "REGULAR GAME","Are you sure? Current game progress will be lost."));
        preferences.add(regularGame);
        return preferences;
    }

    /**
     * A helper function for showing a warning dialog box
     * @param game : current game
     * @param windowTitle : dialog box title
     * @param labelText : label message text
     */
    private void showDialogBox(chessGame game, String windowTitle, String labelText) {
        String[] options = {"YES", "NO"};
        JPanel panel = new JPanel();
        JLabel label = new JLabel(labelText);
        panel.add(label);
        int selectedOption = JOptionPane.showOptionDialog(null, panel, windowTitle,
                JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options);

        //if the user pressed YES, do something
        if(selectedOption == 0)
        {
            if(windowTitle.compareTo("FORFEIT") == 0) {
                //update the player scores
                if (game.isBlackTurn) {
                    game.chess.player1.score++;
                } else game.chess.player2.score++;

                gameFrame.dispose();
                System.exit(0);

            } else if(windowTitle.compareTo("RESTART") == 0) {
                gameFrame.dispose();
                new chessController(alternatePieces, gamesIs960);
            } else if(windowTitle.compareTo("FAIRY PIECES") == 0) {
                gameFrame.dispose();
                alternatePieces = 0;
                new chessController(alternatePieces, gamesIs960);
            } else if (windowTitle.compareTo("960 STYLE") == 0) {
                    gameFrame.dispose();
                    gamesIs960 = true;
                    new chessController(alternatePieces, gamesIs960);
            } else if (windowTitle.compareTo("REGULAR GAME") == 0) {
                gameFrame.dispose();
                gamesIs960 = false;
                alternatePieces = 1;
                new chessController(alternatePieces, gamesIs960);
        }
        }
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
            assignPieceImage(game.chess);
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
         * This function assigns each piece tile an image based on the piece tile's location
         * @param gameBoard : The gameBoard on which the pieces are placed.
         */
        private void assignPieceImage(final chessBoard gameBoard) {
            this.removeAll();
            if(gameBoard.board[this.coordinateX][this.coordinateY] != null) {
                try {
                    final BufferedImage image = ImageIO.read(getClass().getResource("Assets/" + gameBoard.board[coordinateX][coordinateY] + ".png"));
                    add(new JLabel(new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH))));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * A function which redraws all the pieces whenever a move is made
         * @param game : current game
         */
        private void drawPiece(chessGame game) {
            this.removeAll();
            assignPieceColor();
            assignPieceImage(game.chess);
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
