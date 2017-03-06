package com.basementstudios.tag.screen;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.*;

/**
 * State to load a character.
 *
 * @author Jaspreet Dhanjan
 */

public class CharacterSelectionScreen extends Screen {
	private ActionInterface<CharacterData, Action> options = new ActionInterface<CharacterData, Action>("Select Characters");
	private CharacterData[] newCharacters = new CharacterData[3];
	
	private int pp = 0;

	public void init() {
/*		for (CharacterData data : GameController.availableCharacters) {
			options.add(data, new Action() {
				public void onClick() {
					newCharacters[pp++] = data;
					options.remove(data);
					// if (pp == 3) save();
				}
			});
		}*/
	}

	public void tick(Input input) {
		options.tick(input);

		if (input.esc.isClicked()) {
			screenManager.toLastScreen();
		}
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();
		options.render(bm);
		// bm.drawString("Select three Chricters to take into battle.", 12, Game.VIEWPORT_HEIGHT-32, 0xffffff);
		bm.drawString("<- Back (Esc)", 12, Game.VIEWPORT_HEIGHT - 16, 0xffffff);
	}

	public void renderHud(Bitmap bm) {
		bm.clear();

		Bitmap sprite = ResourceManager.i.playerSpriteSheet.getSprites()[0][0];

		int i = 0;
		for (; i < newCharacters.length; i++) {
			CharacterData data = newCharacters[i];
			bm.render(sprite, 12 + i * 128, 0, 0xffffff);
			bm.drawString(data.getName(), 12 + i * 128, 128, 0xffffff);
		}

		for (; i < 3; i++) {
			bm.render(sprite, 12 + i * 128, 0, 0x333333);
			bm.drawString("Empty Slot", 12 + i * 128, 128, 0x7c7c7c);
		}
		CharacterData characterData = options.getSelected();
		mobHud(bm, characterData, 410, 0);
	}

	private void mobHud(Bitmap bm, CharacterData player, int xStart, int yStart) {
		if (player != null) {
			bm.drawString("Name: " + player.getName(), xStart, yStart, 0xffffff);
			bm.drawString("Damage " + player.getDmg(), xStart, yStart + 16, 0xffffff);
			bm.drawString("Defence " + player.getDef(), xStart, yStart + 32, 0xffffff);
			bm.drawString("Speed " + player.getSpd(), xStart, yStart + 48, 0xffffff);

			String weaponType;
			switch (player.getWeaponType()) {
				case CharacterData.NO_WEAPON:
					weaponType = "Fists";
					break;
				case CharacterData.MELEE_WEAPON:
					weaponType = "Melle";
					break;
				case CharacterData.RANGED_WEAPON:
					weaponType = "Ranged";
					break;
				case CharacterData.MAGIC_WEAPON:
					weaponType = "Magic";
					break;
				default:
					weaponType = "Im not gonna ask";
					break;
			}

			bm.drawString("Weapon Type " + weaponType, xStart, yStart + 64, 0xffffff);
			if (player.getSpellDuration() != 0) {
				bm.drawString("Spell Duration " + player.getSpellDuration(), xStart, yStart + 80, 0xffffff);
			}
		}
	}
}