package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.appl.Player;
import com.webcheckers.ui.view.BoardView;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Testing class for BoardView
 *
 * @author Suwamik Paul
 */
@Tag("UI-tier")
public class BoardViewTest {

    private static Player r = new Player("r", Color.RED);
    private static Player w = new Player("w", Color.WHITE);
    private static Board b = new Board(r,w);

    @Test
    public void testCreation(){
        BoardView x = new BoardView(b, false);
        BoardView y = new BoardView(b, true);
        assertNotNull(x);
        assertNotNull(y);
    }

    @Test
    public void testIterator(){
        BoardView x = new BoardView(b, false);
        assertNotNull(x.iterator());
    }

}
