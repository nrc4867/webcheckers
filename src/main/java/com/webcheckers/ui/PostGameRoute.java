package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.util.Attributes;
import spark.*;

import java.util.Objects;

/**
 * Assign two players to a game in preparation to display
 *
 * @author Nicholas Chieppa
 */
public class PostGameRoute implements Route {

    private final PlayerLobby playerLobby;

    public final String CHALLENGE_PARAM = "challenge";

    public PostGameRoute(final PlayerLobby playerLobby) {
        this.playerLobby = Objects.requireNonNull(playerLobby, "Playerlobby must be set");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        // player who sent the request
        Player reqPlayer = httpSession.attribute(Attributes.PLAYER_SIGNIN_KEY);
        // the challenged player
        Player selectedPlayer = playerLobby.getPlayer(request.queryParams(CHALLENGE_PARAM));

        if(reqPlayer == null || selectedPlayer == null) {
            // a player that is not signed in cannot be in a game
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        if(selectedPlayer.getBoard() != null) {
            // the selected player is already in a game
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        if(reqPlayer.getBoard() == null) {
            final Board board = new Board(reqPlayer, selectedPlayer);
            reqPlayer.setBoard(board);
            selectedPlayer.setBoard(board);
            response.redirect(WebServer.GAME_URL);
        } else {
            // the player challenged someone while still playing a game
            response.redirect(WebServer.GAME_URL);
        }

        return null;
    }
}
