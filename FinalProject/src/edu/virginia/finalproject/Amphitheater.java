package edu.virginia.finalproject;

import sofia.util.Timer;

import sofia.app.Screen;

public class Amphitheater extends Screen {
	public void initialize(Amphitheater amphitheater) {
		Timer.callOnce(this, "finish", 4000);
	}

	public void doneButton4Clicked() {
		Timer.callOnce(this, "finish", 0);
	}
}
