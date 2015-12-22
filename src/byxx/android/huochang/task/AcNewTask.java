package byxx.android.huochang.task;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.task.others.TaskSortMenuFragment;


import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.internal.app.ActionBarImpl;
import com.actionbarsherlock.internal.app.ActionBarWrapper;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;





public class AcNewTask extends SlidingActivity implements
		ActionBar.TabListener {

	private  String[] CONTENT ;//= new String[] { "101", "102", "103"};
	private FragmentManager mFt = getSupportFragmentManager();
	private TaskSortMenuFragment mMenuFragment;
	private SlidingMenu mSm;
	private ViewPager mViewPager;
	private MyAdapter mMyadapter;
	String taskflag = null; //火车或汽车 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.task_sliding);
		setBehindContentView(R.layout.frame_menu);
		taskflag = getIntent().getStringExtra("taskflag");
		mViewPager = (ViewPager) findViewById(R.id.pager);                                                  
		getMyAdapter();
		CONTENT = MaStation.getInstance().getUser().getGdm();
		initActionBar();
		FragmentTransaction fragmentTransaction = mFt.beginTransaction();
		mMenuFragment = new TaskSortMenuFragment();
		fragmentTransaction.replace(R.id.menu, mMenuFragment);
		initSlidingMenu();
		fragmentTransaction.commit();

		
		this.onPageChangeListener.onPageSelected(0);
		this.toggle();
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}
	
	public MyAdapter getMyAdapter(){
		if(mMyadapter == null){
			mMyadapter = new MyAdapter(mFt);
		}
		return mMyadapter;
	}
	
	
	private void initSlidingMenu() {

		mSm = getSlidingMenu();
		// 设置阴影宽度
		mSm.setShadowWidth(getWindowManager().getDefaultDisplay().getWidth() / 40);
		// 设置菜单占屏幕的比例
		// 根据是否是平板,设置对应的菜单占据比例
		if (isTablet(this)) {
			mSm.setBehindOffset(getWindowManager().getDefaultDisplay()
					.getWidth() / 2 + 50);
		} else {
			mSm.setBehindOffset(getWindowManager().getDefaultDisplay()
					.getWidth() / 2 + 50);
		}
		// 设置滑动时菜单的是否淡入淡出
		mSm.setFadeEnabled(true);
		// 设置淡入淡出的比例
		mSm.setFadeDegree(0.35f);
		// 设置要使菜单滑动，触碰屏幕的范围
		mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

	}

	private void initActionBar() {

		ActionBar actionBarSherlock = getSupportActionBar();
		mViewPager.setAdapter(mMyadapter);
		mViewPager.setOnPageChangeListener(onPageChangeListener);
		// 模拟创建fragment实例（只在demo中使用，可根据实际项目创建）
		for (int i = 0; i < mMyadapter.getCount(); ++i) {
			actionBarSherlock.addTab(actionBarSherlock.newTab().setText(CONTENT[i]).setTabListener(this));
		}

		// 如果为平板设备，则利用反射机制将actionbar的tabview嵌套在同一级
		// 否则tabview将显示到第二级
		if (isTablet(this)) {
			if (actionBarSherlock instanceof ActionBarImpl) {
				enableEmbeddedTabs(actionBarSherlock);

			} else if (actionBarSherlock instanceof ActionBarWrapper) {
				try {
					Field actionBarField = actionBarSherlock.getClass()
							.getDeclaredField("mActionBar");
					actionBarField.setAccessible(true);
					enableEmbeddedTabs(actionBarField.get(actionBarSherlock));
				} catch (Exception e) {
				}
			}
		} else {

		}
		actionBarSherlock.setDisplayHomeAsUpEnabled(false);
		actionBarSherlock.setHomeButtonEnabled(true);
		actionBarSherlock.setDisplayShowTitleEnabled(false);
		actionBarSherlock.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	}

	class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			return AcNewTaskFragment.newInstance(CONTENT[position],taskflag,1);//(CONTENT[position % CONTENT.length]);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
	
	
//	ActionBar actionBarnew;
//	
//	
//	public com.actionbarsherlock.app.ActionBar getActionBar(){
//		if(actionBarnew == null){
//			actionBarnew = getSupportActionBar();
//		}
//		return actionBarnew;
//	}

	public ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {

			final ActionBar actionBar = getSupportActionBar();
			actionBar.setSelectedNavigationItem(position);
			switch (position) {
			case 0:
				mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				break;
			default:
				mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				break;
			}
			//屏蔽 因为会引起空指针报错 ，下面代码是为了侧面菜单选到对应的选项
//			mMenuFragment.mSortListAdapter.setSelectItem(position);
//			mMenuFragment.mSortListAdapter.notifyDataSetInvalidated();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * 官方用法（判断当前设备是否为平板设备）
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	private void enableEmbeddedTabs(Object actionBar) {
		try {
			Method setHasEmbeddedTabsMethod = actionBar.getClass()
					.getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
			setHasEmbeddedTabsMethod.setAccessible(true);
			setHasEmbeddedTabsMethod.invoke(actionBar, true);
		} catch (Exception e) {
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case android.R.id.home:

			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void sort(String idcard) {
		// TODO Auto-generated method stub
		
		
		try {
			List<ZydDto> dtos = new ArrayList<ZydDto>();
			dtos = MoTask.deepCopy(MaStation.getInstance().getMoTask().getQZydDtos());
			List<ZydDto> islist = new ArrayList<ZydDto>();
			for(int i = 0 ;i<dtos.size();i++){
					if(dtos.get(i).getCardId().equals(idcard)){ 
						islist.add(dtos.get(i));
						dtos.remove(i);
					}
				}
			for(int i = 0 ;i<dtos.size();i++){
				 if(dtos.get(i).getPbFlag() == 1){
					 islist.add(dtos.get(i));
				 }
			}
//			if(pbflag == 1){
//				
//				listView.setAdapter(getBaTask());
//				getBaTask().setDatas(unfList);
//				getBaTask().notifyDataSetChanged();
//			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
