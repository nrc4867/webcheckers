package com.webcheckers.ui;
import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SignInException;
import com.webcheckers.appl.Player;
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

import java.util.Hashtable;

/**
 * Post SignoutRoute Test
 * @author Abhaya Tamrakar
 */
@Tag("UI-Tier")
public class PostSignOutRouteTest {

    private final String TEST_NAME = "Happy";

    // attributes holding mock objects
    private PostSignOutRoute psour;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() throws SignInException {
        request=mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);

        // Create a Lobby with a Player Signed in
        playerLobby=new PlayerLobby(new Hashtable<String, Player>());
        // Add Player
        player = playerLobby.reserveName(TEST_NAME);

        psour=new PostSignOutRoute(playerLobby,engine);

    }

    /**
     * Test Signin out the player in the lobby
     * @throws Exception
     */
    @Test
    public void signOutTest() throws Exception {
        // Check if Player is in the lobby
        System.out.println(playerLobby.containsPlayer(TEST_NAME));

        when(request.session().attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player);

        //Sign-out Player
        psour.handle(request,response);

        //Check if Player doesn't exist in the lobby anymore
        assertFalse(playerLobby.containsPlayer(TEST_NAME));
    }


}
