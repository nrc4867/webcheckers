package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Testing class for ReplayModeOptions
 *
 * @author Suwamik Paul
 */
@Tag("Model-tier")
public class ReplayModeOptionsTest {

    public static Board b = new Board(mock(Player.class), mock(Player.class));
    public static ReplayModeOptions r  =ReplayModeOptions.createOptions(b, -1);;

    @Test
    public void testcreateOptions(){
        assertEquals(r.getOptions().get("hasPrevious"), false);
        assertEquals(r.getOptions().get("hasNext"), true);

        b.nextTurn();
        b.nextTurn();

        r = ReplayModeOptions.createOptions(b, 1);
        assertEquals(r.getOptions().get("hasPrevious"), true);
        assertEquals(r.getOptions().get("hasNext"), true);

        r= ReplayModeOptions.createOptions(b, 3);
        assertEquals(r.getOptions().get("hasPrevious"), true);
        assertEquals(r.getOptions().get("hasNext"), false);
    }

    @Test
    public void testGetOptions(){
        assertNotNull(r.getOptions());
    }
}
