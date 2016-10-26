package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private String lastRoom;
	public ComputerPlayer(String name, int row, int col, Color color) {
		super(name, row, col, color);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		Board board = Board.getInstance();
		Map<Character, String> legend = board.getLegend();
		
		Set<BoardCell> cat1 = new HashSet<BoardCell>();
		Set<BoardCell> cat2 = new HashSet<BoardCell>();
		
		for (BoardCell c : targets) {
			if (!legend.get(c.getInitial()).equals(lastRoom) && !c.isWalkway()) cat1.add(c);
			else cat2.add(c);
		}		
		
		Random rand = new Random();
		
		if (!cat1.isEmpty()) {
			int selection = rand.nextInt() % cat1.size();
			int i = 0; //Unofficial index for cycling through cat1 set
			for (BoardCell c : cat1) {
				if (i == selection) return c;
				i++;
			}		
		}
		
		int selection = rand.nextInt() % cat2.size();
		int i = 0; //Unofficial index for cycling through cat2 set
		for (BoardCell c : cat2) {
			if (i == selection) return c;
			i++;
		}
		
		return board.getCellAt(getRow(), getCol());
	}
	
	public void makeAccusation() {
		
	}

	public void createSuggestion() {
		
	}
	
	public void setLastRoom(String lastRoom) {
		this.lastRoom = lastRoom;
	}
	
	
}
