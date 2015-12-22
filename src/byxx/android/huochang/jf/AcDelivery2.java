package byxx.android.huochang.jf;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.scanner.CaptureActivity;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AcDelivery2 extends BaseActivity{
	ProgressDialog progressDialog;
	Sendzcjf sendzcjf;
	getUnLoadjf loadjf;
	EditText hpNo = null;
	EditText cpNum = null;
	Button query = null;
	static final int XZING = 0x3001a;
	String[] carNo = { "粤","湘","云", "辽", "黑", "新", "桂", "甘", "蒙", "陕", "吉",
			"贵", "川", "青", "藏", "琼", "宁", "沪", "渝", "鲁", "晋", "豫", "皖", "鄂",
			"浙", "闽", "赣", "苏", "冀", "津", "京"};
	//A B C D E F G H I J K L M N O P Q R S T U V W X Y Z 
	String[] cNum = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	String re_carNo="/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
	private TextView hw;
	TextView pm ;
	TextView js ;
	TextView jbrq ;
	Spinner spinner;
	Spinner AZ_spinner;
	TextView state;
	TextView remark;
	RelativeLayout rlcontext;
	Button bfjf,jf;
	Button btnzxing;
	String hpid;
	Context context;
//	GdmsUnloadJfDTO value;
	String hfs = "<font color=#027bc8 >";
	String hfe = "</font>";
    private ArrayAdapter<String> adapter ,adaperAZ; 
    UnloadJfDTO value;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delivery2);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		context = this;
//		hpid = getIntent().getStringExtra("hpid");
		hpNo = (EditText)findViewById(R.id.et_hpNo);
		cpNum = (EditText)findViewById(R.id.et_cphm);
		query = (Button)findViewById(R.id.btn_order_query);
		btnzxing = (Button)findViewById(R.id.btn_zxing_query);
		hw = (TextView)findViewById(R.id.tv_hw);
		pm = (TextView)findViewById(R.id.tv_pm);
		js = (TextView)findViewById(R.id.tv_js);
		jbrq = (TextView)findViewById(R.id.tv_jbrq);
		spinner = (Spinner)findViewById(R.id.spinner1);
		state = (TextView)findViewById(R.id.tv_state);
		bfjf = (Button)findViewById(R.id.btn_bfjf);
		
		jf = (Button)findViewById(R.id.btn_jf);
		remark = (TextView)findViewById(R.id.tv_bz);
		rlcontext = (RelativeLayout)findViewById(R.id.rl_context);
		AZ_spinner = (Spinner)findViewById(R.id.spinner2);
//		if(hpid != null){
//
//			value = MaStation.getInstance().getMoDelivery().getValue();
//		}
//		if(value != null){
//			String s ="";
//			if(value.getHpid()!= null){
//			s = value.getHpid();
////			s = s.substring(s.indexOf("  ")+2); //空2格
//			hpNo.setText(Html.fromHtml(s));
//			s = s.substring(s.indexOf(" ")+1); //空2格
//			hw.setText(Html.fromHtml(hfs+"货票:"+hfe+s));
//			}
//			
//			s ="";
//			if(value.getPm() != null){
//			s = value.getPm();
//			pm.setText(Html.fromHtml(hfs+"品名:"+hfe+s));
//			}
//			s ="";
//
//			s = value.getJs()+"";
//			js.setText(Html.fromHtml(hfs+"件数:"+hfe+s));
//			s ="";
//			if(value.getXbkTime() != null){
//			s=  ByString.getTimeStr(value.getXbkTime(),"MM月dd日 HH:mm");
//			jbrq.setText(Html.fromHtml(hfs+"限搬日期:"+hfe+s));
//			}
//			s ="";
//
//			s = GdmsUnloadJfDTO.proflagNames[value.getProflag()];
//			state.setText(Html.fromHtml(hfs+"状态:"+hfe+s));
//
//		}
		rlcontext.setVisibility(8);
		query.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!hpNo.getText().toString().trim().equals("")){
				loadjf = new getUnLoadjf();
				loadjf.execute(hpNo.getText().toString().trim());
				}else{
					
				}
			}
		});
		btnzxing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				scanFaile();
			}
		});
        //将可选内容与ArrayAdapter连接起来  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,carNo);  
        adaperAZ = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cNum);  
        //设置下拉列表的风格  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        adaperAZ.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
          
        //将adapter 添加到spinner中  
        spinner.setAdapter(adapter);  
        AZ_spinner.setAdapter(adaperAZ);
        //添加事件Spinner事件监听    
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener()); 
        AZ_spinner.setOnItemSelectedListener(new SpinnerSelectedListener()); 
//        spinner.setSelection(2);
    	//完全交付 定义为4
		jf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendzcjf = new Sendzcjf();
//				String hpid = params[0];
//				String chp = params[1];
//				int proFlag = Integer.parseInt(params[2]);
				if(value != null){
				  if(value.getProflag()==3){	
				sendzcjf.execute(value.getHpid(),spinner.getSelectedItem().toString().trim()+AZ_spinner.getSelectedItem().toString().trim()+cpNum.getText().toString().trim(),"4");
				
				  }else if(value.getProflag()<3){
					  Toast.makeText(context, "该货物没有卸完,还不能进行交付", Toast.LENGTH_SHORT).show();
				  }else if(value.getProflag() >3){
					  Toast.makeText(context, "该货物已经进行了交付", Toast.LENGTH_SHORT).show();
				  }
				  }
			}
		});
		//部分交付 定义为5
		bfjf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendzcjf = new Sendzcjf();
