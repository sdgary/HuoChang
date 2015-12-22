package byxx.android.huochang.task;



import java.util.ArrayList;
import java.util.List;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.R;
import byxx.android.huochang.function.AcFunction;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.MenuItem;




public class AcTask extends FragmentActivity{
	AcTaskFragment doTaskFragment;
	AcTaskFragment undoTaskFragment;
	
	private ViewPager m_vp;
	//页面列表
	private List<Fragment> fragmentList;
	//标题列表
	List<String>   titleList    = new ArrayList<String>();
	//通过pagerTabStrip可以设置标题的属性
	private PagerTabStrip pagerTabStrip;
	
	private PagerTitleStrip pagerTitleStrip;
	String taskflag = null; //火车或汽车 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task);
		AppManager.getAppManager().addActivity(this);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		
		taskflag = getIntent().getStringExtra("taskflag");
		m_vp = (ViewPager)findViewById(R.id.viewpager);
		
		pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab);
		//设置下划线的颜色
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_blue_bright)); 
		//设置背景的颜色
		pagerTabStrip.setBackgroundColor(getResources().getColor(android.R.color.black));
		pagerTabStrip.setTextColor(getResources().getColor(android.R.color.white));
//		pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//		pagerTitleStrip=(PagerTitleStrip) findViewById(R.id.pagertab);
//		//设置背景的颜色
//		pagerTitleStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
		
//		mfragment1 = new fragment1();
//		mfragment2 = new fragment2();
//		mfragment3 = new fragment3();
		doTaskFragment = AcTaskFragment.newInstance(taskflag,1);
		undoTaskFragment = AcTaskFragment.newInstance(taskflag,3);
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(doTaskFragment);
		fragmentList.add(undoTaskFragment);
		 titleList.add("现在 ");
	    titleList.add("完成");
	   


		
		m_vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
//		m_vp.setOffscreenPageLimit(2);
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}
	
	public class MyViewPagerAdapter extends FragmentPagerAdapter{
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titleList.get(position);
		}

		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				buHomeOnClick();
//				Toast.makeText(this, "bu", Toast.LENGTH_SHORT).show();
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}


	private void buHomeOnClick() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(AcTask.this,AcFunction.class);
		startActivity(intent);
		finish();
	} 
	
}
