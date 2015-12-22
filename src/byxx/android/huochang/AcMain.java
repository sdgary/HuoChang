package byxx.android.huochang;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.load.RuLoad;
import byxx.android.huochang.update.AcUpdate;
import byxx.android.huochang.user.User;
import byxx.android.huochang.util.ByDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AcMain extends BaseActivity {
	long lastLogin = 0;
	Button btn_Login;
	Button btn_Cancle;
	ImageButton btn_Reset;
	TextView tvVersion;
	EditText etUserID;
	EditText etUserPWD;
	private Handler handler;
	static Context context;
	ProgressDialog dialog = null;
	ProgressDialog dialog1 = null;
	public final int DIALOG_LOGIN = 0;
	public final int DIALOG_LOAD = 1;

	private WifiAdmin mWifiAdmin;
	private SlidingDrawer slidingDrawer;
	private Button pingButton;
	private Button networkButton;
	private Button timeButton;
	private TextView pingInfo;
	private TextView networkInfo;
	private TextView timeInfo;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		this.context = this;
		if(!StaticClass.isload){
			btn_Login = (Button)findViewById(R.id.btnLogin);
			btn_Cancle = (Button)findViewById(R.id.btnCancel);
			btn_Reset = (ImageButton)findViewById(R.id.imgbtn_delete);
			tvVersion = (TextView)findViewById(R.id.tv_version);
			etUserID = (EditText)findViewById(R.id.eTUser);
			etUserPWD = (EditText)findViewById(R.id.eTPasswod);
//		etUserID.setText("00000");
//		etUserID.setText("1021");
//		etUserPWD.setText("2");
			MaStation.context = this.getApplicationContext();
//			try {
//				tvVersion.setText(AcUpdate.getVersionName(this)+(MaStation.isTestUrl()?"测试":""));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			btn_Login.setOnClickListener(new  OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(Math.abs(System.currentTimeMillis() - lastLogin) < 3000){
						Toast.makeText(context, "正在请求登录，请稍候！", Toast.LENGTH_SHORT).show();
					}else{
						buLogin();
					}


				}
			});
			btn_Cancle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AppManager.getAppManager().AppExit(AcMain.this);
