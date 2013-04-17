package edu.virginia.finalproject;

import sofia.util.Timer;

import sofia.app.Screen;

public class Ohill extends Screen {
	public void initialize(Ohill ohill) {
		Timer.callOnce(this, "finish", 4000);
	}

	public void doneButtonClicked() {
		Timer.callOnce(this, "finish", 0);
	}
}
