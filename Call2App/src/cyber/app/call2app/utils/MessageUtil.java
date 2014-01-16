package cyber.app.call2app.utils;

import cyber.app.call2app.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MessageUtil {

	/**
	 * Show normal info
	 * 
	 * @param act
	 */
	public static void showDialogInfo(Activity act, int message) {
		// create dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		builder.setTitle(R.string.app_name);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setNegativeButton(R.string.btn_ok,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Show normal info
	 * 
	 * @param act
	 */
	public static void showDialogInfo(Activity act, String message) {
		// create dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		builder.setTitle(R.string.app_name);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setNegativeButton(R.string.btn_ok,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Show custom dialog
	 * 
	 * @param act
	 */
	public static void showCustomDialog(Activity act, String message, int resourceId) {
		// create dialog
		AlertDialog alertDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(act)
		.setTitle(message);
		final FrameLayout frameView = new FrameLayout(act);
		builder.setView(frameView);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alertDialog = builder.create();
		LayoutInflater inflater = alertDialog.getLayoutInflater();
		inflater.inflate(resourceId, frameView);
		alertDialog.show();			
	}

	/**
	 * Show custom dialog
	 * 
	 * @param act
	 */
	public static void showCustomDialog(Activity act, int message, int resourceId) {
		// create dialog
		AlertDialog alertDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(act)
		.setTitle(message);
		final FrameLayout frameView = new FrameLayout(act);
		builder.setView(frameView);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alertDialog = builder.create();
		LayoutInflater inflater = alertDialog.getLayoutInflater();
		inflater.inflate(resourceId, frameView);
		alertDialog.show();	
	}

	/**
	 * Show message by Toast
	 * 
	 * @param context
	 * @param messageId
	 */
	public static void showToastInfo(Context context, int messageId) {
		Toast.makeText(context, messageId, Toast.LENGTH_LONG).show();
	}

	/**
	 * Show message by Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showToastInfo(Context context, String message) {
		if (!StringUtil.isEmptyOrNull(message)) {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}
}
