package byxx.android.huochang.alert;

import byxx.android.huochang.MaStation;
import byxx.android.huochang.StaticClass;
import byxx.android.huochang.audio.MaAudio;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;


public class ThSpeek extends Thread {
	boolean runflag = false;

	public boolean isRunflag() {
		return runflag;
	}

	public void setRunflag(boolean runflag) {
		this.runflag = runflag;
	}

	String soundMess = null;

	int type = 0;

	Context context;

	String jobId;

	public ThSpeek(String soundMess, int type, Context context, String jobId) {
		this.soundMess = soundMess;
		this.type = type;
		this.context = context;
		this.jobId = jobId;
	}

	public void run() {
		runflag = true;
		while (runflag) {
			try {
				// MaStation.getInstance().getTtsService().speak(soundMess,
				// TextToSpeech.QUEUE_FLUSH);
				if (StaticClass.isload) {
					boolean bNext = true;
					int soundId = MaAudio.ID_ALERT;
					switch (type) {
					case NoAlertMess.TASK_CHANGE:
					case NoAlertMess.TASK_ADD:
						soundId = MaAudio.ID_TASK_CHANGE;
						break;
					case NoAlertMess.TASK_IMPORTION:
					 soundId = MaAudio.ID_ALERT;
					 	break;
					case NoAlertMess.TASK_PRVE_START_TRAIN:
						soundId = MaAudio.ID_TASK_PRVE_START_TRAIN;
						break;
					case NoAlertMess.TASK_CHANGE_START_TRAIN:
						soundId = MaAudio.ID_TASK_CHANGE_START_TRAIN;
						break;
					case NoAlertMess.TASK_STOP_TICKET:
						soundId = MaAudio.ID_TASK_STOP_TICKET;
						break;
					case NoAlertMess.TASK_TRAIN_FINISH:
						soundId = MaAudio.ID_TASK_TRAIN_FINISH;
						break;
					case NoAlertMess.TASK_READY:
						soundId = MaAudio.ID_TASK_PREPARE;
//						if (jobId != null) {
//							Task task = MaStation.getInstance().getMoTask()
//									.getTaskByJobId(jobId);
//							if (task != null) {
//								bNext = MaStation.getInstance().getMoTask()
//										.isNotifyReady(task);
//							}
//						}
						break;
					case NoAlertMess.TASK_START:
						soundId = MaAudio.ID_TASK_START;
//						if (jobId != null) {
//							Task task = MaStation.getInstance().getMoTask()
//									.getTaskByJobId(jobId);
//							if (task != null) {
//								bNext = MaStation.getInstance().getMoTask()
//										.isNotifyStart(task);
//							}
//						}
						break;
					case NoAlertMess.TASK_END:
						soundId = MaAudio.ID_TASK_END_AND_NEXT;
						break;
					case NoAlertMess.TASK_ALLOW_OPEN:
						soundId = MaAudio.ID_TASK_ALLOW_OPEN;
						break;
					default:
						break;
					}
					//
					if (!bNext)
						break;
					//
					MaStation.getInstance().getMaAudio().play(soundId);
					//
					try {
						String ns = AcAlertDialog.NOTIFICATION_SERVICE;
						NotificationManager mNotificationManager = (NotificationManager) context
								.getSystemService(ns);
						Notification notification = new Notification();
						notification.when = System.currentTimeMillis();
						notification.defaults = Notification.DEFAULT_VIBRATE
								| Notification.FLAG_ONLY_ALERT_ONCE;
						mNotificationManager.notify(9999, notification);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//
				}
				for (int i = 0; i < 15; i++) {
					Thread.sleep(1000);
					if (!runflag) {
						break;
					}
				}
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
		}
	}

	public void stopAlert() {
		setRunflag(false);
	}
}
