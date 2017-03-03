package com.basementstudios.tag;

import java.io.*;
import java.util.List;

import com.basementstudios.network.*;

public class JIO {
	public static void load(String path) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			
			for (CharacterData characterData : (List<CharacterData>) ois.readObject()) {
				GameController.selectedCharacters.add(new CharacterRetriever().getUpdateData(characterData));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}