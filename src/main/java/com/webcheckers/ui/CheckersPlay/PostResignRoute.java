package com.webcheckers.ui.CheckersPlay;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class PostResignRoute implements Route {

    private final Gson gson;

    public static final String resign = "You have resigned from the game";

    public PostResignRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player requester = httpSession.attribute(Attributes.PLAYER_SIGNIN_KEY);

        if (!PostValidateRoute.playerInGame(requester)) {
            return gson.toJson(Message.error(PostCheckTurnRoute.NO_GAME));
        }

        requester.resign();
        requester.getOpponent().setBoardController(null);
        requester.setBoardController(null);

        return gson.toJson(Message.info(resign));
    }
}
