package byxx.android.huochang.login;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class RuLogin implements Runnable {

	private Thread thread = null;
	private String userName = null;
	private String password = null;
	private String pdaId = null;
	private Handler handler = null;

	public RuLogin(Handler handler, String userName, String password,
			String pdaId) {
		this.handler = handler;
		this.userName = userName;
		this.password = password;
		this.pdaId = pdaId;
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
//			MaStation.getInstance().recordExc(e);
		}
		thread = null;
	}

	public void run() {
		try {
			deal();
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	private void deal() {
		try {
			// 读取
			MaStation.getInstance().login(userName, password, pdaId);
			// 通知
			if (handler != null) {
				Message tMessage = Message.obtain();
				tMessage.what = Constant.MSG_ID_LOGIN;
				handler.sendMessage(tMessage);
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
}
