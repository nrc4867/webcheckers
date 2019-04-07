package com.webcheckers.ui.CheckersSpectate;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSpectateRoute implements Route{

    @Override
    public Object handle(Request request, Response response) throws Exception {
        for (String s:
                request.queryParams()) {
            System.out.println(s);
        }
        return null;
    }
}
