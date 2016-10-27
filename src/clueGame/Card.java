package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String cardName, CardType cardtype) {
		super();
		this.cardName = cardName;
		this.cardType = cardtype;
	}


	public boolean equals(Card aCard) {
		return (this.cardName.equals(aCard.getName()) && this.cardType.equals(aCard.getType()));
	}
	
	public String getName() {
		return cardName;
	}


	public CardType getType() {
		return cardType;
	}
}
