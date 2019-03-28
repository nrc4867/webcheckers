package com.webcheckers.ui.CheckersPlay;

import static com.webcheckers.util.Checkers.*;
import com.google.gson.Gson;
import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Move;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.net.URLDecoder.decode;

public class PostValidateRoute implements Route {
    public static final String VALID_MOVE = "Valid Move";
    public static final String INVALID_MOVE = "Invalid Move";

    public static final String ERROR = "Movement Check Error";

    private final Gson gson;

    public PostValidateRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player requester = httpSession.attribute(Attributes.PLAYER_SIGNIN_KEY);

        if (!playerInGame(requester)) {
            return gson.toJson(Message.error(PostCheckTurnRoute.NO_GAME));
        }

        final BoardController controller = requester.getBoardController();

        final String dataStr  = decode(request.body().substring(11), StandardCharsets.UTF_8.name());
        final Move move = gson.fromJson(dataStr, Move.class);

        if(controller.testMovement(move, getMoves(httpSession))) {
            getMoves(httpSession).add(move);
            return gson.toJson(Message.info(VALID_MOVE));
        }
        return gson.toJson(Message.error(INVALID_MOVE));
    }




}
