package byxx.android.huochang.audio;

import java.util.HashMap;

import byxx.android.huochang.MaStation;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;


public class MaAudio {
	public static final int ID_ALERT = 1;// 提醒
	public static final int ID_TASK_CHANGE = 2;// 2; //任务变更
	public static final int ID_TASK_END_AND_NEXT = 1;// 3; //任务冲突,结束任务
	public static final int ID_TASK_PREPARE = 1;// 4; //任务准备
	public static final int ID_TASK_START = 1;// 5; //任务开始
	public static final int ID_PHONE = 1;// 6;  //来电
	public static final int ID_TASK_STOP_TICKET = 1;// 7; //停检
	public static final int ID_TASK_TRAIN_FINISH = 1;// 8; //交接完成
	public static final int ID_TASK_ALLOW_OPEN = 1;// 9; //允许放客
	public static final int ID_TASK_PRVE_START_TRAIN = 1;// 10; //前方发车
	public static final int ID_TASK_CHANGE_START_TRAIN = 1;// 11; 任务变更,前方发车
	public static final int ID_TASK_PIVOT_PROCEEDING=1;//12; 重点事项
	public static final int ID_TASK_RISK=1;//13; 风险
	private SoundPool soundPool = null;
	private HashMap<Integer, Integer> soundMap = null;

	public AudioManager audioManager = null; //

	public void setRingMax() {
		try {
			if (audioManager != null) {
				audioManager
						.setStreamVolume(
								AudioManager.STREAM_SYSTEM,
								audioManager
										.getStreamMaxVolume(AudioManager.STREAM_SYSTEM),
								0);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public void setMusicMax() {
		try {
			if (audioManager != null) {
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
						audioManager
								.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
						0);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public void setNotificationMax() {
		try {
			if (audioManager != null) {
				audioManager
						.setStreamVolume(
								AudioManager.STREAM_NOTIFICATION,
								audioManager
										.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION),
								0);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public void setVoiceCallMax() {
		try {
			if (audioManager != null) {
				audioManager
						.setStreamVolume(
								AudioManager.STREAM_VOICE_CALL,
								audioManager
										.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
								0);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	private MediaPlayer mp = null;

	public MediaPlayer getMp() {
		if (mp == null) {
			try {
				Context ac = MaStation.getInstance().getContext();
				Uri uri = RingtoneManager.getActualDefaultRingtoneUri(ac,
						RingtoneManager.TYPE_RINGTONE);
				mp = MediaPlayer.create(ac, uri);
				mp.setLooping(true);
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
		}
		return mp;
	}

	public void setMp(MediaPlayer mp) {
		this.mp = mp;
	}

	private SoundPool getSoundPool() {
		if (soundPool == null) {
			soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		}
		return soundPool;
	}

	private HashMap<Integer, Integer> getSoundMap() {
		if (soundMap == null) {
			soundMap = new HashMap<Integer, Integer>();
		}
		return soundMap;
	}

	public void load(int id, Context context, int resId, int priority) {
		try {
			if (!getSoundMap().containsKey(id)) {
				getSoundMap().put(id,
						getSoundPool().load(context, resId, priority));
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public void play(int id) {
		try {
			getSoundPool().play(id, 1, 1, 0, 0, 1);
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public void setSpeakerphoneOn(boolean onFlag) {
		try {
			if (audioManager != null) {
				audioManager.setSpeakerphoneOn(onFlag);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
			// TODO: handle exception
		}
	}
}
