package com.webcheckers.model;

import com.webcheckers.appl.BoardController;
import com.webcheckers.appl.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

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
	 * @param row Row position on the board
	 * @param col Column position on the board
	 * @param owner Which player this belongs to.
	 * @see Piece(int, int, Player, Type)
	 */
	public Piece(int row, int col, Player owner)
	{
		this(row, col, owner, Type.SINGLE);
	}

	/**
	 * Constructs a new Piece with the given parameters.
	 *
	 * @param row Row position on the board
	 * @param col Column position on the board
	 * @param owner Which player this belongs to.
	 * @param type What kind of piece this is.
	 * @see Piece(int, int, Player)
	 */
	public Piece(int row, int col, Player owner, Type type) {
		setCol(col);
		setRow(row);
		this.owner = owner;
		this.type = type;
		this.color = owner.getColor();
	}

	// ACCESSORS ==============================================================

	public int getCol() {return col;}
	public int getRow() {return row;}
	public Position getPos() {return new Position(row, col);}
	public Player getOwner() {return owner;}
	public Type getType() {return type;}
	public Color getColor() {return color;}


	/**
	 * Gets the piece's current position on the board, taking previous
	 * moves into account.
	 * @param prev Previous moves.
	 * @return The current position of the Piece.
	 */
	public Position getCurrentPosition(ArrayList<Move> prev) {
		if (prev.isEmpty()) return getPos();
		else return Move.getLast(prev).getEnd();
	}

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
	public boolean isKing() {return type == Type.KING; }

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