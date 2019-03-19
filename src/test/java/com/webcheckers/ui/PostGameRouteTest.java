package com.webcheckers.ui;

import com.webcheckers.appl.LazySessionWatcher;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.util.Attributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostGameRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private PostGameRoute CuT;

    // attributes holding mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private LazySessionWatcher sessionWatcher;

    private Player player1;
    private Player player2;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session(true)).thenReturn(session);

        engine = mock(TemplateEngine.class);
        response = mock(Response.class);

        playerLobby = mock(PlayerLobby.class);

        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);

        player2 = mock(Player.class);
        when(playerLobby.getPlayer(any(String.class))).thenReturn(player2);

        CuT = new PostGameRoute(playerLobby);
    }

    /**
     * The requester does not exist most likely due to a timeout
     */
    @Test
    public void nullRequester() {
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(null);
    }

    /**
     * the selected player does not exist most likely due to a timeout
     */
    @Test
    public void nullSelection() {
        when(playerLobby.getPlayer(any(String.class))).thenReturn(null);

    }

    /**
     * The selected player is already in a game
     */
    @Test
    public void selectionInGame() {
        when(player2.getBoard()).thenReturn(any(Board.class));
    }

    /**
     * A new game must be created
     */
    @Test
    public void createGame() {
        when(player1.getBoard()).thenReturn(null);
    }

    /**
     * The match requester is already in a game
     */
    @Test
    public void stillInGame() {
        when(player1.getBoard()).thenReturn(any(Board.class));
    }


}