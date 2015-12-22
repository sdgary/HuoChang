package byxx.android.huochang.message;

import java.util.List;
import java.util.Map;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleExpandableListAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyTalkManList extends SimpleExpandableListAdapter {

	Handler handler = null;

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public MyTalkManList(Context context,
			List<? extends Map<String, ?>> groupData, int groupLayout, int i,
			String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, int j, String[] childFrom, int[] childTo) {
		super(context, groupData, groupLayout, groupFrom, groupTo, childData,
				childLayout, childFrom, childTo);
		// TODO Auto-generated constructor stub
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CheckBox checkBox = new CheckBox(parent.getContext());
		try {
			Object obj = getChild(groupPosition, childPosition);
			if (obj instanceof Map<?, ?>) {
				checkBox.setText(((Map<?, ?>) obj).get("NAME").toString());
			}
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (getHandler() != null) {
						Message msg = new Message();
						msg.what = Constant.MSG_ID_SELECTED_MAN;
						Bundle bu = new Bundle();
						bu.putString("NAME", buttonView.getText().toString());
						bu.putBoolean("STATE", isChecked);
						msg.setData(bu);
						getHandler().sendMessage(msg);
					}
				}
			});
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return checkBox;
	}
}
