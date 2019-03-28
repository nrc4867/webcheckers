package com.webcheckers.ui.CheckersPlay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import spark.*;

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
    public void handleTest(){
        player1 = mock(Player.class);
        when(session.attribute(Attributes.PLAYER_SIGNIN_KEY)).thenReturn(player1);
        String json=null;
//        when(!PostValidateRoute.playerInGame(player1)).thenReturn(true);
        when(player1.isSelectedPlayerInGame()).thenReturn(false);
        try {
            json = (String) pbr.handle(request,response);
            Message a = gson.fromJson(json,Message.class);
            System.out.println(a.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
