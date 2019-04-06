package com.webcheckers.appl.chinook;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Tag ("chinook-tier")
public class ScoredMoveListTest {

    private static final Move REG = new Move();
    private static final Move JMP = new Move();

    static {
        REG.setMovement(Move.MoveType.REGULAR);
        JMP.setMovement(Move.MoveType.JUMP);
    }

    @Test
    public void moveScores() {

        assertEquals(ScoredMoveList.MOVE_SCORE,
                     ScoredMoveList.getMoveScore(REG));

        assertEquals(ScoredMoveList.JUMP_SCORE,
                     ScoredMoveList.getMoveScore(JMP));
    }

    @Test
    public void constructor() {

        ScoredMoveList list = new ScoredMoveList();
        assertEquals(0, list.getScore());
        assertEquals(0, list.getMoves().size());

        ArrayList<Move> moves = new ArrayList<>();
        moves.add(REG);
        moves.add(JMP);
        list = new ScoredMoveList(moves);
        assertEquals(ScoredMoveList.MOVE_SCORE + ScoredMoveList.JUMP_SCORE,
                     list.getScore());
        assertEquals(2, list.getMoves().size());
    }
}
