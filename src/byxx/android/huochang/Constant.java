package byxx.android.huochang;


public class Constant {
	public static int BODY_FONT_SIZE = 14;
	public static double CLASS_SCALE = 1.0;// 缩放比例
	public static double TASK_SCALE = 1.0;// 缩放比例

	public static final int HTTP_CACHE_SIGN = 0x0001;

	public static final int MOVEBOX_REQ = 0x1001;// 缩放比例
	public static final int MOVEBOX_RES_ON = 0X1002;// 执行
	public static final int MOVEBOX_RES_OFF = 0X1003;// 取消
	
	// 外部消息通信 0x20xx
	// public static final int COM_ID_CLASS_PLAN_SEND = 0x2001;
	// public static final int COM_ID_CLASS_PLAN_SEND_AUTO = 0x2002;
	// public static final int COM_ID_CLASS_PLAN_RECEIVE = 0x2003;
	// public static final int COM_ID_CLASS_PLAN_RECEIVE_AUTO = 0x2004;
	// 本机消息通信 0x21xx
	public static final int MSG_ID_LOGIN = 0x2101;// 登录
	public static final int MSG_ID_LOAD = 0x2102; // 初始数据
	public static final int PING = 0x2102a; // 初始数据
	
//	public static final int MSG_ID_CLASS_PLAN = 0x2103; // 开行计划
//	public static final int MSG_ID_TASK_CURR = 0x2105; // 当前任务
//	public static final int MSG_ID_TASK_NEXT = 0x2106; // 未完成任务
//	public static final int MSG_ID_TASK_DONE = 0x2107;// 已完成任务
//	public static final int MSG_ID_TASK_TMR = 0x2108;// 第二天任务
//	// 功能
//	public static final int MSG_ID_REPORT_ERR_SAVE = 0x210e; // 故障上报
//	public static final int MSG_ID_REPORT_ERR_READ = 0x210f; // 条码读取
//	public static final int MSG_ID_REPORT_C_F_D = 0x210a; //故障正常确认
//	public static final int MSG_ID_REPORT_W_D_F = 0x210b;//故障查询
//	// 查询
//	public static final int MSG_ID_PLAN_TIME = 0x2110; // 列车时刻表
//	public static final int MSG_ID_TICKET = 0x2111; // 列车时刻表
//	public static final int MSG_ID_PLAN_TIME_R = 0x2112; // 营业时刻
//	public static final int MSG_ID_FIND_TRAIN = 0X2113;// 定位车次
//	public static final int MSG_ID_PLAN_LOCAL = 0X2114;// 加载本地数据
//	public static final int MSG_ID_PLAN_FACT = 0X2115;// 加载远程数据
//	//
	public static final int MSG_ID_NFC = 0x2116; // NFC返回
	public static final int MSG_NFC_DATA = 131074;
	public static final int MSG_NFC_NULL = 131076;
	public static final int MSG_ID_LOGOUT = 0X2117;// 退班
//	public static final int MSG_ID_LOGOUT_ALLOW = 0X2118;// 退班
	public static final int MSG_ID_CHOOSE_MAN = 0X2119;// 人员选择	
	public static final int MSG_ID_SELECTED_MAN = 0X2120;// 人员选择
//	//
//	public static final int MSG_ID_TASK_CLOSE_DOOR_R = 0x2200; // 关门上报
//	public static final int MSG_ID_TASK_CLOSE_DOOR = 0x2201; // 关门
//	public static final int MSG_ID_TASK_STOP_SLEEP_R = 0x2202; // 停稳上报
//	public static final int MSG_ID_TASK_STOP_SLEEP = 0x2203; // 停稳
//	public static final int MSG_ID_TASK_STOP_CHECK_TICKET_R = 0X2204;// 停检上报
//	public static final int MSG_ID_TASK_STOP_CHECK_TICKET = 0X2205;// 停检
//	public static final int MSG_ID_TASK_SEND_TRAIN_LEAVE_R = 0X2206;// 发车上报
//	public static final int MSG_ID_TASK_SEND_TRAIN_LEAVE = 0X2207;// 发车
//	public static final int MSG_ID_TASK_STOP_TRAIN_R = 0X2208;// 意外停车上报
//	public static final int MSG_ID_TASK_STOP_TRAIN = 0X2209;// 意外停车
//	public static final int MSG_ID_TASK_WATER_FINISH_R = 0X220a;// 上水完毕上报
//	public static final int MSG_ID_TASK_WATER_FINISH = 0X220b;// 上水完毕
//	public static final int MSG_ID_TASK_XW_FINISH_R = 0X2201a; //吸污完毕上报
//	public static final int MSG_ID_TASK_XW_FINISH = 0X2201b; // 吸污完毕
//	public static final int MSG_ID_TASK_TRAIN_FINISH_R = 0X220c;// 交车完毕上报
//	public static final int MSG_ID_TASK_TRAIN_FINISH = 0X220d;// 交车完毕
//	public static final int MSG_ID_TASK_ALLOW_OPEN_R = 0X220e;// 允许放客上报
//	public static final int MSG_ID_TASK_ALLOW_OPEN = 0X220f;// 允许放客
//	public static final int MSG_ID_TASK_ALLOW_OPEN_RECEIVE_R = 0X2210;// 允许放客确认上报
//	public static final int MSG_ID_TASK_ALLOW_OPEN_RECEIVE = 0X2211;// 允许放客确认
//	public static final int MSG_ID_TASK_BEGIN_TIME_R_NFC = 0x2212; // 开始作业上报（NFC方式）
//	public static final int MSG_ID_TASK_BEGIN_TIME_R = 0x2213; // 开始作业上报（人工方式）
//	public static final int MSG_ID_TASK_BEGIN_TIME = 0x2214; // 开始
//	public static final int MSG_ID_TASK_END_TIME_R = 0x2215; // 结束任务
//	public static final int MSG_ID_TASK_END_TIME = 0x2216; // 结束任务
//	public static final int MSG_ID_TASK_CHANGE = 0x2217; // 任务变更
//	public static final int MSG_ID_TASK_MESSAGE = 0x2218; // 任务重点事项显示
//	public static final int MSG_ID_TASK_RISK = 0x2219; // 任务风险内容
//	public static final int MSG_ID_TASK_BEGIN_TIME_AUTO = 0x2220; // 开始时间自动上报
//	public static final int MSG_ID_TASK_READY_TIME_ALERT_R = 0x2221; // 准备作业确认上报
//	public static final int MSG_ID_TASK_READY_TIME_ALERT = 0x2222; // 准备作业确认上报
//	public static final int MSG_ID_TASK_START_TIME_ALERT_R = 0x2223; // 开始作业确认上报
//	public static final int MSG_ID_TASK_START_TIME_ALERT = 0x2224; // 开始作业确认上报
	
	

	
	
	
	
	
	
//	public static final int MSG_ID_TASK_COUNT = 0x22ff; //任务开始时间 保存计时 
//	//
//	public static final int MSG_ID_QUERY_HC_TRAINNAME_R = 0X2300;// 换乘列车车次
//	public static final int MSG_ID_QUERY_HC_TRAINNAME = 0X2301;// 
//	public static final int MSG_ID_QUERY_HC_TRAIN_R = 0X2302;// 换乘信息概述 
//	public static final int MSG_ID_QUERY_HC_TRAIN = 0X2303;// 
//	public static final int MSG_ID_QUERY_HC_SEAT_R = 0X2304;// 换乘席位查询 *
//	public static final int MSG_ID_QUERY_HC_SEAT = 0X2305;// 
//	public static final int MSG_ID_QUERY_HC_TRAIN_SEAT_R = 0X2306;// 换乘列车席位查询
//	public static final int MSG_ID_QUERY_HC_TRAIN_SEAT = 0X2307;// 
//	public static final int MSG_ID_QUERY_HC_TRAIN_CARRIAGE_R = 0X2308;// 换乘列车车厢查询
//	public static final int MSG_ID_QUERY_HC_TRAIN_CARRIAGE = 0X2309;// 
//	public static final int MSG_ID_QUERY_HC_SEATINFO_R = 0X230a;// 换乘车厢详情查询
//	public static final int MSG_ID_QUERY_HC_SEATINFO = 0X230b;//
//	//
//	public static final int MSG_ID_MESSAGE_SHOW = 0X2400;// 重点消息显示
//	public static final int MSG_ID_MESSAGE_COMMIT_R = 0X2401;// 签收消息
//	public static final int MSG_ID_MESSAGE_COMMIT = 0X2402;//
//	public static final int MSG_ID_MESSAGE_SEND_R = 0X2403;// 消息互发发送
//	public static final int MSG_ID_MESSAGE_SEND = 0X2404;// 
//	public static final int MSG_ID_MESSAGE_RECEIVE = 0X2405;// 消息互发接收
//	public static final int MSG_ID_MESSAGE_READ_R=0x2406;//读取消息数据
//	public static final int MSG_ID_MESSAGE_READ=0x2407;//
//	public static final int MSG_ID_MESSAGE_COUNT=0x2408;//消息统计
//	//
//	public static final int MSG_ID_DOWNLOAD_WRITE_TIME=0x2500;//时刻表下载
//	public static final int MSG_ID_DOWNLOAD_READ_TIME=0x2501;//装载本地时刻表
//	//
//	public static final int MSG_ID_TALK_END=0x2600;//对话结束
//	public static final int MSG_ID_TALK_HUNDUP=0x2601;//对话挂断
//	//
//	public static final int MSG_ID_RISK_OWNER_R = 0X2700;// 获取风险岗位
//	public static final int MSG_ID_RISK_OWNER = 0X2701;//
//	public static final int MSG_ID_RISK_MEASURE_R = 0X2702;// 获取网位风险卡内容
//	public static final int MSG_ID_RISK_MEASURE = 0X2703;//
//	
	public static final int MSG_ID_SAVEFTPFILE = 0X2903; //上报ftp信息
	public static final int MSG_ID_LOSEGOODS = 0X2904; //上报丢失物品
//	
//	public static final int MSG_ID_IMPLEMENT_IDNUM =0X2901; //实施组上报IDCARD
//	public static final int MSG_ID_REGIONPATROL =0X290a; //查询rfid卡的

	
	
