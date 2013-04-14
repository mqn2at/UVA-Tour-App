package edu.virginia.finalproject;

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

public class FirstDemo extends Screen {
	private EditText currX;
	private EditText currY;
	private EditText targetX;
	private EditText targetY;
	private EditText distance;
	private EditText status;
	private TextView textView1;

	private FirstDemoModel model;
	private LocationManager locMan;

	public void initialize() {
		model = new FirstDemoModel();
		model.addObserver(this);
		model.setTargetX(38.032966);
		model.setTargetY(-78.514148);
		targetX.setText(String.format("%.6f", model.getTargetX()));
		targetY.setText(String.format("%.6f", model.getTargetY()));

		GPS gps = new GPS(this);
		Location loc = gps.getLocation();
		currX.setText(String.format("%.6f", loc.getLatitude()));
		currY.setText(String.format("%.6f", loc.getLongitude()));
		float[] dist = new float[1];
		Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), 38.032966,
				-78.514148, dist);
		distance.setText(dist[0] + "");
	}

	// ----------------------------------------------------------
	/**
	 * Called when the user has committed an editing operation in the widget
	 * with ID "billAmount", by pressing the "Done" or "Enter" key on their
	 * device's keyboard.
	 */
	public void currXEditingDone() {
		float x;
		try {
			x = Float.parseFloat(currX.getText().toString());
		}
		catch (NumberFormatException e) {
			x = 0.0f;
		}

		model.setCurrX(x);
	}

	public void currYEditingDone() {
		float y;
		try {
			y = Float.parseFloat(currY.getText().toString());
		}
		catch (NumberFormatException e) {
			y = 0.0f;
		}

		model.setCurrY(y);
	}

	@SuppressLint("DefaultLocale")
	public void changeWasObserved(FirstDemoModel model) {
		double dist = model.getDistance();
		String d = String.format("%.2f", dist);
		String s;
		if (dist < 5) {
			s = "hot";
		}
		else if (dist > 200) {
			s = "cold";
		}
		else if (dist >= 70) {
			s = "cool";
		}
		else {
			s = "warm";
		}
		status.setText(s);
		// distance.setText(d);
		
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
		private static final long MIN_DIST = 10; // 10 meters

		// The minimum time between updates in milliseconds
		private static final long MIN_TIME = 1000 * 60 * 1; // 1 minute

		// Declaring a Location Manager
		protected LocationManager locationManager;

		public GPS(Context context) {
			this.mContext = context;
		}

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

		// method to stop using the GPS
		public void stopUsingGPS() {
			if (locationManager != null) {
				locationManager.removeUpdates(GPS.this);
			}
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
			currX.setText(String.format("%.6f", location.getLatitude()));
			currY.setText(String.format("%.6f", location.getLongitude()));
			float[] dist = new float[1];
			Location.distanceBetween(location.getLatitude(), location.getLongitude(), 38.032966,
					-78.514148, dist);
			distance.setText(dist[0] + "");
			String s;
			if (dist[0] < 5) {
				s = "hot";
			}
			else if (dist[0] > 200) {
				s = "cold";
			}
			else if (dist[0] >= 70) {
				s = "cool";
			}
			else {
				s = "warm";
			}
			status.setText(s);
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
