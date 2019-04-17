package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
    public void testDeltaRadius(){
        intialize();
        assertTrue(m.deltaRadius(1));

        Move a = new Move();
        Position b = new Position();
        b.setRow(2);
        b.setCell(2);
        Position c = new Position();
        c.setRow(3);
        c.setCell(4);
        a.setStart(b);
        a.setEnd(c);

        assertTrue(a.deltaRadius(1));
        assertTrue(a.deltaRadius(2));
        assertFalse(a.deltaRadius(3));
        assertFalse(a.deltaRadius(4));
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
    public void testGetJumpedPosition(){
        intialize();
        m.setMovement(Move.MoveType.REGULAR);
        assertNull(m.getJumpedPosition());
    }

    @Test
    public void testSameJumpedPosition(){
        intialize();
        Move a = new Move();
        Position b = new Position();
        b.setRow(1);
        b.setCell(1);
        Position c = new Position();
        c.setRow(2);
        c.setCell(2);
        a.setStart(b);
        a.setEnd(c);

        m.setMovement(Move.MoveType.REGULAR);
        assertFalse(m.sameJumpedPosition(a));

        m.setMovement(Move.MoveType.JUMP);
        a.setMovement(Move.MoveType.REGULAR);
        assertFalse(m.sameJumpedPosition(a));
    }

    @Test
    public void testMisc(){
        intialize();
        System.out.println(m.toString());
        assertEquals(m.hashCode(), Objects.hash(s,e));
    }

    @Test
    public void testEquals(){
        Move a = new Move();
        Position b = new Position();
        b.setRow(1);
        b.setCell(1);
        Position c = new Position();
        c.setRow(2);
        c.setCell(2);
        a.setStart(b);
        a.setEnd(c);
        intialize();
        assertNotEquals(m, s);
        assertEquals(m,a);

        c.setRow(3);
        a.setEnd(c);
        assertNotEquals(m,a);

        b.setRow(4);
        a.setStart(b);
        assertNotEquals(m,a);

        c.setRow(2);
        a.setEnd(c);
        assertNotEquals(m,a);

        assertEquals(m,m);
    }

    @Test
    public void testConnectedMoves(){
        Move a = new Move();
        Position b = new Position();
        b.setRow(2);
        b.setCell(2);
        Position c = new Position();
        c.setRow(4);
        c.setCell(4);
        a.setStart(b);
        a.setEnd(c);
        intialize();
        assertTrue(Move.ConnectedMoves(m,a));
        assertFalse(Move.ConnectedMoves(a,m));
        b.setCell(6);
        assertFalse(Move.ConnectedMoves(m,a));
    }

    @Test
    public void testConnectedMovesArray(){
        ArrayList<Move> arr = new ArrayList<>();
        assertTrue(Move.ConnectedMoves(arr));

        intialize();
        arr.add(m);
        assertTrue(Move.ConnectedMoves(arr));

        Move a = new Move();
        Position b = new Position();
        b.setRow(2);
        b.setCell(2);
        Position c = new Position();
        c.setRow(4);
        c.setCell(4);
        a.setStart(b);
        a.setEnd(c);
        arr.add(a);


        Move x = new Move();
        Position y= new Position();
        y.setRow(4);
        y.setCell(4);
        Position z = new Position();
        z.setRow(7);
        z.setRow(7);
        x.setStart(y);
        x.setEnd(z);
        arr.add(x);

        assertTrue(Move.ConnectedMoves(arr));
        b.setRow(4);
        assertFalse(Move.ConnectedMoves(arr));

    }

    @Test
    public void testReenters(){
        ArrayList<Move> arr = new ArrayList<>();
        Move a = new Move();
        Position b = new Position();
        b.setRow(2);
        b.setCell(2);
        Position c = new Position();
        c.setRow(2);
        c.setCell(2);
        a.setStart(b);
        a.setEnd(c);
        arr.add(a);

        assertTrue(a.reenters(arr));
    }

    @Test
    public void testGetLast(){
        ArrayList<Move> arr = new ArrayList<>();
        assertNull(Move.getLast(arr));

        Move a = new Move();
        Position b = new Position();
        b.setRow(2);
        b.setCell(2);
        Position c = new Position();
        c.setRow(2);
        c.setCell(2);
        a.setStart(b);
        a.setEnd(c);
        arr.add(a);

        assertEquals(a, Move.getLast(arr));
    }

}
