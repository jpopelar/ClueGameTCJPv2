package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String cardName, CardType cardtype) {
		super();
		this.cardName = cardName;
		this.cardType = cardtype;
	}


	public boolean equals() {
		return false;
	}
	
	public String getName() {
		return cardName;
	}


	public CardType getType() {
		return cardType;
	}
}
