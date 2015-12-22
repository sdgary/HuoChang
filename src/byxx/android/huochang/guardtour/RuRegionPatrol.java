package byxx.android.huochang.guardtour;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class RuRegionPatrol implements Runnable{
	

//	private String filename = null;
//	private String Describe = null;
	private String idnum = null;
	private Thread thread = null;
	private boolean runFlag = true;
	private Handler handler = null;
	private Context context;
	
    public RuRegionPatrol(Handler _handler,Context _context){
    	this.handler = _handler;
    	this.context = _context;
    }
	
	
	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}
	

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public void close() {
		setRunFlag(false);
		try {
			if (thread != null && thread.isAlive()) {
				thread.interrupt();
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		thread = null;
	}
	
	public void start(String _idcardnum) {
		setRunFlag(true);
		setIdnum(_idcardnum);
		if (thread == null || thread.isInterrupted()) {
			thread = new Thread(this, this.getClass().getName());
			thread.start();
//			MyExecutorService.getInstance().addThread(thread);
		}
	}
	public void start() {
		setRunFlag(true);
		if (thread == null || thread.isInterrupted()) {
			thread = new Thread(this, this.getClass().getName());
			thread.start();
//			MyExecutorService.getInstance().addThread(thread);
		}
	}
	
	public void run() {
		// TODO Auto-generated method stub

			try {
					// 读取
					boolean notnull = false;
//						MaStation.getInstance().getMoRegionPatrol().kfcDicRegionPatrolDTOs = MoRegionPatrol.deepCopy(MaStation.getInstance().getMoRegionPatrol().getDicRegionPatrolDTOs(context));
//						Log.v("testid","MaStation.getInstance().getMoRegionPatrol().kfcDicRegionPatrolDTOs.size() " +MaStation.getInstance().getMoRegionPatrol().kfcDicRegionPatrolDTOs.size());
//						if(!MaStation.getInstance().getMoRegionPatrol().kfcDicRegionPatrolDTOs.isEmpty()){
//							notnull = true;
//						}else {
//							notnull = false;
//						}
					// 通知
					if (handler != null) {
						Message tMessage = Message.obtain();
//							tMessage.what = Constant.MSG_ID_REGIONPATROL;
							Bundle tBundle = new Bundle();
							tBundle.putBoolean("realallregionpalrol", notnull);
							tMessage.setData(tBundle);
						handler.sendMessage(tMessage);
					}
				} catch (Exception e) {
					MaStation.getInstance().recordExc(e);
				}


		return;
	}

	
	
       
	

		
		
}


