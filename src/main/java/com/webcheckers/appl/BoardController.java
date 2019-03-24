package com.webcheckers.appl;

import com.webcheckers.model.*;

import java.util.ArrayList;

//
// Remember, white player is at the top of the board!
//
public class BoardController {

    private final Board board;

    public BoardController(Board b) {
        this.board = b;
    }


    public void movePieces(ArrayList<Move> moves) {
        for (Move move: moves) {
            if(move.getMovement() == Move.MoveType.REGULAR) {
                makeMove(move);
            } else {
                makeJump(move);
            }
        }
    }

    /**
     * Removes the piece from its current position and transfers it.
     * It might be a good idea to call shouldKing().
     * @param p Piece to move.
     * @param destRow Row to move to.
     * @param destCol Col to move to.
     * @return True if successful.
     */
    @Deprecated
    public boolean movePiece(Piece p, int destRow, int destCol)
    {
        System.out.printf("Moving from %d,%d to %d,%d\n",
                p.getCol(),p.getRow(), destCol, destRow);
        if (!canMoveTo(p, destRow, destCol)) {
            return false;
        }

        board.setPiece(null, p.getRow(), p.getCol());
        board.setPiece(p, destRow, destCol);
        p.setCol(destCol);
        p.setRow(destRow);

        System.out.println("Moved to " + p.getCol() +","+ p.getRow());

        return true;
    }

    public boolean makeMove(Move move) {
        Piece moved = board.getPiece(move.getStartRow(), move.getStartCell());
        if(moved == null) return false;
        board.setPiece(null, move.getStartRow(), move.getStartCell());

        moved.setCol(move.getEndCell());
        moved.setRow(move.getEndRow());
        board.setPiece(moved, move.getEndRow(), move.getEndCell());

        return true;
    }

    public boolean makeJump(Move jump) {
        if(!makeMove(jump)) return false;
        Piece middle = getMiddlePiece(jump);
        board.setPiece(null, middle.getRow(), middle.getCol());
        return true;
    }

    /**
     * Jump the piece. Removes it from its current Space, along
     * with the Piece jumped over. Places it in the new Space.
     *
     * @param p Piece jumping.
     * @param destRow Row to jump to.
     * @param destCol Col to jump to.
     * @return True if jumped.
     */
    @Deprecated
    public boolean jumpPiece(Piece p, int destRow, int destCol)
    {
        if (!canJumpTo(p, destRow, destCol)) {
            return false;
        }

        int middleRow = p.getRow() - ((p.getRow() - destRow)/2);
        int middleCol = p.getCol() - ((p.getCol() - destCol)/2);

        board.setPiece(null, p.getRow(), p.getCol());
        board.setPiece(null, middleRow, middleCol);
        board.setPiece(p, destRow, destCol);

        p.setCol(destCol);
        p.setRow(destRow);

        return true;
    }

    /**
     * Kings the Piece, if it can be kinged.
     * @param p Piece to king.
     * @return True if kinged.
     */
    public boolean king(Piece p) {
        if (shouldKing(p)) {
            p.setType(Piece.Type.KING);
            return true;
        }

        return false;
    }

    /**
     * test to see if a move can be made, if the move is valid its movement type is set
     * @param move the move to check
     * @param moves a list of valid moves already slotted to be preformed this turn
     * @return true if the move is valid in the context of moves
     */
    public boolean testMovement(Move move, ArrayList<Move> moves) {
        final boolean testMove = canMoveTo(move, moves);
        final boolean testJump = canJumpTo(move, moves);

        if (testMove || testJump) {
            move.setMovement((testMove)? Move.MoveType.REGULAR: Move.MoveType.JUMP);
            return true;
        }
        return false;
    }

