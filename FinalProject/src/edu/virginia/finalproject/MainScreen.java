package edu.virginia.finalproject;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import sofia.app.Screen;
import sofia.app.ScreenLayout;
import android.annotation.SuppressLint;
import android.widget.EditText;
import android.text.format.*;
import sofia.util.*;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends Screen {
	private TextView currLat;
	private TextView currLong;
	private TextView destName;
	private TextView destLat;
	private TextView destLong;
	private TextView dist;
	private TextView status;
	private TextView timeElapsed;
	private EditText editLat;
	private EditText editLong;

	private LocationManager locMan;
	private GPS gps;
	private Timer timer;
	private int time = 0;
	private ArrayList<Stop> stops;
	private int currentStop = 0;
	private boolean usingGPS = true;
	private boolean notFinished = true;
	private double lat, lon;

	public void initialize() {
		currentStop = 0;
		stops = new ArrayList<Stop>();
		Stop afc = new Stop("AFC", 38.032966, -78.514148);
		Stop ohill = new Stop("OHill Dining Hall", 38.034817, -78.514599);
		Stop rotunda = new Stop("Rotunda", 38.035366, -78.503537);
		Stop rice = new Stop("Rice Hall", 38.033667, -78.510629); // check
		Stop clark = new Stop("Clark", 38.033574, -78.507625);
		Stop amphitheatre = new Stop("Amphitheater", 38.033617, -78.505822);
		stops.add(afc);
		stops.add(ohill);
		stops.add(rotunda);
		stops.add(rice);
		stops.add(clark);
		stops.add(amphitheatre);

		destName.setText(stops.get(currentStop).getName());
		destLat.setText(String.format("%.6f", stops.get(currentStop)
				.getLatitude()));
		destLong.setText(String.format("%.6f", stops.get(currentStop)
				.getLongitude()));

		gps = new GPS(this);
		Location loc = gps.getLocation();
		updatePosition(loc);

		presentScreen(WelcomeScreen.class, new WelcomeScreen());
		timer = Timer.callRepeatedly(this, "clock", 1000);
	}

	public void clock() {
		Location loc = gps.getLocation();
		if (notFinished) {
			updatePosition(loc);
		}

		time++;
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
		timeElapsed.setText(h + ":" + m + ":" + s);
	}

	public void updatePosition(Location loc) {
		if (usingGPS) {
			lat = loc.getLatitude();
			lon = loc.getLongitude();
		}
		else {
			try {
				lat = Double.parseDouble(editLat.getText() + "");
				lon = Double.parseDouble(editLong.getText() + "");
			}
			catch (Exception e) {
				lat = 0.0;
				lon = 0.0;
			}
		}
		currLat.setText(String.format("%.6f", lat));
		currLong.setText(String.format("%.6f", lon));

		float[] distance = new float[1];
		Location.distanceBetween(lat, lon,
				stops.get(currentStop).getLatitude(), stops.get(currentStop)
						.getLongitude(), distance);
		dist.setText(String.format("%.3f", distance[0]));

		String s;
		if (distance[0] < 5) {
			s = "hot";
		}
		else if (distance[0] > 200) {
			s = "cold";
		}
		else if (distance[0] >= 70) {
			s = "cool";
		}
		else {
			s = "warm";
		}
		status.setText(s);

		if (distance[0] < 50) {
			destinationReached();
		}
	}

	public void destinationReached() {
		currentStop++;
		if (currentStop >= stops.size()) {
			notFinished = false;
			timer.stop();

			// present congratulatory screen
			presentScreen(CongratsScreen.class, timeElapsed.getText());

			// present last stop screen
			presentScreen(Amphitheater.class, new Amphitheater());

			// kill app
			finish();
		}
		else {
			// present info screen
			if (currentStop == 1) {
				presentScreen(AFC.class, new AFC());
			}
			if (currentStop == 2) {
				presentScreen(Ohill.class, new Ohill());
			}
			if (currentStop == 3) {
				presentScreen(Rotunda.class, new Rotunda());
			}
			if (currentStop == 4) {
				presentScreen(Rice.class, new Rice());
			}
			if (currentStop == 5) {
				presentScreen(Clark.class, new Clark());
			}

			// update with new coordinates
			destName.setText(stops.get(currentStop).getName());
			destLat.setText(String.format("%.6f", stops.get(currentStop)
					.getLatitude()));
			destLong.setText(String.format("%.6f", stops.get(currentStop)
					.getLongitude()));

			Location loc = gps.getLocation();
			updatePosition(loc);
		}
	}

	// GPS button
	public void gpsButtonClicked() {
		usingGPS = true;
	}

	// Manual button
	public void manualButtonClicked() {
		usingGPS = false;
	}

	public class GPS extends Service implements LocationListener {

		private final Context mContext;

		// boolean for GPS status
		boolean isGPSEnabled = false;

		// boolean for network status
		boolean isNetworkEnabled = false;

		// boolean for GPS status
		boolean canGetLocation = false;

		Location location; // location
		double latitude; // latitude
		double longitude; // longitude

		// The minimum distance to change Updates in meters
		private static final long MIN_DIST = 1; // 1 meters

		// The minimum time between updates in milliseconds
		private static final long MIN_TIME = 1000; // 1 second

		// Declaring a Location Manager
		protected LocationManager locationManager;

		public GPS(Context context) {
			this.mContext = context;
		}

		// gets Location object for current location
		public Location getLocation() {
			try {
				locationManager = (LocationManager) mContext
						.getSystemService(LOCATION_SERVICE);

				// getting GPS status
				isGPSEnabled = locationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER);

				// getting network status
				isNetworkEnabled = locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

				if (!isGPSEnabled && !isNetworkEnabled) {
					// no network provider is enabled
				}
				else {
					this.canGetLocation = true;
					if (isNetworkEnabled) {
						locationManager.requestLocationUpdates(
								LocationManager.NETWORK_PROVIDER, MIN_TIME,
								MIN_DIST, this);
						Log.d("Network", "Network");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
					// if GPS Enabled get lat/long using GPS Services
					if (isGPSEnabled) {
						if (location == null) {
							locationManager.requestLocationUpdates(
									LocationManager.GPS_PROVIDER, MIN_TIME,
									MIN_DIST, this);
							Log.d("GPS Enabled", "GPS Enabled");
							if (locationManager != null) {
								location = locationManager
										.getLastKnownLocation(LocationManager.GPS_PROVIDER);
								if (location != null) {
									latitude = location.getLatitude();
									longitude = location.getLongitude();
								}
							}
						}
					}
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}

			return location;
		}

		// returns the Latitude coordinate
		public double getLatitude() {
			if (location != null) {
				latitude = location.getLatitude();
			}
			return latitude;
		}

		// returns the Longitude coordinate
		public double getLongitude() {
			if (location != null) {
				longitude = location.getLongitude();
			}
			return longitude;
		}

		// boolean to check if wifi is enabled
		public boolean canGetLocation() {
			return this.canGetLocation;
		}

		@Override
		public void onLocationChanged(Location location) {
			// updatePosition(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public IBinder onBind(Intent arg0) {
			return null;
		}
	}
}
