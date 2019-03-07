package com.webcheckers.controller;

import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.model.GameException;
import com.webcheckers.model.Piece;

import java.util.List;

//
// Remember, white player is at the top of the board!
//
public class BoardController {

    private Board board;

    public BoardController(Board b) {
        this.board = b;
    }

    /**
     * Returns true if the Piece can move (NOT jump!) to the
     * given space.
     *
     * TODO
     */
    private boolean canMoveTo(Piece p, int destRow, int destCol) {

        return false;

    }

    /**
     * Returns true if the Piece can jump to the destination.
     * Requirements checked:
     *
     *      Piece p is on the Board
     *      Destination is in bounds, 2 spaces diagonally away from p
     *      P is either a KING or of the correct color to jump
     *      Destination is empty
     *      The space between P and the Destination has an enemy piece.
     *
     */
    private boolean canJumpTo(Piece p, int destRow, int destCol) {

        int rowDelta = destRow - p.getRow();
        int colDelta = destCol - p.getCol();

        // You must specify a valid place to jump to (2 space radius)
        if ((rowDelta != 2 && rowDelta != -2)
        ||  (colDelta != 2 && colDelta != -2)) {
            throw new IllegalArgumentException("Deltas must be 2 or -2!");
        }

        // The Piece doesn't exist on that board.
        if (p != board.getPiece(p.getRow(), p.getCol())) {
            throw new GameException("Piece doesn't exist in that location!");
        }

        // The Piece can't jump north if it's white and single (#me)
        if (p.getColor() == Color.WHITE && p.getType() != Piece.Type.KING
        &&  destRow < p.getRow()) {
            return false;
        }

        // The Piece can't jump south if it's red and single
        if (p.getColor() == Color.RED && p.getType() != Piece.Type.KING
        &&  destRow > p.getRow()) {
            return false;
        }

        // The destination must be in bounds
        if (!board.inBounds(destRow, destCol)) {
            return false;
        }

        // The destination must be empty
        if (board.hasPiece(destRow, destCol)) {
            return false;
        }

        // The intermediate space must have a piece of the opposite color
        int rowInter = p.getRow() + (rowDelta/2);
        int colInter = p.getCol() + (colDelta/2);
        Piece enemy = board.getPiece(rowInter, colInter);

        if (enemy == null) {
            return false;
        }

        if (enemy.getColor() == p.getColor()) {
            return false;
        }

        return true;
    }

    /**
     * Checks the piece to see if it can jump <i>over</i>
     * an enemy piece. White pieces jump south, red pieces
     * jump north. Kings can jump anywhere.
     */
    public boolean hasAvailableJumps(Piece p) {
        return canJumpTo(p, p.getRow()-2, p.getCol()-2)
            || canJumpTo(p, p.getRow()-2, p.getCol()+2)
            || canJumpTo(p, p.getRow()+2, p.getCol()-2)
            || canJumpTo(p, p.getRow()+2, p.getCol()+2);
    }
}
