package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private String lastRoom;
	private static Board board;
	private Set<Card> detNotes;
	private Solution suggestion;
	public ComputerPlayer(String name, int row, int col, Color color) {
		super(name, row, col, color);
		board = board.getInstance();
		detNotes= new HashSet<Card>(); //Stores unseen cards
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		board = Board.getInstance();
		Map<Character, String> legend = board.getLegend();
		
		Set<BoardCell> cat1 = new HashSet<BoardCell>();
		Set<BoardCell> cat2 = new HashSet<BoardCell>();
		
		for (BoardCell c : targets) {
			if (!legend.get(c.getInitial()).equals(lastRoom) && !c.isWalkway()) cat1.add(c);
			else cat2.add(c);
		}		
		
		Random rand = new Random();
		
		if (!cat1.isEmpty()) {
			int selection = rand.nextInt(cat1.size());
			int i = 0; //Unofficial index for cycling through cat1 set
			for (BoardCell c : cat1) {
				if (i == selection) return c;
				i++;
			}		
		}
		
		int selection = rand.nextInt(cat2.size());
		int i = 0; //Unofficial index for cycling through cat2 set
		for (BoardCell c : cat2) {
			if (i == selection) return c;
			i++;
		}
		
		return board.getCellAt(getRow(), getCol());
	}
	
	public void makeAccusation() {
		
	}

	public Solution createSuggestion() {
		String room, weapon, person;
		room = getRoom();
		
		ArrayList<Card> weapons = new ArrayList<Card>();
		ArrayList<Card> people = new ArrayList<Card>();
		
		for (Card c : detNotes) {
			switch (c.getType()) {
			case PERSON:
				people.add(c);
				break;
			case WEAPON:
				weapons.add(c);
				break;
			default:
			}
		}
		
		Random rand = new Random();	
		
		int selection = rand.nextInt(people.size());
		person = people.get(selection).getName();
		
		selection = rand.nextInt(weapons.size());
		weapon = weapons.get(selection).getName();
		
		suggestion = new Solution(person, weapon, room);		
		return suggestion;
	}
	
	public void setLastRoom(String lastRoom) {
		this.lastRoom = lastRoom;
	}
	
	public void addToNotes(Card theCard) {
		detNotes.add(theCard);
	}
	
	public void clearNotes() {
		detNotes.clear();
	}
	
	public String getRoom() {
		Map<Character,String> legend = board.getLegend();
		
		return legend.get(board.getCellAt(getRow(), getCol()).getInitial());
	}
	
}
