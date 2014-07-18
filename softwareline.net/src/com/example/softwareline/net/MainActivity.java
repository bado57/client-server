package com.example.softwareline.net;



import com.example.softwareline.net.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class MainActivity extends Activity {
    
	
	Button btngiriskayit;
	Button btngiris;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		btngiriskayit = (Button) findViewById(R.id.btngiriskayit);
		btngiris = (Button) findViewById(R.id.btngiris);
		
		
		btngiris.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				btngiris.setBackgroundColor(Color.BLUE);
				Intent i = new Intent(getApplicationContext(), GirisYap.class);
				startActivity(i);
				
			}
		});
		
	
		btngiriskayit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
			
				 btngiriskayit.setBackgroundColor(Color.BLUE);
				Intent i = new Intent(getApplicationContext(), KayitOl.class);
				startActivity(i);
				
			}
		});
	}


}
