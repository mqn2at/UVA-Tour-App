package edu.virginia.finalproject;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.*;

public class Map extends Activity {

	private GoogleMap map;
	private double currLat; // latitude coordinate of current location
	private double currLong; // longitude coordinate of current location
	private double destLat; // latitude coordinate of destination location
	private double destLong; // longitude coordinate of destination location

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		//get current location and destination data from MainScreen
		currLat = getIntent().getDoubleExtra("currLat", 0.0);
		currLong = getIntent().getDoubleExtra("currLong", 0.0);
		destLat = getIntent().getDoubleExtra("destLat", 0.0);
		destLong = getIntent().getDoubleExtra("destLong", 0.0);

		LatLng position = new LatLng(currLat, currLong);
		LatLng destination = new LatLng(destLat, destLong);
		
		//set marker on map
		Marker markerPosition = map.addMarker(new MarkerOptions()
				.position(position)
				.title("Current Position")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		Marker markerDestination = map.addMarker(new MarkerOptions().position(
				destination).title("Next Destination"));
		
		//center map on current location
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
	}
}
