package com.webcheckers.model;

import java.util.Objects;

public class Space {

    private Piece piece;

    private final int rowIdx; // Row on the board
    private final int cellIdx; // Column on the board

    // Constructors ===========================================================

    public Space(int row, int col) {
        this(row, col, null);
    }

    /**
     * Construct a new Space. Space must exist within the bounds of
     * the board.
     * @param row Row index of the square.
     * @param col Column index of the square.
     * @param piece Piece to place on the square (may be null).
     */
    public Space(int row, int col, Piece piece) {
        if (row < 0 || row >= Board.getSize()
        ||  col < 0 || col >= Board.getSize()) {
            throw new IllegalArgumentException(
                    "Creating a square out of bounds!"
            );
        }
        this.piece = piece;
        this.rowIdx = row;
        this.cellIdx = col;
    }

    // Accessors ==============================================================

    public int getRowIdx() {return rowIdx;}
    public int getCellIdx() {return cellIdx;}
    public Piece getPiece() {return piece;}
    public void setPiece(Piece p) {piece = p;}

    /**
     * @return True if there's no piece and the square is black.
     */
    public boolean isValid() {
        return piece == null && isBlack();
    }

    /**
     * Check, based on row and cell index, if it's a black square.
     *
     * @return True if this is a black square, false otherwise.
     */
    public boolean isBlack() {
        return rowIdx % 2 != cellIdx % 2;
    }

    // Object =================================================================
    @Override
    public int hashCode() {
        return Objects.hash(rowIdx, cellIdx);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof Space)) {return false;}
        final Space s = (Space) o;
        return s.cellIdx == this.cellIdx && s.rowIdx == this.rowIdx;
    }

    @Override
    public String toString() {

        if (piece == null) {
            return rowIdx + "," + cellIdx + ": empty";
        }

        else {
            return rowIdx + "," + cellIdx + ":" + piece.toString();
        }
    }
}
