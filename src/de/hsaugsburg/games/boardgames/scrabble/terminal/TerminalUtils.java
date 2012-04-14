package de.hsaugsburg.games.boardgames.scrabble.terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class TerminalUtils {
	
	private static final String MSG_DIR = "terminal/public/messages/";
	public static final String SERVER_MSG = MSG_DIR + "servermsg.";
	public static final String SERVER_OBJ = MSG_DIR + "serverobj.";
	public static final String INPUT_REQUEST = MSG_DIR + "inputreq.";
	public static final String INPUT_RESPONSE = MSG_DIR + "inputres.";
	
	public static void writeObject(File file, Object obj) {
		try {
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
			stream.writeObject(obj);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static Object readObject(File file) {
		ObjectInputStream stream;
		Object obj = null;
		try {
			stream = new ObjectInputStream(new FileInputStream(file));
			obj = stream.readObject();
			stream.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return obj;
	}
	
	
	public static void writeFile(File file, String msg) {
		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(file), true);
			writer.print(msg);
			writer.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static String readFile(File file) {
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int count = 0;
			while (!reader.ready() && count < 4) {
				count++;
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					
				}
			}
			while (true) {
				reader.mark(1);
				if (reader.read() == -1) {
					break;
				}
				reader.reset();
				buffer.append((char)reader.read());
			}
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return buffer.toString();
	}
	
}
