package com.basementstudios.tag.util;

/**
 * A colour utility class to extract RGB values from hexadecimal colours or vice-versa.
 * 
 * @author Jaspreet Dhanjan
 */

public class Colour {
	public static int hex(int r, int g, int b) {
		return (r << 16 | g << 8 | b);
	}

	public static int[] rgb(int hex) {
		int r = (hex >> 16) & 0xff;
		int g = (hex >> 8) & 0xff;
		int b = (hex) & 0xff;
		return new int[] { r, g, b };
	}
}