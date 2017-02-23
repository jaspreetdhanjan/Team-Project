package com.basementstudios.tag.screen;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.*;

public class CharacterSelectionScreen extends Screen {
	private ActionInterface<String, Action> options;

	private CharacterData[] newCharacters = new CharacterData[3];
	private int pp = 0;

	public void init() {
		options = new ActionInterface<String, Action>("Select Characters");

		for (CharacterData data : PlayerController.availableCharacters) {
			String t = "Character: " + data.getName();
			options.add(t, new Action() {
				public void onHovered() {
				}

				public void onClick() {
					newCharacters[pp++] = data;
					options.remove(t);
				}
			});
		}

		options.add("Save", new Action() {
			public void onHovered() {
			}

			public void onClick() {
				for (int i = 0; i < PlayerController.selectedCharacters.length; i++) {
					PlayerController.selectedCharacters[i] = newCharacters[i];
				}
				screenManager.toLastScreen();
				// TODO: Add this to a txt file.
			}
		});
		
		options.add("Quit", new Action() {
			public void onHovered() {
			}
			
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
		for (int i = 0; i < newCharacters.length; i++) {
			CharacterData data = newCharacters[i];
			if (data == null) {
				bm.drawString("-Empty Slot-", 8, 8+ i * 16, 0x00);
				continue;
			}
			bm.drawString(data.getName(), 8, 8+i * 16, 0xff0000);
		}
	}
}