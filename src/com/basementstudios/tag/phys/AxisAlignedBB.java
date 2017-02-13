package com.basementstudios.tag.phys;

/**
 * An Axis-Aligned Bounding Box used to detect overlapping items in the game.
 * 
 * @author Jaspreet Dhanjan
 */

// TODO: Needs to be completed!
public class AxisAlignedBB {
	public double x, y;
	public double xSize, ySize;

	public void set(double x, double y, double xSize, double ySize) {
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public boolean overlaps(AxisAlignedBB bb) {
		return false;
	}

	public boolean contains(double x0, double y0, double x1, double y1) {
		return false;
	}
}