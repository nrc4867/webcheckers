package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.Objects;

@Tag("Model-tier")
public class PieceTest {

    private static final int INBOUNDS = 3;
    private static final int OUTBOUNDS_LOW = -1;
    private static final int OUTBOUNDS_HIGH = Board.getSize()+1;
    private static Player PLAYER = new Player("Player", Color.RED);
    private static Piece p = new Piece(0,0, PLAYER, Piece.Type.SINGLE);



    @Test
    public void ctorGood3Args() {
        Piece p =new Piece(INBOUNDS, INBOUNDS, PLAYER);
        assertEquals("Player'S SINGLE PIECE AT (3,3)", p.toString());
    }

    @Test
    public void ctorGood4Args() {
        Piece p = new Piece(INBOUNDS, INBOUNDS, PLAYER, Piece.Type.KING);
        assertEquals("Player'S KING PIECE AT (3,3)", p.toString());
    }

    @Test
    public void ctorBadArgsLowCol() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(INBOUNDS, OUTBOUNDS_LOW, PLAYER);},
            "Piece allowed low column.");
    }


    @Test
    public void ctorBadArgsHighCol() {
        assertThrows(IllegalArgumentException.class, () -> {
                    new Piece(INBOUNDS, OUTBOUNDS_HIGH, PLAYER);},
                "Piece allowed high column.");
    }


    @Test
    public void ctorBadArgsLowRow() {
        assertThrows(IllegalArgumentException.class, () -> {
                    new Piece(OUTBOUNDS_LOW, INBOUNDS, PLAYER);},
                "Piece allowed low row.");
    }


    @Test
    public void ctorBadArgsHighRow() {
        assertThrows(IllegalArgumentException.class, () -> {
                    new Piece(OUTBOUNDS_LOW, INBOUNDS, PLAYER);},
                "Piece allowed high row.");
    }

    @Test
    public void setGoodCol() {
        Piece p = new Piece(INBOUNDS, INBOUNDS, PLAYER);
        p.setCol(INBOUNDS);
        assertEquals(INBOUNDS, p.getCol());
    }

    @Test
    public void setLowCol() {
        Piece p = new Piece(INBOUNDS, INBOUNDS, PLAYER);
        assertThrows(IllegalArgumentException.class, ()-> {
                p.setCol(OUTBOUNDS_LOW);},
                "Piece allowed low column.");
    }

    @Test
    public void setHighCol() {
        Piece p = new Piece(INBOUNDS, INBOUNDS, PLAYER);
        assertThrows(IllegalArgumentException.class, ()-> {
                    p.setCol(OUTBOUNDS_HIGH);},
                "Piece allowed high column.");
    }

    @Test
    public void setGoodRow() {
        Piece p = new Piece(INBOUNDS, INBOUNDS, PLAYER);
        p.setRow(INBOUNDS);
        assertEquals(INBOUNDS, p.getRow());
    }

    @Test
    public void setLowRow() {
        Piece p = new Piece(INBOUNDS, INBOUNDS, PLAYER);
        assertThrows(IllegalArgumentException.class, ()-> {
                    p.setRow(OUTBOUNDS_LOW);},
                "Piece allowed low row.");
    }

    @Test
    public void setHighRow() {
        Piece p = new Piece(INBOUNDS, INBOUNDS, PLAYER);
        assertThrows(IllegalArgumentException.class, ()-> {
                    p.setRow(OUTBOUNDS_HIGH);},
                "Piece allowed high row.");
    }

    @Test
    public void testGetCol(){
        assertEquals(p.getCol(), 0);
        p.setCol(1);
        assertEquals(p.getCol(), 1);
    }

    @Test
    public void testGetRow(){
        assertEquals(p.getRow(), 0);
        p.setRow(1);
        assertEquals(p.getRow(), 1);
    }

    @Test
    public void testGetOwner(){
        assertEquals(p.getOwner(), PLAYER);
    }

    @Test
    public void testGetSetType(){
        assertEquals(p.getType(), Piece.Type.SINGLE);
        p.setType(Piece.Type.KING);
        assertEquals(p.getType(), Piece.Type.KING);
        p.setType(Piece.Type.SINGLE);
        assertEquals(p.getType(), Piece.Type.SINGLE);
    }

    @Test
    public void testGetColor(){
        assertEquals(p.getColor(), PLAYER.getColor());
    }

    @Test
    public void testEnemyOf(){
        Player a = new Player("Testing", Color.WHITE);
        Player b = new Player("Testing2", Color.RED);
        Piece aa = new Piece(0,1,a);
        Piece bb = new Piece(0,2, b);
        assertFalse(p.enemyOf(bb));
        assertTrue(p.enemyOf(aa));
        assertTrue(aa.enemyOf(bb));

    }

    @Test
    public void testEquals(){
        Piece x = new Piece(0,1, PLAYER);
        assertNotEquals(p, x);
        x.setCol(0);
        assertEquals(p,x);
    }

    @Test
    public void testMisc(){
        System.out.println(p.toString());
        assertEquals(p.hashCode(), Objects.hash(PLAYER, Piece.Type.SINGLE, 0, 0));
    }

}
