package byxx.android.huochang.function;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.StaticClass;
import byxx.android.huochang.SuperApplication;
import byxx.android.huochang.checkBox.CheckBoxActivity;
import byxx.android.huochang.dw.AcNewDw;
import byxx.android.huochang.gdcarcheck.AcGdCarCheck;
import byxx.android.huochang.guardtour.AcGuardTour;
import byxx.android.huochang.jf.AcDelivery2;
import byxx.android.huochang.message.AcMessage;
import byxx.android.huochang.movebox.MoveBoxDetailListActivity;
import byxx.android.huochang.picture.AcPicture;
import byxx.android.huochang.recorder.AcRecorder;
import byxx.android.huochang.scanner.BarCodeActivity;
import byxx.android.huochang.stnsh.AcSHsmp;
import byxx.android.huochang.storemanager.AcStoreManager;
import byxx.android.huochang.storemanager.WhGoodsDTO;
import byxx.android.huochang.task.AcZxTask;
import byxx.android.huochang.traininfo.AcNewTrainInfo;
import byxx.android.huochang.traininfo.AcTrainInfo;
import byxx.android.huochang.update.AcUpdate;
import byxx.android.huochang.updatebox.UpdateBoxActivity;
import byxx.android.huochang.user.User;
import byxx.android.huochang.util.ByDialog;
import byxx.android.wj.http.cache.HttpCacheService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//
public class AcFunction extends BaseActivity implements OnPageChangeListener{
	private ViewPager vp;
    private List<View> views;
    private ImageView[] dots;
    private int currentIndex;
    private final int pageCount = 2;
    private Context context;
    /** Called when the activity is first created. */
    
	private Handler handler = null;
	
//	public IncomingCallReceiver callReceiver;
	
	private EditText etWork = null;

	private static final int MAX_SERVICE_NUM = 30; // 后台服务最大标志数
	private static final String LinphoneClassName = "org.linphone.LinphoneService";
	String[] smod = {"取送计划","装卸作业","线路查询","货物扫码","巡查防溜","移箱计划","仓库管理","消息通知","录音","修改箱位", "验箱拍照"} ;

	int[] test_usemod = {1,2,3,4,5,6,7,8,9,10,11};
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		try {
			this.context = this;
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.function);
			Intent errIntent=getIntent();
			this.context = this;
//			setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.ic_launcher);
			Intent intent = new Intent(this,HttpCacheService.class);
			getContext().startService(intent);
			if(MaStation.getInstance().getUser() == null){
				try{
				String s = MaStation.getString4SP(context, User.SP_NAME,User.SP_NAME);
				GsonBuilder gb = new GsonBuilder();
				gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
				Gson gson = gb.create();
				if(s != null){
					MaStation.getInstance().setUser(gson.fromJson(s, User.class));
				}
				}catch(Exception e){
					
				}
			}	
		/*
		 * ViewPager 滑动
		 * 	
		 */
		initDots();
		//test
		int fun[] = MaStation.getInstance().getUser().getFunctions();
		int newfun[] = new int[fun.length];

		System.arraycopy(fun, 0, newfun, 0, fun.length);
//		newfun[newfun.length-1] = 9;
//		InitViewPager((test_usemod ));
        InitViewPager((MaStation.getInstance().isTest() != true? newfun:test_usemod ));
        
        
        /////////////////////////////////////////////////////////
        StaticClass.isload = true;
		//
        if(MaStation.getInstance().isTestUrl()){
        	setTitle("大朗货场综合指挥系统"+(MaStation.getInstance().isTestUrl()?" *测试00000*":""));
        }else{
    		setTitle("大朗货场综合指挥系统"+(MaStation.getInstance().isTest()?" *落地测试00000*":""));
        }

		setTitleColor(Color.BLACK);
		showVesionMess() ;

