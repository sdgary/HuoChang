//package byxx.android.huochang.alert;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map.Entry;
//
//import android.app.ActionBar;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
//
//
///**
// * 
// */
//public class AcTaskChange extends Activity {
//
//	private Context context;
//	private ListView listView;
//	private TextView stateView;
//	private BATaskChange listViewAdapter;
//	
//	private ProgressDialog progressDialog;
//	private Thread thread;
//	private AlertDialog.Builder dialogshow;
//	private Handler handler;
//	private RuTaskRefresh ruTaskRefresh;
//	private static boolean run = false;
//	
//	private Runnable task = new Runnable() {
////		int num = 0;
//        public void run() {
//            // TODO Auto-generated method stub
////        	if(progressDialog != null && progressDialog.isShowing()){
////        		progressDialog.dismiss();
////        	}
////        	else{
////        		progressDialog = ProgressDialog.show(context, 
////                  "请稍等...", "数据正在加载中...", true);
////        	}
//        	StartRuTaskRefresh();
////        	Log.v("runTF", "run " + num++);
////        	Log.v("runTF", "count "+ ruTaskRefresh.getAlertMess().size());
//            if (run) {
//            	// 数据隔一定时间刷新一次
//            	getHandler().postDelayed(this, 1000 * 10);
//            }
//        }
//    };
//    
//    public void showDialog(String s) {
//    	dialogshow = new AlertDialog.Builder(context);
//    	dialogshow.setTitle("提示");
//    	dialogshow.setMessage(s);
//    	dialogshow.setPositiveButton("确定",  new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//			}
//		});
//    	dialogshow.create().show();
//    }
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		try{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.taskchangealert);
//		SuperApplication.getInstance().addActivity(this);
//		this.context = this;
//		ruTaskRefresh = getRuTaskRefresh();
//		
//		final ActionBar actionBar = getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setDisplayShowTitleEnabled(true);
//		
//		stateView = (TextView) findViewById(R.id.stateView);
////		stateView.setVisibility(View.GONE);
//		listView = (ListView) findViewById(R.id.listView);
//		listViewAdapter = new BATaskChange();
//		listView.setAdapter(listViewAdapter);
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				ViewHolder holder = (ViewHolder) arg1.getTag();
//				// 改变CheckBox的状态
//				holder.checkBox.toggle();
//				// 将CheckBox的选中状况记录下来
//				listViewAdapter.getIsSelected().put(arg2, holder.checkBox.isChecked());
//				if(holder.checkBox.isChecked())
//					holder.textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.listview_shape2));
//				else
//					holder.textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.listview_shape));
//
////				Log.v("BlankOut" , "arg2: " + arg2);
////				Log.v("BlankOut" , "checkBox: " + holder.checkBox.isChecked());
//				// TODO Auto-generated method stub
////				showDialog();
////				thread = new Thread(
////						new RuBlankOut(getHandler(), getRuTaskRefresh(), position , listViewAdapter.getIsSelected()));
////				listViewAdapter.notifyDataSetChanged();
//			}
//		});
//		 showDialog(0);
//		}catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//		
//	}
//	
//	// 设置菜单
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.taskchangealert_menu, menu);
//		return true;
//	}
//	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		run = true;
////		progressDialog = ProgressDialog.show(context, 
////                "请稍等...", "数据正在加载中...", true);
////		getHandler().postDelayed(task, 0);
//		getHandler().post(task);
//	}
//	
//	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
////		StopSpeek();
//		super.onPause();
//		run = false;
//		getHandler().removeCallbacks(task);
//	}
//	
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
////		StopSpeek();
//		super.onStop();
//		StopRuTaskRefresh();
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch(item.getItemId()) {
//			case android.R.id.home:
//				Intent intent = new Intent(context, AcNewFunctions.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(intent);
//				return true;
//			case R.id.selectAll:
//				for (int i = 0; i < ruTaskRefresh.getAlertMess().size(); i++) {          
//					listViewAdapter.getIsSelected().put(i, true);
//				}
//				listViewAdapter.notifyDataSetChanged();	// 更新listViewAdapter数据
//				return true;
//			case R.id.cancelAll:
//				if(listViewAdapter.getIsSelected().isEmpty())
//					return true;
//				else {
////					for(int i = 0; i < ruTaskRefresh.getAlertMess().size(); i++) {
////						if(listViewAdapter.getIsSelected().get(i))
////							listViewAdapter.getIsSelected().put(i, false);
////					}
//					listViewAdapter.getIsSelected().clear();
//					listViewAdapter.notifyDataSetChanged();	// 更新listViewAdapter数据
//				}
//				return true;
//			case R.id.clearTask:
//				thread = new Thread(
//						new RuBlankOut(getHandler(), getRuTaskRefresh(), listViewAdapter.getIsSelected()));
//				thread.start();
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//		}
//		
//	}
//	
//	// RuTaskRefresh Runnable
//	public void StartRuTaskRefresh() {
//		getRuTaskRefresh().start();
//	}
//
//	public void StopRuTaskRefresh() {
//		getRuTaskRefresh().close();
//	}
//	
//	public RuTaskRefresh getRuTaskRefresh() {
//		if (ruTaskRefresh == null) {
//			ruTaskRefresh = new RuTaskRefresh(getHandler(), context);
//		}
//		return ruTaskRefresh;
//	}
//	
//	protected Dialog onCreateDialog(int id) {
//		try {
//			if (progressDialog == null) {	
//				progressDialog = new ProgressDialog(this);
//				switch (id) {
//				case 0:
//					progressDialog.setMessage("正在读取，请稍候...");
//					break;
//				case 1:
//					progressDialog.setMessage("正在提交，请稍候...");
//					break;
//				default:
//					break;
//				}
//				progressDialog.setIndeterminate(true);
//				progressDialog.setCancelable(true);
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//		return progressDialog;
//	}
//	
//	
//	
//	private Handler getHandler() {
//		if (handler == null) {
//			handler = new Handler() {
//				@Override
//				public void handleMessage(Message msg) {
//					dealHandleMessage(msg);
//				}
//			};
//		}
//		return handler;
//	}
//	
//	private void dealHandleMessage(Message msg) {
//		boolean closeDialog = true;
//		try {
//			if (msg == null || msg.getData() == null)
//				return;
//			String s;
//			
//			switch (msg.what) {
//				case Constant.REFRESH_DONE:
//					// 添加入list
////					Log.v("refresh", ruTaskRefresh.getAlertMess().size() + ByString.getTimeStr(System.currentTimeMillis() , "mm:ss") );
//					ruTaskRefresh.getAlertMess().clear();
//					for(KfcJobMsg kfcJobMsg : ruTaskRefresh.getJobMsgList()) {
//						if(kfcJobMsg.getMess().length()> 0){
//							ruTaskRefresh.getAlertMess().add(kfcJobMsg.getMess());
//						}
////						ruTaskRefresh.setCount(ruTaskRefresh.getCount() + 1);
//					}
//					if(ruTaskRefresh.getNetworkConnected()) {
//						s = "任务变更数量为：" + ruTaskRefresh.getAlertMess().size() + "   网络连接正常！";
//						stateView.setBackgroundColor(getResources().getColor(R.color.green));
//					}
//					else {
//						s = "无法读取任务变更数量    网络连接出错";
//						stateView.setBackgroundColor(getResources().getColor(R.color.red));
//					}
//					stateView.setText(s);
////					if(ruTaskRefresh.getAlertMess().size() == 0) {
////						Toast.makeText(context, "无任务变更！", Toast.LENGTH_SHORT).show();
////					}
////					Log.v("BlankOut" , "count: " + ruTaskRefresh.getCount());
////					Log.v("BlankOut" , "alertMess: " + ruTaskRefresh.getJobMsgList().get(0).getMess());
////					Log.v("BlankOut" , "msgId: " + ruTaskRefresh.getJobMsgList().get(0).getMsgId());
//					listViewAdapter.notifyDataSetChanged();	// 更新listViewAdapter数据
//					StopRuTaskRefresh();
//					
//					break;
//				case Constant.REFRESH_FAILED:
////					progressDialog.dismiss();
//					if(ruTaskRefresh.getNetworkConnected()) {
//						s = "任务变更数量为：" + ruTaskRefresh.getAlertMess().size() + "   网络连接正常！";
//						stateView.setBackgroundColor(getResources().getColor(R.color.green));
//					}
//					else {
//						s = "无法读取任务变更数量    网络连接出错";
//						stateView.setBackgroundColor(getResources().getColor(R.color.red));
//					}
//					stateView.setText(s);
//					listViewAdapter.notifyDataSetChanged();	// 更新listViewAdapter数据
//					StopRuTaskRefresh();
//					
//					break;
//				case Constant.BLANKOUT_TRUE:
////					int position = msg.getData().getInt("position");
////					Toast.makeText(context, "确任接受任务变更信息: "+ ruTaskRefresh.getJobMsgList().get(position).getMsgId(), Toast.LENGTH_SHORT).show();
//					// 删除当前信息的显示
////					ruTaskRefresh.getAlertMess().remove(position);
////					ruTaskRefresh.getJobMsgList().remove(position);
////					if(!listViewAdapter.getIsSelected().isEmpty()) {
////						Log.v("BlankOut", "listViewAdapter.getIsSelected().size: " + listViewAdapter.getIsSelected().size());
////						Iterator<Entry<Integer, Boolean>> it = listViewAdapter.getIsSelected().entrySet().iterator();
////						Entry<Integer, Boolean> entry;
////						while(it.hasNext()) {
////							entry = it.next();
////							// 如果value为true则表示被选中
////							if(entry.getValue().booleanValue()) {
////								ruTaskRefresh.getJobMsgList().remove(entry.getKey().intValue());
////								ruTaskRefresh.getAlertMess().remove(entry.getKey().intValue());
//////								listViewAdapter.getIsSelected().remove(entry.getKey().intValue());
////							}
////						}
////						listViewAdapter.getIsSelected().clear();
////					}
//					// 数据重新刷新从数据库取回来
//
//					StartRuTaskRefresh();
////					ruTaskRefresh.getAlertMess().clear();
////					for(KfcJobMsg kfcJobMsg : ruTaskRefresh.getJobMsgList()) {
////						ruTaskRefresh.getAlertMess().add(kfcJobMsg.getMess());
////					}
//					showDialog("成功确认" + listViewAdapter.getIsSelected().size() +"条数据变更！");
//					listViewAdapter.getIsSelected().clear();
////					if(ruTaskRefresh.getNetworkConnected()) {
////						s = "任务变更数量为：" + ruTaskRefresh.getAlertMess().size() + "   网络连接正常！";
////					}
////					else {
////						s = "无法读取任务变更数量    网络连接出错";
////						stateView.setBackgroundColor(getResources().getColor(R.color.red));
////					}
////					stateView.setText(s);
////					listViewAdapter.notifyDataSetChanged();	// 更新listViewAdapter数据
//					thread.interrupt();	// 停止RuBlankOut线程
//					break;
//				case Constant.BLANKOUT_FALSE:
////					position = msg.getData().getInt("position");
////					Toast.makeText(context, "无法确任任务变更信息: " + ruTaskRefresh.getJobMsgList().get(position).getMsgId(), Toast.LENGTH_SHORT).show();
//					// 数据重新刷新从数据库取回来
//
//					StartRuTaskRefresh();
////					ruTaskRefresh.getAlertMess().clear();
////					for(KfcJobMsg kfcJobMsg : ruTaskRefresh.getJobMsgList()) {
////						ruTaskRefresh.getAlertMess().add(kfcJobMsg.getMess());
////					}
//					showDialog("确认数据变更失败！");
//					listViewAdapter.getIsSelected().clear();
////					if(ruTaskRefresh.getNetworkConnected()) {
////						s = "任务变更数量为：" + ruTaskRefresh.getAlertMess().size() + "   网络连接正常！";
////					}
////					else {
////						s = "无法读取任务变更数量    网络连接出错";
////						stateView.setBackgroundColor(getResources().getColor(R.color.red));
////					}
////					stateView.setText(s);
////					listViewAdapter.notifyDataSetChanged();	// 更新listViewAdapter数据
//					thread.interrupt();	// 停止RuBlankOut线程
//					break;
//				default:
//					break;
//			
//			}
//		}
//		catch(Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//		if (closeDialog && progressDialog != null && progressDialog.isShowing()) {
//			dismissDialog(0);
//		}	
//		
//	}
//	
//	public class BATaskChange extends BaseAdapter {
//
//		private Context context = null;
//		private LayoutInflater mInflater;
//		// 利用List保存选择状态
////		private List<Integer> isSelected;
//		// 利用HashMap保存选择状态
//		private HashMap<Integer, Boolean> isSelected;
//		
//		public HashMap<Integer, Boolean> getIsSelected() {
//			return isSelected;
//		}
//
//		public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
//			this.isSelected = isSelected;
//		}
//
////		public List<Integer> getIsSelected() {
////			return isSelected;
////		}
////
////		public void setIsSelected(List<Integer> isSelected) {
////			this.isSelected = isSelected;
////		}
//
//		public BATaskChange () {
////			isSelected = new ArrayList<Integer>();
//			isSelected = new HashMap<Integer, Boolean>();
////			initDate();
//		}
//		
////		// 初始化isSelected的数据   
////		private void initDate(){
////			for(int i = 0; i < 100; i++)
////				getIsSelected().put(i, false);
////		}
//		
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return ruTaskRefresh.getAlertMess().size();
//		}
//		
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//		
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			this.context = parent.getContext();
//			this.mInflater = LayoutInflater.from(this.context);
//			ViewHolder holder = null;
//			
//			if(convertView == null) {
//			    convertView = mInflater.inflate(R.layout.taskchangealert_items, null);
//			    holder = new ViewHolder();
//			    holder.textView = (TextView) convertView.findViewById(R.id.taskchange_item);
//			    holder.checkBox = (CheckBox) convertView.findViewById(R.id.taskchange_checkbox);
//			    convertView.setTag(holder);
//			}
//			else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			
//			// 设置内容为任务变更信息
//			holder.textView.setText(ruTaskRefresh.getAlertMess().get(position));
//			
//			// 设置选择项
//			if(!getIsSelected().isEmpty() && getIsSelected().get(position) != null){
//				holder.checkBox.setChecked(getIsSelected().get(position));
//				if(holder.checkBox.isChecked()){
//					holder.textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.listview_shape2));
//					holder.textView.setPadding(8, 10, 6, 6);
//				}else{
//					holder.textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.listview_shape));
//					holder.textView.setPadding(8, 10, 6, 6);
//				}
//			}
//			else{
//				holder.checkBox.setChecked(false);
//				holder.textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.listview_shape));
//				holder.textView.setPadding(8, 10, 6, 6);
//			}
//			
////			final CheckBox checkBox = holder.checkBox;
////			// 设置是否选择
////	        holder.checkBox.setOnClickListener(new OnClickListener() {
////				public void onClick(View arg0) {
////					// TODO Auto-generated method stub
////					if(isSelected.contains(new Integer(position))) {
////						// 取消
////	                    isSelected.remove(new Integer(position));
////	                    checkBox.setChecked(false);
////	                }
////	                else {
////	                	// 选中
////	                	isSelected.add(new Integer(position));
////	                    checkBox.setChecked(true);
////	                }
////				}     
////	        });     
//
//			// 确定接受任务变更信息
//			return convertView;
//		}
//		
//		public class ViewHolder {
//			TextView textView;	// 信息栏
//			CheckBox checkBox;	// 选择项
//		}
//	}
//	
//}
