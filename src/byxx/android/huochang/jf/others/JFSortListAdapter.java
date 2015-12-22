package byxx.android.huochang.jf.others;

import java.util.Vector;

import byxx.android.huochang.R;

//import com.hades.R;
//import com.hades.bean.ClassItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JFSortListAdapter extends BaseAdapter {

	private Vector<String> items;
	private Context context;
	private LayoutInflater mInflater;
	private int selectItem = -1;

	public JFSortListAdapter(Context context) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		items = new Vector<String>();
	}

	public String getMessageByIndex(int index) {
		return items.elementAt(index);
	}

	public void addItem(String item) {
		items.add(item);
		this.notifyDataSetChanged();
	}
	
	
	public void addAllDate(Vector<String> vector){
		items.addAll(vector);
	}

	public void copyItems(Vector<String> messages) {
		if (messages == null) {
			messages = new Vector<String>();
		}
		if (items != null && items.size() > 0) {
			for (int i = 0; i < items.size(); i++) {
				messages.add(items.get(i));
			}
		}
	}

	public void removeAll() {
		items.clear();
		this.notifyDataSetChanged();
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.class_part_item, null);
			holder = new ViewHolder();
//			holder.partLayout = (RelativeLayout) convertView
//					.findViewById(R.id.classGroupLayout);

//			holder.partName = (TextView) convertView
//					.findViewById(R.id.class_part_name);
			holder.className = (TextView) convertView.findViewById(R.id.class_item_name);
//			holder.mIcon = (ImageView) convertView
//					.findViewById(R.id.class_item_img);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String string = items.get(position);
		if(string != null){
			holder.className.setText(string);
		}
//		if (classItem != null) {
//			if (classItem.ifTop) {
//				holder.partLayout.setVisibility(View.VISIBLE);
//				holder.partName.setText(classItem.partName);
//				holder.className.setText(classItem.className);
//			} else {
//				holder.partLayout.setVisibility(View.GONE);
//				holder.partName.setText(classItem.partName);
//				holder.className.setText(classItem.className);
//			}

//			int id = context.getResources().getIdentifier(classItem.classIcon,
//					"drawable", context.getPackageName());
//			Drawable da = context.getResources().getDrawable(id);
//			holder.mIcon.setBackgroundDrawable(da);

//		}
		if (position == selectItem) {
			convertView.findViewById(R.id.class_item_name).setSelected(true);
		} else {
			convertView.findViewById(R.id.class_item_name).setSelected(false);
		}
		return convertView;
	}

	public class ViewHolder {
//		RelativeLayout partLayout;
//		TextView partId;
//		TextView partName;
		TextView className;
//		ImageView mIcon;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}
}
