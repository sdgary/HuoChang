package byxx.android.huochang.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.MyExecutorService;

public class RuTask implements Runnable{
	public static final  int REAL_ALL_TASK_TRAIN = 0x111a;
	public static final  int REAL_ALL_TASK_TRUCK= 0x111b;
	public static final  int SENT_TASK_START = 0x112a;
	public static final  int SENT_TASK_OVER = 0x113a;
	public static final  int SENT_TASK_OVER_HW = 0x113b;

	
	private Thread thread = null;
	private String  GDM ;
	private boolean runFlag = true;
	private Handler handler = null;
	private int type = 0;
	private ZydDto task;
	private String id  = null;
	private String remark = null;
	

	public RuTask() {
		// TODO Auto-generated constructor stub
	}
	
	public RuTask(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	



	public ZydDto getTask() {
		return task;
	}

	public void setTask(ZydDto task) {
		this.task = task;
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
			String websreturn = null;
			switch (type) {
			case REAL_ALL_TASK_TRAIN:
				Log.v("runTF", "all");
				MaStation.getInstance().getMoTask().HzydDtos = MaStation.getInstance().getMoTask().getTaskH();// MaStation.getInstance().ge);
				if(MaStation.getInstance().getMoTask().HzydDtos != null && !MaStation.getInstance().getMoTask().HzydDtos.isEmpty()){
					notnull = true;
				}else {
					notnull = false;
				}
				break;
			case REAL_ALL_TASK_TRUCK:
				Log.v("runTF", "all");
				MaStation.getInstance().getMoTask().QzydDtos = MaStation.getInstance().getMoTask().getTaskQ();// MaStation.getInstance().ge);
				if(MaStation.getInstance().getMoTask().QzydDtos != null && !MaStation.getInstance().getMoTask().QzydDtos.isEmpty()){
					notnull = true;
				}else {
					notnull = false;
				}
				break;
			case SENT_TASK_START:
				websreturn = MaStation.getInstance().getMoTask().reportZydStart(getTask());
				break;
			case SENT_TASK_OVER:
				websreturn = MaStation.getInstance().getMoTask().reportZydOver(getTask());
				break;
			case SENT_TASK_OVER_HW:
				websreturn = MaStation.getInstance().getMoTask().reportZydOver(getTask());
				break;
			default:
				break;
			}

			
			// 通知
			if (handler != null) {
				Message tMessage = Message.obtain();
				Bundle tBundle = new Bundle();
				switch (type) {
				case REAL_ALL_TASK_TRAIN:
					tMessage.what = Constant.MSG_ID_READ_TASK_TRAIN;
					tBundle.putBoolean("realalltask", notnull);
					tMessage.setData(tBundle);
					break;
				case REAL_ALL_TASK_TRUCK:
					tMessage.what = Constant.MSG_ID_READ_TASK_TRUCK;
					tBundle.putBoolean("realalltask", notnull);
					tMessage.setData(tBundle);
					break;
				case SENT_TASK_START:
					tMessage.what = Constant.MSG_ID_SENT_TASK_START;
					tBundle.putString("ok", websreturn);
					tBundle.putInt("jobId",getTask().getZypbId());
					tMessage.setData(tBundle);
					break;
				case SENT_TASK_OVER:
					tMessage.what = Constant.MSG_ID_SENT_TASK_OVER;
					tBundle.putString("ok", websreturn);
					tBundle.putInt("jobId",getTask().getZypbId());
					tMessage.setData(tBundle);
					break;
				case SENT_TASK_OVER_HW:
					tMessage.what = Constant.MSG_ID_SENT_TASK_OVER_HW;
					tBundle.putString("ok", websreturn);
					tBundle.putInt("jobId",getTask().getZypbId());
					tMessage.setData(tBundle);
					break;
				default:
					break;
				}
				handler.sendMessage(tMessage);	
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
}
