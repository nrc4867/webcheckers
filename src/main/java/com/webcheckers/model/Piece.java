package com.webcheckers.model;

import com.webcheckers.appl.Player;

import java.io.Serializable;
import java.util.Objects;

/**
 * Pieces are moved around by Players. They inhabit {@link Space} on the
 * {@link Board}. They may be
 */
public class Piece implements Serializable{

	// TYPE ENUM ==============================================================

	public enum Type {SINGLE, KING};

	// ATTRIBUTES =============================================================
	
	transient private final Player owner;
	private final Color color;
	private Type type;

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
		setCol(col);
		setRow(row);
		this.owner = owner;
		this.type = type;
		this.color = owner.getColor();
	}

	// ACCESSORS ==============================================================

	public int getCol() {return col;}
	public int getRow() {return row;}
	public Player getOwner() {return owner;}
	public Type getType() {return type;}
	public Color getColor() {return color;}

	/**
	 * Checks if two pieces are enemies
	 * @param piece the piece to compare
	 * @return true if the colors of the pieces are opposite
	 */
	public boolean enemyOf(Piece piece) {return !getColor().equals(piece.getColor());}

	public void setCol(int c) {
		if (c < 0 || c >= Board.getSize()) {
			throw new IllegalArgumentException("Piece out of bounds!");
		}
		col = c;
	}

	public void setRow(int r) {
		if (r < 0 || r >= Board.getSize()) {
			throw new IllegalArgumentException("Piece out of bounds!");
		}
		row = r;
	}

	public void setType(Type t) {type = t;}


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