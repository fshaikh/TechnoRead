package com.example.technoread;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

public class AppService {
	private Context _context = null;
	private static AppService _appService = null;
	private ArrayList<NewsSourceModel> _sourcesList = null;
	private DOMFeedParser _parser = null;
	
	private AppService(Context context)
	{
		_context = context;
		_sourcesList = new ArrayList<NewsSourceModel>();
	}
	
	public static AppService GetInstance(Context context)
	{
		if(_appService == null)
		{
			_appService = new AppService(context);
		}
		return _appService;
	}
	
	public boolean SeedSources()
	{
		boolean status = SourceFileSeeder.SeedSourceStorage(_context);
		if(status)
		{
			// read the source into memory
			if(_parser == null)
			{
				_parser = new DOMFeedParser(_context);
				_parser.LoadParser();
			}
			_sourcesList = _parser.GetItems();
			
		}
		return true;
	}
	
	public boolean BackupSourceFile()
	{
		try {
			SourceFileSeeder.BackupAppDb(_context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	public NewsSourceModel GetItem(int position)
	{
		return _sourcesList.get(position);
	}
	
	public int GetSourcesCount()
	{
		return _sourcesList.size();
	}
	
	public CharSequence GetPageTitle(int position)
	{
		return _sourcesList.get(position).Title;
	}
	
}
