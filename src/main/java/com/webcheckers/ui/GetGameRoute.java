package com.webcheckers.ui;

import com.webcheckers.model.*;
import com.webcheckers.ui.view.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.ArrayList;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.NavBar;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private final TemplateEngine templateEngine;

    private final Board board;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param  board
     *   The Board associated with this game.
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetHomeRoute(final Board board, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.board = Objects.requireNonNull(board, "Board is required!");
        //
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        LOG.finer("GetGameRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("currentUser", board.getActivePlayer());
        vm.put("viewMode", 0);
        vm.put("modeOptions", new ArrayList<>());
        vm.put("redPlayer", board.getRedPlayer());
        vm.put("whitePlayer", board.getWhitePlayer());
        vm.put("activeColor", board.getActiveColor());
        vm.put("board", new BoardView(board));

        // display a user message in the Home page
        vm.put("message", WELCOME_MSG);

        NavBar.updateNavBar(vm, httpSession);

        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
