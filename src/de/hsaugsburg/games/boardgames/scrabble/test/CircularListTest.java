package de.hsaugsburg.games.boardgames.scrabble.test;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import de.hsaugsburg.games.boardgames.Player;
import de.hsaugsburg.games.boardgames.scrabble.CircularList;

public class CircularListTest {
	
	CircularList<Player> list;
	Player p1;
	Player p2;
	Player p3;
	Player p4;
	
	@Before
	public void setUp() throws Exception {
		list = new CircularList<Player>();
		p1 = new Player("Alice");
		p2 = new Player("Bob");
		p3 = new Player("Tina");
		p4 = new Player("Nick");
	}

	@Test
	public void testAdd() {
		list.reset();
		list.add(p1);
		assertTrue(list.next() != null);
	}

	@Test
	public void testNext() {
		list.reset();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		assertEquals(list.next().getName(),"Bob");
		assertTrue(list.next().equals(p3));
		assertTrue(list.next().equals(p4));
		assertTrue(list.next().equals(p1));
	}

	@Test
	public void testPrevious() {
		list.reset();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.next();
		assertEquals(list.previous(), p1);
		assertTrue(list.previous().equals(p4));
		assertTrue(list.previous().equals(p3));
		assertTrue(list.previous().equals(p2));
	}

	@Test
	public void testReset() {
		list.add(p1);
		list.reset();
		assertTrue(list.next() == null);
	}
	
	@Test
	public void testGetAll() {
		assertTrue(list.getAll() instanceof List);
	}

}
