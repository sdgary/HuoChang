package byxx.android.huochang.alert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.MyExecutorService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class RuBlankOut implements Runnable {

//	private int position;
	private RuTaskRefresh ruTaskRefresh;
	private Handler handler;
	private Thread thread;
	private HashMap<Integer, Boolean> isSelected;
	
	public RuBlankOut(Handler _handler, RuTaskRefresh 
			_ruTaskRefresh, HashMap<Integer, Boolean> _isSelected) {
		// TODO Auto-generated constructor stub
		this.handler = _handler;
		this.ruTaskRefresh = _ruTaskRefresh;
//		this.position = _position;
		this.isSelected = _isSelected;
	}
	
	public void close() {
		try {
			if (thread != null && thread.isAlive()) {
				thread.interrupt();
			}
		} 
		catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		thread = null;
	}
	
	public void start() {
		if (thread == null || thread.isInterrupted()) {
			thread = new Thread(this, this.getClass().getName());
//			thread.start();
			MyExecutorService.getInstance().addThread(thread);
		}
	}
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			Message msg = new Message();
//			Bundle data = new Bundle();
			String s = "";
			String ns = "";
//			// 单个的时候
//			if(isSelected.isEmpty()) {
//				ns = ruTaskRefresh.getJobMsgList().get(position).getMsgId();
//			}
//			// 多个的时候
//			else {
			if(!isSelected.isEmpty()) {
				Iterator<Entry<Integer, Boolean>> it = isSelected.entrySet().iterator();
				Entry<Integer, Boolean> entry;
				while(it.hasNext()) {
					entry = it.next();
					// 如果value为true则表示被选中
					if(entry.getValue().booleanValue()) {
						Log.v("BlankOut", "key: " + entry.getKey());
						Log.v("BlankOut", "Value: " + entry.getValue());
//						s = s + ruTaskRefresh.getJobMsgList().get(entry.getKey().intValue()).getMsgId() + ",";
					}
				}
//				Log.v("BlankOut", "s: " + s);
				// 去除最后一个逗号","
				if(!s.isEmpty()) {
					if(s.endsWith(",")){
						ns = s.substring(0, s.length() - 1);
					}
//					Log.v("BlankOut", "ns: " + ns);
				}else{
					ns = "";
				}
			}

//			boolean b = MaStation.getInstance().blankOutChangeMsg(ns);
//			if(b) {
//				msg.what = Constant.BLANKOUT_TRUE;
//			}
//			else{
//				msg.what = Constant.BLANKOUT_FALSE;
//
//			}
			handler.sendMessage(msg);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			MaStation.getInstance().recordExc(e);
		}
	}

}
