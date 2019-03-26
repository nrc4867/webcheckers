package com.webcheckers.appl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import static com.webcheckers.appl.PlayerLobby.validName;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Hashtable;

/**
 * A test class for the PlayerLobby class.
 *
 * @author Dylan Cuprewich & Abhaya Tamrakar
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

   private final String p1_name = "Test Name";
   private final String p2_name = "Harry";
   private final String p3_name = "Jack";
   private final String p4_name = "Tony";
   private final String invalid = "!-TOy";
   private final String fail_name = " ";
   private final String empty_name= "";



   private PlayerLobby playerLobby;
   private Hashtable<String, Player> players;
   private Player p1;
   private Player p2;
   private Player p3;



/*
Setup the Environment
 */
   @BeforeEach
   public void testSetup(){
      players=new Hashtable<>();
      playerLobby= new PlayerLobby(players);

      p1 = new Player(p1_name);
      p2 = new Player(p2_name);
      p3 = new Player(p3_name);
      players.put(p1_name,p1);
      players.put(p2_name,p2);


   }

   @Test
   public void containsPlayer_test(){
      assertTrue(playerLobby.containsPlayer(p1_name));
      assertEquals(playerLobby.containsPlayer(p1),playerLobby.containsPlayer(p1_name));
      assertTrue(playerLobby.containsPlayer(p2));
      assertFalse(playerLobby.containsPlayer(p3_name));
      assertFalse(playerLobby.containsPlayer(p3));
   }
   @Test
   public void validNameTest(){
      assertTrue(validName(p4_name));
      assertFalse(validName(invalid));
      assertFalse(validName(fail_name));
      assertFalse(validName(empty_name));
   }

   @Test
   public void reserveNameTest() throws SignInException {
      playerLobby.reserveName(p4_name);
      assertTrue(playerLobby.containsPlayer(p4_name));
      assertThrows(SignInException.class, () -> {
         playerLobby.reserveName(fail_name);}, "Invalid name");
   }

   @Test
   public void numReservedTest(){
      assertSame(2, playerLobby.numReserved());
   }

   @Test
   public void getPlayerTest(){
      assertEquals(playerLobby.getPlayer(p2_name),p2);
      assertNull(playerLobby.getPlayer(p4_name));
      assertNotNull(playerLobby.getPlayers());
   }

   @Test
   public void namesTest() {
      assertEquals(players.keySet(), playerLobby.names());
   }


}
