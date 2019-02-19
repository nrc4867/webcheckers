package com.webcheckers.ui;

import com.webcheckers.appl.SignInException;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

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

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     *
     * @param playerLobby the username checker service
     * @param templateEngine the HTML template rendering engine
     */
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        Objects.requireNonNull(playerLobby, "PlayerLobby must not be null");
        Objects.requireNonNull(templateEngine, "TemplateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
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
        // Start creating elements that must be placed in the page
        final Map<String, Object> pageElements = new HashMap<>();
        pageElements.put(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);

        final String playerName = request.queryParams("username");

        ModelAndView mv;
        try {
            playerLobby.reserveName(playerName);
        } catch (SignInException message) {
            mv = error(pageElements, message.getMessage());
            return templateEngine.render(mv);
        }
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
