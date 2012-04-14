package de.hsaugsburg.games.boardgames;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public class GridPoint implements Comparable<GridPoint>, Cloneable, Serializable {

	private static final long serialVersionUID = 7447516188289707391L;
	private final int row;
	private final int column;
	
	public GridPoint(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int compareTo(GridPoint rc) {
		if (this.row > rc.row) {
			return 1; //this is located left from or above rc
		} else if (this.row < rc.row) {
			return -1; //this is located right from or below rc
		} else {
			if (this.column > rc.column) {
				return 1;
			} else if(this.column < rc.column) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
	public GridPoint clone() {
		GridPoint temp = new GridPoint(this.row, this.column);
		return temp;	
	}
	
	public String toString() {
		return ("(" + this.row + ", " + this.column + ")");
	}
	
	public GridPoint neg() {
		return new GridPoint(-row, -column);
	}
	
	public GridPoint plus(GridPoint delta) {
		return new GridPoint(this.row + delta.row, this.column + delta.column);
	}
	
	public GridPoint minus(GridPoint delta) {
		return new GridPoint(this.row - delta.row, this.column - delta.column);
	}
	
	public boolean equals (Object o) {
		if (o instanceof GridPoint) {
			GridPoint rc = (GridPoint) o;
			return (this.row == rc.getRow() && this.column == rc.getColumn());
		}
		else return false;
	}
	
	public boolean isManhattanColinearWith(Set<GridPoint> points) {
		Iterator<GridPoint> it = points.iterator();
		GridPoint tmp;
		while (it.hasNext()) {
			tmp = it.next();
			if (this.minus(tmp).getColumn() != 0 && this.minus(tmp).getRow() != 0) {
				return false;
			}
		}
		return true;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
}
