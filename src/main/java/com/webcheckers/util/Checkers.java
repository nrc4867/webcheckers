package com.webcheckers.util;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Move;
import spark.Session;

import java.util.ArrayList;

/**
 * Contains functions for managing checkers-players
 *
 * @author Nicholas Chieppa
 */
public abstract class Checkers {

    /**
     * Get the player from a https request
     * @param playerSession the session
     * @return a player object living on the session or null
     */
    public static Player getPlayer(Session playerSession) {
        return playerSession.attribute(Attributes.PLAYER_SIGNIN_KEY);
    }

    /**
     * See if a player is in a game
     * @param player the player
     * @return true if the player is playing a game
     */
    public static boolean playerInGame(Player player) {
        return (player != null) && player.inGame();
    }

    /**
     * See if the player has a board independently if they are playing a game
     * @param player the player
     * @return true if the player is playing a game
     */
    public static boolean playerHasBoard(Player player) {
        return (player != null) && player.getBoard() != null;
    }

    /**
     * See if a player is in a game
     * @param session the session
     * @return true if the session is playing a game
     */
    public static boolean playerInGame(Session session) {
        return  playerInGame(getPlayer(session));
    }

    /**
     * Get the moves that the player has made this turn
     * @param playerSession the session that has made the moves
     * @return the list of moves, may be empty
     */
    public static ArrayList<Move> getMoves(Session playerSession) {
        if(playerSession.attribute(Attributes.PLAYER_MOVES_KEY) == null) {
            playerSession.attribute(Attributes.PLAYER_MOVES_KEY, new ArrayList<Move>());
        }
        return playerSession.attribute(Attributes.PLAYER_MOVES_KEY);
    }

    /**
     * Clear the moves that have been made this turn
     * @param playerSession the session that has moves
     */
    public static void clearMoves(Session playerSession) {
        getMoves(playerSession).clear();
    }

    /**
     * Clear only the last move made this turn
     * @param playerSession the session that has moves
     */
    public static void popMove(Session playerSession) {
        ArrayList<Move> moves =  getMoves(playerSession);
        if (moves.size() != 0)
            moves.remove(moves.size() - 1);
    }

}
