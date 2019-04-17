package com.webcheckers.ui.CheckersReplay;
import static com.webcheckers.util.Checkers.clearMoves;
import static com.webcheckers.util.Checkers.getMoves;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.webcheckers.appl.BoardList;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.model.ModeOptions;
import com.webcheckers.model.Move;
import com.webcheckers.ui.CheckersPlay.PostResignRoute;
import com.webcheckers.ui.CheckersPlay.PostValidateRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.view.Mode;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Checkers;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Unit test for PostReplayRoute
 * @author Abhaya Tamrakar
 */
@Tag("UI-Tier")
public class PostReplayRouteTest {
    private PostReplayRoute prr;
    private Request request;
    private Session session;
    private Response response;
    private BoardList boardList;

    private Gson gson = new Gson();
    @BeforeEach
    public void setup(){
        request=mock(Request.class);
        session=mock(Session.class);
        when(request.session()).thenReturn(session);

        response=mock(Response.class);
        boardList=mock(BoardList.class);


        prr= new PostReplayRoute(boardList);
    }

    @Test
    public void handleNULLTest() throws Exception {
        Player player1= mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        when(player1.inGame()).thenReturn(false);

        Board b = mock(Board.class);
        when(request.queryParams(PostReplayRoute.REPLAY_PARAM)).thenReturn(PostReplayRoute.REPLAY_PARAM);
        when(boardList.getBoard(PostReplayRoute.REPLAY_PARAM)).thenReturn(b);
        try {
            prr.handle(request, response);
        } catch (HaltException e) { // Expected result
            verify(response).redirect(WebServer.REPLAY_GAME);
        }


    }

    @Test
    public void handleTest() throws Exception {
        Player player1= mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        when(player1.inGame()).thenReturn(true);

        Board b = mock(Board.class);
        when(request.queryParams(PostReplayRoute.REPLAY_PARAM)).thenReturn(PostReplayRoute.REPLAY_PARAM);
        when(boardList.getBoard(PostReplayRoute.REPLAY_PARAM)).thenReturn(b);
        try {
            prr.handle(request, response);
        } catch (HaltException e) { // Expected result
            verify(response).redirect(WebServer.HOME_URL);
        }

    }
}
