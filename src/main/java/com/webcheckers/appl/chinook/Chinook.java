package com.webcheckers.appl.chinook;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.model.*;

import java.util.ArrayList;

/**
 * Chinook is the AI program, named after Chinook,
 * the original Checkers AI grandmaster.
 */
public class Chinook extends Player {

    public Chinook() {
        this("Chinook");
    }

    public Chinook(String name) {
        super(name);
    }

    @Override
    public void alertTurn() {

        BoardController control = getBoardController();
        ArrayList<Move> route = makeMove();
        control.movePieces(route);
        control.toggleTurn();

    }

    /**
     * Look at the state of the board and return a list of
     * the most effective moves. If the list is empty, then
     * Chinook has lost.
     * @return Returns a list of moves (may be empty).
     */
    private ArrayList<Move> makeMove() {

        // Holds the most effective moves
        ScoredMoveList overallBest = new ScoredMoveList();

        // For each piece owned by Chinook
        for (Piece p : getBoard().getPlayerPieces(this)) {

            // Find that piece's most effective moves
            ScoredMoveList pieceBest = bestRoute(p);

            // If better than the previous best, replace it
            if (pieceBest.getScore() > overallBest.getScore()) {
                overallBest = pieceBest;
            }
        }

        // Return the best moves
        return overallBest.getMoves();

    }


    /**
     * Given a piece, find the best moves it can make this turn.
     * @param p Piece to look at.
     * @return Returns a ScoredMoveList of the piece's best route.
     */
    private ScoredMoveList bestRoute(Piece p) {

        return bestRoute(p, new ScoredMoveList());
    }


    /**
     * Given a piece and previous moves made by it, find the best
     * future moves.
     *
     * @param p Piece to move
     * @param prev Previous moves made this turn by this piece
     * @return
     */
    private ScoredMoveList bestRoute(Piece p, ScoredMoveList prev) {

        ScoredMoveList best = new ScoredMoveList();

        // All 8 possible moves
        ArrayList<Move> possible = new ArrayList<>();
        Position START = p.getPos();
        int sRow = START.getRow();
        int sCol = START.getCell();
        Move NE_REG = new Move(START, new Position(sRow-1, sCol+1));
        Move SE_REG = new Move(START, new Position(sRow+1, sCol+1));
        Move SW_REG = new Move(START, new Position(sRow+1, sCol-1));
        Move NW_REG = new Move(START, new Position(sRow-1, sCol-1));
        Move NE_JMP = new Move(START, new Position(sRow-2, sCol+2));
        Move SE_JMP = new Move(START, new Position(sRow+2, sCol+2));
        Move SW_JMP = new Move(START, new Position(sRow+2, sCol-2));
        Move NW_JMP = new Move(START, new Position(sRow-2, sCol-2));
        possible.add(NE_REG);
        possible.add(SE_REG);
        possible.add(SW_REG);
        possible.add(NW_REG);
        possible.add(NE_JMP);
        possible.add(SE_JMP);
        possible.add(SW_JMP);
        possible.add(NW_JMP);


        // Check the score of each move
        BoardController control = getBoardController();
        for (Move m : possible) {

            // Move is valid
            if (control.testMovement(m, prev.getMoves())) {

                // Create a new move list with this move
                ScoredMoveList temp = new ScoredMoveList(prev.getMoves());
                temp.addMove(m);

                // Recursively find the best route, starting with this move
                ScoredMoveList route = bestRoute(p, temp);

                // If better than the best, replace it
                if (route.getScore() > best.getScore()) {
                    best = route;
                }
            }
        }

        return best;
    }

}