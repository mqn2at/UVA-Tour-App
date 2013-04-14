package edu.virginia.finalproject;

public class FirstDemoModel extends sofia.util.Observable {
	private double currX;
	private double currY;
	private double targetX;
	private double targetY;

	public double getCurrX() {
		return currX;
	}

	public void setCurrX(double currX) {
		this.currX = currX;
		notifyObservers();
	}

	public double getCurrY() {
		return currY;
	}

	public void setCurrY(double currY) {
		this.currY = currY;
		notifyObservers();
	}

	public double getTargetX() {
		return targetX;
	}

	public void setTargetX(double targetX) {
		this.targetX = targetX;
		notifyObservers();
	}

	public double getTargetY() {
		return targetY;
	}

	public void setTargetY(double targetY) {
		this.targetY = targetY;
		notifyObservers();
	}

	public double getDistance() {
		return Math.sqrt(Math.pow(targetX - currX, 2)
				+ Math.pow(targetY - currY, 2));
	}
}
