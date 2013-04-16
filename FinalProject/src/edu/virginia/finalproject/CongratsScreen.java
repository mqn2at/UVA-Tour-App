package edu.virginia.finalproject;

import android.widget.TextView;

import sofia.app.Screen;
import sofia.util.Timer;

public class CongratsScreen extends Screen {
	private String time; 
	private TextView clock;
	
	public void initialize(CongratsScreen congratsScreen) {
		clock.setText(time);
		Timer.callOnce(this, "finish", 4000);
	}
	public void setTime(String time){
		this.time = time;
	}
}
