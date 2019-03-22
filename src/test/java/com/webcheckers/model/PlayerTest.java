package com.webcheckers.model;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

/**
 * A test class for the Player class.
 *
 * @author Dylan Cuprewich
 */
@Tag("Model-tier")
public class PlayerTest {

   protected static String playerName = "Test Name";
   protected static Color colorRed = Color.RED;
   protected static Color colorWhite = Color.WHITE;
   protected static PlayerLobby playerLobby;
   protected static PlayerLobby playerLobbyAnother;
   protected static BoardController playerBoard;

   /**
    * Component under Testing.
    */
   protected static Player playerTest1 = new Player(playerName);
   protected static Player playerTest2 = new Player(playerName, colorWhite);

   /**
    * CONSTRUCTORS
    */

   @Test
   public void single_param_constructor(){
      Player player1 = new Player(playerName);

      assertEquals(playerName, player1.getName());
      assertNull(player1.getColor());
   }

   @Test
   public void two_param_constructor(){
      Player player2 = new Player(playerName, colorRed);

      assertEquals(playerName, player2.getName());
      assertEquals(colorRed, player2.getColor());
   }

   /**
    * OBJECT CHECKS
    */

   @Test
   public void equals_test(){
      assertEquals(playerTest1, playerTest2);
   }

   @Test
   public void isSelectedPlayer1InGame_test(){
      assertFalse(playerTest1.isSelectedPlayerInGame());
   }

   @Test
   public void isSelectedPlayer2InGame_test(){
      assertFalse(playerTest2.isSelectedPlayerInGame());


   }

   @Test
   public void hashCode_test(){
      assertEquals(playerName.hashCode(), playerTest1.hashCode());
      assertEquals(playerName.hashCode(), playerTest2.hashCode());
   }

   @Test
   public void cleanup_test(){


      //playerLobby.containsPlayer(playerTest1);
   }

   /**
    * GETTERS
    */

   @Test
   public void getBoard_test(){
      Player player1 = new Player(playerName);
      Player player2 = new Player(playerName, colorRed);

      assertNull(player1.getBoard(), "Player 1 has no board");
      assertNull(player2.getBoard(), "Player 2 has no board");
   }

   @Test
   public void getLobby_test(){
      Player player1 = new Player(playerName);
      Player player2 = new Player(playerName, colorRed);

      assertNull(player1.getLobby());
      assertNull(player2.getLobby());

   }

   /**
    * SETTERS
    */

   @Test
   public void setColor_test(){
      Player player1 = new Player(playerName);
      Player player2 = new Player(playerName, colorRed);

      player1.setColor(colorWhite);
      player2.setColor(colorWhite);

      assertEquals(colorWhite, player1.getColor());
      assertEquals(colorWhite, player2.getColor());
   }

   @Test
   public void selectInGameOpponent_test(){
      Player player1 = new Player(playerName);
      Player player2 = new Player(playerName, colorRed);

      player1.selectInGameOpponent(true);
      player2.selectInGameOpponent(true);

      assertTrue(player1.isSelectedPlayerInGame());
      assertTrue(player2.isSelectedPlayerInGame());
   }

   @Test
   public void setBoard_test(){
      Player player1 = new Player(playerName);

      playerBoard = mock(BoardController.class);

      player1.setBoardController(playerBoard);
      player1.getBoard();

      verify(playerBoard, times(1)).getBoard();
   }

   @Test
   public void setLobby_test(){
      Player player1 = new Player(playerName);

      playerLobby = mock(PlayerLobby.class);

      player1.setLobby(playerLobby);

      assertEquals(playerLobby, player1.getLobby());

      playerLobbyAnother = mock(PlayerLobby.class);

      player1.setLobby(playerLobbyAnother);

      assertNotEquals(playerLobby, player1.getLobby());
      assertEquals(playerLobbyAnother, player1.getLobby());
   }

}