    /**
     * Returns true if the Piece can move (NOT jump!) to the
     * given space.
     */
    @Deprecated
    public boolean canMoveTo(Piece p, int destRow, int destCol) {

        if (p == null) {
            throw new IllegalArgumentException(
                    "Piece doesn't exist!"
            );
        }
        int rowDelta = destRow - p.getRow();
        int colDelta = destCol - p.getCol();

        // Destination must be one of four adjacent corners
        if ((rowDelta != 1 && rowDelta != -1)
        ||  (colDelta != 1 && colDelta != -1)) {
            return false;
        }

        // Piece must be on the board
        if (board.getPiece(p.getRow(), p.getCol()) == null) {
            return false;
        }

        if (!(p.equals(board.getPiece(p.getRow(), p.getCol())))) {
            throw new IllegalArgumentException(
                    "Piece doesn't exist in that location!");
        }

        // Destination must be in bounds
        if (!board.inBounds(destRow, destCol)) {
            return false;
        }

        // Destination must be empty
        if (board.hasPiece(destRow, destCol)) {
            return false;
        }

        // Destination must be in the correct direction
        if (p.getType() != Piece.Type.KING) {

            if (p.getColor() == Color.WHITE && destRow < p.getRow()) {
                return false;
            }

            if (p.getColor() == Color.RED && destRow > p.getRow()) {
                return false;
            }
        }

        return true;
    }

