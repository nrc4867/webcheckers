package com.webcheckers.ui;
import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SignInException;
import com.webcheckers.model.Player;
import com.webcheckers.util.Attributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

@Tag("UI-Tier")
public class PostSignOutRouteTest {
    private PostSignOutRoute psour;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player;

    @BeforeEach
    public void setup() throws SignInException {
        request=mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        playerLobby=new PlayerLobby();
        player = playerLobby.reserveName("Happy");
        psour=new PostSignOutRoute(playerLobby,engine);

    }
    @Test
    public void signOutTest() throws Exception {
        System.out.println(playerLobby.containsPlayer("Happy"));
        when(request.session().attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player);
        psour.handle(request,response);
        assertEquals(playerLobby.containsPlayer("Happy"),false);
    }


}
