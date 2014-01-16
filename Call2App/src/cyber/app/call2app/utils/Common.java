package cyber.app.call2app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import cyber.app.call2app.Constants;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Common {
	/**
	 * Set default language
	 * 
	 * @param act
	 * @param defLanguage
	 */
	public static void setDefaultLanguage(Activity act, String defLanguage) {
		if (defLanguage != null) {
			// Change locale settings in the app.
			Resources res = act.getResources();
			DisplayMetrics dm = res.getDisplayMetrics();
			android.content.res.Configuration conf = res.getConfiguration();
			conf.locale = new Locale(defLanguage);
			res.updateConfiguration(conf, dm);
		}
	}

	/**
	 * Determine the network connection
	 * 
	 * @param context
	 *            the Context
	 * @return TRUE if network connect is establishing
	 */
	public static boolean isConnect(Context context) {
		// Checking network configuration
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		return networkInfo != null && networkInfo.isConnectedOrConnecting();
	}

	/**
	 * Check GPS provider
	 * 
	 * @param context
	 * @return true if enable or false if not
	 */
	public static boolean isGPSProviderEnabled(Context context) {
		LocationManager locManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		boolean value = locManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (!value) {
			value = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}

		return value;
	}

	/**
	 * Check Blue tooth status
	 * @return
	 */
	public static boolean isBluetoothOn() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		// Check device supporting blue tooth or not
		if (mBluetoothAdapter != null) {
			return mBluetoothAdapter.isEnabled();
		}

		return false;
	}

	/**
	 * using WSG84 using the Metric system
	 * 
	 * @return meters distance
	 */
	public static float getDistance(double startLati, double startLongi,
			double goalLati, double goalLongi) {
		float[] resultArray = new float[5]; // meters
		Location.distanceBetween(startLati, startLongi, goalLati, goalLongi,
				resultArray);
		return resultArray[0];
	}

	/**
	 * Hide keyboard
	 * 
	 * @param act
	 *            Activity
	 * @param view
	 */
	public static void hideKeyboard(Activity act, View view) {
		InputMethodManager imm = (InputMethodManager) act
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * Get file content by filename
	 * 
	 * @param c
	 * @param filename
	 * @return content String
	 */
	public static String getFileContent(Context c, String filename) {
		try {
			InputStream fin = c.getAssets().open(filename);
			byte[] buffer = new byte[fin.available()];
			fin.read(buffer);
			fin.close();
			return new String(buffer);
		} catch (IOException e) {
			Log.e("inspector", e.getLocalizedMessage());
		}
		return "";
	}

	/**
	 * Check the schedule has expired or not
	 * 
	 * @param endDate
	 * @return
	 */
	public static boolean hasNotExpired(Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);

		// Check expired date
		return (endDate == null || cal.getTime().before(endDate));
	}
	
	/**
	 * Get list applications
	 * 
	 * @param pm
	 * @return list application info
	 */
	public static List<ResolveInfo> getApps(PackageManager pm) {
		// Create filter to search applications
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		// Get list applications
		List<ResolveInfo> listApps = pm.queryIntentActivities(intent,
				PackageManager.PERMISSION_GRANTED);

		Map<String, ResolveInfo> map = new HashMap<String, ResolveInfo>();
		for (ResolveInfo info : listApps) {
			map.put(info.activityInfo.packageName, info);
		}

		// Remove current app
		map.remove(Constants.CURRENT_APP);

		List<ResolveInfo> result = new ArrayList<ResolveInfo>();
		Iterator<Entry<String, ResolveInfo>> iterator = map.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, ResolveInfo> mapEntry = (Entry<String, ResolveInfo>) iterator
					.next();
			Log.d(Constants.TAG, mapEntry.getValue().activityInfo.name);
			result.add(mapEntry.getValue());
		}

		// Sort by application name
		Collections.sort(result, new ResolveInfo.DisplayNameComparator(pm));

		return result;
	}
	
	public static void hideApp(PackageManager pm) {
	}
}
