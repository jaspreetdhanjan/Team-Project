package com.basementstudios.tag.phys;

/**
 * An Axis-Aligned Bounding Box used to detect overlapping items in the game.
 * 
 * @author Jaspreet Dhanjan
 */

// TODO: Needs to be completed!
public class AxisAlignedBB {
	public double xPos, yPos;
	public double xSize, ySize;

	public void set(double xPos, double yPos, double xSize, double ySize) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
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