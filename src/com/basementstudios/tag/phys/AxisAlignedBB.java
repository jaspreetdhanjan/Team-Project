package com.basementstudios.tag.phys;

/**
 * An Axis-Aligned Bounding Box used to detect overlapping items in the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class AxisAlignedBB {
	public double x, y;
	public double xs, ys;

	public void set(double x, double y, double xs, double ys) {
		this.x = x;
		this.y = y;
		this.xs = xs;
		this.ys = ys;
	}

	public boolean overlaps(AxisAlignedBB bb) {
		return false;
	}

	public boolean overlaps(double x0, double y0, double x1, double y1) {
		return false;
	}
}