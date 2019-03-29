package com.webcheckers.ui.CheckersPlay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Board;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.ArrayList;


import static com.webcheckers.util.Checkers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Post Resign Route Test
 * @author Abhaya Tamrakar
 */
@Tag("Application-Tier")
public class PostResignRouteTest {
    private Request request;
    private Session session;
    private Response response;
    private GsonBuilder builder;
    private Gson gson;
    private PostResignRoute prr;



    private Player player1;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        builder = new GsonBuilder();
        response = mock(Response.class);

        gson = builder.create();

        prr = new PostResignRoute(gson);
    }

    @Test
    public void handleNullTest() throws Exception {
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json;
        when(player1.getBoardController()).thenReturn(null);

        when(getMoves(session)).thenReturn(new ArrayList<>());


        json = (String) prr.handle(request,response);
        Message a = gson.fromJson(json,Message.class);

        assertEquals(PostCheckTurnRoute.NO_GAME,a.getText());
    }

    @Test
    public void handleTest() throws Exception {
        player1 = new Player("p1");
        Player player2 = new Player("p2");
        Board b = new Board(player1,player2);
        BoardController bc = new BoardController(b);
        player1.setBoardController(bc);

        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json;


        when(getMoves(session)).thenReturn(new ArrayList<>());


        json = (String) prr.handle(request,response);
        Message a = gson.fromJson(json,Message.class);

        assertEquals(prr.resign,a.getText());

    }
}
