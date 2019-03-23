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

public class PostCheckTurnRoute implements Route {

    private final Gson gson;

    public final String OPPONENT_RESIGNED_FORMAT = "%s has resigned from the game!";
    public final String YOUR_TURN = "It is now your turn.";

    public PostCheckTurnRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player requester = httpSession.attribute(Attributes.PLAYER_SIGNIN_KEY);

        if (!PostValidateRoute.playerInGame(requester)) {
            halt();
            return null;
        }

        if(requester.checkTurn())
            return gson.toJson(Message.info(YOUR_TURN));

        return gson.toJson(Message.error("No True implementation"));
    }
}
