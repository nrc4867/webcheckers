package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the SignIn page.
 *
 * @author Michael Bianconi
 */
public class GetSignInRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  static final String TITLE_ATTR = "title";
  static final String MESSAGE_ATTR = "message";

  static final String VIEW_FORM = "signin.ftl";
  static final Message SIGNIN_MSG = Message.info("Please register a name for yourself.");
  static final String TITLE = "Sign in";

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSignInRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetSignInRoute is initialized.");
  }

  /**
   * Render the WebCheckers SignIn page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the SignIn page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetSignInRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);

    // display a user message in the Sign In page
    vm.put(MESSAGE_ATTR, SIGNIN_MSG);

    // render the View
    return templateEngine.render(new ModelAndView(vm , VIEW_FORM));
  }

}
