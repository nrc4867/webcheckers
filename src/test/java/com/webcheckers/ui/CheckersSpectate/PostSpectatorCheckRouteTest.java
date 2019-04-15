package com.webcheckers.ui.CheckersSpectate;

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
 * Post Spectator Check Route Test
 * @author Abhaya Tamrakar
 */
@Tag("Application-Tier")
public class PostSpectatorCheckRouteTest {
    private Request request;
    private Session session;
    private Response response;
    private GsonBuilder builder;
    private Gson gson;
    private PostSpectatorCheckRoute pscr;
    private Player player1;
    private final static Message FALSE = Message.info("false");
    private final static Message TRUE = Message.info("true");

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        builder = new GsonBuilder();
        response = mock(Response.class);

        gson = builder.create();

        pscr = new PostSpectatorCheckRoute(gson);
    }

    @Test
    public void handleNullTest() throws Exception {
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        when(player1.getBoardController()).thenReturn(null);
        String json;
        json = (String) pscr.handle(request,response);
        Message fromJson = gson.fromJson(json,Message.class);
        assertEquals(FALSE,fromJson);
    }

    @Test
    public void handleNull2Test() throws Exception {
        player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        Board board = new Board(player1,player2);
        BoardController bc= new BoardController(board);
        when(player1.getBoardController()).thenReturn(bc);


        when(session.attribute(Attributes.SPECTATOR_TURN_KEY)).thenReturn(null);
        String json;
        json = (String) pscr.handle(request,response);
        Message fromJson = gson.fromJson(json,Message.class);
        assertEquals(FALSE,fromJson);
    }
    @Test
    public void handleNotNullTest() throws Exception {
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        BoardController b= mock(BoardController.class);
        when(player1.getBoardController()).thenReturn(b);
        when(b.getMovesMade()).thenReturn(2);

        String json;
        json = (String) pscr.handle(request,response);
        Message fromJson = gson.fromJson(json,Message.class);
        assertEquals(TRUE,fromJson);
    }
}
