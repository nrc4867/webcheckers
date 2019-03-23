package com.webcheckers.ui.CheckersPlay;

import com.google.gson.Gson;
import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Piece;
import com.webcheckers.ui.CheckersPlay.Json.Move;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import static spark.Spark.halt;

import java.nio.charset.StandardCharsets;
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
            halt();
            return null;
        }

        final BoardController controller = requester.getBoardController();

        final String dataStr  = decode(request.body().substring(11), StandardCharsets.UTF_8.name());
        final Move move = gson.fromJson(dataStr, Move.class);

        final Piece startPosition = new Piece(move.getStartCell(), move.getStartRow(), requester);

        if(controller.canMoveTo(startPosition, move.getEndRow(), move.getEndCell())) {
            return gson.toJson(Message.info(VALID_MOVE));
        }
        return gson.toJson(Message.error(INVALID_MOVE));
//        return gson.toJson(Message.error(ERROR));
}

    public static boolean playerInGame(Player player) {
        return (player != null) && player.getBoardController() != null;
    }
}
