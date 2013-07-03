package com.example.technoread;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.menu_save:
			SaveChanges();
			return true;
		case R.id.menu_add:
			AddNewsSource(null);
			return true;
		}
		return true;
	}

	public void Refresh(NewsSourceModel model, boolean _isNew)
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
	
	private void AddNewsSource(NewsSourceModel model)
	{
		AddNewsSourceDialog dlg = new AddNewsSourceDialog(model,getApplicationContext());
		dlg.show(getFragmentManager(), "AddNewsSourceDialog");
	}

	private void SetListAdaptorInternal(ArrayList<NewsSourceModel> modelRecords)
	{
		_adapter = new NewsSourceListAdapter(this, R.layout.configureactivity_listviewitem,  modelRecords);
		ListView lv = getListViewInternal();
		lv.setAdapter(_adapter);
		lv.setOnItemClickListener(new ListHelper());
	}
	
	private ListView getListViewInternal() {
		ListView lv = (ListView)findViewById(R.id._newsSourceListView);
		return lv;
	}
	
	private final class ListHelper implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long id)
		{
			NewsSourceModel model =  (NewsSourceModel) AppService.GetInstance(getApplicationContext()).GetItemFromAll((int) id);
			if(model != null)
			{
				AddNewsSource(model);
			}
		}
		
	}

}
