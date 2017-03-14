package com.basementstudios.tag.screen;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.ui.Action;
import com.basementstudios.tag.ui.ActionInterface;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * State to load a character.
 *
 * @author Jaspreet Dhanjan
 */

public class CharacterSelectionScreen extends Screen {
	private ActionInterface<CharacterData, Action> options = new ActionInterface<CharacterData, Action>("Select Characters");

    private ArrayList<CharacterData> newCharacters = new ArrayList<CharacterData>();
    private int pp = 0;

	public void init() {
        for (CharacterData data : Characters.getAvailable()) {
            options.add(data, new Action() {
				public void onClick() {
                    pp++;
                    newCharacters.add(data);
                    options.remove(data);
                    if (pp == 3) save();
                }
			});
        }
    }

    public void save() {
        GameController.selectedCharacters = new ArrayList<CharacterData>();
        for (int i = 0; i < newCharacters.size(); i++) {
            GameController.selectedCharacters.add(newCharacters.get(i));
        }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("doc/chara.ser", false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(GameController.selectedCharacters);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data");
        } catch (IOException i) {
            i.printStackTrace();
        }
        screenManager.toLastScreen();
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
		bm.drawString("<- Back (Esc)", 12, Game.VIEWPORT_HEIGHT - 16, 0xffffff);
	}

	public void renderHud(Bitmap bm) {
		bm.clear();
		int i = 0;
        for (; i < newCharacters.size(); i++) {
            CharacterData data = newCharacters.get(i);
            bm.render(Mob.getSpriteSheet(data.getType()).getSprites()[0][Mob.getOffset(data.getWeaponType(), data.getType())], 12 + i * 128, 0, 0xffffff);

			bm.drawString(data.getName(), 12 + i * 128, 128, 0xffffff);
		}

		for (; i < 3; i++) {
            bm.render(ResourceManager.i.necromancerSpriteSheet.getSprites()[0][0], 12 + i * 128, 0, 0x333333);
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

            String weponType;
            switch (player.getWeaponType()) {
				case CharacterData.NO_WEAPON:
                    weponType = "Paper Knife";
                    break;
				case CharacterData.MELEE_WEAPON:
                    weponType = "Melee";
                    break;
				case CharacterData.RANGED_WEAPON:
                    weponType = "Ranged";
                    break;
				case CharacterData.MAGIC_WEAPON:
                    weponType = "Magic";
                    break;
				default:
                    weponType = "Im not gonna ask";
                    break;
			}

            bm.drawString("Weapon Type " + weponType, xStart, yStart + 64, 0xffffff);
            if (player.getSpellDuration() != 0) {
				bm.drawString("Spell Duration " + player.getSpellDuration(), xStart, yStart + 80, 0xffffff);
			}
		}
	}
}