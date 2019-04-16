package com.webcheckers.model;

import java.util.HashMap;

public class ReplayModeOptions {

    /**
     * Hashmap keys
     */
    private static final String NEXT = "hasNext";
    private static final String PREV = "hasPrevious";

    private final HashMap<String, Object> options = new HashMap<>();

    private ReplayModeOptions(final boolean gofowards, final boolean gobackwards) {
        options.put(NEXT, gofowards);
        options.put(PREV, gobackwards);
    }

    public HashMap<String, Object> getOptions() {
        return new HashMap<>(options);
    }

    /**
     * Create replay options starting from a specific turn
     * @param board the board that is being replayed
     * @param  fromTurn the turn to start at
     * @return options for the next turn
     */
    public static ReplayModeOptions createOptions(Board board, int fromTurn) {
        boolean gofowards = fromTurn < board.getTurn();
        boolean gobackwards = fromTurn > 0;
        return new ReplayModeOptions(gofowards, gobackwards);
    }

}
