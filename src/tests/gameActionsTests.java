package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionsTests {
	
	private static Board board;
	public static final int TARGET_RUNS = 100;
	
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
	}

	@Test
	public void testAccusation() { //Tests solution checking on board
		Solution theSolution = new Solution("Natsuki Subaru","Katana","Server Room");
		board.setSolution(theSolution); //Hard code a solution 
		
		assert(board.checkAccusation(theSolution)); //Ensure that the game registers it as correct
		
		Solution badSol1 = new Solution("Jotaro Kujo","Katana","Server Room"); //Wrong person
		assert(!board.checkAccusation(badSol1));
		
		Solution badSol2 = new Solution("Natsuki Subaru","Dominator","Server Room"); //Wrong weapon
		assert(!board.checkAccusation(badSol2));
		
		Solution badSol3 = new Solution("Natsuki Subaru","Katana","Patio"); //Wrong room
		assert(!board.checkAccusation(badSol3));
	}
	
	@Test
	public void testTargeting() { //Tests solution checking on board
		ComputerPlayer cpuPlay = new ComputerPlayer("Motoko Kusanagi", 3, 4, Color.blue); //Make a CPU player
		board.calcTargets(cpuPlay.getRow(),cpuPlay.getCol(),1); //Assume a roll of 1 for movement, so no rooms in target list
		
		Set<BoardCell> targets = board.getTargets(); //Get target list
		//System.out.println(targets);
				
		boolean got24 = false;
		boolean got44 = false;
		boolean got33 = false;
		
		for (int i = 0; i < TARGET_RUNS; i++) { //Run a number of target selection, turn on bools as their squares are selected
			BoardCell targCell = cpuPlay.pickLocation(targets);
			cpuPlay.setLastRoom("Patio");
			
			if (targCell.equals(board.getCellAt(2, 4))) got24 = true;
			if (targCell.equals(board.getCellAt(4, 4))) got44 = true;
			if (targCell.equals(board.getCellAt(3, 3))) got33 = true;
		}
		
		assert(got44); 
		assert(got24);
		assert(got33); //Check all were selected at some point
		
		cpuPlay.setLocation(5, 3); //Put one door within range, assume unvisited
		board.calcTargets(cpuPlay.getRow(),cpuPlay.getCol(),3); //Fix roll of 3
		
		targets = board.getTargets();
		for (int i = 0; i < TARGET_RUNS; i++) {
			assert(cpuPlay.pickLocation(targets).equals(board.getCellAt(3, 2))); //Ensure the CPU picks the unvisited room
			cpuPlay.setLastRoom(null); //Trick into thinking each run is fresh
		}
		
		Map<Character, String> legend = board.getLegend();
		cpuPlay.setLastRoom(legend.get('G')); //Set gym as last visited room
		
		boolean got34 = false;
		boolean got32 = false;
		boolean got23 = false;
		boolean got54 = false;
		boolean got43 = false;
		
		for (int i = 0; i < TARGET_RUNS; i++) { //Run a number of target selection, turn on bools as their squares are selected
			BoardCell targCell = cpuPlay.pickLocation(targets);
			
			if (targCell.equals(board.getCellAt(3, 4))) got34 = true;
			if (targCell.equals(board.getCellAt(3, 2))) got32 = true; //Gym room door, should be selected randomly without bias
			if (targCell.equals(board.getCellAt(2, 3))) got23 = true;
			if (targCell.equals(board.getCellAt(5, 4))) got54 = true;
			if (targCell.equals(board.getCellAt(4, 3))) got43 = true;
		}
		
		assert(got34);
		assert(got32);
		assert(got23);
		assert(got54);
		assert(got43); //Check all were selected at some point
		
		cpuPlay.setLocation(3, 4); //Set location again
		board.calcTargets(cpuPlay.getRow(),cpuPlay.getCol(),2); //Fix roll of 2, now 2 rooms in range with 1 recently visited
		
		targets = board.getTargets();
		
		for (int i = 0; i < TARGET_RUNS; i++) {
			assert(cpuPlay.pickLocation(targets).equals(board.getCellAt(2, 5))); //Ensure the CPU picks the unvisited room
			cpuPlay.setLastRoom(legend.get('G')); //Trick into thinking it visited the gym last
		}
	}
	
	@Test
	public void testSuggestions() { //Test CPU player suggestion functionality
		ComputerPlayer cpuPlay = new ComputerPlayer("Motoko Kusanagi", 24, 3, Color.blue); //Make a CPU player
		Map<Character, String> legend = board.getLegend();
		
		cpuPlay.clearNotes(); //Paranoid detective note clear
		
		Card person1 = new Card("Edward Elric", CardType.PERSON);
		Card weapon1 = new Card("Blood Curse", CardType.WEAPON);
		Card room1 = new Card("Patio", CardType.ROOM); //Make some cards
		
		cpuPlay.addToNotes(person1);
		cpuPlay.addToNotes(weapon1);
		cpuPlay.addToNotes(room1); //Add the cards to detective notes, these cards have NOT been seen by CPU player
		
		Solution suggestion = cpuPlay.createSuggestion(); //One of each card type is seen, so suggestion should only return one Solution
		
		assert(suggestion.person.equals(person1.getName())); //Should be the person not seen
		assert(suggestion.weapon.equals(weapon1.getName())); //Should be the weapon not seen
		assert(suggestion.room.equals(legend.get(board.getCellAt(cpuPlay.getRow(), cpuPlay.getCol()).getInitial()))); //Should be current room and NOT room unseen
		
		Card person2 = new Card("Jotaro Kujo", CardType.PERSON);
		Card weapon2 = new Card("Dominator", CardType.WEAPON); //Make two more cards
		
		cpuPlay.addToNotes(person2);
		cpuPlay.addToNotes(weapon2); //Add them to detective notes, now there isn't any certainty		
		
		Solution poss1 = new Solution("Jotaro Kujo","Dominator","Server Room");
		Solution poss2 = new Solution("Jotaro Kujo","Blood Curse","Server Room");
		Solution poss3 = new Solution("Edward Elric","Dominator","Server Room");
		Solution poss4 = new Solution("Edward Elric","Blood Curse","Server Room"); //List possible suggestions
		
		boolean got1 = false;
		boolean got2 = false;
		boolean got3 = false;
		boolean got4 = false;
		
		for (int i = 0; i < TARGET_RUNS; i++) { //Run a number of suggestions, see if all are selected at some point
			suggestion = cpuPlay.createSuggestion(); //Should be four possible suggestions (two people, two weapons, one room)
			
			if (suggestion.equals(poss1)) got1 = true;
			if (suggestion.equals(poss2)) got2 = true;
			if (suggestion.equals(poss3)) got3 = true;
			if (suggestion.equals(poss4)) got4 = true;
		}
		
		assert(got1);
		assert(got2);
		assert(got3);
		assert(got4); //Check all were selected at some point
	}

}
