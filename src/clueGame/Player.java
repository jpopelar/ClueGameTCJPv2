package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
	private String playerName;
	private int row, col;
	private Color color;
	private Set<Card> hand;
	private Set<Card> detNotes;
	
	public Player(String name, int row, int col, Color color) {
		super();
		this.playerName = name;
		this.row = row;
		this.col = col;
		this.color = color;
		
		hand = new HashSet<Card>();
		detNotes = new HashSet<Card>();
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> matchCards = new ArrayList<Card>();
		
		for (Card c : hand) if (c.getName().equals(suggestion.person) || c.getName().equals(suggestion.weapon) || c.getName().equals(suggestion.room)) matchCards.add(c);
		
		System.out.println(matchCards);
		
		if (matchCards.size() == 0) return null;
		
		Random rand = new Random();
		return matchCards.get(rand.nextInt(matchCards.size()));
	}
	
	//SETTERS AND GETTERS ARE FOR TESTING PURPOSES ONLY!!!!
	
	public void setName(String name) {
		this.playerName = name;
	}

	public void setLocation(int row, int col) {
		this.row = row;
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

	public Set<Card> getHand() {
		return hand;
	}

	public void giveCard(Card targetCard) {
		hand.add(targetCard);
	}
}
