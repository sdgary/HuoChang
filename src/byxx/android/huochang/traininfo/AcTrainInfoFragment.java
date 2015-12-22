package byxx.android.huochang.traininfo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.picture.LoadImage;
import byxx.android.huochang.picture.PictureUtil;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 当前fragment
 * @author WGary
 *
 */
public class AcTrainInfoFragment extends Fragment{
	public static final int PHOTO_TYPE_IMPORTANT = 11; // 重点车
	public static final int PHOTO_TYPE_BROKEN = 12; // 坏车
	public static final int PHOTO_TYPE_MEETING = 13; // 车前会
	PullToRefreshListView listView;
	List<CarDto> itemList = new ArrayList<CarDto>();
	TextView tvTime,tvState;
	String tabnum ;//单个标头名
	BaTrainInfo baTrainInfo;
	Handler handler;
	Context mcontext ;
	ListView lv = null;
	Button buReturnHome;
//	Button refresh;
	ProgressDialog dialog = null;
	RuTrainInfo ruTrainInfo ;
	boolean intRunType = true;

//	public ProgressDialog getProgressDialog() {
//		if(progressDialog == null){
//		}
//		return progressDialog;
//	}

	ProgressDialog progressDialog;
	int type ;
	String valueid = null ;
	AlertDialog.Builder  dialogshow = null;
	BaTrainInfo listAdapter;
	String SD_CARD_TEMP_DIR;
	String pgname = null;
	private Bitmap photo;
	static String savePathString = Environment.getExternalStorageDirectory()+"/"+"DCIM"+"/"+"100ANDRO/"; //Environment.getExternalStorageDirectory()+"/"+"DCIM"+"/"+"Camera/";
	static final int CAMERACODE2 = 12;
	static final int CAMERACODE1 = 11;
	FileUploadTask fileUploadTask;
	
    private static final String KEY_CONTENT = "AcTrainInfoFragment:Content";
    
	 public static AcTrainInfoFragment newInstance(final String content) {
		 AcTrainInfoFragment fragment = new AcTrainInfoFragment();
		 	fragment.tabnum = content;
		 	fragment.RefreshListView();
	        return fragment;
	    }
	 
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent intent){
		 super.onActivityResult(requestCode, resultCode, intent);
		 switch (requestCode) {
			case CAMERACODE1:
			  try {
				   File f = new File(SD_CARD_TEMP_DIR);
				   save(SD_CARD_TEMP_DIR);
			  }catch (Exception e) {

			  }
			break;
			default:
				break;
			}
	 }
	 
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         return inflater.inflate(R.layout.traininfo_tab, container, false);
    }
	 	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	mcontext = getView().getContext();
		tvState = (TextView)getView().findViewById(R.id.tvstate);
		tvTime = (TextView)getView().findViewById(R.id.tvtime);		
		listView = (PullToRefreshListView) getView().findViewById(R.id.lv_traininfo);
//			refresh = (Button)getView().findViewById(R.id.refresh);
//			listView.setAdapter(getBaTrainInfo());
		listAdapter = new BaTrainInfo();
		listAdapter.setHandler(getHandler());
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
//				Toast.makeText(mcontext, "是不是重点？是就拍照，再派班。不是直接派班", Toast.LENGTH_LONG).show();
			}
			
		});
		
		listView.setOnPullEventListener(new OnPullEventListener<ListView>() {
		

		@Override
		public void onPullEvent(PullToRefreshBase<ListView> refreshView,
				State state, Mode direction) {
			// TODO Auto-generated method stub
			if (state.equals(State.PULL_TO_REFRESH)) {
				refreshView.getLoadingLayoutProxy().setPullLabel(getString(R.string.listview_empty));
				refreshView.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.listview_title));
				refreshView.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.listview_refesh));
				String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.listview_refeshTime)+":"+label);
			}
		}
		});
		
		
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				progressDialog = new ProgressDialog(mcontext);
				progressDialog.setMessage("正在加载数据,请稍候...");
				RefreshListView();
				progressDialog.show();
			}
		});
		
    }
    
    public void RefreshListView(){
    	getRuTrainInfo().setType(RuTrainInfo.REAL_ALL_TRAININFO);
    	getRuTrainInfo().setGDM(tabnum);
		Log.v("dl", tabnum+"");
    	getRuTrainInfo().start();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, tabnum);
    }
	
