package com.webcheckers.ui.view;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The BoardView class is the intermediary between the Board model
 * and its HTTP display. The Row inner class is used in conjunction
 * to iterate through the board's spaces.
 *
 * @author Michael Bianconi
 */
public class BoardView implements Iterable<BoardView.Row> {



    /**
     * This Row inner class is a second iterable that will iterate
     * through its squares in the board. It will automatically update
     * when the board changes (probably. There might be threading
     * issues but who knows).
     */
    public class Row implements Iterable<Space> {

        private final int index;
        private Board board;

        public Row(int index, Board board) {
            if (index < 0 || index >= Board.getSize()) {
                throw new IllegalArgumentException("Row out of bounds!");
            }

            this.board = board;
            this.index = index;
        }

        // ACCESSORS ==========================================================

        public int getIndex() {return index;}

        // OBJECT =============================================================

        @Override
        public int hashCode() {return index;}

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (!(o instanceof Row)) {return false;}
            final Row r = (Row) o;
            return r.index == this.index;
        }

        // ITERATOR ===========================================================

        /**
         * Take the appropriate row from the board and return an
         * iterator to its squares.
         *
         * @return Returns an Iterator to the this Row's squares.
         */
        @Override
        public Iterator<Space> iterator() {
            return Arrays.asList(board.getSpaces()[index]).iterator();
        }
    }




    // ATTRIBUTES =============================================================

    private Board board;
    private List<Row> rows;

    // CONSTRUCTORS ===========================================================

    /**
     * Constructs a new view and creates its rows.
     *
     * @param b Board to view.
     */
    public BoardView(Board b) {
        this.board = b;
        this.rows = new ArrayList<>();
        for (int i = 0; i < Board.getSize(); i++) {
            rows.add(new Row(i, board));
        }
    }

    // ITERABLE ===============================================================

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

}
