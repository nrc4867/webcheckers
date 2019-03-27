package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webcheckers.model.Cleanup;
import com.webcheckers.util.Attributes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import spark.Session;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * LazySessionWatcher Test
 * @author Abhaya Tamrakar
 */
@Tag("Application-tier")
public class LazySessionWatcherTest {

    private Set<Session> sessions;
    private final int sessionCheckTimer = 1;
    private final int sessionTimeout = sessionCheckTimer;
    private LazySessionWatcher watcher;
    private LazySessionWatcher w2;

    @BeforeEach
    public void setup(){
        sessions=new HashSet<>();
        watcher=new LazySessionWatcher();
        w2=mock(LazySessionWatcher.class);
    }

    @Test
    public void addSessionTest(){
        Session s=mock(Session.class);
        watcher.addSession(s);
        assertTrue(watcher.getSessions().contains(s));
        watcher.prune();
        assertFalse(watcher.getSessions().contains(s));
    }

    @Test
    public void removeAtrributeTest(){
        Session s =mock(Session.class);
        TreeSet<String> attributes = new TreeSet();
        attributes.add(Attributes.PLAYER_SIGNIN_KEY);
        when(s.attributes()).thenReturn(attributes);
        watcher.addSession(s);
        watcher.removeAttributes(s);

    }


}
