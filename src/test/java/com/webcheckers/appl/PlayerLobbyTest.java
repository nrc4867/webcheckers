package com.webcheckers.appl;

import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Hashtable;

/**
 * A test class for the PlayerLobby class.
 *
 * @author Dylan Cuprewich
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

   protected static PlayerLobby playerLobby = new PlayerLobby();
   protected static Hashtable<String, Player> players = new Hashtable<>();
   protected static String p1_name = "Test Name";
   protected static String p2_name = null;
   protected static String fail_name = " ";
   protected static Player p1 = new Player(p1_name);

   /**
    * CONSTRUCTORS
    */

   /**
    * OBJECTS
    */

   /**
    * GETTERS
    */

   @Test
   public void containsPlayer_test(){
      PlayerLobby pLobby = mock(PlayerLobby.class);
      when(pLobby.containsPlayer(p1_name)).thenReturn(true);

      assertNotEquals(pLobby.containsPlayer(p1_name), playerLobby.containsPlayer(p1));
   }

   @Test
   public void geqtPlayer_test(){
      assertNull(playerLobby.getPlayer(p2_name));
   }

   @Test
   public void getPlayers_test(){
      assertNotNull(playerLobby.getPlayers());
   }

   @Test
   public void numReserved_test(){
      assertSame(0, playerLobby.numReserved());
   }

   @Test
   public void names_test(){
      assertSame(new HashSet<String>().getClass(), playerLobby.names().getClass());
   }

   /**
    * SETTERS
    */

   @Test
   public void reserveName_test(){
      assertThrows(SignInException.class, () -> {
         playerLobby.reserveName(fail_name);}, "Invalid name");

   }
}