    /**
     * checks to see if a move is valid in the context of other moves performed this turn
     * @param move the move to check
     * @param moves a list of valid moves already slotted to be preformed this turn
     * @return true if the move is valid in the context of moves
     */
    public boolean canMoveTo(Move move, ArrayList<Move> moves) {
        if(moves.size() != 0) return false; // You cannot make more than one regular move in a round

        Piece piece = board.getPiece(move.getStartRow(), move.getStartCell());

        // Piece must be on the board
        if (piece == null) {
            return false;
        }

        // Destination must be one of four adjacent corners
        if (!move.deltaRadius(1)) {
            return false;
        }

        // Destination must be in bounds
        if (!board.inBounds(move.getEndRow(), move.getEndCell())) {
            return false;
        }

        // Destination must be empty
        if (board.hasPiece(move.getEndRow(), move.getEndCell())) {
            return false;
        }



        // Destination must be in the correct direction
        if (!allowedDirection(move, piece)) {
            return false;
        }

        return true;
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
    @Deprecated
    public boolean canJumpTo(Piece p, int destRow, int destCol) {

        int rowDelta = destRow - p.getRow();
        int colDelta = destCol - p.getCol();

        // You must specify a valid place to jump to (2 space radius)
        if ((rowDelta != 2 && rowDelta != -2)
        ||  (colDelta != 2 && colDelta != -2)) {
            return false;
        }

        // The Piece doesn't exist on that board.
        if (!(p.equals(board.getPiece(p.getRow(), p.getCol())))) {
            throw new IllegalArgumentException(
                    "Piece doesn't exist in that location!");
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
        int middleRow = p.getRow() - ((p.getRow() - destRow)/2);
        int middleCol = p.getCol() - ((p.getCol() - destCol)/2);
        Piece enemy = board.getPiece(middleRow, middleCol);

        if (enemy == null) {
            return false;
        }

        if (enemy.getColor() == p.getColor()) {
            return false;
        }

        return true;
    }

    /**
     * checks to see if a jump-move is valid in the context of other moves performed this turn
     * @param move the move to check
     * @param moves a list of valid moves already slotted to be preformed this turn
     * @return true if the move is valid in the context of moves
     */
    public boolean canJumpTo(Move move, ArrayList<Move> moves) {
        // Check to make sure that the move connects with the previous moves
        if(moves.size() != 0 && !Move.ConnectedMoves(moves.get(moves.size() - 1), move)) return false;

        if (!move.deltaRadius(2)) {
            return false;
        }

        // Destination must be in bounds
        if (!board.inBounds(move.getEndRow(), move.getEndCell())) {
            return false;
        }

        // Destination must be empty
        if (board.hasPiece(move.getEndRow(), move.getEndCell())) {
            return false;
        }

        Piece piece;
        if(moves.size() == 0) { // the first jump in the sequence
            piece = board.getPiece(move.getStartRow(), move.getStartCell());
        } else {
            piece = board.getPiece(moves.get(0).getStartRow(), moves.get(0).getStartCell());
        }
        if(piece == null) return false; // the original piece should exist on the board

        // make sure the piece is moving in the right direction
        if (!allowedDirection(move, piece)) {
            return false;
        }

        // The intermediate space must have a piece of the opposite color
        Piece middle = getMiddlePiece(move);
        if (middle == null) {
            return false;
        }
        if (!piece.enemyOf(middle)) {
            return false;
        }

        return true;
    }

    private boolean allowedDirection(Move move, Piece piece) {
        // The Piece can't jump north if it's white and single (#me)
        if (piece.getColor() == Color.WHITE && piece.getType() != Piece.Type.KING
                &&  move.getEndRow() < piece.getRow()) {
            return false;
        }

        // The Piece can't jump south if it's red and single
        if (piece.getColor() == Color.RED && piece.getType() != Piece.Type.KING
                &&  move.getEndRow() > piece.getRow()) {
            return false;
        }

        return true;
    }

    /**
     *  Get the piece in the middle of a jump move
     * @param move the move
     * @return the piece in the middle of the jump, if there is no piece then null
     */
    private Piece getMiddlePiece(Move move) {
        int middleRow = move.getStartRow() - ((move.getStartRow() - move.getEndRow())/2);
        int middleCol = move.getStartCell() - ((move.getStartCell() - move.getEndCell())/2);
        return board.getPiece(middleRow, middleCol);
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

    /**
     * Checks if the piece can move (NOT jump).
     * @param p Piece to check.
     * @return True if it can move.
     */
    public boolean hasAvailableMoves(Piece p) {
        return canMoveTo(p, p.getRow()-1, p.getCol()-1)
                || canMoveTo(p, p.getRow()-1, p.getCol()+1)
                || canMoveTo(p, p.getRow()+1, p.getCol()-1)
                || canMoveTo(p, p.getRow()+1, p.getCol()+1);
    }

    /**
     * Checks the entire Board to see if any of the given player's
     * pieces can jump.
     */
    public boolean hasAvailableJumps(Player p) {

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {

                // If a piece exists on the space, and its owned
                // by the player, check if it can jump
                if (board.hasPiece(row, col)) {
                    Piece piece = board.getPiece(row, col);
                    if (piece.getOwner() == p && hasAvailableJumps(piece)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /**
     * Checks of any of the Player's pieces can move (NOT jump).
     * @param p Player to check.
     * @return True if any of their pieces can move.
     */
    public boolean hasAvailableMoves(Player p) {

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {

                // If a piece exists on the space, and its owned
                // by the player, check if it can jump
                if (board.hasPiece(row, col)) {
                    Piece piece = board.getPiece(row, col);
                    if (piece.getOwner() == p && hasAvailableMoves(piece)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks to see if the Player can move or jump any pieces.
     */
    public boolean noMovesLeft(Player p) {

        return !(hasAvailableJumps(p) || hasAvailableMoves(p));
    }

    /**
     * Checks if the Piece should be kinged. A Piece is kinged when it
     * reaches the opposite side of the board.
     */
    public boolean shouldKing(Piece p) {

        // Piece must exist on the board
        if (!(p.equals(board.getPiece(p.getRow(), p.getCol())))) {
            throw new IllegalArgumentException(
                    "Piece doesn't exist in that location!");
        }

        // Piece cannot be kinged twice
        if (p.getType() == Piece.Type.KING) {
            return false;
        }

        if (p.getColor() == Color.WHITE && p.getRow() == board.getSize()-1) {
            return true;
        }

        if (p.getColor() == Color.RED && p.getRow() == 0) {
            return true;
        }

        return false;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isActivePlayer(Player player) {
        return player.equals(board.getActivePlayer());
    }

    public void resign(Player player) {
        board.setResign(player);
    }

    public void toggleTurn() {
        board.switchActivePlayer();
    }
    

}
