package com.webcheckers.ui;

import com.webcheckers.appl.LazySessionWatcher;
import com.webcheckers.appl.SessionTimeoutWatchDog;
import com.webcheckers.appl.SignInException;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import com.webcheckers.util.NavBar;
import spark.*;
import static spark.Spark.halt;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The UI controller for the POST signin page
 *
 * @author Nicholas Chieppa
 */
public class PostSignInRoute implements Route {

    public static final String VIEW_NAME = "signin.ftl";

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    private final LazySessionWatcher sessionWatcher;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     *
     * @param playerLobby the username checker service
     * @param sessionWatcher an active thread that prunes inactive sessions
     * @param templateEngine the HTML template rendering engine
     */
    public PostSignInRoute(PlayerLobby playerLobby, LazySessionWatcher sessionWatcher, TemplateEngine templateEngine) {
        Objects.requireNonNull(playerLobby, "PlayerLobby must not be null");
        Objects.requireNonNull(templateEngine, "TemplateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        this.sessionWatcher = sessionWatcher;
    }

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     *
     * @param playerLobby the username checker service
     * @param templateEngine the HTML template rendering engine
     */
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        this(playerLobby, null, templateEngine);
    }


    /**
     * Render the WebCheckers SignIn page or redirect to homepage.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the SignIn page, Redirect to homepage
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session(true);

        // Start creating elements that must be placed in the page
        final Map<String, Object> pageElements = new HashMap<>();
        pageElements.put(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);

        final String playerName = request.queryParams("username");

        ModelAndView mv;
        try {
            // Create a player login
            Player player = playerLobby.reserveName(playerName);
            httpSession.attribute(Attributes.PLAYER_SIGNIN_KEY, player);
            // Kill the player login after a minute of session inactivity. (vis. Browser closes)
            if(sessionWatcher != null) {
                sessionWatcher.addSession(httpSession);
            }
            else { // remove the session after 10 mins
                httpSession.attribute(Attributes.PLAYER_SESSION_KEY, new SessionTimeoutWatchDog(playerLobby, player));
                httpSession.maxInactiveInterval(1); // not respected by server
            }
        } catch (SignInException message) {
            mv = error(pageElements, message.getMessage());
            return templateEngine.render(mv);
        }
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

    /**
     * Handle error messages for signing in
     * @param pageElements the elements on the page
     * @param message the message to return to the user
     * @return the UI view that the user sees
     */
    private ModelAndView error(Map<String, Object> pageElements, String message) {
        pageElements.put(GetSignInRoute.MESSAGE_ATTR, Message.info(message));
        return new ModelAndView(pageElements, VIEW_NAME);
    }
}
