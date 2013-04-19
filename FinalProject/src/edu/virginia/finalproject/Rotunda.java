package edu.virginia.finalproject;

import sofia.util.Timer;

import sofia.app.Screen;

public class Rotunda extends Screen {
	public void initialize(Rotunda rotunda) {
		Timer.callOnce(this, "finish", 4000);
	}

	public void doneButton2Clicked() {
		Timer.callOnce(this, "finish", 0);
	}
}
