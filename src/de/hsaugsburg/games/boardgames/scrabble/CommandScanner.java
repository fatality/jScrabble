package de.hsaugsburg.games.boardgames.scrabble;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import de.hsaugsburg.games.boardgames.ICommand;
import de.hsaugsburg.games.boardgames.ICommandScanner;
import de.hsaugsburg.games.boardgames.exceptions.GameException;
import de.hsaugsburg.games.boardgames.exceptions.UnknownCommandException;
import de.hsaugsburg.games.boardgames.scrabble.terminal.TerminalServer;

public class CommandScanner implements ICommandScanner {
	
	private static final long serialVersionUID = -4156056603868156550L;
	private String[] params;
	private ICommand[] commands;
	private ICommand command;
	private TerminalServer server;
	private boolean terminal;
	
	public CommandScanner(ICommand[] commands) {
		this(commands, false);
		this.server = new TerminalServer();
	}
	
	public CommandScanner(ICommand[] commands, boolean terminal) {
		this.commands = commands;
		this.server = new TerminalServer();
		this.terminal = terminal;
	}
	
	public Object next() throws GameException {
		return next(-1); // Dummy value.
	}
	
	public boolean isTerminal() {
		return terminal;
	}
	
	public Object next(int id) throws GameException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String string;
			if(!terminal || id == -1) {
				string = reader.readLine();
			} else {
				string = server.getInputLine(id, "$");
			}
			params = string.split("[ ]+");
			command = null;
			for (int i = 0; i < commands.length; i++) {
				if (commands[i].getToken().equals(params[0])) {
					command = commands[i];
					break;
				}
			}
			
			if (params.length-1 < command.getParamTypes().length && command.hasOptionalParams()) {
				return command;
			}
			
			for (int j = 0; j < command.getParamTypes().length; j++) {
				if(command.getParamTypes()[j] == int.class) {
					command.getParams()[j] = Integer.parseInt(params[j+1])-1;
				} else if(command.getParamTypes()[j] == char.class) {
					Character c = params[j+1].toUpperCase().charAt(0);
					command.getParams()[j] = (int)(c-'A');
				} else if(command.getParamTypes()[j] == LetterPiece.class) {
					command.getParams()[j] = LetterPiece.valueOf(params[j+1].toUpperCase());
				} else if(command.getParamTypes()[j] == String.class) {
					command.getParams()[j] = params[j+1].toUpperCase();
				}
			}
			
		} catch(Exception ex) {
			StringBuffer sb = new StringBuffer();
			if (command != null)  {
				sb.append(command.getHelpText());
			}
			throw new UnknownCommandException("Unknown command: " + params[0] + " " + sb);
		}
		return command;
	}
	
}
