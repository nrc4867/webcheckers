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

import java.nio.charset.StandardCharsets;

import static java.net.URLDecoder.decode;
import static spark.Spark.halt;

public class PostSubmitRoute implements Route {
    public static final String SUCCESSFUL_SUBMISSION = "The moves have been submitted";
    public static final String JUMPS_LEFT = "You have available jumps left.";
    public static final String ERROR = "No moves made";

    private final Gson gson;

    public PostSubmitRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player requester = getPlayer(httpSession);

        if (!playerInGame(requester)) {
            return gson.toJson(Message.error(PostCheckTurnRoute.NO_GAME));
        }

        if(getMoves(httpSession).size() != 0) {
            final BoardController controller = requester.getBoardController();

            if(controller.mustJumpThisTurn(getMoves(httpSession))) {
                return gson.toJson(Message.error(JUMPS_LEFT));
            }

            controller.movePieces(getMoves(httpSession));
            Board board = controller.getBoard();
            board.setPlayMode(CreateModeOptions.createOptions(controller));
            controller.toggleTurn();

            clearMoves(httpSession);
            return gson.toJson(Message.info(SUCCESSFUL_SUBMISSION));
        }

        return gson.toJson(Message.error(ERROR));
    }
}
