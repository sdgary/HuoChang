package byxx.android.huochang;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.ab.db.storage.AbSqliteStorage;
import com.alibaba.fastjson.JSON;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import byxx.android.huochang.alert.MoAlert;
import byxx.android.huochang.audio.MaAudio;
//import byxx.android.huochang.alert.MoAlert;
import byxx.android.huochang.dw.MoDW;
import byxx.android.huochang.dw.MoGJH;
import byxx.android.huochang.dw.MoJhx;
import byxx.android.huochang.gdcarcheck.MoGdCarCheck;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.http.cache.CacheDAO;
import byxx.android.huochang.jf.MoDelivery;

import byxx.android.huochang.message.MoMessage;
import byxx.android.huochang.message.ReceiverDto;
import byxx.android.huochang.message.XMsgDto;
import byxx.android.huochang.nfc.MoNfc;
import byxx.android.huochang.task.GdContainerPos;
import byxx.android.huochang.task.GdmsZxzypbDTO;
import byxx.android.huochang.task.MoTask;
import byxx.android.huochang.traininfo.MoTrainInfo;
import byxx.android.huochang.user.User;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;
import byxx.android.huochang.util.LogWriter;
import byxx.android.huochang.webservice.MaWebService;

public class MaStation extends Application{
	public static String version = "BY-0.0.0.18"; // 版本
	public static String stationCode="DLQ";
	public String versionDownload = null;// 可供下载的版本
	public boolean showVesionMess = true;// 显示版本不一致信息
	public boolean showAlertFlag = true;// 显示告警框

	private ProgressDialog dialogMen = null;
	private boolean test = false;// 默认为false
	//数据库操作类
	private AbSqliteStorage mAbSqliteStorage = null;
	//定义数据库操作实现类
	private CacheDAO cacheDAO = null;

	//箱位数据
	private Hashtable<String, List<GdContainerPos> > boxPositions = new Hashtable<String, List<GdContainerPos> >();
//	private boolean testserver = false;// 默认为false
	
	private static MaStation instance;
	private User user = null;

	private boolean isConnected = true;//默认可以联网
	public boolean isConnected() {
		return isConnected;
	}

	public void setIsConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	MaWebService maWebService;
	MoGJH moGJH;
	MoJhx moJhx;
	MoTask moTask;
	MoDW moDW;
	MoNfc moNfc;
	MoAlert moAlert;
	MoMessage moMessage;
	MoDelivery moDelivery;
	MaAudio maAudio;
	MoTrainInfo moTrainInfo;
	MoGdCarCheck moGdCarCheck;

	static String DLURL = "http://10.160.2.122:7007/hyzx_ws/HyzxWebService?wsdl";
	static String DLURL2 = "http://10.167.97.14:7001/hyzx_ws/HyzxWebService?wsdl";
	static String DLURL3  ="http://10.167.93.128:7001/hyzx/pda/"; 
	static String DLURL_test  ="http://10.167.93.128:7005/hyzx_ws/HyzxWebService?wsdl";

	public Handler getHandlerCache() {
		return handlerCache;
	}

	public void setHandlerCache(Handler handlerCache) {
		this.handlerCache = handlerCache;
	}

	//	String DLURL = "http://10.167.5.81:7001/hyzx_ws/HyzxWebService?wsdl";
	public Handler handlerCache;
	public Handler handlerTask;
	public Handler handlerChooseMan = null;// 选择人员页
	public static Context context;

	//用于消息通知待选人员
	private List<ReceiverDto> menList = new ArrayList<ReceiverDto>();//候选人列表

	//用于装卸作业数据缓存
	private HashMap<Integer, GdmsZxzypbDTO> AcZxTaskData = new HashMap<Integer, GdmsZxzypbDTO>();

	public HashMap<Integer, GdmsZxzypbDTO> getAcZxTaskData() {
		if(AcZxTaskData == null){
			AcZxTaskData = new HashMap<Integer, GdmsZxzypbDTO>();
		}
		return AcZxTaskData;
	}

	public void setAcZxTaskData(HashMap<Integer, GdmsZxzypbDTO> acZxTaskData) {
		AcZxTaskData = acZxTaskData;
	}

	//用于记录并归类消息通知
	private HashMap<String, HashMap<Integer, XMsgDto>> msgMap = new HashMap<String, HashMap<Integer,XMsgDto>>();

	public HashMap<String, HashMap<Integer, XMsgDto>> getMsgMap() {
		return msgMap;
	}

	public void setMsgMap(HashMap<String, HashMap<Integer, XMsgDto>> msgMap) {
		this.msgMap = msgMap;
	}
	
	/**
	 * 将消息dto归类，使用前需判断dto是否为空
	 * @param dto
	 */
	public void addMsg(XMsgDto dto){
		HashMap<Integer, XMsgDto> msg = new HashMap<Integer, XMsgDto>();
		if(dto!=null){
			msg.put(dto.getMsgId(), dto);
		}
//		if(dto.getMsgSender().equals(getUser().getPostId())){//发送者为用户
//			if(dto.getReceivers()!= null && dto.getReceivers().length == 1){
//				msgMap.put(dto.getReceivers()[0], msg);
//			}
//		}
	}
	
