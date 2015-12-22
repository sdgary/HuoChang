package byxx.android.huochang;

import java.util.LinkedList;
import java.util.Vector;


import byxx.android.huochang.message.AcMessage;

import byxx.android.huochang.task.RuTask;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class SeHeartBeat extends Service {
	Thread thread;
	Context context;
	boolean run = false;
	Handler handler;
	Notification mNotification = null;
	NotificationManager mNotificationManager = null;
	static final int  MSG_HEART = 0x5003a;
	  private static int noticationId = 0xb0001;  
	public static int getNoticationId() {
		return noticationId;
	}

	public void addNoticationId() {
		noticationId +=1 ;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
		
	}
	

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		AppManager.getAppManager().addService(this);
		context = this;
		run = true;
		getHandler().postDelayed(Runnableheart,30000);
        mNotificationManager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
//		thread = new Thread(new RuHeartBeat());
//		if(thread != null || thread.isInterrupted()){
//			thread.start();
//		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
//			if (thread != null && thread.isAlive()) {
//				thread.interrupt();
//			}
			clearNotifications();
			run = false;
			getHandler().removeCallbacks(Runnableheart);
		} catch (Exception e) {

		}
		
	}
	
	  private Runnable Runnableheart = new Runnable() {  
	        public void run() {  
	            // TODO Auto-generated method stub  
				ReadStateHeart heart = new ReadStateHeart();
				heart.execute();
	            if (run) {
	            	getHandler().postDelayed(this, 1000*10);  
	            }  
	        }  
	    };  
	
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
				try {
					switch (msg.what) {
					case MSG_HEART: 

						break;

					default:
					
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
//				MaStation.getInstance().recordExc(e);
			}
			return;
		}
			
		
//	 class RuHeartBeat implements Runnable{
//		 public RuHeartBeat() {
//			// TODO Auto-generated constructor stub
//		}
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			
//				try {
////					int Conut = 0;
//					while(true){
//					try{
//
//					}catch(Exception e){
//						e.printStackTrace();
////						Toast.makeText(context, "连接服务中断，请注意",Toast.LENGTH_SHORT).show();
//					}
////						Log.v("SeHeartBeat", "SeHeartBeat  "+re+"  "+Conut++);
//						Thread.sleep(1000*2);
//					}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		 
//	 }
	 
	 
	 private class ReadStateHeart extends AsyncTask<String, Void, String> {
			
			@Override
			protected String doInBackground(String... params) {
				try {
					return MaStation.getInstance().getMaWebService().heartbeat(MaStation.stationCode, (MaStation.getInstance().getUser().getCode()==null?"": (MaStation.getInstance().getUser().getCode())),(MaStation.getInstance().getUser().getPostName()==null?"": (MaStation.getInstance().getUser().getPostName())), "", System.currentTimeMillis());
				} catch (Exception e) {
					// TODO: handle exception
					return null;
				}
				
			}
			
			@Override
			protected void onPreExecute() {
			}
			
			@Override
			protected void onPostExecute(String result) {

				try{
					if(isNetworkConnected()||MaStation.getInstance().isTest()){
						if(result != null && !result.equals("[]") && !"".equals(result)){

							if("cache".equals(result)){
								ByDialog.showMessage(context, "网络断开，操作已缓存！");
							}else if(Integer.parseInt(result) > 0){
								alertNotification(result);
							}
							Log.v("heart", result);
						}else{
							ByDialog.showMessage(context, "网络信号不好！");
						}
					}else{
						ByDialog.showMessage(context, "WIFI未开启！");
					}
				}catch(Exception e){
					ByDialog.showMessage(context, e.getMessage());
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
		
		
		private void alertNotification(String s) {
			try {

//			if(mNotification != null) {
//				mNotificationManager.cancel(getNoticationId());
//				addNoticationId();
//			}
			Intent intent = new Intent();
			intent.setClass(context, AcMessage.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			PendingIntent pi = PendingIntent.getActivity(context, 1, intent, 0);
			int icon = R.drawable.imessage;  
	        CharSequence tickerText = "消息通知";  
	        long when = System.currentTimeMillis(); 
	        mNotification = new Notification(icon, tickerText, when);
	        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
	        
	        mNotification.contentView = null;
	        mNotification.defaults =Notification.DEFAULT_VIBRATE;
	        mNotification.sound = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.highwire); 
	        mNotification.setLatestEventInfo(context, "消息通知","", pi);  
//	        mNotificationManager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	        mNotificationManager.notify(9876, mNotification);
			} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}

		}
		
		
		/**
		  * 添加一个notification
		  */
//		 private void addNotificaction() {
//		  NotificationManager manager = (NotificationManager) this
//		  .getSystemService(Context.NOTIFICATION_SERVICE);
//		  // 创建一个Notification
//		  Notification notification = new Notification();
//		  // 设置显示在手机最上边的状态栏的图标
//		  notification.icon = R.drawable.excel;
//		  // 当当前的notification被放到状态栏上的时候，提示内容
//		  notification.tickerText = "注意了，我被扔到状态栏了";
//		  
//		  /***
//		   * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，该Intent会被触发
//		   * notification.contentView:我们可以不在状态栏放图标而是放一个view
//		   * notification.deleteIntent 当当前notification被移除时执行的intent
//		   * notification.vibrate 当手机震动时，震动周期设置
//		   */
//		  // 添加声音提示
//		  notification.defaults=Notification.DEFAULT_SOUND;
//		  // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
//		  notification.audioStreamType= android.media.AudioManager.ADJUST_LOWER;
//		  
//		  //下边的两个方式可以添加音乐
//		  //notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
//		  //notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
//		  Intent intent = new Intent(this, Notification2Activity.class);
//		  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//		  // 点击状态栏的图标出现的提示信息设置
//		  notification.setLatestEventInfo(this, "内容提示：", "我就是一个测试文件", pendingIntent);
//		  manager.notify(1, notification);
//		  
//		 }
		
		public void clearNotifications() {
			if(mNotification != null) {
				mNotificationManager.cancel(9876);
				mNotificationManager.cancelAll();
//				mNotificationManager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
//			   	mNotificationManager.cancelAll();
			}
		}
}
