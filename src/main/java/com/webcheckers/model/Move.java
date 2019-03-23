package com.webcheckers.model;

import java.io.Serializable;

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

    public void setMovement(MoveType movement) {
        this.movement = movement;
    }

    public MoveType getMovement() {
        return movement;
    }

    @Override
    public String toString() {
        return "Move: " + start.toString() + " " + end.toString() + " " + String.valueOf((movement != null)?movement:"");
    }
}
