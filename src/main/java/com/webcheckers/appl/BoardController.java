package com.webcheckers.appl;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.Set;

//
// Remember, white player is at the top of the board!
//
public class BoardController {

    private final Board board;

    public BoardController(Board b) {
        this.board = b;
    }

    /**
     * Move pieces on the board
     *
     * @param moves a set of moves in order that they should be made, the moves have already been tested as valid
     */
    public void movePieces(ArrayList<Move> moves) {
        for (Move move : moves) {
            if (move.getMovement() == Move.MoveType.REGULAR) {
                makeMove(move);
            } else {
                makeJump(move);
            }
        }
    }

    /**
     * Move a piece on the board, the move has previously been tested as valid
     *
     * @param move the move to make
     * @return the move was completed
     */
    public boolean makeMove(Move move) {
        Piece moved = board.getPiece(move.getStart());
        if (moved == null) return false;
        board.setPiece(null, move.getStart());

        moved.setCol(move.getEndCell());
        moved.setRow(move.getEndRow());
        king(moved);
        board.setPiece(moved, move.getEnd());

        return true;
    }

    /**
     * make a jump move by removing a piece on the board while moving,
     * the move has previously been tested as valid
     *
     * @param jump the jump to make
     * @return the jump was completed
     */
    public boolean makeJump(Move jump) {
        if (!makeMove(jump)) return false;
        Piece middle = board.getPiece(jump.getJumpedPosition());
        board.setPiece(null, middle.getPos());
        return true;
    }

    /**
     * Kings the Piece, if it can be kinged.
     *
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
     *
     * @param move  the move to check
     * @param moves a list of valid moves already slotted to be preformed this turn
     * @return true if the move is valid in the context of moves
     */
    public boolean testMovement(Move move, ArrayList<Move> moves) {
        boolean testMove = false;
        if (!mustJumpThisTurn(moves)) {
            testMove = canMoveTo(move, moves);
        }

        boolean testJump = canJumpTo(move, moves);

        if (testMove || testJump) {
            move.setMovement((testMove) ? Move.MoveType.REGULAR : Move.MoveType.JUMP);
            return true;
        }
        return false;
    }

