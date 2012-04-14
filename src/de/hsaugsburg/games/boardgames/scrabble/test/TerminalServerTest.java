package de.hsaugsburg.games.boardgames.scrabble.test;

import de.hsaugsburg.games.boardgames.scrabble.terminal.TerminalServer;

public class TerminalServerTest {
	
	public static void main(String[] args) {
		TerminalServer server = new TerminalServer();
//		server.sendMessage("hallo1", 1);
//		server.sendMessage("hallo2", 2);
//		server.sendMessage("hallo3", 3);
//		server.sendMessage("hallo4", 4);
//		
//		System.out.println(server.getInputLine(1, "$"));
//		System.out.println(server.getInputLine(2, "$"));
//		System.out.println(server.getInputLine(3, "$"));
//		System.out.println(server.getInputLine(4, "$"));
		
		for (int i = 1; i < 10; i++) {
			server.sendMessage("hallo" + i, 1);
			System.out.println(server.getInputLine(1, "$"));
		}
	}
	
}
