package com.webcheckers.ui.CheckersReplay;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Board;
import com.webcheckers.model.ModeOptions;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Checkers;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class GetStopWatchingRoute implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session session = request.session();
        Player player = Checkers.getPlayer(session);

        if (Checkers.playerHasBoard(player)) {
            Board board = player.getBoard();
            boolean gameActive = (boolean) board.getModeOptions().get(ModeOptions.GAME_OVER_STATE);

            if (!board.isPlaying(player) || !gameActive) // prevent players from leaving by coming to this url
                player.enableExit();
        }

        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}
