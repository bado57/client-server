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
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") public class AdminMesajTum extends Activity {
    
	EditText txtName;
	EditText txtKonu;
	EditText txtTum;
	String pid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// üye url
	private static final String url_mesaj_detials = "http://www.softvareline.tk/adminmesajtum.php";


	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "product";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	private static final String TAG_KONU= "mesaj_konu";
	private static final String TAG_ICERIK = "mesaj_icerik";

	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mesajtum);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}

		Intent i = getIntent();
		

		pid = i.getStringExtra(TAG_PID);
		 System.out.println(pid);
		
		new GetProductDetails().execute();


	}

	
	class GetProductDetails extends AsyncTask<String, String, String> {
		 
		
		@Override
		protected void onPreExecute() {
			System.out.println("geldimi1");
			super.onPreExecute();
			pDialog = new ProgressDialog(AdminMesajTum.this);
			pDialog.setMessage("Mesaj yükleniyor lütfen bekleyiniz..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		
		protected String doInBackground(String... params) {

			
			runOnUiThread(new Runnable() {
				public void run() {
					
					int success;
					try {
						// Building Parametersssssssssssss
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("pid", pid));
						System.out.println(params);
						
						System.out.println("selam11111111111111");
						JSONObject json = jsonParser.makeHttpRequest(url_mesaj_detials, "GET", params);
						System.out.println("selam22222222222222");
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							System.out.println("selam33333333333333");
							// successfully geri dönüþ bildirimi
							JSONArray productObj = json
									.getJSONArray(TAG_PRODUCT); // JSON Array
							System.out.println("sona geldik");
							
							JSONObject product = productObj.getJSONObject(0);

							System.out.println("sonunda  geldik");
							txtName = (EditText) findViewById(R.id.inputmesajName);
							txtKonu = (EditText) findViewById(R.id.inputmesajKonu);
							txtTum = (EditText)  findViewById(R.id.inputmesajTum);

							
							txtName.setText(product.getString(TAG_NAME));
							txtKonu.setText(product.getString(TAG_KONU));
							txtTum.setText(product.getString(TAG_ICERIK));

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

