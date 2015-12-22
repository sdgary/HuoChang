//package byxx.android.huochang.task;
//
//
//
//import java.sql.Timestamp;
//import java.util.List;
//
//import org.apache.commons.net.ntp.TimeStamp;
//
//import byxx.android.huochang.BaseActivity;
//import byxx.android.huochang.Constant;
//import byxx.android.huochang.MaStation;
//import byxx.android.huochang.R;
//import byxx.android.huochang.function.AcFunction;
//import byxx.android.huochang.traininfo.AcTrainInfo;
//import byxx.android.huochang.traininfo.BaTrainInfo;
//import byxx.android.huochang.traininfo.CarDto;
//import byxx.android.huochang.traininfo.RuTrainInfo;
//import byxx.android.huochang.util.ByDialog;
//import byxx.android.huochang.util.ByString;
//
//import android.app.ActionBar;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.MenuItem;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class AcTasktab extends BaseActivity{
//	ListView listView;
//	TextView tvTime,tvState;
//	String tabnum ;
//	BaTask mbaTask;
//	Handler handler;
//	Context mcontext ;
//	ListView lv = null;
//	Button buReturnHome;
//	ProgressDialog dialog = null;
//	RuTask ruTask ;
//	List<ZydDto> nListShow ;
//	boolean intRunType = true;
//	int type ;
//	String valueid = null ;
//	AlertDialog.Builder  dialogshow = null;
//	boolean run = false;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.tasktab);
//		
//		final ActionBar actionBar = getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setDisplayShowTitleEnabled(true);
//
//		tvState = (TextView)findViewById(R.id.tvstate);
//		tvTime = (TextView)findViewById(R.id.tvtime);		
//		listView = (ListView)findViewById(R.id.lv_task);
//		listView.setAdapter(getBaTask());
//		
////		listView.setOnItemClickListener(new OnItemClickListener() {
////			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
////					long arg3) {
////				// TODO Auto-generated method stub
//////				Log.v("msg", "点击"+arg2);
////				GJHdto  value= getBaGJH().getDatas().get(arg2);
//////				Log.v("msg", kfcFailure.getId());
//////	    		dialogshow.create().show();  
////				Intent intent = new Intent();
////				intent.setClass(AcGJHTab.this, AcJzx.class);
////				Bundle bundle = new Bundle();
////				bundle.putString("jhh", value.jhno);
////				intent.putExtras(bundle);
////				startActivity(intent);
////			}
////		});
//		
//	}
//	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
////		Intent intent = getIntent();
////		int tabnum1 = intent.getIntExtra("tabnum",0);
////		Log.v("dl", tabnum1+ " onresume"+ " "+ tabnum);
//		run = true;
//		getHandler().postDelayed(Runnabletask,0);
//	}
//	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		run =false;
////		getHandler().post(task);
//		getHandler().removeCallbacks(Runnabletask);
//	}
//	
//	public BaTask getBaTask() {
//		if(mbaTask == null){
//			mbaTask = new BaTask(getHandler());
//		}
//		return mbaTask;
//	}
//
//	public Handler getHandler() {
//		if (handler == null) {
//			handler = new Handler() {
//				public void handleMessage(Message msg) {
//					dealHandleMessage(msg);
//				}
//			};
//		}
//		return handler;
//	}
//
//	private void dealHandleMessage(Message msg) {
//		try {
//			if (msg == null)
//				return;
//			int dId = 0; // 对话框的标识
//			boolean closeDialog = true;
//			try {
//				// 读取完后设置数据
//				Bundle bundle = msg.getData();
//				switch (msg.what) {
//				case Constant.MSG_ID_READ_TASK:
//					dId = 0;
//					boolean ballreal = bundle.getBoolean("realalltask");
//					if (ballreal) {
//						tvTime.setText(ByString.getTimeStr(System.currentTimeMillis(), "dd日 HH:mm"));
//						tvState.setText("");
//						tvState.setTextSize(24);
//						showData();
//					}else{
//						showData();
//						tvState.setTextSize(30);
//						tvState.setText("暂无数据");
//					}
//					ruTask.close();
//					break;
//				case Constant.MSG_ID_SENT_TASK_START_R:
//					dealR(RuTask.SENT_TASK_START);
//				break;
//				case Constant.MSG_ID_SENT_TASK_START:
//					dId = 1;
//					dealCommit(msg.getData(), RuTask.SENT_TASK_START);
//				break;
//				case Constant.MSG_ID_SENT_TASK_OVER_R:
//					dealR(RuTask.SENT_TASK_OVER);
//				break;
//				case Constant.MSG_ID_SENT_TASK_OVER:
//					dId = 1;
//					dealCommit(msg.getData(), RuTask.SENT_TASK_OVER);
//				break;
//				default:
//					break;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if (closeDialog && dialog != null && dialog.isShowing()) {
//				dismissDialog(dId);
//			}
//		} catch (Exception e) {
////			MaStation.getInstance().recordExc(e);
//		}
//		return;
//	}
//	
//	private void dealCommit(Bundle bundle, int type) {
//		// TODO Auto-generated method stub
//
//		try {
//			// 刷新
//
//		if(bundle.getString("ok")!= null){
//				if(bundle.getString("ok").toUpperCase().contentEquals("TRUE")){
//				int id = bundle.getInt("jobId");
//				ZydDto task = MaStation.getInstance().getMoTask().findbyid(id);
//				
//					if (task != null) {
//						Log.v("task","ac  刷新"+task.getZypbId()+"  "+task.getCh());
//						Timestamp tiCur = new Timestamp(System.currentTimeMillis());
//						if(type == RuTask.SENT_TASK_START){
//							if(task.isKS){
//								task.setKsTime(tiCur);
//							}else{
//								task.setKsTime(null); 
//							}
//						}
//						else if(type == RuTask.SENT_TASK_OVER){
//							if(task.isJS){
//								task.setZzTime(tiCur);
//							}else{
//								task.setZzTime(null);
//							}
//						}
//					}
//				}else if(bundle.getString("ok").toUpperCase().contentEquals("FAIL")){
//					ByDialog.showMessage(this, "服务器其他原因，保存失败");
//				}else{
//					ByDialog.showMessage(this, " "+bundle.getString("ok").toString());
//				}
//			}else {
//				ByDialog.showMessage(this, "保存失败，请注意");
//			}
//			nListShow.clear();
//			nListShow = MoTask.deepCopy(MaStation.getInstance().getMoTask().getZydDtos());
//			getBaTask().setDatas(nListShow);
//			getBaTask().notifyDataSetChanged();
//			tvTime.setText("按钮刷新时间 "+ByString.getTimeStr(System.currentTimeMillis(),"dd日 HH:mm:ss"));
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//	
//	}
//	
//
//	private void dealR(int type) {
//		// TODO Auto-generated method stub
//		showDialogM(1);
//		if(ruTask != null){
//			ruTask.close();
//		}
//		
//		ZydDto ttask= MaStation.getInstance().getMoTask().getTaskSelect();
//		ruTask = new RuTask(getHandler());
//		ruTask.setType(type);
//		ruTask.setTask(ttask);
//		ruTask.start();
//	}
//
//	private void showDialogM(int type) {
//		try {
//			showDialog(type);
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//	}
//	
//	private void dellChange(){
//		
//	}
//	private void showData() {
//		try {
//			nListShow = MoTask.deepCopy(MaStation.getInstance().getMoTask().getZydDtos());
//			getBaTask().setDatas(nListShow);
//			for(ZydDto zDto:nListShow){
//				Log.v("task1", zDto.getZypbId()+" ");
//			}
//			getBaTask().notifyDataSetChanged();
//		} catch (Exception e) {
////			MaStation.getInstance().recordExc(e);
//		}
//	}
//	
//	public RuTask getTask() {
//		if (ruTask == null) {
//			ruTask= new RuTask(getHandler());
//			
//		}
//		return ruTask;
//	}
//	
//	  private Runnable Runnabletask = new Runnable() {  
//	      int count = 0;
//	        public void run() {  
//	            // TODO Auto-generated method stub  
////	            Log.v("runTF", "run "+count ++);
////				setType(Rufault.REAL_ALL_TICKETOFFICE_ERR);
//	        	getTask().setType(RuTask.REAL_ALL_TASK);
//	    		Log.v("dl", (count++) + "");
//	    		getTask().start();
//	            if (run) {
//	            	getHandler().postDelayed(this, 1000*5);  
//	            }  
//	        }  
//	    };  
//
//		public boolean onOptionsItemSelected(MenuItem item) {
//			switch(item.getItemId()) {
//				case android.R.id.home:
//					buHomeOnClick();
////					Toast.makeText(this, "bu", Toast.LENGTH_SHORT).show();
//					return true;
//					
//				default:
//					return super.onOptionsItemSelected(item);
//			}
//			
//		}
//
//
//		private void buHomeOnClick() {
//			// TODO Auto-generated method stub
//			Intent intent = new Intent();
//			intent.setClass(AcTasktab.this,AcFunction.class);
//			startActivity(intent);
//			finish();
//		} 
//	    
//}
