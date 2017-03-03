package com.basementstudios.client;

import com.basementstudios.network.*;
import com.basementstudios.tag.Game;

/**
 * Entry point for the app.
 * 
 * @author James Bray
 */

public class Run {
	public static void main(String[] args) {
		try {
			new Token();
			new Game();
		} catch (InvalidTokenException e) {
			new LoginLauncher();
		}
	}
}