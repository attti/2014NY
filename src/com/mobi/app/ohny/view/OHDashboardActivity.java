package com.mobi.app.ohny.view;

import java.util.Date;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.app.ohny.R;
import com.mobi.app.ohny.util.OHEvent.EventType;
import com.mobi.app.ohny.util.OHTimeUtil;

@SuppressLint("NewApi")
public class OHDashboardActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private static final String mTitle = "OHNY 2014";
	private static final String[] mDrawerOptions = { "Map", "Live List",
			"My Note" };
	private BaseAdapter adapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = View.inflate(OHDashboardActivity.this,
						R.layout.drawer_item, null);
			TextView tv = (TextView) convertView.findViewById(R.id.title);
			tv.setText(mDrawerOptions[position]);
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public int getCount() {
			return mDrawerOptions.length;
		}
	};
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			v.setActivated(true);
			// load fragment accordingly
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setNavDrawer();
//		getFragmentManager().beginTransaction().add(R.id.content_frame, fragment);
	}

	@Override
	protected void onResume() {
		super.onResume();
		throwToast();

	}
	private void throwToast() {
		String toastMsg = new String();
		Date now = new Date();
		Map dateUpdate = OHTimeUtil.instance().getDateUpdate(now);
		if (dateUpdate.containsKey(EventType.EVENT_ON))
			toastMsg = "The OHNY is on, day "+dateUpdate.get(EventType.EVENT_ON);
		else if (dateUpdate.containsKey(EventType.EVENT_COMING)) {
			toastMsg = "The OHNY is coming in %d day(s).";
			toastMsg = String.format(toastMsg, dateUpdate.get(EventType.EVENT_COMING));
		}
		else if (dateUpdate.containsKey(EventType.EVENT_PASSED))
			toastMsg = "Sorry, OHNY this year is gone. ";
		Log.d(getClass().getName(), toastMsg);
		Toast.makeText(OHDashboardActivity.this,  toastMsg, Toast.LENGTH_LONG).show();
			
	}

	private void setNavDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(itemClickListener);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
