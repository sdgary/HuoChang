package byxx.android.huochang.alert;

import java.util.ArrayList;
import java.util.List;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.MyExecutorService;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * 	
 */
public class RuTaskRefresh implements Runnable {
	
	private Thread thread;
	private Handler handler;
//	private List<KfcJobMsg> jobMsgList;
	private List<String> alertMess;
//	private int count;
	private Context context;
	private boolean networkConnected = false;
	
//	public List<KfcJobMsg> getJobMsgList() {
//		return jobMsgList;
//	}
//
//	public void setJobMsgList(List<KfcJobMsg> jobMsgList) {
//		this.jobMsgList = jobMsgList;
//	}
	
	public List<String> getAlertMess() {
		return alertMess;
	}

	public void setAlertMess(List<String> alertMess) {
		this.alertMess = alertMess;
	}

	public boolean getNetworkConnected() {
		return networkConnected;
	}

	public void setNetworkConnected(boolean networkConnected) {
		this.networkConnected = networkConnected;
	}
	
//	public int getCount() {
//		return count;
//	}
//
//	public void setCount(int count) {
//		this.count = count;
//	}
	
	public RuTaskRefresh() {
		// TODO Auto-generated constructor stub
	}
	
	public RuTaskRefresh(Handler handler, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.handler = handler;
//		count = 0;
		alertMess = new ArrayList<String>();
//		jobMsgList = new ArrayList<KfcJobMsg>();
	}
	
	public void close() {
		try {
			if (thread != null && thread.isAlive()) {
				thread.interrupt();
			}
		} 
		catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		thread = null;
	}
	
	public void start() {
		if (thread == null || thread.isInterrupted()) {
			thread = new Thread(this, this.getClass().getName());
//			thread.start();
			MyExecutorService.getInstance().addThread(thread);
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			refreshTask();
//			Log.v("refresh", "refreshTask");
		} 
		catch (Exception e) {
			MaStation.getInstance().recordExc(e);
//			Log.v("refresh", e + "");
		}
		
	}
	
	// 判断是否连接网络
	public boolean isNetworkConnected(Context context) {  
	    if (context != null) {
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();
	        }  
	    }  
	    return false;  
	}
	
	// 刷新任务变更
	private void refreshTask() {
		if(isNetworkConnected(context)) {
			setNetworkConnected(true);
			// 如果是测试数据
//			if(MaStation.getInstance().isTest()) {
//				setJobMsgList(MaStationTest.getInstance().dynamicRefurbish(
//						MaStation.getInstance().getStationCode(), 
//						MaStation.getInstance().getUser().getCode()));
//			}
			// 如果是实际数据
//			else {
//				setJobMsgList(MaStation.getInstance().getMaWebService().dynamicRefurbish(
//								MaStation.stationCode, 
//								MaStation.getInstance().getUser().getCode()));

				
//			}
			if(handler != null) {
				Message msg = new Message();
//				msg.what = Constant.REFRESH_DONE;
				handler.sendMessage(msg);
//				Log.v("refresh", Constant.REFRESH_DONE + "");
			}
		}
		else
		{
			if(handler != null) {
				Message msg = new Message();
//				msg.what = Constant.REFRESH_FAILED;
				handler.sendMessage(msg);
//				Log.v("refresh", Constant.REFRESH_FAILED + "");
			}
		}
	}
	
}
