package com.webcheckers.ui.CheckersPlay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static com.webcheckers.util.Checkers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Post Validate Route Test
 * @author Abhaya Tamrakar
 */
@Tag("Application-Tier")
public class PostValidateRouteTest {
    private Request request;
    private Session session;
    private Response response;
    private GsonBuilder builder;
    private Gson gson;
    private PostValidateRoute pvr;



    private Player player1;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        builder = new GsonBuilder();
        response = mock(Response.class);

        gson = builder.create();


        pvr = new PostValidateRoute(gson);
    }

    @Test
    public void handleNullTest() throws Exception {
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json;
        when(player1.getBoardController()).thenReturn(null);

        when(getMoves(session)).thenReturn(new ArrayList<>());


        json = (String) pvr.handle(request,response);
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

        Move m1= new Move();
        Position start=new Position();
        start.setRow(5);
        start.setCell(0);
        Position end=new Position();
        end.setRow(4);
        end.setCell(1);
        m1.setStart(start);
        m1.setEnd(end);

        ArrayList<Move> moves = new ArrayList<>();

        when(getMoves(session)).thenReturn(moves);
        String jsontostring = gson.toJson(m1);

        when(request.body()).thenReturn("actionData="+jsontostring);
        String json;

        json = (String) pvr.handle(request,response);
        Message a = gson.fromJson(json, Message.class);

        assertEquals(pvr.VALID_MOVE,a.getText());

    }

    @Test
    public void handleError() throws Exception {
        player1 = new Player("p1");
        Player player2 = new Player("p2");
        Board b = new Board(player1,player2);
        BoardController bc = new BoardController(b);
        player1.setBoardController(bc);
        Move m1= new Move();
        Position start=new Position();
        start.setRow(5);
        start.setCell(0);
        Position end=new Position();
        end.setRow(4);
        end.setCell(1);
        m1.setStart(start);
        m1.setEnd(end);
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(m1);

        when(getMoves(session)).thenReturn(moves);

        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String jsontostring = gson.toJson(m1);

        when(request.body()).thenReturn("actionData="+jsontostring);
        String json;
        json = (String) pvr.handle(request,response);
        Message a = gson.fromJson(json, Message.class);

        assertEquals(pvr.INVALID_MOVE,a.getText());
    }


}
