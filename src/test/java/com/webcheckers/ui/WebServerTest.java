package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

/**
 * Unit test for WebServer
 * @author Abhaya Tamrakar
 */
@Tag("UI-Tier")
public class WebServerTest {

    private PlayerLobby playerLobby;
    private TemplateEngine engine;
    private Gson gson;
    private WebServer server;

    @BeforeEach
    public void setup(){
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        gson = new Gson();

        server = new WebServer(engine,gson);
    }

    @Test
    public void test(){
        server.initialize();

    }

}
