package com.webcheckers.ui;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.BoardList;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Attributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    private BoardList boardList;

    private Board board;

    private Player player1;
    private Player player2;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        engine = mock(TemplateEngine.class);
        response = mock(Response.class);

        playerLobby = mock(PlayerLobby.class);
        board = mock(Board.class);

        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);

        player2 = mock(Player.class);
        when(playerLobby.getPlayer(request.queryParams(PostGameRoute.CHALLENGE_PARAM))).thenReturn(player2);

        boardList = mock(BoardList.class);

        CuT = new PostGameRoute(playerLobby, boardList);
    }

    /**
     * The requester does not exist most likely due to a timeout
     */
    @Test
    public void nullRequester() {
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(null);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            verify(response).redirect(WebServer.HOME_URL);
        }
    }

    /**
     * the selected player does not exist most likely due to a timeout
     */
    @Test
    public void nullSelection() {
        when(playerLobby.getPlayer(request.queryParams(PostGameRoute.CHALLENGE_PARAM))).thenReturn(null);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            verify(response).redirect(WebServer.HOME_URL);
        }
    }

    /**
     * The selected player is already in a game
     */
    @Test
    public void selectionInGame() {
        when(player2.inGame()).thenReturn(true);
        Board b = mock(Board.class);
        when(player2.getBoard()).thenReturn(b);
        when(b.getTurn()).thenReturn(0);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            verify(response).redirect(WebServer.GAME_URL);
        }
    }

    /**
     * A new game must be created
     */
    @Test
    public void createGame() {
 /*       when(player1.getBoard()).thenReturn(null);

        final ArgumentCaptor<BoardController> captor = ArgumentCaptor.forClass(BoardController.class);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            verify(response).redirect(WebServer.GAME_URL);
        }

        verify(player1).setBoardController(captor.capture());

        verify(player1, times(1)).setBoardController(captor.getValue());
        verify(player2, times(1)).setBoardController(captor.getValue());*/
    }

    /**
     * The match requester is already in a game
     */
    @Test
    public void stillInGame() {
        when(player1.getBoard()).thenReturn(board);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            verify(response).redirect(WebServer.GAME_URL);
        }
    }


}