package byxx.android.huochang.dw;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.MyExecutorService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class RuGJH  implements Runnable{
	public static final  int REAL_ALL_GJH = 0x111a;
	public static final  int TAB1 = 1;
	public static final int TAB2 = 2;
	public static final int TAB3 = 3;
	private Thread thread = null;
	private String tab ;
	private boolean runFlag = true;
	private Handler handler = null;
	private int type = 0;

	private String id  = null;
	private String remark = null;
	

	public RuGJH() {
		// TODO Auto-generated constructor stub
	}
	
	public RuGJH(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
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
			if (type == REAL_ALL_GJH) {
				Log.v("runTF", "all");
				MaStation.getInstance().getMoGJH().gjhlist = MaStation.getInstance().getMoGJH().getGJH4web(getTab());
				if(MaStation.getInstance().getMoGJH().gjhlist != null && !MaStation.getInstance().getMoGJH().gjhlist.isEmpty()){
					notnull = true;
				}else {
					notnull = false;
				}
			}
//			} else if (type == ACCEPT_TICKETOFFICE_ERR) {
//				Log.v("test2", getId());
//				baccept = MaStation.getInstance().getMofault().acceptTicketOfficeFault(getId());
//			}else if (type == DEAL_TICKETOFFICE_ERR){
////				"0x714d4666348007e0"
//				bdeal = MaStation.getInstance().getMofault().dealTicketOfficeFault(getRemark(),getId());
//			
//			}
//			else if (type == TEST){
//				bdeal = true;
//			}
			// 通知
			if (handler != null) {
				Message tMessage = Message.obtain();
				if (type == REAL_ALL_GJH) {
					tMessage.what = Constant.MSG_ID_READ_GJH;
					Bundle tBundle = new Bundle();
					tBundle.putBoolean("realallgjh", notnull);
					tMessage.setData(tBundle);
				}
//				} else if (type == ACCEPT_TICKETOFFICE_ERR) {
//					tMessage.what = Constant.MSG_ID_TICKETOFFICE_ACCEPT;
//					Bundle tBundle = new Bundle();
//					tBundle.putBoolean("accept", baccept);
//					tMessage.setData(tBundle);
//				}else if(type == DEAL_TICKETOFFICE_ERR){
//					tMessage.what = Constant.MSG_ID_TICKETOFFICE_DEAL;
//					Bundle tBundle = new Bundle();
//					tBundle.putBoolean("deal", bdeal);
//					tMessage.setData(tBundle);
//				}else if(type == TEST){
//					tMessage.what = Constant.MSG_ID_TICKETOFFICE_TEST;
//					Bundle tBundle = new Bundle();
//					tBundle.putBoolean("deal", bdeal);
//					tMessage.setData(tBundle);
//				}
				handler.sendMessage(tMessage);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
			
		}
	}
}
