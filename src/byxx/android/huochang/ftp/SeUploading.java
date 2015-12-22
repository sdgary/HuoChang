package byxx.android.huochang.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import byxx.android.huochang.Constant;
import byxx.android.huochang.R;
import byxx.android.huochang.dw.AcJzx;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.Toast;

public class SeUploading extends Service{
    CommandReceiver cmdReceiver;
    boolean flag;
    static String savePathString = Environment.getExternalStorageDirectory()
			+ "/" + "DCIM" + "/" + "100ANDRO/";
	private String mfileName = null;
	static final int SELECTCODE = 0xbc3b8;
	static final int CAMERACODE =  0xbc804;
	static final int CAMERACODE2 = 0xbc868;
	static final int TAKE_VIDEO = 0xbc8cc;
	static final int FTP_RETURN_OK =0x115a;
	static final int FTP_RETURN_ERR = 0x115b;
	static final int FTP_NOTIFICATION_CREATE = 0x116a;
	static final int FTP_NOTIFICATION_CANCEL= 0x116b;

//	RuUpLoading runURuUpLoading =null;
	Handler handler = null;
	Context mcontext;
//	int type = 0;
//	String mTitle = null;
	String mFileDescribe = null;
	String mRemoteFileDes = null;
//	String locusRegion =null;
//	String locusRegionDes =null;
//	String loseDescribe =null;
//	String findMan =null;
//	String trainNum =null;
//	String trainCarSeat =null;
//	String conductor =null;
//	long locusTime = 0;
//	long giveupTime = 0;
//	String remark =null;
	String stationCode =null;
	String pictures = null;
//	int iol; //其他或失物 0、1

	public FTPClient ftpClient = new FTPClient();
	private NotificationManager mNotificationManager = null;  
    private Notification mNotification = null;  
    private static final int NOTIFY_ID = 0;  
	String fileId = null;
    
	boolean isACLP = false;
	boolean isACothers = false;
	
	
    @Override
    public void onCreate() {//重写onCreate方法
    	    mcontext = this;
            flag = true;
            cmdReceiver = new CommandReceiver();
            mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);  
            Log.v("ftp","SeUploading  onCreate()");
            super.onCreate();
    }
    
    
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public SeUploading() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
        IntentFilter filter = new IntentFilter();//创建IntentFilter对象
        filter.addAction("byxx.station.seuuploading");
        registerReceiver(cmdReceiver, filter);//注册Broadcast Receiver
        Bundle bundle = intent.getExtras();
        mfileName = bundle.getString("setfilename");
        mRemoteFileDes = null;
        mRemoteFileDes = bundle.getString("remotefilename");
        if(mRemoteFileDes == null){
        	mRemoteFileDes = "";
        }
//        type = bundle.getInt("setfiletype");
        
//        mTitle = bundle.getString("setTitle");
//        mFileDescribe = bundle.getString("setFileDescribe");
//        
//        Log.v("uploading", "uploading : "+mfileName + " "+type);
//        
//
//        iol = bundle.getInt("OthorLP");
//    	locusRegion =bundle.getString("locusRegion");
//    	locusRegionDes =bundle.getString("locusRegionDes");
//    	loseDescribe =bundle.getString("loseDescribe");
//    	findMan =bundle.getString("findMan");
//    	trainNum =bundle.getString("trainNum");
//    	trainCarSeat =bundle.getString("trainCarSeat");
//    	conductor =bundle.getString("conductor");
//    	locusTime = bundle.getLong("locusTime");
//    	giveupTime = bundle.getLong("giveupTime");
//    	remark =bundle.getString("remark");
//    	
//    	int i = bundle.getInt("isLPorOthers"); //1代表 LP -1代表 others
    	
