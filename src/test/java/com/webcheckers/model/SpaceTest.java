package com.webcheckers.model;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Test")
public class SpaceTest {

    private static final int INBOUNDS = 3;
    private static final int OUTBOUNDS_LOW = -1;
    private static final int OUTBOUNDS_HIGH = Board.getSize()+1;
    private static Player player = new Player("Testing", Color.RED);
    private static Piece p = new Piece(0,0, player);
    private static Space s = new Space(0,0);

    @Test
    public void ctorGood() {
        Space s = new Space(INBOUNDS, INBOUNDS);
        assertEquals("3,3: empty", s.toString());
    }

    @Test
    public void ctorLowRow() {
        assertThrows(IllegalArgumentException.class, ()-> {
                    new Space(OUTBOUNDS_LOW, INBOUNDS);},
                "Piece allowed low row.");
    }

    @Test
    public void ctorHighRow() {
        assertThrows(IllegalArgumentException.class, ()-> {
                    new Space(OUTBOUNDS_HIGH, INBOUNDS);},
                "Piece allowed high row.");
    }

    @Test
    public void ctorLowCol() {
        assertThrows(IllegalArgumentException.class, ()-> {
                    new Space(INBOUNDS, OUTBOUNDS_LOW);},
                "Piece allowed low col.");
    }

    @Test
    public void ctorHighCol() {
        assertThrows(IllegalArgumentException.class, () -> {
                    new Space(INBOUNDS, OUTBOUNDS_HIGH);},
                "Piece allowed high col.");
    }

    @Test
    public void testGetRow(){
        assertEquals(s.getRowIdx(), 0);
    }

    @Test
    public void testGetCol(){
        assertEquals(s.getCellIdx(), 0);
    }

    @Test
    public void testSetGetPiece(){
        assertNull(s.getPiece());
        s.setPiece(p);
        assertEquals(s.getPiece(), p);
    }

    @Test
    public void testIsWhite(){
        Space f = new Space(0,1);
        assertTrue(s.isWhite());
        assertFalse(f.isWhite());
    }

    @Test
    public void testIsBlack(){
        Space t = new Space(0,1);
        assertFalse(s.isBlack());
        assertTrue(t.isBlack());
    }

    @Test
    public void testIsValid(){
        Space a = new Space(0,0, p);
        Space b = new Space(0,0 );
        Space c = new Space(0, 1, p);
        Space d = new Space(0,1);
        assertFalse(a.isValid());
        assertFalse(b.isValid());
        assertFalse(c.isValid());
        assertTrue(d.isValid());
    }

    @Test
    public void testMisc(){
        System.out.println(s.toString());
        assertEquals(s.hashCode(), Objects.hash(0,0));
    }
}
