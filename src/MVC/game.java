package MVC;

import Controller.chessController;

/**
 * This class connects the whole MVC model together. It initiates a controller class object which then, connects the model component and the view (GUI)
 */
public class game {
    public static void main(String[] args) {
        chessController newGame = new chessController(1, false);
    }
}
