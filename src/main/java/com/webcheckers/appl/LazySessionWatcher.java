package com.webcheckers.appl;

import com.webcheckers.model.Cleanup;
import com.webcheckers.ui.WebServer;
import spark.Session;

import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
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
    private final int sessionCheckTimer = 500 * 1000;
    /**
     * The min amount of time a session has to report before it is removed
     */
    private final int sessionTimeout = sessionCheckTimer;


    private final Set<Session> sessions = new HashSet<>();

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

    public Set<Session> getSessions() {
        return sessions;
    }


    // Add Session to watch
    public synchronized void addSession(Session session) {
        LOG.info("Session <" + session.id() + "> Added to watch");
        sessions.add(session);
    }

    /**
     * Remove any sessions that are past session timeout
     */
    public synchronized void prune() {
        Iterator<Session> itr = sessions.iterator();
        while(itr.hasNext()){
            Session session = itr.next();
            if (Instant.now().toEpochMilli() - session.lastAccessedTime() > sessionTimeout) {
                LOG.info("Session <" + session.id() + "> Timeout");
                removeAttributes(session);
                session.invalidate();
                itr.remove();
            }
        }
    }

    public synchronized void removeAttributes(Session session) {
        for (String attr : session.attributes()) {
            if (session.attribute(attr) instanceof Cleanup)
                ((Cleanup) session.attribute(attr)).cleanup();
        }
    }
}
