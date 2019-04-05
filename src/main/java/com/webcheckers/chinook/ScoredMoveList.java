package com.webcheckers.chinook;

import com.webcheckers.appl.BoardController;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;

import java.util.ArrayList;


/**
 * Creates a list of possible moves and assigns the
 * turn a score based on how effective those moves would be
 * if performed.
 *
 * TODO check if a move would king a piece
 */
public class ScoredMoveList {

    private static final int KING_SCORE = 3;
    private static final int JUMP_SCORE = 2;
    private static final int MOVE_SCORE = 1;
    private static final int EMPTY_SCORE = 0;

    private ArrayList<Move> moves;
    private int score;

    /** Create a ScoredMoveList without any moves. */
    public ScoredMoveList() {
        this(null);
    }

    /** Add all given moves to the scored list. */
    public ScoredMoveList(ArrayList<Move> moves) {

        this.moves = new ArrayList<>();
        if (moves != null) for (Move m : moves) addMove(m);
    }

    /**
     * @param move Move to add.
     * @return Returns the new overall score;
     */
    public int addMove(Move move) {

        moves.add(move);
        score += getMoveScore(move);
        return score;
    }

    /**
     * Given a move, check its effectiveness.
     * @param move Move to check.
     * @return
     */
    public static int getMoveScore(Move move) {

        if (move.getMovement() == Move.MoveType.JUMP)
            return JUMP_SCORE;

        else return MOVE_SCORE;
    }

    /** @return Returns the list of moves. */
    public ArrayList<Move> getMoves() {return moves;}

    /** @return Returns the overall score. */
    public int getScore() {return score;}
}