	public static String getURL() {
//		 生产
		return DLURL3;
//		return DLURL_test;
		// 测试

	}
	
	public MaStation() {
		// TODO Auto-generated constructor stub

	}
	
	public static MaStation getInstance() {
		if (instance == null) {
			instance = new MaStation();
		}
		return instance;
	}
	public boolean isTest() {
		return test;
	}
	
	public void setTest(boolean istest){
		this.test = istest;
	}
	
	public static boolean isTestUrl() {
		return getURL().equals(DLURL_test);
	}
	


	public Context getContext() {
		return context;
	}


	
	public User getUser() {
//		if(user == null){
//		String s = MaStation.getString4SP(getApplicationContext(), "base", "user");
//		GsonBuilder gb = new GsonBuilder();
//		gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
//		Gson gson = gb.create();
//		this.user = gson.fromJson(s, User.class);
//		}
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void recordExc(Exception e) {
		// TODO Auto-generated method stub
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/byxxdownload/" + ByString.getTimeStr(System.currentTimeMillis(), "yyyyMMdd")+ ".log") ;
		LogWriter.writeLog(file,"DLQ","异常",e);
	}

	public void record(String tag, String message) {
		// TODO Auto-generated method stub
		Exception e = new Exception();
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/byxxdownload/" + ByString.getTimeStr(System.currentTimeMillis(), "yyyyMMdd")+ ".log") ;
		LogWriter.writeLog(file,tag,message,e);
	}
	
	/**
	 * 退出系统
	 */
	public void quitSystem() {
		try {
			AppManager.getAppManager().finishAllActivity();
			//
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		Log.v("load", "Mastation System.exit(0)");

	}

	
	public MoGJH getMoGJH(){
		if(moGJH == null){
			moGJH = new MoGJH();
		}
		return moGJH;
	}
	public MoJhx getMoJhx(){
		if(moJhx == null){
			moJhx = new MoJhx();
		}
		return moJhx;
	}

	public MoTask getMoTask(){
		if(moTask == null){
			moTask = new MoTask();
			
		}
		return moTask;
	}

	public MoAlert getMoAlert() {
		if (moAlert == null) {
			moAlert = new MoAlert();
			moAlert.init();
		}
		return moAlert;
	}
	public MoMessage getMoMessage() {
		if (moMessage == null) {
			moMessage = new MoMessage();
		}
		return moMessage;
	}
	
	public MoDelivery getMoDelivery() {
		if (moDelivery == null) {
			moDelivery = new MoDelivery();
		}
		return moDelivery;
	}
	
	public MoGdCarCheck getMoCarCheck(){
		if(moGdCarCheck == null){
			moGdCarCheck = new MoGdCarCheck();
		}
		return moGdCarCheck;
	}
	
	public MaAudio getMaAudio() {
		if (maAudio == null) {
			maAudio = new MaAudio();
		}
		return maAudio;
	}
	
	public MoTrainInfo getMoTrainInfo(){
		if(moTrainInfo == null){
			moTrainInfo = new MoTrainInfo();
		}
		return moTrainInfo;
	}
	
	public MoDW getMoDW(){
		if(moDW == null){
			moDW = new MoDW();
		}
		return moDW;
	}
	public MoNfc getMoNfc(){
		if(moNfc == null ){
			moNfc = new MoNfc();
			
		}
		return moNfc;
	}
	public MaWebService getMaWebService() {
		if(maWebService == null){
			maWebService = new MaWebService(getContext());
		}
		return maWebService;
	}

	public void setMaWebService(MaWebService maWebService) {
		this.maWebService = maWebService;
	}

//	public void login(String userName, String password, String pdaId) {
//		// TODO Auto-generated method stub
//		MaStation.getInstance().getMaWebService().login("DLQ", "h5wq", "2", pdaId);
//	}

	/**
	 * 登录处理
	 * @param userCode
	 * @param password
	 * @param pdaId
	 * @return
	 */
	public User login(String userCode, String password, String pdaId) {
		User tUser = null;
		try {
			//初始化股道箱位集合
				boxPositions = new Hashtable<String, List<GdContainerPos>>();
//				tUser = getMaWebService().login("DLQ", "h5wq", "2", pdaId);
				tUser = getMaWebService().login(MaStation.stationCode, userCode, password, pdaId);
				MaStation.getInstance().setUser(tUser);
				if(MaStation.getInstance().getUser() != null && MaStation.getInstance().getUser().getPostName()!=null){
					//初始化候选人列表
					new LoadMenList().execute();// 执行刷新线程
				}

			if(tUser!=null && tUser.getGdm()!= null){
				for (int i=0; i<tUser.getGdm().length; i++){
					new RefreshXW().execute(tUser.getGdm()[i], String.valueOf(i));//传股道码及股道码的下标。
				}
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
			e.printStackTrace();
		}
		return tUser;
	}
	
	
	public void loadLogin() {
		// TODO Auto-generated method stub

	}

	 /**
	   * 把String对象存储为SharedPreferences
	   * @param context
	   * @param s  //editor.putString(key, s);
	   * @param name //context.getSharedPreferences(name,0);
	   * @param key //editor.putString(key, s);
	   */
	  	public static void setString2SP(Context context,String s,String name,String key){
	  		SharedPreferences preferences = context.getSharedPreferences(name,0);
	  		Editor editor = preferences.edit();
	  		editor.putString(key, s);
	  		editor.commit();	
	  	}
	  	/**
	  	 * 从SharedPreferences转为String对象
	  	 * @param context
	  	 * @param name
	  	 * @param key
	  	 * @return
	  	 */
	  	public static String getString4SP(Context context,String name,String key){
	  		String s= null;
	  		SharedPreferences preferences = context.getSharedPreferences(name,0);
	  		s = preferences.getString(key, "");
	  		return s;
	  	}
	
		/**
		 * 返回MAC地址
		 * 
		 * @param context
		 * @return
		 */
		public static String getPDAMAC(Context context) {

			// MAC地址
			try {
				String currmac = "";
				WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				currmac = wifiInfo.getMacAddress();
				return currmac;
			} catch (Exception e) {
				Log.v("test", e + "");
			}
			return null;
		}

		public List<ReceiverDto> getMenList() {
			return menList;
		}

		public void setMenList(List<ReceiverDto> menList) {
			this.menList = menList;
		}

		/**
		 * 待选人员列表线程
		 * @author WGary 韦永城
		 * 2015-6-18
		 */
		  private class LoadMenList extends AsyncTask<String, Void, String> {
				@Override
				protected String doInBackground(String... params) {
					return MaStation.getInstance().getMaWebService()
							.getReceivers();
				}
				
				@Override
				protected void onPreExecute() {

				}
				
				@Override
				protected void onPostExecute(String result) {
					try{
						if(result != null && !result.equals("[]") && !"".equals(result)){


//						GdmsZxzypbDTO obj;
							StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
//						if(info != null && !info.equals("[]") && info.getData() != null){
							if(info != null && info.getData() != null && info.isSuccess()){
								setMenList(JSON.parseArray(info.getData().toString(), ReceiverDto.class));
							}else if("cache".equals(result)){
								ByDialog.showMessage(context, "网络断开，操作已缓存！");
							}else{
								ByDialog.showMessage(context, info.getError());
							}
						}else{
							ByDialog.showMessage(context, "网络信号不好！");
						}
					}catch (Exception e){
						MaStation.getInstance().recordExc(e);
					}
				}
		}// end AsyncTask

	//调整箱位数据加载异步线程
	private class RefreshXW extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			System.out.println(params[0]+"@"+params[1]);
			return MaStation.getInstance().getMaWebService().getContainerPos(params[0])+"@"+params[1];
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected void onPostExecute(String result) {
			try{
				if(result == null || result.length() <=0 || "".equals(result) ){
					ByDialog.showMessage(context, "网络信号不好！");
				}else if("cache".equals(result)){
					ByDialog.showMessage(context, "网络断开，操作已缓存！");
				}else{
					String [] res = result.split("@");
					int index = Integer.parseInt(res[1]);
					if(res[0] != null && !res[0].equals("")){
						List<GdContainerPos> nList = null;
						StandReturnInfo info = JSON.parseObject(res[0].trim(), StandReturnInfo.class);
						if(info!=null && info.isSuccess() && info.getData() != null){//成功且有数据
							nList = JSON.parseArray(info.getData().toString(), GdContainerPos.class);
							if(nList != null && nList.size()>0){
								boxPositions.put(getUser().getGdm()[index], nList);
							}
						}else if(info!=null && info.isSuccess() && info.getData() == null){
							ByDialog.showMessage(context, "箱位数据为空！");
//					Toast.makeText(context,"箱位数据为空！",Toast.LENGTH_SHORT).show();
						}else {
							ByDialog.showMessage(context,info.getError());
//					Toast.makeText(context,info.getError(),Toast.LENGTH_LONG).show();
						}
					}else if("cache".equals(res[0])){
						ByDialog.showMessage(context, "网络断开，操作已缓存！");
					}else{
						ByDialog.showMessage(context, "网络信号不好！");
					}
				}
			} catch (Exception e){
				MaStation.getInstance().recordExc(e);
			}

		}
	}

	/**
	 * 取得箱位信息集合
	 * @return
	 */
	public Hashtable<String, List<GdContainerPos>> getBoxPositions() {
		if(boxPositions == null){
			boxPositions = new Hashtable<String, List<GdContainerPos> >();
		}
		return boxPositions;
	}

	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
}
