package com.webcheckers.appl;

public class Board {

	///
	/// Constants
	///
	private static int SIZE = 8;
	private static int TOTAL_SQUARES = SIZE * SIZE;
	private static int NUM_PLAYERS = 2;

	///
	/// Attributes
	///

	// Red player's name
	private String redName;

	// White player's name
	private String whiteName;

	// Player who's turn it is
	private Player active;
}