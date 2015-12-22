package byxx.android.huochang.update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.util.ByDialog;


public class AcUpdate extends BaseActivity {


	private ProgressBar progressBar;

	private TextView textView;

	private Button button;
	private Button button2;
	TextView showServerVersion = null;
	private int FileLength;

	private int DownedFileLength = 0;

	private InputStream inputStream;

	// private URLConnection connection;
//	private String fileName = "station.apk";
	private String fileName = "HuoChang.apk";

	// private String url = "http://10.172.36.57:7001/order/android/";
//	private String url = "http://10.160.2.83:7003/kfcweb/";
	private String url = "http://10.167.93.128:7001/hyzxWeb/pda/";
	// private String url = "http://10.160.4.218:7008/kfcweb/";

	private String path = "byxxdownload";

	private HttpURLConnection connection;

	private OutputStream outputStream;

	private Handler handler = null;
    
	boolean isNeedUpdate = false;
	private static final int DOWNLOAD_STOP = 0x110b;
	private static final int DOWNLOAD_START = 0x111a;
	private static final int DOWNLOADING = 0x111b;
	private static final int DOWNLOAD_END = 0x111c;
	private CommandReceiver cmdReceiver;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.update);
			final ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(true);
			
			
//			SuperApplication.getInstance().addActivity(this);
			progressBar = (ProgressBar) findViewById(R.id.progressBar1);
			cmdReceiver = new CommandReceiver();
			textView = (TextView) findViewById(R.id.textView3);
			//
			TextView textView2 = (TextView) findViewById(R.id.textView2);
			String s1 = getString(R.string.system_name) + "\n" + "版本号:"
					+ getVersionName(this);// R.string.system_name;

			
			textView2.setText(s1);
			
			showServerVersion = (TextView)findViewById(R.id.updateinfoshow);
			isNeedUpdate = (!getVersionName(this).equals(MaStation.getInstance().getUser().getVersion()));
			if(isNeedUpdate){
				showServerVersion.setText("请更新 "+MaStation.getInstance().getUser().getVersion());
				showVesionMess();
			}else{
				showServerVersion.setText("");
			}

			
			//
			button = (Button) findViewById(R.id.button1);
			button.setOnClickListener(new ButtonListener());
			button2 = (Button) findViewById(R.id.button2);
			button2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					button2OnClick();
				}
			});
			getHandler();
		
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
		AppManager.getAppManager().addActivity(this);
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
    
	@Override
	public void onStart() {
		IntentFilter filter = new IntentFilter(); // 创建IntentFilter对象
		filter.addAction("byxx.station.acuuploading");
		registerReceiver(cmdReceiver, filter); // 注册Broadcast Receiver
		super.onStart();
	}

	@Override
	public void onDestroy() {
		// 取消注册广播
		unregisterReceiver(cmdReceiver);
		AcUpdate.this.finish();
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onRestart() {
		super.onRestart();
	}

	
	
	private void button2OnClick() {
		try {
			String savePathString = Environment.getExternalStorageDirectory()
					+ "/" + path + "/" + fileName;
			File file = new File(savePathString);
			if (!file.exists()) {
				Toast.makeText(getApplicationContext(), "没有安装文件.",
						Toast.LENGTH_LONG).show();
				return;
			}
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
			startActivity(intent);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

//	class ButtonListener implements OnClickListener {
//		public void onClick(View v) {
//			try {
//				DownedFileLength = 0;
//				Thread thread = new Thread() {
//					public void run() {
//						try {
//							MaStation.getInstance().reBiuldWifi = false;
//							downFile();
//							MaStation.getInstance().reBiuldWifi = true;
//						} catch (Exception e) {
//							MaStation.getInstance().recordExc(e);
//						}
//					}
//				};
//				thread.start();
//				button.setEnabled(false);
//				Toast.makeText(getApplicationContext(), "下载开始",
//						Toast.LENGTH_SHORT).show();
//			} catch (Exception e) {
//				MaStation.getInstance().recordExc(e);
//			}
//		}
//	}

	public class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			try {
				
				if(button.getText().equals("取消")){
				Intent intent = new Intent();
				intent.putExtra("cmd", DOWNLOAD_STOP); // 发送广播命令为1,取消下载
				intent.setAction("byxx.station.seuuploading");
				AcUpdate.this.sendBroadcast(intent);
				button.setText("下载程序");
				}
				else if(button.getText().equals("下载程序")){
				// 判断网络连接是否可用
				if (isNetworkConnected(AcUpdate.this)) {
						// 启动服务SeUpdate Service
						Intent intent = new Intent(AcUpdate.this, SeUpdate.class);
						intent.putExtra("startFlag", true);
						startService(intent);
						
						
						button2.setEnabled(false);
//						Toast.makeText(AcUpdate.this, "下载开始", Toast.LENGTH_SHORT).show();
					}
					else {
						Toast.makeText(AcUpdate.this, "无法连接网络, 请连接网络！", Toast.LENGTH_SHORT).show();
					}
				 }
			}
			catch (Exception e) {
				e.printStackTrace();
				// MaStation.getInstance().recordExc(e);
			}
		}
	}
	
	
	private void showVesionMess() {
		
			if (isNeedUpdate) {
				ByDialog.showMessage(this, "版本变更："
								+ MaStation.getInstance().getUser().getVersion()
								+ "\n请及时下载更新");
			}

	}
	
//	public Handler getHandler() {
//		if (handler == null) {
//			handler = new Handler() {
//				public void handleMessage(Message msg) {
//					dealHandleMessage(msg);
//				}
//			};
//		}
//		return handler;
//	}
//
//	private void dealHandleMessage(Message msg) {
//		try {
//			if (!Thread.currentThread().isInterrupted()) {
//
//				switch (msg.what) {
//
//				case 0:
//					progressBar.setMax(FileLength);
//					break;
//				case 1:
//					progressBar.setProgress(DownedFileLength);
//					int x = DownedFileLength * 100 / FileLength;
//					textView.setText(x + "%");
//					break;
//
//				case 2:
//					button.setEnabled(true);
//					Toast.makeText(getApplicationContext(), "下载完成",
//							Toast.LENGTH_LONG).show();
//
//					break;
//				default:
//					break;
//				}
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//	}
	
	

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

	public void dealHandleMessage(Message msg) {
		try {
			int x = 0;
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case DOWNLOAD_START:
					button.setText("取消");
					button2.setEnabled(false);
					progressBar.setMax(FileLength);
					break;

				case DOWNLOADING:
					x = DownedFileLength * 100 / FileLength;
					progressBar.setMax(FileLength);
					progressBar.setProgress(DownedFileLength);
					textView.setText(x + "%");
					break;

				case DOWNLOAD_END:
					
					button2.setEnabled(true);
					button.setText("下载程序");
//					button3.setEnabled(false);
					x = DownedFileLength * 100 / FileLength;
					progressBar.setProgress(DownedFileLength);
					textView.setText(x + "%");
					Toast.makeText(getApplicationContext(), "下载完成",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			// MaStation.getInstance().recordExc(e);
		}
	}
	
	
	private void downFile() {
		try {
			// String urlString =
			// "http://192.168.43.225:7001/order/android/MyNFC.apk";
			String urlString = url + fileName;
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			// if (connection.getReadTimeout() == 5) {
			if (connection.getResponseCode() >= 400) {
				Toast.makeText(getApplicationContext(), "连接超时,无法升级?",
						Toast.LENGTH_LONG).show();
				return;
			}

			inputStream = connection.getInputStream();

			String savePAth = Environment.getExternalStorageDirectory() + "/"
					+ path;

			File file1 = new File(savePAth);

			if (!file1.exists()) {
				file1.mkdir();
			}
			String savePathString = savePAth + "/" + fileName;

			File file = new File(savePathString);

			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/*
			 * 
			 * 向SD卡中写入文件,用Handle传递线程
			 */

			Message message = null;
			try {
				outputStream = new FileOutputStream(file);
				// byte[] buffer = new byte[1024 * 4];
				byte[] buffer = new byte[1024 * 4];
				FileLength = connection.getContentLength();
				message = new Message();
				message.what = 0;
				handler.sendMessage(message);
				// while (DownedFileLength < FileLength) {
				// DownedFileLength += inputStream.read(buffer);
				// outputStream.write(buffer);
				// outputStream.flush();
				int len = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
					DownedFileLength += len;
					//
					message = new Message();
					message.what = 1;
					handler.sendMessage(message);
					// Thread.sleep(5);
					Thread.sleep(10);
				}
				message = new Message();
				message.what = 2;
				handler.sendMessage(message);
				outputStream.flush();
				outputStream.close();
				inputStream.close();
				connection.disconnect();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
//				MaStation.getInstance().recordExc(e);
			} catch (IOException e) {
				e.printStackTrace();
//				MaStation.getInstance().recordExc(e);
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	public class CommandReceiver extends BroadcastReceiver {// 继承自BroadcastReceiver的子类
		@Override
		public void onReceive(Context context, Intent intent) {// 重写onReceive方法
			int cmd = intent.getIntExtra("cmd", 0);// 获取Extra信息
			Message message;
			if (cmd == DOWNLOAD_START) {
				DownedFileLength = intent.getIntExtra("DownedFileLength", 0);
				FileLength = intent.getIntExtra("FileLength", 0);
				message = new Message();
				message.what = DOWNLOAD_START;
				handler.sendMessage(message);
			}
			if (cmd == DOWNLOADING) {
				DownedFileLength = intent.getIntExtra("DownedFileLength", 0);
				FileLength = intent.getIntExtra("FileLength", 0);
				message = new Message();
				message.what = DOWNLOADING;
				handler.sendMessage(message);
			}
			if (cmd == DOWNLOAD_END) {
				DownedFileLength = intent.getIntExtra("DownedFileLength", 0);
				FileLength = intent.getIntExtra("FileLength", 0);
				message = new Message();
				message.what = DOWNLOAD_END;
				handler.sendMessage(message);
			}
		}
	}
	
	// 判断是否连接网络
	public boolean isNetworkConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				buHomeOnClick();
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	private void buHomeOnClick() {
		try {
			Intent intent = new Intent();
			intent.setClass(AcUpdate.this, AcFunction.class);
			startActivity(intent);
			finish();
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
	
}
