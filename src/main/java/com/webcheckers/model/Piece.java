package com.webcheckers.model;

import java.util.Objects;

public class Piece {

	// TYPE ENUM ==============================================================

	// DEAD may be extraneous, including it right now in case we need it.
	public enum Type {SINGLE, KING};

	// ATTRIBUTES =============================================================
	
	private final Player owner;
	private Type type;

	private final int col;
	private final int row;

	// CONSTRUCTORS ===========================================================

	/**
	 * Constructs a new standard piece.
	 *
	 * @param col Column position on the board
	 * @param row Row position on the board
	 * @param owner Which player this belongs to.
	 * @see Piece(int, int, Player, Type)
	 */
	public Piece(int col, int row, Player owner)
	{
		this(col, row, owner, Type.SINGLE);
	}

	/**
	 * Constructs a new Piece with the given parameters.
	 *
	 * @param col Column position on the board
	 * @param row Row position on the board
	 * @param owner Which player this belongs to.
	 * @param type What kind of piece this is.
	 * @see Piece(int, int, Player)
	 */
	public Piece(int col, int row, Player owner, Type type) {
		this.col = col;
		this.row = row;
		this.owner = owner;
		this.type = type;
	}

	// ACCESSORS ==============================================================

	public int getCol() {return col;}
	public int getRow() {return row;}
	public Player getOwner() {return owner;}
	public Type getType() {return type;}
	public Color getColor() {return owner.getColor();}

	// Object =================================================================

	@Override
	public int hashCode() {
		return Objects.hash(owner, type, col, row);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (!(o instanceof Piece)) {return false;}
		final Piece piece = (Piece) o;
		return (piece.owner == this.owner
			 && piece.type == this.type
			 && piece.col == this.col
			 && piece.row == this.row);
	}

	/**
	 * Example:
	 *
	 * RED'S KING PIECE AT (5,6)
	 */
	@Override
	public String toString() {
		return String.format(
			"%s'S %s PIECE AT (%d,%d)",
			owner.toString(), type.name(), col, row
		);
	}
}