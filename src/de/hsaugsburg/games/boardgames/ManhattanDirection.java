package de.hsaugsburg.games.boardgames;

import java.io.Serializable;

/**
 * Represents a mathematical vector by storing a {@link GridPoint}
 * that equals a direction. The {@link GridPoint} can be received
 * by accessing a direction of this <code>enum</code> type.
 * 
 * @author Marc Rochow, Anja Radtke
 */
public enum ManhattanDirection implements Serializable {
	
	DOWN(1, 0) {
		public ManhattanDirection orthogonalDir() {
			return LEFT;
		}
	},
	UP(-1, 0) {
		public ManhattanDirection orthogonalDir() {
			return RIGHT;
		}
	},
	RIGHT(0, 1) {
		public ManhattanDirection orthogonalDir() {
			return DOWN;
		}
	},
	LEFT(0, -1) {
		public ManhattanDirection orthogonalDir() {
			return UP;
		}
	};
	
	public GridPoint gp;
	
	/**
	 * Constructs a new {@link GridPoint} for this direction.
	 * @param row vector direction parameter should be of value |1| or 0.
	 * @param column vector direction parameter should be of value |1| or 0.
	 */
	private ManhattanDirection(int row, int column) {
		gp = new GridPoint(row, column);
	}
	
	/**
	 * Calculates the direction. The Manhattan direction can only
	 * be "RIGHT" if the points are in the same row and only "DOWN"
	 * if the points are in the same column.
	 * 
	 * @param a first {@link GridPoint}
	 * @param b second {@link GridPoint}
	 * @return the Manhattan direction of the two points.
	 */
	public static ManhattanDirection calcDir(GridPoint a, GridPoint b) {
		if (a.minus(b).getColumn() == 0) {
			return DOWN;
		} else if (a.minus(b).getRow() == 0) {
			return RIGHT;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a direction, orthogonal to this direction
	 * in a clockwise order. The actual method is empty
	 * since it is overrided by each <code>enum</code> value. 
	 * 
	 * @return next Manhattan direction.
	 */
	public ManhattanDirection orthogonalDir() {
		return null;
	}
	
}
