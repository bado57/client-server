package com.example.softwareline.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.softwareline.net.R;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MesajKullanici extends Activity {
	

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText inputIcerik;
	EditText inputKonu;
	  private Bundle extras = null;
	  private String namesi ;
	  private String sifresi;

	// burada url yi yaz�yoruz
	private static String url_servis = "http://www.softvareline.tk/kullanicimesaj.php";

	// php den d�nen json sonucu
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mesajgonder);
		
		
		extras = getIntent().getExtras();
	    namesi     = extras.getString(KullaniciPage.yeninamee);
	    sifresi     = extras.getString(KullaniciPage.yenisifree);
		//System.out.println(namesi);
		//System.out.println(sifresi);
		inputKonu = (EditText) findViewById(R.id.edtgnderkonu);
		inputIcerik = (EditText) findViewById(R.id.edtgndermesajicerik);
		

		Button btnmesajgonder = (Button) findViewById(R.id.btngndermesaj);

		btnmesajgonder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// asynctask a execute ettim
				new CreateKayit().execute();
			}
		});
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
			pDialog = new ProgressDialog(MesajKullanici.this);
			pDialog.setMessage("Mesaj g�nderiliyor, l�tfen bekleyiniz...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String konu = inputKonu.getText().toString();
			String icerik = inputIcerik.getText().toString();


			// paremetre ayarlar�
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("konu", konu));
			params.add(new BasicNameValuePair("icerik", icerik));
			params.add(new BasicNameValuePair("kullaniciadi", namesi));
			params.add(new BasicNameValuePair("kullanicisifre", sifresi));


			// jsonparsera g�nderdim url i params � yani diziyi felan
			JSONObject json = jsonParser.makeHttpRequest(url_servis,
					"POST", params);
			//Log.d("Create Response", json.toString());

		
			
			try {
			
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
				
					// ba�ar�yla mesaj g�nderildi
					Intent i = new Intent(getApplicationContext(), GirisYap.class);
					startActivity(i);
					// ekran� kapat
					finish();
				} else {
					
					if(success==2){
					
						Intent i = new Intent(getApplicationContext(), MesajKullanici.class);
						startActivity(i);
						
					}else{
						
						//giri� ba�ar�s�z
					}
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

}
