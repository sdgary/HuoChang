package byxx.android.huochang.jf;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.dw.AcDWTab;
import byxx.android.huochang.dw.AcMBL;
import byxx.android.huochang.dw.GJHdto;
import byxx.android.huochang.task.ZydDto;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.support.v4.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.widget.Button;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class AcDeliveryFragment extends Fragment{
    ListView listView;
	TextView tvTime,tvState;
	public String tabnum ;
	BaDeliveryFramgment baDeliveryFramgment;
	Handler handler;
	Context mcontext ;
	ListView lv = null;
	Button buReturnHome;
	List<GdmsUnloadJfDTO> nList;
//	Button refresh;
	ProgressDialog dialog = null;
	RuTrainInfo ruTrainInfo ;
	boolean intRunType = true;
	ProgressDialog progressDialog;
	ReadDelivery readDelivery;
	int type ;
	String valueid = null ;
	AlertDialog.Builder  dialogshow = null;
	boolean run = false;
    private static final String KEY_CONTENT = "AcDeliveryFragment:Content";
    
	 public static AcDeliveryFragment newInstance(String content) {
		 AcDeliveryFragment fragment = new AcDeliveryFragment();
		 	fragment.tabnum = content;
	        return fragment;
	    }




	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	         return inflater.inflate(R.layout.traininfo_tab, container, false);
	    }

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	    	// TODO Auto-generated method stub
	    	super.onActivityCreated(savedInstanceState);
	    	mcontext = getView().getContext();
			tvState = (TextView)getView().findViewById(R.id.tvstate);
			tvTime = (TextView)getView().findViewById(R.id.tvtime);		
			listView = (ListView)getView().findViewById(R.id.lv_traininfo);
//			refresh = (Button)getView().findViewById(R.id.refresh);
			listView.setAdapter(getBaDeliveryFramgment());

			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					GdmsUnloadJfDTO  value= getBaDeliveryFramgment().getDatas().get(arg2);
					MaStation.getInstance().getMoDelivery().setValue(value);
					Intent intent = new Intent();
					intent.setClass(getView().getContext(), AcDelivery.class);
					Bundle bundle = new Bundle();
					bundle.putString("hpid", value.getHpid());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
//			showData();
//			refresh.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//		        	getRuTrainInfo().setType(RuTrainInfo.REAL_ALL_TRAININFO);
//		        	getRuTrainInfo().setGDM(tabnum);
//		    		Log.v("dl", tabnum+"");
//		        	getRuTrainInfo().start();
//					progressDialog.show();
//				}
//			});
	    }
	    
	    
	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putString(KEY_CONTENT, tabnum);
	    }
	
	    

		@Override
//		public void onCreate(Bundle savedInstanceState) {
//			// TODO Auto-generated method stub
//			super.onCreate(savedInstanceState);
//			setContentView(R.layout.traininfo_tab);
//			Intent intent = getIntent();
//			tabnum = intent.getStringExtra("tabnum");
//			Log.v("dl", tabnum+"");
////			switch (tabnum) {
////			case 1:
////				
////				break;
////			case 2:
////				
////				break;
////			case 3:
////				
////				break;
//	//
////			default:
////				break;
////			}
//			tvState = (TextView)findViewById(R.id.tvstate);
//			tvTime = (TextView)findViewById(R.id.tvtime);		
//			listView = (ListView)findViewById(R.id.lv_traininfo);
//			listView.setAdapter(getBaTrainInfo());
//			
////			listView.setOnItemClickListener(new OnItemClickListener() {
////				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
////						long arg3) {
////					// TODO Auto-generated method stub
//////					Log.v("msg", "点击"+arg2);
////					GJHdto  value= getBaGJH().getDatas().get(arg2);
//////					Log.v("msg", kfcFailure.getId());
//////		    		dialogshow.create().show();  
////					Intent intent = new Intent();
////					intent.setClass(AcGJHTab.this, AcJzx.class);
////					Bundle bundle = new Bundle();
////					bundle.putString("jhh", value.jhno);
////					intent.putExtras(bundle);
////					startActivity(intent);
////				}
////			});
//			
//		}
		
