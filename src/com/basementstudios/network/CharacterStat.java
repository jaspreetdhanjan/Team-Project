package com.basementstudios.network;

public class CharacterStat {
	private int value, id;
	private String name;
	
	public CharacterStat(int id, int value, String name){
		this.id=id;
		this.name=name;
		this.value=value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
