package edu.virginia.finalproject;

import sofia.app.Screen;

public class AFC extends Screen{
	public void initialize (AFC afc) {
		
	}
	
	public void doneButtonClicked() {
		presentScreen(MainScreen.class, new MainScreen());
	}
}
