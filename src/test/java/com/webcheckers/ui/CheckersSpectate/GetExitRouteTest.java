package com.webcheckers.ui.CheckersSpectate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Attributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;

/**
 * Unit test for GetExit Route
 * @author Abhaya Tamrakar
 */
@Tag("UI-Tier")
public class GetExitRouteTest {
    private GetExitRoute ger;
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


        ger= new GetExitRoute();
    }

    @Test
    public void handldeTest()  {

        Player player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);


        try {
            ger.handle(request, response);
            assertNull(player1.getBoardController());
        } catch (Exception e) { // Expected result
            verify(response).redirect(WebServer.HOME_URL);
        }

    }

    @Test
    public void handle2Test(){
        try {
            ger.handle(request, response);
        } catch (Exception e) { // Expected result
            verify(response).redirect(WebServer.HOME_URL);
        }
    }

}
