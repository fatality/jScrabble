package de.hsaugsburg.games.boardgames.scrabble;

import de.hsaugsburg.games.boardgames.IPiece;

public enum LetterPiece implements IPiece {
	
	//(Points, Pieces)
	A(1, 5),
	B(3, 2),
	C(4, 2),
	D(1, 4),
	E(1, 15),
	F(4, 2),
	G(2, 3),
	H(2, 4),
	I(1, 6),
	J(6, 1),
	K(4, 2),
	L(2, 3),
	M(3, 4),
	N(1, 9),
	O(2, 3),
	P(4, 1),
	Q(10, 1),
	R(1, 6),
	S(1, 7),
	T(1, 6),
	U(1, 6),
	V(4, 1),
	W(3, 1),
	X(8, 1),
	Y(10, 1),
	Z(3, 1),
	\u00c4(6, 1), //AE
	\u00d6(8, 1), //OE
	\u00dc(6, 1); //UE
	
	private int points;
	private int count;
	
	private LetterPiece(int points, int count) {
		this.points = points;
		this.count = count;
	}
	
	public int getPoints() {	
		return points;
	}
	
	public int getCount() {
		return count;
	}
	
}
