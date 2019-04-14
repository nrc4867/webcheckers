package com.webcheckers.appl;

import com.webcheckers.model.*;

/**
 * Players play WebCheckers. They should <i>only</i> be created
 * after the user has signed in with a valid username.
 *
 * Their color is assigned when they are added to a Board, but can
 * be changed using {@link #setColor(Color)}.
 */
public class Player implements Cleanup {

	private final String name;

	private boolean playerInGame;

	/**
	 * The board the player is currently playing on
	 */
	private Board board = null;

    /**
     * The lobby the player is currently part of
     */
    private PlayerLobby lobby;

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

    public PlayerLobby getLobby() {
        return lobby;
    }
    public void setLobby(PlayerLobby lobby) {
        if(this.lobby != null) this.lobby.removePlayer(this);
        this.lobby = lobby;
    }

    public boolean inGame() {return playerInGame;}
	public BoardController getBoardController() {
		return new BoardController(board);
	}
	public void removeBoard() {
		playerInGame = false;
		board = null;
	}
	public void setBoard(Board board) {
		this.board = board;
		this.playerInGame = board != null;
	}
	public void enableExit() {
		playerInGame = false;
	}

	public void setColor(Color c) {this.color = c;}

	public void resign() {
		if(board != null) {
			board.setPlayMode(ModeOptions.resign(this));
		}
	}

	public boolean checkTurn() {
		return board != null && board.isActivePlayer(this);
	}
	public void alertTurn() {}

	public boolean ownsPiece(Piece piece) {
		return equals(piece.getOwner());
	}

	public Player getOpponent() {
		if(getBoardController() != null)
			return (color == Color.WHITE)?getBoard().getRedPlayer():getBoard().getWhitePlayer();
		return null;
	}

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

    @Override
    public void cleanup() {
        lobby.removePlayer(this);
    }


}