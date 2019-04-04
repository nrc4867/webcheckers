package com.webcheckers.chinook;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Board;
import com.webcheckers.model.Move;

import java.util.ArrayList;

/**
 * Chinook is the AI program, named after Chinook,
 * the original Checkers AI grandmaster.
 */
public class Chinook {

    /** The player representing Chinook. */
    private Player player;

    /** The board Chinook is playing on. */
    private Board board;

    public Chinook(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    /**
     * Look at the state of the board and make a move.
     * @return Returns the move to make, or null if
     *         no move can be made.
     *
     * TODO
     */
    public Move makeMove() {

        return null;
    }
}
