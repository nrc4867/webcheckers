package com.webcheckers.ui.CheckersPlay;

import com.google.gson.Gson;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostBackupRoute implements Route {

    private final Gson gson;

    public PostBackupRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return gson.toJson(Message.error("No True implementation"));
    }
}
