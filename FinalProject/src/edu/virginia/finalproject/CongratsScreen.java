package edu.virginia.finalproject;

import android.widget.TextView;

import sofia.app.Screen;
import sofia.util.Timer;

public class CongratsScreen extends Screen {
	private TextView clock;

	//shows screen for a bit, then exits
	public void initialize(String time) {
		clock.setText(time);
		Timer.callOnce(this, "finish", 4000);
	}
}
