package byxx.android.huochang.checkBox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import byxx.android.huochang.R;
import byxx.android.huochang.util.ByString;

/**
 * 代替系统原生的camera
 */
public class CameraMore extends Activity implements SurfaceHolder.Callback {

	private Context mContext;
	private Button exitBtn;
	private Camera mCamera;
	private ImageView mButton;
	private SurfaceView mSurfaceView;
	private SurfaceHolder holder;
	private AutoFocusCallback mAutoFocusCallback = new AutoFocusCallback();
	private ImageView sendImageIv;
	private static String strCaptureFilePath = Environment
			.getExternalStorageDirectory() + "/DCIM/Camera/";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		/* 隐藏状态栏 */
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/* 隐藏标题栏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* 设定屏幕显示为横向 */
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.photograph_layout);
		/* SurfaceHolder设置 */
		mSurfaceView = (SurfaceView) findViewById(R.id.mSurfaceView);
		holder = mSurfaceView.getHolder();
		holder.addCallback(CameraMore.this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
				// TODO Auto-generated method stub
//				mCamera.autoFocus(mAutoFocusCallback);
		/* 设置拍照Button的OnClick事件处理 */
		mButton = (ImageView) findViewById(R.id.myButton);
		mButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				//自动对焦先，再拍照
				mCamera.autoFocus(mAutoFocusCallback);
				System.out.println("完成照相功能！");
			}
		});
		exitBtn = (Button) findViewById(R.id.exitBtn);
		exitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});


	}
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			mCamera.autoFocus(mAutoFocusCallback);
			super.handleMessage(msg);
		}
	};
	public void surfaceCreated(SurfaceHolder surfaceholder) {
		try {
			/* 打开相机， */
			System.out.println("打开照相功能！");
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int format, int w,
			int h) {
		/* 相机初始化 */
		initCamera();
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		stopCamera();
		mCamera.release();
		mCamera = null;
	}

	/* 拍照方法 */
	private void takePicture() {
		if (mCamera != null) {
			mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
			System.out.println("this is takePicture()");
		}else{

		}
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {

		System.out.println("intent=" + intent);
		System.out.println("requestCode=" + requestCode);

		super.startActivityForResult(intent, requestCode);

	}

	//快门反馈
	private ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			System.out.println("this is onShtter");
		}
	};
	//照片反馈
	private PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] _data, Camera _camera) {
			System.out.println("this is onPictureTaken");
		}
	};
	//照片反馈
	private PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] _data, Camera _camera) {
			System.out.println("this is onJPGTaken");
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {// 判断SD卡是否存在，并且可以可以读写
			} else {
				Toast.makeText(CameraMore.this, "SD卡不存在或写保护",
						Toast.LENGTH_LONG).show();
			}
			try {
				/* 取得相片 */
				Bitmap bm = BitmapFactory.decodeByteArray(_data, 0,
						_data.length);
				System.out.println("-------相片----"+bm);
				String state = Environment.getExternalStorageState();
				if (state.equals(Environment.MEDIA_MOUNTED)) {
					String photoname = null;//相片名称
					photoname = "Byxx_MoreCamera_"+ ByString.getTimeStr(System.currentTimeMillis(), "yyyyMMddHHmmss")
					+ ".jpg";
					//存相片方法，如果存储时间长，可用线程队列或线程池，个人觉得反应在1s左右，纯属正常反应范围
					saveFile(bm, photoname, strCaptureFilePath);
				} else {
					Toast.makeText(CameraMore.this, "请插入SD卡", Toast.LENGTH_LONG).show();
				}
				stopCamera();
				initCamera();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	AlertDialog.Builder b;

	/**
	 * 转换时间
	 *
	 * @param d
	 * @return
	 */
	public String formatTimer(int d) {
		return d >= 10 ? "" + d : "0" + d;
	}

	/* 自定义class AutoFocusCallback */
	public final class AutoFocusCallback implements
			Camera.AutoFocusCallback {
		public void onAutoFocus(boolean focused, Camera camera) {
			/* 对到焦点再拍照 */
			if (Counter.getCounter().getCount()) {
				if(focused){
					takePicture();
				}
			}else if(!Counter.getCounter().getCount()){
				finish();
			}
		}
	};

	/* 相机初始化的method */
	private void initCamera() {
		if (mCamera != null) {
			try {
				Camera.Parameters parameters = mCamera.getParameters();
				/*
				 * 设定相片大小为1024*768， 格式为JPG
				 */
				parameters.setPictureFormat(PixelFormat.JPEG);
//				parameters.setPictureSize(1024, 768);
				/**
				 * 这里老有问题，先注释，以后有时间可以研究下
				 */
//				mCamera.setParameters(parameters);
				/*  初始化计数器 */

				/* 打开预览画面 */
				mCamera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* 停止相机的method */
	private void stopCamera() {
		if (mCamera != null) {
			try {
				/* 停止预览 */
				mCamera.stopPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 存图片文件到本地
	 * @param bm
	 * @param fileName 文件名要带格式
	 * @param path
	 * @throws IOException
	 */
	public void saveFile(Bitmap bm, String fileName, String path) throws IOException {
		String subForder = path;
		File foder = new File(subForder);
		if (!foder.exists()) {
			foder.mkdirs();
		}
		File myCaptureFile = new File(subForder, fileName);
		if (!myCaptureFile.exists()) {
			myCaptureFile.createNewFile();
		}
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);//与文件名格式对应
		bos.flush();
		bos.close();

		//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到新保存的图片了！
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(myCaptureFile);
		intent.setData(uri);
		mContext.sendBroadcast(intent);
	}

}


