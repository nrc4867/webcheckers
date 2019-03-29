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


import static com.webcheckers.ui.GetGameRoute.MATCH_TURN_FORMAT;
import static com.webcheckers.util.Checkers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Post Check Turn Route Test
 * @author Abhaya Tamrakar
 */
@Tag("Application-Tier")
public class PostCheckTurnRouteTest {
    private Request request;
    private Session session;
    private Response response;
    private GsonBuilder builder;
    private Gson gson;
    private PostCheckTurnRoute pctr;



    private Player player1;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        builder = new GsonBuilder();
        response = mock(Response.class);

        gson = builder.create();

        pctr = new PostCheckTurnRoute(gson);
    }

    @Test
    public void handleNullTest() throws Exception {
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json;
        when(player1.getBoardController()).thenReturn(null);

        when(getMoves(session)).thenReturn(new ArrayList<>());

        json = (String) pctr.handle(request,response);
        Message a = gson.fromJson(json,Message.class);

        assertEquals(PostCheckTurnRoute.NO_GAME,a.getText());
    }

    @Test
    public void handleNotNullTest() throws Exception {
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json=null;
        when(player1.getBoardController()).thenReturn(mock(BoardController.class));

        when(getMoves(session)).thenReturn(new ArrayList<>());

        when(player1.checkTurn()).thenReturn(true);

        json = (String) pctr.handle(request,response);
        Message a = gson.fromJson(json,Message.class);

        assertEquals("true",a.getText());
    }

    @Test
    public void handleCheckTest() throws Exception {
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json=null;
        when(player1.getBoardController()).thenReturn(mock(BoardController.class));

        when(getMoves(session)).thenReturn(new ArrayList<>());

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Board board = new Board(p1,p2);

        when(player1.checkTurn()).thenReturn(false);
        when(player1.getBoard()).thenReturn(board);

        json = (String) pctr.handle(request,response);
        Message a = gson.fromJson(json,Message.class);

        assertEquals(String.format(MATCH_TURN_FORMAT, p1.toString()),a.getText());
        assertNotEquals(String.format(MATCH_TURN_FORMAT, p2.toString()),a.getText());

    }




}
