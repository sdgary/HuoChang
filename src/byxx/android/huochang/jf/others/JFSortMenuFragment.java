package byxx.android.huochang.jf.others;


import java.util.Vector;

import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.dw.AcJzx;
import byxx.android.huochang.dw.AcDW;
import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.jf.AcAllDelivery;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class JFSortMenuFragment extends Fragment {

	private ListView mSortList;
	Button gohome;
	public JFSortListAdapter mSortListAdapter;
//	private PullPersonPaseService mPullPersonPaseService = null;
	private AcAllDelivery AllDelivery;
	public Vector<String> data = new Vector<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.class_list_screen, container, false);
		AllDelivery = (AcAllDelivery) getActivity(); 
		mSortList = (ListView) v.findViewById(R.id.classItemListView);
		gohome = (Button)v.findViewById(R.id.traininfo_menu_gohome);
		mSortListAdapter = new JFSortListAdapter(getActivity());
		mSortListAdapter.setSelectItem(0);
		mSortList.setAdapter(mSortListAdapter);
		data.clear();
		for(String s:MaStation.getInstance().getUser().getGdm()){
			data.addElement(s);
		}
		
		mSortListAdapter.removeAll();
		mSortListAdapter.addAllDate(data);
		
		mSortList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mSortListAdapter.setSelectItem(arg2);
				mSortListAdapter.notifyDataSetInvalidated();
				AllDelivery.onPageChangeListener.onPageSelected(arg2);
				AllDelivery.toggle();
			}
		});
		gohome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(),AcFunction.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		
		return v;
	}


	
//	private void addAdapterItem(Vector<ClassItem> data) {
//		Vector<ClassItem> classItem = new Vector<ClassItem>();
//		classItem.removeAllElements();
//
//		ClassItem temp = null;
//		Set<Integer> set = new HashSet<Integer>();
//		if (data != null && data.size() > 0) {
//			for (int i = 0; i < data.size(); i++) {
//				// 获取数据
//				temp = data.get(i);
//				// 判断是否存在这个partid 如果存在
//				// 说明此条数据是在同一个栏目下
//				if (set.contains(temp.partId)) {
//												
//					classItem.add(temp);
//				} else {
//					// 设置置顶 也就是显示栏
//					// 将此条partid 添加到set 以便后面判断
//					temp.ifTop = true;
//					set.add(temp.partId);
//					classItem.add(temp);
//
//				}
//			}
//			mSortListAdapter.removeAll();
//			for (ClassItem item : classItem) {
//				mSortListAdapter.addItem(item);
//			}
//		}
//	}
}
