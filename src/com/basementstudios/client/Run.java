package com.basementstudios.client;

import com.basementstudios.network.*;

public class Run {
	public static void main(String[] args) {
		try {
			new Token();
			new CharacterSelect();
		} catch (InvalidTokenException e) {
			new Launcher();
		}
	}
}