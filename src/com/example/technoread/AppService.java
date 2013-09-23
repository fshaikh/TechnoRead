package com.example.technoread;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;


public class AppService {
	private Context _context = null;
	private static AppService _appService = null;
	private ArrayList<NewsSourceModel> _sourcesList = null;
	private ArrayList<NewsSourceModel> _addedViewModel = null;
	private DOMFeedParser _parser = null;
	
	private AppService(Context context)
	{
		_context = context;
		_sourcesList = new ArrayList<NewsSourceModel>();
		_addedViewModel = new ArrayList<NewsSourceModel>();
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
			AddViewModelList();
		}
		return true;
	}
	
	private void AddViewModelList() {
		for(NewsSourceModel model: _sourcesList)
		{
			if(model.IsAdded)
			{
				_addedViewModel.add(model);
			}
		}
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
		return _addedViewModel.get(position);
	}
	
	public NewsSourceModel GetItemFromAll(int position)
	{
		return _sourcesList.get(position);
	}
	
	public int GetSourcesCount()
	{
		return _addedViewModel.size();
	}
	
	public CharSequence GetPageTitle(int position)
	{
		return _addedViewModel.get(position).Title;
	}
	//TODO: Use iterator pattern instead
	public ArrayList<NewsSourceModel> GetNewsSource()
	{
		return _sourcesList;
	}
	
	public ArrayList<NewsSourceModel> GetAddedNewsSource()
	{
		return _addedViewModel;
	}
	
	public void SetAddStatus(int position,boolean isAdded)
	{
		NewsSourceModel model = _sourcesList.get(position);
		if(model != null)
		{
			model.IsAdded = isAdded;
			if(!model.IsAdded)
			{
				_addedViewModel.remove(model);
			}
			else
			{
				if(!_addedViewModel.contains(model))
				{
					_addedViewModel.add(model);
				}
				
			}
		}
	}
	
	public void AddNewsSource(NewsSourceModel model)
	{
		
		if(_sourcesList.contains(model))
		{
			int index = _sourcesList.indexOf(model);
			_sourcesList.set(index, model);
		}
		else
		{
			_sourcesList.add(model);
		}
		

		if(_addedViewModel.contains(model))
		{
			int index = _addedViewModel.indexOf(model);
			_addedViewModel.set(index, model);
		}
		else
		{
			_addedViewModel.add(model);
		}
	}
	
	public String GetRawSourceData()
	{
		String fileContent = DALWriter.ReadFile(_context);
		return fileContent;
	}
	
	public boolean PersistChanges()
	{
		// save the in-memory model to xml file
		try {
			DALWriter.SaveToFile(_context, _sourcesList);
			return true;
		} catch (IllegalArgumentException e) {

		} catch (IllegalStateException e) {

		} catch (IOException e) {

		}
		return false;

	}
}
