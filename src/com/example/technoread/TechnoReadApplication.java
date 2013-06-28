package com.example.technoread;

import android.app.Application;

public class TechnoReadApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		AppService.GetInstance(getApplicationContext()).SeedSources();
	}
	
}
