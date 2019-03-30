package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * Testing class for Position
 *
 * @author Suwamik Paul
 */
@Tag("Model-tier")
public class PositionTest {

    Position p = new Position();

    @Test
    public void testGetSetCell(){
        assertEquals(p.getCell(), 0);
        p.setCell(1);
        assertEquals(p.getCell(), 1);
        p.setCell(8);
        assertEquals(p.getCell(), 8);
        p.setCell(-1);
        assertEquals(p.getCell(), -1);
    }

    @Test
    public void testGetSetRow(){
        assertEquals(p.getRow(), 0);
        p.setRow(1);
        assertEquals(p.getRow(), 1);
        p.setRow(8);
        assertEquals(p.getRow(), 8);
        p.setRow(-1);
        assertEquals(p.getRow(), -1);
    }

    @Test
    public void testEquals(){
        Position a = new Position();
        a.setCell(1);
        a.setRow(1);
        assertNotEquals(a,p);
        assertNotEquals(p,a);

        assertEquals(a,a);
        assertEquals(p,p);
    }

    @Test
    public void testMisc(){
        System.out.println(p.toString());
        assertEquals(p.hashCode(), Objects.hash(0,0));
    }
}
