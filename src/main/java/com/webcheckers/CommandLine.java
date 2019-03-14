package com.webcheckers;

import com.webcheckers.controller.BoardController;
import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;

import java.util.Scanner;

public class CommandLine {

    public static void main(String[] args) {
        Player red = new Player("red");
        Player white = new Player("white");
        Board board = new Board(red, white);
        BoardController control = new BoardController(board);

        System.out.println("Board test!");
        System.out.println("Usage: [start row,start col] [end row,end col]");

        while (true) {

            if (control.noMovesLeft(board.getActivePlayer())) {
                System.out.println(board.getActivePlayer() + " has lost!");
                return;
            }

            // Prompt
            System.out.print(board);
            System.out.print(board.getActivePlayer()+"> ");
            System.out.flush();

            // Read input
            Scanner scanner = new Scanner(System.in);
            String[] in = scanner.nextLine().split("[ ,]");

            // Bad input length
            if (in.length != 4) {
                System.out.println("Bad input!");
                continue;
            }

            int sRow = Integer.valueOf(in[0]);
            int sCol = Integer.valueOf(in[1]);
            int eRow = Integer.valueOf(in[2]);
            int eCol = Integer.valueOf(in[3]);

            Piece selected = board.getPiece(sRow, sCol);

            // Bad starting position
            if (selected == null) {
                System.out.println("Bad piece!");
                continue;
            }

            // Try to move piece
            if (control.movePiece(selected, eRow, eCol)) {

                if (control.shouldKing(selected)) {
                    control.king(selected);
                }

                board.switchActivePlayer();
                continue;
            }

            // Try to jump piece
            if (control.jumpPiece(selected, eRow, eCol)) {

                // When kinged, end the turn
                if (control.shouldKing(selected)) {
                    control.king(selected);
                    board.switchActivePlayer();
                    continue;
                }

                // Multi jump
                if (control.hasAvailableJumps(board.getActivePlayer())) {
                    continue;
                }

                // No more jumps
                else
                {
                    board.switchActivePlayer();
                    continue;
                }
            }

            // Can't move or jump
            System.out.println("Bad destination!");
        }
    }


}