//		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
//			Intent intent = getIntent();
//			int tabnum1 = intent.getIntExtra("tabnum",0);
//			Log.v("dl", tabnum1+ " onresume"+ " "+ tabnum);
//			run = true;
//			getHandler().postDelayed(task,0);
			progressDialog = new ProgressDialog(mcontext);
			progressDialog.setMessage("正在加载数据,请稍候...");
			readDelivery = new ReadDelivery();
			readDelivery.execute(tabnum);
			
		} 
		
		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
//			run =false;
//			getHandler().post(task);
			if(progressDialog != null){
				progressDialog.dismiss();
			}
//			getHandler().removeCallbacks(task);
		}
		
		public BaDeliveryFramgment getBaDeliveryFramgment() {
			if(baDeliveryFramgment == null){
				baDeliveryFramgment = new BaDeliveryFramgment();
			}
			return baDeliveryFramgment;
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

				boolean closeDialog = true;
				try {
					// 读取完后设置数据

					switch (msg.what) {
					case Constant.MSG_ID_READ_TRAININFO:

						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (closeDialog && dialog != null && dialog.isShowing()) {

				}
			} catch (Exception e) {
//				MaStation.getInstance().recordExc(e);
			}
			return;
		}
		private void showData() {
			try {
//				GdmsUnloadJfDTO dto = new GdmsUnloadJfDTO();
				
				List<GdmsUnloadJfDTO> nListShow = MaStation.getInstance().getMoDelivery().getGdmsUnloadJfDTOs();
				getBaDeliveryFramgment().setDatas(nListShow);
				getBaDeliveryFramgment().notifyDataSetChanged();
			} catch (Exception e) {
//				MaStation.getInstance().recordExc(e);
			}
		}
		
		public RuTrainInfo getRuTrainInfo() {
			if (ruTrainInfo == null) {
				ruTrainInfo= new RuTrainInfo(getHandler());
				
			}
			return ruTrainInfo;
		}
		
		  private Runnable task = new Runnable() {  
//		      int count = 0;
		        public void run() {  
		            // TODO Auto-generated method stub  
//		            Log.v("runTF", "run "+count ++);
//					setType(Rufault.REAL_ALL_TICKETOFFICE_ERR);
		        	getRuTrainInfo().setType(RuTrainInfo.REAL_ALL_TRAININFO);
		        	getRuTrainInfo().setGDM(tabnum);
		    		Log.v("dl", tabnum+"");
		        	getRuTrainInfo().start();
					progressDialog.show();
//		            if (run) {
//		            	getHandler().postDelayed(this, 1000*60*5);  
//		            }  
		        }  
		    };  

			/**
			 * 检测网络是否可用
			 * @return
			 */
			public boolean isNetworkConnected() {
				ConnectivityManager cm = (ConnectivityManager)mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				return ni != null && ni.isConnectedOrConnecting();
			}
		    
			
			 private class ReadDelivery extends AsyncTask<String, Void, String> {
					@Override
					protected String doInBackground(String... params) {
						String tgdm = params[0];
						return MaStation.getInstance().getMaWebService().getDjfGoods(tgdm);
					}
					
					@Override
					protected void onPreExecute() {
						progressDialog = new ProgressDialog(mcontext);
						progressDialog.setMessage("正在提交,请稍候...");
						progressDialog.setIndeterminate(true);
						progressDialog.setCancelable(true);
						progressDialog.setCanceledOnTouchOutside(false);
						progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialogInterface) {
								if (readDelivery != null) {
									readDelivery.cancel(true);
								}
							}
						});
						progressDialog.show();
					}
					
					@Override
					protected void onPostExecute(String result) {

						try{
							if(isNetworkConnected()||MaStation.getInstance().isTest()){
							if(result != null && !result.equals("[]")&& !"".equals(result)){
								if("cache".equals(result)){
									ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
								}else{
									GsonBuilder gb = new GsonBuilder();
									gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
									Gson gson = gb.create();
									MaStation.getInstance().getMoDelivery().gdmsUnloadJfDTOs = gson.fromJson(result, new TypeToken<LinkedList<GdmsUnloadJfDTO>>(){}.getType());
									showData();
								}

							}else{
								ByDialog.showMessage(mcontext, "网络信号不好！");
							}

							}
						}catch(Exception e){
							ByDialog.showMessage(mcontext, e.getMessage());
							if(progressDialog != null){
								progressDialog.cancel();
							}
						}
						if(progressDialog != null){
							progressDialog.cancel();
						}
					}
					
				}	
			 

				
}
