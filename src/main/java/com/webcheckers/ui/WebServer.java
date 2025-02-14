package com.webcheckers.ui;

import static spark.Spark.*;

import java.util.Hashtable;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;

import com.webcheckers.appl.BoardList;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.chinook.Chinook;
import com.webcheckers.ui.CheckersPlay.*;
import com.webcheckers.ui.CheckersReplay.*;
import com.webcheckers.ui.CheckersSpectate.GetExitRoute;
import com.webcheckers.ui.CheckersSpectate.PostSpectatorCheckRoute;
import spark.TemplateEngine;


/**
 * The server that initializes the set of HTTP request handlers.
 * This defines the <em>web application interface</em> for this
 * WebCheckers application.
 *
 * <p>
 * There are multiple ways in which you can have the client issue a
 * request and the application generate responses to requests. If your team is
 * not careful when designing your approach, you can quickly create a mess
 * where no one can remember how a particular request is issued or the response
 * gets generated. Aim for consistency in your approach for similar
 * activities or requests.
 * </p>
 *
 * <p>Design choices for how the client makes a request include:
 * <ul>
 *     <li>Request URL</li>
 *     <li>HTTP verb for request (GET, POST, PUT, DELETE and so on)</li>
 *     <li><em>Optional:</em> Inclusion of request parameters</li>
 * </ul>
 * </p>
 *
 * <p>Design choices for generating a response to a request include:
 * <ul>
 *     <li>View templates with conditional elements</li>
 *     <li>Use different view templates based on results of executing the client request</li>
 *     <li>Redirecting to a different application URL</li>
 * </ul>
 * </p>
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class WebServer {
  private static final Logger LOG = Logger.getLogger(WebServer.class.getName());
  public static final Boolean DEBUG = true;

  //
  // Constants
  //

  /**
   * The URL pattern to request the Home page.
   */
  public static final String HOME_URL = "/";
  public static final String SIGNIN_URL = "/signin";
  public static final String SIGNOUT_URL = "/signout";
  public static final String GAME_URL = "/game";

   //
   // AJAX URLS
   //

  /**
   * URL used by players during gameplay
   */
  public static final String CHECK_TURN_URL = "/checkTurn";
  public static final String RESIGN_URL = "/resignGame";
  public static final String SUBMIT_TURN_URL = "/submitTurn";
  public static final String BACKUP_URL = "/backupMove";
  public static final String VALIDATE_MOVE_URL = "/validateMove";

  /**
   * URL used by players during spectator mode
   */
//  public static final String SPECTATOR_GAME_URL = "/spectator/game";
  public static final String SPECTATOR_CHECK_TURN_URL = "/spectator/checkTurn";
  public static final String SPECTATOR_STOP_WATCHING_URL = "/spectator/stopWatching";

  /**
   * URL used by players during replay mode
   */
  public static final String REPLAY_START = "/replay";
  public static final String REPLAY_GAME = "/replay/game";
  public static final String REPLAY_STOP = "/replay/stopWatching";
  public static final String REPLAY_NEXT = "/replay/nextTurn";
  public static final String REPLAY_PREVIOUS = "/replay/previousTurn";

  //
  // Attributes
  //

  private final PlayerLobby playerLobby;
  private final BoardList boardList;
  private final TemplateEngine templateEngine;
  private final Gson gson;

  //
  // Constructor
  //

  /**
   * The constructor for the Web Server.
   *
   * @param templateEngine
   *    The default {@link TemplateEngine} to render page-level HTML views.
   * @param gson
   *    The Google JSON parser object used to render Ajax responses.
   *
   * @throws NullPointerException
   *    If any of the parameters are {@code null}.
   */
  public WebServer(final TemplateEngine templateEngine, final Gson gson) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    //
    this.templateEngine = templateEngine;
    this.boardList = new BoardList();
    this.gson = gson;
    this.playerLobby = new PlayerLobby(new Hashtable<String, Player>());

    this.playerLobby.addPlayer(new Chinook("Dylan`"));

//    if(DEBUG) {
//      WebSeverCommandPrompt cmd = new WebSeverCommandPrompt(playerLobby);
//      cmd.start();
//    }

  }

  //
  // Public methods
  //

  /**
   * Initialize all of the HTTP routes that make up this web application.
   *
   * <p>
   * Initialization of the web server includes defining the location for static
   * files, and defining all routes for processing client requests. The method
   * returns after the web server finishes its initialization.
   * </p>
   */
  public void initialize() {

    // Configuration to serve static files
    staticFileLocation("/public");

    //// Setting any route (or filter) in Spark triggers initialization of the
    //// embedded Jetty web server.

    //// A route is set for a request verb by specifying the path for the
    //// request, and the function callback (request, response) -> {} to
    //// process the request. The order that the routes are defined is
    //// important. The first route (request-path combination) that matches
    //// is the one which is invoked. Additional documentation is at
    //// http://sparkjava.com/documentation.html and in Spark tutorials.

    //// Each route (processing function) will check if the request is valid
    //// from the client that made the request. If it is valid, the route
    //// will extract the relevant data from the request and pass it to the
    //// application object delegated with executing the request. When the
    //// delegate completes execution of the request, the route will create
    //// the parameter map that the response template needs. The data will
    //// either be in the value the delegate returns to the route after
    //// executing the request, or the route will query other application
    //// objects for the data needed.

    //// FreeMarker defines the HTML response using templates. Additional
    //// documentation is at
    //// http://freemarker.org/docs/dgui_quickstart_template.html.
    //// The Spark FreeMarkerEngine lets you pass variable values to the
    //// template via a map. Additional information is in online
    //// tutorials such as
    //// http://benjamindparrish.azurewebsites.net/adding-freemarker-to-java-spark/.

    //// These route definitions are examples. You will define the routes
    //// that are appropriate for the HTTP client interface that you define.
    //// Create separate Route classes to handle each route; this keeps your
    //// code clean; using small classes.

    // Shows the CheckersPlay game Home page.
    get(HOME_URL, new GetHomeRoute(playerLobby, boardList, templateEngine));
    get(SIGNIN_URL, new GetSignInRoute(templateEngine));

    post(SIGNIN_URL, new PostSignInRoute(playerLobby, templateEngine));
    post(SIGNOUT_URL, new PostSignOutRoute(playerLobby, templateEngine));

    get(GAME_URL, new GetGameRoute(templateEngine, gson));
    post(GAME_URL, new PostGameRoute(playerLobby, boardList));

    // Handles for Play state AJAX calls
    post(CHECK_TURN_URL, new PostCheckTurnRoute(gson));
    post(RESIGN_URL, new PostResignRoute(gson));
    post(VALIDATE_MOVE_URL, new PostValidateRoute(gson));
    post(SUBMIT_TURN_URL, new PostSubmitRoute(gson));
    post(BACKUP_URL, new PostBackupRoute(gson));

    // Handles for Spectator
    get(SPECTATOR_STOP_WATCHING_URL, new GetExitRoute());
    post(SPECTATOR_CHECK_TURN_URL, new PostSpectatorCheckRoute(gson));

    //Handles for replay
    post(REPLAY_START, new PostReplayRoute(boardList));
    get(REPLAY_GAME, new GetReplayRoute(templateEngine, gson));
    post(REPLAY_NEXT, new PostNextTurnRoute(gson));
    post(REPLAY_PREVIOUS, new PostPreviousTurnRoute(gson));
    get(REPLAY_STOP, new GetStopWatchingRoute());

    LOG.config("WebServer is initialized.");
  }

}