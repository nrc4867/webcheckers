package com.webcheckers.ui.CheckersPlay;

import static com.webcheckers.util.Checkers.*;
import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostBackupRoute implements Route {
    public static final String SUCCESS_BACKUP = "The last move has been reset.";

    private final Gson gson;

    public PostBackupRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player requester = getPlayer(httpSession);

        if (!playerHasBoard(requester)) {
            return gson.toJson(Message.error(PostCheckTurnRoute.NO_GAME));
        }

        popMove(httpSession);

        return gson.toJson(Message.info(SUCCESS_BACKUP));
    }
}
