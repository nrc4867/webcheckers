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

	public Player getRedPlayer() {return redPlayer;}
	public Player getWhitePlayer() {return whitePlayer;}
	public Player getActivePlayer() {return activePlayer;}
	public Piece[][] getSquares() {return squares;}
	public int getSize() {return SIZE;}
	public int getTotalSquares() {return SIZE*SIZE;}


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