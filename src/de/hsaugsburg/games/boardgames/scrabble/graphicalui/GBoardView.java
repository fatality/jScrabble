package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.JPanel;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleBoard;

public class GBoardView extends JPanel {
	
	private static final long serialVersionUID = 880738091454614694L;
	private ScrabbleBoard board;
	
	public GBoardView(ScrabbleBoard board) {
		this.board = board;
	}
	
	public void setBoard(ScrabbleBoard board) {
		this.board = board;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(450, 450);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (board == null) {
			return;
		}
		super.paintComponent(g);

		int aWidth = this.getWidth()/board.getWidth();
		int aHeight = this.getHeight()/board.getHeight();
		int dy = -1;	
		int spacey = this.getHeight()%board.getHeight();
		
		for (int row = 0; row < board.getWidth(); row++) {		
			int dx = -1;
			int height = aHeight;
			int spacex = this.getWidth()%board.getWidth();
			
			if (0 <= spacey--) {
				height++; dy++;
			}
			
			for (int column = 0; column < board.getHeight(); column++) {
				int width = aWidth;
				
				if (0 <= spacex--) {
					width++; dx++;
				}
				
				int x = column*aWidth + dx;
				int y = row*aHeight + dy;
				
				if (board.getPiece(row, column) != null) {		
					if (board.getDetails(row, column).isFixed()) {
						g.setColor(Color.DARK_GRAY);
					} else {
						g.setColor(Color.GRAY);
					}
					g.fill3DRect(x, y, width, height, true);
					g.setColor(Color.WHITE);
					g.setFont(new Font("Tahoma", Font.BOLD, (int)(height*0.4)));
					g.drawString(board.getPiece(row, column).name(), x+width/3 , y+height/2 );
					g.setFont(new Font("Tahoma", Font.PLAIN, (int)(height*0.3)));
					g.drawString(Integer.toString(board.getPiece(row, column).getPoints()), x+(width/3)*2, y+(height/3)*2);
				} else if (row == 7 && column == 7) {
					g.setColor(new Color(150, 150, 10));
					g.fill3DRect(x, y, width, height, true);
				} else if (board.getDetails(row, column).getWordMultiplier() == 2) {
					g.setColor(new Color(150, 120, 70));
					g.fill3DRect(x, y, width, height, true);
				} else if (board.getDetails(row, column).getWordMultiplier() == 3) {
					g.setColor(new Color(150, 70, 50));
					g.fill3DRect(x, y, width, height, true);
				} else if (board.getDetails(row, column).getLetterMultiplier() == 1) {
					g.setColor(new Color(80, 100, 80));
					g.fill3DRect(x, y, width, height, true);
				} else if (board.getDetails(row, column).getLetterMultiplier() == 2) {
					g.setColor(new Color(40, 80, 150));
					g.fill3DRect(x, y, width, height, true);
				} else if (board.getDetails(row, column).getLetterMultiplier() == 3) {
					g.setColor(new Color(40, 120, 150));
					g.fill3DRect(x, y, width, height, true);
				}
			}		
		}
	}
	
}
