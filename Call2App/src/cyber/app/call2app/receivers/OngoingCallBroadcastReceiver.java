package cyber.app.call2app.receivers;

import cyber.app.call2app.Preferences;
import cyber.app.call2app.utils.StringUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OngoingCallBroadcastReceiver extends BroadcastReceiver {

	String diledNumber;
	Preferences pre;
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		diledNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
		
		Log.i("cybertest", "number: "+diledNumber);
		
		pre = Preferences.getInstance();
		String app = pre.getCodeAppPair(diledNumber);
		//checking, get and run suitable application
		if(!StringUtil.isEmptyOrNull(app)) {
			Intent runIntent = context.getPackageManager()
					.getLaunchIntentForPackage(app);
			context.startActivity(runIntent);
			
			// Cancel our call.
			setResultData(null);
			
		}else {
			// don't know
		}
	}
}
