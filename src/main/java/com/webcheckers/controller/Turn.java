package com.webcheckers.controller;


import com.webcheckers.model.Color;

import java.util.Objects;

/**
 * Every move a player makes can be stored as a Turn. This will allow
 * the BoardController to be able to undo those moves as a revert,
 * or to log the moves for a replay mode.
 *
 * Objects were not used as variables, only primitives and enums. This
 * is so that you do not need to recreate the board during replay,
 * only provide the default board layout.
 *
 * The downside is that board must remain consistent with the turns in
 * order for the turns to be reverted.
 */
public class Turn {

    // ATTRIBUTES =============================================================

    /** Color of the Player. */
    public final Color PLAYER;

    /** Row that the moving piece began at. */
    public final int START_ROW;

    /** Column that the moving piece began at. */
    public final int START_COL;

    /** Row that the moving piece ended at. */
    public final int END_ROW;

    /** Column that the moving piece ended at. */
    public final int END_COL;

    /** Whether or not the moving piece was kinged during this move. */
    public final boolean KINGED;

    /** Whether or not the moving piece jumped another. */
    public final boolean JUMPED;

    /** What turn this move occured on. */
    public final int TURN_NUMBER;

    // CONSTRUCTORS ===========================================================

    public Turn(Color player,
                int startRow, int startCol,
                int endRow, int endCol,
                boolean kinged,
                boolean jumped,
                int turn) {
        PLAYER = player;
        START_ROW = startRow;
        START_COL = startCol;
        END_ROW = endRow;
        END_COL = endCol;
        KINGED = kinged;
        JUMPED = jumped;
        TURN_NUMBER = turn;
    }

    // OBJECT =================================================================

    @Override
    public String toString() {

        String action = JUMPED ? "jumped" : "moved";
        String king = KINGED ? ", kinging it" : "";

        return String.format("%s %s a piece from (%d,%d) to (%d,%d)%s.",
                PLAYER, action, START_COL, START_ROW, END_COL, END_ROW, king);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Turn)) return false;
        final Turn turn = (Turn) o;

        return PLAYER      == turn.PLAYER
            && START_COL   == turn.START_COL
            && START_ROW   == turn.START_ROW
            && END_COL     == turn.END_COL
            && END_ROW     == turn.END_ROW
            && KINGED      == turn.KINGED
            && JUMPED      == turn.JUMPED
            && TURN_NUMBER == turn.TURN_NUMBER;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PLAYER,
                START_COL,
                START_ROW,
                END_ROW,
                END_COL,
                KINGED,
                JUMPED,
                TURN_NUMBER);
    }
}
