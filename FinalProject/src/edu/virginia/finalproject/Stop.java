package edu.virginia.finalproject;

//class used to store data for each destination
public class Stop {
	private String name;
	private double latitude;
	private double longitude;

	//Constructor for class, stores the name of the destination and its coordinates
	public Stop(String name, double latitude, double longtitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longtitude;
	}

	//getter method for name
	public String getName() {
		return name;
	}

	//setter method for name
	public void setName(String name) {
		this.name = name;
	}

	//getter method for latitude
	public double getLatitude() {
		return latitude;
	}

	//setter method for latitude
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	//getter method for longitude 
	public double getLongitude() {
		return longitude;
	}

	//getter method for latitude
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
