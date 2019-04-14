package com.webcheckers.model;

import com.webcheckers.appl.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores a boards mode options
 *
 * @author Nicholas Chieppa
 */
public class ModeOptions {

    private static final ModeOptions gameActive = new ModeOptions(false, "");

    /**
     * Map attribute keys
     */
    static final String GAME_OVER_STATE = "isGameOver";
    static final String GAME_OVER_REASON = "gameOverMessage";

    private final HashMap<String, Object> modeOptions = new HashMap<>();

    private ModeOptions(final boolean isGameOver, final String reason) {
        modeOptions.put(GAME_OVER_STATE, isGameOver);
        modeOptions.put(GAME_OVER_REASON, isGameOver);
    }

    public Map<String, Object> getOptions() {
        return new HashMap<>(modeOptions);
    }

    /**
     * @param player the player who resigned
     * @return a mode options object with a resign message
     */
    public static ModeOptions resign(Player player) {
        return new ModeOptions(true, player.getName() + " resigned.");
    }

    /**
     * @param player the player who won
     * @return a mode options object with a won message
     */
    public static ModeOptions won(Player player) {
        return new ModeOptions(true, player.getName() + " won.");
    }

    /**
     * @return a default game active mode
     */
    public static ModeOptions gameActive() {
        return gameActive;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ModeOptions)) return false;
        ModeOptions com = (ModeOptions) obj;
        return modeOptions.equals(com.modeOptions);
    }

    @Override
    public int hashCode() {
        return modeOptions.hashCode();
    }
}
