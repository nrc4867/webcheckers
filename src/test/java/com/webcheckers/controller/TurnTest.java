package com.webcheckers.controller;

import static org.junit.jupiter.api.Assertions.*;


import com.webcheckers.appl.Player;
import com.webcheckers.model.Color;
import com.webcheckers.ui.view.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.webcheckers.ui.view.BoardView.Row;

import java.util.*;

/**
 * Testing class for Turn
 *
 * @author Abhaya Tamrakar
 */
@Tag("UI-tier")
public class TurnTest {

    private int startRow;
    private Turn turn;
    private int startCol;
    private int endRow;
    private int endCol;
    private boolean kinged;
    private boolean jumped;
    private int tn;

    private Turn turn2;

    @BeforeEach
    public void setup(){

        startRow=5;
        startCol=0;
        endCol=1;
        endRow=4;
        kinged=false;
        jumped=false;
        tn=1;
        turn = new Turn(Color.RED,startRow,startCol,endRow,endCol,kinged,jumped,tn);
        turn2 = new Turn(Color.RED,startRow,startCol,endRow,endCol,kinged,jumped,tn);
    }


    @Test
    public void tests(){
        String action = jumped ? "jumped" : "moved";
        String king = kinged ? ", kinging it" : "";

        String check = String.format("%s %s a piece from (%d,%d) to (%d,%d)%s.",
                Color.RED, action, startCol, startRow, endCol, endRow, king);

        assertEquals(check,turn.toString());
        assertTrue(turn.equals(turn2));
        assertFalse(turn.equals(new Player("p1")));


        int hash = Objects.hash(Color.RED,startCol,startRow,endRow,endCol,kinged,jumped,tn);
        assertEquals(hash,turn.hashCode());
        assertNotEquals(hash,"str".hashCode());


    }


}