//    	if(i > 0){
//    		isACLP = true;
//    	}else if (i < 0){
//    		isACothers = true;
//    	}
    	
    	setPictures(mfileName);
    	Log.v("ftp", " "+getPictures() +" "+ mfileName);        
        if(mfileName!= null){
//          //调用方法启动线程
         
        	FTPThread ftpthread = new  FTPThread(mHandler,mfileName,mRemoteFileDes);
        	ftpthread.start();
        }
        return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	    Log.v("ftp","SeUploading  onDestroy()  结束后台服务");
		this.unregisterReceiver(cmdReceiver);//取消注册的CommandReceiver
		 
	}

	
	
	
	public String getPictures() {
		return pictures;
	}


	public void setPictures(String pictures) {
		this.pictures = pictures;
	}




	private class CommandReceiver extends BroadcastReceiver{//继承自BroadcastReceiver的子类
        @Override
        public void onReceive(Context context, Intent intent) {//重写onReceive方法
                int cmd = intent.getIntExtra("cmd", -1);//获取Extra信息
                int SEUPLOADING_STOP = 1;
                if(cmd == SEUPLOADING_STOP){//如果发来的消息是停止服务                                
                        flag = false;//停止线程
                        stopSelf();//停止服务
                }
        }                
	}
	
	
	
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			
			String flag =null;
			switch (msg.what)
			{

			case FTP_NOTIFICATION_CREATE:
	             int rate = msg.arg1;  
	                if (rate < 100) {  
	                    // 更新进度  
	                    RemoteViews contentView = mNotification.contentView;  
	                    contentView.setTextViewText(R.id.rate, rate + "%");  
	                    contentView.setProgressBar(R.id.progress, 100, rate, false);  
	                } else {  
	                    // 下载完毕后变换通知形式  
	                    mNotification.flags = Notification.FLAG_AUTO_CANCEL;  
	                    mNotification.contentView = null;  
	                    Intent intent = new Intent(mcontext, AcJzx.class);  
	                    PendingIntent contentIntent = PendingIntent.getActivity(mcontext, 0, intent, 0);  
	                    mNotification.setLatestEventInfo(mcontext, "上传完成", "文件已上传完毕", contentIntent);  
	                }  
	  
	                // 最后别忘了通知一下,否则不会更新  
	                mNotificationManager.notify(NOTIFY_ID, mNotification);  
				break;
			case FTP_NOTIFICATION_CANCEL:
	                // 取消通知  
	                mNotificationManager.cancel(NOTIFY_ID);  
	                break;  
			case FTP_RETURN_OK:
					flag  = msg.getData().getString("isuploading");
					Toast.makeText(mcontext, flag, Toast.LENGTH_SHORT).show();
	//			    tvstatus.setText("上传"+flag);
					String sentType = "0";
					if(("上传新文件成功") == flag ){
						
//					  if(type == 3){
//						  sentType ="1";
//					  }
		  
//					  if(iol == 0){
//					  //启动线程 报告文件名，标题，类型，内容 回服务器
//					  threadStart(mfileName,mTitle,sentType,mFileDescribe);
//					  }
//					  if(iol == 1){
//						  Log.v("ftp", " }else if(iol == 1){" + pictures);
//						  threadStart1(
//								  sentType,
//								  locusRegion,
//								  locusRegionDes,
//								  loseDescribe,
//								  findMan,
//								  trainNum,
//								  trainCarSeat,
//								  conductor,
//								  locusTime,
//								  giveupTime,
//								  remark,
//								  getPictures());  
//					  }
					  	
					}
					setBCULResult(flag);
//					setBCULResultLP(flag);
					mNotificationManager.cancel(NOTIFY_ID); 
					Toast.makeText(mcontext,flag, Toast.LENGTH_SHORT).show();
					break;
			case FTP_RETURN_ERR:
					flag = "上传失败";
					setBCULResult(flag);
//					setBCULResultLP(flag);
					mNotificationManager.cancel(NOTIFY_ID);  
					Toast.makeText(mcontext, flag, Toast.LENGTH_SHORT).show();
					break;
					
			case Constant.MSG_ID_SAVEFTPFILE:
				fileId = msg.getData().getString("ftpresult");
			
				if(fileId == null){
					Toast.makeText(mcontext, "上传服务器失败", Toast.LENGTH_SHORT).show();
				}else{
					setBCUSeResult(fileId);
//					setBCUSeResultLP(fileId);
//					Toast.makeText(mcontext, fileId, Toast.LENGTH_SHORT).show();
				}
				
				break;
				
			case Constant.MSG_ID_LOSEGOODS:
				String s=  msg.getData().getString("sendLoseGoods");
				if(s == null){
					Toast.makeText(mcontext, "上传服务器失败", Toast.LENGTH_SHORT).show();
				}else{
					setBCUSeResult(s);  //广播id可能有错 暂时屏蔽处理 20130327 @way
//					setBCUSeResultLP(s);
				}
				
				break;
			default:
	  
			}


		};
	};
	
	//发送上传FTP结果广播
	private void setBCULResult(String _flag){
		if(isACothers){
		//广播
		Intent bintent = new Intent();
		bintent.setAction(Constant.BC_UPLOADING_RESULT);
		Bundle bundle = new Bundle();
		bundle.putString("setFtpReceiverFlag", _flag);
        bintent.putExtras(bundle);
        sendBroadcast(bintent);//发送广播
        stopSelf();
		}
	}
	
	//发送上传服务器结果广播
	private void setBCUSeResult(String fileid){
		//广播
		if(isACothers){
			Intent bintent = new Intent();
			bintent.setAction(Constant.BC_SERVICE_FILEID);
			Bundle bundle = new Bundle();
			bundle.putString("setFileId", fileid);
	        bintent.putExtras(bundle);
	        sendBroadcast(bintent);//发送广播
	        stopSelf();
		}
	}
	
