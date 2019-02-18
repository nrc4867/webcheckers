package com.webcheckers.ui;

import com.webcheckers.appl.SignInException;
import com.webcheckers.appl.SignInService;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostSignInRoute implements Route {

    public static final String VIEW_NAME = "signin.ftl";

    private final SignInService signInService;
    private final TemplateEngine templateEngine;

    public PostSignInRoute(SignInService signInService, TemplateEngine templateEngine) {
        Objects.requireNonNull(signInService, "SignInService must not be null");
        Objects.requireNonNull(templateEngine, "TemplateEngine must not be null");

        this.signInService = signInService;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // Start creating elements that must be placed in the page
        final Map<String, Object> pageElements = new HashMap<>();
        pageElements.put(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);

        final String playerName = request.queryParams("username");

        ModelAndView mv;
        try {
            signInService.reserveName(playerName);
        } catch (SignInException message) {
            mv = error(pageElements, message.getMessage());
            return templateEngine.render(mv);
        }
        return null;
    }

    private ModelAndView error(Map<String, Object> pageElements, String message) {
        pageElements.put(GetSignInRoute.MESSAGE_ATTR, Message.info(message));
        return new ModelAndView(pageElements, VIEW_NAME);
    }
}
