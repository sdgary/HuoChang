
package byxx.android.huochang.movebox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import byxx.android.huochang.AppManager;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.WjUtilTime;
import byxx.android.wj.http.cache.LoadingDialog;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @author WGary 韦永城
 * 2015-6-8
 */
public class MoveBoxDetailListActivity extends Activity {
	
	private EventAdapter adapter;
	private Context context;
	private Intent intent;
	Handler handler;
	private Spinner spinner1;
	private Spinner spinner2;
	private String gdm;//筛选股道码
	private List<String> spinList = new ArrayList<String>();
	private List<JzxHwzwModifyDTO> nList = new  ArrayList<JzxHwzwModifyDTO>();//结果集合
	private List<JzxHwzwModifyDTO> itemList = new  ArrayList<JzxHwzwModifyDTO>();
	private boolean reflag = false;//resume是否刷新全部数据，默认为否
	FinishRefresh finishRefresh;
	ProgressDialog progressDialog;
	
	PullToRefreshListView mainList;//下拉列表控件
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		AppManager.getAppManager().addActivity(this);
		
		setContentView(R.layout.activity_move_box_detail);

		context = getApplicationContext();
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		initSpinner1();
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		initSpinner2();
		
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				onSelect(spinner1.getSelectedItem().toString(), spinner2.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				onSelect(spinner1.getSelectedItem().toString(), spinList.get(arg2));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		mainList = (PullToRefreshListView) findViewById(R.id.mainList);
		mainList.setMode(Mode.PULL_FROM_START);
		//list适配器
		adapter = new EventAdapter(this);
		mainList.setAdapter(adapter);
		//点击事件
		mainList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				TextView idView = (TextView) view.findViewById(R.id.jzxnum);
//				String yxId = idView.getText().toString().replace("集装箱号:", "");
//				JzxHwzwModifyDTO dto= null;
//				for (int i = 0; i < itemList.size(); i++) {
//					if (itemList.get(i).getJzxnum().equals(yxId)) {
//						dto = itemList.get(i);
//					}
//				}
				JzxHwzwModifyDTO dto = (JzxHwzwModifyDTO) adapter.getItem(position - 1);
				Intent intent = new Intent();
				intent.setClass(context, MoveBoxPageActivity.class);
//				Bundle bundle = new Bundle();
				intent.putExtra("obj", dto);
//				bundle.put
//				intent.putExtras(bundle);
				reflag = true;//返回时将刷新为全部数据
				startActivity(intent);
//				startActivityForResult(intent, Constant.MOVEBOX_REQ);
//				AppManager.getAppManager().finishActivity(MoveBoxDetailListActivity.this);
//				MoveBoxDetailListActivity.this.finish();

			}
		});
		
		mainList.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
									State state, Mode direction) {
				if (state.equals(State.PULL_TO_REFRESH)) {
//						refreshView.getLoadingLayoutProxy().setPullLabel(getString(R.string.listview_empty));
//						refreshView.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.listview_title));
//						refreshView.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.listview_refesh));
					String label = DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.listview_refeshTime) + ":" + label);
				}
			}
		});
			
		mainList.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				refreshEventListItem(context);
			}

		});
		
