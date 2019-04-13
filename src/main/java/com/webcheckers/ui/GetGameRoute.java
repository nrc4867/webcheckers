package com.webcheckers.ui;

import static com.webcheckers.util.Checkers.clearMoves;
import static com.webcheckers.util.Checkers.getPlayer;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.model.*;
import com.webcheckers.ui.view.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.ArrayList;

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

    public static final String MATCH_TURN_FORMAT = "It is currently %s's turn.";
    public static final String YOUR_TURN = "It is currently your turn.";

    static final String TITLE = "title";
    static final String GAME = "GAME!";
    static final String CURRENT_USER = "currentUser";
    static final String VIEW_MODE = "viewMode";
    static final String MODE_OPTIONS = "modeOptions";
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";

    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;
    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine, final Gson gson) {

//        /************************** TEST CODE *******************************/
//        Board b = new Board(new Player("red"), new Player("white"));
//        this.board = Objects.requireNonNull(b, "Board is required!");
//        /*********************** END TEST CODE ******************************/

        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gson = Objects.requireNonNull(gson, "Gson is required");
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
        // player who sent the request
        final Player reqPlayer = getPlayer(httpSession);
        clearMoves(httpSession);

        final Board board = (reqPlayer != null)?reqPlayer.getBoard():null;

        if(board == null) {
            // a player has to be on a board to see games
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        LOG.finer("GetGameRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();

        vm.put(TITLE, GAME);
        vm.put(CURRENT_USER, board.getActivePlayer());
        vm.put(VIEW_MODE, Mode.PLAY);
        vm.put(MODE_OPTIONS, gson.toJson(board.getModeOptions()));
        vm.put(RED_PLAYER, board.getRedPlayer());
        vm.put(WHITE_PLAYER, board.getWhitePlayer());
        vm.put(ACTIVE_COLOR, board.getActiveColor());

        if (reqPlayer.getColor() == Color.RED) {
            vm.put("board", new BoardView(board,false));
        }

        else if (reqPlayer.getColor() == Color.WHITE) {
            vm.put("board", new BoardView(board,true));
        }

        // display a user message in the Home page
        if(!reqPlayer.equals(board.getActivePlayer())) {
            vm.put("message", opponentsTurn(board.getActivePlayer()));
        } else {
            vm.put("message", Message.info(YOUR_TURN));
        }

        NavBar.updateNavBar(vm, httpSession);

        // render the View
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }

    public static Message opponentsTurn(Player opponent) {
        return Message.error(String.format(MATCH_TURN_FORMAT, opponent.toString()));
    }
}
