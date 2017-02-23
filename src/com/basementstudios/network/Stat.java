package com.basementstudios.network;

/**
 * Defines a character's stat.
 * 
 * @author James Bray
 */

public class Stat {
	private final int id;
	private int value;
	private String name;

	public Stat(int id, int value, String name) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}