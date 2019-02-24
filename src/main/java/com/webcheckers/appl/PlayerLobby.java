package com.webcheckers.appl;


import com.webcheckers.model.Color;
import com.webcheckers.model.Player;

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
      "That name's already taken!";

  public static final String NAME_INVALID_MESSAGE =
      "Your name must have at least one alphanumeric character!";

  private static Set<Player> players = new HashSet<>();


  /**
   * Creates a new PlayerLobby. All SignInServices reference the same
   * players set.
   *
   * @author Michael Bianconi
   */
  public PlayerLobby() {
    LOG.fine("New sign in service instance created.");
  }


  /**
   * Checks that the name is valid (contains at least one alphanumeric
   * character) with the help of regular expressions.
   *
   *
   * @param name Name to check.
   * @return Returns true if the name is valid, false otherwise.
   * @author Michael Bianconi, Dylan Cuprewich
   */
  public boolean validName(String name) {

    // name cannot only contain whitespace
    if (name.length() == 0) {return false;}

    // name has no symbols
    for (char c : name.toCharArray()) {
      if (!Character.isLetterOrDigit(c) && c!=' ') {return false;}
    }

    return true;
  }

  /**
   * Attempts to add a name to the names in use list.
   * Reserved names cannot be used by any other service.
   *
   * @param name Name to add.
   * @throws SignInException if name is taken or invalid.
   * @author Michael Bianconi
   */
  public Player reserveName(String name) throws SignInException {
    name = name.trim();
    Player newPlayer = new Player(name);
    if (!validName(name)) {
      throw new SignInException(NAME_INVALID_MESSAGE);
    }
    if(players.contains(newPlayer)) {
      throw new SignInException(NAME_TAKEN_MESSAGE);
    }
    players.add(newPlayer);
    return newPlayer;
  }


  /**
   * Attempts to remove a name from the list.
   *
   * @param player player to remove.
   * @return Returns false if the name isn't in the list (should never happen).
   * @author Michael Bianconi
   */
  public boolean removePlayer(Player player) {
    return players.remove(player);
  }


  /**
   * @return Returns the number of reserved names.
   * @author Michael Bianconi
   */
  public int numReserved() {
    return players.size();
  }

  /**
   * Get the Players object list from the lobby
   * @return Players List
   * @author Abhaya Tamrakar
   */
  public static Set<Player> getPlayers() {
    return players;
  }

  /**
   * @return the list of player names that are on this server
   */
  public synchronized Set<String> names() {
    Set<String> names = new HashSet<>();
    for(Player player: players) {
      names.add(player.getName());
    }
    return names;
  }

}