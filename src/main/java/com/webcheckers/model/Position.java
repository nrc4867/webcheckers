package com.webcheckers.model;

import java.io.Serializable;

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
}
