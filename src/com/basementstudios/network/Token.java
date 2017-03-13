package com.basementstudios.network;

import java.io.*;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * A login token.
 * 
 * @author James Bray
 * @author Jaspreet Dhanjan
 */

public class Token {
	private static final String AUTH_URL = "http://tag.yarbsemaj.com/api/login/auth.php";
	private static final String TOKEN_FILE = "doc/token.txt";

	/*private static File tokenFile = new File(TOKEN_FILE);

	private Token() {
	}
	
	public static void create() {
		try {
			tokenFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void put(String token) {
		try {
			new PrintWriter(tokenFile).close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void remove() {
		tokenFile.delete();
	}

	public static boolean hasToken() {
		return tokenFile.exists();
	}
	
	public static String getTokenString() throws InvalidTokenException {
		String tokenText = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(tokenFile));
			tokenText = bufferedReader.readLine();
			bufferedReader.close();
		} catch (IOException e) {
			throw new InvalidTokenException();
		}

		if (!isTokenValid(tokenText) || tokenText == null || tokenText.trim().isEmpty()) {
			throw new InvalidTokenException();
		}

		return tokenText;
	}

	private static boolean isTokenValid(String tokenText) {
		HashMap<String, String> arguments = new HashMap<>();
		arguments.put("Token", tokenText);
		JSONObject loginData = null;
		try {
			loginData = new PostRequest().send(AUTH_URL, arguments);
			return (boolean) loginData.get("success");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return false;
	}*/

	private FileReader tokenFile;
	private String tokenText;
	
	public Token() throws InvalidTokenException {
		update();
	}
	
	public Token(String token) {
		add(token);
	}
	
	private boolean isValid() {
		HashMap<String, String> arguments = new HashMap<>();
		arguments.put("Token", tokenText);
		JSONObject loginData = null;
		try {
			loginData = new PostRequest().send(AUTH_URL, arguments);
			return (boolean) loginData.get("success");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void update() throws InvalidTokenException {
		try {
			tokenFile = new FileReader(TOKEN_FILE);
			BufferedReader bufferedReader = new BufferedReader(tokenFile);
			tokenText = bufferedReader.readLine();
			bufferedReader.close();
			if (!isValid()) {
				throw new InvalidTokenException();
			}
		} catch (IOException e) {
			throw new InvalidTokenException();
		}
	}
	
	public String getToken() throws InvalidTokenException {
		return tokenText;
	}
	
	public static void remove() {
		File tokenFile = new File(TOKEN_FILE);
		tokenFile.delete();
	}
	
	public void add(String token) {
		FileWriter tokenFile;
		try {
			tokenFile = new FileWriter(TOKEN_FILE);
			BufferedWriter bufferedWriter = new BufferedWriter(tokenFile);
			bufferedWriter.write(token);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}