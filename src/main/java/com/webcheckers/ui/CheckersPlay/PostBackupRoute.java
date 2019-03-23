package com.webcheckers.ui.CheckersPlay;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class PostBackupRoute implements Route {
    public static final String SUCCESS_BACKUP = "The players moves have been reset";

    private final Gson gson;

    public PostBackupRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player requester = httpSession.attribute(Attributes.PLAYER_SIGNIN_KEY);

        if (!PostValidateRoute.playerInGame(requester)) {
            return gson.toJson(Message.error(PostCheckTurnRoute.NO_GAME));
        }

        PostValidateRoute.getMoves(httpSession).clear();

        return gson.toJson(Message.info(SUCCESS_BACKUP));
    }
}
