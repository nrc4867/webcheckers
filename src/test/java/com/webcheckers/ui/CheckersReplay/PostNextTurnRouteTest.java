package com.webcheckers.ui.CheckersReplay;
import static com.webcheckers.util.Checkers.clearMoves;
import static com.webcheckers.util.Checkers.getMoves;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.model.ModeOptions;
import com.webcheckers.model.Move;
import com.webcheckers.ui.CheckersPlay.PostValidateRoute;
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
 * Unit test for PostNextTurnRoute
 * @author Abhaya Tamrakar
 */
@Tag("UI-Tier")
public class PostNextTurnRouteTest {
    private PostNextTurnRoute pntr;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    private Gson gson = new Gson();
    @BeforeEach
    public void setup(){
        request=mock(Request.class);
        session=mock(Session.class);
        when(request.session()).thenReturn(session);

        response=mock(Response.class);
        engine = mock(TemplateEngine.class);


        pntr= new PostNextTurnRoute(gson);
    }

    @Test
    public void handleNULLTest() throws Exception {
        Player player1= mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json="";
        when(player1.getBoard()).thenReturn(null);
        json = (String) pntr.handle(request,response);
        Message a = gson.fromJson(json,Message.class);

        assertEquals(PostNextTurnRoute.ERROR,a);
    }

    @Test
    public void handleTest() throws Exception {
        Player player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json = "";
        Board b = mock(Board.class);
        when(player1.getBoard()).thenReturn(b);
        json = (String) pntr.handle(request, response);
        Message a = gson.fromJson(json, Message.class);

        assertEquals(PostNextTurnRoute.SUCCESS, a);
    }
}
