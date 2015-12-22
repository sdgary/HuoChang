package byxx.android.huochang.gdcarcheck.others;


import java.util.Vector;

import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;

import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.gdcarcheck.AcGdCarCheck;




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

public class CCSortMenuFragment extends Fragment {

	private ListView mSortList;
	Button gohome;
	public CCSortListAdapter mSortListAdapter;
//	private PullPersonPaseService mPullPersonPaseService = null;
	private AcGdCarCheck acGdCarCheck;
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
		acGdCarCheck = (AcGdCarCheck) getActivity(); 
		mSortList = (ListView) v.findViewById(R.id.classItemListView);
		gohome = (Button)v.findViewById(R.id.traininfo_menu_gohome);
		mSortListAdapter = new CCSortListAdapter(getActivity());
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
				acGdCarCheck.onPageChangeListener.onPageSelected(arg2);
				acGdCarCheck.toggle();
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


	

}