//		refreshEventListItem(getApplicationContext());
		refreshEventListItem(context);
		AppManager.getAppManager().addActivity(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(reflag){
			refreshEventListItem(context);
		}
		reflag = false;//刷新完成后，避免刷卡筛选冲突
		adapter.notifyDataSetChanged();
		MaStation.getInstance().getMoNfc().setHandlerFunc(getHandler(), false);
		
		MaStation.getInstance().getMoNfc().setHandler(getHandler());
		MaStation.getInstance().getMoNfc().setIdOrData(false);
	}

	@Override
	protected void onStart() {
		try {
//			MaStation.getInstance().getMoNfc().setHandler(getHandler());
			
			//本功能不需要显示nfcID

			MaStation.getInstance().getMoNfc().setHandlerFunc(getHandler(), false);
			
			MaStation.getInstance().getMoNfc().setHandler(getHandler());
			MaStation.getInstance().getMoNfc().setIdOrData(false);
//			MaStation.getInstance().handlerTask=getHandler();
			
//			if(MaStation.getInstance().handlerTask != null){
//				MaStation.getInstance().handlerTask=getHandler();
//			}
//			MaStation.getInstance().getMoNfc().setHandler(
//					MaStation.getInstance().handlerTask);

		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		//
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		if (MaStation.getInstance().handlerTask != null) {
//			MaStation.getInstance().getMoNfc().setHandler(
//					MaStation.getInstance().handlerTask, true);
//		}
		AppManager.getAppManager().finishActivity(this);
	}

	public Handler getHandler() {
		if (handler == null) {
			handler = new Handler(){
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
			try {
				// 读取完后设置数据
				switch (msg.what) {
				case Constant.MSG_NFC_DATA: // nfc返回
					String regionId = msg.getData().getString("blockData");
					if (("NO_CHECK").equals(regionId)) {
						 // NFC卡没有检测到
					} else {
						getNFC(regionId);
					}
					break;
					
				case Constant.MSG_NFC_NULL://mfc为空
					Toast.makeText(context, "没有获取到TAG信息，无法读取各扇区数据，请更换磁卡！", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	private void getNFC(String regionId){
		//搜索到的数据
		List<JzxHwzwModifyDTO> matchList = new ArrayList<JzxHwzwModifyDTO>();
		for (int i = 0; i < itemList.size(); i++) {
			if (regionId.equals(itemList.get(i).getJobId())) {
				matchList.add(itemList.get(i));
			}
		}
		
		if (matchList.size()==0) {
			Toast.makeText(context, "没有找到符合的数据", Toast.LENGTH_SHORT).show();
		}else if (matchList.size()==1) {
			intent = new Intent(MoveBoxDetailListActivity.this,MoveBoxPageActivity.class);
			intent.putExtra("obj", matchList.get(0));
			startActivity(intent);
		}else {
//			LoadingDialog.create(context, "搜索到"+matchList.size()+"条数据", LoadingDialog.RIGHT);
			itemList.clear();
			itemList.addAll(matchList);
			adapter.notifyDataSetChanged();
		}
	}
	
	
	public void refreshEventListItem(final Context context){
		finishRefresh = new FinishRefresh();
		finishRefresh.execute();
		
	}
	
	  //下拉单组合数据加载
	private void initSpinner1(){
		//first:clear data
		List<String> spin = new ArrayList<String>();
		spin.add("全部");
		for(int i =0 ; i< MaStation.getInstance().getUser().getGdm().length ; i++){
			spin.add(MaStation.getInstance().getUser().getGdm()[i]);
		}
		ArrayAdapter<String> selectAdapterTrack = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,spin);
		selectAdapterTrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(selectAdapterTrack);
		spinner1.setSelection(0);
		
	}
	
	private void initSpinner2(){
		//first:clear data
		spinList.clear();
		spinList.add("全部");
		spinList.add("直装");
		spinList.add("直卸");
		spinList.add("站内调整");
		spinList.add("汽车到达");
		spinList.add("汽车出发");
		spinList.add("火车到达");
		spinList.add("火车出发");
		ArrayAdapter<String> selectAdapterTrack = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,spinList);
		selectAdapterTrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(selectAdapterTrack);
		spinner2.setSelection(0);
	}
	
	
	
	private class FinishRefresh extends AsyncTask<String, Void, String>{  
        @Override  
        protected String doInBackground(String... params) {  
            return 	MaStation.getInstance().getMaWebService().getYxjh();
//            nList =JSON.parseArray(info.getData().toString(), JzxHwzwModifyDTO.class);;  
        }
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MoveBoxDetailListActivity.this);
			progressDialog.setMessage("正在提交,请稍候...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialogInterface) {
					if (finishRefresh != null) {
						finishRefresh.cancel(true);
					}
				}
			});
			progressDialog.show();
		}

        @Override
        protected void onPostExecute(String result){
			try {
//        	if(isNetworkConnected()){
				if(result != null && !result.equals("[]") && !"".equals(result)){
					if("cache".equals(result)){
						ByDialog.showMessage(context, "网络断开，操作已缓存！");
					}else{
						StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
						if(info != null && !info.equals("[]") && info.getData() != null && !"".equals(info)){
							nList = JSON.parseArray(info.getData().toString(), JzxHwzwModifyDTO.class);
							if(nList!= null && nList.size()>0){
								itemList.clear();
								itemList.addAll(nList);
								adapter.notifyDataSetChanged();
								initSpinner1();
								initSpinner2();
							}
						}
					}
//					List<JzxHwzwModifyDTO> nList = null;
				}else{
					ByDialog.showMessage(context, "网络异常");
				}
				mainList.onRefreshComplete();
				if(progressDialog != null){
					progressDialog.cancel();
				}
			}catch (Exception e){
				MaStation.getInstance().recordExc(e);
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
	
	public void onSelect(String gdm, String sp){
//		List<String> tmp = new ArrayList<String>();
		if("全部".equals(sp)){
			itemList.clear();
			itemList.addAll(nList);
		}else if("直装".equals(sp)){
			itemList.clear();
			for (JzxHwzwModifyDTO o : nList) {
				if(o.getWorkType() == 41 ){
					itemList.add(o);
				}
			}
//			itemList.addAll(nList);
		}else if("直卸".equals(sp)){
			itemList.clear();
			for (JzxHwzwModifyDTO o : nList) {
				if(o.getWorkType() == 40 ){
					itemList.add(o);
				}
			}
		}else if("站内调整".equals(sp)){
			itemList.clear();
			for (JzxHwzwModifyDTO o : nList) {
				if(o.getWorkType() == 30 ){
					itemList.add(o);
				}
			}
			
		}else if("汽车到达".equals(sp)){
			itemList.clear();
			for (JzxHwzwModifyDTO o : nList) {
				if(o.getWorkType() == 20 ){
					itemList.add(o);
				}
			}
			
		}else if("汽车出发".equals(sp)){
			itemList.clear();
			for (JzxHwzwModifyDTO o : nList) {
				if(o.getWorkType() == 21 ){
					itemList.add(o);
				}
			}
		}else if("火车到达".equals(sp)){
			itemList.clear();
			for (JzxHwzwModifyDTO o : nList) {
				if(o.getWorkType() == 10 ){
					itemList.add(o);
				}
			}
		}else if("火车出发".equals(sp)){
			itemList.clear();
			for (JzxHwzwModifyDTO o : nList) {
				if(o.getWorkType() == 11 ){
					itemList.add(o);
				}
			}
		}
//			tmp.clear();
//			tmp.addAll(itemList);
		if("全部".equals(gdm)){
			
		}else{
			for(int i=0; i<itemList.size(); i++){
				JzxHwzwModifyDTO b = itemList.get(i);
				if(b.getGdm()!= null && gdm!=null && !b.getGdm().equals(gdm)){
					itemList.remove(b);
					i--;
				}
			}
		}
		
		adapter.notifyDataSetChanged();
	}
	
	
	/*      
	 * 新建一个类继承BaseAdapter，实现视图与数据的绑定
     */
	private class EventAdapter extends BaseAdapter {
		
		public EventAdapter(Context context) {
        }

        @Override
        public int getCount() {
            return itemList.size();//返回数组的长度        
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        
        /*书中详细解释该方法*/
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
        	ListItemMoveBoxDetail mainListItem = new ListItemMoveBoxDetail();;
            if(view == null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
                view = inflater.inflate(R.layout.move_box_main_detail_list_item, null);
                mainListItem = new ListItemMoveBoxDetail();
                mainListItem.setXh((TextView)view.findViewById(R.id.xh));
                mainListItem.setWorkType((TextView)view.findViewById(R.id.workType));
                mainListItem.setJzxnum((TextView)view.findViewById(R.id.jzxnum));
                mainListItem.setNewHw((TextView)view.findViewById(R.id.newHw));
                mainListItem.setOldHw((TextView)view.findViewById(R.id.oldHw));
                mainListItem.setStatus((TextView)view.findViewById(R.id.status));
                mainListItem.setId((TextView)view.findViewById(R.id.id));
                view.setTag(mainListItem);
            }else {
				mainListItem = (ListItemMoveBoxDetail) view.getTag();
			}
            
            JzxHwzwModifyDTO dto = itemList.get(position);
            mainListItem.getId().setText(dto.getPk());
            mainListItem.getXh().setText((position+1)+"   "+MoveBoxUtils.getFlag(dto.getWorkType()));
            mainListItem.getWorkType().setText(MoveBoxUtils.getZWType(dto.getGdm(), dto.getWorkType()));
            mainListItem.getJzxnum().setText("股道：" + dto.getGdm()+ "   集装箱号:"+dto.getJzxcode()+dto.getJzxnum());
            mainListItem.getNewHw().setText("新货位:"+dto.getNewHw());
            mainListItem.getOldHw().setText("原货位:"+dto.getOldHw());
            if (dto.getWorkTime()!=null) {
            	mainListItem.getStatus().setText("执行时间:"+WjUtilTime.HHmm.format(dto.getWorkTime()));
            	mainListItem.getXh().setTextColor(Color.rgb(2, 123, 200)); 
			}else if(dto.getIsRefuse() == 1){
				mainListItem.getStatus().setText("已拒收");
				mainListItem.getXh().setTextColor(Color.RED);
			}else{
				mainListItem.getStatus().setText("未执行");
				mainListItem.getXh().setTextColor(Color.BLACK);
			}
            return view; 
        }

			
	}
}
