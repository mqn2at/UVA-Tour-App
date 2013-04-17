package edu.virginia.finalproject;

import android.widget.TextView;

import sofia.app.Screen;
import sofia.util.Timer;

public class CongratsScreen extends Screen {
	private int time; 
	private TextView clock;
	
	public void initialize(CongratsScreen congratsScreen) {
		int hour = time / 3600;
		int min = time % 3600 / 60;
		int sec = time % 60;
		String h = hour + "";
		String m = min + "";
		String s = sec + "";
		if (h.length() < 2) {
			h = "0" + h;
		}
		if (m.length() < 2) {
			m = "0" + m;
		}
		if (s.length() < 2) {
			s = "0" + s;
		}
		clock.setText(h + ":" + m + ":" + s);
		Timer.callOnce(this, "finish", 4000);
	}
	public void setTime(int time){
		this.time = time;
	}
}
