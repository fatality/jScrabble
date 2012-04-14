package de.hsaugsburg.games.boardgames.scrabble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CircularList<T> implements Serializable {
	
	private static final long serialVersionUID = -4488884422541989373L;
	private int current;
	private List<T> list = new ArrayList<T>();
	
	public void add(T t) {
		list.add(t);
	}
	
	public T next() {
		if (list.size() != 0) {
			return list.get(current = ++current%list.size());
		} else {
			return null;
		}
	}
	
	public T previous() {
		if (list.size() != 0) {
			return list.get(current =(--current+list.size())%list.size());
		} else {
			return null;
		}
	}
	
	public T current() {
		return list.get(current);
	}
	
	public List<T> getAll() {
		return list;
	}
	
	public void reset() {
		list.clear();
		current = 0;
	}
	
}
