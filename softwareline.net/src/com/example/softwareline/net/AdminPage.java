package com.example.softwareline.net;

import com.example.softwareline.net.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPage extends Activity {
	Button btnlocations;
	Button btnmesaj;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);
		
		// Buttons
		btnlocations = (Button) findViewById(R.id.btnlocation);
		btnmesaj = (Button) findViewById(R.id.btnadminmesaj);
		

		btnlocations.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				btnlocations.setBackgroundColor(Color.BLUE);
				Intent i = new Intent(getApplicationContext(), LocationKoordinant.class);
				startActivity(i);
				
			}
		});
		

		btnmesaj.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {

				 btnmesaj.setBackgroundColor(Color.BLUE);
				Intent i = new Intent(getApplicationContext(), AdminMesaj.class);
				startActivity(i);
				
			}
		});
	}


}
