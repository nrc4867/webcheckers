package com.webcheckers.appl;

import com.webcheckers.model.Board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BoardList {

    private final HashMap<String, Board> boardsCreated = new HashMap<>();

    public Board createBoard(Player red, Player white) {
        Board board = new Board(red, white);
        // Yes im putting the boards name as the key, this is to keep consistency with the project
        // very late into the assignment.
        boardsCreated.put(board.toString(), board);
        return board;
    }

    public Board getBoard(String key) {
        return boardsCreated.getOrDefault(key, null);
    }

    public Object[] getBoardsCreated() {
        return new HashSet<>(boardsCreated.values()).toArray();
    }
}
