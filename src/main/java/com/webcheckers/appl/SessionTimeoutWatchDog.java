package com.webcheckers.appl;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.logging.Logger;

public class SessionTimeoutWatchDog implements HttpSessionBindingListener {
    private static final Logger LOG = Logger.getLogger(SessionTimeoutWatchDog.class.getName());

    /**
     * The length of time given to a player before they are removed from the server
     */
    public static final int maxInactiveInterval = 1;

    private final PlayerLobby lobby;
    private final Player player;

    /**
     * Create a session watch dog to watch for the client to end its session with the server
     *
     * @param lobby the lobby the player is on
     * @param player the player in the lobby
     */
    public SessionTimeoutWatchDog(PlayerLobby lobby, Player player) {
        LOG.info("Session created for " + player.getName());
        this.lobby = lobby;
        this.player = player;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
    }

    /**
     * Watch for the session to end
     *
     * @param event the session is dead
     */
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        // remove the player from the lobby on session timeout
        LOG.info(player + " Disconnected.");
        lobby.removePlayer(player);
    }
}
