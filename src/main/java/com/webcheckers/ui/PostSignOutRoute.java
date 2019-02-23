package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import com.webcheckers.util.NavBar;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * PostSignOut Handles signing out the user
 * 
 * @author Nicholas Chieppa
 */
public class PostSignOutRoute implements Route {

    public static final Message SIGN_OUT_MESS = Message.info("You have successfully signed out.");
    public static final String VIEW_FORM = "signin.ftl";

    private final PlayerLobby lobby;
    private final TemplateEngine templateEngine;

    /**
     * Creates route for player sign-out
     * 
     * @param lobby the lobby to remove the player from
     */
    public PostSignOutRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        Objects.requireNonNull(lobby, "Lobby cannot be null");
        Objects.requireNonNull(templateEngine, "template engine cannot be null");
        this.lobby = lobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session httpSession = request.session();
        if (httpSession.attribute(NavBar.PLAYER_SIGNIN_KEY) != null) { 
            if(!(httpSession.attribute(NavBar.PLAYER_SIGNIN_KEY) instanceof Player)) return null;
            lobby.removePlayer((Player) httpSession.attribute(NavBar.PLAYER_SIGNIN_KEY)); // remove player from lobby
            httpSession.attribute(NavBar.PLAYER_SIGNIN_KEY, null); // remove sign-in from session
        }
        // send user back to the sign-in page
        response.redirect(WebServer.SIGNIN_URL);
        return null;
    }

}