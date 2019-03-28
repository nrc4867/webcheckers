package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.appl.Player;
import com.webcheckers.ui.view.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.webcheckers.ui.view.BoardView.Row;

/**
 * Testing class for BoardView
 *
 * @author Suwamik Paul
 */
@Tag("UI-tier")
public class BoardViewTest {

    private static Player r;
    private static Player w;
    private static Board b;
    private BoardView.Row row2;
    private BoardView.Row row3;



    @BeforeEach
    public void setup(){
        r = new Player("r", Color.RED);
        w  = new Player("w", Color.WHITE);
        b = new Board(r,w);
        row2 = new BoardView(b,true).new Row(2,b,true);
        row3 = new BoardView(b,true).new Row(2,b,true);


    }
    @Test
    public void testCreation(){
        BoardView x = new BoardView(b, false);
        BoardView y = new BoardView(b, true);
        assertThrows(IllegalArgumentException.class, () -> {
            BoardView.Row row= new BoardView(b,true).new Row(-1,b,true);},"Invalid index");
        assertThrows(IllegalArgumentException.class, () -> {
            BoardView.Row row= new BoardView(b,true).new Row(10,b,true);},"Invalid index");


        assertNotNull(x);
        assertNotNull(y);
    }

    @Test
    public void testIterator(){
        BoardView x = new BoardView(b, false);
        assertNotNull(x.iterator());
        assertNotNull(row2.iterator());
    }


    @Test
    public void overridingTests(){
        assertEquals(row2.getIndex(),2);
        assertEquals(row2.hashCode(),2);
        assertTrue(row2.equals(row3));
        assertTrue(row2.equals(row2));
        assertFalse(row2.equals(b));

    }


}
