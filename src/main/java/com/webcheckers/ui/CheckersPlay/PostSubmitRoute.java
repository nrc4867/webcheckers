package com.webcheckers.ui.CheckersPlay;

import com.google.gson.Gson;
import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.nio.charset.StandardCharsets;

import static java.net.URLDecoder.decode;
import static spark.Spark.halt;

public class PostSubmitRoute implements Route {
    public static final String SUCCESSFUL_SUBMISSION = "The moves have been submitted";
    public static final String ERROR = "No moves made";

    private final Gson gson;

    public PostSubmitRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player requester = httpSession.attribute(Attributes.PLAYER_SIGNIN_KEY);

        if (!PostValidateRoute.playerInGame(requester)) {
            return gson.toJson(Message.error(PostCheckTurnRoute.NO_GAME));
        }

        if(PostValidateRoute.getMoves(httpSession).size() != 0) {
            final BoardController controller = requester.getBoardController();

            controller.movePieces(PostValidateRoute.getMoves(httpSession));
            controller.toggleTurn();

            PostValidateRoute.getMoves(httpSession).clear();
            return gson.toJson(Message.info(SUCCESSFUL_SUBMISSION));
        }

        return gson.toJson(Message.error(ERROR));
    }
}
