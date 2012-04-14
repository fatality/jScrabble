package de.hsaugsburg.games.boardgames;

import java.io.Serializable;
import java.util.Vector;

/**
 * The Player class keeps track of following data about a player:
 * the player's points, ID number and the name. The ID number is
 * generated from the number of instances of this class.
 * 
 * @author Marc Rochow, Anja Radtke
 */
public class Player implements Serializable {

	private static final long serialVersionUID = -8194978558396778905L;
	private Vector<Integer> points;
	private String name;
	private final int ID;

	private static int numberOfInstances;
	
	/**
	 * Creates a new player with the specified name.
	 * @param name the player's name as a <code>String</code>.
	 */
	public Player(String name) {
		points = new Vector<Integer>();
	    this.name = name;
	    this.ID = ++numberOfInstances;
	}
	
	/**
	 * Adds points to the current points count.
	 * @param newPoints the points to be added.
	 */
	public void addPoints(int newPoints) {
	    points.add(newPoints);
	}
	
	/**
	 * Player's total points.
	 * @return the points total of this player.
	 */
	public int getPoints() {
		int total = 0;
		for (Integer tmp : points) {
			total += tmp;
		}
		return total;
	}
	
	public int getLastPoints() {
		return points.lastElement();
	}
	
	/**
	 * Returns the name.
	 * @return plyer's name as a <code>String</code>.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets this player's ID number.
	 * @return ID number of this player.
	 */
	public int getId() {
		return ID;
	}
	
	/**
	 * Decrements the ID/instances number when the garbage collector
	 * removes the player object.
	 */
	public void finalize() {
		--numberOfInstances;
	}
	
}
