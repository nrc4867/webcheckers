package com.webcheckers.ui.CheckersReplay;

import com.webcheckers.appl.BoardList;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Board;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Checkers;
import com.webcheckers.util.Spectators;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.Objects;

import static spark.Spark.halt;
import static spark.Spark.redirect;

/**
 * This route is for setting a player on a replay board
 *
 * @author Nicholas Chieppa
 */
public class PostReplayRoute implements Route{

    public static final String REPLAY_PARAM = "replay";

    final BoardList boardList;

    public PostReplayRoute(BoardList boardList) {
        this.boardList = Objects.requireNonNull(boardList, "The boardlist is required");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session httpsSession = request.session();
        Player requester = Checkers.getPlayer(httpsSession);

        Board board = boardList.getBoard(request.queryParams(REPLAY_PARAM));

        if (Checkers.playerInGame(requester)) { // Cannot replay if you are already in a game
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        requester.setBoard(board);
        Spectators.setTurn(httpsSession, 0);

        response.redirect(WebServer.REPLAY_GAME);
        halt();
        return null;
    }
}
