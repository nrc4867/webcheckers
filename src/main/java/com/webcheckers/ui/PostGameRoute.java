package com.webcheckers.ui;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Attributes;
import spark.*;
import static spark.Spark.halt;

import java.util.Objects;

/**
 * Assign two players to a game in preparation to display
 *
 * @author Nicholas Chieppa
 */
public class PostGameRoute implements Route {

    private final PlayerLobby playerLobby;

    public static final String CHALLENGE_PARAM = "challenge";

    public PostGameRoute(final PlayerLobby playerLobby) {
        this.playerLobby = Objects.requireNonNull(playerLobby, "Playerlobby must be set");
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        // player who sent the request
        Player reqPlayer = httpSession.attribute(Attributes.PLAYER_SIGNIN_KEY);
        // the challenged player
        Player selectedPlayer = playerLobby.getPlayer(request.queryParams(CHALLENGE_PARAM));

        if(reqPlayer == null || selectedPlayer == null || selectedPlayer.equals(reqPlayer)) {
            // a player that is not signed in cannot be in a game
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        if(selectedPlayer.getBoardController() != null) {
            // the selected player is already in a game
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        if(reqPlayer.getBoardController() == null) {
            final BoardController boardController = new BoardController(new Board(reqPlayer, selectedPlayer));
            reqPlayer.setBoardController(boardController);
            selectedPlayer.setBoardController(boardController);
            response.redirect(WebServer.GAME_URL);
        } else {
            // the player challenged someone while still playing a game
            response.redirect(WebServer.GAME_URL);
        }

        halt();
        return null;
    }
}
