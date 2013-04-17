package edu.virginia.finalproject;

import sofia.util.Timer;

import sofia.app.Screen;

public class Rice extends Screen {
	public void initialize(Rice rice) {
		Timer.callOnce(this, "finish", 4000);
	}

	public void button1Clicked() {
		Timer.callOnce(this, "finish", 0);
	}
}
