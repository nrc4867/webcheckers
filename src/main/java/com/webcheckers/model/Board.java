package com.webcheckers.model;

/**
 * Board model for WebCheckers.
 *
 * @see com.webcheckers.ui.view.BoardView
 */
public class Board {

	///
	/// Constants
	///
	private static final int SIZE = 8;
	private static final int NUM_PLAYERS = 2;

	///
	/// Attributes
	///
	private Player redPlayer;
	private Player whitePlayer;
	private Space[][] spaces;
	private Player activePlayer;
	private int activeRow;
	private int activeCol;

	// CONSTRUCTORS ===========================================================

	/**
	 * Constructs the board. Red goes first. Board starts with 3 rows of 4
	 * pieces for each player. Players will have their colors automatically
	 * assigned.
	 *
	 * @param red Red player.
	 * @param white White player.
	 */
	public Board(Player red, Player white) {
		red.setColor(Color.RED);
		white.setColor(Color.WHITE);
		redPlayer = red;
		whitePlayer = white;
		spaces = new Space[SIZE][SIZE];

		activePlayer = red;
		activeRow = 0;
		activeCol = 0;
		initialize();

	}

	// ACCESSORS ==============================================================

	/**
	 * Creates the checkerboard pattern with new pieces. White goes on top.
	 */
	private void initialize() {

		for (int row = 0; row < getSize(); row++) {
			for (int col = 0; col < getSize(); col++) {
				spaces[row][col] = new Space(row, col);
			}
		}

		for (int i = 1; i < SIZE; i+=2) {
			spaces[0][i].setPiece(new Piece(i,0,whitePlayer));
		}
		for (int i = 0; i < SIZE; i+=2) {
			spaces[1][i].setPiece(new Piece(i,1,whitePlayer));
		}
		for (int i = 1; i < SIZE; i+=2) {
			spaces[2][i].setPiece(new Piece(i,2,whitePlayer));
		}

		for (int i = 0; i < SIZE; i+=2) {
			spaces[SIZE-3][i].setPiece(new Piece(i, getSize()-3, redPlayer));
		}
		for (int i = 1; i < SIZE; i+=2) {
			spaces[SIZE-2][i].setPiece(new Piece(i, getSize()-2, redPlayer));
		}
		for (int i = 0; i < SIZE; i+=2) {
			spaces[SIZE-1][i].setPiece(new Piece(i, getSize()-1, redPlayer));
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

	/** @return Returns the Red player. */
	public Player getRedPlayer() {return redPlayer;}

	/** @return Returns the White player. */
	public Player getWhitePlayer() {return whitePlayer;}

	/** @return Returns the active player. */
	public Player getActivePlayer() {return activePlayer;}

	/** @return Returns the color of the active player. */
	public Color getActiveColor() {return getActivePlayer().getColor();}

	/** @return Returns a 2D array. Empty spaces have null pieces. */
	public Space[][] getSpaces() {return spaces;}

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

	/** @return Returns whether the given square is holding a piece. */
	public boolean hasPiece(int row, int col) {
		return spaces[row][col] == null;
	}


	// Object =================================================================

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {

				if (spaces[row][col] == null) {
					builder.append(" ");
				}
				else if (spaces[row][col].getPiece().getColor() == Color.RED) {
					builder.append("R");
				}
				else if (spaces[row][col].getPiece().getColor() == Color.WHITE) {
					builder.append("W");
				}
			}

			builder.append("\n");
		}

		return builder.toString();
	}

}