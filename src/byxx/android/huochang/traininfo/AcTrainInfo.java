package byxx.android.huochang.traininfo;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.dw.AcDWTab;
import byxx.android.huochang.dw.AcDW;
import byxx.android.huochang.function.AcFunction;

public class AcTrainInfo extends TabActivity{
	private TabHost mTabHost = null;
    private TabSpec mTab1;
    private TabSpec mTab2;
    private TabSpec mTab3;
    private TabSpec mTab4;
    Button buReturnHome = null;
    Bundle bundle = null;
//	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		   setContentView(R.layout.traininfo);
		   AppManager.getAppManager().addActivity(this);
		   
			final ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(true);
			
		  String s[] = MaStation.getInstance().getUser().getGdm();
		 mTabHost = getTabHost();
	        // Main tab
		 if(s[0] != null){
		 mTab1 = mTabHost.newTabSpec("traininfo_1");
		 mTab1.setIndicator(s[0]);
		 mTab1.setContent(R.id.traininfo_1);
		 Intent iTab1 = new Intent(AcTrainInfo.this,AcTrainInfoTab.class);
		 bundle = new Bundle();
		 bundle.putString("tabnum",s[0]);
		 iTab1.putExtras(bundle);
		 mTab1.setContent(iTab1);
	     mTabHost.addTab(mTab1);
		 }
	     
		 if(s[1] != null){
	     mTab2 = mTabHost.newTabSpec("traininfo_2");
	     mTab2.setIndicator(s[1]);
	     mTab2.setContent(R.id.traininfo_2);
	     Intent iTab2 = new Intent(AcTrainInfo.this,AcTrainInfoTab.class);
		 bundle = new Bundle();
		 bundle.putString("tabnum",s[1]);
		 iTab2.putExtras(bundle);
		 mTab2.setContent(iTab2);
	     mTabHost.addTab(mTab2);
		 }
	     
		 if(s[2] != null){
	     mTab3 = mTabHost.newTabSpec("traininfo_3");
	     mTab3.setIndicator(s[2]);
	     mTab3.setContent(R.id.traininfo_3);
	     Intent iTab3 = new Intent(AcTrainInfo.this,AcTrainInfoTab.class);
		 bundle = new Bundle();
		 bundle.putString("tabnum",s[2]);
		 iTab3.putExtras(bundle);
		 mTab3.setContent(iTab3);
	     mTabHost.addTab(mTab3);
		 }
		 
		 if(s[3] != null){
			 mTab4 = mTabHost.newTabSpec("traininfo_4");
			 mTab4.setIndicator(s[3]);
			 mTab4.setContent(R.id.traininfo_4);
			 Intent iTab4 = new Intent(AcTrainInfo.this,AcTrainInfoTab.class);
			 bundle = new Bundle();
			 bundle.putString("tabnum",s[3]);
			 iTab4.putExtras(bundle);
			 mTab4.setContent(iTab4);
			 mTabHost.addTab(mTab4);
		 }
		 
	     int childCount = mTabHost.getTabWidget().getChildCount();
	        for (int i = 0; i < childCount; i++) {
	            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 80;
	            TextView tv=(TextView)mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
	            tv.setTextColor(Color.WHITE);
	            tv.getPaint().setFakeBoldText(true);
	            tv.setTextSize(15);
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
		intent.setClass(AcTrainInfo.this,AcFunction.class);
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
