package com.webcheckers.util;

/**
 * This class stores a list of session attributes used by the server
 * 
 * @author  Nicholas Chieppa
 */
public class Attributes {
    /**
     * This key stores a Player model on the HTTP Session
     * this Player model stores the users current signin
     */
    public static final String PLAYER_SIGNIN_KEY = "playerSignin";

    /**
     * This key stores the session watch dog that is created on player sign in
     */
    public static final String PLAYER_SESSION_KEY = "sessionWatcher";
}
