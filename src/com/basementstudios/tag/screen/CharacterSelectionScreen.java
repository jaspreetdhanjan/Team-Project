package com.basementstudios.tag.screen;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.*;

/**
 * State to load a character.
 * 
 * @author Jaspreet Dhanjan
 */

public class CharacterSelectionScreen extends Screen {
	private ActionInterface<String, Action> options = new ActionInterface<String, Action>("Select Characters");

	private CharacterData[] newCharacters = new CharacterData[3];
	private int pp = 0;

	public void init() {
		for (CharacterData data : GameController.availableCharacters) {
			String t = "Character: " + data.getName();
			options.add(t, new Action() {
				public void onClick() {
					if (pp >= 3) return;
					newCharacters[pp++] = data;
					options.remove(t);
				}
			});
		}

		options.add("Save", new Action() {
			public void onClick() {
				for (int i = 0; i < GameController.selectedCharacters.length; i++) {
					GameController.selectedCharacters[i] = newCharacters[i];
				}
				screenManager.toLastScreen();
				// TODO: Add this to a txt file.
			}
		});

		options.add("Quit", new Action() {
			public void onClick() {
				screenManager.toLastScreen();
			}
		});
	}

	public void tick(Input input) {
		options.tick(input);
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();
		options.render(bm);
	}

	public void renderHud(Bitmap bm) {
		bm.clear();
		for (int i = 0; i < newCharacters.length; i++) {
			CharacterData data = newCharacters[i];
			if (data == null) {
				bm.drawString("-Empty Slot-", 8, 8 + i * 16, 0x00);
				continue;
			}
			bm.drawString(data.getName(), 8, 8 + i * 16, 0xff0000);
		}
	}
}