package com.webcheckers.appl;


import java.util.logging.Logger;
import java.util.Set;
import java.util.HashSet;

/**
 * Object to keep track of Players in WebCheckers.
 *
 * @author Michael Bianconi
 * @since Sprint 1
 */
public class PlayerLobby {

  private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

  public static final String NAME_TAKEN_MESSAGE =
      "That name's already taken!.";

  public static final String NAME_INVALID_MESSAGE =
      "Your name must have at least one alphanumeric character!";

  private static Set<String> namesInUse = new HashSet<>();


  /**
   * Creates a new PlayerLobby. All SignInServices reference the same
   * namesInUse set.
   *
   * @author Michael Bianconi
   */
  public PlayerLobby() {
    LOG.fine("New sign in service instance created.");
  }


  /**
   * Checks that the name is valid (contains at least one alphanumeric
   * character).
   *
   * We could probably regex this to make it simpler, but not right now.
   *
   * @param name Name to check.
   * @return Returns true if the name is valid, false otherwise.
   * @author Michael Bianconi
   */
  public boolean validName(String name) {

    for (char c : name.toCharArray()) {
      if (Character.isDigit(c) || Character.isLetter(c)) {
        return true;
      }
    }

    return false;
  }


  /**
   * Attempts to add a name to the names in use list.
   * Reserved names cannot be used by any other service.
   *
   * @param name Name to add.
   * @throws SignInException if name is taken or invalid.
   * @see freeName()
   * @author Michael Bianconi
   */
  public void reserveName(String name) throws SignInException {

    if (!validName(name)) {
      throw new SignInException(NAME_INVALID_MESSAGE);
    }

    if (!namesInUse.add(name)) {
      throw new SignInException(NAME_TAKEN_MESSAGE);
    }

    return;
  }


  /**
   * Attempts to remove a name from the list.
   *
   * @param name Name to remove.
   * @return Returns false if the name isn't in the list (should never happen).
   * @see reserveName()
   * @author Michael Bianconi
   */
  public boolean freeName(String name) {

    return namesInUse.remove(name);
  }


  /**
   * @return Returns the number of reserved names.
   * @author Michael Bianconi
   */
  public int numReserved() {

    return namesInUse.size();
  }

}