package com.example.technoread;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class AppWebViewClient extends WebViewClient {
	private ReaderView _readerView = null;
	
	public AppWebViewClient(ReaderView readerView)
	{
		_readerView = readerView;
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
		_readerView.ShowProgress(false);
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		super.onPageStarted(view, url, favicon);
		_readerView.ShowProgress(true);
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
		return false;
	}

}
