package com.webcheckers.ui;

import static com.webcheckers.util.Checkers.clearMoves;
import static com.webcheckers.util.Checkers.getMoves;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.model.Move;
import com.webcheckers.ui.CheckersPlay.PostValidateRoute;
import com.webcheckers.ui.view.Mode;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Checkers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;

/**
 * Unit test for GetHomeRoute Test
 * @author Abhaya Tamrakar
 */
@Tag("UI-Tier")
public class GetGameRouteTest {

    private GetGameRoute ghr;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;



    @BeforeEach
    public void setup(){
        request=mock(Request.class);
        session=mock(Session.class);
        when(request.session()).thenReturn(session);

        response=mock(Response.class);
        engine = mock(TemplateEngine.class);

        playerLobby = mock(PlayerLobby.class);


        ghr= new GetGameRoute(engine);
    }
    @Test
    public void boardNull(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Player player1= mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        when(player1.getBoard()).thenReturn(null);

        when(getMoves(session)).thenReturn(new ArrayList<>());
        ghr.handle(request,response);

        verify(response).redirect(WebServer.HOME_URL);
    }
    @Test void handle(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(getMoves(session)).thenReturn(new ArrayList<>());

        Player player1= mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        Board b1 = mock(Board.class);
        when(player1.getBoard()).thenReturn(b1);
        Player player2 = new Player("Happy", Color.WHITE);
        Player player3 = new Player("Happy2", Color.RED);
        when(b1.getActivePlayer()).thenReturn(player2);
        when(b1.getRedPlayer()).thenReturn(player3);
        when(b1.getWhitePlayer()).thenReturn(player2);
        when(b1.getActiveColor()).thenReturn(Color.WHITE);;

        when(player1.getColor()).thenReturn(Color.RED);
        ghr.handle(request,response);

        // Analyze the content passed into the render method
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.GAME);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE, Mode.PLAY);
        testHelper.assertViewModelAttribute(GetGameRoute.MODE_OPTIONS, new ArrayList<>());
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, b1.getRedPlayer());
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, b1.getWhitePlayer());
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR, b1.getActiveColor());
        //   * test view name
        testHelper.assertViewName(GetGameRoute.VIEW_NAME);

    }

    @Test
    public void whitePlayer(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(getMoves(session)).thenReturn(new ArrayList<>());

        Player player1= mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        Board b1 = mock(Board.class);
        when(player1.getBoard()).thenReturn(b1);
        when(b1.getActivePlayer()).thenReturn(player1);
        when(player1.getColor()).thenReturn(Color.WHITE);

        ghr.handle(request, response);

    }


}