//		@Override
//		public void onResume() {
//			// TODO Auto-generated method stub
//			super.onResume();
//			run = true;
//			getHandler().postDelayed(task,0);
//		} 
//		
//		@Override
//		public void onPause() {
//			// TODO Auto-generated method stub
//			super.onPause();
//			run =false;
//			getHandler().post(task);
//			progressDialog.dismiss();
//			getHandler().removeCallbacks(task);
//		}
	
	public BaTrainInfo getBaTrainInfo() {
		if(baTrainInfo == null){
			baTrainInfo = new BaTrainInfo();
			baTrainInfo.setHandler(getHandler());
		}
		return baTrainInfo;
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

	private void dealHandleMessage(Message msg) {
		try {
			if (msg == null)
				return;
			int dId = 0; // 对话框的标识
			boolean closeDialog = true;
			try {
				// 读取完后设置数据
			Bundle bundle = msg.getData();
			switch (msg.what) {
			case Constant.MSG_ID_READ_TRAININFO:
				dId = 0;
				boolean ballreal = bundle.getBoolean("realalltraininfo");
				tvState.setText("");
				tvState.setTextSize(24);
				if(isNetworkConnected()|| MaStation.getInstance().isTest()){
				if (ballreal) {
					tvTime.setText(ByString.getTimeStr(System.currentTimeMillis(), "dd日 HH:mm"));
					
					showData();
				}else{
					showData();
					tvState.setTextColor(Color.WHITE);
					tvState.setTextSize(30);
					tvState.setText("暂无数据");
				}
				}else{
					tvState.setTextSize(30);
					tvState.setTextColor(Color.RED);
					tvState.setText("网络错误,请注意");
				}
				
				ruTrainInfo.close();
				if(progressDialog != null){
					progressDialog.cancel();
				}
				break;
			case Constant.MSG_ID_MSB_CAMERA_R:
				camra();//？拍照逻辑
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (closeDialog && dialog != null && dialog.isShowing()) {
		}
		} catch (Exception e) {
		}
		return;
	}
	
	private void showData() {
		try {
			List<CarDto> nListShow = MaStation.getInstance().getMoTrainInfo().getTrainInfo();
			listAdapter.setDatas(nListShow);
			listAdapter.notifyDataSetChanged();
			listView.onRefreshComplete();  
		} catch (Exception e) {
//				MaStation.getInstance().recordExc(e);
		}
	}
	
	private void destoryBimap() {    
        if (photo != null && !photo.isRecycled()) {    
            photo.recycle();    
            photo = null;    
        }    
      }
	
	//?拍照界面
	public void camra(){
		destoryBimap();  
        String state = Environment.getExternalStorageState();  
        if (state.equals(Environment.MEDIA_MOUNTED)) {  

        pgname = null;
        pgname = "DL_"+ByString.getTimeStr(java.lang.System.currentTimeMillis(), "yyyyMMddHHmmss");
        pgname += ".jpg";
        SD_CARD_TEMP_DIR =  savePathString+ File.separator + pgname;
            	  Intent takePictureFromCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	  takePictureFromCameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
            	  takePictureFromCameraIntent.putExtra( android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SD_CARD_TEMP_DIR)));
            	  startActivityForResult(takePictureFromCameraIntent, CAMERACODE1);    
         } else {  
            Toast.makeText(mcontext,"请插入SD卡", Toast.LENGTH_LONG).show();
        }  
	}
	
	
	public RuTrainInfo getRuTrainInfo() {
		if (ruTrainInfo == null) {
			ruTrainInfo= new RuTrainInfo(getHandler());
			
		}
		return ruTrainInfo;
	}
	
  private Runnable task = new Runnable() {  
//		      int count = 0;
        public void run() {  
        	getRuTrainInfo().setType(RuTrainInfo.REAL_ALL_TRAININFO);
        	getRuTrainInfo().setGDM(tabnum);
    		Log.v("dl", tabnum+"");
        	getRuTrainInfo().start();
			progressDialog.show();
        }
    };

	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager)mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	private void save(String mCurrentPhotoPath) {
		if (mCurrentPhotoPath != null) {
			try {
				File f = new File(mCurrentPhotoPath);
				Bitmap bm = PictureUtil.getSmallBitmap(mCurrentPhotoPath);
				 LoadImage loadImage = new LoadImage(f.getPath(),bm);
//						 imgAdapter1.addPhoto(loadImage);
//		                 fileNameList1.add(loadImage);  		
				FileOutputStream fos = new FileOutputStream(f);
				bm.compress(Bitmap.CompressFormat.JPEG, 20, fos);
//				Toast.makeText(mcontext, "图片保存成功", Toast.LENGTH_SHORT).show();
				 if(bm!=null&&bm.isRecycled())
			       {
			          bm.recycle();
			       }
//							photoName的产生方法是勾计划正文的主键,gjhzwDto.getGjhId+"@"+gjhzwDto.getSwh
				 upload(loadImage.getFileName());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			Toast.makeText(mcontext, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 上传到服务器
	 */
	private void upload(String mCurrentPhotoPath) {

		if (mCurrentPhotoPath != null) {
			fileUploadTask = new FileUploadTask();
			fileUploadTask.execute(mCurrentPhotoPath);
		}else {
			Toast.makeText(mcontext, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
		}
	}
	
	private class FileUploadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			String filePath = params[0];
			String photoName ="";
			String content;
			
			content = PictureUtil.bitmapToString(filePath);
			Gson gson = new Gson();
			String json = gson.toJson(content);
			System.out.println(json);
//				public static final int PHOTO_TYPE_IMPORTANT = 11; // 重点车
//				public static final int PHOTO_TYPE_BROKEN = 12; // 坏车
//				public static final int PHOTO_TYPE_MEETING = 13; // 车前会
			photoName =  "trainInfo" + "@" +System.currentTimeMillis();
			return MaStation.getInstance().getMaWebService().saveTypeImg(photoName,getBaTrainInfo().uploadImgState,json);//helper.sendPost(json);// 使用http post
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(mcontext);
			progressDialog.setMessage("正在提交,请稍候...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialogInterface) {
					if (fileUploadTask != null) {
						fileUploadTask.cancel(true);
					}
				}
			});
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			try{
				if("".equals(result)){
					ByDialog.showMessage(mcontext, "网络异常");
				}else if("cache".equals(result)){
					ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
				}else {
//				Toast.makeText(mcontext, result, Toast.LENGTH_SHORT).show();
					if(result != null){
						StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
						if(info != null){
							if(info.isSuccess()){
								ByDialog.showMessage(mcontext, "提交成功！");
							}else{
								ByDialog.showMessage(mcontext, "提交失败：" + info.getError());
							}
						}
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
		    
}
