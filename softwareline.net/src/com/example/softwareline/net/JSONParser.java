package com.example.softwareline.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
    
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = null;

	// yap�c� metod bo� kullanmad�m
	public JSONParser() {

	}

	// bu fonksiyon post ve get metodunu http sorgusuna g�nderir
	
	public JSONObject makeHttpRequest(String url, String method,
			List<NameValuePair> params) {

		// HTTP sorgu yap�m� burda ba�latt�m
		try {
			// post metodu burdan
			if(method == "POST"){
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				//System.out.println(httpResponse);
				
			}else if(method == "GET"){
				// get metodu olursa e�er
				
				DefaultHttpClient httpClient = new DefaultHttpClient();
				//t�rk�e karekteri a�ma
				String paramString = URLEncodedUtils.format(params, "utf-8");
				//g�nderilecek url li olu�turuyor.
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				System.out.println(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				
				
			}			
			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
		
		
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			System.out.println(json);
		} catch (Exception e) {
			Log.e("Sunucu Hatas�", "d�n��t�me ba�ar�s�z" + e.toString());
		}

		// burda d�nen string de�eri object e d�n��t�r�p onuda kayitol a d�nd�recez
		try {
			System.out.println("selam5");
			//hata burda json.encode dan de�er d�nm�yor normalde insert i�lemi tamam d�r
			jObj = new JSONObject(json);
			
		} catch (JSONException e) {
			Log.e("JSON Parser", "veri parse hatas� " + e.toString());
			
		}

		// json stringini object olarak d�nr�yorum
		return jObj;

	}
}
