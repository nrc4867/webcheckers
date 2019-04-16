package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.BoardList;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.Player;
import com.webcheckers.util.NavBar;
import spark.*;

import com.webcheckers.util.Attributes;
import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  //message for welcoming player
  static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  static final String VIEW_NAME = "home.ftl";
  static final String TITLE_ATTR = "title";
  static final String MESSAGE_ATTR = "message";
  static final String TITLE = "Welcome!";
  public final String CHALLENGE_PARAM = "challenge";

  // message for if player selects another player who us in game
  static final Message PLAYER_IN_GAME = Message.info("Selected Player is already in Game.");

  private final TemplateEngine templateEngine;
  private final BoardList boardList;
  private final PlayerLobby playerLobby;


  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param  playerLobby
   *   the Playerlobby containing the players on this server
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final BoardList boardList, final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "PlayerLobby is required");
    this.boardList = Objects.requireNonNull(boardList, "Boardlist is required");
    //
    LOG.config("GetHomeRoute is initialized.");
  }


  /**
   * Render the WebCheckers Home page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Home page
   *
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session httpSession = request.session();

    // get current session player
    Player current = (Player) httpSession.attribute(Attributes.PLAYER_SIGNIN_KEY);

    if(current != null && current.inGame()) {
      response.redirect(WebServer.GAME_URL);
      return null;
    }

    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);

    vm.put(MESSAGE_ATTR, WELCOME_MSG);

    Set<String> names = new HashSet<>();
    Set<String> playersInGame = playerLobby.getPlayersInGame();

    if(current != null) {
      names.addAll(playerLobby.getAvailablePlayers());
      names.remove(current.getName()); //remove player from displaying yourself on lobby
      vm.put("ingame", playersInGame.toArray());
      vm.put("pastgames", boardList.getBoardsCreated());
    }else{
      int num_players= playerLobby.getPlayers().size();
      vm.put("ingame", new String[]{"Number of Players playing checkers: " + playersInGame.size()});
      vm.put("pastgames", new String[]{"You must sign in to re-watch past games"});
      names.add("Number of Players online: "+ num_players); //show Number of players online.
    }
    // create the list of players
    vm.put("users", names.toArray());

    NavBar.updateNavBar(vm, httpSession);

    // render the View
    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }
}
