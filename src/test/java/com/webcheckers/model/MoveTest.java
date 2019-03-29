package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * Testing class for Move
 *
 * @author Suwamik Paul
 */
@Tag("Model-tier")
public class MoveTest {

    public static Move m = new Move();
    public static Position s = new Position();
    public static Position e = new Position();

    public static void intialize(){
        s.setRow(1);
        s.setCell(1);
        e.setRow(2);
        e.setCell(2);
        m.setStart(s);
        m.setEnd(e);
    }

    @Test
    public void testGetSetStart(){
        intialize();
        assertEquals(m.getStartRow(), s.getRow());
        assertEquals(m.getStartCell(), s.getCell());
        m.setStart(e);
        assertEquals(m.getStartRow(), e.getRow());
        assertEquals(m.getStartCell(), e.getCell());
    }

    @Test
    public void testGetSetEnd(){
        intialize();
        assertEquals(m.getEndRow(), e.getRow());
        assertEquals(m.getEndCell(), e.getCell());
        m.setEnd(s);
        assertEquals(m.getEndRow(), s.getRow());
        assertEquals(m.getEndCell(), s.getCell());
    }

    @Test
    public void testRowDelta(){
        intialize();
        assertEquals(m.rowDelta(), e.getRow() - s.getRow());
    }

    @Test
    public void testCellDelta(){
        intialize();
        assertEquals(m.cellDelta(), e.getCell() - s.getCell());
    }

    @Test
    public void testGetSetMovement(){
        intialize();
        m.setMovement(Move.MoveType.REGULAR);
        assertEquals(m.getMovement(), Move.MoveType.REGULAR);
        m.setMovement(Move.MoveType.JUMP);
        assertEquals(m.getMovement(), Move.MoveType.JUMP);
    }

    @Test
    public void testMisc(){
        intialize();
        System.out.println(m.toString());
        assertEquals(m.hashCode(), Objects.hash(s,e));
    }

}
