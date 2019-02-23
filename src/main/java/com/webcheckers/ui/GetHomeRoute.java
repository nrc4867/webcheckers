package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.NavBar;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;


  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param  playerLobby
   *   the Playerlobby containing the players on this server
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "PlayerLobby is required");
    //
    LOG.config("GetHomeRoute is initialized.");
  }


  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session httpSession = request.session();
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    // get current session player
    Player current = (Player) httpSession.attribute(NavBar.PLAYER_SIGNIN_KEY);
    Set<String> names = new HashSet<>();
    //remove player from displaying yourself on lobby
    if(current!=null) {

      for (Player player : PlayerLobby.getPlayers()) {
        if (!current.equals(player)) {
          names.add(player.getName());
        }
      }
    }
      // create the list of players
      vm.put("users", names.toArray());

    NavBar.updateNavBar(vm, httpSession);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
