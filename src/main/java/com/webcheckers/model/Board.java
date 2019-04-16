package com.webcheckers.model;

import com.webcheckers.appl.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Board model for WebCheckers.
 *
 * @see com.webcheckers.ui.view.BoardView
 */
public class Board {
	private static int totalBoards = 0;

	///
	/// Constants
	///
	private static final int SIZE = 8;
	private static final int NUM_PLAYERS = 2;

	///
	/// Attributes
	///

	/**
	 * The id of the board is created in the order of board creation
	 */
	private final int boardID;

	private Player redPlayer;
	private Player whitePlayer;

	/**
	 * The spaces that make up the board
	 */
	private Space[][] spaces;
	/**
	 * The previous boards by turn
	 */
	private HashMap<Integer, Space[][]> previousBoards = new HashMap<>();

	/**
	 * The player moving this turn
	 */
	private Player activePlayer;

	/**
	 * a boards mode for mode options as json, setting this option currently designates a board as complete
	 */
	private ModeOptions mode;

	private int activeRow;
	private int activeCol;

	/**
	 * What turn the board is currently on
	 */
	private int turn = 0;

	// CONSTRUCTORS ===========================================================

	/**
	 * Calls Board(red,white,false).
	 */
	public Board(Player red, Player white) {
		this(red, white, false);
	}

	/**
	 * Constructs the board. Red goes first. Board starts with 3 rows of 4
	 * pieces for each player. Players will have their colors automatically
	 * assigned.
	 *
	 * @param red Red player.
	 * @param white White player.
	 * @param empty If true, do not populate the board with Pieces.
	 */
	public Board(Player red, Player white, boolean empty) {
		boardID = totalBoards++;

		red.setColor(Color.RED);
		white.setColor(Color.WHITE);
		redPlayer = red;
		whitePlayer = white;
		spaces = new Space[SIZE][SIZE];

		activePlayer = red;
		activeRow = 0;
		activeCol = 0;

		for (int row = 0; row < getSize(); row++) {
			for (int col = 0; col < getSize(); col++) {
				spaces[row][col] = new Space(row, col);
			}
		}

		if (!empty) initialize();
		saveBoard();
	}

	// ACCESSORS ==============================================================

	/**
	 * Creates the checkerboard pattern with new pieces. White goes on top.
	 */
	private void initialize() {

		for (int i = 1; i < SIZE; i+=2) {
			spaces[0][i].setPiece(new Piece(0, i, whitePlayer));
		}
		for (int i = 0; i < SIZE; i+=2) {
			spaces[1][i].setPiece(new Piece(1, i, whitePlayer));
		}
		for (int i = 1; i < SIZE; i+=2) {
			spaces[2][i].setPiece(new Piece(2, i, whitePlayer));
		}

		for (int i = 0; i < SIZE; i+=2) {
			spaces[SIZE-3][i].setPiece(new Piece(getSize()-3, i, redPlayer));
		}
		for (int i = 1; i < SIZE; i+=2) {
			spaces[SIZE-2][i].setPiece(new Piece(getSize()-2, i, redPlayer));
		}
		for (int i = 0; i < SIZE; i+=2) {
			spaces[SIZE-1][i].setPiece(new Piece(getSize()-1, i, redPlayer));
		}
	}

	/** Sets the currently active player. */
	public void setActivePlayer(Player p) {activePlayer = p;}

	/** Sets the currently active square. */
	public void setActiveSpace(int row, int col) {

		if ((row < 0 || row >= Board.getSize())
		||  (col < 0 || col >= Board.getSize())) {
			throw new IllegalArgumentException("Active space out of bounds!");
		}

		activeRow = row;
		activeCol = col;
	}

	public int getBoardID() {return boardID;}

	/** @return Returns the Red player. */
	public Player getRedPlayer() {return redPlayer;}

	/** @return Returns the White player. */
	public Player getWhitePlayer() {return whitePlayer;}

	/**
	 * See if a player is playing on this board
	 * @param player the player to check
	 * @return true if the player is white or red
	 */
	public boolean isPlaying(Player player) {
		return (player.equals(whitePlayer) || player.equals(redPlayer));
	}

	/** @return Returns the active player. */
	public Player getActivePlayer() {return activePlayer;}

	/** @return True if given player is active player. */
	public boolean isActivePlayer(Player p) {
		return p.equals(getActivePlayer());
	}

	/** @return Returns the color of the active player. */
	public Color getActiveColor() {return getActivePlayer().getColor();}

	/** @return Returns a 2D array. Empty spaces have null pieces. */
	public Space[][] getSpaces() {return spaces;}

	public void setSpaces(Space[][] spaces) {
		if(spaces.length == Board.getSize() && spaces[0].length == Board.getSize()) {
			this.spaces = spaces;
		}
	}

	/** @return Returns the Piece at the index, or NULL. */
	public Piece getPiece(int r, int c) {return spaces[r][c].getPiece();}

