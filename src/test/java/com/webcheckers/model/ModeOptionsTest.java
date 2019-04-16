package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import com.webcheckers.ui.view.Mode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Testing class for ModeOptions
 *
 * @author Suwamik Paul
 */
@Tag("Model-tier")
public class ModeOptionsTest {

    private static ModeOptions m;
    private static Player p = new Player("Testing");


    @Test
    public void testGameActive(){
        m = ModeOptions.gameActive();
        assertEquals(ModeOptions.gameActive().getOptions(), m.getOptions());
    }

    @Test
    public void testWon(){
        m = ModeOptions.won(p);
        assertEquals(ModeOptions.won(p).getOptions(), m.getOptions());
    }

    @Test
    public void testResign(){
        m = ModeOptions.resign(p);
        assertEquals(ModeOptions.resign(p).getOptions(), m.getOptions());
    }

    @Test
    public void testEquals(){
        ModeOptions n = ModeOptions.gameActive();
        m = ModeOptions.gameActive();
        assertNotEquals(m, p);
        assertEquals(m, m);
        assertEquals(m.getOptions(),n.getOptions());
    }

    @Test
    public void testsHashCode(){
        System.out.println(m.hashCode());
    }
}
