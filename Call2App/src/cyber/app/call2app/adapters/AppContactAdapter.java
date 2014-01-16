/**
 * 
 */
package cyber.app.call2app.adapters;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cyber.app.call2app.Preferences;
import cyber.app.call2app.R;
import cyber.app.call2app.utils.StringUtil;

/**
 * @author vietdung
 *
 */
public class AppContactAdapter extends ArrayAdapter<ResolveInfo> {
	private int mViewResId;
	private LayoutInflater mInflater;
	private List<ResolveInfo> appList;
	private PackageManager pm;
	private Preferences pre;
	private Activity act;

	public AppContactAdapter(Context context, int mViewResId, Activity act, List<ResolveInfo> appList) {
		super(context, mViewResId);
		this.pm = context.getPackageManager();
		this.pre = Preferences.getInstance();
		this. act = act;
		this.appList = appList;
		
		this.mInflater = 
				(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mViewResId = mViewResId;
		
	}

	@Override
	public int getCount() {
		return appList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResId, null);
		
		ResolveInfo item = appList.get(position);
		CharSequence name = item.loadLabel(pm);
		Drawable icon = item.loadIcon(pm);
		final String packageName = item.activityInfo.packageName;
		final String code = pre.getAppCodePair(packageName);
		
		// set application-icon for view
		ImageView appIcon = (ImageView)convertView.findViewById(R.id.appIcon);
		appIcon.setImageDrawable(icon);
		
		// set application-name for view
		TextView appName = (TextView)convertView.findViewById(R.id.appInfo);
		appName.setText(name);
		
		// if app-code pair exist, set it into EditText
		final EditText appCode = (EditText)convertView.findViewById(R.id.appCode);
		appCode.setInputType(InputType.TYPE_CLASS_PHONE);
		
		if (!StringUtil.isEmptyOrNull(code)) {
			appCode.setText(code);
		}
		
		final Button btnEdit = (Button)convertView.findViewById(R.id.btnSetCodeNumber);
		btnEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (appCode.isEnabled()) {	// save new info
					final String codeNumber = appCode.getText().toString();
					final String oldPackageName = pre.getCodeAppPair(codeNumber);
					
					// check if codeNumber is duplicated
					if (!StringUtil.isEmptyOrNull(oldPackageName)){
						AlertDialog.Builder builder = new AlertDialog.Builder(act);
						builder.setTitle(R.string.warning);
						builder.setMessage(R.string.duplicated);
						builder.setPositiveButton(R.string.btn_ok, 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										pre.removePair(oldPackageName);
										updatePairValue(packageName, code, codeNumber);
										AppContactAdapter.this.notifyDataSetChanged();
									}
								});
						builder.setNegativeButton(R.string.btn_cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.cancel();
									}
								});
						AlertDialog alert = builder.create();
						alert.show();
					} else {
						updatePairValue(packageName, code, codeNumber);
					}
					
					appCode.setEnabled(false);
					btnEdit.setText(view.getResources().getString(R.string.btn_setCodeNumber));
					btnEdit.setTextColor(Color.RED);
					AppContactAdapter.this.notifyDataSetChanged();
					
				} else {	// make code-EditText enable
					btnEdit.setText(view.getResources().getString(R.string.btn_save));
					btnEdit.setTextColor(Color.RED);
					appCode.setEnabled(true);
					appCode.requestFocus();
				}
			}

			private void updatePairValue(final String packageName,
					final String oldCode, String newCode) {
				if (!StringUtil.isEmptyOrNull(newCode)) {
					// update code-app pair and app-code pair to preference
					if (!StringUtil.isEmptyOrNull(oldCode))
						pre.removePair(oldCode);
					pre.saveAppCodePair(packageName, newCode);
					pre.saveCodeAppPair(newCode, packageName);
				}
			}
		});
		
		return convertView;
	}
	
}
