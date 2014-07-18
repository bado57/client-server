package com.example.softwareline.net;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import com.example.softwareline.net.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationBulma extends Activity{

	
	  static  LatLng Burda;
	  static  LatLng Burda1;
	  private GoogleMap map;
	  private Bundle extras = null;
	  private double koor = -1;
	  private double koor1 = -1;

	  @SuppressLint("NewApi") @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_maps);
	    
	    extras = getIntent().getExtras();
	    koor     = extras.getDouble(LocationKoordinant.koordinant);
	    koor1     = extras.getDouble(LocationKoordinant.koordinantým);

	    Burda = new LatLng(koor, koor1);
	    Burda1 = new LatLng(koor, koor1);
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	        .getMap();
	    
	    Marker kiel = map.addMarker(new MarkerOptions()
	        .position(Burda1)
	        .title("Servis Konum")
	        .snippet("Yarý Güneþli")
	        .icon(BitmapDescriptorFactory
	            .fromResource(R.drawable.kids)));

	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(Burda, 15));

	    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	  }

	

	
}






