package com.basementstudios.tag.util;

import com.basementstudios.tag.screen.LoadingScreen;

import java.util.ArrayList;
import java.util.List;

public class Logger {
	private static final List<String> logs = new ArrayList<String>();

	public static synchronized void log(String msg) {
		logs.add(msg);
		LoadingScreen.status = msg;
		System.out.println(msg);
	}
}
