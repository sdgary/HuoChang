package byxx.android.huochang.load;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import android.os.Handler;
import android.os.Message;


/**
 * 登录后的初始化
 * 
 * @author zhangyiyu
 * 
 */
public class RuLoad implements Runnable {

	private Thread thread = null;
	private Handler handler = null;

	public RuLoad(Handler handler) {
		this.handler = handler;
	}

	public void start() {

		if (thread == null || thread.isInterrupted()) {
			thread = new Thread(this, this.getClass().getName());
			thread.start();
//			MyExecutorService.getInstance().addThread(thread);
		}
	}

	public void close() {
		try {
			if (thread != null && thread.isAlive()) {
				thread.interrupt();
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		thread = null;
	}

	public void run() {
		try {
			deal();
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	private void deal() {
		try {
			// 读取
			MaStation.getInstance().loadLogin();
			// 通知
			if (handler != null) {
				Message tMessage = Message.obtain();
				tMessage.what = Constant.MSG_ID_LOAD;
				handler.sendMessage(tMessage);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
}