	/** @return Returns the Piece at the index, or NULL. */
	public Piece getPiece(Position p) {return getPiece(p.getRow(),p.getCell());}

	/** @return Returns the <i>moving</i> piece (start position). */
	public Piece getPiece(Move m) {return getPiece(m.getStart());}

	/** @return Returns the <i>first</i> move's piece. */
	public Piece getPiece(ArrayList<Move> m) {
		if (m.isEmpty()) return null;
		else return getPiece(m.get(0));
	}

	/**
	 * Given a current move and a list of previous moves, get their
	 * starting piece. If the previous moves list is not empty,
	 * return getPiece(ArrayList), otherwise return getPiece(Move).
	 */
	public Piece getPiece(Move move, ArrayList<Move> prev) {
		if (prev.isEmpty()) return getPiece(move);
		else return getPiece(prev);
	}

	/** @return If a jump, return the piece jumped over, otherwise null. */
	public Piece getMiddlePiece(Move move) {
		return getPiece(move.getJumpedPosition());
	}


	/** @return Returns if the given coordinates are in bounds. */
	public boolean inBounds(int r, int c) {
		return c >= 0 && c < SIZE && r >= 0 && r < SIZE;
	}

	/** @return Returns the width/length of the board. */
	public static int getSize() {return SIZE;}

	/** @return Returns the SIZE of the board, squared. */
	public static int getTotalSpaces() {return SIZE*SIZE;}

	/** @return Returns the active square. */
	public Space getActiveSpace() {
		return spaces[activeRow][activeCol];
	}

	/** @return Returns the active piece. */
	public Piece getActivePiece() {
		return getActiveSpace().getPiece();
	}

	public int getTurn() {
		return turn;
	}

    /**
     * Get a board by the turn it was created at
     * @param turn the turn to see the board at, should be a value 0 <= turn < turn
     * @return a previous board, or the 0 turn board if the turn count is invalid
     */
	public Board getBoardByTurn(int turn) {
	    Board board = new Board(redPlayer, whitePlayer, true);
	    totalBoards --; // Don't need to increment

	    board.setSpaces(previousBoards.getOrDefault(turn, previousBoards.get(0)));
	    return board;
	}

	/** @return Returns whether the player has <i>any</i> pieces. */
	public boolean hasPiece(Player p) {

		for (Space[] rows : spaces) {
			for (Space col : rows) {
				Piece piece = col.getPiece();
				if (piece != null && piece.getOwner().equals(p))
					return true;
			}
		}

		return false;
	}

	/** @return Returns whether the given square is holding a piece. */
	public boolean hasPiece(int row, int col) {
		if (!inBounds(row, col)){
			throw new IllegalArgumentException("Out of bounds!");
		}
		return spaces[row][col].getPiece() != null;
	}

	public boolean hasPiece(Position p) {
		return hasPiece(p.getRow(), p.getCell());
	}

	/** @return Returns all pieces belonging to a player. */
	public ArrayList<Piece> getPlayerPieces(Player player) {

		ArrayList<Piece> pieces = new ArrayList<>();

		for (int r = 0; r < getSize(); r++) {
			for (int c = 0; c < getSize(); c++) {
				Piece p = getPiece(r,c);
				if (p != null && p.getOwner().equals(player)) pieces.add(p);
			}
		}

		return pieces;
	}

	public void setPlayMode(ModeOptions mode) {
		if(mode == null) return;
		this.mode = mode;
		whitePlayer.enableExit();
		redPlayer.enableExit();
	}

	public Map<String, Object> getModeOptions() {
		return (mode != null)?mode.getOptions():ModeOptions.gameActive().getOptions();
	}

	public void setPiece(Piece p, Position pos) {
		setPiece(p, pos.getRow(), pos.getCell());
	}

	public void setPiece(Piece p, int row, int col) {
		if (!inBounds(row, col)) {
			throw new IllegalArgumentException("Out of bounds!");
		}
		spaces[row][col].setPiece(p);
	}

	public void nextTurn() {
		activePlayer = (activePlayer == redPlayer) ? whitePlayer : redPlayer;

		turn++;
		saveBoard();
		activePlayer.alertTurn();
	}

	/**
	 * Copy a board and store it
	 */
	private void saveBoard() {
		Space[][] spaces = new Space[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {

				spaces[i][j] = new Space(i, j);
				try {
					Piece piece = this.spaces[i][j].getPiece();
					spaces[i][j].setPiece((Piece) piece.clone());
				}catch (NullPointerException | CloneNotSupportedException e) {}

			}
		}


		previousBoards.put(turn, spaces);
	}


	// Object =================================================================

	@Override
	public String toString() {
		return "Game: " + (boardID + 1) + ": " + redPlayer + " vs. " + whitePlayer;
	}

	@Override
	public int hashCode() {
		return boardID;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Board)) return false;
		Board board = (Board) obj;
		return this.boardID == board.boardID;
	}
}