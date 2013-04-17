package edu.virginia.finalproject;

import sofia.util.Timer;

import sofia.app.Screen;

public class Gilmer extends Screen {
	public void initialize(Gilmer gilmer) {
		Timer.callOnce(this, "finish", 4000);
	}

	public void doneButton2Clicked() {
		Timer.callOnce(this, "finish", 0);
	}
}
