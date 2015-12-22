package byxx.android.huochang.gdcarcheck;

import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;

import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;

public class AcGDCarcheckOne extends BaseActivity {
	EditText hpNo = null;
	EditText cpNum = null;
	Button query = null;

	private TextView hw;
	TextView pm ;
	TextView js ;
	TextView jbrq ;
	Spinner spinner;
	Spinner AZ_spinner;
	TextView state;
	TextView checkId;
	TextView checkType;
	TextView planendTime;
	TextView icCardTime1;
	TextView icCardTime2;
	TextView IDCard;
	TextView tvresult;
	RatingBar ratingBar;
	Handler handler;
	DoCheckTask doCheckTask;
	Button Btnsk;
	String hpid;
	Context context;
	GdCarCheckDTO value;
	String hfs = "<font color=#027bc8 >";
	String hfe = "</font>";
    private ArrayAdapter<String> adapter ,adaperAZ; 
    int position;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carcheck_one);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		context = this;
	     checkId = (TextView)findViewById(R.id.checkId);
	     checkType = (TextView)findViewById(R.id.checkType);
	     planendTime = (TextView)findViewById(R.id.planEndTime);
	     icCardTime1 = (TextView)findViewById(R.id.icCardTime1);
	     icCardTime2 = (TextView)findViewById(R.id.icCardTime2);
	     Btnsk = (Button)findViewById(R.id.btbsk);
	     IDCard = (TextView)findViewById(R.id.tvidnum);
	     tvresult = (TextView)findViewById(R.id.tvresult);
	     ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
	     position = getIntent().getIntExtra("ftnum", 0);


		showdate();


		Btnsk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String id = IDCard.getText().toString().trim();
				if (!id.equals("")) {
					if (value.getIcCardId1() != null && value.getIcCardId1().equals(id)) {
						tvresult.setText("本卡号已上报");
					} else {
						doCheckTask = new DoCheckTask();
						doCheckTask.execute(id);
					}

				} else {
					tvresult.setText("请刷卡");
				}

			}
		});
		AppManager.getAppManager().addActivity(this);
	}                                        

	private void showdate(){
		value = MaStation.getInstance().getMoCarCheck().getValue();
		if(value != null){
			String s = "";
			if(value.getCheckId() != null){
				s = value.getCheckId()+"";
				
//				s = s.substring(s.indexOf(" ")+1); //空2格
				checkId.setText(Html.fromHtml(hfs+hfe+s));
			}
			s = "";

				s = value.getCheckTypeName();
				checkType.setText(Html.fromHtml(hfs+""+hfe+s));
			

			s = "";
			if(value.getPlanEndTime() != null){
				s = ByString.getTimeStr(value.getPlanEndTime(), "dd日HH:mm");
				planendTime.setText(Html.fromHtml(hfs+"检查截止  "+hfe+s));
			}else{
				planendTime.setText("");
			}

			s = "";
			if(value.getIcCardTime1() != null){
				s = ByString.getTimeStr(value.getIcCardTime1(), "dd日HH:mm");
				icCardTime1.setText(Html.fromHtml(hfs+"刷卡1  "+hfe+s));
			}else{
				icCardTime1.setText("");
			}
			s = "";
			if(value.getIcCardTime2() != null){
				s = ByString.getTimeStr(value.getIcCardTime2(), "dd日HH:mm");
				icCardTime2.setText(Html.fromHtml(hfs+"刷卡2 "+hfe+s));
			}else{
				icCardTime2.setText("");
			}

//			ratingBar.setRating(value.getNumStars()/value.getCheckCs());
			ratingBar.setNumStars(value.getCheckCs());
			ratingBar.setRating(value.getNumStars());

		}
	}
	
	private void getNFC(String regionId) {
		try {
			IDCard.setText(regionId);
			tvresult.setText("");
//			edcontext.setText("");
//			edcontext.setText(MaStation.getInstance().getMoRegionPatrol().findRFIDbyid(regionId));

			if (regionId == null || regionId.equals("")) {
				return;
			}
			}catch (Exception e) {
			
		 }
	}
	
	@Override
	protected void onStart() {
		try {
			MaStation.getInstance().getMoNfc().setHandler(getHandler());
			MaStation.getInstance().handlerTask=getHandler();
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		//
		super.onStart();
	}
	
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
		}
	private void dealHandleMessage(Message msg) {
			try {
				if (msg == null)
					return;
				try {
					// 读取完后设置数据
				Bundle bundle = msg.getData();
				switch (msg.what) {	
				
				case Constant.MSG_ID_NFC: // nfc返回
					String regionId = msg.getData().getString("regionId");
					getNFC(regionId);
					break;

				default:
					break;
				}
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
	
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		 }
	}
	public Handler getHandler(){
		if (handler == null){
			handler = new Handler() {
				public void handleMessage(Message msg) {
					dealHandleMessage(msg);
				}
			};
		}
		return handler;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (MaStation.getInstance().handlerTask != null) {
			MaStation.getInstance().getMoNfc().setHandler(
					MaStation.getInstance().handlerTask);
		}
		AppManager.getAppManager().finishActivity(this);
	}
	private void buHomeOnClick(Boolean key) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(AcGDCarcheckOne.this,AcGdCarCheck.class);
		Bundle bundle = new Bundle();
		bundle.putInt("one_num", position);
		bundle.putBoolean("valuedtos", key);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
		
	} 
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				buHomeOnClick(false);
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	
	 private class DoCheckTask extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... params) {
				String idcard = params[0];
				return MaStation.getInstance().getMaWebService().doCheckTask(value.getCheckId().toString(),idcard);
			}
			
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(AcGDCarcheckOne.this);
				progressDialog.setMessage("正在提交,请稍候...");
				progressDialog.setIndeterminate(true);
				progressDialog.setCancelable(true);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialogInterface) {
						if (doCheckTask != null) {
							doCheckTask.cancel(true);
						}
					}
				});
				progressDialog.show();
			}
			
			@Override
			protected void onPostExecute(String result) {

				try{
					if(isNetworkConnected()||MaStation.getInstance().isTest()){
					if(result != null  && !"".equals(result)){
						if("cache".equals(result)){
							ByDialog.showMessage(context, "网络断开，操作已缓存！");
						}else {
							if(value.getCheckCs() > 1){
								IDCard.setText("");
								GsonBuilder gb = new GsonBuilder();
								gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
								Gson gson = gb.create();

								MaStation.getInstance().getMoCarCheck().value = gson.fromJson(result, GdCarCheckDTO.class);
								if(!value.getCheckId().equals(MaStation.getInstance().getMoCarCheck().value.getCheckId())){
									buHomeOnClick(true);
									return;
								}
								showdate();

							}else{
								buHomeOnClick(true);
							}
						}

					}else{
						ByDialog.showMessage(context, "网络信号不好！");
//						Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
					}

					}
				}catch(Exception e){
					ByDialog.showMessage(context, e.getMessage());
					if(progressDialog != null){
						progressDialog.cancel();
					}
				}
				if(progressDialog != null){
					progressDialog.cancel();
				}
			}
		}
	 
		/**
		 * 检测网络是否可用
		 * @return
		 */
		public boolean isNetworkConnected() {
			ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			return ni != null && ni.isConnectedOrConnecting();
		}
		
		@Override  
		public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    if(keyCode == KeyEvent.KEYCODE_BACK){    //点击返回按键  
	    	buHomeOnClick(true);
	        return true;  
	    }  
	    return super.onKeyDown(keyCode, event);  
	}  	
}