//				finish();

				}
			});
			btn_Reset = (ImageButton)findViewById(R.id.imgbtn_delete);
			btn_Reset.setOnFocusChangeListener(new OnFocusChangeListener() {

				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					if(hasFocus){
						btn_Reset.setVisibility(View.VISIBLE);
					}else{
						btn_Reset.setVisibility(View.GONE);
					}
				}
			});
			btn_Reset.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					etUserID.setText("");
					etUserPWD.setText("");

				}
			});

			pingInfo = (TextView) findViewById(R.id.pingInfo);
			networkInfo = (TextView) findViewById(R.id.networkInfo);
			timeInfo = (TextView) findViewById(R.id.timeInfo);
			pingButton = (Button) findViewById(R.id.pingButton);
			networkButton = (Button) findViewById(R.id.networkButton);
			timeButton = (Button) findViewById(R.id.timeButton);
			mWifiAdmin = new WifiAdmin(context, getHandler());
			pingButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mWifiAdmin.newThread();
				}
			});

			networkButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					networkInfo.setText(getWifiInfo());
				}
			});

			timeButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					timeInfo.setText("当前系统时区：" + mWifiAdmin.getTimeZone()
							+ "\n" + "当前系统时间：" + mWifiAdmin.getTime());
				}

			});

			slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);

			slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {

				public void onDrawerOpened() {
					// TODO Auto-generated method stub
				}
			});

			slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {

				public void onDrawerClosed() {
					// TODO Auto-generated method stub
				}
			});

			slidingDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {

				public void onScrollStarted() {
					// TODO Auto-generated method stub
				}

				public void onScrollEnded() {
					// TODO Auto-generated method stub
				}
			});
		}else{
			nextActivity();
		}
		AppManager.getAppManager().addActivity(this);
	}

	protected void buLogin() {
		// TODO Auto-generated method stub
		if (!checkInputNull()) {
			return;
		}

		String tStr = etUserID.getText().toString().trim();
		if ("测试甲".equals(tStr) || "00000".equals(tStr)) {
			tStr = "00000";
		}
		// 启动测试数据
		MaStation.getInstance().setTest("00000".equalsIgnoreCase(tStr));
		buLogin(etUserID.getText().toString().trim(), etUserPWD.getText().toString().trim(), "");

	}

	private void buLogin(String userid,String pwd,String info){
		showDialog(0);
		lastLogin = System.currentTimeMillis();
				(new byxx.android.huochang.login.RuLogin(getHandler(), userid, pwd, info)).start();
	}

	public Handler getHandler() {
		if (handler == null) {
			handler = new Handler() {
				public void handleMessage(Message msg) {
					dealHandleMessage(msg);
				}
			};
		}
		return handler;
	}

	protected void dealHandleMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == Constant.MSG_ID_LOGIN) {
			dealLoginOver();
		} else if (msg.what == Constant.MSG_ID_LOAD) {
			dealLoadOver();
		}
		if(msg.what == Constant.PING) {
			Bundle bundle = msg.getData();
			String result = bundle.getString("pingIpAddress");
			pingInfo.setText("pingIP地址："
					+ mWifiAdmin.getPingIpAddress() + "\n"
					+ "ping状态：" + result);
			mWifiAdmin.closeThread();
		}
	}

	// 校验用户编号，密码不能为空
	private boolean checkInputNull() {
		try {
//				EditText tEditText = (EditText) findViewById(R.id.eTUser);


			String tStr = etUserID.getText().toString().trim();

			//免工号登陆
//				if(etpw.getText().toString().trim() == null || etpw.getText().toString().equals("")){
//					etpw.setText(etuser.getText().toString().trim());
//				}

			if (tStr.equals("")) {
				ByDialog.showMessage(this, "输入工号不能为空");
				return false;
			}
//				if (!specialFlag) {
//					tEditText = (EditText) findViewById(R.id.eTPasswod);
			tStr = etUserPWD.getText().toString().trim();
			if (tStr.equals("")) {
				ByDialog.showMessage(this, "输入密码不能为空");
				return false;
			}
//				}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return true;
	}
	private void dealLoginOver() {
		try {
			if (dialog != null && dialog.isShowing()) {
				dismissDialog(DIALOG_LOGIN);
			}
			User tUser = MaStation.getInstance().getUser();
			if (tUser == null || User.CODE_ERR_NW.equals(tUser.getCode())) {
				ByDialog.showMessage(this, "登录失败，可能网络不好!");
				return;
			} else if (User.CODE_ERR_NP.equals(tUser.getCode())) {
				ByDialog.showMessage(this, "登录失败，用户或密码不对!");
				return;
			} else if (tUser.isLoadErr()) {
				ByDialog.showMessage(this, tUser.getLoadErrMess());
				return;
			}
			etUserID.setText(tUser.getName());// 显示
			showDialog(DIALOG_LOAD);

			if(tUser != null){
				GsonBuilder gb = new GsonBuilder();
				gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
				Gson gson = gb.create();
				String	s = gson.toJson(tUser);
				MaStation.setString2SP(context, s, User.SP_NAME, User.SP_NAME);
			}
//			MaStation.getInstance().clear();
//			load();
			try {
				tvVersion.setText(getVersionName(this)+(MaStation.isTestUrl()?"测试":""));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			(new RuLoad(getHandler())).start();
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e, false);
		}
	}

	private void dealLoadOver() {
		try {
			if (dialog1 != null && dialog1.isShowing()) {
				dismissDialog(DIALOG_LOAD);
			}
			Intent intent = new Intent();
			intent.setClass(this.getApplicationContext(), SeHeartBeat.class);
			startService(intent);
			nextActivity();
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	private void nextActivity() {
		// TODO Auto-generated method stub
		Intent i = new Intent();
//		i.setClass(AcMain.this, AcFunction.class);
//		AcNewDw
		i.setClass(AcMain.this, AcFunction.class);
		startActivity(i);
		finish();
	}

	protected Dialog onCreateDialog(int id) {
		try {
			switch (id) {
				case DIALOG_LOGIN:
					if (dialog == null) {
						dialog = new ProgressDialog(this);
						// dialog.setTitle("Indeterminate");
						dialog.setMessage("正在登录，请稍候...");
						dialog.setIndeterminate(true);
						dialog.setCancelable(true);
						dialog.setCanceledOnTouchOutside(false);
						return dialog;
					}
				case DIALOG_LOAD:
					if (dialog1 == null) {
						dialog1 = new ProgressDialog(this);
						// dialog.setTitle("Indeterminate");
						dialog1.setMessage("正在初始化，请稍候...");
						dialog1.setIndeterminate(true);
						dialog.setCanceledOnTouchOutside(false);
						dialog1.setCancelable(true);
						return dialog1;
					}
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return dialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static String getPADMAC() {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String mac = wifiInfo.getMacAddress();
		return mac;
	}

	public String getWifiInfo() {
		mWifiAdmin = new WifiAdmin(context, getHandler());
		StringBuffer sb = new StringBuffer();
		sb = sb
//				.append("当前网络信息\n")
				.append("Wifi状态：" + mWifiAdmin.getWifiState() +"\n" )
				.append("连接点名称：" + mWifiAdmin.getSSID() + "\n")
				.append("连接点MAC地址：" + mWifiAdmin.getBSSID() + "\n")
				.append("信号强度：" + mWifiAdmin.getRssi() + "dBm\n")
				.append("本机MAC地址：" + mWifiAdmin.getMacAddress() + "\n")
				.append("本机IP地址：" + mWifiAdmin.getIpAddress() + "\n")
				.append("网关：" + mWifiAdmin.getGateWay() +"");


		return sb.toString();
	}

	/*
     * 获取当前程序的版本号
     */
	public static String getVersionName(Context context) throws Exception{
		//获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		//getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		return packInfo.versionName;
	}
}
