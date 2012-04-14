package de.hsaugsburg.games.boardgames.connectfour;

import java.util.Iterator;
import de.hsaugsburg.games.boardgames.*;
import de.hsaugsburg.games.boardgames.connectfour.exceptions.ColumnFullException;
import de.hsaugsburg.games.boardgames.connectfour.exceptions.SwitchNotAllowedException;
import de.hsaugsburg.games.boardgames.exceptions.OutsideBoardException;

/**
 * @author Marc Rochow, Anja Radtke
 */
public class ConnectFourBoard extends Board<BinaryPiece, Object> {
	
	/**
	 * @param row		Row position of the last piece that was set.
	 * @param column 	Column position of the last piece that was set.
	 */
	
	private static final long serialVersionUID = -8693819979936325758L;
	public BinaryPiece piece;
	private int row;
	private int column;

	public BinaryPiece getPiece() {
		return piece;
	}

	public void setPiece(BinaryPiece piece) {
		this.piece = piece;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getRow(){
		return this.row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * <code>public ConnectFourBoard()</code>
	 * loads the board with a standard board size of 6 x 7.
	 */ 
	public ConnectFourBoard() {
		this(6, 7);
	}
	
	/**
	 * <code>public ConnectFourBoard(int rows, int columns)</code> 
	 * allows an individual board size.
	 */
	public ConnectFourBoard(int rows, int columns) {
		super(rows, columns);
	}
	
	/**
	 * This method tries to put a piece at the first free square on the connect4 board counting from the bottom
	 * in the specified column.
	 * @param piece requires an object of the enumerator type <code>Piece</code>.
	 * @param column a integer value which is in range of the board width.
	 * @throws OutsideBoardException thrown from lower in the hierarchy.
	 * @throws ColumnFullException	if there is no free square in the specified column.
	 */
	public void drop(int column) throws OutsideBoardException, ColumnFullException {
		this.row = getRowForColumn(this.column = column);
		putPiece(piece, row, column);
	}
	
	/**
	 * This Method gets the row where the piece has to be dropped
	 * @param column
	 * @return
	 * @throws OutsideBoardException
	 * @throws ColumnFullException
	 */
	public int getRowForColumn(int column) throws OutsideBoardException, ColumnFullException{
		for (int row = getHeight()-1; row >= 0; row--) {
			if (isEmpty(row, column)){
				return row;
			} 
		}
		throw new ColumnFullException("Column full!");
	}

	/**
	 * @param piece	pieces of this kind will be counted.
	 * @param i	row position of the last piece that was set plus vector <b>i</b>.
	 * @param j	column position of the last piece that was set plus vector <b>j</b>.
	 * @return	integer value of the number of pieces that have been found.
	 */
	public int countEqualPiecesInOneDirection(BinaryPiece piece, int i, int j) {
		int numberOfEqualPieces = 0;

		Iterator<GridPoint> it = iterator(new GridPoint(row, column), new GridPoint(i, j), false);
		
		while (it.hasNext()) {
			GridPoint gp = it.next();
			if (getPiece(gp) != null && getPiece(gp).equals(getPiece(row, column))) {
				numberOfEqualPieces++;
			} else {
				break;
			}
		}
		return numberOfEqualPieces;
	}
	
	/**
	 * Please read the description of <code>nextDropAllowed()</code> to understand what this method does.
	 * <code>nextDropAllowed()</code> is contained in <code>de.hsaugsburg.games.boardgames.connectfour.ConnectFourEngine</code>.
	 * @param i is the first coordinate of a vector
	 * @param j	is the second coordinate of a vector
	 * @return	<code>true</code> if the last dropped piece is in a row of four equal pieces 
	 * @throws BoardNotInitializedException catches a system message from lower in the hierarchy
	 * and converts is to a user friendly message: "Please start a new game first!".
	 */
	public boolean countEqualPiecesInOffDirections(BinaryPiece piece, int i, int j) {
		if ((countEqualPiecesInOneDirection(piece, i, j) + countEqualPiecesInOneDirection(piece, -i, -j)) > 2) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks if one of the players has four in a row and hence won the game.
	 * To do this it calls the <code>countEqualPiecesInOffDirections()</code> method which checks
	 * opposite directions and calls <code>countEqualPiecesInOneDirection()</code> in turn to do so.
	 * The method can be found in <code>de.hsaugsburg.games.boardgames.connectfour.ConnectFourEngine</code>.
	 * 
	 * @return	<code>true</code> if the last dropped piece is in a row of four equal pieces 
	 * @throws BoardNotInitializedException	is thrown from a class lower in the hierarchy
	 */
	public boolean nextDropAllowed(BinaryPiece piece) {
		if (countEqualPiecesInOffDirections(piece, 1, 0) ||
			countEqualPiecesInOffDirections(piece, 0, 1) ||
			countEqualPiecesInOffDirections(piece, 1, 1) ||
			countEqualPiecesInOffDirections(piece, 1, -1)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Switch Piece is now completely defined in this class and not in the super-class!
	 * 
	 * @throws SwitchNotAllowedException		If the game is already finished a Exception is thrown if you try to add another piece to the board.
	 * @throws BoardNotInitializedException 
	 * @throws BoardNotInitializedException		is thrown from a class lower in the hierarchy
	 */
	public void switchPiece() throws SwitchNotAllowedException  {
		if (!nextDropAllowed(getPiece(row, column))) {
			throw new SwitchNotAllowedException();
		}
		switch(piece) {
		case X:
			piece = BinaryPiece.O;
			break;
		case O:
			piece = BinaryPiece.X;
			break;
		}
	}
}
