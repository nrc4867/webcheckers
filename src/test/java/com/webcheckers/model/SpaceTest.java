package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("Model-Test")
public class SpaceTest {

    private static final int INBOUNDS = 3;
    private static final int OUTBOUNDS_LOW = -1;
    private static final int OUTBOUNDS_HIGH = Board.getSize()+1;

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
}
