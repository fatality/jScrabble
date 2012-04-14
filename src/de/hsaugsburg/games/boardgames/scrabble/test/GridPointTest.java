package de.hsaugsburg.games.boardgames.scrabble.test;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import de.hsaugsburg.games.boardgames.GridPoint;

public class GridPointTest {

	private GridPoint gp0;
	private GridPoint gp1;
	private GridPoint gp4;
	private Set<GridPoint> gpSet;
	

	private Set<GridPoint> gps1;
	private Set<GridPoint> gps2;
	private Set<GridPoint> gps3;
	
	@Before
	public void setUp() throws Exception {
		
		gpSet = new TreeSet<GridPoint>();
		gps1 = new HashSet<GridPoint>();
		gps2 = new HashSet<GridPoint>();
		gps3 = new HashSet<GridPoint>();
		
		gp0 = new GridPoint(1, 1);
		gp1 = new GridPoint(1, 1);
		gp4 = new GridPoint(5, 5);
		
		// 3 x 3 Grid
		gpSet.add(new GridPoint(1, 1));
		gpSet.add(new GridPoint(2, 1));
		gpSet.add(new GridPoint(0, 0));
		gpSet.add(new GridPoint(0, 2));
		gpSet.add(new GridPoint(0, 1));
		gpSet.add(new GridPoint(2, 0));
		gpSet.add(new GridPoint(1, 0));
		gpSet.add(new GridPoint(2, 2));
		gpSet.add(new GridPoint(1, 2));
		
		for (int i = 0; i < 10; i++) {
			gps1.add(new GridPoint(1, i));
			gps2.add(new GridPoint(i, 1));
			gps3.add(new GridPoint(i, -i));
		}
		
	}

	@Test
	public void testGridPoint() {
		assertTrue(new GridPoint(1, 1).equals(gp0));
	}

	@Test
	public void testCompareTo() {
		System.out.println(gpSet);
		gpSet.remove(new GridPoint(1, 0));
		System.out.println(gpSet);
		gpSet.add(new GridPoint(1, 0));
		System.out.println(gpSet);
		GridPoint[] gpArr = gpSet.toArray(new GridPoint[gpSet.size()]);
		
		for (int i = 0; i < gpArr.length; i++) {
			System.out.println(gpArr[i].toString());
		}
		assertTrue(gpArr[0].equals(new GridPoint(0, 0)));
		assertTrue(gpArr[8].equals(new GridPoint(2, 2)));
	}

	@Test
	public void testClone() {
		GridPoint tmp = gp0.clone();
		assertTrue(gp0.equals(tmp));
	}

	@Test
	public void testToString() {
		assertTrue(gp0.toString().equalsIgnoreCase("(1, 1)"));
	}

	@Test
	public void testNeg() {
		assertTrue((gp0.neg().getRow() + gp0.getRow() == 0)
				&& (gp0.neg().getColumn() + gp0.getColumn() == 0));
	}

	@Test
	public void testPlus() {
		assertTrue(gp0.plus(gp4).equals(new GridPoint(6, 6)));
	}

	@Test
	public void testMinus() {
		assertTrue(gp0.minus(gp4).equals(new GridPoint(-4, -4)));
	}

	@Test
	public void testEqualsObject() {
		assertTrue(gp1.equals(gp0));
		assertTrue(!gp1.equals(Integer.valueOf(2)));
	}

	@Test
	public void testGetRow() {
		assertSame(gp0.getRow() - 1, 0);
	}

	@Test
	public void testGetColumn() {
		assertSame(gp0.getColumn() - 1, 0);
	}
	
	@Test
	public void testIsManhattanColinearWith() {
        assertTrue(gp0.isManhattanColinearWith(gps1));
        assertTrue(gp0.isManhattanColinearWith(gps2));
        assertTrue(!gp0.isManhattanColinearWith(gps3));
    }

}
