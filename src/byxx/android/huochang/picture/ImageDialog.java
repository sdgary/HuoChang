package byxx.android.huochang.picture;

import java.io.File;
import java.io.IOException;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.common.ApiClient;
import byxx.android.huochang.common.FileUtils;
import byxx.android.huochang.common.ImageUtils;
import byxx.android.huochang.common.StringUtils;
import byxx.android.huochang.common.UIHelper;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * 图片对话框
 * 
 * @author Way
 * @version 1.0
 */
public class ImageDialog extends BaseActivity {

	private ViewSwitcher mViewSwitcher;
	private Button btn_back;
	private Button btn_del;
	private ImageView mImage;

	private Thread thread;
	private Handler handler;
	private Bitmap bitmap, zoomedBMP;
	private static final double ZOOM_IN_SCALE = 1.25;// 放大系数
	private static final double ZOOM_OUT_SCALE = 0.8;// 缩小系数

	float minScaleR;// 最小缩放比例
	static final float MAX_SCALE = 4f;// 最大缩放比例
	
	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	PointF start = new PointF();
	PointF mid = new PointF();
	DisplayMetrics dm;
	
	// We can be in one of these 3 states
	static final int NONE = 0;// 初始状态
	static final int DRAG = 1;// 拖动
	static final int ZOOM = 2;// 缩放
	int mode = NONE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_dialog);

		this.initView();

		this.initData();
		AppManager.getAppManager().addActivity(this);
	}

	private View.OnTouchListener touchListener = new View.OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
//			thread.interrupt();
//			handler = null;
//			finish();
			return true;
		}
	};

	private void initView() {
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// 获取分辨率
		mViewSwitcher = (ViewSwitcher) findViewById(R.id.imagedialog_view_switcher);
		mViewSwitcher.setOnTouchListener(touchListener);
		btn_back = (Button)findViewById(R.id.imagedialog_back_button);
		btn_del = (Button)findViewById(R.id.imagedialog_delelt_button);
		btn_del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(ImageDialog.this);
				builder.setMessage("确认删除该图片吗?");
				builder.setCancelable(false);
				builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {// which=-1
						Intent intent=new Intent();
					    intent.putExtra("ISDEL", "true");
					    intent.putExtra("localImg", localImg);
					    intent.putExtra("URLimgID", imgURLID);
					    if(photoName != null){
					    	intent.putExtra("photoName", photoName);
					    }
					    setResult(RESULT_OK, intent);
					    finish();
					}
				});
				builder.setNegativeButton("取消",
						new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {// which=-2

					}
				});
				builder.create().show();
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thread.interrupt();
				handler = null;
				Intent intent=new Intent();
			    intent.putExtra("ISDEL", "false");
			    intent.putExtra("localImg", localImg);
			    setResult(RESULT_OK, intent);
				finish();
			}
		});
