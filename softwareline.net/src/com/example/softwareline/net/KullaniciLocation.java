package com.example.softwareline.net;


import java.util.ArrayList;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.softwareline.net.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") public class KullaniciLocation extends Activity{

	TextView txtKonum;
	Button btnkullanciharita;

	String pid;
	double x;
	double y;


	private ProgressDialog pDialog;
	

	JSONParser jsonParser = new JSONParser();


	private static String url_servis_mesaj = "http://www.softvareline.tk/kullaniciharita.php";



	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "product";
	private static final String TAG_ENLEM = "enlem";
	private static final String TAG_BOYLAM = "boylam";
	public static String koordinanttt   = "koordinaat";
	public static String koordinantttým   = "koordinaatt";

	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kullaniciharita);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}

	
		btnkullanciharita = (Button) findViewById(R.id.btnkullaniciharita);
		

		
		new KullaniciLocationn().execute();

		
		btnkullanciharita.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				 Bundle extras = new Bundle();
			      Intent intent = new Intent();
			      extras.putDouble(koordinanttt, x);
			      extras.putDouble(koordinantttým, y);
			 
			      intent.putExtras(extras);
			      /** Yeni sayfayi cagiralim */
			      intent.setClass(getApplicationContext(), LocationBulmaKullanici.class);
			      startActivity(intent);
			
			}
		});
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class KullaniciLocationn extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(KullaniciLocation.this);
			pDialog.setMessage("Location verileri yükleniyor lütfen bekleyiniz...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		
		protected String doInBackground(String... params) {

		
			runOnUiThread(new Runnable() {
				public void run() {
		
					int success;
					try {

						List<NameValuePair> params = new ArrayList<NameValuePair>();
					     pid="location";
						params.add(new BasicNameValuePair("pid", pid));
 
						System.out.println("111");
						JSONObject json = jsonParser.makeHttpRequest(url_servis_mesaj, "POST", params);

						System.out.println("112");
						
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {

						
							JSONArray productObj = json
									.getJSONArray(TAG_PRODUCT); 
							
						
							JSONObject product = productObj.getJSONObject(0);

							
							txtKonum = (TextView) findViewById(R.id.textkullaniciharita);
							//System.out.println(product.getString(TAG_ENLEM));
							x=product.getDouble(TAG_ENLEM);
							y=product.getDouble(TAG_BOYLAM);
							String Text = "Aracýn Bulunduðu koordinat  bilgileri : \n" +"enlem = " + product.getString(TAG_ENLEM) +"\nboylam = "+ product.getString(TAG_BOYLAM);
				             txtKonum.setText(Text);

						}else{
					
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}
		

		
		protected void onPostExecute(String file_url) {
			
			pDialog.dismiss();
		}
	
	}



}
