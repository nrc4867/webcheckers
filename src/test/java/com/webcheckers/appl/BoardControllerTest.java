package com.webcheckers.appl;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.BoardController;
import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.model.Piece;
import com.webcheckers.appl.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Application-Tier")
public class BoardControllerTest {

    private Player player1 = new Player("Player1", Color.RED);
    private Player player2 = new Player("Player2", Color.WHITE);
    private Board board = new Board(player1, player2);
    private BoardController boardController = new BoardController(board);

    @Test
    public void movePieceTest() {
        Piece piece = new Piece(0, 5, player1);
        boardController.movePiece(piece, 4, 1);

        assertEquals(piece.getRow(), 4);
        assertEquals(piece.getCol(), 1);
    }

    @Test
    public void invalidMoveTest() {
        Piece piece = new Piece(0, 5, player1);
        assertFalse(boardController.movePiece(piece, 4, 0));
        assertFalse(boardController.movePiece(piece, 3, 2));
        assertFalse(boardController.movePiece(piece, 6, 0));
    }

    @Test
    public void testJump() {
        Piece piece1 = new Piece(0, 5, player1);
        Piece piece2 = new Piece(1,4,player2);

        board.setPiece(piece2,4,1);

        assertTrue(boardController.canJumpTo(piece1,3,2));
        boardController.jumpPiece(piece1,3,2);

        assertEquals(piece1.getRow(),3);
        assertEquals(piece1.getCol(),2);
    }

    @Test
    public void InvalidJummp() {
        Piece piece1 = new Piece(0, 5, player1);
        Piece piece2 = new Piece(2,4,player2);

        board.setPiece(piece2,4,2);

        assertFalse(boardController.canJumpTo(piece1,3,2));
        assertFalse(boardController.jumpPiece(piece1,3,2));

        assertEquals(piece1.getRow(),5);
        assertEquals(piece1.getCol(),0);
    }
}