	public static final int MSG_ID_READ_GJH = 0x3101a;//
	public static final int MSG_ID_READ_JHX = 0x3101b;//
	public static final int MSG_ID_JZX_STATUE_R = 0x9101a;//
	public static final int MSG_ID_JZX_STATUE = 0x9101b;//
	
	public static final int MSG_ID_READ_TRAININFO = 0x3102a;//
	
	public static final int MSG_ID_READ_TASK_TRAIN = 0X3103a;
	public static final int MSG_ID_READ_TASK_TRUCK = 0X3103b;
	public static final int MSG_ID_SENT_TASK_START_R= 0x32001a;
	public static final int MSG_ID_SENT_TASK_START= 0x32001b;
	public static final int MSG_ID_SENT_TASK_OVER_R= 0x32001c;
	public static final int MSG_ID_SENT_TASK_OVER= 0x32001d;
	public static final int MSG_ID_SENT_TASK_OVER_HW_R= 0x32002c;
	public static final int MSG_ID_SENT_TASK_OVER_HW= 0x32002d;
	
	public static final int MSG_ID_MSB_CAMERA_R = 0x32002a;
	public static final int MSG_ID_MSB_PAIBAN = 0x32003a;
	
//	public static final int MSG_ID_TICKETOFFICE_ACCEPT = 0x3101b; //自动售票机故障受理
//	public static final int MSG_ID_TICKETOFFICE_DEAL = 0x3101c; //自动售票机故障处理完毕
//	public static final int MSG_ID_TICKETOFFICE = 0x3101d;  //自动售票机故障;
//	public static final int MSG_ID_TICKETOFFICE_TEST = 0x3101e;
	
//	public static final int MSG_ID_TRASITTIMETABLE = 0x3111a ;//地方交通工具时刻表查询
//	public static final int MSG_ID_TRASITTIMETABLE_KEY = 0x3111b ;//地方交通工具时刻表关键字查询
//	
//	public static final int MSG_ID_HCS_ONE = 0x32111; //候车室查询
//	public static final int MSG_ID_HCS_ALL = 0x32112; //候车室查询		
//	
//	public final static int MSG_ID_AFFIRMONBOARDOVER_R = 0x3111c; //确认候车室旅客已放客完毕(开车前10分钟)
//	public final static int MSG_ID_AFFIRMONBOARDOVER = 0x3111d; //确认候车室旅客已放客完毕(开车前10分钟)
//	
//	public final static int MSG_ID_CHANGEPWD = 0x3111e; //修改密码
//	
//	
//	public static final int[] ClassPlanColor = new int[] { Color.CYAN,
//			Color.YELLOW, Color.BLUE, Color.GREEN };
//	public final static int  ISVIEWREFRESH = 0X983333;   //定时刷任务界面
//	
//	public static final int REFRESH_DONE = 0x40001;		// 任务变更刷新完成
//	public static final int REFRESH_FAILED = 0x40002;	// 任务变更刷新出错
//	public static final int BLANKOUT_TRUE = 0x40003;	// 任务变更确认点击信息
//	public static final int BLANKOUT_FALSE = 0x40004;	// 任务变更取消点击信息
	
	
	
	
	
	//广播
	public final static String BC_UPLOADING_UPDATEUI = "byxx.station.updateui";
    public final static String BC_UPLOADING_RESULT = "byxx.station.updateresult";
    public final static String BC_UPLOADING_UPDATEUI_LP = "byxx.station.updateui.lp";
    public final static String BC_UPLOADING_RESULT_LP = "byxx.station.updateresult.lp";
	public final static String BC_SERVICE_FILEID="byxx.station.fileid";
	public final static String BC_SERVICE_FILEID_LP="byxx.station.fileid.lp";
	
	
	
	
	//摄像头
	public final static int MSG_ID_MONITOR = 0X5100ab ;
	
	
}
