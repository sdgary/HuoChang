package byxx.android.huochang.dw;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;

public class AcDWTab extends Activity{
	ListView listView;
	String tabnum ;
	BaDWFragment baDW;
	Handler handler;
	Context mcontext ;
	ListView lv = null;
	Button buReturnHome;
	ProgressDialog dialog = null;
	RuGJH rugjh ;
	boolean intRunType = true;
	int type ;
	String valueid = null ;
	AlertDialog.Builder  dialogshow = null;
	boolean run = false;
	boolean ishavembl = false;
	TextView state;
	ImageButton addrw;
	ProgressDialog progressDialog;
	List<XGjhzwDto> nListShow = new ArrayList<XGjhzwDto>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qxc_jh_tab);
		Intent intent = getIntent();
		mcontext =this;
		tabnum = intent.getStringExtra("tabnum");
		Log.v("dl", tabnum+"");
//		addrw = (ImageButton)findViewById(R.id.btn_add);
		listView = (ListView)findViewById(R.id.listView1);
		state = (TextView)findViewById(R.id.state);
		listView.setAdapter(getBaGJH());
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				XGjhzwDto  value= getBaGJH().getDatas().get(arg2);
				Intent intent = new Intent();
				intent.setClass(AcDWTab.this, AcMBL.class);
				Bundle bundle = new Bundle();
				bundle.putInt("swh", (value.getSwh()>0? value.getSwh():0));
				bundle.putInt("cs", (value.getCs() > 0 ? value.getCs():0));//value.getCs());
				bundle.putLong("jhsj", (value.getJhsj() != null? value.getJhsj().getTime():0));//value.getJhsj().getTime());
				bundle.putLong("facetime", (value.getFactTime() != null? value.getFactTime().getTime():0));//value.getFactTime().getTime());
				bundle.putString("gdm", tabnum);
				bundle.putInt("gjhid",value.getGjhid());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		//长按键删除任务 功能
//		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//				@Override
//				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//						 int arg2, long arg3) {
//					// TODO Auto-generated method stub
//					if(nListShow != null){
//						final int targ = arg2;
//						AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
//						builder.setMessage(nListShow.get(arg2).getSwh()+"删除，确认?");
//						builder.setCancelable(false);
//						builder.setNegativeButton("取消",
//							new android.content.DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int which) {// which=-2
//
//							}
//						});
//						builder.setPositiveButton("确认",
//								new android.content.DialogInterface.OnClickListener(){
//							public void onClick(DialogInterface dialog, int which) {// which=-1
//								if(nListShow.get(targ).getAddflag() == 2){
//									nListShow.remove(targ);
//									getBaGJH().setDatas(nListShow);
//									getBaGJH().notifyDataSetChanged();
//									
//									GsonBuilder gb = new GsonBuilder(); 
//									gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
//									Gson gson = gb.create();
//									String s = gson.toJson(nListShow);
//									MaStation.setString2SP(mcontext,s,"GJHdto", "GJHdto"+tabnum);
//								}else if(nListShow.get(targ).getAddflag() == 1){
//									Toast.makeText(mcontext, "默认数据不能删除", Toast.LENGTH_SHORT).show();
//								}
//							}
//						});
//						builder.create().show();
//					}
//					return false;
//				}
//
//		});
//		addrw.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				String s = MaStation.getString4SP(mcontext, "GJHdto", "GJHdto"+tabnum);
//				GsonBuilder gb = new GsonBuilder(); 
//				gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
//				Gson gson = gb.create();
//				List<XGjhzwDto> nList = gson.fromJson(s, new TypeToken<LinkedList<XGjhzwDto>>(){}.getType());
//				XGjhzwDto dto = new XGjhzwDto();
//					int rnum = nList.size()+1;
//					dto.setJhno(rnum+"");
//					dto.setActualtime(System.currentTimeMillis());
//					dto.setPlantime(System.currentTimeMillis()- 6000*10);
//					dto.setQscolor_flag(1);
//					dto.setLayout_color(0);
//					dto.setAddflag(2);
//
//					nListShow.add(dto);
//
//					nList.add(dto);
//					String ts = gson.toJson(nList);
//					MaStation.setString2SP(mcontext,ts,"GJHdto", "GJHdto"+tabnum);
//					getBaGJH().setDatas(nList);
//					getBaGJH().notifyDataSetChanged();
//					XGjhzwDto  value= getBaGJH().getDatas().get(rnum-1);
//					Intent intent = new Intent();
//					intent.setClass(AcDWTab.this, AcMBL.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("jhh", value.jhno);
//					bundle.putString("gdm", tabnum);
//					intent.putExtras(bundle);
//					startActivity(intent);
//			}
//		});
		

		getHandler().postDelayed(task,0);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		run =false;
