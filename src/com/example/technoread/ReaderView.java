package com.example.technoread;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ReaderView extends LinearLayout {
	private WebView _webView = null;
	private ProgressBar _progressBar = null;
	private NewsSourceModel _model = null;
	private Context _context = null;
	private ProgressDialog _progressDialog = null;
	
	public ReaderView(Context context) {
		super(context);
		_context = context;
		// inflate the layout
		InflateLayout();
	}
	
	private void InflateLayout() {
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.readerviewlayout, this, true);
		// get references to contained views
		_webView = (WebView)findViewById(R.id._webView);
		_progressBar = (ProgressBar)findViewById(R.id._progressBar);
		_progressDialog = new ProgressDialog(_context);
	}

	public void Init(NewsSourceModel model)
	{
		_model = model;
		_webView.loadUrl(_model.Url);
		if(_model.OpenInline)
		{
			_webView.setWebViewClient(new AppWebViewClient(this));
		}
		_webView.getSettings().setJavaScriptEnabled(true);
		
	}

	public String GetUrl()
	{
		return _webView.getUrl();
	}
	
	public boolean GoBack(int keyCode, KeyEvent event)
	{
		// Check if the key event was the Back button and if there's history 
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && _webView.canGoBack()) { 
	    	_webView.goBack(); 
	        return true; 
	    } 
	    return false;
	}
	
	public void ShowProgress(Boolean show)
	{
		if(show)
		{
			//_progressBar.setVisibility(ProgressBar.VISIBLE);
			//_progressDialog.setMessage("Loading...");
			//_progressDialog.show();
			SendRefreshBroadcast(true);
		}
		else
		{
			//_progressBar.setVisibility(ProgressBar.GONE);
			//_progressDialog.hide();
			SendRefreshBroadcast(false);
		}
	}

	private void SendRefreshBroadcast(boolean status) {
		Intent doneintent = new Intent("refresh");
		doneintent.putExtra(SPConstants.REFRESHINTENT_KEY, status);
		LocalBroadcastManager.getInstance(_context).sendBroadcast(doneintent);
	}
}
