package com.webcheckers.appl;

import com.webcheckers.model.Board;

import java.util.HashSet;
import java.util.Set;

public class BoardList {

    private final Set<Board> boardsCreated = new HashSet<>();

    public Board createBoard(Player red, Player white) {
        Board board = new Board(red, white);
        boardsCreated.add(board);
        return board;
    }

    public Object[] getBoardsCreated() {
        return new HashSet<>(boardsCreated).toArray();
    }
}
