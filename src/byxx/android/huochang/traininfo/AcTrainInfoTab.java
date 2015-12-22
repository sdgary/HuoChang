package byxx.android.huochang.traininfo;

import java.util.List;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.util.ByString;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class AcTrainInfoTab extends Activity {
	ListView listView;
	TextView tvTime,tvState;
	String tabnum ;
	BaTrainInfo baTrainInfo;
	Handler handler;
	Context mcontext ;
	ListView lv = null;
	Button buReturnHome;
	ProgressDialog dialog = null;
	RuTrainInfo ruTrainInfo ;
	boolean intRunType = true;
	int type ;
	String valueid = null ;
	AlertDialog.Builder  dialogshow = null;
	boolean run = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.traininfo_tab);
		Intent intent = getIntent();
		tabnum = intent.getStringExtra("tabnum");
		Log.v("dl", tabnum+"");
//		switch (tabnum) {
//		case 1:
//			
//			break;
//		case 2:
//			
//			break;
//		case 3:
//			
//			break;
//
//		default:
//			break;
//		}
		tvState = (TextView)findViewById(R.id.tvstate);
		tvTime = (TextView)findViewById(R.id.tvtime);		
		listView = (ListView)findViewById(R.id.lv_traininfo);
		listView.setAdapter(getBaTrainInfo());
		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
////				Log.v("msg", "点击"+arg2);
//				GJHdto  value= getBaGJH().getDatas().get(arg2);
////				Log.v("msg", kfcFailure.getId());
////	    		dialogshow.create().show();  
//				Intent intent = new Intent();
//				intent.setClass(AcGJHTab.this, AcJzx.class);
//				Bundle bundle = new Bundle();
//				bundle.putString("jhh", value.jhno);
//				intent.putExtras(bundle);
//				startActivity(intent);
//			}
//		});
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Intent intent = getIntent();
//		int tabnum1 = intent.getIntExtra("tabnum",0);
//		Log.v("dl", tabnum1+ " onresume"+ " "+ tabnum);
		run = true;
		getHandler().postDelayed(task,0);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		run =false;
//		getHandler().post(task);
		getHandler().removeCallbacks(task);
	}
	
	public BaTrainInfo getBaTrainInfo() {
		if(baTrainInfo == null){
			baTrainInfo = new BaTrainInfo();
		}
		return baTrainInfo;
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
				case Constant.MSG_ID_READ_TRAININFO:
					dId = 0;
					boolean ballreal = bundle.getBoolean("realalltraininfo");
					if (ballreal) {
						tvTime.setText(ByString.getTimeStr(System.currentTimeMillis(), "dd日 HH:mm"));
						tvState.setText("");
						tvState.setTextSize(24);
						showData();
					}else{
						showData();
						tvState.setTextSize(30);
						tvState.setText("暂无数据");
					}
					ruTrainInfo.close();
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
			List<CarDto> nListShow = MaStation.getInstance().getMoTrainInfo().getTrainInfo();//MaStation.getInstance().getMofault().getAlKfcFailure();
			getBaTrainInfo().setDatas(nListShow);
			getBaTrainInfo().notifyDataSetChanged();
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	public RuTrainInfo getRuTrainInfo() {
		if (ruTrainInfo == null) {
			ruTrainInfo= new RuTrainInfo(getHandler());
			
		}
		return ruTrainInfo;
	}
	
	  private Runnable task = new Runnable() {  
//	      int count = 0;
	        public void run() {  
	            // TODO Auto-generated method stub  
//	            Log.v("runTF", "run "+count ++);
//				setType(Rufault.REAL_ALL_TICKETOFFICE_ERR);
	        	getRuTrainInfo().setType(RuTrainInfo.REAL_ALL_TRAININFO);
	        	getRuTrainInfo().setGDM(tabnum);
	    		Log.v("dl", tabnum+"");
	        	getRuTrainInfo().start();
	            if (run) {
	            	getHandler().postDelayed(this, 1000*60*5);  
	            }  
	        }  
	    };  
}
