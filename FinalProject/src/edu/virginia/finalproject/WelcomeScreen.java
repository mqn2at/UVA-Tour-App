package edu.virginia.finalproject;


import sofia.app.Screen;
import sofia.util.Timer;


public class WelcomeScreen extends Screen{

	public void initialize(WelcomeScreen welcomeScreen) {
		Timer.callOnce(this, "finish", 4000);
	}
	
}
