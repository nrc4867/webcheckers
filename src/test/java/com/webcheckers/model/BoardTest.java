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
    public void testSetActivePlayer(){
        assertEquals(b.getActivePlayer(), r);
        b.setActivePlayer(w);
        assertEquals(b.getActivePlayer(), w);
        b.setActivePlayer(r);
        assertEquals(b.getActivePlayer(), r);
    }

    @Test
    public void testGetSpaces(){
        int SIZE = 8;
        Space[][] spaces = new Space[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                spaces[row][col] = new Space(row, col);
            }
        }
        for (int i = 1; i < SIZE; i+=2) {
            spaces[0][i].setPiece(new Piece(0, i, w));
        }
        for (int i = 0; i < SIZE; i+=2) {
            spaces[1][i].setPiece(new Piece(1, i, w));
        }
        for (int i = 1; i < SIZE; i+=2) {
            spaces[2][i].setPiece(new Piece(2, i, w));
        }

        for (int i = 0; i < SIZE; i+=2) {
            spaces[SIZE-3][i].setPiece(new Piece(SIZE-3, i, r));
        }
        for (int i = 1; i < SIZE; i+=2) {
            spaces[SIZE-2][i].setPiece(new Piece(SIZE-2, i, r));
        }
        for (int i = 0; i < SIZE; i+=2) {
            spaces[SIZE-1][i].setPiece(new Piece(SIZE-1, i, r));
        }

        Space[][] spaces1 = b.getSpaces();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                assertEquals(spaces[row][col], spaces1[row][col]);
            }
        }
    }

    @Test void testSetSpaces(){
        int SIZE = 8;
        Space[][] spaces = new Space[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                spaces[row][col] = new Space(row, col);
            }
        }
        b.setSpaces(spaces);
        assertEquals(b.getSpaces(), spaces);
    }

    @Test
    public void testGetTotalSize() {
        int SIZE = 8;
        assertEquals(Board.getTotalSpaces(), SIZE * SIZE);
    }

    @Test
    public void testGetActivePiece(){
        Piece p = new Piece(0,1,w);
        b.setActiveSpace(0,1);
        b.setPiece(p, 0,1);
        assertEquals(b.getActivePiece(), p);
    }

    @Test
    public void testSetActiveSpace(){
        assertThrows(IllegalArgumentException.class , () ->{b.setActiveSpace(-1, -1);});
        b.setActiveSpace(0,0);
        assertEquals(b.getActiveSpace(), b.getSpaces()[0][0]);
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
    public void testHasPiece(){
        assertThrows(IllegalArgumentException.class , () ->{b.hasPiece(-1, -1);});
        assertThrows(IllegalArgumentException.class , () ->{b.hasPiece(9, 4);});
        assertThrows(IllegalArgumentException.class , () ->{b.hasPiece(4, 9);});
        assertThrows(IllegalArgumentException.class , () ->{b.hasPiece(9, 9);});
        assertFalse(b.hasPiece(0,0));
        assertTrue(b.hasPiece(0,1));
    }

    @Test
    public void testSetPiece(){
        Piece p = new Piece(0,0, r);
        b.setPiece(p,0, 0);
        assertTrue(b.hasPiece(0,0));
        assertEquals(b.getPiece(0,0), p);
        b.setActiveSpace(0,0);
        assertEquals(b.getActivePiece(),p);
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
