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
			if (characterData.size() > 3)
				new Game(characterData);
			else
				JOptionPane.showMessageDialog(null,
						"You need at least 3 characters on your account in order to play", "Error",
						JOptionPane.ERROR_MESSAGE);
		} catch (InvalidTokenException e) {
			new LoginLauncher();
		}
	}
}