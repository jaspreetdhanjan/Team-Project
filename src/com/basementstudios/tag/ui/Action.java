package com.basementstudios.tag.ui;

/**
 * Button interactions handled here.
 * 
 * @author Jaspreet Dhanjan
 */

public interface Action {
	public default void onHover() {
	}

	public default void onClick() {
	}

	public default void onLeft() {
	}

	public default void onRight() {
	}
}