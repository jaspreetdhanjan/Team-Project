package com.basementstudios.client;

import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.basementstudios.network.PostRequest;

/**
 * Verifies login information with the server.
 * 
 * @author James Bray
 */

public class LoginController {
	private static final String URL_STRING = "http://tag.yarbsemaj.com/api/login/login.php";

	public static JSONObject login(String username, String password) {
		HashMap<String, String> arguments = new HashMap<String, String>();
		arguments.put("User_Name", username);
		arguments.put("Password", password);

		JSONObject loginData = null;
		try {
			loginData = new PostRequest().send(URL_STRING, arguments);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return loginData;
	}
}