package byxx.android.huochang.jf;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.MyExecutorService;

public class RuTrainInfo implements Runnable{
	public static final  int REAL_ALL_TRAININFO = 0x111a;
	private Thread thread = null;
	private String  GDM ;
	private boolean runFlag = true;
	private Handler handler = null;
	private int type = 0;

	private String id  = null;
	private String remark = null;
	

	public RuTrainInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public RuTrainInfo(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	



	public String getGDM() {
		return GDM;
	}

	public void setGDM(String gDM) {
		GDM = gDM;
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
//			boolean baccept = false;
//			boolean bdeal = false;
			boolean notnull = false;
			if (type == REAL_ALL_TRAININFO) {
				Log.v("runTF", "all");
				MaStation.getInstance().getMoTrainInfo().trainInfo = MaStation.getInstance().getMoTrainInfo().getTrainInfo(getGDM());
				if(MaStation.getInstance().getMoTrainInfo().trainInfo != null && !MaStation.getInstance().getMoTrainInfo().trainInfo.isEmpty()){
					notnull = true;
				}else {
					notnull = false;
				}
			}

			// 通知
			if (handler != null) {
				Message tMessage = Message.obtain();
				if (type == REAL_ALL_TRAININFO) {
					tMessage.what = Constant.MSG_ID_READ_TRAININFO;
					Bundle tBundle = new Bundle();
					tBundle.putBoolean("realalltraininfo", notnull);
					tMessage.setData(tBundle);
				}
				handler.sendMessage(tMessage);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
}
