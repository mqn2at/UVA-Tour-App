package edu.virginia.finalproject;

import sofia.util.Timer;

import sofia.app.Screen;

public class Clark extends Screen {
	public void initialize(Clark clark) {
		Timer.callOnce(this, "finish", 4000);
	}

	public void doneButton3Clicked() {
		Timer.callOnce(this, "finish", 0);
	}
}
