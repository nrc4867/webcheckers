package com.webcheckers.ui.CheckersSpectate;

import com.google.gson.Gson;
import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import static com.webcheckers.util.Checkers.getPlayer;

import com.webcheckers.util.Message;
import com.webcheckers.util.Spectators;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostSpectatorCheckRoute implements Route {

    private final static Message FALSE = Message.info("false");
    private final static Message TRUE = Message.info("true");

    private final Gson gson;

    public PostSpectatorCheckRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session httpSession = request.session();
        Player player = getPlayer(httpSession);
        if(player == null || player.getBoardController() == null) {
            return gson.toJson(FALSE);
        }

        BoardController controller = player.getBoardController();

        if (Spectators.getTurn(httpSession) < controller.getMovesMade()) {
            Spectators.setTurn(httpSession, controller.getMovesMade());
            return gson.toJson(TRUE);
        }

        return gson.toJson(FALSE);
    }
}
