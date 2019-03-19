package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private static Player r = new Player("r", Color.RED);
    private static Player w = new Player("w", Color.WHITE);

    @Test
    public void testTwoArgs(){
        new Board(r,w);
    }

    @Test
    public void testPlayers(){
        Board b = new Board(r,w);
        assertEquals(b.getWhitePlayer(), w);
        assertEquals(b.getRedPlayer(), r);
        assertEquals(b.getActivePlayer(), r);
        assertEquals(b.getActiveColor(), r.getColor());
    }

    @Test
    public void testInBounds(){
        Board b = new Board(r,w);
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
        Board b = new Board(r,w);
        b.switchActivePlayer();
        assertEquals(b.getActivePlayer(), w);
        b.switchActivePlayer();
        assertEquals(b.getActivePlayer(), r);
    }

}
