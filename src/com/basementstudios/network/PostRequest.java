package com.basementstudios.network;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PostRequest {
	public JSONObject send(String urlString, Map<String, String> arguments) throws IOException, ParseException {
		URL url = new URL(urlString);
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection) con;

		http.setRequestMethod("POST");
		http.setDoOutput(true);

		StringJoiner sj = new StringJoiner("&");

		for (Map.Entry<String, String> entry : arguments.entrySet()) {
			sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
		}
		byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
		int length = out.length;

		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		http.connect();

		try (OutputStream os = http.getOutputStream()) {
			os.write(out);
		}

		JSONParser parser = new JSONParser();
		String s = IOUtils.toString(http.getInputStream(), "UTF-8");
		JSONObject obj = (JSONObject) parser.parse(s);
		return obj;
	}
}