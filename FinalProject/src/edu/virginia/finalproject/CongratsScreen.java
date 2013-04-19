package edu.virginia.finalproject;

import android.widget.TextView;

import sofia.app.Screen;
import sofia.util.Timer;

public class CongratsScreen extends Screen {
	private TextView clock;

	public void initialize(String time) {
		clock.setText(time);
		Timer.callOnce(this, "finish", 4000);
	}
}