//	//lp
//	//发送上传FTP结果广播
//	private void setBCULResultLP(String _flag){
//		if(isACLP){
//		//广播
//		Intent bintent = new Intent();
//		bintent.setAction(Constant.BC_UPLOADING_RESULT_LP);
//		Bundle bundle = new Bundle();
//		bundle.putString("setFtpReceiverFlag", _flag);
//        bintent.putExtras(bundle);
//        sendBroadcast(bintent);//发送广播
//        stopSelf();
//		}
//	}
//	
//	//发送上传服务器结果广播
//	private void setBCUSeResultLP(String fileid){
//		if(isACLP){
//		//广播
//		Intent bintent = new Intent();
//		bintent.setAction(Constant.BC_SERVICE_FILEID_LP);
//		Bundle bundle = new Bundle();
//		bundle.putString("setFileId", fileid);
//        bintent.putExtras(bundle);
//        sendBroadcast(bintent);//发送广播
//        stopSelf();
//		}
//	}
	
	
	
	class FTPThread extends Thread{

        private String mfilenamerun = null;
        private String mRemotefilerun = null;
		Handler handler=null;
//		int typerun = 0;
		
		public FTPThread() {
			// TODO Auto-generated constructor stub
		}
		
		public FTPThread(Handler _handler ,String _filename,String _Remotefilename) {
			// TODO Auto-generated constructor stub
			this.mfilenamerun = _filename;
			this.mRemotefilerun = _Remotefilename;
			this.handler=_handler;
//			this.typerun = _type;
		}
				 
		@Override
		public void run() {
			// TODO Auto-generated method stub 
			try{
			setUpNotification(); 
			InContinueFTP myFtp = new InContinueFTP();
			long sl = System.currentTimeMillis();
			myFtp.connect("10.167.93.128", 21, "byxx","byxx");
			String pandn = null;
			pandn = savePathString +mfilenamerun;

			Log.v("ftp", "pandn = "+pandn +"mfileName "+mfileName);
		
			UploadStatus upstatus = myFtp.upload(pandn,mRemotefilerun+"/"+mfileName);
			myFtp.disconnect();
			long el = System.currentTimeMillis();
			Log.v("times", "ftp"+(el-sl));
			String isSuccess = "未知错误";
			if(UploadStatus.Create_Directory_Fail == upstatus){
				isSuccess = "远程服务器相应目录创建失败";

			}else if(UploadStatus.Create_Directory_Success == upstatus){
				isSuccess = "远程服务器闯将目录成功";

			}else if(UploadStatus.Upload_New_File_Success == upstatus){
				isSuccess = "上传新文件成功";

			}else if(UploadStatus.Upload_New_File_Failed == upstatus){
				isSuccess = "上传新文件失败";
				
			}else if(UploadStatus.File_Exits == upstatus){
				isSuccess = " 文件已经存在";

			}else if(UploadStatus.Upload_New_File_Failed == upstatus){
				isSuccess = "上传新文件失败";
				
			}else if(UploadStatus.Remote_Bigger_Local == upstatus){
				isSuccess = "远程文件大于本地文件";
				
			}else if(UploadStatus.Upload_From_Break_Success == upstatus){
				isSuccess = "断点续传成功";
	
			}else if(UploadStatus.Upload_From_Break_Failed == upstatus){
				isSuccess = " 断点续传失败";
				
			}else if(UploadStatus.Delete_Remote_Faild == upstatus){
				isSuccess = "删除远程文件失败";
			}
			Log.v("ftp", isSuccess);
			Message msg=new Message();
			msg.what = FTP_RETURN_OK;
			Bundle tBundle = new Bundle();
			tBundle.putString("isuploading", isSuccess);
			msg.setData(tBundle);
			handler.sendMessage(msg);
		
			}catch(Exception e){
             Log.v("ftp", " "+e);
			 handler.sendEmptyMessage(FTP_RETURN_ERR);	
			}
		}
	}
	
