package com.basementstudios.tag;

import java.util.*;

import com.basementstudios.network.CharacterData;

public class Characters {
	private static final List<CharacterData> availableCharacters = new ArrayList<CharacterData>();

	public static void setAvailable(List<CharacterData> characters) {
		availableCharacters.addAll(characters);
	}

	public static List<CharacterData> getAvailable() {
		return availableCharacters;
	}
}