//		getHandler().removeCallbacks(task);
	}
	
	public BaDWFragment getBaGJH() {
		if(baDW == null){
			baDW = new BaDWFragment();
		}
		return baDW;
	}

	public Handler getHandler() {
		if (handler == null) {
			handler = new Handler() {
				public void handleMessage(Message msg) {
					dealHandleMessage(msg);
				}
			};
		}
		return handler;
	}

	private void dealHandleMessage(Message msg) {
		try {
			if (msg == null)
				return;
			int dId = 0; // 对话框的标识
			boolean closeDialog = true;
			try {
				// 读取完后设置数据
				Bundle bundle = msg.getData();
				switch (msg.what) {
				case Constant.MSG_ID_READ_GJH:
					dId = 0;
					boolean ballreal = bundle.getBoolean("realallgjh");
					if (ballreal) {

						showData();
					}else{
						showData();
						if(isNetworkConnected()){
							state.setText("该股道无对位任务");
						}else{
							state.setText("网络错误");
						}
					}
					rugjh.close();
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (closeDialog && dialog != null && dialog.isShowing()) {
				dismissDialog(dId);
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
		return;
	}
	private void showData() {
		try {
//			GsonBuilder gb = new GsonBuilder(); 
//			gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
//			Gson gson = gb.create();
			
			
			nListShow = MaStation.getInstance().getMoGJH().getGjhlist();//MaStation.getInstance().getMofault().getAlKfcFailure();

//			String s = gson.toJson(nListShow);
//			//获取落地数据
//			String old = MaStation.getString4SP(mcontext, "GJHdto", "GJHdto"+tabnum);
//			//落地数据转为list<>类型
//			List<XGjhzwDto> oldnlist = gson.fromJson(old, new TypeToken<LinkedList<XGjhzwDto>>(){}.getType());
//			if(oldnlist != null){
//				//跟新数据比落地数据量大 的取跟新数据
//				if(nListShow.size() > oldnlist.size()){
//					
//					MaStation.setString2SP(MaStation.getInstance().context, s, "GJHdto", "GJHdto"+tabnum);
//					getBaGJH().setDatas(nListShow);
//					}else{
//						nListShow = oldnlist;
//						getBaGJH().setDatas(nListShow);
//					}
//				}else{
//					MaStation.setString2SP(MaStation.getInstance().context, s, "GJHdto", "GJHdto"+tabnum);
					getBaGJH().setDatas(nListShow);
//				}

			getBaGJH().notifyDataSetChanged();
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	public RuGJH getRuGjh() {
		if (rugjh == null) {
			rugjh= new RuGJH(getHandler());
			
		}
		return rugjh;
	}
	
	  private Runnable task = new Runnable() {  
//	      int count = 0;
	        public void run() {  
	            // TODO Auto-generated method stub  

	        	getRuGjh().setType(RuGJH.REAL_ALL_GJH);
	        	getRuGjh().setTab(tabnum);
	        	getRuGjh().start();
	        	
//	            if (run) {
//	            	getHandler().postDelayed(this, 1000*5);  
//	            }  
	        }  
	    };  
	    
//		private class ReadMBL extends AsyncTask<String, Void, String> {
//			
//			@Override
//			protected String doInBackground(String... params) {
//				String gdm = params[0];
//				return MaStation.getInstance().getMaWebService().getGdxc4MBL(MaStation.stationCode,gdm);
//			}
//			
//			@Override
//			protected void onPreExecute() {
//				progressDialog = new ProgressDialog(AcDWTab.this);
//				progressDialog.setMessage("正在读取,请稍候...");
//				progressDialog.show();
//			}
//			
//			@Override
//			protected void onPostExecute(String result) {
//
//				try{
//					if(isNetworkConnected()||MaStation.getInstance().isTest()){
//					if(result != null && !result.equals("[]")){
//						GsonBuilder gb = new GsonBuilder();
//						gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
//						Gson gson = gb.create();
//						MaStation.getInstance().getMoDW().carDto4dws = gson.fromJson(result, new TypeToken<LinkedList<CarDto4dw>>(){}.getType());
//							ishavembl = true;
//							run = true;
//							getHandler().postDelayed(task,0);
//							addrw.setVisibility(0);
//							state.setText(""); 
//							progressDialog.dismiss();
//					}else{    
//						ishavembl = false;
//						state.setText("该股道无对位任务");
//						addrw.setVisibility(8);
//						progressDialog.dismiss();
//					}
//					}else{
//						progressDialog.dismiss();
//						
//					}
//				}catch(Exception e){
//					progressDialog.dismiss();
//				}
//			}
//			
//		}	
	
		/**
		 * 检测网络是否可用
		 * @return
		 */
		public boolean isNetworkConnected() {
			ConnectivityManager cm = (ConnectivityManager)mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			return ni != null && ni.isConnectedOrConnecting();
		}
		
	    @Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if(keyCode == KeyEvent.KEYCODE_BACK){    //点击返回按键  
//	    		Intent intent = new Intent();
//	    		intent.setClass(AcDWTab.this,AcFunction.class);
//	    		startActivity(intent);
//	    		finish();
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    }  	
}