    /**
     * check to see if there is a valid jump for this turn
     *
     * @param moves the moves made this turn
     * @return true if there is a valid jump
     */
    public boolean mustJumpThisTurn(ArrayList<Move> moves) {
        Piece piece = board.getPiece(moves);
        Player activePlayer = board.getActivePlayer();

        if (piece != null) {
            return mustJumpThisTurn(piece, moves);
        }

        for (Space[] rowspace : board.getSpaces()) {
            for (Space space : rowspace) {
                piece = space.getPiece();
                if (piece != null && activePlayer.ownsPiece(piece))
                    if (mustJumpThisTurn(piece, moves)) return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if a player must jump this turn
     *
     * @param piece the piece multi-jumping
     * @param moves the moves already made
     * @return true if there are available multi-jumps
     */
    private boolean mustJumpThisTurn(Piece piece, ArrayList<Move> moves) {

        final Position current = piece.getCurrentPosition(moves);

        Set<Move> possibleMoves = Move.generateMoves(current, 2);
        for (Move move : possibleMoves) {
            if (canJumpTo(move, moves)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks to see if a move is valid in the context of other moves performed this turn
     *
     * @param move  the move to check
     * @param moves a list of valid moves already slotted to be preformed this turn
     * @return true if the move is valid in the context of moves
     */
    public boolean canMoveTo(Move move, ArrayList<Move> moves) {
        if (moves.size() != 0) return false; // You cannot make more than one regular move in a round

        Piece piece = board.getPiece(move, moves);

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
        if (!allowedDirection(piece, move, moves)) {
            return false;
        }

        return true;
    }

    /**
     * checks to see if a jump-move is valid in the context of other moves performed this turn
     *
     * @param move  the move to check
     * @param moves a list of valid moves already slotted to be preformed this turn
     * @return true if the move is valid in the context of moves
     */
    public boolean canJumpTo(Move move, ArrayList<Move> moves) {

        // Check to make sure that the move connects with the previous moves
        if (!moves.isEmpty() && !Move.ConnectedMoves(moves.get(moves.size() - 1), move)) return false;

        // Cannot jump if the previous move was a not a jump
        if (!moves.isEmpty() && moves.get(moves.size() - 1).getMovement() == Move.MoveType.REGULAR) return false;

        // Must jump diagonally 2 spaces
        if (!move.deltaRadius(2)) {
            return false;
        }

        // Destination must be in bounds
        if (!board.inBounds(move.getEndRow(), move.getEndCell())) {
            return false;
        }

        // Do not allow the piece to jump over the same tile twice
        for (Move m : moves) {
            if (move.sameJumpedPosition(m)) {
                return false;
            }
        }

        // Destination must be empty, but ignore if its a previously
        // jumped-from position. The previous check will prevent
        // backtracking
        if (board.hasPiece(move.getEnd())
                && !move.reenters(moves)) {
            return false;
        }

        Piece piece = board.getPiece(move, moves);
        if (piece == null) {
            return false; // the original piece should exist on the board
        }

        // make sure the piece is moving in the right direction
        if (!allowedDirection(piece, move, moves)) {
            return false;
        }

        // The intermediate space must have a piece of the opposite color
        Piece middle = board.getMiddlePiece(move);
        if (middle == null) {
            return false;
        }
        if (!piece.enemyOf(middle)) {
            return false;
        }

        return true;
    }

    /**
     * Check to make sure a piece can move in a direction
     *
     * @param move  the movement
     * @param piece the piece
     * @return the move is valid for the piece
     */
    private boolean allowedDirection(Piece piece, Move move, ArrayList<Move> moves) {
        // The Piece can't jump north if it's white and single (#me)
        Position current = piece.getCurrentPosition(moves);

        if (piece.getColor() == Color.WHITE && !piece.isKing()
                && move.getEndRow() < current.getRow()) {
            return false;
        }

        // The Piece can't jump south if it's red and single
        if (piece.getColor() == Color.RED && !piece.isKing()
                && move.getEndRow() > current.getRow()) {
            return false;
        }

        return true;
    }

    /**
     * See if a piece should be kinged based on a move
     *
     * @param move the move to check
     * @return true if a piece is kinged
     */
    public boolean shouldKing(Move move) {
        final ArrayList<Move> moves = new ArrayList<>();
        moves.add(move);
        return shouldKing(moves);
    }

    /**
     * Check if a piece should be kinged based on a series of moves
     *
     * @param moves the moves to make
     * @return true if the piece is kinged
     */
    public boolean shouldKing(ArrayList<Move> moves) {
        Piece piece = board.getPiece(moves);

        Move lastMove = moves.get(moves.size() - 1);

        if (piece.isKing()) return false;
        if (piece.getColor() == Color.WHITE && lastMove.getEndRow() == Board.getSize() - 1) return true;
        if (piece.getColor() == Color.RED && lastMove.getEndRow() == 0) return true;
        return false;
    }

    /**
     * Checks if the Piece should be kinged. A Piece is kinged when it
     * reaches the opposite side of the board.
     */
    public boolean shouldKing(Piece p) {

        // Piece cannot be kinged twice
        if (p.isKing()) return false;

        if (p.getColor() == Color.WHITE && p.getRow() == Board.getSize() - 1) {
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

    /**
     * Check the active player
     *
     * @param player the player to check
     * @return true if player is active
     */
    @Deprecated
    public boolean isActivePlayer(Player player) {
        return player.equals(board.getActivePlayer());
    }

    /**
     * Remove a player from play
     *
     * @param player the player to resign
     */
    public void resign(Player player) {
        board.setPlayMode(ModeOptions.resign(player));
    }


    /**
     * Toggle the active player
     */
    public void toggleTurn() {
        board.switchActivePlayer();
    }


    /**
     * Checks whether the active player has any moves remaining.
     * If the active player has no moves, then they have lost.
     * 
     * Only call at the beginning of the turn!
     *
     * @return Returns INPROGRESS, RED_WON, WHITE_WON
     */
    public GameState getGameState() {

        // Iterate through every space on the board
        for (Space[] rowspace : board.getSpaces()) {
            for (Space space : rowspace) {
                Piece piece = space.getPiece();

                // Space does not contain a piece
                if (piece == null) continue;

                // Piece not owned by active player, ignore
                if (!board.getActivePlayer().ownsPiece(piece)) continue;

                // Piece can jump
                if (mustJumpThisTurn(piece, new ArrayList<>()))
                    return GameState.INPROGRESS;

                // Piece can move
                Set<Move> possible = piece.getPossibleMoves();

                for (Move m : possible) {
                    if (canMoveTo(m, new ArrayList<>())) {
                        return GameState.INPROGRESS;
                    }
                }
            }
        }

        // No possible moves!
        if (board.getActivePlayer().equals(board.getRedPlayer()))
            return GameState.WHITE_WON;

        else return GameState.RED_WON;
    }
}
