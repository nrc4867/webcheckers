package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Attributes;
import spark.Session;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A session watcher that periodically checks for
 * old sessions then removes them
 *
 * @author Nicholas Chieppa
 */
public class LazySessionWatcher extends Thread {
    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

    /**
     * How often the sessions are
     */
    private final int sessionCheckTimer = 30 * 1000;
    /**
     * The min amount of time a session has to report before it is removed
     */
    private final int sessionTimeout = sessionCheckTimer;


    private final PlayerLobby lobby;
    private final Set<Session> sessions = new HashSet<>();

    public LazySessionWatcher(final PlayerLobby lobby) {
        this.lobby = Objects.requireNonNull(lobby, "Playerlobby cannont be null");
    }

    @Override
    public void run() {
        LOG.info("Session watcher started");
        while (true) {
            try {
                sleep(sessionCheckTimer);
                synchronized (sessions) {
                    prune();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add a session to watch
     * @param session the watched session
     */
    public synchronized void addSession(Session session) {
        LOG.info("Session <" + session.id() + "> Added to watch");
        sessions.add(session);
    }

    /**
     * Remove any sessions that are past session timeout
     */
    private synchronized void prune() {
        for (Session session: sessions) {
            if (Instant.now().toEpochMilli() - session.lastAccessedTime() > sessionTimeout) {
                Player player = session.attribute(Attributes.PLAYER_SIGNIN_KEY);
                if(player != null) lobby.removePlayer(player);
                /**
                 * Clear any boards the player may be on
                 */
                LOG.info("Session <" + session.id() + "> Timeout");
                session.invalidate();
                sessions.remove(session);
            }
        }
    }
}
