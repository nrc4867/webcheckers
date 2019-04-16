package com.webcheckers.appl;

import com.webcheckers.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * BoardList Test
 * @author Abhaya Tamrakar
 */
@Tag("Application-Tier")
public class BoardListTest {
    private BoardList boardList;
    private HashMap<String, Board> boardsCreated;
    private Player p1;
    private Player p2;
    private Board b1;

    @BeforeEach
    public void setup(){
        boardList = new BoardList();
        boardsCreated = new HashMap<>();
        p1 = new Player("Red");
        p2 = new Player("White");

        b1=boardList.createBoard(p1,p2);
    }

    @Test
    public void createBoardTest(){
        assertEquals(b1.getRedPlayer().getName(),"Red");
        assertEquals(b1.getWhitePlayer().getName(),"White");
    }

    @Test
    public void addons(){
        assertNull(boardList.getBoard("a"));
        assertNotNull(boardList.getBoardsCreated());
    }
}
