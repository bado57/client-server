package com.example.softwareline.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.softwareline.net.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class LocationKoordinant extends Activity {
	public static double x;
	public static double y;
	TextView tvAddress;
	
		private ProgressDialog pDialog;

		JSONParser jsonParser = new JSONParser();

		// burada url yi yaz�yoruz
		private static String url_servis = "http://www.softvareline.tk/konumadmin.php";

		// php den d�nen json sonucu
		private static final String TAG_SUCCESS = "success";
	
	public static String koordinant   = "koordinat";
	public static String koordinant�m   = "koordinatt";
    public void onCreate(Bundle savedInstanceState)

    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.locationkoordinat);
        final String gpsAcildi = "GPS Ac�ld�";
        final String gpsKapatildi = "GPS Kapal�";
        tvAddress = (TextView) findViewById(R.id.textadress);
        final TextView konumText = (TextView)findViewById(R.id.textView2);

        LocationManager konumYoneticisi = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

 
        LocationListener konumDinleyicisi = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText( getApplicationContext(),gpsAcildi,Toast.LENGTH_SHORT).show();
                konumText.setText("GPS Veri bilgileri Al�n�yor...");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText( getApplicationContext(),gpsKapatildi,Toast.LENGTH_SHORT).show();
                konumText.setText("GPS Baglant�s� Bekleniyor...");
            }

            @Override
            public void onLocationChanged(Location loc) {
                x=(double) loc.getLatitude();
                y=(double) loc.getLongitude();
                Log.w("Veri","Al�n�yor");
                String Text = "Arac�n Bulundu�u koordinat  bilgileri : \n" +"enlem = " + loc.getLatitude() +"\nboylam = "+ loc.getLongitude();
                konumText.setText(Text);
                new ReverseGeocodingTask().execute(loc);
            }
        };
        final Button btnharitam=(Button)findViewById(R.id.btnHarita);
        btnharitam.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				btnharitam.setBackgroundColor(Color.BLUE);
				 
			      Bundle extras = new Bundle();
			      Intent intent = new Intent();
			      extras.putDouble(koordinant, x);
			      extras.putDouble(koordinant�m, y);
			 
			      intent.putExtras(extras);
			      /** Yeni sayfayi cagiralim */
			      intent.setClass(getApplicationContext(), LocationBulma.class);
			      startActivity(intent);
				  
			 	 
				
			}
		});
        final Button btnlocationkoordinant=(Button)findViewById(R.id.btnLoactionGnder);
        btnlocationkoordinant.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new CreateKayit().execute();
				
			}
		});

        konumYoneticisi.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, konumDinleyicisi);
    }
	/**
	 *arka plandaki asynctask i�lemleri ��nk� ne kadar s�rece�ini bilmiyoruz
	 * */
	class CreateKayit extends AsyncTask<String, String, String> {
		
		/**
		 * ba�lad��� andan itibaren diaolg g�sterimi kay�t sayfas� i�in
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LocationKoordinant.this);
			pDialog.setMessage("Kayd�n�z ger�ekle�tiriliyor, l�tfen bekleyiniz...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String enlem = String.valueOf(x);
			String boylam = String.valueOf(y);
			// paremetre ayarlar�
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            
			params.add(new BasicNameValuePair("enlem", enlem));
			params.add(new BasicNameValuePair("boylam", boylam));
			


			// jsonparsera g�nderdim url i params � yani diziyi felan
			JSONObject json = jsonParser.makeHttpRequest(url_servis,
					"POST", params);
			//Log.d("Create Response", json.toString());

		
			
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// ba�ar�yla kay�t oldunuz
					Intent i = new Intent(getApplicationContext(), LocationKoordinant.class);
					startActivity(i);
					// ekran� kapat
					finish();
				} else {
					// ba�ar�s�z kay�t i�lemi
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
			
		}

		/**
		 * asynctask tamamen bitti�inde yap�lack yerdir bizde prograss dialogu iptal ettik i�te.
		 * **/
		protected void onPostExecute(String file_url) {
			// sonkez d�ner progress
			pDialog.dismiss();
		}

	}
	private class ReverseGeocodingTask extends AsyncTask<Location, Void, String> {
		@Override
		protected String doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(LocationKoordinant.this, Locale.getDefault());

			Location loc = params[0];
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
			} catch (IOException e) {
				e.printStackTrace();
				return e.getMessage();
			}
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);
				String addressText = String.format("%s, %s, %s", address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "", address.getLocality(),
						address.getCountryName());
				return addressText;
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			tvAddress.setText("Adres="+result);
		}

	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
