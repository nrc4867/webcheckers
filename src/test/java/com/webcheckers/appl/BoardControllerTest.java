package com.webcheckers.appl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        BoardController b= mock(BoardController.class);
    }

    // Test this after makemove and jumpmove
    @Test
    public void movePiecesTest(){
        Move m1= new Move();
        Position start=new Position();
        start.setRow(5);
        start.setCell(0);
        Position end=new Position();
        end.setRow(4);
        m1.setMovement(Move.MoveType.REGULAR);
        end.setCell(1);
        m1.setStart(start);
        m1.setEnd(end);
        Move m2= new Move();
        m2.setMovement(Move.MoveType.JUMP);
        Position start2=new Position();
        start2.setRow(4);
        start2.setCell(1);
        Position end2=new Position();
        end2.setRow(2);
        end2.setCell(3);
        m2.setStart(start2);
        m2.setEnd(end2);

        Piece p2P= new Piece(3, 2, player2);
        boardController.getBoard().setPiece(p2P,3,2);

        ArrayList<Move> moves = new ArrayList<>();
        moves.add(m1);
        moves.add(m2);

        boardController.getBoard().setPiece(null,2,3);

        assertNull(boardController.getBoard().getPiece(2,3));
        assertNotNull(boardController.getBoard().getPiece(5,0));
        assertNotNull(boardController.getBoard().getPiece(3,2));


        boardController.movePieces(moves);


        assertEquals(boardController.getBoard().getPiece(2,3).getRow(),2);
        assertEquals(boardController.getBoard().getPiece(2,3).getCol(),3);

        assertNull(boardController.getBoard().getPiece(5,0));
        assertNull(boardController.getBoard().getPiece(4,1));
        assertNull(boardController.getBoard().getPiece(3,2));


    }

    @Test
    public void makeMoveTest(){
        Move m1= new Move();
        Position start=new Position();
        start.setRow(5);
        start.setCell(0);
        Position end=new Position();
        end.setRow(4);
        end.setCell(1);
        m1.setStart(start);
        m1.setEnd(end);

        assertNotNull(boardController.getBoard().getPiece(5,0));
        boardController.makeMove(m1);
        assertEquals(boardController.getBoard().getPiece(4,1).getRow(),4);
        assertEquals(boardController.getBoard().getPiece(4,1).getCol(),1);
        assertNull(boardController.getBoard().getPiece(5,0));

    }

    @Test
    public void makeJumpTest(){
        Move m1= new Move();
        Position start=new Position();
        start.setRow(5);
        start.setCell(0);
        Position end = new Position();
        end.setRow(3);
        end.setCell(2);
        m1.setStart(start);
        m1.setEnd(end);
        Piece p2P= new Piece(4, 1, player2);
        boardController.getBoard().setPiece(p2P,4,1);

        assertNotNull(boardController.getBoard().getPiece(5,0));
        assertNotNull(boardController.getBoard().getPiece(4,1));
        assertNull(boardController.getBoard().getPiece(3,2));
        boardController.makeJump(m1);
        assertEquals(boardController.getBoard().getPiece(3,2).getRow(),3);
        assertEquals(boardController.getBoard().getPiece(3,2).getCol(),2);
        assertNull(boardController.getBoard().getPiece(4,1));
        assertNotNull(boardController.getBoard().getPiece(3,2));

    }

    @Test
    public void kingTest(){
        Piece p1= new Piece(0, 5, player1);
        Piece p4 = new Piece(3, 1, player2);

        boardController.getBoard().setPiece(p1,0,5);
        boardController.getBoard().setPiece(p4,3,1);


        assertTrue(boardController.king(p1));
        assertFalse(boardController.shouldKing(p4));
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
        Piece p1= new Piece(0, 5, player1);
        Piece p2 = new Piece(7, 3, player2);
        Piece p3 = new Piece(2, 3, player1);
        Piece p4 = new Piece(3, 1, player2);
        Piece p5 = new Piece(3, 2, player2);

        boardController.getBoard().setPiece(p1,0,5);
        boardController.getBoard().setPiece(p2,7,3);
        boardController.getBoard().setPiece(p3,2,3);
        boardController.getBoard().setPiece(p4,3,1);

        assertTrue(boardController.shouldKing(p1));
        assertTrue(boardController.shouldKing(p2));
        assertFalse(boardController.shouldKing(p3));
        assertFalse(boardController.shouldKing(p4));

        assertThrows(IllegalArgumentException.class,()->{
            boardController.shouldKing(p5);
        });

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
