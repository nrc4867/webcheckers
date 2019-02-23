package com.webcheckers.util;

import com.webcheckers.appl.Player;
import spark.Session;

import java.util.Map;

/**
 * Manages nav-bar controls
 * @author Nicholas Chieppa
 */
public class NavBar {

    public static final String NAV_BAR_ATTR = "currentUser";
    public static final String PLAYER_SIGNIN_KEY = "playerSignin";

    /**
     * Update the nav-bar at the top of the webpage
     * @param pageElements the elements that are inserted into the webpage
     * @param session the session created by the page request
     */
    public static void updateNavBar(Map<String, Object> pageElements, Session session) {
        if(session.attribute(PLAYER_SIGNIN_KEY) != null) { // if the user has a key assigned to them
            Player player = (Player) session.attribute(PLAYER_SIGNIN_KEY);
            pageElements.put(NAV_BAR_ATTR, player); // display their name in the nav-bar
        }
    }
}