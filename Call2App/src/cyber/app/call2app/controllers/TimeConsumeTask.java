package cyber.app.call2app.controllers;

import cyber.app.call2app.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class TimeConsumeTask extends AsyncTask<Void, Void, Object> {
	protected Activity activity;
	protected ProgressDialog progressDialog;

	/**
	 * Constructor
	 * 
	 * @param activity
	 *            Activity
	 */
	public TimeConsumeTask(Activity activity) {
		this.activity = activity;
	}


	@Override
	protected Object doInBackground(Void... params) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Show progress dialog
				progressDialog = ProgressDialog.show(activity, "",
						activity.getString(R.string.processing), true);
				progressDialog.setCancelable(false);
			}
		});
		
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		progressDialog.dismiss();
	}
}
