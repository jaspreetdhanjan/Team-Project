package com.basementstudios.client;

import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.basementstudios.network.PostRequest;

public class LoginController {
	private static final String URL_STRING = "http://tag.yarbsemaj.com/api/login/login.php";

	public static JSONObject login(String username, String password) {
		HashMap<String, String> arguments = new HashMap<>();
		arguments.put("User_Name", username);
		arguments.put("Password", password);

		JSONObject loginData = null;
		try {
			loginData = new PostRequest().send(URL_STRING, arguments);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		System.out.println(loginData.toString());
		return loginData;
	}
}