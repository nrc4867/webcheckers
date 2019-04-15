package com.webcheckers.appl.chinook;
import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Testing The AI Chinook
 * @author Abhaya Tamrakar
 */
@Tag("Application-Tier")
public class ChinookTest {
    private BoardController boardController;
    private Player chinook;
    private Chinook chinook2;



    @BeforeEach
    public void setup(){
        chinook= new Chinook();
        chinook2 = new Chinook("Dylan");
        Player player= new Player("player1");
        Board board = new Board(player,chinook);
        chinook.setBoard(board);
        chinook.setColor(Color.WHITE);
        boardController = new BoardController(board);

    }

    @Test
    public void alertTest(){
        chinook.alertTurn();
    }



}