//		btn_preview = (Button) findViewById(R.id.imagedialog_preview_button);
//		btn_preview.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				String imgURL = getIntent().getStringExtra("img_url");
//				UIHelper.showImageZoomDialog(v.getContext(), imgURL);
//				finish();
//			}
//		});

		mImage = (ImageView) findViewById(R.id.imagedialog_image);
		mImage.setOnTouchListener(touchListener);
	}
	String localImg = null;
	String imgURLID = null;
	String photoName = null;
	private void initData() {
		imgURLID = getIntent().getStringExtra("img_url");
		photoName = getIntent().getStringExtra("photoName");
		final String ErrMsg = "加载失败";//getString(R.string.msg_load_image_fail);
		localImg = getIntent().getStringExtra("local_img");
		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1 && msg.obj != null) {
//					mImage.setImageBitmap((Bitmap) msg.obj);
//					mViewSwitcher.showNext();
					bitmap = (Bitmap) msg.obj;
					mImage.setImageBitmap(bitmap);
					minZoom();// 计算最小缩放比
					CheckView();// 设置图像居中
					mImage.setImageMatrix(matrix);
					mViewSwitcher.showNext();
				} else {
					UIHelper.ToastMessage(ImageDialog.this, ErrMsg);
					Intent intent=new Intent();
				    intent.putExtra("ISDEL", "false");
				    intent.putExtra("localImg", localImg);
				    setResult(RESULT_OK, intent);
					finish();
				}
			}
		};
		thread = new Thread() {
			public void run() {
				Message msg = new Message();
				Bitmap bmp = null;
				try {
				
				if (!StringUtils.isEmpty(localImg)) {
					try {
						bmp = BitmapFactory.decodeFile(localImg);
//						btn_del.setVisibility(View.VISIBLE);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						msg.what = -1;
						msg.obj = e;
					}
					msg.what = 1;
					msg.obj = bmp;

				}
				
				String filename = FileUtils.getFileName(imgURLID);
				if(imgURLID != null){
				try {

//					// 读取本地图片
//					if (imgURLID.endsWith("portrait.gif")
//							|| StringUtils.isEmpty(imgURLID)) {
//						bmp = BitmapFactory.decodeResource(
//								mImage.getResources(), R.drawable.widget_dface);
//					}
//					if (bmp == null) {
//						// 是否有缓存图片
//						// Environment.getExternalStorageDirectory();返回/sdcard
//						String filepath = getFilesDir() + File.separator
//								+ filename;
//						File file = new File(filepath);
//						if (file.exists()) {
//							bmp = ImageUtils.getBitmap(mImage.getContext(),
//									filename);
//							if (bmp != null) {
//								// 缩放图片
//								bmp = ImageUtils.reDrawBitMap(ImageDialog.this,
//										bmp);
//							}
//						}
//					}
					if (bmp == null) {
						bmp = ApiClient.getNetBitmap(MaStation.getInstance().getUser().getImgUrl()+imgURLID,ImageDialog.this);
						if (bmp != null) {
							try {
								// 写图片缓存
								ImageUtils.saveImage(mImage.getContext(),
										filename, bmp);
							} catch (IOException e) {
								e.printStackTrace();
							}
							// 缩放图片
							bmp = ImageUtils
									.reDrawBitMap(ImageDialog.this, bmp);
						}
					}
					msg.what = 1;
					msg.obj = bmp;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}//end try
				}//end if
				if (handler != null && !isInterrupted()){
					handler.sendMessage(msg);
				}
			}catch(Exception e){
				e.printStackTrace();
				msg.what = -1;
				msg.obj = e;
			}

			}
		};
		thread.start();
	}
	
	
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if(keyCode == KeyEvent.KEYCODE_BACK){    //点击返回按键  
			thread.interrupt();
			handler = null;
			Intent intent=new Intent();
		    intent.putExtra("ISDEL", "false");
		    setResult(RESULT_OK, intent);
			finish();
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    }  
    
    
	/**
	 * 最小缩放比例，最大为100%
	 */
	private void minZoom() {
		// minScaleR = Math.min(
		// (float) dm.widthPixels / (float) bitmap.getWidth(),
		// (float) dm.heightPixels / (float) bitmap.getHeight());
		if (bitmap.getWidth() >= dm.widthPixels)
			minScaleR = ((float) dm.widthPixels) / bitmap.getWidth();
		else
			minScaleR = 1.0f;

		if (minScaleR < 1.0) {
			matrix.postScale(minScaleR, minScaleR);
		}
	}

	private void center() {
		center(true, true);
	}
	
	/**
	 * 限制最大最小缩放比例，自动居中
	 */
	private void CheckView() {
		float p[] = new float[9];
		matrix.getValues(p);
		if (mode == ZOOM) {
			if (p[0] < minScaleR) {
				matrix.setScale(minScaleR, minScaleR);
			}
			if (p[0] > MAX_SCALE) {
				matrix.set(savedMatrix);
			}
		}
		center();
	}
	
	/**
	 * 横向、纵向居中
	 */
	protected void center(boolean horizontal, boolean vertical) {
		Matrix m = new Matrix();
		m.set(matrix);
		RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {
			// 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
			int screenHeight = dm.heightPixels;
			if (height < screenHeight) {
				deltaY = (screenHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < screenHeight) {
				deltaY = mImage.getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int screenWidth = dm.widthPixels;
			if (width < screenWidth) {
				deltaX = (screenWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < screenWidth) {
				deltaX = screenWidth - rect.right;
			}
		}
		matrix.postTranslate(deltaX, deltaY);
	}
}