//				String hpid = params[0];
//				String chp = params[1];
//				int proFlag = Integer.parseInt(params[2]);
				if(value != null){
					 if(value.getProflag()==3){	
					sendzcjf.execute(value.getHpid(),spinner.getSelectedItem().toString().trim()+AZ_spinner.getSelectedItem().toString().trim()+cpNum.getText().toString().trim(),"5");
					  }else if(value.getProflag()<3){
						  Toast.makeText(context, "该货物没有卸完,还不能进行交付", Toast.LENGTH_SHORT).show();
					  }else if(value.getProflag() >3){
						  Toast.makeText(context, "该货物已经进行了交付", Toast.LENGTH_SHORT).show();
					  }
					 }
			}
		});
	}                                        

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		receiveFaile();
	}
	
	private void scanFaile() {
		try {
//			TextView tvResult = (TextView) findViewById(R.id.textView3);
			hpNo.setText("");
			//

			Intent intent = new Intent();
			intent.setClass(AcDelivery2.this, CaptureActivity.class);
			startActivityForResult(intent,XZING);
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
	
	private void receiveFaile() {
		try {
			if (MaStation.getInstance().getMoDelivery().isScanflag()) {
				String tStr = MaStation.getInstance().getMoDelivery()
						.getScanResult();
				if (tStr == null) {
					tStr = "";
				}
				receiveId(tStr);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
	
	private void receiveId(String tStr) {
		try {

			hpNo.setText(tStr);

		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
    //使用数组形式操作  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  

        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
    
	private void buHomeOnClick() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(AcDelivery2.this,AcFunction.class);
		startActivity(intent);
		finish();
	} 
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				buHomeOnClick();
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == XZING){

			if(resultCode == RESULT_OK){
			if(data != null && !data.equals("")){
			String re= data.getStringExtra("Decode");
			if(re != null){
				hpNo.setText(re);
			}
			}
			}

		}
		
		}
	
	 private class Sendzcjf extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... params) {
				String hpid = params[0];
				String chp = params[1];
				int proFlag = Integer.parseInt(params[2]);
				return MaStation.getInstance().getMaWebService().reportJfGoods(hpid,chp,proFlag);
			}
			
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(context);
				progressDialog.setMessage("正在提交,请稍候...");
				progressDialog.setIndeterminate(true);
				progressDialog.setCancelable(true);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialogInterface) {
						if (sendzcjf != null) {
							sendzcjf.cancel(true);
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
//						GsonBuilder gb = new GsonBuilder(); 
//						gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
//						Gson gson = gb.create();
						
//						showData();
						if("cache".equals(result)){
							ByDialog.showMessage(context, "网络断开，操作已缓存！");
						}else{
							ByDialog.showMessage(context, result);
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
	 private class getUnLoadjf extends AsyncTask<String, Void, String> {
		 @Override
		 protected String doInBackground(String... params) {
			 String hph = "B026440";//params[0];
			 return MaStation.getInstance().getMaWebService().getDjfGoodsByHph(hph);
		 }
		 
		 @Override
		 protected void onPreExecute() {
			 progressDialog = new ProgressDialog(context);
			 progressDialog.setMessage("正在提交,请稍候...");
			 progressDialog.setIndeterminate(true);
			 progressDialog.setCancelable(true);
			 progressDialog.setCanceledOnTouchOutside(false);
			 progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			 progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				 @Override
				 public void onCancel(DialogInterface dialogInterface) {
					 if (sendzcjf != null) {
						 sendzcjf.cancel(true);
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
						 }else{
							 GsonBuilder gb = new GsonBuilder();
							 gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
							 Gson gson = gb.create();
							 value = gson.fromJson(result, UnloadJfDTO.class);
							 if(value != null){
								 rlcontext.setVisibility(0);
								 showData();
						 }
						}
					 }else{
						 ByDialog.showMessage(context, "网络信号不好！");
//						 Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
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

		public void showData() {
			// TODO Auto-generated method stub
			String s ="";
			if(value.getHpid()!= null){
			s = value.getHpid();
//			s = s.substring(s.indexOf("  ")+2); //空2格
			hpNo.setText(Html.fromHtml(s));
			s = s.substring(s.indexOf(" ")+1); //空2格
			hw.setText(Html.fromHtml(hfs+"货票:"+hfe+s));
			}
			
			s ="";
			if(value.getPm() != null){
			s = value.getPm();
			pm.setText(Html.fromHtml(hfs+"品名:"+hfe+s));
			}
			s ="";

			s = value.getJs()+"";
			js.setText(Html.fromHtml(hfs+"件数:"+hfe+s));
			s ="";
			if(value.getXbkTime() != null){
			s=  ByString.getTimeStr(value.getXbkTime(),"MM月dd日 HH:mm");
			jbrq.setText(Html.fromHtml(hfs+"限搬日期:"+hfe+s));
			}
			s ="";

			s = GdmsUnloadJfDTO.proflagNames[value.getProflag()];
			state.setText(Html.fromHtml(hfs+"状态:"+hfe+s));
			
			s ="";
			if(value.getRemark() != null){
			s = value.getRemark()+"";
			remark.setText(hfs+"备注:"+hfe+s);
			}

			
			
		}
//		1018(赵玺) 10:03:11
//		小区,昨天加的按货票查询时没有限定处理状态,也就是说不管是否能交付或者
//		已经交付过了的记录都查出来了,所以需要你在前台处理一下,
//		只有具备交付条件的记录,你的操作按钮才显示出来,否则提示出不能进行交付原因.
//		具体判断方法:判断dto中的proflag字段,=3时可以进行交付,
//		<3时提示该货物没有卸完,还不能进行交付,>3时表示已经进行了交付
}