//	public RuUpLoading getRuUpLoading() {
//
//			runURuUpLoading = new RuUpLoading(mHandler);
//
//		return runURuUpLoading;
//	}
	
	/**
	 * 任务线程启动
	 */ 
//	public void threadStart(String _mfileName, String _title ,String _type, String _etfileDescribe
//			) {
//		getRuUpLoading().switchtype = 0;
//		getRuUpLoading().start(_mfileName,_title,_type,_etfileDescribe);
//	}

//	public void threadStart1(
//				String _type,
//				String _locusRegion,
//				String _locusRegionDes,
//				String _loseDescribe,
//				String _findMan,
//				String _trainNum,
//				String _trainCarSeat,
//				String _conductor,
//				long _locusTime,
//				long _giveupTime,
//				String _remark ,
//				String _mfileName
//			) {
//		getRuUpLoading().switchtype = 1;
//		getRuUpLoading().startl(
//				 _locusRegion,
//				 _locusRegionDes,
//				 _loseDescribe,
//				 _findMan,
//				 _trainNum,
//				 _trainCarSeat,
//				 _conductor,
//				 _remark,
//				 _giveupTime,
//				 _locusTime,
//				 _mfileName,
//				 _type);
//	}
//	public void startl(String locusRegion,
//			String locusRegionDes,
//			String loseDescribe,
//			String findMan,
//			String trainNum,
//			String trainCarSeat,
//			String conductor,
//			String remark,
//			long giveupTime,
//			long locusTime ,
//			String Pictures,
//			String type) {
	
