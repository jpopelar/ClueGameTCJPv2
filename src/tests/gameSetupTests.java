package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class gameSetupTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt");		
		board.initialize();
		
		board.setPeopleFile("TCJPSuspects.txt");
		board.loadPeopleConfig();
		
		board.setWeaponFile("TCJPWeapons.txt");
		board.loadWeaponConfig();
		board.dealCards();
	}

	@Test
	public void playerSetUp() { //Check that player data is correctly loaded from file
		//Get player list, make sure the size is good
		ArrayList<Player> players = board.getPlayers();
		assertEquals(players.size(), 6);
		
		//First entry in player list should be human player with following parameters		
		Player humPlayer = players.get(0);		
		assert(humPlayer instanceof HumanPlayer);
		assertEquals(humPlayer.getName(), "Edward Elric");
		assertEquals(humPlayer.getRow(), 23);
		assertEquals(humPlayer.getCol(), 10);
		assertEquals(humPlayer.getColor(), Color.red);
		
		//Test parameters for a couple CPU players
		Player compPlayer1 = players.get(4);
		assert(compPlayer1 instanceof ComputerPlayer);
		assertEquals(compPlayer1.getName(), "Natsuki Subaru");
		assertEquals(compPlayer1.getRow(), 0);
		assertEquals(compPlayer1.getCol(), 4);
		assertEquals(compPlayer1.getColor(), Color.yellow);
		
		Player compPlayer2 = players.get(3);
		assert(compPlayer2 instanceof ComputerPlayer);
		assertEquals(compPlayer2.getName(), "Princess Mononoke");
		assertEquals(compPlayer2.getRow(), 1);
		assertEquals(compPlayer2.getCol(), 10);
		assertEquals(compPlayer2.getColor(), Color.green);
		
	}
	
	@Test
	public void makeDeck() { //Check that deck is built properly and has everything we need
		Set<Card> deck = board.getDeck();
		
		assertEquals(deck.size(), 21); //Check deck size
		
		int rooms = 0;
		int people = 0;
		int weapons = 0;
		
		for (Card c : deck) { //For loop counts up the number of each type of card in deck
			switch (c.getType()) {
			case PERSON:
				people++;
				break;
			case ROOM:
				rooms++;
				break;
			case WEAPON:
				weapons++;
				break;
			default:
			}
		}
		//Check results
		assertEquals(rooms, 9);
		assertEquals(people, 6);
		assertEquals(weapons, 6);
		
		boolean foundRoomString = false;
		boolean foundWeaponString = false;
		boolean foundPersonString = false;

		for (Card c : deck) { //For loop that cycles through deck and finds individual cards
			String cardName = c.getName();
			
			if (cardName.equals("Spike Spiegel")) foundPersonString = true; //Individual person
			if (cardName.equals("Patio")) foundRoomString = true; //Individual room
			if (cardName.equals("Dominator")) foundWeaponString = true; //Individual weapon
		}
		//Check results
		assert(foundPersonString);
		assert(foundRoomString);
		assert(foundWeaponString);
	}
	
	@Test
	public void dealCardsTests() {
		ArrayList<Player> players = board.getPlayers(); //Get players list
		Set<Card> deck = board.getDeck(); //Get deck
		Set<Card> cardsDealt = new HashSet<Card>(); //Make set containing cards dealt out

		for (Player p : players) {
			Set<Card> theirHand = p.getHand();
			for (Card c : theirHand) cardsDealt.add(c); //Scan through each player's hand, add their cards to the dealt set
		}

		assert(deck.equals(cardsDealt)); //Check to make sure the deck and dealt sets are the same, then all cards were dealt
		
		//Uniqueness of cards test
		for (Player p : players) { //For each player
			Set<Card> theirHand = p.getHand(); //Get the cards in their hand

			ArrayList<Player> otherPlayers = new ArrayList<Player>(players);
			otherPlayers.remove(p);	//Make a separate list of all other players

			for (Player o : otherPlayers) {
				for (Card c : theirHand) { //Cycle through each card in active player's hand
					assert(!o.getHand().contains(c)); //Ensure that it does not exist in anyone else's
				}
			}
		}
		
		int normalHandSize = 3;
		int largeHandSize = 4; //For a 21 card deck, each player's hand will have 3 or 4 cards

		for (Player p : players) assert((p.getHand().size() == normalHandSize) || (p.getHand().size() == largeHandSize)); //Check that for each player's hand		

		
	}

}
