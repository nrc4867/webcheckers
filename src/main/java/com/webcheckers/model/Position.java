package com.webcheckers.model;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable{
    private int row;
    private int cell;

    public int getCell() {
        return cell;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + cell + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position)) return false;
        Position pos = (Position)obj;
        return pos.cell == cell && pos.row == row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, cell);
    }
}
