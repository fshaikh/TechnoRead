package com.example.technoread;

import java.util.Hashtable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	Hashtable _webViewColl = new Hashtable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//AppService.GetInstance(getApplicationContext()).BackupSourceFile();
		switch(item.getItemId())
		{
		case R.id.menu_openinbrowser:
			OpenInBrowser();
			break;
		case R.id.menu_share:
			Toast.makeText(getApplicationContext(), "share", Toast.LENGTH_LONG).show();
			SendShareBroadcast();
			break;
		}
		return true;
	}
	
	private void SendShareBroadcast() {
		// TODO Auto-generated method stub
		WebView wv =GetCurrentWebView() ;
		String url = wv.getUrl();
		// send share broadcast
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT	, url);
		shareIntent.setType("text/plain");
		startActivity(Intent.createChooser(shareIntent, "Share URL"));
	}

	private void OpenInBrowser() {
		// TODO Auto-generated method stub
		WebView wv =GetCurrentWebView() ;
		String url = wv.getUrl();
		Intent openIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(openIntent);
	}

	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		WebView wv =GetCurrentWebView() ;
	    // Check if the key event was the Back button and if there's history 
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) { 
	    	wv.goBack(); 
	        return true; 
	    } 
	    // If it wasn't the Back key or there's no web page history, bubble up to the default 
	    // system behavior (probably exit the activity) 
	    return super.onKeyDown(keyCode, event); 
	}
	
	public WebView GetCurrentWebView()
	{
		return (WebView) _webViewColl.get(mViewPager.getCurrentItem());
	}

	public void SetCurrentWebView(int position,WebView wv)
	{
		_webViewColl.put(position, wv);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			NewsSourceModel model = AppService.GetInstance(getApplicationContext()).GetItem(position);
			
			Fragment fragment = new NewsSectionFragment();
			Bundle args = new Bundle();
			args.putParcelable(Constants.MODELKEY, model);
			args.putInt(Constants.POSITIONKEY, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return AppService.GetInstance(getApplicationContext()).GetSourcesCount();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return AppService.GetInstance(getApplicationContext()).GetPageTitle(position);
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class NewsSectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */


		public NewsSectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Bundle bundle = this.getArguments();
			
			NewsSourceModel model =  bundle.getParcelable(Constants.MODELKEY);
			
			WebView webView = new WebView(getActivity());
			webView.loadUrl(model.Url);
			if(model.OpenInline)
			{
				webView.setWebViewClient(new WebViewClient());
			}
			webView.getSettings().setJavaScriptEnabled(true);
			int position = bundle.getInt(Constants.POSITIONKEY);
			((MainActivity)getActivity()).SetCurrentWebView(position,webView);
			
			return webView;
		}
	}

}
