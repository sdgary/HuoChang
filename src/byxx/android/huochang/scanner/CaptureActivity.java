package byxx.android.huochang.scanner;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.scanner.camera.CameraManager;
import byxx.android.huochang.scanner.decoding.CaptureActivityHandler;
import byxx.android.huochang.scanner.decoding.InactivityTimer;
import byxx.android.huochang.scanner.view.ViewfinderView;
import byxx.android.huochang.storemanager.WhGoodsDTO;
import byxx.android.huochang.storemanager.WhZydDTO;
import byxx.android.huochang.util.ByDialog;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class CaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private WindowManager mWindowManager = null;
	private Bitmap map = null;
	Context context;
	CheckCode checkCode;
	ProgressDialog progressDialog;

	//默认屏幕长宽
	public int diaplayWidth = 480;
	public int diaplayHeight = 800;
	private String resStr = "";//用于显示扫描结果
	Result res = null;
	private WhGoodsDTO resObj = new WhGoodsDTO();
	

	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture);
		// 初始化 CameraManager
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		
		this.mWindowManager = getWindowManager();
		Display display = this.mWindowManager.getDefaultDisplay();
		this.diaplayWidth = display.getWidth();
//		CameraManager.get().setMIN_WIDTH((int) (this.diaplayWidth * 0.75));
		this.diaplayHeight = display.getHeight();
//		CameraManager.get().setMIN_HEIGHT((int) (this.diaplayHeight * 0.5));
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		}
		else  {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		}
		catch (IOException ioe) {
			return;
		}
		catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface)  {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(final Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		resStr = obj.getText();
//		new CheckCode().execute(obj.getText());
//		map = barcode;
//		AlertDialog.Builder dialog = new AlertDialog.Builder(CaptureActivity.this);
//		if (map == null) {
//			dialog.setIcon(null);
//		}
//		else {
//
//			Drawable drawable = new BitmapDrawable(map);
//			dialog.setIcon(drawable);
//		}
//		
//		if(resObj == null){
//			dialog.setTitle("扫描结果");
//			dialog.setMessage(resStr);
//		}else{
//			dialog.setTitle("品名：" + resObj.getPm() + '\n' + "重量/件数：" + resObj.getWeight() + resObj.getJldw());
//			dialog.setMessage("收货人："+ resObj.getShr()+ "\n发货人："+resObj.getShr()+'\n'
//					+"到达:"+resObj.getYSLX(resObj.getDdlx())+"    入库：" + resObj.getFactintime() + '\n'		
//					+"运单:"+resObj.getYdh()+"    货票：" + resObj.getHph()	);
//		}
//	
//		
//		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			 {
//				//用默认浏览器打开扫描得到的地址
////				Intent intent = new Intent();
////				intent.setAction("android.intent.action.VIEW");
////				Uri content_url = Uri.parse(obj.getText());
////				intent.setData(content_url);
////				startActivity(intent);
//				finish();
//			}
//		});
//		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				finish();
//			}
//		});
//		dialog.create().show();
		
		Intent intent = new Intent();
		intent.setClass(this, BarCodeActivity.class);
//		Bundle bundle = new Bundle();
		intent.putExtra("obj", resObj);
//		bundle.put
//		intent.putExtras(bundle);

		startActivity(intent);
		finish();
		
	
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			}
			catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	public void runCheckCode(Result obj, Bitmap barcode){
		map = barcode;
		res = obj;
		checkCode = new CheckCode();
		checkCode.execute(obj.getText());
	}
	
	public class CheckCode extends AsyncTask<String, Void, String>{  
        @Override  
        protected String doInBackground(String... params) { 

             return MaStation.getInstance().getMaWebService().getWhGoodsDtoByBarCode(params[0]);

//    		return good;
//            nList =JSON.parseArray(info.getData().toString(), JzxHwzwModifyDTO.class);;  
        }

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("正在提交,请稍候...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialogInterface) {
					if (checkCode != null) {
						checkCode.cancel(true);
					}
				}
			});
			progressDialog.show();
		}

        @Override  
        protected void onPostExecute(String result){
			try{
				if(isNetworkConnected()){
					if(result != null && !result.equals("[]") && !"".equals(result)){
						if("cache".equals(result)){
							ByDialog.showMessage(context, "网络断开，操作已缓存！");
						}else{
							StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
							if(info != null && !info.equals("[]") && info.getData() != null){
								resObj = JSON.parseObject(info.getData().toString(), WhGoodsDTO.class);
							}
							handleDecode(res, map);
						}
					} else{
						ByDialog.showMessage(context, "网络异常");
					}
				}
				if(progressDialog != null){
					progressDialog.cancel();
				}
			}catch (Exception e){
				MaStation.getInstance().recordExc(e);
			}
		}
    }  
	
	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

}