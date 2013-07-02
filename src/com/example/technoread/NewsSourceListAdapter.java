package com.example.technoread;
import java.util.ArrayList;
import java.util.List;

import com.example.technoread.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


public class NewsSourceListAdapter extends ArrayAdapter<NewsSourceModel> {

	private ArrayList<NewsSourceModel> _nsList = null;
	@SuppressWarnings("unused")
	private Context _context = null;
	
	public NewsSourceListAdapter(Context context,
			int textViewResourceId, List<NewsSourceModel> objects)
	{
		super(context, textViewResourceId, objects);
		_nsList = (ArrayList<NewsSourceModel>) objects;
		_context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if(rowView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.configureactivity_listviewitem, null);
		}
		NewsSourceModel source = _nsList.get(position);
		if(source != null)
		{
			SetTextValues(source,rowView,position);
			return rowView;
		}
		return super.getView(position, convertView, parent);

	}

	private void SetTextValues(NewsSourceModel source, View rowView,int position)
	{
		TextView tv = (TextView)rowView.findViewById(R.id._titleTextView);
		tv.setText(source.Title);
		
		CheckBox cb = (CheckBox)rowView.findViewById(R.id._addedCheckBox);
		cb.setChecked(source.IsAdded);
		
		final int newsSourcePos = position;
		
		cb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View cbView) {
				// TODO Auto-generated method stub
				CheckBox cb = (CheckBox)cbView;
				AppService.GetInstance(getContext()).SetAddStatus(newsSourcePos, cb.isChecked());
			}
		});
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//not required.
			}
		});
	}
	
	

}
