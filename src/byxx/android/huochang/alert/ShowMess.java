package byxx.android.huochang.alert;

import java.util.Vector;

import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.StaticClass;

import android.R.integer;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


public class ShowMess extends Thread {
	public static final int TYPE_SHOW = 999;
	public static final int TYPE_TASK_START=998;
    private static int noticationId = 0xb0078;  
//	public static int taskchangenum = 0;
	
	boolean runFlag = true;

	Handler handlerAlert = null;// 告警页

//	MoTask moTask;

	boolean nextMess = false;
	
	private ThSpeek speek;
	
	Vector<NoAlertMess> veData = new Vector<NoAlertMess>();

	private NotificationManager mNotificationManager = null;  
    private Notification mNotification = null; 
    private static int id = 1;
	
	public static int getNoticationId() {
		return noticationId;
	}

	public void addNoticationId() {
		ShowMess.noticationId +=1 ;
	}

	public Handler getHandlerAlert() {
		return handlerAlert;
	}

	public void setHandlerAlert(Handler handlerAlert) {
		this.handlerAlert = handlerAlert;
	}

	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}

	public boolean isNextMess() {
		return nextMess;
	}

	public void setNextMess(boolean nextMess) {
		this.nextMess = nextMess;
		Log.v("1011", "setNextMess(boolean nextMess) "+isNextMess());
	}

	/**
	 * add 要弹出的内容（NoAlertMess）
	 * @param obj
	 */
	public void add(NoAlertMess obj) {
		try {
			if (!isIn(obj)) {
				Log.v("al", "add(NoAlertMess obj) -----!isIn(obj)------->"+obj.getId());
				veData.add(obj);
			}
			Log.v("al", "add(NoAlertMess obj) ------------>"+obj.getId());
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
			Log.v("al", "add(NoAlertMess obj) ------------>"+e);
		}
		return;
	}

	private boolean isIn(NoAlertMess obj) {
		boolean b = false;
		try {
			if (veData.size() > 0) {
				try {
					NoAlertMess noAlertMess;
					for (int i = 0; i < veData.size(); i++) {
						noAlertMess = veData.elementAt(i);
						if (noAlertMess.isSame(obj)) {
							b = true;
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!b) {
				if (showData != null && showData.size() > 0) {
					try {
						NoAlertMess noAlertMess;
						for (int i = 0; i < showData.size(); i++) {
							noAlertMess = showData.elementAt(i);
							if (noAlertMess.isSame(obj)) {
								b = true;
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return b;
	}

	private Vector<NoAlertMess> showData = null;
	/*
	 * 30秒
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		
//		this.nextMess = true;
		setNextMess(true);
		
		while (runFlag) {
			try {
				if (isNextMess()) {
					Log.v("al", "isNextMess()");
					showData = getShowData();
					Log.v("mNotificationId", showData.size()+"");
					if (showData.size() > 0) {
						setNextMess(false);
						alertMess(showData);
					}
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
				setNextMess(true);
			}
		}
	}

	private void alertMess(Vector<NoAlertMess> showData) {
		try {
			if (StaticClass.isload) {
				NoAlertMess obj = null;
				boolean isShow  = false;
				for (int i = 0; i < showData.size(); i++) {
					obj = showData.get(i);
					switch (obj.getType()) {
					case NoAlertMess.TASK_IMPORTION:
					case NoAlertMess.TASK_ADD:
						Log.v("1101","	case NoAlertMess.TASK_IMPORTION:  alertMess(obj);");
						alertMess(obj);
						break;


				
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	private Vector<NoAlertMess> getShowData() {
		Vector<NoAlertMess> veRe = new Vector<NoAlertMess>();
		try {
			if (veData.size() > 0) {
				NoAlertMess obj = veData.remove(0);
				Log.v("al", "Vector<NoAlertMess> getShowData() --------->"+obj.getId());
				veRe.addElement(obj);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return veRe;
	}

	private void alertMess(NoAlertMess noAlertMess) {
		try {
			String alertMess = noAlertMess.getMessage();
			int type = noAlertMess.getType();
			long time = noAlertMess.getTime();
			String jobId = null;
			Object data = noAlertMess.getObj();
			if (data instanceof Vector<?>) {
				try {
//					Vector<Task> data1 = (Vector<Task>) data;
//					Task task = null;
//					if (data1.size() > 0) {
//						task = data1.get(0);
//						jobId = task.getJobId();
//					}
				} catch (Exception e) {
					MaStation.getInstance().recordExc(e);
				}
			}
			// 提示任务变化
			boolean isOpen = false;

			
			

			
			

			////广州站临时屏蔽 
			if (!isOpen) {
				Intent intent = new Intent();
				Bundle bd = new Bundle();
				bd.putString("showText", alertMess);
				bd.putInt("type", type);
				bd.putLong("time", time);
				bd.putString("msgids", noAlertMess.getId());
				if (jobId != null){
					bd.putString("jobId", jobId);
				}
				intent.putExtras(bd);
				Log.v("alarm", "alarm  ----->"+alertMess+"\n ----------------------<");
				//byxx.android.station.alert.AcAlertDialog
				intent.setAction("byxx.android.station.action.MyDialog_ACTION");
				//
//				moTask.setAlertIntent(intent);
				//
				intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				PendingIntent pi = PendingIntent.getActivity(MaStation.getInstance().context, 0, intent, 0);
				AlarmManager manager = (AlarmManager) MaStation.getInstance().context //context
						.getSystemService(Service.ALARM_SERVICE);
				if (StaticClass.isload) {
					//该方法用于设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
					manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pi);
				}
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	private void showMess(NoAlertMess noAlertMess) {
		try {
			// String id=MaStation.getInstance().getMoMessage().get
			
			String alertMess = noAlertMess.getMessage();
			int type = noAlertMess.getType();
			long time = noAlertMess.getTime();
			String jobId = null;
			Object data = noAlertMess.getObj();
			if (data instanceof Vector<?>) {
				try {
//					Vector<Task> data1 = (Vector<Task>) data;
//					Task task = null;
//					if (data1.size() > 0) {
//						task = data1.get(0);
//						jobId = task.getJobId();
//					}
				} catch (Exception e) {
					MaStation.getInstance().recordExc(e);
				}
			}
			// 提示任务变化
			boolean isOpen = false;

			
			

			//广州站临时屏蔽 
			if (!isOpen) {
				Intent intent = new Intent();
				Bundle bd = new Bundle();
				bd.putString("showText", alertMess);
				bd.putInt("type", type);
				bd.putLong("time", time);
				if (jobId != null)
					bd.putString("jobId", jobId);
				intent.putExtras(bd);
				intent.setAction("byxx.android.station.action.MyDialog_ACTION");
				//
//				moTask.setAlertIntent(intent);
				//
				intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				PendingIntent pi = PendingIntent.getActivity(MaStation
						.getInstance().context, 0, intent, 0);
				AlarmManager manager = (AlarmManager) MaStation.getInstance().context
						.getSystemService(Service.ALARM_SERVICE);
				
				
				if (StaticClass.isload) {
					manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pi);
				}
				
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	
//	private void alertNotification(NoAlertMess noAlertMess) {
//		try {
//		String alertMess = noAlertMess.getMessage();
//		int type = noAlertMess.getType();
//		long time = noAlertMess.getTime();
//		String jobId = null;
//		Object data = noAlertMess.getObj();
//		if (data instanceof Vector<?>) {
//			try {
////				Vector<Task> data1 = (Vector<Task>) data;
////				Task task = null;
////				if (data1.size() > 0) {
////					task = data1.get(0);
////					jobId = task.getJobId();
////				}
//			} catch (Exception e) {
//				MaStation.getInstance().recordExc(e);
//			}
//		}
//		// 提示任务变化
////		boolean isOpen = false;
//		
//
//
//		
////		if (!isOpen) 
//		
////		MyWindowManager.TaskChangeNum +=1;
//		Log.v("mNotificationId", "before: " + getNoticationId());
//		if(mNotification != null) {
//			mNotificationManager.cancel(getNoticationId());
//			addNoticationId();
//		}
//		Log.v("mNotificationId", "after: " + getNoticationId());
//		Intent intent = new Intent();
//		intent.setAction("byxx.android.station.action.TASK_CHANGE_ACTION");
//		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//		PendingIntent pi = PendingIntent.getActivity(MaStation.getInstance().context, getNoticationId(), intent, 0);
//		
////		int icon = R.drawable.history_orange;  
//        CharSequence tickerText = analysisType(type);  
//        long when = System.currentTimeMillis(); 
//        
//        mNotification = new Notification(icon, tickerText, when);
//        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
//        mNotification.contentView = null;
//        mNotification.defaults =Notification.DEFAULT_VIBRATE;
//        String as = (alertMess.length() >= 50 ?alertMess.substring(0, 50):alertMess.toString());
//        mNotification.setLatestEventInfo(MaStation.getInstance().context, "任务变更" + id++, as, pi);  
//
////        MoTask.checkChangeNext = true;
//		setNextMess(true);
//		
//        mNotificationManager = (NotificationManager) MaStation.getInstance().context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(getNoticationId(), mNotification);
//		} catch (Exception e) {
//		MaStation.getInstance().recordExc(e);
//	}
//
//	}
	
	

	
	/**
	 * 解析type
	 * @param type
	 * @return
	 */
	public String analysisType(int type){
		String s  = null;
		switch (type) {
		case NoAlertMess.TASK_CHANGE:
			s = "任务变更";
			break;
		case NoAlertMess.TASK_PRVE_START_TRAIN:
			s = "前方站列车发车";
			break;
		case NoAlertMess.TASK_CHANGE_START_TRAIN:
			s = "前方站列車出發,任务变更";
			break;
		case NoAlertMess.TASK_IMPORTION:
			s = "重点事项";
			break;
		case NoAlertMess.TASK_ADD:
			s = "任务添加";
			break;
		default:
			s = "其他";
			break;
		}
		return s;
	}
	
	
//	public MoTask getMoTask() {
//		return moTask;
//	}
//
//	public void setMoTask(MoTask moTask) {
//		this.moTask = moTask;
//	}

	private void StopSpeek() {
		try {
			if (speek != null) {
				speek.stopAlert();
				MaStation.getInstance().getMoAlert().remove(speek);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
	
	/**
	  * 添加一个notification
	  */
//	 private void addNotificaction() {
//	  NotificationManager manager = (NotificationManager) this
//	  .getSystemService(Context.NOTIFICATION_SERVICE);
//	  // 创建一个Notification
//	  Notification notification = new Notification();
//	  // 设置显示在手机最上边的状态栏的图标
//	  notification.icon = R.drawable.excel;
//	  // 当当前的notification被放到状态栏上的时候，提示内容
//	  notification.tickerText = "注意了，我被扔到状态栏了";
//	  
//	  /***
//	   * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，该Intent会被触发
//	   * notification.contentView:我们可以不在状态栏放图标而是放一个view
//	   * notification.deleteIntent 当当前notification被移除时执行的intent
//	   * notification.vibrate 当手机震动时，震动周期设置
//	   */
//	  // 添加声音提示
//	  notification.defaults=Notification.DEFAULT_SOUND;
//	  // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
//	  notification.audioStreamType= android.media.AudioManager.ADJUST_LOWER;
//	  
//	  //下边的两个方式可以添加音乐
//	  //notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
//	  //notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
//	  Intent intent = new Intent(this, Notification2Activity.class);
//	  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//	  // 点击状态栏的图标出现的提示信息设置
//	  notification.setLatestEventInfo(this, "内容提示：", "我就是一个测试文件", pendingIntent);
//	  manager.notify(1, notification);
//	  
//	 }
	
//	public void clearNotifications() {
//		if(mNotification != null) {
//			mNotificationManager = (NotificationManager) MaStation.getInstance().viewFirst.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
//		   	mNotificationManager.cancelAll();
//		}
//	}
	
}
