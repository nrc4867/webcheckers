package com.webcheckers.ui.CheckersPlay;

import static com.webcheckers.util.Checkers.*;
import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class PostCheckTurnRoute implements Route {

    private final Gson gson;

    public final static String NO_GAME = "You are not in a game";
    public final static String OPPONENT_RESIGNED_FORMAT = "%s has resigned from the game!";
    public final static String YOUR_TURN = "It is now your turn.";

    public PostCheckTurnRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player requester = getPlayer(httpSession);

        if (requester.getBoard() == null) {
            return gson.toJson(Message.error("No board assignment"));
        }

        if(requester.checkTurn())
            return gson.toJson(Message.info("true"));

        return gson.toJson(GetGameRoute.opponentsTurn(requester.getBoard().getActivePlayer()));
    }
}
