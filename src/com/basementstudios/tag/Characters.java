package com.basementstudios.tag;

import com.basementstudios.network.CharacterData;
import com.basementstudios.network.CharacterLoader;
import com.basementstudios.tag.util.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Characters {
	private static final List<CharacterData> availableCharacters = new ArrayList<CharacterData>();

	public static List<CharacterData> getAvailable() {
		return availableCharacters;
	}

    public static void setAvailable(List<CharacterData> characters) {
        availableCharacters.addAll(characters);
    }

    public static void load(String fileName) {
        ObjectInputStream ois = null;
        try {
            FileInputStream fin = new FileInputStream(fileName);
            ois = new ObjectInputStream(fin);
            Logger.log("Loading ->  characters from file");
            for (CharacterData characterData : (ArrayList<CharacterData>) ois.readObject()) {
                GameController.selectedCharacters.add(new CharacterLoader().getUpdateData(characterData));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}