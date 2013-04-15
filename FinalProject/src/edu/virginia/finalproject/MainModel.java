package edu.virginia.finalproject;

public class MainModel extends sofia.util.Observable {
	private double currLat;
	private double currLong;
	private double destLat;
	private double destLong;

	public double getcurrLat() {
		return currLat;
	}

	public void setcurrLat(double currLat) {
		this.currLat = currLat;
		notifyObservers();
	}

	public double getcurrLong() {
		return currLong;
	}

	public void setcurrLong(double currLong) {
		this.currLong = currLong;
		notifyObservers();
	}

	public double getdestLat() {
		return destLat;
	}

	public void setdestLat(double destLat) {
		this.destLat = destLat;
		notifyObservers();
	}

	public double getdestLong() {
		return destLong;
	}

	public void setdestLong(double destLong) {
		this.destLong = destLong;
		notifyObservers();
	}
}
