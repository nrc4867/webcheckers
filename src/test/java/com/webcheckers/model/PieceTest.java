package test;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board;
import com.webcheckers.appl.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.model.Piece;

@Tag("Model-tier")
public class PieceTest {

    private static final int INBOUNDS = 3;
    private static final int OUTBOUNDS_LOW = -1;
    private static final int OUTBOUNDS_HIGH = Board.getSize()+1;
    private static final Player PLAYER = new Player("Player");


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
            new Piece(OUTBOUNDS_LOW, INBOUNDS, PLAYER);},
            "Piece allowed low column.");
    }


    @Test
    public void ctorBadArgsHighCol() {
        assertThrows(IllegalArgumentException.class, () -> {
                    new Piece(OUTBOUNDS_HIGH, INBOUNDS, PLAYER);},
                "Piece allowed high column.");
    }


    @Test
    public void ctorBadArgsLowRow() {
        assertThrows(IllegalArgumentException.class, () -> {
                    new Piece(INBOUNDS, OUTBOUNDS_LOW, PLAYER);},
                "Piece allowed low row.");
    }


    @Test
    public void ctorBadArgsHighRow() {
        assertThrows(IllegalArgumentException.class, () -> {
                    new Piece(INBOUNDS, OUTBOUNDS_LOW, PLAYER);},
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
}
