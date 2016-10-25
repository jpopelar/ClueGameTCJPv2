package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	private String lastRoom;
	public ComputerPlayer(String name, int row, int col, Color color) {
		super(name, row, col, color);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {
		
	}

	public void createSuggestion() {
		
	}
	
	public void setLastRoom(String lastRoom) {
		this.lastRoom = lastRoom;
	}
	
	
}
