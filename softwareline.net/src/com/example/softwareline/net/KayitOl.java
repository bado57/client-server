package com.example.softwareline.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.softwareline.net.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class KayitOl extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText inputName;
	EditText inputSoyad;
	EditText inputKullaniciadi;
	EditText inputSifre;
	EditText inputUyeEposta;

	// burada url yi yazýyoruz
	private static String url_servis = "http://www.softvareline.tk/kayit.php";

	// php den dönen json sonucu
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kayitol);

		inputName = (EditText) findViewById(R.id.inputad);
		inputSoyad = (EditText) findViewById(R.id.inputsoyad);
		inputKullaniciadi = (EditText) findViewById(R.id.inputkadi);
		inputSifre = (EditText) findViewById(R.id.inputsifre);
		inputUyeEposta = (EditText) findViewById(R.id.inputeposta);

		Button btnkayit = (Button) findViewById(R.id.btnkayitol);

		btnkayit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// asynctask a execute ettim
				new CreateKayit().execute();
			}
		});
	}

	/**
	 *arka plandaki asynctask iþlemleri çünkü ne kadar süreceðini bilmiyoruz
	 * */
	class CreateKayit extends AsyncTask<String, String, String> {

		/**
		 * baþladýðý andan itibaren diaolg gösterimi kayýt sayfasý için
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(KayitOl.this);
			pDialog.setMessage("Kaydýnýz gerçekleþtiriliyor, lütfen bekleyiniz...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String name = inputName.getText().toString();
			String soyad = inputSoyad.getText().toString();
			String kadi = inputKullaniciadi.getText().toString();
			String sifre = inputSifre.getText().toString();
			String eposta = inputUyeEposta.getText().toString();

			// paremetre ayarlarý
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("soyad", soyad));
			params.add(new BasicNameValuePair("kadi", kadi));
			params.add(new BasicNameValuePair("sifre", sifre));
			params.add(new BasicNameValuePair("eposta", eposta));
			


			// jsonparsera gönderdim url i params ý yani diziyi felan
			JSONObject json = jsonParser.makeHttpRequest(url_servis,
					"POST", params);
			//Log.d("Create Response", json.toString());

		
			
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// baþarýyla kayýt oldunuz
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(i);
					// ekraný kapat
					finish();
				} else {
					// baþarýsýz kayýt iþlemi
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
			
		}

		/**
		 * asynctask tamamen bittiðinde yapýlack yerdir bizde prograss dialogu iptal ettik iþte.
		 * **/
		protected void onPostExecute(String file_url) {
			// sonkez döner progress
			pDialog.dismiss();
		}

	}
}
