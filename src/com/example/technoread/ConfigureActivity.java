package com.example.technoread;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class ConfigureActivity extends Activity {
	
	NewsSourceListAdapter _adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configure);
		SetListAdaptorInternal(AppService.GetInstance(getApplicationContext()).GetNewsSource());
		
		GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0xFFFFFFFF, 0xFFFFFFFF});
	getActionBar().setBackgroundDrawable(gd);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_configure, menu);
		return true;
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(getApplicationContext(), "OnResume", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.menu_save:
			SaveChanges();
			return true;
		case R.id.menu_add:
			AddNewsSource();
			return true;
		}
		return true;
	}

	public void Refresh(NewsSourceModel model)
	{
		AppService.GetInstance(getApplicationContext()).AddNewsSource(model);
		boolean status = AppService.GetInstance(getApplicationContext()).PersistChanges();
		if(status)
		{
			Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
		}
		_adapter.notifyDataSetChanged();
	}

	private void SaveChanges() {
		boolean status = AppService.GetInstance(getApplicationContext()).PersistChanges();
		if(status)
		{
			Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
		}
		finish();

	}
	
	private void AddNewsSource()
	{
		AddNewsSourceDialog dlg = new AddNewsSourceDialog(null,getApplicationContext());
		dlg.show(getFragmentManager(), "AddNewsSourceDialog");
	}

	private void SetListAdaptorInternal(ArrayList<NewsSourceModel> modelRecords)
	{
		_adapter = new NewsSourceListAdapter(this, R.layout.configureactivity_listviewitem,  modelRecords);
		ListView lv = getListViewInternal();
		lv.setAdapter(_adapter);
	}
	
	private ListView getListViewInternal() {
		ListView lv = (ListView)findViewById(R.id._newsSourceListView);
		return lv;
	}

}
