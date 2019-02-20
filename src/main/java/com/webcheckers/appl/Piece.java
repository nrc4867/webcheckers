package com.webcheckers.appl;

import java.util.Objects;
public class Piece {

	// TYPE ENUM ==============================================================

	// DEAD may be extraneous, including it right now in case we need it.
	public enum Type {STANDARD, KING, DEAD};

	// ATTRIBUTES =============================================================
	
	private final Player owner; // red, white
	private Type type; // standard, king

	private int col;
	private int row;

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
		this(col, row, owner, Type.STANDARD);
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
	public Piece(int col, int y, Player owner, Type type) {
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

	public void setCol(int c) {col = c;}
	public void setRow(int r) {row = r;}
	public void setType(Type t) {type = t;}
	public void king() {type = Type.KING;}
	public void kill() {type = Type.DEAD;}

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
			owner.name(), type.name(), col, row
		);
	}
}