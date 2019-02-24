package com.webcheckers.model;

/**
 * Players play WebCheckers. They should <i>only</i> be created
 * after the user has signed in with a valid username.
 *
 * Their color is assigned when they are added to a Board, but can
 * be changed using {@link #setColor(Color)}.
 */
public class Player {

	private final String name;
	/**
	 * The board the player is currently viewing
	 */
	private Board board = null;

	/**
	 * Player's Color (RED or WHITE). Null if unassigned. Will be overwritten
	 * when assigned to a Board. Players should not be assigned to more than
	 * one Board.
	 *
	 * @see Color
	 */
	private Color color;

	// CONSTRUCTORS ===========================================================

	/** Simplified constructor that sets the Player's Color to null. */
	public Player(String name) {
		this(name, null);
	}

	public Player(String name, Color color) {

		this.name = name;
		this.color = color;
	}

	// ACCESSORS ==============================================================

	public String getName() {return name;}
	public Color getColor() {return color;}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setColor(Color c) {this.color = c;}

	// OBJECT =================================================================
	@Override
	public int hashCode() {return name.hashCode();}

	@Override
	public String toString() {return name;}

	/**
	 * Checks the Player's names for equality. Per specifications, no two
	 * players should have the same name.
	 *
	 * @param o Object to check
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (!(o instanceof Player)) {return false;}
		final Player player = (Player) o;
		return player.name.equals(this.name);
	}
}