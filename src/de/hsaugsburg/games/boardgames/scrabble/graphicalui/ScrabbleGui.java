package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import de.hsaugsburg.games.boardgames.scrabble.IScrabbleEngine;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;

public class ScrabbleGui {
	
	private JFrame frame;
	private GBoardView boardArea;
	private GLetterBar letterBar;
	private JLabel messageLabel;
	private Controller controller;
	
	public ScrabbleGui(String title, IScrabbleEngine engine) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex){
			messageLabel.setText(ex.getMessage());
		}
		controller = new Controller(engine, this);
		frame = new JFrame(title);
		frame.addWindowListener(new ExitListener(controller));
		boardArea = new GBoardView(engine.getBoard());
		boardArea.addMouseListener(new AddPieceListener(controller));
		frame.add(BorderLayout.SOUTH, createControlPanel());
		frame.add(BorderLayout.CENTER, boardArea);
		frame.add(BorderLayout.NORTH, createMenuBar());
		frame.setLocation(200, 200);
		frame.pack();
		frame.setVisible(true);
		displayMessage("Hallo Welt!");
	}
	
	protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // create file menu with submenues and menu items
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem menuItem = new JMenuItem("Newgame");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new NewgameListener(controller));
        fileMenu.add(menuItem);

        fileMenu.addSeparator();
        fileMenu.add(menuItem = new JMenuItem("Exit"));
        menuItem.addActionListener(new ExitListener(controller));

        menuBar.add(createLfMenu());

        menuBar.add(new JMenu("Help"));

        return menuBar;
    }
	
	private JMenu createLfMenu() {
        JMenu lfMenu = new JMenu("L&F");

        final JMenuItem metalItem = lfMenu.add("Metal");
        final JMenuItem nimbusItem = lfMenu.add("Nimbus");
        final JMenuItem systemItem = lfMenu.add("System");
        
        ActionListener lfActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (e.getSource().equals(metalItem)) {
                        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    } else if (e.getSource().equals(nimbusItem)) {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					} else if (e.getSource().equals(systemItem)) {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					}
                } catch (Exception ex) {
                    displayMessage(ex.toString());
                }
                SwingUtilities.updateComponentTreeUI(frame);
                frame.pack();
            }
        };

        metalItem.addActionListener(lfActionListener);
        nimbusItem.addActionListener(lfActionListener);
        systemItem.addActionListener(lfActionListener);
        return lfMenu;
    }
	
	protected JPanel createControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		JPanel labelHull = new JPanel();
		labelHull.add(messageLabel = new JLabel());
		JPanel letterBarHull = new JPanel();
		letterBarHull.setBorder(BorderFactory.createRaisedBevelBorder());
		letterBarHull.add(createLetterBar());
		controlPanel.add(BorderLayout.CENTER, letterBarHull);
		controlPanel.add(BorderLayout.SOUTH, labelHull);
		controlPanel.add(BorderLayout.EAST, createControlButtons());
		return controlPanel;
	}
	
	protected JPanel createControlButtons() {
		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(2 , 2));
		JButton button = new JButton("commit");
		button.addActionListener(new CommandListener(controller));
		controls.add(button);
		button = new JButton("agree");
		button.addActionListener(new CommandListener(controller));
		controls.add(button);
		button = new JButton("reject");
		button.addActionListener(new CommandListener(controller));
		controls.add(button);
		button = new JButton("pass");
		button.addActionListener(new CommandListener(controller));
		controls.add(button);
		return controls;
	}
	
	private GLetterBar createLetterBar() {
		letterBar = new GLetterBar(controller);
		FlowLayout barLayout = new FlowLayout();
		barLayout.setHgap(1);
		letterBar.setLayout(barLayout);
		return letterBar;
	}
	
	public GLetterBar getLetterBar() {
		return letterBar;
	}
	
	public GBoardView getBoardArea() {
		return boardArea;
	}
	
	public void repaintBoardArea() {
        boardArea.repaint();
    }
	
	public void repaintLetterBar() {
		letterBar.repaint();
	}
	
	public void displayMessage(String msg) {
        messageLabel.setText(msg);
    }
	
}
