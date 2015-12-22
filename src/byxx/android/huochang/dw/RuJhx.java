package byxx.android.huochang.dw;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.MyExecutorService;

public class RuJhx implements Runnable{
	public static final  int REAL_ALL_JHX = 0x111b;
	public static final  int SEND_X_ZK = 0x112a;
	public static final  int TAB1 = 1;
	public static final int TAB2 = 2;
	public static final int TAB3 = 3;
	private Thread thread = null;
	private boolean runFlag = true;
	private Handler handler = null;
	private int type = 0;
	JzxDTO dto;
	private String id  = null;
	private String remark = null;
	

	public RuJhx() {
		// TODO Auto-generated constructor stub
	}
	
	public RuJhx(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public JzxDTO getDto() {
		return dto;
	}

	public void setDto(JzxDTO dto) {
		this.dto = dto;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void start() {

		if (thread == null || thread.isInterrupted()) {
			thread = new Thread(this, this.getClass().getName());
//			thread.start();
			MyExecutorService.getInstance().addThread(thread);
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
		// TODO Auto-generated method stub
		try {
			deal();
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
		
	}
	
	private void deal() {
		try {
			// 读取

			boolean notnull = false;
			String websreturn = null;
			switch (type) {
			case REAL_ALL_JHX:
				Log.v("runTF", "all");
				MaStation.getInstance().getMoJhx().jzxdtos = MaStation.getInstance().getMoJhx().getJHX4web(1);
				if(MaStation.getInstance().getMoJhx().jzxdtos != null && !MaStation.getInstance().getMoJhx().jzxdtos.isEmpty()){
					notnull = true;
				}else {
					notnull = false;
				}
				break;
			case SEND_X_ZK:
				websreturn = "ok";
				break;
			}

			// 通知
			if (handler != null) {
				Message tMessage = Message.obtain();
				Bundle tBundle ;
				switch (type) {
				case REAL_ALL_JHX:
					tMessage.what = Constant.MSG_ID_READ_JHX;
					tBundle = new Bundle();
					tBundle.putBoolean("realalljhx", notnull);
					tMessage.setData(tBundle);
					break;
				case SEND_X_ZK:
					tMessage.what = Constant.MSG_ID_JZX_STATUE;
					tBundle = new Bundle();
					tBundle.putString("ok", websreturn);
					tBundle.putString("id", dto.getCh());
					tMessage.setData(tBundle);
					break;
				}

				handler.sendMessage(tMessage);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
}
