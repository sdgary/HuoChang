package byxx.android.huochang.message;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.util.ByDialog;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class AcMessage extends BaseActivity{
	PullToRefreshListView listView;
	BaMessage baMessage;
	ProgressDialog progressDialog;
	ReadMSG msg;
	Context context;
	Button refresh;
	TextView state;
	private List<XMsgDto> nList = new  ArrayList<XMsgDto>();//结果集合
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message);
		
		context = this;
		listView = (PullToRefreshListView)findViewById(R.id.lv_msg);
		refresh = (Button)findViewById(R.id.refresh);
		state = (TextView)findViewById(R.id.tvstate);
		
		listView.setAdapter(getBaMessage());
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				XMsgDto dto = (XMsgDto) getBaMessage().getItem(position-1);
				if(dto != null){
					Intent intent = new Intent();
					intent.setClass(context, AcMessageSend.class);
//				Bundle bundle = new Bundle();
					dto.setReply(true);//点击即回复模式，人员选择禁用
					intent.putExtra("obj", dto);
//				bundle.put
//				intent.putExtras(bundle);
					startActivity(intent);
				}else{
					ByDialog.showMessage(context,"消息数据有错，请联系中心！");
				}

			}
		});
		
		listView.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
					if (state.equals(State.PULL_TO_REFRESH)) {
//						refreshView.getLoadingLayoutProxy().setPullLabel(getString(R.string.listview_empty));
//						refreshView.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.listview_title));
//						refreshView.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.listview_refesh));
						String label = DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.listview_refeshTime)+":"+label);
					}
				}
			});
			
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				refreshEventListItem();
			}
				
		});
		
		
		
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				msg = new ReadMSG();
				msg.execute();
			}
		});
//		ReadMSG msg = new ReadMSG();
//		msg.execute();
		refreshEventListItem();
		AppManager.getAppManager().addActivity(this);
	}
	
	public void refreshEventListItem(){
		msg = new ReadMSG();
		msg.execute();
	}
	
	BaMessage getBaMessage(){
		if(baMessage == null){
			baMessage = new BaMessage();
		}
		return baMessage;
	}
	
	private class ReadMSG extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			return MaStation.getInstance().getMaWebService().getMsg();
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(AcMessage.this);
			progressDialog.setMessage("正在读取,请稍候...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialogInterface) {
					if (msg != null) {
						msg.cancel(true);
					}
				}
			});
			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {

			try{
				if(isNetworkConnected()||MaStation.getInstance().isTest()){
					if(result != null && !result.equals("[]") && !"".equals(result)){

						if("cache".equals(result)){
							ByDialog.showMessage(context, "网络断开，操作已缓存！");
						}else{
							StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);

							if(info != null && !info.equals("[]") && info.getData() != null  && !"".equals(info)){
								nList = JSON.parseArray(info.getData().toString(), XMsgDto.class);
								if(nList!= null && nList.size()>0){
									getBaMessage().setDatas(nList);
									getBaMessage().notifyDataSetChanged();
									listView.onRefreshComplete();
//								for(XMsgDto d : nList){
//									if(d != null){
//										MaStation.getInstance().addMsg(d);
//									}
//
//								}
								}else{
									state.setText("当前暂无消息！");
									state.setTextColor(Color.RED);
								}
							}
						}
//						if(progressDialog != null){
//							progressDialog.dismiss();
//						}
					}else{
//						if(progressDialog != null){
//							progressDialog.dismiss();
//						}
						listView.onRefreshComplete();
						ByDialog.showMessage(context, "网络信号不好！");
					}
				}else{// first if
//					if(progressDialog != null){
//						progressDialog.dismiss();
//					}
					listView.onRefreshComplete();
				}// first if
			}catch(Exception e){
				if(progressDialog != null){
					progressDialog.cancel();
				}
				listView.onRefreshComplete();
				MaStation.getInstance().recordExc(e);
			}
			if(progressDialog != null){
				progressDialog.cancel();
			}
		}
		
	}	
	
	
	private void showData() {
		try {
			List<XMsgDto> nListShow = MaStation.getInstance().getMoMessage().getMsgDtos();
			getBaMessage().setDatas(nListShow);
			getBaMessage().notifyDataSetChanged();
			if(nListShow == null || nListShow.size() <=0 ){
				state.setText("当前暂无消息！");
				state.setTextColor(Color.RED);
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
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


	private void buHomeOnClick() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(AcMessage.this,AcFunction.class);
		startActivity(intent);
		finish();
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
}
