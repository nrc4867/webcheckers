package com.webcheckers.util;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;

import java.io.*;
import java.util.Scanner;

/**
 * A command prompt for quickly controlling live boards
 * @author Nicholas Chieppa
 */
public class WebSeverCommandPrompt extends Thread {
    /**
     * The PlayerLobby to act upon
     */
    private final PlayerLobby lobby;

    /**
     * The load command
     * usage: load save-name red-player white-player [location: ./boards/]
     */
    public static final String LOAD = "load";
    private static final String LOAD_MESSAGE = "FILE LOADED";

    /**
     * create a board from a comma separated value file
     * usage: csv save-name red-player white-player [location: ./boards/]
     */
    public static final String LOADCSV = "csv";
    // inputs on the csv-file, all other inputs ignored
    private static final String RED = "r";
    private static final String WHITE = "w";
    private static final String RED_KING = "rk";
    private static final String WHITE_KING = "wk";

    /**
     * The save command
     * usage: save player save-name [location: ./boards/]
     */
    public static final String SAVE = "save";
    private static final String SAVE_MESSAGE = "FILE SAVED";

    /**
     * Toggle command, changes the turn of the players board
     * usage: toggle player
     */
    public static final String TOGGLE_TURN = "toggle";
    public static final String TOGGLE_MESSAGE = "TOGGLED PLAYER";

    public static final String INVALID = "Invalid Command";
    public static final String INVALID_ARGS = "Incorrect arguments";
    public static final String PLAYER_ERROR = "Player not available";
    public static final String BOARD_ERROR = "Board not available";

    /**
     * The default save location
     */
    private static final String defaultSave = System.getProperty("user.dir") + "/boards/";
    private final Scanner scanner = new Scanner(System.in);

