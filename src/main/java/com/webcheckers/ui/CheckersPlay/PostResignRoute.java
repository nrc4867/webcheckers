package com.webcheckers.ui.CheckersPlay;

import static com.webcheckers.util.Checkers.*;
import com.google.gson.Gson;
import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Board;
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
        Player requester = getPlayer(httpSession);

        if (!playerInGame(requester)) {
            return gson.toJson(Message.error(PostCheckTurnRoute.NO_GAME));
        }

        Board board = requester.getBoard();
        if (board.isActivePlayer(requester)) // switch the active player to force a page reload
            board.switchActivePlayer();

        requester.resign();
        requester.getOpponent().enableExit();
        requester.removeBoard();

        return gson.toJson(Message.info(resign));
    }
}
