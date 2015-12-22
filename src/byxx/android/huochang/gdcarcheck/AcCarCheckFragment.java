package byxx.android.huochang.gdcarcheck;

import java.util.LinkedList;
import java.util.List;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;

import byxx.android.huochang.jf.GdmsUnloadJfDTO;
import byxx.android.huochang.jf.RuTrainInfo;
import byxx.android.huochang.util.ByDialog;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.AlertDialog;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class AcCarCheckFragment extends Fragment{
	PullToRefreshListView listView;
	TextView tvTime,tvState;
	public String tabnum ;
	BaCarCheckFragment baCarCheckFragment;
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
	int type ;
	String valueid = null ;
	AlertDialog.Builder  dialogshow = null;
	boolean run = false;
    private static final String KEY_CONTENT = "AcCarCheckFragment:Content";
    private int fPosition;
	GetCheckTasks checkTasks;

	 public static AcCarCheckFragment newInstance(String content,int position) {
		 AcCarCheckFragment fragment = new AcCarCheckFragment();
		 	fragment.tabnum = content;
		 	fragment.fPosition = position;
	        return fragment;
	    }




	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	         return inflater.inflate(R.layout.gdcarcheck, container, false);
	    }

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	    	// TODO Auto-generated method stub
	    	super.onActivityCreated(savedInstanceState);
	    	mcontext = getView().getContext();
			tvState = (TextView)getView().findViewById(R.id.tvstate);
			tvTime = (TextView)getView().findViewById(R.id.tvtime);		
			listView = (PullToRefreshListView)getView().findViewById(R.id.lv_task);
//			refresh = (Button)getView().findViewById(R.id.refresh);
			listView.setAdapter(getBaCarCheckFragment());

			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					GdCarCheckDTO  value= getBaCarCheckFragment().getDatas().get(arg2-1);
					MaStation.getInstance().getMoCarCheck().setValue(value);
					Intent intent = new Intent();
					intent.setClass(getView().getContext(), AcGDCarcheckOne.class);
					Bundle bundle = new Bundle();
					bundle.putInt("ftnum",fPosition);
					intent.putExtras(bundle);
					startActivity(intent);
					getActivity().finish();
				}
			});
			
			//下拉菜单拉事件
		listView.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				// TODO Auto-generated method stub
				if (state.equals(State.PULL_TO_REFRESH)) {
//					refreshView.getLoadingLayoutProxy().setPullLabel(getString(R.string.listview_empty));
//					refreshView.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.listview_title));
//					refreshView.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.listview_refesh));
					String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE| DateUtils.FORMAT_ABBREV_ALL);
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.listview_refeshTime)+":"+label);
				}
			}
		});
		
		//下拉菜单刷新事件
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
//	        	getRuTrainInfo().setType(RuTrainInfo.REAL_ALL_TRAININFO);
//	        	getRuTrainInfo().setGDM(tabnum);
//	    		Log.v("dl", tabnum+"");
//	        	getRuTrainInfo().start();
				checkTasks = new GetCheckTasks();
				checkTasks.execute(tabnum);
				
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
	


		
//		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
//			Intent intent = getIntent();
//			int tabnum1 = intent.getIntExtra("tabnum",0);
//			Log.v("dl", tabnum1+ " onresume"+ " "+ tabnum);
//			run = true;
//			getHandler().postDelayed(task,0);
			checkTasks = new GetCheckTasks();
			checkTasks.execute(tabnum);
			
		} 
		
		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
//			run =false;
//			getHandler().post(task);
			if(progressDialog != null){
				progressDialog.cancel();
			}
//			getHandler().removeCallbacks(task);
		}
		
		public BaCarCheckFragment getBaCarCheckFragment() {
			if(baCarCheckFragment == null){
				baCarCheckFragment = new BaCarCheckFragment();
			}
			return baCarCheckFragment;
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
				
				List<GdCarCheckDTO> nListShow = MaStation.getInstance().getMoCarCheck().dto;
				getBaCarCheckFragment().setDatas(nListShow);
				getBaCarCheckFragment().notifyDataSetChanged();

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
		    
			
			 private class GetCheckTasks extends AsyncTask<String, Void, String> {
					@Override
					protected String doInBackground(String... params) {
						String tgdm = params[0];
						return MaStation.getInstance().getMaWebService().getCheckTasks(tgdm);
//						return 	MaStation.getInstance().getMaWebService().doCheckTask(28+"",123+"");
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
								if (checkTasks != null) {
									checkTasks.cancel(true);
								}
							}
						});
						progressDialog.show();
					}
					
					@Override
					protected void onPostExecute(String result) {

						try{
							if(isNetworkConnected()||MaStation.getInstance().isTest()){
							if(result != null && !result.equals("[]")&&!"".equals(result)){
								if("cache".equals(result)){
									ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
								}else{
									GsonBuilder gb = new GsonBuilder();
									gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
									Gson gson = gb.create();
									MaStation.getInstance().getMoCarCheck().dto = gson.fromJson(result, new TypeToken<LinkedList<GdCarCheckDTO>>(){}.getType());
									showData();
								}

							}else {
								ByDialog.showMessage(mcontext, "网络信号不好！");
							}

							}
							listView.onRefreshComplete();
							if(progressDialog != null){
								progressDialog.cancel();
							}
						}catch(Exception e){
							ByDialog.showMessage(mcontext, e.getMessage());
							if(progressDialog != null){
								progressDialog.cancel();
							}
						}
					}
					
				}	
			 

}
