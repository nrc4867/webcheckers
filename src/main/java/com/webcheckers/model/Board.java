package com.webcheckers.model;

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
	private Piece[][] squares;
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
		red.setColor(Player.Color.RED);
		white.setColor(Player.Color.WHITE);
		redPlayer = red;
		whitePlayer = white;
		squares = new Piece[SIZE][SIZE];

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

		for (int i = 1; i < SIZE; i+=2) {
			squares[0][i] = new Piece(i,0,whitePlayer);
		}
		for (int i = 0; i < SIZE; i+=2) {
			squares[1][i] = new Piece(i, 1, whitePlayer);
		}
		for (int i = 1; i < SIZE; i+=2) {
			squares[2][i] = new Piece(i, 1, whitePlayer);
		}

		for (int i = 0; i < SIZE; i+=2) {
			squares[SIZE-3][i] = new Piece(i, 1, redPlayer);
		}
		for (int i = 1; i < SIZE; i+=2) {
			squares[SIZE-2][i] = new Piece(i, 1, redPlayer);
		}
		for (int i = 0; i < SIZE; i+=2) {
			squares[SIZE-1][i] = new Piece(i, 1, redPlayer);
		}
	}

	/** Sets the opposing player to be the active player. */
	public void switchActivePlayer() {
		if (activePlayer.equals(redPlayer)) {
			activePlayer = whitePlayer;
		}
		else {
			activePlayer = redPlayer;
		}
	}

	/** Sets the currently active square. */
	public void setActiveSquare(int row, int col) {
		activeRow = row;
		activeCol = col;
	}

	/** @return Returns the Red player. */
	public Player getRedPlayer() {return redPlayer;}

	/** @return Returns the White player. */
	public Player getWhitePlayer() {return whitePlayer;}

	/** @return Returns the active player. */
	public Player getActivePlayer() {return activePlayer;}

	/** @return Returns a 2D array. Empty squares have null pieces. */
	public Piece[][] getSquares() {return squares;}

	/** @return Returns the width/length of the board. */
	public int getSize() {return SIZE;}

	/** @return Returns the SIZE of the board, squared. */
	public int getTotalSquares() {return SIZE*SIZE;}

	/** @return Returns the active piece. */
	public Piece getActivePiece() {return squares[activeRow][activeCol];}

	/** @return Returns whether the given square is holding a piece. */
	public boolean hasPiece(int row, int col) {
		return squares[row][col] == null;
	}


	// Object =================================================================

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {

				if (squares[row][col] == null) {
					builder.append(" ");
				}
				else if (squares[row][col].getColor() == Player.Color.RED) {
					builder.append("R");
				}
				else if (squares[row][col].getColor() == Player.Color.WHITE) {
					builder.append("W");
				}
			}

			builder.append("\n");
		}

		return builder.toString();
	}

}