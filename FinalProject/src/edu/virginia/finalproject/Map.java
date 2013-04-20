package edu.virginia.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import com.google.android.maps.*;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.*;

public class Map extends Activity {

	private GoogleMap map;

	private double latitude;
	private double longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		
		latitude = getIntent().getDoubleExtra("latitude", 0.0);
		longitude = getIntent().getDoubleExtra("longitude", 0.0);
		
		LatLng position = new LatLng(latitude, longitude);
		Marker marker = map.addMarker(new MarkerOptions().position(position));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 18));
	}
	/*
	 * @Override protected boolean onTap(int index) { finish(); }
	 */
}
