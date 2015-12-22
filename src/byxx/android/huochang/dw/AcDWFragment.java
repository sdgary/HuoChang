package byxx.android.huochang.dw;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
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


public class AcDWFragment extends Fragment{
	PullToRefreshListView listView;
	String tabnum ;
	BaDWFragment baDW = null;
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
//	ImageButton addrw;
	Button BtnRefresh;
	ProgressDialog progressDialog;
	List<XGjhzwDto> nListShow = new ArrayList<XGjhzwDto>();
    private static final String KEY_CONTENT = "AcDWFragment:Content";
	private Refreshdate refreshdate;

	public ProgressDialog getProgressDialog() {
		if(progressDialog == null){
			progressDialog = new ProgressDialog(AcDWFragment.this.getActivity());
			progressDialog.setMessage("正在加载数据,请稍候...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialogInterface) {
					if (refreshdate != null) {
						refreshdate.cancel(true);
					}
					if (listView != null){
						listView.onRefreshComplete();
					}
				}
			});
		}
		return progressDialog;
	}
	
	 public static AcDWFragment newInstance(String content) {
		 AcDWFragment fragment = new AcDWFragment();
		 	fragment.tabnum = content;
	        return fragment;
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	         return inflater.inflate(R.layout.qxc_jh_tab, container, false);
	    }

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	    	// TODO Auto-generated method stub
	    	super.onActivityCreated(savedInstanceState);
	    	mcontext = getView().getContext();
			Log.v("dl", tabnum+"");
//			addrw = (ImageButton)getView().findViewById(R.id.btn_add);
			listView = (PullToRefreshListView)getView().findViewById(R.id.listView1);
			state = (TextView)getView().findViewById(R.id.state);
			BtnRefresh = (Button)getView().findViewById(R.id.refresh);
			listView.setAdapter(getBaGJH());
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					XGjhzwDto  value= getBaGJH().getDatas().get(arg2-1);
					MaStation.getInstance().getMoGJH().setVclkDto(value.getXClkDtos());
					MaStation.getInstance().getMoGJH().xGjhzwDto = value;
					Intent intent = new Intent();
					intent.setClass(mcontext, AcMBL.class);
					Bundle bundle = new Bundle();
					intent.putExtra("obj", value);
					bundle.putString("gdm", tabnum);
					
					Log.v("dw", "AcDWFragment  "+ value.getGjhid() +"  " +value.getSwh());
					intent.putExtras(bundle);
					startActivity(intent);
					getActivity().finish();
				}
			});
			
			listView.setOnPullEventListener(new OnPullEventListener<ListView>() {

				@Override
				public void onPullEvent(
						PullToRefreshBase<ListView> refreshView, State state,
						Mode direction) {
					if (state.equals(State.PULL_TO_REFRESH)) {
						//下拉显示内容
//						refreshView.getLoadingLayoutProxy().setPullLabel(getString(R.string.listview_empty));
//						refreshView.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.listview_title));
//						refreshView.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.listview_refesh));
						String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.listview_refeshTime)+":"+label);
					}
					
				}
			});
			
			listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					// TODO Auto-generated method stub
					refreshdate = new Refreshdate();
					refreshdate.execute(tabnum);
				}
				
			});
			
			BtnRefresh.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					refreshdate = new Refreshdate();
					refreshdate.execute(tabnum);
				}
			});
			getHandler().postDelayed(task,0);

	    }// end oncreat

	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putString(KEY_CONTENT, tabnum);
	    }

	
//	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
	}
	@Override
	public void onPause() {
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
							state.setText("该股道无数据");
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

				refreshdate = new Refreshdate();
				refreshdate.execute(tabnum);
	            if (run) {
	            	getHandler().postDelayed(this, 1000*300);  
	            }  
	        }  
	    };  
	    
	
	    private class Refreshdate extends AsyncTask<String, Void, String> {

				@Override
				protected String doInBackground(String... params) {
					String gdm =   params[0];
					return MaStation.getInstance().getMaWebService().getQsgjh(gdm);
				}
				
				@Override
				protected void onPreExecute() {
					getProgressDialog().show();
				}
				
				@Override
				protected void onPostExecute(String result) {
					try{
						if(isNetworkConnected()){
							if(result != null && !result.equals("[]") && !"".equals(result)){
								if("cache".equals(result)){
									ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
								}else {
									GsonBuilder gb = new GsonBuilder();
									gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
									Gson gson = gb.create();
									MaStation.getInstance().getMoGJH().gjhlist = gson.fromJson(result, new TypeToken<LinkedList<XGjhzwDto>>(){}.getType());
									getBaGJH().setDatas(MaStation.getInstance().getMoGJH().gjhlist);
									getBaGJH().notifyDataSetChanged();
									state.setText("");
								}
							}else{
								getBaGJH().setDatas(null);
								getBaGJH().notifyDataSetChanged();
								if(result.equals("{}")){
									state.setText("该股道无对位任务");
								}else {
									ByDialog.showMessage(mcontext, "网络信号不好！");
								}
							}
						}else{
							state.setText("WIFI未开启");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					listView.onRefreshComplete();
					if(getProgressDialog() != null){
						getProgressDialog().cancel();
					}
				}
		}
	    
		/**
		 * 检测网络是否可用
		 * @return
		 */
		public boolean isNetworkConnected() {
			ConnectivityManager cm = (ConnectivityManager)mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			return ni != null && ni.isConnectedOrConnecting();
		}
}