//	public void stopThread() {
//		getRuUpLoading().setRunFlag(false);
//	}


	
	/** 
     * 创建通知 
     */  
    private void setUpNotification() {  
        int icon = R.drawable.queue_icon_send;  
        CharSequence tickerText = "开始上传";  
        long when = System.currentTimeMillis(); 
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);  
        mNotification = new Notification(icon, tickerText, when);  
  
        // 放置在"正在运行"栏目中  
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;  
  
        RemoteViews contentView = new RemoteViews(mcontext.getPackageName(), R.layout.notification_layout);  
        contentView.setTextViewText(R.id.fileName, mfileName);  
        // 指定个性化视图  
        mNotification.contentView = contentView;  
  
        Intent intent = new Intent(this, AcJzx.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(mcontext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);  
        // 指定内容意图  
        mNotification.contentIntent = contentIntent;  
        mNotificationManager.notify(NOTIFY_ID, mNotification);  
    }  
	
	
	
	
	
	
	public class InContinueFTP {
		public FTPClient ftpClient = new FTPClient();

		public InContinueFTP() {
			// 设置将过程中使用到的命令输出到控制台
			this.ftpClient.addProtocolCommandListener(new PrintCommandListener(
					new PrintWriter(System.out)));
		}


		public boolean connect(String hostname, int port, String username,
				String password) throws IOException {
			ftpClient.connect(hostname, port);
			ftpClient.setControlEncoding("UTF-8");
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				if (ftpClient.login(username, password)) {
					return true;
				}
			}
			disconnect();
			return false;
		}


		public DownloadStatus download(String remote, String local)
				throws IOException {
			// 设置被动模式
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			DownloadStatus result;

			// 检查远程文件是否存在
			FTPFile[] files = ftpClient.listFiles(new String(
					remote.getBytes("UTF-8"), "iso-8859-1"));
			if (files.length != 1) {
				System.out.println("远程文件不存在");
				return DownloadStatus.Remote_File_Noexist;
			}

			long lRemoteSize = files[0].getSize();
			File f = new File(local);
			// 本地存在文件，进行断点下载
			if (f.exists()) {
				long localSize = f.length();
				// 判断本地文件大小是否大于远程文件大小
				if (localSize >= lRemoteSize) {
					System.out.println("本地文件大于远程文件，下载中止");
					return DownloadStatus.Local_Bigger_Remote;
				}

				// 进行断点续传，并记录状态
				FileOutputStream out = new FileOutputStream(f, true);
				ftpClient.setRestartOffset(localSize);
				InputStream in = ftpClient.retrieveFileStream(new String(remote
						.getBytes("UTF-8"), "iso-8859-1"));
				byte[] bytes = new byte[1024];
				long step = lRemoteSize / 100;
				long process = localSize / step;
				int c;
				while ((c = in.read(bytes)) != -1) {
					out.write(bytes, 0, c);
					localSize += c;
					long nowProcess = localSize / step;
					if (nowProcess > process) {
						process = nowProcess;
						if (process % 10 == 0)
							System.out.println("下载进度：" + process);
						// TODO 更新文件下载进度,值存放在process变量中
					}
				}
				in.close();
				out.close();
				boolean isDo = ftpClient.completePendingCommand();
				if (isDo) {
					result = DownloadStatus.Download_From_Break_Success;
				} else {
					result = DownloadStatus.Download_From_Break_Failed;
				}
			} else {
				OutputStream out = new FileOutputStream(f);
				InputStream in = ftpClient.retrieveFileStream(new String(remote
						.getBytes("UTF-8"), "iso-8859-1"));
				byte[] bytes = new byte[1024];
				long step = lRemoteSize / 100;
				long process = 0;
				long localSize = 0L;
				int c;
				while ((c = in.read(bytes)) != -1) {
					out.write(bytes, 0, c);
					localSize += c;
					long nowProcess = localSize / step;
					if (nowProcess > process) {
						process = nowProcess;
						if (process % 10 == 0){
//							System.out.println("下载进度：" + process);
						}
						// TODO 更新文件下载进度,值存放在process变量中
					}
				}
				in.close();
				out.close();
				boolean upNewStatus = ftpClient.completePendingCommand();
				if (upNewStatus) {
					result = DownloadStatus.Download_New_Success;
				} else {
					result = DownloadStatus.Download_New_Failed;
				}
			}
			return result;
		}


		public UploadStatus upload(String local, String remote) throws IOException {
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding("UTF-8");
			UploadStatus result;
			// 对远程目录的处理
			String remoteFileName = remote;
			if (remote.contains("/")) {
				remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
				// 创建服务器远程目录结构，创建失败直接返回
				if (CreateDirecroty(remote, ftpClient) == UploadStatus.Create_Directory_Fail) {
					return UploadStatus.Create_Directory_Fail;
				}
			}

			// 检查远程是否存在文件
			FTPFile[] files = ftpClient.listFiles(new String(remoteFileName
					.getBytes("UTF-8"), "iso-8859-1"));
			if (files.length == 1) {
				long remoteSize = files[0].getSize();
				File f = new File(local);
				long localSize = f.length();
				if (remoteSize == localSize) {
					return UploadStatus.File_Exits;
				} else if (remoteSize > localSize) {
					return UploadStatus.Remote_Bigger_Local;
				}

				// 尝试移动文件内读取指针,实现断点续传
				result = uploadFile(remoteFileName, f, ftpClient, remoteSize);

				// 如果断点续传没有成功，则删除服务器上文件，重新上传
				if (result == UploadStatus.Upload_From_Break_Failed) {
					if (!ftpClient.deleteFile(remoteFileName)) {
						return UploadStatus.Delete_Remote_Faild;
					}
					result = uploadFile(remoteFileName, f, ftpClient, 0);
				}
			} else {
				result = uploadFile(remoteFileName, new File(local), ftpClient, 0);
			}
			return result;
		}


		public void disconnect() throws IOException {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
		}


		public UploadStatus CreateDirecroty(String remote, FTPClient ftpClient)
				throws IOException {
			UploadStatus status = UploadStatus.Create_Directory_Success;
			String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
			if (!directory.equalsIgnoreCase("/")
					&& !ftpClient.changeWorkingDirectory(new String(directory
							.getBytes("UTF-8"), "iso-8859-1"))) {
				// 如果远程目录不存在，则递归创建远程服务器目录
				int start = 0;
				int end = 0;
				if (directory.startsWith("/")) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf("/", start);
				while (true) {
					String subDirectory = new String(remote.substring(start, end)
							.getBytes("UTF-8"), "iso-8859-1");
					if (!ftpClient.changeWorkingDirectory(subDirectory)) {
						if (ftpClient.makeDirectory(subDirectory)) {
							ftpClient.changeWorkingDirectory(subDirectory);
						} else {
							System.out.println("创建目录失败");
							return UploadStatus.Create_Directory_Fail;
						}
					}

					start = end + 1;
					end = directory.indexOf("/", start);

					// 检查所有目录是否创建完毕
					if (end <= start) {
						break;
					}
				}
			}
			return status;
		}


		public UploadStatus uploadFile(String remoteFile, File localFile,
				FTPClient ftpClient, long remoteSize) throws IOException {
			UploadStatus status;
			Intent bcintent = new Intent();
			// 显示进度的上传
			long step = localFile.length() / 100;
			long process = 0;
			long localreadbytes = 0L;
			RandomAccessFile raf = new RandomAccessFile(localFile, "r");
			OutputStream out = ftpClient.appendFileStream(new String(remoteFile
					.getBytes("UTF-8"), "iso-8859-1"));
			// 断点续传
			if (remoteSize > 0) {
				ftpClient.setRestartOffset(remoteSize);
				process = remoteSize / step;
				raf.seek(remoteSize);
				localreadbytes = remoteSize;
			}
			byte[] bytes = new byte[1024];
			int c;
			while ((c = raf.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localreadbytes += c;
				if (localreadbytes / step != process) {
					process = localreadbytes / step;
//					Log.v("process","上传进度:"+ process);
					if(isACothers){
						bcintent.setAction(Constant.BC_UPLOADING_UPDATEUI);
					}else{
						bcintent.setAction(Constant.BC_UPLOADING_UPDATEUI_LP);
					}
					Bundle bundle = new Bundle();
					bundle.putLong("process_data", process);
					bcintent.putExtras(bundle);
	                sendBroadcast(bcintent);//发送广播
	                
	                
	                

	    			int t = (int)process;
	    			int tten =(t/10)*10;
	    			if(t == tten){
		    			Message pmsg=new Message();
		    			pmsg.what = FTP_NOTIFICATION_CREATE;
	    				pmsg.arg1 = tten;
	    				mHandler.sendMessage(pmsg); //发送handler
	    			}
	    			
	                
	                
	                
	                
					// TODO 汇报上传状态
	                
	                
	                
				}
			}
			out.flush();
			raf.close();
			out.close();
			boolean result = ftpClient.completePendingCommand();
			if (remoteSize > 0) {
				status = result ? UploadStatus.Upload_From_Break_Success
						: UploadStatus.Upload_From_Break_Failed;
			} else {
				status = result ? UploadStatus.Upload_New_File_Success
						: UploadStatus.Upload_New_File_Failed;
			}
			return status;
		}
	
	
	}
}
