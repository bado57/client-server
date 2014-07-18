package com.example.softwareline.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.softwareline.net.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AdminMesaj extends ListActivity {

	private ProgressDialog pDialog;

	
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	
	private static String url_servis_mesaj = "http://www.softvareline.tk/adminmesaj.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESAJ = "adminmesaj";
	private static final String TAG_PID = "pid";
	private static final String TAG_MESAJKONU = "mesaj_konu";

	JSONArray messages = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminmesaj);

		
		productsList = new ArrayList<HashMap<String, String>>();

		
		new LoadAllProducts().execute();

		ListView lv = getListView();

		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String pid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();

				
				Intent in = new Intent(getApplicationContext(),
						AdminMesajTum.class);
				in.putExtra(TAG_PID, pid);
				
				startActivityForResult(in, 100);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == 100) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

	
	class LoadAllProducts extends AsyncTask<String, String, String> {

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AdminMesaj.this);
			pDialog.setMessage("Mesajlar yükleniyor, lütfen bekleyiniz..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * tüm projelere getirmeye url 
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(url_servis_mesaj, "GET", params);
			
			
			Log.d("TÜM MESAJLAR ", json.toString());

			try {

				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					messages = json.getJSONArray(TAG_MESAJ);


					for (int i = 0; i < messages.length(); i++) {
						JSONObject c = messages.getJSONObject(i);


						String id = c.getString(TAG_PID);
						String name = c.getString(TAG_MESAJKONU);


						HashMap<String, String> map = new HashMap<String, String>();


						map.put(TAG_PID, id);
						map.put(TAG_MESAJKONU, name);


						productsList.add(map);
					}
				} else {

					Intent i = new Intent(getApplicationContext(),
							AdminPage.class);
					// çalýþan önceki tüm aktiviteleri kapattým
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 *backgrounddaki tüm dismis iþleri bittikten sonra burasý çalýþýr ve biter
		 * **/
		protected void onPostExecute(String file_url) {
			// tüm mesajlar geldikten sonraki dismiss
			pDialog.dismiss();
			// Background Thread daki UI yi güncelleþtir
			runOnUiThread(new Runnable() {
				public void run() {
					/**ÖZELLEÞTÝRÝLMÝÞ LÝSTVÝEW OALRAK GEÇER
					 * tümünü liste ye yazdýrýyoruz artýk yani dönen deðerden alýp mesajlarý listeye tek
					 * tek aktarýyorum burada
					 * */
					ListAdapter adapter = new SimpleAdapter(
							AdminMesaj.this, productsList,
							R.layout.list_item, new String[] { TAG_PID,
									TAG_MESAJKONU},
							new int[] { R.id.pid, R.id.name });
					// listviewi güncelle
					setListAdapter(adapter);
				}
			});

		}

	}
}
