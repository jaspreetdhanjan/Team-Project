package com.basementstudios.tag.phys;

/**
 * An Axis-Aligned Bounding Box used to detect overlapping items in the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class AxisAlignedBB {
	public double xPos, yPos;
	public double xSize, ySize;

	public AxisAlignedBB() {
	}
	
	public AxisAlignedBB(double xPos, double yPos, double xSize, double ySize) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public void set(double xPos, double yPos, double xSize, double ySize) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	
	public void set(AxisAlignedBB bb) {
		this.xPos = bb.xPos;
		this.yPos = bb.yPos;
		this.xSize = bb.xSize;
		this.ySize = bb.ySize;
	}

	public boolean contains(AxisAlignedBB bb) {
		if (bb.xPos < xPos || bb.yPos < yPos) return false;
		if (bb.xPos + bb.xSize > xPos + xSize || bb.yPos + bb.ySize > yPos + ySize) return false;
		return true;
	}

	public boolean overlaps(AxisAlignedBB bb) {
		if (bb.xPos + bb.xSize < xPos || bb.yPos + bb.ySize < yPos) return false;
		if (bb.xPos > xPos + xSize || bb.yPos > yPos + yPos) return false;
		return true;
	}
}