package com.webcheckers.util;

import spark.Session;

/**
 * This class contains functions for managing checkers-spectators
 */
public abstract class Spectators {

    /**
     * set the turn of a spectator
     * @param spectatorSession the session of the spectator
     * @param turn the turn to set the spectator to
     */
    public static void setTurn(Session spectatorSession, int turn) {
        spectatorSession.attribute(Attributes.SPECTATOR_TURN_KEY, turn);
    }

    /**
     * get the last move the spectator saw
     * @param spectatorSession the session of the spectator
     * @return the last move or 0 if none assigned
     */
    public static int getTurn(Session spectatorSession) {
        if(spectatorSession.attribute(Attributes.SPECTATOR_TURN_KEY) == null) return 0;
        return spectatorSession.attribute(Attributes.SPECTATOR_TURN_KEY);
    }
}
