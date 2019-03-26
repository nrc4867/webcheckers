package com.webcheckers.appl;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.BoardController;
import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.appl.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Application-Tier")
public class BoardControllerTest {

    private Player player1;
    private Player player2;
    private Board board;
    private BoardController boardController;

    @BeforeEach
    public void setup(){
        player1 = new Player("Player1", Color.RED);
        player2 = new Player("Player2", Color.WHITE);
        board = new Board(player1, player2);
        boardController= new BoardController(board);
    }

    // Test this after makemove and jumpmove
    @Test
    public void movePiecesTest(){
        Move move1 = new Move();
        move1.setMovement(Move.MoveType.REGULAR);
//        move1.setStart();


    }

    @Test
    public void makeMoveTest(){

    }

    @Test
    public void makeJumpTest(){

    }

    @Test
    public void kingTest(){

    }

    @Test
    public void testMovementTest(){

    }

    @Test
    public void canMoveToTest(){

    }

    @Test
    public void canJumptoTest(){

    }

    @Test
    public void allowedDirectionTest(){

    }
    @Test
    public void getMiddlePieceTest(){

    }

    @Test
    public void noMovesLeftTest(){

    }

    @Test
    public void shouldKingTest(){

    }

    @Test
    public void getBoard(){

    }

    @Test
    public void isActivePlayerTest(){

    }

    @Test
    public void resignTest(){

    }
    @Test
    public void toggleTurnTest(){

    }
//    @Test
//    public void movePieceTest() {
//        Piece piece = new Piece(0, 5, player1);
//        boardController.movePiece(piece, 4, 1);
//
//        assertEquals(piece.getRow(), 4);
//        assertEquals(piece.getCol(), 1);
//    }
//
//    @Test
//    public void invalidMoveTest() {
//        Piece piece = new Piece(0, 5, player1);
//        assertFalse(boardController.movePiece(piece, 4, 0));
//        assertFalse(boardController.movePiece(piece, 3, 2));
//        assertFalse(boardController.movePiece(piece, 6, 0));
//    }
//
//    @Test
//    public void testJump() {
//        Piece piece1 = new Piece(0, 5, player1);
//        Piece piece2 = new Piece(1,4,player2);
//
//        board.setPiece(piece2,4,1);
//
//        assertTrue(boardController.canJumpTo(piece1,3,2));
//        boardController.jumpPiece(piece1,3,2);
//
//        assertEquals(piece1.getRow(),3);
//        assertEquals(piece1.getCol(),2);
//    }
//
//    @Test
//    public void InvalidJummp() {
//        Piece piece1 = new Piece(0, 5, player1);
//        Piece piece2 = new Piece(2,4,player2);
//
//        board.setPiece(piece2,4,2);
//
//        assertFalse(boardController.canJumpTo(piece1,3,2));
//        assertFalse(boardController.jumpPiece(piece1,3,2));
//
//        assertEquals(piece1.getRow(),5);
//        assertEquals(piece1.getCol(),0);
//    }
}
