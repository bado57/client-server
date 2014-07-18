package com.example.softwareline.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.softwareline.net.R;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GirisYap extends Activity {
	// Progress Dialog
		private ProgressDialog pDialog;

		JSONParser jsonParser = new JSONParser();
		EditText inputName;
		EditText inputSifre;
		EditText inputKullaniciadi;
		public static String namee   = "kullaniciadi";
		public static String sifree   = "kullanicisifre";
		String name;
		String sifre;

		// burada url yi yaz�yoruz
		private static String url_servis = "http://www.softvareline.tk/giris.php";

		// php den d�nen json sonucu
		private static final String TAG_SUCCESS = "success";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.girisyap);
			
			inputName = (EditText) findViewById(R.id.editgirisadi);
			inputSifre = (EditText) findViewById(R.id.editgirissifre);
			

			Button btngiris = (Button) findViewById(R.id.btngirisgir);

			btngiris.setOnClickListener(new View.OnClickListener() {

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
				pDialog = new ProgressDialog(GirisYap.this);
				pDialog.setMessage("Giri� Yap�l�yor, l�tfen bekleyiniz...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
			protected String doInBackground(String... args) {
				 name = inputName.getText().toString();
				 sifre = inputSifre.getText().toString();

				// paremetre ayarlar�
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", name));
				params.add(new BasicNameValuePair("sifre", sifre));


				// jsonparsera g�nderdim url i params � yani diziyi felan
				JSONObject json = jsonParser.makeHttpRequest(url_servis,
						"POST", params);
				//Log.d("Create Response", json.toString());

			
				
				try {
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// ba�ar�yla kay�t oldunuz
						Intent i = new Intent(getApplicationContext(), AdminPage.class);
						startActivity(i);
						// ekran� kapat
						finish();
					} else {
						
						if(success==2){

						      Bundle extras = new Bundle();
						      Intent intent = new Intent();
						      extras.putString(namee, name);
						      extras.putString(sifree, sifre);
						 
						      intent.putExtras(extras);
						      /** Yeni sayfayi cagiralim */
						      intent.setClass(getApplicationContext(), KullaniciPage.class);
						      startActivity(intent);
							
							
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
