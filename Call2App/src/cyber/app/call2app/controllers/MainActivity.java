package cyber.app.call2app.controllers;

import java.util.List;

import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.searchboxsdk.android.StartAppSearch;
import com.startapp.android.publish.StartAppAd;

import cyber.app.call2app.Constants;
import cyber.app.call2app.Preferences;
import cyber.app.call2app.R;
import cyber.app.call2app.adapters.AppContactAdapter;
import cyber.app.call2app.utils.Common;
import cyber.app.call2app.utils.MessageUtil;

public class MainActivity extends ListActivity {
	private PackageManager pm;
	private Preferences pre;

	// for advertisement
	private StartAppAd startAppAd = new StartAppAd(this);

	TimeConsumeTask task = new TimeConsumeTask(this) {
		@Override
		protected List<ResolveInfo> doInBackground(Void... params) {
			super.doInBackground(params);
			return Common.getApps(pm);

		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			if (result != null && result instanceof List<?>) {
				List<ResolveInfo> appList = (List<ResolveInfo>)result;
				AppContactAdapter adapter = 
						new AppContactAdapter
						(getApplicationContext(), R.layout.list_row, MainActivity.this, appList);

				MainActivity.this.setListAdapter(adapter);

				// show help to user if it is first time they run this application
				if (pre.isFirstTime()) {
					MessageUtil.showCustomDialog(MainActivity.this, R.string.howtouse, R.layout.help);
					pre.setFirstTime(false);
				}

			} else {
				MessageUtil.showDialogInfo(this.activity,
						R.string.cannotGetApp);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pm = getPackageManager();
		pre = Preferences.getInstance();

		// for advertisement
		StartAppAd.init(this, Constants.STARTAPP_DEV_ID, Constants.STARTAPP_APP_ID);
		StartAppSearch.init(this, Constants.STARTAPP_DEV_ID, Constants.STARTAPP_APP_ID);
		StartAppSearch.showSearchBox(this);

		task.execute();
	}

	@Override
	public void onResume(){
		super.onResume();
		startAppAd.onResume();
	}

	@Override
	public void onBackPressed() {
		startAppAd.onBackPressed();
		super.onBackPressed();
	}

	@Override
	public void onPause() {
		super.onPause();
		startAppAd.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_help:
			MessageUtil.showCustomDialog(MainActivity.this, R.string.howtouse, R.layout.help);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
