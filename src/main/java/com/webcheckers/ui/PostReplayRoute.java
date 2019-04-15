package com.webcheckers.ui;

import com.webcheckers.appl.Player;
import com.webcheckers.util.Checkers;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

/**
 * This route is for setting a player on a replay board
 *
 * @author Nicholas Chieppa
 */
public class PostReplayRoute implements Route{
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session httpsSession = request.session();
        Player requester = Checkers.getPlayer(httpsSession);

        if (Checkers.playerInGame(requester)) { // Cannot replay if you are already in a game
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }


        return null;
    }
}
