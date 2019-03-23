package com.webcheckers.ui.CheckersPlay.Json;

import java.io.Serializable;

public class Move implements Serializable {
    private Position start;
    private Position end;

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

    @Override
    public String toString() {
        return "Move: " + start.toString() + " " + end.toString();
    }
}
