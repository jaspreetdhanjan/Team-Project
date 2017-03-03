package com.basementstudios.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a character's item.
 * 
 * @author James Bray
 */

public class Item implements Serializable {

	private final int id;

	private int slotID, typeID;
	private String name, slot, type;

	private List<Stat> stats = new ArrayList<Stat>();

	public Item(int id, String name, int slotID, String slot, int typeID, String type) {
		this.id = id;
		this.name = name;
		this.slotID = slotID;
		this.slot = slot;
		this.typeID = typeID;
		this.type = type;
	}


	public void addStat(Stat stat){
		stats.add(stat);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getID() {
		return id;
	}

	public int getSlotID() {
		return slotID;
	}

	public void setSlotID(int slotID) {
		this.slotID = slotID;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public List<Stat> getStats() {
		return stats;
	}
}