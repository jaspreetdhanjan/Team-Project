package com.basementstudios.tag.screen;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.*;

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
    private ActionInterface<String, Action> options = new ActionInterface<String, Action>("Select Characters");

    private ArrayList<CharacterData> newCharacters = new ArrayList<CharacterData>();
    private int pp = 0;

    public void init() {
        for (CharacterData data : GameController.availableCharacters) {
            String t = "Character: " + data.getName();
            options.add(t, new Action() {
                public void onClick() {
                    if (pp >= 3) return;
                    newCharacters.add(data);
                    options.remove(t);
                }
            });
        }

        options.add("Save", new Action() {
            public void onClick() {
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
        for (int i = 0; i < newCharacters.size(); i++) {
            CharacterData data = newCharacters.get(i);
            //if (data == null) {
            //	bm.drawString("-Empty Slot-", 8, 8 + i * 16, 0xff0000);
            //	continue;
            //}
            bm.drawString(data.getName(), 8, 8 + i * 16, 0xff0000);
        }
    }
}