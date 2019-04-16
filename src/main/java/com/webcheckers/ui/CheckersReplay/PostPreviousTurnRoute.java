package com.webcheckers.ui.CheckersReplay;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Checkers;
import com.webcheckers.util.Message;
import com.webcheckers.util.Spectators;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostPreviousTurnRoute implements Route {
    public static final Message SUCCESS = Message.info("true");
    public static final Message ERROR = Message.info("You are not on a board");

    private final Gson gson;

    public PostPreviousTurnRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session session = request.session();
        Player player = Checkers.getPlayer(session);

        if (!Checkers.playerHasBoard(player)) {
            return ERROR;
        }

        Spectators.setTurn(session, Spectators.getTurn(session) - 1);
        return SUCCESS;
    }
}
