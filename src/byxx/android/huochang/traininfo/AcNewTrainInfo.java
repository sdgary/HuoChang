package byxx.android.huochang.traininfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.traininfo.others.SortMenuFragment;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.internal.app.ActionBarImpl;
import com.actionbarsherlock.internal.app.ActionBarWrapper;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;



public class AcNewTrainInfo extends SlidingActivity implements
		ActionBar.TabListener {

	private  String[] CONTENT ;//= new String[] { "101", "102", "103"};
	private FragmentManager mFt = getSupportFragmentManager();
	private SortMenuFragment mMenuFragment;
	private SlidingMenu mSm;
	private ViewPager mViewPager;
	private MyAdapter mMyadapter;
	static final int CAMERACODE2 = 12;
	static final int CAMERACODE1 = 11;
	Hashtable<String, AcTrainInfoFragment> fragmentMap = new Hashtable<String, AcTrainInfoFragment>();
	String SD_CARD_TEMP_DIR;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.traininfo_sliding);
		setBehindContentView(R.layout.frame_menu);
		mViewPager = (ViewPager) findViewById(R.id.pager);                                                  
		getMyAdapter();
		//标头名
		CONTENT = MaStation.getInstance().getUser().getGdm();
		//初始化工具条
		initActionBar();
		FragmentTransaction fragmentTransaction = mFt.beginTransaction();
		mMenuFragment = new SortMenuFragment();
		fragmentTransaction.replace(R.id.menu, mMenuFragment);
		initSlidingMenu();
		fragmentTransaction.commit();
	}

	@Override
	protected void onDestroy() {
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
			mSm.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 2 + 50);
		} else {
			mSm.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 2 + 50);
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
		//保存数据大小
		mViewPager.setOffscreenPageLimit(0);//不做页面缓存
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
			//wj:滑动bug出现地方
			System.out.println(position);
			AcTrainInfoFragment fragment;
			if (fragmentMap.get(String.valueOf(position))==null) {
				fragment =  AcTrainInfoFragment.newInstance(CONTENT[position]);
				fragmentMap.put(String.valueOf(position),fragment);
			}else {
				fragment = fragmentMap.get(String.valueOf(position));
			}
			return fragment;
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
		if (fragmentMap.get(String.valueOf(arg0.getPosition()))!=null) {
			fragmentMap.get(String.valueOf(arg0.getPosition())).RefreshListView();
		}
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
	
	

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
			//
			mMenuFragment.mSortListAdapter.setSelectItem(position);
			mMenuFragment.mSortListAdapter.notifyDataSetInvalidated();
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
}
