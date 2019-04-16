package com.webcheckers.ui.CheckersReplay;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Board;
import com.webcheckers.model.ReplayModeOptions;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.view.BoardView;
import com.webcheckers.ui.view.Mode;
import com.webcheckers.util.Checkers;
import com.webcheckers.util.NavBar;
import com.webcheckers.util.Spectators;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class GetReplayRoute implements Route {

    static final String TITLE = "title";
    static final String GAME = "Replay";
    static final String CURRENT_USER = "currentUser";
    static final String VIEW_MODE = "viewMode";
    static final String MODE_OPTIONS = "modeOptionsAsJSON";
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";

    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;
    private final Gson gson;

    public GetReplayRoute(TemplateEngine templateEngine, Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "Template engine cannot be null");
        this.gson = Objects.requireNonNull(gson);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session httpsSession = request.session();
        Player player = Checkers.getPlayer(httpsSession);

        if (!Checkers.playerHasBoard(player)) { // a player must have a board to view this page
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        Board board = player.getBoard();

        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE, GAME);

        // The current player is determined by the even odd structure of the play.
        Player current = (Spectators.getTurn(httpsSession) % 2 == 0)?
                board.getRedPlayer():board.getWhitePlayer();

        vm.put(CURRENT_USER, player);
        vm.put(VIEW_MODE, Mode.REPLAY);
        ReplayModeOptions options = ReplayModeOptions.createOptions(board, Spectators.getTurn(httpsSession));
        vm.put(MODE_OPTIONS, gson.toJson(options.getOptions()));
        vm.put(RED_PLAYER, board.getRedPlayer());
        vm.put(WHITE_PLAYER, board.getWhitePlayer());
        vm.put(ACTIVE_COLOR, current);

        vm.put("board", new BoardView(board.getBoardByTurn(Spectators.getTurn(httpsSession)), false));

        NavBar.updateNavBar(vm, httpsSession);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
