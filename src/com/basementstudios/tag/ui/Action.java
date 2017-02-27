package com.basementstudios.tag.ui;

public interface Action {
	default public void onClick(){};
	default void onLeft(){};
	default void onRight(){};
}