package byxx.android.huochang.guardtour;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.SentenceSuggestionsInfo;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.common.UIHelper;
import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.stnsh.AcSH;
import byxx.android.huochang.task.AcTask;
import byxx.android.huochang.util.ByDialog;

public class AcGuardTour extends BaseActivity {
//	Button bthome = null;
	EditText edcontext = null;
	TextView tvidnum =null;
	Button btnSent = null;
	Intent intent =null;
	Handler handler = null;
	RuRegionPatrol ruRegionPatrol = null;
	Context context = null;
//	Button btLoadDate = null;
	TextView tvresult = null;
	boolean isback = false;
	Spinner spinner;
	public ProgressDialog progressDialog;
    private ArrayAdapter<String> adapter; 
	private static final String[] slname = {"标准化作业","巡查防溜","检查堆码","劳动纪律","巡查车辆"};
		Send send =null;
		InputMethodManager imm =null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regionpatrol);
//		AppManager.getAppManager().addActivity(this);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		
		context = this;
//		SuperApplication.getInstance().addActivity(this);
//		bthome =(Button)findViewById(R.id.bthome_ni);
		tvidnum = (TextView)findViewById(R.id.tvidnum);
		
		edcontext= (EditText)findViewById(R.id.et_nfcimp_context);
//		btLoadDate = (Button)findViewById(R.id.loaddate);
		tvresult = (TextView)findViewById(R.id.tvresult);
		btnSent = (Button)findViewById(R.id.sent);
		spinner = (Spinner)findViewById(R.id.spinner1);
        //将可选内容与ArrayAdapter连接起来  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,slname);  
          
        //设置下拉列表的风格  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
          
        //将adapter 添加到spinner中  
        spinner.setAdapter(adapter);  
          
        //添加事件Spinner事件监听    
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener()); 

		
		btnSent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String id = tvidnum.getText().toString().trim();
				String s = null;
				if (!id.equals("")) {
					send = new Send();
					send.execute(id, spinner.getSelectedItem().toString().trim(), edcontext.getText().toString().trim());
				} else {
					tvresult.setText("请刷卡");
				}
			}
		});
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

		AppManager.getAppManager().addActivity(this);
	}
	
	@Override
	protected void onStart() {
		try {
			// 接收条码扫描
			receiveFaile();
			//
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
	        imm.hideSoftInputFromWindow(edcontext.getWindowToken(), 0); 
		}
	
	private void receiveFaile() {
		try {
//			if (MaStation.getInstance().getMoReport().isScanflag()) {
//				String tStr = MaStation.getInstance().getMoReport().getScanResult();
//				if (tStr == null) {
//					tStr = "";
//				}
//				receiveId(tStr);
//			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
	
	private void receiveId(String tStr) {
		try {
//			TextView tTextView = (TextView) findViewById(R.id.editText2);
//			tTextView.setText(tStr);
			if (!tStr.equals("")) {
//				readName();
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
	
	public RuRegionPatrol getRuRegionPatrol() {
		if (ruRegionPatrol == null) {
			ruRegionPatrol = new RuRegionPatrol(getHandler(),context);
		}
		return ruRegionPatrol;
	}
	
	private void getNFC(String regionId) {
		try {
			tvidnum.setText(regionId);
			tvresult.setText("");
//			edcontext.setText("");
//			edcontext.setText(MaStation.getInstance().getMoRegionPatrol().findRFIDbyid(regionId));
			if(isback){
				
			}
			if (regionId == null || regionId.equals("")) {
				return;
			}
			}catch (Exception e) {
			
		 }
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
//				case Constant.MSG_ID_REGIONPATROL:
//					boolean b = msg.getData().getBoolean("realallregionpalrol");
//					String ss= null;
//					if(b){
//						isback = true;
//						ss="加载成功";
//						ss+=" 有数据量："+MaStation.getInstance().getMoRegionPatrol().getKfcDicRegionPatrolDTOs().size();
//					}else{
//						isback = false;
//						ss="加载错误，请重试";
//					}
//					/Toast.makeText(context, ss, Toast.LENGTH_SHORT).show();
					
//					tvstate.setText(ss);
//					break;
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
	
	/**
	 * 任务线程启动
	 */ 
	public void threadStart(String _idnum
			) {
		getRuRegionPatrol().start(_idnum);
	}

	public void stopThread() {
		getRuRegionPatrol().setRunFlag(false);
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
	
    //使用数组形式操作  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  

        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
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
		intent.setClass(AcGuardTour.this,AcFunction.class);
		startActivity(intent);
		finish();
	} 
	
private class Send extends AsyncTask<String, Void, String> {

		String idcard = null;
		String type = null;
		String content = null;

		@Override
		protected String doInBackground(String... params) {
			 idcard 	= params[0];
			 type  		= params[1];
			 content 	= params[2];
			return MaStation.getInstance().getMaWebService().reportZydd(idcard,type,content,MaStation.getPDAMAC(context));
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(AcGuardTour.this);
			progressDialog.setMessage("正在查询箱号,请稍候...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialogInterface) {
					if (send != null) {
						send.cancel(true);
					}
				}
			});
			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			try{
			
				if(isNetworkConnected()){
					if(result.equals("true")){
						tvresult.setText("上报成功");
						tvidnum.setText("");
					}else if("".equals(result)){
						ByDialog.showMessage(context, "网络信号不好！");
					}else if("cache".equals(result)){
						ByDialog.showMessage(context, "网络断开，操作已缓存！");
					}else{
						tvresult.setText(result);
					}
					
				    
				}else{
					tvresult.setText("网络错误");
				}
				

			}catch(Exception e){
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
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

}