    public WebSeverCommandPrompt(PlayerLobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void run() {
        while (true) {
            try {
                readInput();
                sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read input from the scanner
     */
    private void readInput() {
        if(scanner.hasNextLine()) {
            String[] input = scanner.nextLine().split(" ");
            switch (input[0]) {
                case LOAD:
                    load(input);
                    break;
                case LOADCSV:
                    csv(input);
                    break;
                case SAVE:
                    save(input);
                    break;
                case TOGGLE_TURN:
                    toggle(input);
                    break;
                default:
                    System.out.println(INVALID);
                    break;
            }
        }
    }

    /**
     * The toggle command
     * usage: toggle player
     */
    private void toggle(String... args) {
        if(args.length < 2) {
            System.out.println(INVALID_ARGS);
            return;
        }

        Player player = lobby.getPlayer(args[1]);
        if(player == null) {
            System.out.println(PLAYER_ERROR);
            return;
        }

        Board board = player.getBoard();
        if(board == null) {
            System.out.println(BOARD_ERROR);
            return;
        }

        board.switchActivePlayer();
        System.out.println(TOGGLE_MESSAGE);
    }

    /**
     * The load command
     * usage: load save-name red-player white-player [location: ./boards/]
     */
    private void load(String... args) {
        if(args.length < 4) {
            System.out.println(INVALID_ARGS);
            return;
        }

        Player redPlayer = lobby.getPlayer(args[2]);
        Player whitePlayer = lobby.getPlayer(args[3]);
        if(redPlayer == null || whitePlayer == null) {
            System.out.println(PLAYER_ERROR);
            return;
        }

        String basePath = (args.length >= 5)?args[4]:defaultSave;
        if(!(basePath.endsWith("/") || basePath.endsWith("\\"))) basePath += "/";

        Board board = new Board(redPlayer, whitePlayer, false);

        Space[][] spaces = (Space[][]) loadObject(basePath + args[1]);
        if(spaces == null) return;
        for(int i = 0; i < Board.getSize(); i++) {
            for (int j = 0; j < Board.getSize(); j++) {
                setSpace(redPlayer, whitePlayer, spaces, i, j);
            }
        }

        board.setSpaces(spaces);
        redPlayer.setBoardController(new BoardController(board));
        whitePlayer.setBoardController(new BoardController(board));
        System.out.println(LOAD_MESSAGE);
    }

    /**
     * create a board from a comma separated value file
     * usage: csv save-name red-player white-player [location: ./boards/]
     */
    private void csv(String... args) {
        if(args.length < 4) {
            System.out.println(INVALID_ARGS);
            return;
        }

        Player redPlayer = lobby.getPlayer(args[2]);
        Player whitePlayer = lobby.getPlayer(args[3]);
        if(redPlayer == null || whitePlayer == null) {
            System.out.println(PLAYER_ERROR);
            return;
        }

        String basePath = ((args.length >= 5)?args[4]:defaultSave).replace("\\", "/");
        if(!(basePath.endsWith("/") || basePath.endsWith("\\"))) basePath += "/";

        Board board = new Board(redPlayer, whitePlayer, false);

        Space[][] spaces = new Space[Board.getSize()][];

        try(BufferedReader reader = new BufferedReader(new FileReader(basePath + args[1]))) {
            for(int i = 0; i < Board.getSize(); i++)
                spaces[i] = readSpaces(redPlayer, whitePlayer, i, reader.readLine().split(","));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + basePath + args[1]);
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        board.setSpaces(spaces);
        redPlayer.setBoardController(new BoardController(board));
        whitePlayer.setBoardController(new BoardController(board));
        System.out.println(LOAD_MESSAGE);
    }

    private Space[] readSpaces(Player redPlayer, Player whitePlayer, int row, String... input) {
        Space[] spaces = new Space[Board.getSize()];
        int j = 0;
        for(; j < input.length; j++) {
            Piece piece = null;

            switch (input[j].toLowerCase()) {
                case RED:
                    piece = new Piece(row, j, redPlayer, Piece.Type.SINGLE);
                    break;
                case WHITE:
                    piece = new Piece(row, j, whitePlayer, Piece.Type.SINGLE);
                    break;
                case RED_KING:
                    piece = new Piece(row, j, redPlayer, Piece.Type.KING);
                    break;
                case WHITE_KING:
                    piece = new Piece(row, j, whitePlayer, Piece.Type.KING);
                    break;
            }

            spaces[j] = new Space(row, j, piece);
        }
        for (; j < Board.getSize(); j++) {
            spaces[j] = new Space(j, row, null);
        }
        return spaces;
    }

    private void setSpace(Player redPlayer, Player whitePlayer, Space[][] spaces, int row, int column) {
        if(spaces[row][column].getPiece() == null) return;
        Piece piece = spaces[row][column].getPiece();

        spaces[row][column] = new Space(row, column, new Piece(column, row, (piece.getColor() == Color.WHITE)?whitePlayer:redPlayer, piece.getType()));

    }

    /**
     * The save command
     * usage: save player save-name [location: ./boards/]
     */
    private void save(String... args) {
        if(args.length < 3) {
            System.out.println(INVALID_ARGS);
            return;
        }

        Player player = lobby.getPlayer(args[1]);
        if(player == null) {
            System.out.println(PLAYER_ERROR);
            return;
        }

        Board board = player.getBoard();
        if(board == null) {
            System.out.println(BOARD_ERROR);
            return;
        }

        String basePath = (args.length >= 4)?args[3]:defaultSave;
        if(!(basePath.endsWith("/") || basePath.endsWith("\\"))) basePath += "/";
        saveObject(board.getSpaces(), basePath + args[2]);
        System.out.println(SAVE_MESSAGE);
    }

    /**
     * save an object
     *
     * @param object the object to save
     * @param path   the file path
     */
    public static void saveObject(Object object, String path) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(path))) {
            stream.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load an object
     *
     * @param location the object to load
     * @return the object loaded or null if not found
     */
    public static Object loadObject(String location) {
        location = location.replace("\\", "/");
        Object obj = null;
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(location))) {
            return stream.readObject();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            System.out.println("File not found: " + location);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
