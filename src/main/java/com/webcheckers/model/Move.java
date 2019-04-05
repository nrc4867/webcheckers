package com.webcheckers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** Generated by JSON when a move is made. */
public class Move implements Serializable {
    private Position start;
    private Position end;

    private MoveType movement;

    public enum MoveType {
        REGULAR, JUMP
    }

    public Move() {  }

    public Move(Position start, Position end) {
        setStart(start);
        setEnd(end);
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

    public void setStart(Position start) {
        this.start = start;
    }

    public void setEnd(Position end) {
        this.end = end;
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
        return getEndCell() - getStartCell();
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

    /**
     * generate a set of moves around an area
     * @param row the start row
     * @param col the start column
     * @param distance the distance from the start row and column
     * @return a set all the black tiles the distance away from the start
     */
    public static Set<Move> generateMoves(int row, int col, int distance) {
        Set<Move> moves = new HashSet<>();
        for (int i = distance; i >= -distance; i--) {
            for (int j = distance; j >= -distance; j--) {
                if(i % 2 == 0 && j % 2 == 0) {
                    Move move = new Move();
                    move.start = new Position(row, col);
                    move.end = new Position(row + i, col - j);
                    moves.add(move);
                }
            }
        }
        return moves;
    }
}

