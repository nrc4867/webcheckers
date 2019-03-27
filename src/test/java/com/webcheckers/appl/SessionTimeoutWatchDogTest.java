package com.webcheckers.appl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import javax.servlet.http.HttpSessionBindingEvent;

import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for WatchoutDog
 * @author Abhaya Tamrakar
 */
@Tag("Application-Tier")
public class SessionTimeoutWatchDogTest {

    private PlayerLobby lobby;
    private Player player;
    private SessionTimeoutWatchDog st;

    @BeforeEach
    public void setup(){
        player = new Player("Player1");
        Hashtable<String,Player> ht=new Hashtable<>();
        ht.put("Player1",player);
        lobby = new PlayerLobby(ht);
        st = new SessionTimeoutWatchDog(lobby,player);
    }

    @Test
    public void test(){
        st.valueBound(null);
        assertTrue(lobby.containsPlayer(player));
        st.valueUnbound(null);
        assertFalse(lobby.containsPlayer(player));
    }

}
