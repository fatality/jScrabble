package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JComponent;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;

public class GLetterPiece extends JComponent implements Transferable {
	
	private static final long serialVersionUID = 2761772336322153951L;
	public static final DataFlavor FLAVOR = new DataFlavor(GLetterPiece.class, "DLetterPiece");
	public static final DataFlavor[] FLAVORS = new DataFlavor[]{ FLAVOR };
	private LetterPiece piece;
	private Controller controller;
	private boolean colorFixed;
	public static final Color DEFAULT_COLOR = Color.DARK_GRAY;
	public static final Color MOUSEOVER_COLOR = Color.ORANGE;
	private Color color = DEFAULT_COLOR;
	
	public GLetterPiece(LetterPiece piece, Controller controller) {
		this.controller = controller;
		this.piece = piece;
		colorFixed = false;
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				if (colorFixed) {
					return;
				}
				setColor(MOUSEOVER_COLOR);
				GLetterPiece.this.repaint();			
			}
			
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (colorFixed) {
					return;
				}
				setColor(DEFAULT_COLOR);
				GLetterPiece.this.repaint();	
			}
			
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getButton() == MouseEvent.BUTTON1 && !isColorFixed() && GLetterPiece.this.controller.getPiece() == null) {
					setColor(MOUSEOVER_COLOR);
					GLetterPiece.this.controller.setPiece(GLetterPiece.this);
					setColorFixed(true);
					GLetterPiece.this.repaint();
					return;
				}
				if (e.getButton() == MouseEvent.BUTTON1 && GLetterPiece.this.controller.getPiece().equals(GLetterPiece.this)) {
					GLetterPiece.this.controller.setPiece(null);
					setColorFixed(false);
					setColor(DEFAULT_COLOR);
					GLetterPiece.this.repaint();
				}
			}
			
		});
	}
	
	public LetterPiece getPiece() {
		return piece;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isColorFixed() {
		return colorFixed;
	}

	public void setColorFixed(boolean colorFixed) {
		this.colorFixed = colorFixed;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(30 , 30);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fill3DRect(0, 0, this.getWidth(), this.getHeight(), true);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Tahoma", Font.BOLD, (int)(this.getHeight()*0.4)));
		g.drawString(piece.name(), this.getWidth()/3 , this.getHeight()/2 );
		g.setFont(new Font("Tahoma", Font.PLAIN, (int)(this.getHeight()*0.3)));
		g.drawString(Integer.toString(piece.getPoints()), (this.getWidth()/3)*2, (this.getHeight()/3)*2);

	}

	@Override
	public Object getTransferData(DataFlavor df)
			throws UnsupportedFlavorException, IOException {
		if(!df.equals(FLAVOR)) {
			throw new UnsupportedFlavorException(df);
		}
		return this;
	}
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return FLAVORS;
	}
	
	@Override
	public boolean isDataFlavorSupported(DataFlavor df) {
		return df.equals(FLAVOR);
	}
	
}