		//
		User tUser = MaStation.getInstance().getUser();
		TextView tView = null;
		tView = (TextView) findViewById(R.id.tVShowUser);
		tView.setGravity(Gravity.CENTER);
		String tStr = "";
		if (tUser != null) {
			tStr = "姓名:" + tUser == null ? "" : tUser.getName();
			tStr = tStr + "(" + tUser.getCode() + ")" + " 班别:"
					+ tUser.getTeams();
		} else {
			tStr = "姓名: 班别:";
		}
		tView.setTextSize(Constant.BODY_FONT_SIZE + 10);
		tView.setText(tStr);
		tView = (TextView) findViewById(R.id.tVFunctionsRegions);
		tView.setGravity(Gravity.CENTER);
		tStr = "";
		if (tUser != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String tWorkday = sdf.format(new Timestamp(System
					.currentTimeMillis()));
			boolean eq = tWorkday.equals(tUser.getWorkDay());
			tStr = "日期:"
					+ (tUser.getWorkDay() == null ? "" : tUser.getWorkDay())
					+ (eq ? "" : "-" + tWorkday);
			tStr = tStr
					+ " 地点:"
					+ (tUser.getCurRegion() == null ? "" : tUser
							.getCurRegion());
		} else {
			tStr = "日期: 地点:";
		}
		tView.setTextSize(Constant.BODY_FONT_SIZE + 6);
		tView.setText(tStr);

