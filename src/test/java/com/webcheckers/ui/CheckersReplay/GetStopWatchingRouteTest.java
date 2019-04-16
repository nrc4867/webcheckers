package com.webcheckers.ui.CheckersReplay;
import static com.webcheckers.util.Checkers.clearMoves;
import static com.webcheckers.util.Checkers.getMoves;
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
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.view.Mode;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Checkers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Unit test for GetStopWatchingRoute
 * @author Abhaya Tamrakar
 */
@Tag("UI-Tier")
public class GetStopWatchingRouteTest {
    private GetStopWatchingRoute gswr;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    @BeforeEach
    public void setup(){
        request=mock(Request.class);
        session=mock(Session.class);
        when(request.session()).thenReturn(session);

        response=mock(Response.class);
        engine = mock(TemplateEngine.class);


        gswr= new GetStopWatchingRoute();
    }

    @Test
    public void handleTest() throws Exception {
        Player player1= mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        Board board = mock(Board.class);
        when(player1.getBoard()).thenReturn(board);
        Map<String,Object> map= new HashMap<>();
        map.put(ModeOptions.GAME_OVER_STATE,true);
        when(board.getModeOptions()).thenReturn(map);
        try {
            gswr.handle(request, response);
        } catch (HaltException e) { // Expected result
            verify(response).redirect(WebServer.HOME_URL);
        }

    }

}
