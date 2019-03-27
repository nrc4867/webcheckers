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
        assertTrue(boardController.canMoveTo(m1,new ArrayList<Move>()));
        assertNotNull(boardController.getBoard().getPiece(5,0));
        boardController.makeMove(m1);
        assertEquals(boardController.getBoard().getPiece(4,1).getRow(),4);
        assertEquals(boardController.getBoard().getPiece(4,1).getCol(),1);
        assertNull(boardController.getBoard().getPiece(5,0));

        Move m2= new Move();
        Position start2=new Position();
        start2.setRow(5);
        start2.setCell(0);
        Position end2=new Position();
        end2.setRow(3);
        end2.setCell(2);
        m2.setStart(start2);
        m2.setEnd(end2);

        boardController.getBoard().setPiece(new Piece(4,5,player2),4,5);
        Move m3 = new Move();
        Position start3=new Position();
        start3.setRow(5);
        start3.setCell(4);
        Position end3=new Position();
        end3.setRow(3);
        end3.setCell(6);
        m3.setStart(start3);
        m3.setEnd(end3);
        assertFalse(boardController.testMovement(m2,new ArrayList<>()));
        assertTrue(boardController.testMovement(m3,new ArrayList<>()));



    }

    @Test
    public void makeJumpAndMovementTest(){
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
        assertTrue(boardController.canJumpTo(m1, new ArrayList<>()));
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
        assertFalse(boardController.king(p4));
    }


    @Test
    public void canMoveAndJumpToTest(){
        Piece piece1 = new Piece(5,4,player1);
        boardController.getBoard().setPiece(piece1,5,4);
        Move outofBound = new Move();
        Position start=new Position();
        start.setRow(0);
        start.setCell(5);
        Position end = new Position();
        end.setRow(-1);
        end.setCell(10);
        outofBound.setStart(start);
        outofBound.setEnd(end);
        boardController.getBoard().setPiece(null ,5,2);
        Move nullPiece = new Move();
        Position start2 = new Position();
        start2.setRow(5);
        start2.setCell(2);
        nullPiece.setStart(start2);
        boardController.getBoard().setPiece(piece1,5,4);

        Move delta = new Move();
        Position start3 = new Position();
        Position end3 =  new Position();
        start3.setRow(5);
        start3.setCell(6);
        end3.setRow(8);
        end3.setCell(1);
        delta.setStart(start3);
        delta.setEnd(end3);

        Move piece = new Move();
        Position start4 = new Position();
        Position end4 =  new Position();
        start4.setRow(6);
        start4.setCell(3);
        end4.setRow(5);
        end4.setCell(4);
        piece.setStart(start4);
        piece.setEnd(end4);

        Move dir = new Move();
        Position start5 = new Position();
        Position end5 =  new Position();
        start5.setRow(6);
        start5.setCell(3);
        end5.setRow(7);
        end5.setCell(2);
        dir.setStart(start5);
        dir.setEnd(end5);


        ArrayList<Move> moves= new ArrayList<>();
        ArrayList<Move> moves1= new ArrayList<>();
        moves1.add(outofBound);
        boardController.getBoard().setPiece(null,7,2);
        assertFalse(boardController.canMoveTo(outofBound,moves1));
        assertFalse(boardController.canMoveTo(nullPiece,moves));
        assertFalse(boardController.canMoveTo(delta,moves));
        assertFalse(boardController.canMoveTo(outofBound,moves));
        assertFalse(boardController.canMoveTo(piece,moves));
        assertFalse(boardController.canMoveTo(dir,moves));


        // canJump

        Move outofBound2 = new Move();
        Position startb2=new Position();
        startb2.setRow(0);
        startb2.setCell(5);
        Position endb2 = new Position();
        endb2.setRow(-2);
        endb2.setCell(9);
        outofBound2.setStart(startb2);
        outofBound2.setEnd(endb2);

        Move dir2 = new Move();
        Position startb5 = new Position();
        Position endb5 =  new Position();
        startb5.setRow(2);
        startb5.setCell(1);
        endb5.setRow(0);
        endb5.setCell(3);
        dir2.setStart(startb5);
        dir2.setEnd(endb5);
        boardController.getBoard().setPiece(null,0,3);
        assertFalse(boardController.canJumpTo(dir2,moves));
        assertFalse(boardController.canJumpTo(outofBound2,moves1));
        assertFalse(boardController.canJumpTo(outofBound2,moves));
        assertFalse(boardController.canJumpTo(delta,moves));
//        assertFalse(boardController.canJumpTo(nullPiece,moves));

        Move m= new Move();
        Position p = new Position();
        p.setCell(2);
        p.setRow(3);
        m.setEnd(p);
        Position pp = new Position();
        pp.setRow(5);
        pp.setCell(4);
        m.setStart(pp);
        Piece hasPieceJump = new Piece(3,2,player2);
        boardController.getBoard().setPiece(hasPieceJump,3,2);

        assertFalse(boardController.canJumpTo(m,moves));
        Move m2 = new Move();
        m2.setStart(pp);
        Position pp2= new Position();
        pp2.setRow(3);
        pp2.setCell(6);
        m2.setEnd(pp2);

        Move m3= new Move();
        m3.setStart(pp2);
        boardController.getBoard().setPiece(null,1,4);
        Position pp4= new Position();
        pp4.setRow(1);
        pp4.setCell(4);
        m3.setEnd(pp4);
        assertFalse(boardController.canJumpTo(m2,moves));
        boardController.getBoard().setPiece(new Piece(3,6,player1),3,6);
        boardController.getBoard().setPiece(null,1,4);
        boardController.getBoard().setPiece(new Piece(2,5,player1),2,5);

        assertFalse(boardController.canJumpTo(m3,moves));


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
        Piece p6 =  new Piece(0,1,player1, Piece.Type.KING);

        boardController.getBoard().setPiece(p1,0,5);
        boardController.getBoard().setPiece(p2,7,3);
        boardController.getBoard().setPiece(p3,2,3);
        boardController.getBoard().setPiece(p4,3,1);
        boardController.getBoard().setPiece(p6,0,1);

        assertTrue(boardController.shouldKing(p1));
        assertTrue(boardController.shouldKing(p2));
        assertFalse(boardController.shouldKing(p3));
        assertFalse(boardController.shouldKing(p4));
        assertFalse(boardController.shouldKing(p6));

        assertThrows(IllegalArgumentException.class,()->{
            boardController.shouldKing(p5);
        });

    }


    @Test
    public void isActivePlayerTest(){
        Player p1 = boardController.getBoard().getActivePlayer();
        assertTrue(boardController.isActivePlayer(p1));

    }

    @Test
    public void resignTest(){
        Player p1 = boardController.getBoard().getActivePlayer();
        boardController.resign(p1);
        assertEquals(boardController.getBoard().getResign(),p1);
    }
    @Test
    public void toggleTurnTest(){
        Player p1 = boardController.getBoard().getActivePlayer();
        boardController.toggleTurn();

        assertNotEquals(boardController.getBoard().getActivePlayer(),p1);

    }

}
