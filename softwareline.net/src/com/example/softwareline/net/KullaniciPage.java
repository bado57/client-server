package com.example.softwareline.net;

import com.example.softwareline.net.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KullaniciPage extends Activity {
	Button btnkonumum;
	Button btnmesaj;
	 private Bundle extras = null;
	  private String namem=null ;
	  private String sifrem=null;
	  public static String yeninamee   = "kullaniciadi";
	  public static String yenisifree   = "kullanicisifre";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kullanici);
		extras = getIntent().getExtras();
		namem     = extras.getString(GirisYap.namee);
		sifrem     = extras.getString(GirisYap.sifree);
		System.out.println(namem);
		System.out.println(sifrem);

		btnkonumum = (Button) findViewById(R.id.btnkonum);
		btnmesaj = (Button) findViewById(R.id.btnmesajgonder);
		
		
		btnkonumum.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				btnkonumum.setBackgroundColor(Color.BLUE);
				
				Intent i= new Intent();
			      
			     i.setClass(getApplicationContext(), KullaniciLocation.class);
			      startActivity(i);
				
				
			}
		});
		
	
		btnmesaj.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
			
				 btnmesaj.setBackgroundColor(Color.BLUE);
				 Bundle extras = new Bundle();
			      Intent intent = new Intent();
			      extras.putString(yeninamee, namem);
			      extras.putString(yenisifree, sifrem);
			      
			      intent.putExtras(extras);
			      
			      intent.setClass(getApplicationContext(), MesajKullanici.class);
			      startActivity(intent);
				
			}
		});
	}
	
}
