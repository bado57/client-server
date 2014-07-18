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

	// yapýcý metod boþ kullanmadým
	public JSONParser() {

	}

	// bu fonksiyon post ve get metodunu http sorgusuna gönderir
	
	public JSONObject makeHttpRequest(String url, String method,
			List<NameValuePair> params) {

		// HTTP sorgu yapýmý burda baþlattým
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
				// get metodu olursa eðer
				
				DefaultHttpClient httpClient = new DefaultHttpClient();
				//türkçe karekteri aþma
				String paramString = URLEncodedUtils.format(params, "utf-8");
				//gönderilecek url li oluþturuyor.
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
			Log.e("Sunucu Hatasý", "dönüþtüme baþarýsýz" + e.toString());
		}

		// burda dönen string deðeri object e dönüþtürüp onuda kayitol a döndürecez
		try {
			System.out.println("selam5");
			//hata burda json.encode dan deðer dönmüyor normalde insert iþlemi tamam dýr
			jObj = new JSONObject(json);
			
		} catch (JSONException e) {
			Log.e("JSON Parser", "veri parse hatasý " + e.toString());
			
		}

		// json stringini object olarak dönrüyorum
		return jObj;

	}
}
