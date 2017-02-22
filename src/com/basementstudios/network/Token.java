package com.basementstudios.network;

import java.io.*;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Token {
	private static final String AUTH_URL = "http://tag.yarbsemaj.com/api/login/auth.php";

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
			tokenFile = new FileReader("token.txt");
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

	public void remove() {
		File tokenFile = new File("token.txt");
		tokenFile.delete();
	}

	public void add(String token) {
		FileWriter tokenFile;
		try {
			tokenFile = new FileWriter("token.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(tokenFile);
			bufferedWriter.write(token);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}