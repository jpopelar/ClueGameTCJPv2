package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class Part3AdjTargetBoardTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("OurRoomLayout.csv", "OurLegend.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(4, 10);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(17, 0);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(19, 20);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(17, 9);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(9, 7);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY LEFT 
		Set<BoardCell> testList = board.getAdjList(5,22);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 21)));
		// TEST DOORWAY UP 
		testList = board.getAdjList(9, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 21)));
		//TEST DOORWAY RIGHT
		testList = board.getAdjList(13, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 15)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(19, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(20, 4)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction DOWN
		Set<BoardCell> testList = board.getAdjList(14, 22);
		assertTrue(testList.contains(board.getCellAt(14, 21)));
		assertTrue(testList.contains(board.getCellAt(13, 22)));
		assertTrue(testList.contains(board.getCellAt(15, 22)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(13, 7);
		assertTrue(testList.contains(board.getCellAt(13, 8)));
		assertTrue(testList.contains(board.getCellAt(13, 6)));
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(15, 10);
		assertTrue(testList.contains(board.getCellAt(15, 9)));
		assertTrue(testList.contains(board.getCellAt(15, 11)));
		assertTrue(testList.contains(board.getCellAt(16, 10)));
		assertTrue(testList.contains(board.getCellAt(14, 10)));
		assertEquals(4, testList.size());
		// Test beside a door direction RIGHT
		testList = board.getAdjList(3, 4);
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertTrue(testList.contains(board.getCellAt(3, 3)));
		assertTrue(testList.contains(board.getCellAt(2, 4)));
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(0, 21);
		assertTrue(testList.contains(board.getCellAt(1, 21)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(6, 0);
		assertTrue(testList.contains(board.getCellAt(5, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 0)));
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways up and down
		testList = board.getAdjList(4, 21);
		assertTrue(testList.contains(board.getCellAt(5, 21)));
		assertTrue(testList.contains(board.getCellAt(3, 21)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(6,10);
		assertTrue(testList.contains(board.getCellAt(7, 10)));
		assertTrue(testList.contains(board.getCellAt(5, 10)));
		assertTrue(testList.contains(board.getCellAt(6, 11)));
		assertTrue(testList.contains(board.getCellAt(6, 9)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(21, 15);
		assertTrue(testList.contains(board.getCellAt(21, 14)));
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(15, 22);
		assertTrue(testList.contains(board.getCellAt(14, 22)));
		assertTrue(testList.contains(board.getCellAt(15, 21)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(18, 5);
		assertTrue(testList.contains(board.getCellAt(18, 6)));
		assertTrue(testList.contains(board.getCellAt(17, 5)));
		assertTrue(testList.contains(board.getCellAt(19, 5)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(21, 13, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 13)));
		assertTrue(targets.contains(board.getCellAt(21, 14)));	
		
		board.calcTargets(15, 0, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 1)));
		assertTrue(targets.contains(board.getCellAt(14, 0)));	
		assertTrue(targets.contains(board.getCellAt(16, 0)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(21, 13, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 13)));
		assertTrue(targets.contains(board.getCellAt(21, 15)));
		assertTrue(targets.contains(board.getCellAt(20, 14)));
		
		board.calcTargets(15, 0, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 0)));
		assertTrue(targets.contains(board.getCellAt(16, 1)));	
		assertTrue(targets.contains(board.getCellAt(15, 2)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 13, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 13)));
		assertTrue(targets.contains(board.getCellAt(18, 14)));
		assertTrue(targets.contains(board.getCellAt(19, 15)));
		assertTrue(targets.contains(board.getCellAt(20, 14)));
		assertTrue(targets.contains(board.getCellAt(21, 15)));
		assertTrue(targets.contains(board.getCellAt(19, 13)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(15, 0, 4);
		targets= board.getTargets();
		System.out.println(targets);
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		assertTrue(targets.contains(board.getCellAt(16, 3)));	
		assertTrue(targets.contains(board.getCellAt(16, 1)));
		assertTrue(targets.contains(board.getCellAt(15, 2)));
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are DARK BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(15, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 1)));
		assertTrue(targets.contains(board.getCellAt(15, 2)));	
		assertTrue(targets.contains(board.getCellAt(16, 5)));	
		assertTrue(targets.contains(board.getCellAt(17, 4)));	
		assertTrue(targets.contains(board.getCellAt(15, 4)));	
		assertTrue(targets.contains(board.getCellAt(15, 6)));	
		assertTrue(targets.contains(board.getCellAt(14, 5)));
		assertTrue(targets.contains(board.getCellAt(16, 3)));
	}	
	
	// Test getting into a room
	// These are DARK BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(16, 17, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// directly left
		assertTrue(targets.contains(board.getCellAt(16, 15)));
		//directly right
		assertTrue(targets.contains(board.getCellAt(16, 19)));
		// directly up
		assertTrue(targets.contains(board.getCellAt(14, 17)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(15, 18)));
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		assertTrue(targets.contains(board.getCellAt(17, 16)));
		assertTrue(targets.contains(board.getCellAt(17, 18)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(5, 18, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(10, targets.size());
		// directly up and down(Can't go either)
		// directly right and left
		assertTrue(targets.contains(board.getCellAt(5, 21)));
		assertTrue(targets.contains(board.getCellAt(5, 15)));
		// right then down
		assertTrue(targets.contains(board.getCellAt(6, 18)));
		//left then down
		assertTrue(targets.contains(board.getCellAt(7, 17)));
		// down then left/right
		assertTrue(targets.contains(board.getCellAt(6, 20)));
		assertTrue(targets.contains(board.getCellAt(6, 16)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(4, 17)));
		assertTrue(targets.contains(board.getCellAt(4, 16)));		
		// 
		assertTrue(targets.contains(board.getCellAt(5, 17)));		
		assertTrue(targets.contains(board.getCellAt(5, 19)));		
		
	}

	// Test getting out of a room
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(1, 15, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 15)));
		// Take two steps
		board.calcTargets(1, 15, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 15)));
		assertTrue(targets.contains(board.getCellAt(2, 14)));
	}

}
