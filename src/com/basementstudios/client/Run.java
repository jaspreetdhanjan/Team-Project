package com.basementstudios.client;

import java.util.List;

import javax.swing.JOptionPane;

import com.basementstudios.network.*;
import com.basementstudios.network.CharacterData;
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
			List<CharacterData> characterData = new CharacterRetriever().getCharacters();
				new Game();
		} catch (InvalidTokenException e) {
			new LoginLauncher();
		}
	}
}