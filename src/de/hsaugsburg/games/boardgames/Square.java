package de.hsaugsburg.games.boardgames;

import java.io.Serializable;


/**
 * Square is a generic base class that represents a field on a board game. 
 * It can store additional <code>details</code> i.e. for marking bonus fields
 * as it can store a <code>piece</code>.
 *
 * @author Marc Rochow, Anja Radtke
 * 
 * @param <P> type of the <code>piece</code> object
 * @param <D> type of the <code>details</code> object
 */
public class Square <P,D> implements Serializable {
	
	private static final long serialVersionUID = 7078835828719332974L;
	private P piece;
	private D details;
	
	/**
	 * Adds a piece to this square.
	 * @param piece the piece to be added.
	 */
	public void putPiece(P piece)  {
		this.piece = piece;
	}
	
	/**
	 * Returns the piece on this square.
	 * @return the piece object on this square.
	 */
	public P getPiece()  {
		return piece;
	}
	
	/**
	 * Removes the piece from this square.
	 * @return the piece object removed.
	 */
	public P removePiece()  {
		P piece = this.piece;
		this.piece = null;
		return piece;
	}
	
	/**
	 * Adds details to this field.
	 * @param details the details to be added.
	 */
	public void putDetails(D details) {
		this.details = details;
	}
	
	/**
	 * Gets the details on this square.
	 * @return the details object of this square.
	 */
	public D getDetails(){
		return details;
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * Also works if the data fields of this square have the values <code>null</code>.
	 * @return boolean value.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Square<?, ?>) {
			Square<?, ?> square = (Square<?, ?>) obj;
			if (piece == null && details != null) {
				return this.details.equals(square.details)
					&& this.piece == square.piece;
			}
			if (details == null && piece != null) {
				return this.details == square.details
					&& this.piece.equals(square.piece);
			}
			if (piece == null && details == null) {
				return this.details == square.details
					&& this.piece == square.piece;
			}
			return this.details.equals(square.details) && this.piece.equals(square.piece);
		}
		return false;
	}
	
}
