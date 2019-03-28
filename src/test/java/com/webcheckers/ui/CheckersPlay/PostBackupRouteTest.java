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
import org.mockito.ArgumentCaptor;
import spark.*;

import java.util.ArrayList;

import static com.webcheckers.ui.CheckersPlay.PostBackupRoute.SUCCESS_BACKUP;
import static com.webcheckers.util.Checkers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Post Backup Route Test
 * @author Abhaya Tamrakar
 */
@Tag("Application-Tier")
public class PostBackupRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private GsonBuilder builder;
    private Gson gson;
    private PostBackupRoute pbr;



    private Player player1;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        builder = new GsonBuilder();
        response = mock(Response.class);

        gson = builder.create();

        pbr = new PostBackupRoute(gson);
    }


    @Test
    public void handleNullTest() throws Exception {
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json;
        when(player1.getBoardController()).thenReturn(null);

        when(getMoves(session)).thenReturn(new ArrayList<>());

        json = (String) pbr.handle(request,response);
        Message a = gson.fromJson(json,Message.class);
        System.out.println(a.getText());
        assertEquals(PostCheckTurnRoute.NO_GAME,a.getText());
    }

    @Test
    public void handleNotNullTest() throws Exception {
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json=null;
        when(player1.getBoardController()).thenReturn(mock(BoardController.class));

        when(getMoves(session)).thenReturn(new ArrayList<>());

        json = (String) pbr.handle(request,response);
        Message a = gson.fromJson(json,Message.class);
        System.out.println(a.getText());
        assertEquals(SUCCESS_BACKUP,a.getText());
    }
}
