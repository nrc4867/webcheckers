package com.webcheckers.ui.CheckersReplay;

import com.webcheckers.appl.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Checkers;
import spark.*;

import java.util.Objects;

import static spark.Spark.halt;

public class GetReplayRoute implements Route {

    private final TemplateEngine templateEngine;

    public GetReplayRoute(TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "Template engine cannot be null");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session httpsSession = request.session();
        Player player = Checkers.getPlayer(httpsSession);

        if (!Checkers.playerHasBoard(player)) { // a player must have a board to view this page
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
        return null;
    }
}
