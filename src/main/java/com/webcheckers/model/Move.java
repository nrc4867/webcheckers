package com.webcheckers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Move implements Serializable {
    private Position start;
    private Position end;

    private MoveType movement;

    public enum MoveType {
        REGULAR, JUMP
    }

    public int getStartRow() {
        return start.getRow();
    }

    public int getEndRow() {
        return end.getRow();
    }

    public int getStartCell() {
        return start.getCell();
    }

    public int getEndCell() {
        return end.getCell();
    }

    /**
     * @return the change in row position
     */
    public int rowDelta() {
        return getEndRow() - getStartRow();
    }

    /**
     * @return the change in column position
     */
    public int cellDelta() {
        return getEndCell() - getEndCell();
    }

    public boolean deltaRadius(int radius) {
        return Math.abs(rowDelta()) == radius || Math.abs(cellDelta()) == radius;
    }

    public void setMovement(MoveType movement) {
        this.movement = movement;
    }

    public MoveType getMovement() {
        return movement;
    }

    /**
     * Checks to see if the end of the first move is the start of the second move
     * @param first the start of the sequence
     * @param second the end of the sequence
     * @return true if the second
     */
    public static boolean ConnectedMoves(Move first, Move second) {
        return first.end.equals(second.start);
    }

    /**
     * Checks to see if the moves connect to each other
     * @param moves moves in the order that they should be preformed
     * @return true if the list is an ordered set of moves
     */
    public static boolean ConnectedMoves(ArrayList<Move> moves) {
        if (moves.size() == 0 || moves.size() == 1) return true;
        for (int i = 0; i < moves.size() - 1; i++) {
            if(!ConnectedMoves(moves.get(i), moves.get(i + 1))) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Move: " + start.toString() + " " + end.toString() + " " + String.valueOf((movement != null)?movement:"");
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Move)) return false;
        Move mov = (Move) obj;
        return mov.start.equals(start) && mov.end.equals(end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
