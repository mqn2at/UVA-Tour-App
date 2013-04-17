package edu.virginia.finalproject;

import sofia.util.Timer;

import sofia.app.Screen;

public class AFC extends Screen{
	public void initialize (AFC afc) {
		Timer.callOnce(this, "finish", 4000);
	}
	
	public void doneButtonClicked() {
		Timer.callOnce(this, "finish", 0);
	}
}