		Log.v("load", "ACNewFunctions oncreate ");
		} catch (Exception e) {
				Log.v("acfunction", ""+e);
		}
		
		//在功能菜单界面中启动时加载缓存服务。
		Intent intent = new Intent();
		intent.setClass(context, HttpCacheService.class);
		startService(intent);
		AppManager.getAppManager().addActivity(this);
    }
    
	@Override
	protected void onResume() {
//		MaStation.getInstance().getMoClassPlan().setShowFlag(false);
		super.onResume();
	}
    
    
    @Override
    public void finish() {
    	// TODO Auto-generated method stub


    	StaticClass.setIsload(false); 
    	super.finish();
    }
    
    @Override
	protected void onDestroy() {
		
		Log.v("load", "acNewFunction onDestroy() ");
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}



	public void setEtWork(EditText etWork) {
		this.etWork = etWork;
	}

	public EditText getEtWork() {
		if (etWork == null) {
			etWork = new EditText(this);
		}
		return etWork;
	}

    
	private void showVesionMess() {
		if (MaStation.getInstance().showVesionMess) {
			try {
				if (!AcUpdate.getVersionName(context).equals(MaStation.getInstance().getUser().getVersion())) {
					ByDialog.showMessage(this, "版本变更："
									+ MaStation.getInstance().getUser().getVersion()
									+ "\n请及时下载更新");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MaStation.getInstance().showVesionMess = false;
		}
	}

	/**
	 * 退出系统
	 */
	private void buQuit() {
		try {
			// 暂停对话框显示
//			MaStation.getInstance().showAlertFlag = false;
//			MaStation.getInstance().getMoTask().waitNfc();
			//
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("确定退出系统?");
			//
			LinearLayout linearLayout = new LinearLayout(this);
			TextView text = new TextView(this);
			text.setText("输入工号：");
			text.setTextSize(18);
			linearLayout.addView(text);
			EditText et = new EditText(this);
			setEtWork(et);
			et.setWidth(300);
			et.setInputType(InputType.TYPE_CLASS_NUMBER);
			linearLayout.addView(et);
			builder.setView(linearLayout);
			//
			builder.setCancelable(false);
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {// which=-1
							MaStation.getInstance().showAlertFlag = true;
							buFinish();
						}
					});
			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {// which=-2
							MaStation.getInstance().showAlertFlag = true;
						}
					});
			builder.create().show();
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
	
	public boolean getSystemLogout(){
			
		
		
		return false;
		
	}
	

	// 退出广州南系统
	public void buFinish() {
		try {
			
			String s1 = getEtWork().getText().toString();
//			s1 = "2003";
//			s1 = (MaStation.getInstance().getUser() == null ? s1:MaStation.getInstance().getUser().getCode());
			if (true||MaStation.getInstance().getUser() == null || MaStation.getInstance().getUser().getCode() == null) {
				StaticClass.setIsload(false);
				MaStation.getInstance().quitSystem();
				AppManager.getAppManager().AppExit(this);
//				SuperApplication.getInstance().exit();
				
				System.exit(0);//正常退出App  
				
				
			} else {
				String code = MaStation.getInstance().getUser().getCode();
				if (s1.equals(code)) {
//				
//					
//					(new RuLogout(getHandler(), RuLogout.TYPE_LOGOUT)).start();
				
				} else {
					Toast.makeText(this, "工号有误,无法退出", Toast.LENGTH_LONG).show();
				}
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	public void finish2(){

		MaStation.getInstance().quitSystem();
		SuperApplication.getInstance().exit();
		System.exit(0);//正常退出App  
	}
	
	// 删除PreferDate Linphone用户数据
	public void DelePreferDate() {
//		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//		SharedPreferences sp =getSharedPreferences("byxx.station_preferences", 0);
		SharedPreferences sp = getSharedPreferences("byxx.station.gzz_preferences", 0);
		sp.edit().clear().commit();
		android.util.Log.v("test", "在Functions中pref删除成功");

	}

	private void startAcQXC(){
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, AcNewDw.class);
		startActivity(intent);
	}
	private void startAcTask(){
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, AcZxTask.class);
//		intent.putExtra("taskflag", taskflag);
		startActivity(intent);
	}
	
	private void startAcTrainInfo(){
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, AcTrainInfo.class);
		startActivity(intent);
	}
	private void startAcNewTrainInfo(){
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, AcNewTrainInfo.class);
		startActivity(intent);
	}
	private void startAcSH(){
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, AcSHsmp.class);
		startActivity(intent);
	}
	private void startAcCapture(){
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, BarCodeActivity.class);
		WhGoodsDTO obj = new WhGoodsDTO();
		obj.setStnname("function");
		intent.putExtra("obj", obj);
		startActivity(intent);
	}
	private void startUpdateImg(){
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, CheckBoxActivity.class);
		startActivity(intent);
	}
	private void startGuardTour() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, AcGuardTour.class);
		startActivity(intent);
	}
	private void startCarCheck() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, AcGdCarCheck.class);
		startActivity(intent);
	}
	
	private void startPicture() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(AcFunction.this, AcPicture.class);
		startActivity(intent);
	}
	
	void testAn(){
		Toast.makeText(context, "该功能未授权", Toast.LENGTH_SHORT).show();
	}
	
	void startMovBox(){
		Intent intent =new Intent();
		intent.setClass(AcFunction.this, MoveBoxDetailListActivity.class);
		startActivity(intent);
	}
	
	void startRecorder(){
		Intent intent =new Intent();
		intent.setClass(AcFunction.this, AcRecorder.class);
		startActivity(intent);
	}
	void startMessage(){
//		Toast.makeText(context, "该功能未授权", Toast.LENGTH_SHORT).show();
		Intent intent =new Intent();
		intent.setClass(AcFunction.this, AcMessage.class);
		startActivity(intent);
	}
	void startJF(){
		Intent intent =new Intent();
		intent.setClass(AcFunction.this, AcDelivery2.class);
		startActivity(intent);
	}
	void startStoreManager(){
		Intent intent =new Intent();
		intent.setClass(AcFunction.this, AcStoreManager.class);
		startActivity(intent);
	}
	void startUpdateBox(){
		Intent intent =new Intent();
		intent.setClass(AcFunction.this, UpdateBoxActivity.class);
		startActivity(intent);
	}
	// 开行计划
	private void tVPlan1OnClick() {
		try {
//			Intent intent = new Intent();
//			intent.setClass(AcNewFunctions.this, AcNewClassPlan.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	private void tVPlan2OnClick() {
		try {
//			Intent intent = new Intent();
////			intent.setAction("byxx.android.station.action.PHASEPLANS_ACTION");
//			intent.setClass(AcNewFunctions.this, AcPhasePlans.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	//任务单
	private void tVPlan3OnClick() {
		try {
//			Intent intent = new Intent();
////			intent.setAction("byxx.android.station.action.TASKS_ACTION");
//            intent.setClass(AcNewFunctions.this,AcTasks.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	private void tVReprotError() {
		try {
//			Intent intent = new Intent();
//			intent.setAction("byxx.android.station.action.ReportError_ACTION");
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	
	private void toChangePwd() {
		try {
//			Intent intent = new Intent();
//			intent.setClass(AcNewFunctions.this, AcChangPwd.class);
//			startActivity(intent);
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	
	
	private void tVTalk() {
		try {
//			Intent intent = new Intent();
//			intent.setAction("byxx.android.station.action.TALK_ACTION");
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	//linphone对讲
	private void TaskLiPhone(){

		try {
//			Intent intent = new Intent(AcNewFunctions.this,LinphoneLauncherActivity.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	//呼叫控制室
	private void OneCall(){
		try {
//			OnKeyCallStatus.isOneCall = true;
//			if(OnKeyCallStatus.isOneCall){
//				try {
//					if (!LinphoneManager.getInstance().acceptCallIfIncomingPending()) {
//					
//						LinphoneManager.getInstance()
//							.newOneKeyOutgoingCall(OnKeyCallStatus.getCallNum());
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//				}
//			Intent intentlinphone = new Intent(AcNewFunctions.this,LinphoneLauncherActivity.class);
//			startActivity(intentlinphone);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	private void tVQuery() {
		try {
//			Intent intent = new Intent();
//			// intent.setAction("byxx.android.station.action.QUERY_ACTION");
//			intent.setAction("byxx.android.station.action.QUERY_HC_ACTION");
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	private void tVMessage() {
		try {
//			Intent intent = new Intent();
////			intent.setAction("byxx.android.station.action.MyMESSAGE_ACTION");
//			intent.setClass(AcNewFunctions.this, AcMessage.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	private void tVDangerReport() {
		try {
//			Intent intent = new Intent();
//			// intent.setAction("byxx.android.station.action.ReportDanger_ACTION");
//			intent.setAction("byxx.android.station.action.RISK_QUERY_ACTION");
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	private void tVTest() {
		try {
//			Intent intent = new Intent();
//			// intent.setAction("byxx.android.station.action.ReportDanger_ACTION");
//			intent.setClass(AcNewFunctions.this, AcTest.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	// 视频监控
	private void monitior() {
//		Intent i = new Intent();
//		i.setClass(AcNewFunctions.this, AcMonitor.class);
//		startActivity(i);
	}
	

	private void tVQuit() {
		try {
//			Intent intent = new Intent();
//			intent.setAction("byxx.android.station.action.QUIT_ACTION");
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	private void tVUpdate() {
		try {
//			Intent intent = new Intent();
//			intent.setAction("byxx.android.station.action.UPDATE_ACTION");
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	private void tvInfo(){
		try {
//			Intent intent = new Intent();
//			intent.setClass(AcNewFunctions.this, Actab.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	private void tvTicketOfficefault(){
		try {
//			Intent intent = new Intent();
//			intent.setClass(AcNewFunctions.this, AcTicketOffice.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	//地方交通工具时刻表
	private void tvTransitTimetable(){
		try {
//			Intent intent = new Intent();
//			intent.setClass(AcNewFunctions.this, AcTransitTimetable.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	//任务汇总
	private void tVTaskAll() {
		try {
//			Intent intent = new Intent();
//			intent.setClass(AcNewFunctions.this, AcNewTaskAll.class);
//			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	//Idcard维护
	private void maintain(){
//      Intent i = new Intent();
//      i.setClass(AcNewFunctions.this, AcImplement.class);
//      startActivity(i);
	}
	
	/**
	 * 查询rfid卡对应的位置
	 */
	private void RegionPatrol(){
//		Intent i = new Intent();
//		i.setClass(AcNewFunctions.this, AcRegionPatrol.class);
//		startActivity(i);
	}
	
	
	
	
	private void chat(){
//		Intent i = new Intent();
//		i.setClass(AcNewFunctions.this, ChatClient.class);
//		startActivity(i);
	}
	
	
	// 任务变更
	private void taskChange(){
//			Intent i = new Intent();
//			i.setClass(AcNewFunctions.this, AcTaskChange.class);
//			startActivity(i);
	}
	
	
	
	//广州站候车室
	private void wr_option(){
//		Intent i = new Intent();
//		i.setClass(AcNewFunctions.this, AcHcDepartTab.class);
//		startActivity(i);
	}
	
	// 专题广播
		private void ztBroad() {
//			Intent i = new Intent();
//			i.setClass(AcNewFunctions.this, AcZTBroad.class);
//			startActivity(i);
		}
	

	private Handler getHandler() {
		if (handler == null) {
			handler = new Handler() {
				public void handleMessage(Message msg) {
					dealHandleMessage(msg);
				}
			};
//			MaStation.getInstance().getHandlers().put(R.string.func_function_name, handler);
		}
		return handler;
	}

	private void dealHandleMessage(Message msg) {
		try {
			if (msg == null || msg.getData() == null)
				return;
			Bundle tBundle = msg.getData();
			switch (msg.what) {
			case 0xefff: // 退班
				finish();
				Intent intent = new Intent(
						"byxx.android.station.service.MASTATION");
				stopService(intent);
				MaStation.getInstance().quitSystem();
				break;
			case 0xeffe:// 退出系统
				buQuit();
				// intent = new Intent();
				// intent.setClass(this, AcStation.class);
				// startActivity(intent);
				// finish();
				break;
			case Constant.MSG_ID_NFC: // 刷卡返回
				try {
					String regionId = msg.getData().getString("regionId");
					intent = new Intent();
					Bundle bu = new Bundle();
					bu.putString("regionId", regionId);
					intent.putExtras(bu);
					intent
							.setAction("byxx.android.station.action.TASKS_ACTION");
//					intent.setClass(AcFunctions.this, AcImplement.class);
					startActivity(intent);
				} catch (Exception e) {
					MaStation.getInstance().recordExc(e);
				}
				break;
			case Constant.MSG_ID_LOGOUT:
				 boolean b = false;
				 b = msg.getData().getBoolean("result");
				 if(b || MaStation.getInstance().isTest()){
					 finish2(); 
				 }else{
					 ByDialog.showMessage(this.context, "退出系统失败，请检测网络");
				 }
				
				break;
			default:
				
				break;
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}


	public void onStart() {
		super.onStart();
//		try {
//			//
//			if (MaStation.getInstance().getUser() == null
//					|| MaStation.getInstance().getUser().getName() == null) {
//				finish();
//			}
//			MaStation.getInstance().getMoNfc().setHandlerFunc(getHandler());
//			// 故障上报页退出,自动将NFC接收页设置为任务单页
//			if (MaStation.getInstance().handlerTask != null) {
//				MaStation.getInstance().getMoNfc().setHandler(
//						MaStation.getInstance().handlerTask);
//			}
////			updateStatus(MaStation.getInstance().getMoTalk().getCallState());
////			MaStation.getInstance().getMoTalk().setInTalkState(this);
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
	}



	public Context getContext() {
		return this;
	}

	/**
	 * Updates the status box at the top of the UI with a messege of your
	 * choice.
	 * 
	 * @param status
	 *            The String to display in the status box.
	 */
	public void updateStatus(final String status) {
		try {
//			MaStation.getInstance().getMoTalk().setCallState(status);
			// Be a good citizen. Make sure UI changes fire on the UI thread.
			this.runOnUiThread(new Runnable() {
				public void run() {
//					TextView labelView = (TextView) findViewById(R.id.etState);
//					labelView.setText(status);
				}
			});
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (getHandler() != null)
				getHandler().sendEmptyMessage(0xeffe);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}



	/**
	 * 通过Service的类名来判断是否启动某个服务
	 * 
	 * @param mServiceList
	 * @param className
	 * @return
	 */
	private boolean ServiceIsStart(
			List<ActivityManager.RunningServiceInfo> mServiceList,
			String className) {

		for (int i = 0; i < mServiceList.size(); i++) {

			if (className.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 获取所有启动的服务的类名
	 * 
	 * @param mServiceList
	 * @return
	 */
	private String getServiceClassName(
			List<ActivityManager.RunningServiceInfo> mServiceList) {
		String res = "";

		for (int i = 0; i < mServiceList.size(); i++) {

			res += mServiceList.get(i).service.getClassName() + " \n";

		}
		return res;
	}
	
    

     /////////////////////////////////////////////////////////////////////////////////////////

    // 初始化底部小圆点
    private void initDots() {
        LinearLayout cousorLayout = (LinearLayout) findViewById(R.id.cousorLayout);

        dots = new ImageView[pageCount];

        for (int i = 0; i < pageCount; i++) {
            dots[i] = (ImageView) cousorLayout.getChildAt(i);
            dots[i].setEnabled(true);
            dots[i].setTag(i);
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);
    }

    private void InitViewPager(int[] mod) {
		vp = (ViewPager) findViewById(R.id.vPager);
		views = new ArrayList<View>();
		views.add(BtnPage(getOnePageItems(mod),clickOneFuncItem()));
		views.add(BtnPage(getTwoPageItems(), clickTwoFuncItem()));
		vp.setAdapter(new FunctionAdapter(views));
		vp.setCurrentItem(0);
		vp.setOnPageChangeListener(this);
	}
    
    
	private View BtnPage(ArrayList<HashMap<String, Object>> _getPageItems,OnItemClickListener _clickFuncItem){
		LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
	    View layout = inflater.inflate(R.layout.btn_page,null);
		GridView gridview = (GridView) layout.findViewById(R.id.BtnPage);
		
		SimpleAdapter saMenuItem = new SimpleAdapter(this, _getPageItems,R.layout.btn_item, new String[] { "ItemImage", "ItemText" },
				new int[] { R.id.ItemImage, R.id.ItemText });
		
		gridview.setAdapter(saMenuItem);
		gridview.setOnTouchListener(forbidenScroll());
		gridview.setOnItemClickListener(_clickFuncItem);
		
		return gridview;
	}
    
    HashMap<String,Integer> hmod ;
    ArrayList<String> amod ;
    private ArrayList<HashMap<String, Object>> getOnePageItems(int[] mod){
		ArrayList<HashMap<String, Object>> memuList = new ArrayList<HashMap<String, Object>>();
		hmod = new HashMap<String,Integer>();
		amod = new ArrayList<String>();
//		String[] smod = {"取送计划","装卸作业","线路查询","货物扫码","巡查防溜","移箱计划","仓库管理","消息通知","录音"} ;
//
//		int[] test_usemod = {1,2,3,4,5,6,7,8,9};
		hmod.put("取送计划",1);
		hmod.put("装卸作业",2);
		hmod.put("线路查询",3);
		hmod.put("货物扫码",4);
		hmod.put("巡查防溜",5);
		hmod.put("移箱计划",6);
		hmod.put("仓库管理",7);
		hmod.put("消息通知",8);
		hmod.put("录音",9);
		hmod.put("修改箱位",10);
		hmod.put("验箱拍照",11);

		
		for(int i:mod){
			
				switch (hmod.get(smod[i-1])) {
				case 1:
					memuList.add(newMenuItem("取送计划", R.drawable.plan1));
					break;
				case 2:
					memuList.add(newMenuItem("装卸作业", R.drawable.zhc));
					break;
				case 3:
					memuList.add(newMenuItem("线路查询", R.drawable.plan2));
					break;
				case 4:
					memuList.add(newMenuItem("货物扫码", R.drawable.scanner));
					break;
				case 5:
					memuList.add(newMenuItem("巡查防溜", R.drawable.retask));
					break;
				case 6:
					memuList.add(newMenuItem("移箱计划", R.drawable.movebox));
					break;
				case 7:
					memuList.add(newMenuItem("仓库管理", R.drawable.storemanager));
					break;
				case 8:
					memuList.add(newMenuItem("消息通知", R.drawable.imessage));
					break;
				case 9:
					memuList.add(newMenuItem("录音", R.drawable.record)); 
					break;
				case 10:
					memuList.add(newMenuItem("修改箱位", R.drawable.movebox));
					break;
				case 11:
					memuList.add(newMenuItem("验箱拍照", R.drawable.uploadimg));
					break;
				default:
				
					break;
				
				}

				amod.add(smod[i-1]);

		}
//		memuList.add(newMenuItem("取送计划", R.drawable.plan1));
//		memuList.add(newMenuItem("装卸火车", R.drawable.zhc));
//		memuList.add(newMenuItem("装卸汽车", R.drawable.zqc));
//		memuList.add(newMenuItem("线路查询", R.drawable.plan2));
//		
//		memuList.add(newMenuItem("收货", R.drawable.plan3));
//		memuList.add(newMenuItem("巡岗", R.drawable.retask));
//		memuList.add(newMenuItem("整车外交付", R.drawable.retask));
//
//		
//		memuList.add(newMenuItem("通知", R.drawable.imessage));
//		memuList.add(newMenuItem("录音", R.drawable.monitor1)); 
		
		
		
//		memuList.add(newMenuItem("故障上报", R.drawable.report));
//		memuList.add(newMenuItem("内交付", R.drawable.message1));
//		memuList.add(newMenuItem("外交付", R.drawable.message1));
//		memuList.add(newMenuItem("综合查询", R.drawable.query));
//		memuList.add(newMenuItem("进度跟踪", R.drawable.query));
		
//		memuList.add(newMenuItem("故障上报", R.drawable.message1));
//		memuList.add(newMenuItem("故障上报", R.drawable.message1));

		return memuList;
	}
    
    private ArrayList<HashMap<String, Object>> getTwoPageItems(){
		ArrayList<HashMap<String, Object>> memuList = new ArrayList<HashMap<String, Object>>();
		//0
 
//		memuList.add(newMenuItem("测试9", R.drawable.query));
		//1
//		memuList.add(newMenuItem("测试10", R.drawable.ticket)); 
		//2
//		memuList.add(newMenuItem("测试11", R.drawable.timetable)); 
		
		//3
//		memuList.add(newMenuItem("测试12", R.drawable.callzks)); 
		//4
//		memuList.add(newMenuItem("测试13", R.drawable.updateinfo));
		//5
		memuList.add(newMenuItem("版本检测", R.drawable.update)); 

		//6

//		memuList.add(newMenuItem("测试14", R.drawable.report));
		//7

//		memuList.add(newMenuItem("测试15", R.drawable.risk));
		
		//8

//		memuList.add(newMenuItem("测试16", R.drawable.report));
		
		return memuList;
	}
    
    
    private HashMap<String, Object> newMenuItem(String text, int imgId){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", imgId);
		map.put("ItemText", text);
		return map;
    }
    
    // 防止GridView自己滑动
    private OnTouchListener forbidenScroll(){
    	return (new OnTouchListener(){
//		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_MOVE){
		            return true;
		        }
		        return false;
		    }

		});
    }
    
    // 按钮的点击事件处理
    private OnItemClickListener clickOneFuncItem(){
    	return (new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				//Intent intent = null;
				if(amod.get(arg2) != null){
				switch (hmod.get(amod.get(arg2))) {
				case 1:
					startAcQXC();
					break;
				
				case 2:
					startAcTask();
					break;
				case 3:
					startAcNewTrainInfo();
					break;
				case 4:
//					startAcSH();
					startAcCapture();
					break;
				case 5:
//					startGuardTour();
					startCarCheck();
					break;
				case 6:
//				    startJF();
					startMovBox();
				 
					break;
				case 7:
					startStoreManager();
				
					break;
				case 8:
					// 视频监控
//					monitior();
					startMessage();
					
					break;
				case 9:

//					monitior();
					startRecorder();
					break;
				case 10:

//					monitior();
					startUpdateBox();
					break;
				case 11:
					startUpdateImg();
//					testAn();
					break;
				case 12:
					// 视频监控
//					monitior();
					testAn();
					break;
				default:
					break;
				}
			}
			}


		});
    }
    
    // 按钮的点击事件处理
    private OnItemClickListener clickTwoFuncItem(){
    	return (new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				//Intent intent = null;
				switch (arg2) {
				case 0:
					
					AcUpdate();
					//换乘查询
					//tVQuery();
//					ztBroad(); 
					break;
				case 1:
					//售票机故障
					//tvTicketOfficefault();
//					Toast.makeText(context, "岗位不匹配", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					//地方交通工具时刻表
//					tvTransitTimetable();
					break;
				case 3:
					//呼叫总控室
//					OneCall();
					break;
				case 4:
					//信息上报
//					tvInfo();
					break;
				case 5:
					//版本检测
//					tVUpdate();
					break;
				case 6:

					//故障上报
//					tVReprotError();
					break;
				case 7:

					//风险管理
//					tVDangerReport();
//					修改密码
//					toChangePwd();
					break;
				case 8:
					//IDCARD维护					
//					maintain();
//					RegionPatrol();
					break;
				case 9:
					break;
				default:
					break;
				}
			}

			private void AcUpdate() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AcFunction.this, AcUpdate.class);
				startActivity(intent);
			}
		});
    }
    
    
    /**
     *当前底部小圆点
     */
    private void setCurDot(int positon)
    {
        if (positon < 0 || positon > pageCount - 1 || currentIndex == positon) {
            return;
        }

        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = positon;
    }
    
//	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void onPageSelected(int arg0) {
		setCurDot(arg0);
		
	}
	

}
