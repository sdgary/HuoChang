package byxx.android.huochang.dw;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.function.AcFunction;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


public class AcDW extends TabActivity {
	private TabHost mTabHost = null;
//    private TabSpec mTab1;
//    private TabSpec mTab2;
//    private TabSpec mTab3;
    private TabSpec mTab;
    Button buReturnHome = null;
    Bundle bundle = null;
    Context context;
    int inum = 0;
//	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		   setContentView(R.layout.qxc_jh);
//		   AppManager.getAppManager().addActivity(this);
		   
			final ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(true);
			
			context = this;
		 mTabHost = getTabHost();
		 String[] s = MaStation.getInstance().getUser().getGdm();
		 inum = s.length;
	        // Main tab
		 if( inum > 0 && s[0] != null){
			 mTab = mTabHost.newTabSpec("tab_qxc_jh_0");
			 mTab.setIndicator(s[0].toString());
			 mTab.setContent(R.id.tab_qxc_jh_0);
			 Intent iTab = new Intent(AcDW.this,AcDWTab.class);
			 bundle = new Bundle();
			 bundle.putString("tabnum",s[0].toString());
			 iTab.putExtras(bundle);
			 mTab.setContent(iTab);
		     mTabHost.addTab(mTab);
		 }
		 if(inum > 1 &&s[1] != null){
			 mTab = mTabHost.newTabSpec("tab_qxc_jh_1");
			 mTab.setIndicator(s[1].toString());
			 mTab.setContent(R.id.tab_qxc_jh_1);
			 Intent iTab = new Intent(AcDW.this,AcDWTab.class);
			 bundle = new Bundle();
			 bundle.putString("tabnum",s[1].toString());
			 iTab.putExtras(bundle);
			 mTab.setContent(iTab);
			 mTabHost.addTab(mTab);
		 }
		 if(inum > 2 &&s[2] != null){
			 mTab = mTabHost.newTabSpec("tab_qxc_jh_2");
			 mTab.setIndicator(s[2].toString());
			 mTab.setContent(R.id.tab_qxc_jh_2);
			 Intent iTab = new Intent(AcDW.this,AcDWTab.class);
			 bundle = new Bundle();
			 bundle.putString("tabnum",s[2].toString());
			 iTab.putExtras(bundle);
			 mTab.setContent(iTab);
			 mTabHost.addTab(mTab);
		 }
		 if(inum > 3 &&s[3] != null){
			 mTab = mTabHost.newTabSpec("tab_qxc_jh_3");
			 mTab.setIndicator(s[3].toString());
			 mTab.setContent(R.id.tab_qxc_jh_3);
			 Intent iTab = new Intent(AcDW.this,AcDWTab.class);
			 bundle = new Bundle();
			 bundle.putString("tabnum",s[3].toString());
			 iTab.putExtras(bundle);
			 mTab.setContent(iTab);
			 mTabHost.addTab(mTab);
		 }
		 if(inum > 4 && s[4] != null){
			 mTab = mTabHost.newTabSpec("tab_qxc_jh_4");
			 mTab.setIndicator(s[4].toString());
			 mTab.setContent(R.id.tab_qxc_jh_4);
			 Intent iTab = new Intent(AcDW.this,AcDWTab.class);
			 bundle = new Bundle();
			 bundle.putString("tabnum",s[4].toString());
			 iTab.putExtras(bundle);
			 mTab.setContent(iTab);
			 mTabHost.addTab(mTab);
		 }

		 
//		 mTab1 = mTabHost.newTabSpec("tab_qxc_jh_1");
//		 mTab1.setIndicator("线 一");
//		 mTab1.setContent(R.id.tab_qxc_jh_1);
//		 Intent iTab1 = new Intent(AcQXC.this,AcGJHTab.class);
//		 bundle = new Bundle();
//		 bundle.putInt("tabnum",1);
//		 iTab1.putExtras(bundle);
//		 mTab1.setContent(iTab1);
//	     mTabHost.addTab(mTab1);
//	     
//	     
//	     mTab2 = mTabHost.newTabSpec("tab_qxc_jh_2");
//	     mTab2.setIndicator("线 二");
//	     mTab2.setContent(R.id.tab_qxc_jh_2);
//	     Intent iTab2 = new Intent(AcQXC.this,AcGJHTab.class);
//		 bundle = new Bundle();
//		 bundle.putInt("tabnum",2);
//		 iTab2.putExtras(bundle);
//		 mTab2.setContent(iTab2);
//	     mTabHost.addTab(mTab2);
//	     
//	     
//	     mTab3 = mTabHost.newTabSpec("tab_qxc_jh_3");
//	     mTab3.setIndicator("线 三");
//	     mTab3.setContent(R.id.tab_qxc_jh_3);
//	     Intent iTab3 = new Intent(AcQXC.this,AcGJHTab.class);
//		 bundle = new Bundle();
//		 bundle.putInt("tabnum",3);
//		 iTab3.putExtras(bundle);
//		 mTab3.setContent(iTab3);
//	     mTabHost.addTab(mTab3);
	     
	     int childCount = mTabHost.getTabWidget().getChildCount();
	        for (int i = 0; i < childCount; i++) {
	            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 80;
	            TextView tv=(TextView)mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
	            tv.setTextColor(Color.WHITE);
	            tv.getPaint().setFakeBoldText(true);
	            tv.setTextSize(15);
	     }

		AppManager.getAppManager().addActivity(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			String s = MaStation.getString4SP(context, "acdwtab", "acdwtab");
			if(s != null && !s.equals("")){
				 	int  i = Integer.parseInt(s);
				  mTabHost.setCurrentTab((i < inum ? i:0 ));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}


	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 MaStation.setString2SP(context, mTabHost.getCurrentTab()+"", "acdwtab", "acdwtab");
		  
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
		intent.setClass(AcDW.this,AcFunction.class);
		startActivity(intent);
		finish();
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}
}
