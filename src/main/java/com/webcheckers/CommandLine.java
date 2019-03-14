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
        boolean jumping = false;
        Piece selected = null;

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
            if (jumping) System.out.print("(jump to) ");
            System.out.flush();

            // Read input
            Scanner scanner = new Scanner(System.in);
            String[] in = scanner.nextLine().split("[ ,]");

            int sRow;
            int sCol;
            int eRow;
            int eCol;

            if (!jumping) {
                // Bad input length
                if (in.length != 4) {
                    System.out.println("Bad input!");
                    continue;
                }

                sRow = Integer.valueOf(in[0]);
                sCol = Integer.valueOf(in[1]);
                eRow = Integer.valueOf(in[2]);
                eCol = Integer.valueOf(in[3]);

                selected = board.getPiece(sRow, sCol);
            }

            else {
                if (in.length != 2) {
                    System.out.println("Bad input!");
                    continue;
                }

                eRow = Integer.valueOf(in[0]);
                eCol = Integer.valueOf(in[1]);
            }

            // Bad starting position
            if (selected == null
            || selected.getColor() != board.getActivePlayer().getColor()) {
                System.out.println("Bad piece!");
                continue;
            }

            if (jumping) {

                if (!control.jumpPiece(selected, eRow, eCol)) {
                    System.out.println("You must jump!");
                    continue;
                } else {
                    // When kinged, end the turn
                    if (control.shouldKing(selected)) {
                        control.king(selected);
                        jumping = false;
                        board.switchActivePlayer();
                        continue;
                    }

                    // Multi jump
                    if (control.hasAvailableJumps(selected)) {
                        jumping = true;
                        continue;
                    }

                    // No more jumps
                    else {
                        jumping = false;
                        board.switchActivePlayer();
                        continue;
                    }
                }
            }

                // Try to move
            else if (control.movePiece(selected, eRow, eCol)) {

                if (control.shouldKing(selected)) {
                    control.king(selected);
                }

                jumping = false;
                board.switchActivePlayer();
                continue;
            }

            // Try to jump piece
            else if (control.jumpPiece(selected, eRow, eCol)) {

                // When kinged, end the turn
                if (control.shouldKing(selected)) {
                    control.king(selected);
                    jumping = false;
                    board.switchActivePlayer();
                    continue;
                }

                // Multi jump
                if (control.hasAvailableJumps(selected)) {
                    jumping = true;
                    continue;
                }

                // No more jumps
                else
                {
                    jumping = false;
                    board.switchActivePlayer();
                    continue;
                }
            }


            // Can't move or jump
            System.out.println("Bad destination!");
        }
    }
}
