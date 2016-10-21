package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row, col;
	private Color color;
	private ArrayList<Card> hand;
	private ArrayList<Card> detNotes;
	
	public Player(String name, int row, int col, Color color) {
		super();
		this.playerName = name;
		this.row = row;
		this.col = col;
		this.color = color;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	//SETTERS AND GETTERS ARE FOR TESTING PURPOSES ONLY!!!!
	
	public void setName(String name) {
		this.playerName = name;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

	public Color getColor() {
		return color;
	}
}
