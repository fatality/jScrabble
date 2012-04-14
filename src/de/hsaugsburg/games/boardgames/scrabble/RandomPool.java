package de.hsaugsburg.games.boardgames.scrabble;

import java.io.Serializable;
import java.util.Random;
import java.util.List;
import java.util.Vector;

public class RandomPool<T> implements Serializable {

	private static final long serialVersionUID = -6535521232391105636L;
	protected Random random = new Random();
	private List<T> collection = new Vector<T>();
	
	public RandomPool() {
		
	}
	
	public void put(T o) {
		collection.add(o);
	}
	
	public T take() {
		return collection.remove(random.nextInt(collection.size()));
	}
	
	public boolean empty() {
		return collection.isEmpty();
	}
	
	public String toString() {
		return collection.toString();
	}
	
	/*
	 * getCollection() for testing purposes only
	 */
	public List<T> getCollection() {
		return collection;
	}
	
}
