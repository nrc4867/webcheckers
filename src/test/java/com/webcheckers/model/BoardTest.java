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
    public void testConstructor(){
        Board board = new Board(r,w,false);
        assertNotNull(board.getSpaces());
    }

    @Test
    public void testPieces(){
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> b.getPiece(9, 9));
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

        spaces = new Space[SIZE][SIZE+1];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE+1; col++) {
                spaces[row][col] = new Space(0,0);
            }
        }
        b.setSpaces(spaces);
        assertNotEquals(b.getSpaces(), spaces);

        spaces = new Space[SIZE+1][SIZE];
        for (int row = 0; row < SIZE+1; row++) {
            for (int col = 0; col < SIZE; col++) {
                spaces[row][col] = new Space(0,0);
            }
        }
        b.setSpaces(spaces);
        assertNotEquals(b.getSpaces(), spaces);


    }

    @Test
    public void testGetBoardId(){
        assert b.getBoardID() >= 0;
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
        assertThrows(IllegalArgumentException.class , () ->{b.setActiveSpace(0, -1);});
        assertThrows(IllegalArgumentException.class , () ->{b.setActiveSpace(-1, 0);});
        assertThrows(IllegalArgumentException.class , () ->{b.setActiveSpace(100, 100);});
        assertThrows(IllegalArgumentException.class , () ->{b.setActiveSpace(0, 100);});
        assertThrows(IllegalArgumentException.class , () ->{b.setActiveSpace(100, 0);});

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
    public void testIsPlaying(){
        assertTrue(b.isPlaying(w));
        assertTrue(b.isPlaying(r));
        Player f = new Player("testing");
        assertFalse(b.isPlaying(f));
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
        b.nextTurn();
        assertEquals(b.getActivePlayer(), w);
        b.nextTurn();
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
        Player f = new Player("testing");
        assertTrue(b.hasPiece(r));
        assertTrue(b.hasPiece(w));
        assertFalse(b.hasPiece(f));
    }

    @Test
    public void testGetBoardByTurn(){
        assertNotNull(b.getBoardByTurn(0));
    }

    @Test
    public void testGetModeOptions(){
        b.setPlayMode(null);
        assertEquals(b.getModeOptions(), ModeOptions.gameActive().getOptions());
        b.setPlayMode(ModeOptions.gameActive());
        assertEquals(b.getModeOptions(), ModeOptions.gameActive().getOptions());
        b.setPlayMode(ModeOptions.resign(r));
        assertEquals(b.getModeOptions(), ModeOptions.resign(r).getOptions());
        b.setPlayMode(ModeOptions.won(w));
        assertEquals(b.getModeOptions(), ModeOptions.won(w).getOptions());
    }

    @Test
    public void testSetPiece(){
        Piece p = new Piece(0,0, r);
        assertThrows(IllegalArgumentException.class , () ->{b.setPiece(p,-1, -1);});
        assertThrows(IllegalArgumentException.class , () ->{b.setPiece(p,9, 4);});
        assertThrows(IllegalArgumentException.class , () ->{b.setPiece(p,4, 9);});
        assertThrows(IllegalArgumentException.class , () ->{b.setPiece(p,9, 9);});
        b.setPiece(p,0, 0);
        assertTrue(b.hasPiece(0,0));
        assertEquals(b.getPiece(0,0), p);
        b.setActiveSpace(0,0);
        assertEquals(b.getActivePiece(),p);
    }


    @Test
    public void testEquals(){
        Board a = new Board(r,w);
        assertNotEquals(b, r);
        assertNotEquals(a,b);
        assertEquals(a,a);
        assertEquals(b,b);
    }

    @Test
    public void testMisc(){
        System.out.println(b.toString());
        assertEquals(b.hashCode(), b.getBoardID());
    }

}
