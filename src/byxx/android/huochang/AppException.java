package byxx.android.huochang;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.http.HttpException;

import byxx.android.huochang.common.UIHelper;
import byxx.android.huochang.function.AcFunction;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

/**
 * 应用程序异常类：用于捕获异常和提示错误信息
 * @version 1.0
 * @created
 */
public class AppException extends Exception implements UncaughtExceptionHandler{

	private final static boolean Debug = false;//是否保存错误日志

	/** 定义异常类型 */
	public final static byte TYPE_NETWORK 	= 0x01;
	public final static byte TYPE_SOCKET	= 0x02;
	public final static byte TYPE_HTTP_CODE	= 0x03;
	public final static byte TYPE_HTTP_ERROR= 0x04;
	public final static byte TYPE_XML	 	= 0x05;
	public final static byte TYPE_IO	 	= 0x06;
	public final static byte TYPE_RUN	 	= 0x07;

	private Application application;
	private byte type;
	private int code;
	private String error;

	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;

//	private AppException(){
//		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//	}

	private AppException(Application application){
		this.application = application;
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	private AppException(byte type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;
		if(Debug){
			this.saveErrorLog(excp);
		}
	}
	public int getCode() {
		return this.code;
	}
	public int getType() {
		return this.type;
	}

	/**
	 * 提示友好的错误信息
	 * @param ctx
	 */
	public void makeToast(Context ctx){
		switch(this.getType()){
			case TYPE_HTTP_CODE:
				String err = ctx.getString(R.string.http_status_code_error, this.getCode());
				error = err;
				Toast.makeText(ctx, err, Toast.LENGTH_SHORT).show();
				break;
			case TYPE_HTTP_ERROR:
				error = ctx.getString(R.string.http_exception_error, this.getCode());
				Toast.makeText(ctx, R.string.http_exception_error, Toast.LENGTH_SHORT).show();
				break;
			case TYPE_SOCKET:
				error = ctx.getString(R.string.socket_exception_error, this.getCode());
				Toast.makeText(ctx, R.string.socket_exception_error, Toast.LENGTH_SHORT).show();
				break;
			case TYPE_NETWORK:
				error = ctx.getString(R.string.network_not_connected, this.getCode());
				Toast.makeText(ctx, R.string.network_not_connected, Toast.LENGTH_SHORT).show();
				break;
			case TYPE_XML:
				error = ctx.getString(R.string.xml_parser_failed, this.getCode());
				Toast.makeText(ctx, R.string.xml_parser_failed, Toast.LENGTH_SHORT).show();
				break;
			case TYPE_IO:
				error = ctx.getString(R.string.io_exception_error, this.getCode());
				Toast.makeText(ctx, R.string.io_exception_error, Toast.LENGTH_SHORT).show();
				break;
			case TYPE_RUN:
				error = ctx.getString(R.string.app_run_code_error, this.getCode());
				Toast.makeText(ctx, R.string.app_run_code_error, Toast.LENGTH_SHORT).show();
				break;
			default:
				error = "抱歉，大朗程序刚才重启了一下！";
				Toast.makeText(ctx, "抱歉，大朗程序需要重新启动一下！", Toast.LENGTH_SHORT).show();
				break;
		}
	}

	/**
	 * 保存异常日志
	 * @param excp
	 */
	public void saveErrorLog(Exception excp) {
		String errorlog = "errorlog.txt";
		String savePath = "";
		String logFilePath = "";
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			//判断是否挂载了SD卡
			String storageState = Environment.getExternalStorageState();
			if(storageState.equals(Environment.MEDIA_MOUNTED)){
				savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/OSChina/Log/";
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdirs();
				}
				logFilePath = savePath + errorlog;
			}
			//没有挂载SD卡，无法写文件
			if(logFilePath == ""){
				return;
			}
			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			fw = new FileWriter(logFile,true);
			pw = new PrintWriter(fw);
			pw.println("--------------------"+(new Date().toLocaleString())+"---------------------");
			excp.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(pw != null){ pw.close(); }
			if(fw != null){ try { fw.close(); } catch (IOException e) { }}
		}

	}

	public static AppException http(int code) {
		return new AppException(TYPE_HTTP_CODE, code, null);
	}

	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, 0 ,e);
	}

	public static AppException socket(Exception e) {
		return new AppException(TYPE_SOCKET, 0 ,e);
	}

	public static AppException io(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new AppException(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof IOException){
			return new AppException(TYPE_IO, 0 ,e);
		}
		return run(e);
	}

	public static AppException xml(Exception e) {
		return new AppException(TYPE_XML, 0, e);
	}

	public static AppException network(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new AppException(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof HttpException){
			return http(e);
		}
		else if(e instanceof SocketException){
			return socket(e);
		}
		return http(e);
	}

	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, 0, e);
	}

	/**
	 * 获取APP异常崩溃处理对象
	 * @param
	 * @return
	 */
	public static AppException getAppExceptionHandler(Application application){
		return new AppException(application);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		if(!handleException(ex) && mDefaultHandler != null) {

			mDefaultHandler.uncaughtException(thread, ex);
		}else{
		if(AppManager.getAppManager().currentActivity() instanceof AcFunction  ){
			Intent intent = new Intent(application.getApplicationContext(), AcMain.class);
			PendingIntent restartIntent = PendingIntent.getActivity(
					application.getApplicationContext(), 0, intent,
					Intent.FLAG_ACTIVITY_NEW_TASK);
			//退出程序
			AlarmManager mgr = (AlarmManager)application.getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
					restartIntent); // 1秒钟后重启应用
			AppManager.getAppManager().finishAllActivity();
		}else if(AppManager.getAppManager().currentActivity() instanceof AcMain){
			AppManager.getAppManager().finishAllActivity();
		}else{
			Intent intent = new Intent(application.getApplicationContext(), AcFunction.class);
			PendingIntent restartIntent = PendingIntent.getActivity(
					application.getApplicationContext(), 0, intent,
					Intent.FLAG_ACTIVITY_NEW_TASK);
			//退出程序
			AlarmManager mgr = (AlarmManager)application.getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
					restartIntent); // 1秒钟后重启应用
			AppManager.getAppManager().finishAllActivity();
		}


		}


	}
	/**
	 * 自定义异常处理:收集错误信息&发送错误报告
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if(ex == null) {
			return false;
		}


//		AppManager.getAppManager().finishActivity();
		final Context context = AppManager.getAppManager().currentActivity();
		makeToast(context);
		// 自定义弹出对话框

		if(context == null) {
			return false;
		}
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				makeToast(application);
				//显示异常信息&发送报告
				new AlertDialog.Builder(application).
						setMessage("抱歉，大朗程序需要重新启动一下！").
						setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
//						AppManager.getAppManager().AppExit(AppManager.getAppManager().currentActivity());
							}
						}).create().show();
				Looper.loop();
			}
		}.start();

		return true;
	}
	/**
	 * 获取APP崩溃异常报告
	 * @param ex
	 * @return
	 */
//	private String getCrashReport(Context context, Throwable ex) {
//		PackageInfo pinfo = ((AppContext)context.getApplicationContext()).getPackageInfo();
//		StringBuffer exceptionStr = new StringBuffer();
//		exceptionStr.append("Version: "+pinfo.versionName+"("+pinfo.versionCode+")\n");
//		exceptionStr.append("Android: "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.MODEL+")\n");
//		exceptionStr.append("Exception: "+ex.getMessage()+"\n");
//		StackTraceElement[] elements = ex.getStackTrace();
//		for (int i = 0; i < elements.length; i++) {
//			exceptionStr.append(elements[i].toString()+"\n");
//		}
//		return exceptionStr.toString();
//	}
}
