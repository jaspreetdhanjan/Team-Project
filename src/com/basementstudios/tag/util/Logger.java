package com.basementstudios.tag.util;

import java.util.*;

public class Logger {
	private static final List<String> logs = new ArrayList<String>();

	public static synchronized void log(String msg) {
		logs.add(msg);
		System.out.println(msg);
	}
}
