package com.webcheckers.ui.CheckersSpectate;

import static com.webcheckers.util.Checkers.getPlayer;
import static spark.Spark.halt;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class GetExitRoute implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session httpSession = request.session();
        Player player = getPlayer(httpSession);
        if (player != null)
            player.removeBoard();
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}
