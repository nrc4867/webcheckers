package com.webcheckers.ui.CheckersPlay;

import com.webcheckers.appl.BoardController;
import com.webcheckers.model.Board;
import com.webcheckers.model.ModeOptions;

/**
 * A pure fabrication to remove creating mode options from a ui class
 *
 * @author Nicholas Chieppa
 */
public class CreateModeOptions {

    static ModeOptions createOptions(BoardController controller) {
        Board board = controller.getBoard();
        System.out.println("Game state: " + controller.getGameState());
        switch (controller.getGameState()){
            case WHITE_WON:
                return ModeOptions.won(board.getWhitePlayer());
            case RED_WON:
                return ModeOptions.won(board.getRedPlayer());
            default:
                return null;
        }
    }
}
