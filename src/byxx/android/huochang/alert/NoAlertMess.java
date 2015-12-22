package byxx.android.huochang.alert;

import byxx.android.huochang.MaStation;
import android.util.Log;

/**
 * 消息提示No.
 * @author Way
 *
 */
public class NoAlertMess {
	public static final int SHOWTYPE_ALERT = 0;// 告警方式
	public static final int SHOWTYPE_MESSAGE = 1;// 消息方式
	//
	public static final int TASK_CHANGE = 0; // 任务变更
	public static final int TASK_READY = 1; // 任务准备
	public static final int TASK_START = 2;  //作业开始
	public static final int TASK_END = 3;
	public static final int TASK_STOP_TICKET = 4;
	public static final int TASK_TRAIN_FINISH = 5;
	public static final int TASK_ALLOW_OPEN = 6;
	public static final int TASK_PRVE_START_TRAIN = 7;// 前方站列車出發
	public static final int TASK_CHANGE_START_TRAIN = 8;// 前方站列車出發,任务变更
	public static final int TASK_PIVOT_PROCEEDING = 9;// 重点任务变更
	public static final int TASK_RISK = 10;// 风险变更
	public static final int TASK_WORK=11;//任务作业
	//
	public static final int MESSAGE_CHANGE = 12;// 重点消息
	
	
	public static final int TASK_IMPORTION = 13;// 重点事项
	public static final int TASK_ADD = 41;// 任务添加
	
	
	public static final int TASK_TW = 14;// 停稳
	
	
	public static final int TASK_FKBZ = 31;
	/**
	 * //站台通知放客班长 （南北头没有具备放客条件)
	 */
	public static final int TASK_FKBZ_ZT_JBFKTJ = 32; //通知放客班长 （南北头没有具备放客条件)
	/**
	 * //贵宾室茶座收到放重点旅客提醒
	 */
	public static final int TASK_GBWYS_FKBZ_ZDLKKSFK =33; //收到放重点旅客提醒
	/**
	 *  //收到（贵宾室、母婴室）重点旅客放客完毕的通知
	 */
	public static final int TASK_GBWYS_FKBZ_ZDLKFKWB = 34;  //收到（贵宾室、母婴室）重点旅客放客完毕的通知
	/**
	 * //通知放客队放客
	 */
	public static final int TASK_FKD_FKBZ_FK = 35; //通知放客队放客
	/**
	 * 放客队开始放旅客
	 */
	public static final int TASK_ZT_FKD_KSFK = 36; 
	/**
	 * 作业状态
	 */
	public static final int TASK_ZYZT = 40;

	
	
	
	private int showType;// 显示类型
	private int type;// 告警类型
	private int typeSub;//子类型
	private String id; // 数据标识
	private long time; // 消息创建时间
	private String message; // 消息标识内容
	private Object obj;// 消息详细内容

	public int getTypeSub() {
		return typeSub;
	}

	public void setTypeSub(int typeSub) {
		Log.v("alert", "typeSub "+typeSub);
		this.typeSub = typeSub;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		Log.v("alert", "id  "+id);
		this.id = id;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		Log.v("alert", "showType "+showType);
		this.showType = showType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		Log.v("alert", "type "+type);
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		Log.v("alert", "\n------------message -------------\n"+message+"\n------------message -------------\n");
		this.message = message;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		Log.v("alert", "time "+time);
		this.time = time;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		Log.v("alert", "obj "+obj);
		this.obj = obj;
	}

	public boolean isSame(NoAlertMess noAlertMess) {
		boolean b = false;
		try {
//			if (getShowType() == SHOWTYPE_ALERT
//					&& message.equals(noAlertMess.message)) {
//				b = true;
//			} 
//			else 
				if (
//						getShowType() == SHOWTYPE_MESSAGE
//					&& 
						getId().equals(noAlertMess.getId())) {
				b = true;
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return b;
	}
}
