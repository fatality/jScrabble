package de.hsaugsburg.games.boardgames.scrabble.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleBoard;
import de.hsaugsburg.games.boardgames.scrabble.graphicalui.GLetterBar;
import de.hsaugsburg.games.boardgames.scrabble.graphicalui.GLetterPiece;
import de.hsaugsburg.games.boardgames.scrabble.graphicalui.GBoardView;

public class ScrabbleGuiTest extends JFrame implements DragGestureListener {
	
	private static final long serialVersionUID = -1646645392100796432L;

	public ScrabbleGuiTest() {
		add(BorderLayout.CENTER, new GBoardView(new ScrabbleBoard()));
		JPanel piecesHull = new JPanel();
		piecesHull.add(createLetterBar());
		add(BorderLayout.SOUTH, piecesHull);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private Container createLetterBar() {
		GLetterBar letterBar = new GLetterBar(null);
		List<LetterPiece> letters = new ArrayList<LetterPiece>();
		for (int j = 0; j < 7; j++) {
			letters.add(LetterPiece.values()[(int) (Math.random() * 28)]);
		}
		letterBar.setLetters(letters);
		
		return letterBar;
	}
	
	public static void main(String[] args) {
		new ScrabbleGuiTest();
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent event) {
		Cursor cursor = null;
	    GLetterPiece piece = (GLetterPiece) event.getComponent();
	    if (event.getDragAction() == DnDConstants.ACTION_MOVE) {
	      cursor = DragSource.DefaultMoveDrop;
	    }
	    event.startDrag(cursor, piece);
		
	}
	
}
