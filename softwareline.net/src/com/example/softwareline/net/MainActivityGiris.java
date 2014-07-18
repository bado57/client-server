package com.example.softwareline.net;



import com.example.softwareline.net.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.widget.ProgressBar;
/**
 * 
 * @author Bayram Altýnýþýk
 * tel:05452004650
 * mail:bayram_20_03@hotmail.com
 *
 */



public class MainActivityGiris extends Activity {
	private static final int PROGRESS = 0x1;

	private ProgressBar mProgress;
	private int mProgressStatus = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_giris);
	
		setmProgress((ProgressBar) findViewById(R.id.progressBar1));

		Handler handler= new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				startActivity(new Intent(MainActivityGiris.this, MainActivity.class));
				MainActivityGiris.this.finish();
			}
		}, 5000);

	}
	
	public int getmProgressStatus() {
		return mProgressStatus;
	}

	public void setmProgressStatus(int mProgressStatus) {
		this.mProgressStatus = mProgressStatus;
	}

	public ProgressBar getmProgress() {
		return mProgress;
	}

	public void setmProgress(ProgressBar mProgress) {
		this.mProgress = mProgress;
	}

	public static int getProgress() {
		return PROGRESS;
	}
}
