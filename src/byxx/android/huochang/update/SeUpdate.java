package byxx.android.huochang.update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import byxx.android.huochang.R;



import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SeUpdate extends Service {
	private Handler handler = null;
	private Thread thread = null;
	private boolean isStartUpdate = false;
	private CommandReceiver cmdReceiver;
	private Context mcontext;
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private RemoteViews contentView;
	
//	private String fileName = "station.apk";
	private String fileName = "HuoChang.apk";
	private String url = "http://10.167.93.128:7001/hyzxWeb/pda/";
	private String path = "byxxdownload";
	private HttpURLConnection connection;
	private OutputStream outputStream;
	private int FileLength;
	private int DownedFileLength = 0;
	private InputStream inputStream;

	private static final int NOTIFY_ID = 0x110a;
	private static final int DOWNLOAD_STOP = 0x110b;
	private static final int DOWNLOAD_START = 0x111a;
	private static final int DOWNLOADING = 0x111b;
	private static final int DOWNLOAD_END = 0x111c;

	@Override
	public void onCreate() {
		mcontext = SeUpdate.this;
		cmdReceiver = new CommandReceiver();
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		IntentFilter filter = new IntentFilter();	// 创建IntentFilter对象
		filter.addAction("byxx.station.seuuploading");
		registerReceiver(cmdReceiver, filter);		// 注册Broadcast Receiver
		if(intent != null) {
			isStartUpdate = intent.getBooleanExtra("startFlag", false);
			if (isStartUpdate) {
				rundonwloading();
			}
		}
		getHandler();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return true;
	}

	@Override
	public void onDestroy() {
		// 取消注册广播
		unregisterReceiver(cmdReceiver);
		// 取消通知显示
		mNotificationManager.cancel(NOTIFY_ID);
		Toast.makeText(mcontext, "下载服务已停止！", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

	public class CommandReceiver extends BroadcastReceiver {// 继承自BroadcastReceiver的子类
		@Override
		public void onReceive(Context context, Intent intent) {// 重写onReceive方法
			int cmd = intent.getIntExtra("cmd", 0);// 获取Extra信息
			// 取消下载
			if (cmd == DOWNLOAD_STOP) { // 如果发来的消息是停止服务
				closeThread();
				stopSelf();
			}
		}
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

	@SuppressLint("NewApi")
	public void dealHandleMessage(Message msg) {
		try {
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case DOWNLOAD_START:
					setUpNotification();
					sendBroadcast("cmd", DOWNLOAD_START, "byxx.station.acuuploading");	// 发送下载开始广播
					break;
					
				case DOWNLOADING:
					sendBroadcast("cmd", DOWNLOADING, "byxx.station.acuuploading");	// 发送下载中广播
					
					int rate = DownedFileLength * 100 / FileLength;
					if (rate < 100) {
						// 更新进度
						contentView = new RemoteViews(mcontext.getPackageName(),
								R.layout.notification_layout);
						contentView.setTextViewText(R.id.rate, rate + "%");
						contentView.setProgressBar(R.id.progress, 100,
								rate, false);
						mNotification.contentView = contentView;
						mNotificationManager.notify(NOTIFY_ID, mNotification);
					}
					else {
						// 下载完毕后变换通知形式
						contentView.setTextViewText(R.id.rate, rate + "%");
						contentView.setProgressBar(R.id.progress, 100, rate,
								false);
						
						Intent intent4 = new Intent(mcontext, AcUpdate.class);
						PendingIntent contentIntent = PendingIntent
								.getActivity(mcontext, 0, intent4, 0);
						Builder builder = new Notification.Builder(mcontext)
							.setContentTitle("下载完成")
							.setContentText("文件已下载完毕，请安装")
							.setContentIntent(contentIntent)
							.setSmallIcon(R.drawable.status_orange)
							.setAutoCancel(true);
						mNotification = builder.getNotification();
						mNotificationManager.notify(NOTIFY_ID, mNotification);
					}
					break;
					
				case DOWNLOAD_END:
					sendBroadcast("cmd", DOWNLOAD_END, "byxx.station.acuuploading");	// 发送下载结束广播
					
					closeThread(); // 停止线程
					stopSelf(); // 停止服务
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

	// 开启下载线程
	public void rundonwloading() {
		try {
			DownedFileLength = 0;
			thread = new Thread() {
				public void run() {
					try {
						// MaStation.getInstance().reBiuldWifi = false;
						downFile();
						// MaStation.getInstance().reBiuldWifi = true;
					}
					catch (Exception e) {
						e.printStackTrace();
						// MaStation.getInstance().recordExc(e);
					}
				}
			};
			thread.start();
			Toast.makeText(getApplicationContext(), "下载开始", Toast.LENGTH_SHORT)
					.show();
		}
		catch (Exception e) {
			e.printStackTrace();
			// MaStation.getInstance().recordExc(e);
		}

	}
	
	// 发送广播给AcUpdate.java
	public void sendBroadcast(String name, int value , String action) {
		Intent intent = new Intent();
		intent.putExtra(name, value); // 发送广播命令为6，下载完成
		intent.setAction(action);
		intent.putExtra("FileLength", FileLength); // 发送FileLength
		intent.putExtra("DownedFileLength", DownedFileLength); // 发送DownedFileLength
		mcontext.sendBroadcast(intent);
	}
	
	// 终止线程
	public void closeThread() {
		if (thread != null || thread.isInterrupted()) {
			try {
				thread.interrupt();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			thread = null;
		}
	}
	
	// 下载文件
	public void downFile() {
		try {
			String urlString = url + fileName;
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			if (connection.getResponseCode() >= 400) {
				Toast.makeText(mcontext, "连接超时,无法升级?",
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
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			// 向SD卡中写入文件,用Handle传递线程
			Message message = null;
			try {
				outputStream = new FileOutputStream(file);
				byte[] buffer = new byte[1024 * 4];
				FileLength = connection.getContentLength();
				message = new Message();
				message.what = DOWNLOAD_START;
				handler.sendMessage(message);
				int len = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
					DownedFileLength += len;
					message = new Message();
					message.what = DOWNLOADING;
					handler.sendMessage(message);
					Thread.sleep(10);	// 防止进度条大幅度更新
				}
				message = new Message();
				message.what = DOWNLOAD_END;
				handler.sendMessage(message);
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
				// MaStation.getInstance().recordExc(e);
			} 
			catch (IOException e) {
				e.printStackTrace();
				// MaStation.getInstance().recordExc(e);
			}
			finally {
				outputStream.flush();
				outputStream.close();
				inputStream.close();
				connection.disconnect();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			// MaStation.getInstance().recordExc(e);
		}
	}

	// 创建通知
	@SuppressLint("NewApi")
	public void setUpNotification() {
		// 指定个性化视图
		contentView = new RemoteViews(mcontext.getPackageName(), R.layout.notification_layout);
		contentView.setTextViewText(R.id.rate, 0 + "%");
		contentView.setProgressBar(R.id.progress, 100, 0, false);
		contentView.setTextViewText(R.id.fileName, "程序更新");  
		// 指定内容意图
		Intent intent = new Intent(mcontext, AcUpdate.class);
		PendingIntent contentIntent = PendingIntent.getActivity(mcontext, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// 利用Builder新建通知
		Builder builder = new Notification.Builder(mcontext)
			.setContentTitle("开始下载！")
			.setSmallIcon(R.drawable.queue_icon_download)
			.setWhen(System.currentTimeMillis())
			.setContent(contentView)
			.setContentIntent(contentIntent);
		mNotification = builder.getNotification(); // API16该方法废弃，应改用build();
		
//		// API16改用build()方法
//		mNotification = new Notification.Builder(mcontext)
//			.setContentTitle("开始下载！")
//			.setSmallIcon(R.drawable.status_orange)
//			.setWhen(System.currentTimeMillis())
//			.setContent(contentView)
//			.setContentIntent(contentIntent)
//			.builder();
		
		// 放置在"正在运行"栏目中
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		
		// Notification Manager放置通知
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(NOTIFY_ID, mNotification);
		
	}

}
