package cyber.app.call2app;

import android.app.Application;
import android.content.SharedPreferences;

public class Preferences extends Application {
	// Preferences Constants
	private final String PREFERENCES_NAME = "preferences";
	private final String LANGUAGE = "language";
	private final String FIRST_TIME = "first_time";

	private SharedPreferences preferences;
	private static Preferences pre;

	@Override
	public void onCreate() {
		super.onCreate();
		pre = this;
		preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
	}

	/**
	 * Get an instance of Preferences
	 * 
	 * @return
	 */
	public static Preferences getInstance() {
		return pre;
	}


	/**
	 * Save default language to preference
	 * 
	 * @param lan
	 *            language
	 */
	public void saveLanguage(String lan) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(LANGUAGE, lan);
		editor.commit();
	}

	

	
	/**
	 * Get server default language info from preference
	 * 
	 * @return null if not exist
	 */
	public String getLanguage() {
		return preferences.getString(LANGUAGE, null);
	}
	
	/**
	 * @return true if application is run first time
	 */
	public boolean isFirstTime() {
		return preferences.getBoolean(FIRST_TIME, true);
	}
	
	/**
	 * Set first time flag 
	 * 
	 * @param isFirst
	 */
	public void setFirstTime(boolean isFirst) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(FIRST_TIME, isFirst);
		editor.commit();
	}
	
	/**
	 * Save code-application pair to preference
	 * 
	 * @param code
	 *            code number
	 * @param app
	 *            package name
	 */
	public void saveCodeAppPair(String code, String app) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(code, app);
		editor.commit();
	}

	/**
	 * Get code-application pair from preference
	 * 
	 * @return null if not exist
	 */
	public String getCodeAppPair(String code) {
		return preferences.getString(code, null);
	}
	
	/**
	 * Save application-code pair to preference
	 * 
	 * @param app
	 *            package name
	 * @param code
	 *            code number
	 */
	public void saveAppCodePair(String app, String code) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(app, code);
		editor.commit();
	}

	/**
	 * Get application-code pair from preference
	 * 
	 * @return null if not exist
	 */
	public String getAppCodePair(String app) {
		return preferences.getString(app, null);
	}
	
	/**
	 * Remove 1 key-value pair from preference 
	 */
	public void removePair(String key) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();
	}
	
	/**
	 * Remove all store info
	 */
	public void removeAll() {
		Thread.setDefaultUncaughtExceptionHandler(null);
		System.gc();
	}
}
