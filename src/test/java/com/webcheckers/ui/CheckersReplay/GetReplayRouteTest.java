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
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.view.Mode;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Checkers;
import com.webcheckers.util.Spectators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Unit test for GetReplayRoute Test
 * @author Abhaya Tamrakar
 */
@Tag("UI-Tier")
public class GetReplayRouteTest {
    private GetReplayRoute ghr;
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


        ghr= new GetReplayRoute(engine, gson);
    }

    @Test
    public void handleTest() throws Exception {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Player player1= mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);

        Board board = mock(Board.class);
        Player player2 = mock(Player.class);
        when(player2.getColor()).thenReturn(Color.WHITE);
        when(player1.getBoard()).thenReturn(board);
        when(player1.getColor()).thenReturn(Color.RED);
        when(board.getRedPlayer()).thenReturn(player1);
        when(board.getWhitePlayer()).thenReturn(player2);
        ghr.handle(request,response);

        // Analyze the content passed into the render method
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(GetReplayRoute.TITLE, GetReplayRoute.GAME);

        //   * test view name
        testHelper.assertViewName(GetReplayRoute.VIEW_NAME);
    }

    @Test
    public void handleNullTest() throws Exception {
        Player player1= mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        when(player1.getBoard()).thenReturn(null);
        try {
            ghr.handle(request, response);
        } catch (HaltException e) { // Expected result
            verify(response).redirect(WebServer.HOME_URL);
        }

    }
}
