package edu.virginia.finalproject;

import sofia.app.Screen;

public class Ohill extends Screen{
	public void initialize (Ohill ohill) {
		
	}
	
	public void doneButtonClicked() {
		presentScreen(MainScreen.class, new MainScreen());
	}
}
