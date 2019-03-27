package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.util.Attributes;
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
public class GetHomeRouteTest {

    private GetHomeRoute ghr;
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


        ghr= new GetHomeRoute(playerLobby,engine);
    }

    @Test
    public void handleTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Player player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        when(player1.isSelectedPlayerInGame()).thenReturn(false);

        // Invoke the test
        ghr.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);


        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);

        //   * test view name
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);

    }

    @Test
    public void handlenullPlayer(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Player player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        when(player1.isSelectedPlayerInGame()).thenReturn(true);
        // Invoke the test
        ghr.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.PLAYER_IN_GAME);

    }

    @Test
    public void boardnotNULLTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        Board b = new Board(new Player("p1"),new Player("p2"));

        when(player1.getBoard()).thenReturn(b);
        // Invoke the test
        ghr.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        verify(response).redirect(WebServer.GAME_URL);
    }

    @Test
    public void noPlayersTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(playerLobby.getPlayers()).thenReturn(new ArrayList<Player>());
        ghr.handle(request,response);


    }

}
