package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Testing class for Board
 *
 * @author Suwamik Paul
 */
@Tag("Model-tier")
public class BoardTest {

    private static Player r = new Player("r", Color.RED);
    private static Player w = new Player("w", Color.WHITE);
    private static Board b = new Board(r,w);

    @Test
    public void testTwoArgs(){
        new Board(r,w);
    }

    @Test
    public void testPieces(){
        try {
            b.getPiece(5347, 43);
        }
        catch (Exception e){
            System.out.println(e);
        }
        assertNull(b.getPiece(0,0));

    }

    @Test
    public void testPlayers(){
        assertEquals(b.getWhitePlayer(), w);
        assertEquals(b.getRedPlayer(), r);
        assertEquals(b.getActivePlayer(), r);
        assertEquals(b.getActiveColor(), r.getColor());
    }

    @Test
    public void testInBounds(){
        // Both inbound
        assertTrue(b.inBounds(0,0));
        assertTrue(b.inBounds(7,7));
        // Row out of bounds
        assertFalse(b.inBounds(-1, 0));
        assertFalse(b.inBounds(8,0));
        // Column out of bounds
        assertFalse(b.inBounds(0, -1));
        assertFalse(b.inBounds(0, 8));
        // Both out of bounds
        assertFalse(b.inBounds(8,8));
        assertFalse(b.inBounds(-1,-1));
    }

    @Test
    public void testSwitchActivePlayer(){
        b.switchActivePlayer();
        assertEquals(b.getActivePlayer(), w);
        b.switchActivePlayer();
        assertEquals(b.getActivePlayer(), r);
    }

    @Test
    public void testResign(){
        b.setResign(r);
        assertEquals(b.getResign(), r);
        b.setResign(w);
        assertEquals(b.getResign(), w);
    }

    @Test
    public void testEquals(){
        Board a = new Board(r,w);

        assertNotEquals(a,b);
        assertEquals(a,a);
        assertEquals(b,b);
    }

}
