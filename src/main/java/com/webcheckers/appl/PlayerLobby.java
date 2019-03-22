package com.webcheckers.appl;


import java.util.Collection;
import java.util.Hashtable;
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
      "Your name must have one alphanumeric character and no symbols!";

  private Hashtable<String, Player> players = new Hashtable<>();


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
  public static boolean validName(String name) {

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
  public synchronized Player reserveName(String name) throws SignInException {
    name = name.trim();
    Player newPlayer = new Player(name);
    if (!validName(name)) {
      throw new SignInException(NAME_INVALID_MESSAGE);
    }
    if(players.contains(newPlayer)) {
      throw new SignInException(NAME_TAKEN_MESSAGE);
    }
    players.put(name, newPlayer);
    newPlayer.setLobby(this);

    return newPlayer;
  }


  /**
   * Attempts to remove a name from the list.
   *
   * @param player player to remove.
   * @return Returns false if the name isn't in the list (should never happen).
   * @author Michael Bianconi
   */
  public synchronized Player removePlayer(Player player) {
    return players.remove(player.getName());
  }


  /**
   * @return Returns the number of reserved names.
   * @author Michael Bianconi
   */
  public synchronized int numReserved() {
    return players.size();
  }

  /**
   * Get the Players object list from the lobby
   * @return Players List
   * @author Abhaya Tamrakar
   */
  public synchronized Collection<Player> getPlayers() {
    return players.values();
  }

  public synchronized boolean containsPlayer(Player player) {
    return containsPlayer(player.getName());
  }

  public synchronized boolean containsPlayer(String name) {
    return players.containsKey(name);
  }

  /**
   * Get a player
   * @param name the players name
   * @return a player, if the player doesn't exist then null
   */
  public synchronized Player getPlayer(String name) {
    if (name == null) return null;
    return players.getOrDefault(name, null);
  }

  /**
   * @return the list of player names that are on this server
   */
  public synchronized Set<String> names() {
    return new HashSet<>(players.keySet());
  }

}