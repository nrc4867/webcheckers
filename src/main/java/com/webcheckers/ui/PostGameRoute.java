package com.webcheckers.ui;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.BoardList;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Spectators;
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
    private final BoardList boardList;

    public static final String CHALLENGE_PARAM = "challenge";

    public PostGameRoute(final PlayerLobby playerLobby, final  BoardList boardList) {
        this.playerLobby = Objects.requireNonNull(playerLobby, "Playerlobby must be set");
        this.boardList = Objects.requireNonNull(boardList, "Boardlist must be set");
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

        if(selectedPlayer.inGame()) {
            // the selected player is already in a game
            reqPlayer.setBoard(selectedPlayer.getBoard());
            reqPlayer.setColor(selectedPlayer.getColor());
            Spectators.setTurn(httpSession, selectedPlayer.getBoard().getTurn());
            response.redirect(WebServer.GAME_URL);
            halt();
            return null;
        }

        if(!reqPlayer.inGame()) {
            Board board = boardList.createBoard(reqPlayer, selectedPlayer);
            reqPlayer.setBoard(board);
            selectedPlayer.setBoard(board);
            response.redirect(WebServer.GAME_URL);
        } else {
            // the player challenged someone while still playing a game
            response.redirect(WebServer.GAME_URL);
        }

        halt();
        return null;
    }
}
