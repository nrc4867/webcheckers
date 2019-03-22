package com.webcheckers.ui;

import com.webcheckers.appl.LazySessionWatcher;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SignInException;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostSignInRouteTest {

    private final String TAKEN_NAME = "Bill";
    private final String CHOSEN_NAME = "Jeff";
    private final String INVALID_NAME = "%a";

    /**
     * The component-under-test (CuT).
     */
    private PostSignInRoute CuT;

    // attributes holding mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private LazySessionWatcher sessionWatcher;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() throws SignInException {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session(true)).thenReturn(session);

        engine = mock(TemplateEngine.class);
        response = mock(Response.class);

        playerLobby = mock(PlayerLobby.class);
        sessionWatcher = mock(LazySessionWatcher.class);

        when(playerLobby.containsPlayer(TAKEN_NAME)).thenReturn(true);
        when(playerLobby.reserveName(CHOSEN_NAME)).thenReturn(mock(Player.class));

        when(playerLobby.reserveName(TAKEN_NAME)).thenThrow(new SignInException(PlayerLobby.NAME_TAKEN_MESSAGE));
        when(playerLobby.reserveName(INVALID_NAME)).thenThrow(new SignInException(PlayerLobby.NAME_INVALID_MESSAGE));

        CuT = new PostSignInRoute(playerLobby, sessionWatcher, engine);
    }

    @Test
    void signInSuccess() {
        // Sign in with a name that is not taken
        when(request.queryParams(PostSignInRoute.USERNAME_PARAM)).thenReturn(CHOSEN_NAME);

        // Start the test

        try {
            CuT.handle(request, response);
        } catch (HaltException e) { // Expected result
            verify(response).redirect(WebServer.HOME_URL);
        }
    }

    @Test
    void signInNameTaken() {
        when(request.queryParams(PostSignInRoute.USERNAME_PARAM)).thenReturn(TAKEN_NAME);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // start the test

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetSignInRoute.MESSAGE_ATTR,
                Message.info(PlayerLobby.NAME_TAKEN_MESSAGE));

        testHelper.assertViewName(PostSignInRoute.VIEW_NAME);
    }

    @Test
    void signInInvalidName() {
        when(request.queryParams(PostSignInRoute.USERNAME_PARAM)).thenReturn(INVALID_NAME);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // start the test

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetSignInRoute.MESSAGE_ATTR,
                Message.info(PlayerLobby.NAME_INVALID_MESSAGE));

        testHelper.assertViewName(PostSignInRoute.VIEW_NAME);
    }

    @Test
    void usingSessionWatchDog() {
        CuT = new PostSignInRoute(playerLobby, engine);

        // all the other methods should still work without supplying a session watcher

        signInSuccess();
        signInNameTaken();
        signInInvalidName();
    }
}