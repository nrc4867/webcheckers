package com.webcheckers.appl;

public class Player {

	public enum Color {RED, WHITE};

	private final String name;
	private final Color color;

	// CONSTRUCTORS ===========================================================

	public Player(String name, Color color) {

		this.name = name;
		this.color = color;
	}

	// ACCESSORS ==============================================================

	public String getName() {return name;}
	public Color getColor() {return color;}

	// OBJECT =================================================================

	@Override
	public int hashCode() {return name.hashCode();}

	@Override
	public String toString() {return name;}

	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (!(o instanceof Player)) {return false;}
		final Player player = (Player) o;
		return player.name.equals(this.name);
	}

}