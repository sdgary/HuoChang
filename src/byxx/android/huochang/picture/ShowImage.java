package byxx.android.huochang.picture;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.picture.ImageAdapter.ViewHolder;
import byxx.android.huochang.task.ZydDto;
import byxx.android.huochang.util.ByDialog;

public class ShowImage  extends BaseActivity {
    private GridView gridView;  
    private ViewSwitcher mViewSwitcher;
    private ImageShowAdapter imgAdapter;  
    private Button goback;
	Context context;
	public ProgressDialog progressDialog;
	DisplayMetrics dm;
	List<String> nList = null;
	String xh ;
	String yd;
	String photoName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.showimg);
		this.context = this;
		this.initView();
		this.initData();
		AppManager.getAppManager().addActivity(this);
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// 获取分辨率
		mViewSwitcher = (ViewSwitcher) findViewById(R.id.imagedialog_view_switcher);
		mViewSwitcher.setOnTouchListener(touchListener);
		gridView =(GridView)findViewById(R.id.gridView1);
		goback = (Button)findViewById(R.id.goback_bt);
		xh = getIntent().getStringExtra("xh");
		yd = getIntent().getStringExtra("yd");
		photoName = getIntent().getStringExtra("photoName");
		if(xh != null && yd != null){
		ImgInfoDownTask task = new ImgInfoDownTask();
		task.execute(xh,yd);
		}
		if(photoName != null){
			ImgInfoDownNormalPhotos task = new ImgInfoDownNormalPhotos();
			task.execute(photoName);
		}
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		imgAdapter = new ImageShowAdapter(context);
		gridView.setAdapter(imgAdapter);
		 gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
	            @Override  
	            public void onItemClick(AdapterView<?> parent, View view,  
	                    int position, long id) {
	            	
	                String loadimg = nList.get(position).toString();  
//	                ViewHolder holder = (ViewHolder)view.getTag();  
	            		Intent intent = new Intent();
	            		intent.setClass(context, ImageDialog.class);
	            		intent.putExtra("img_url", loadimg.toString());
	            		if(photoName != null){
	            			intent.putExtra("photoName", photoName);
	            		}
	            		startActivityForResult(intent,PictureUtil.RESULTISDEL1);


	            
	        }
		 });
		 
		goback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});	
		 
	}

	private class ImgInfoDownTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			String jzxNum = params[0];
			String ydNum = params[1];
			return "";
//					needcall		MaStation.getInstance().getMaWebService().getJzxPhotos(jzxNum,ydNum);//helper.sendPost(json);// 使用http post
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("正在加载数据,请稍候...");
			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(progressDialog != null){
				progressDialog.dismiss();
			}
			try{
				if(result != null && !"".equals(result)){
					if("cache".equals(result)){
						ByDialog.showMessage(context, "网络断开，操作已缓存！");
					}else{
						GsonBuilder gb = new GsonBuilder();
						gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
						Gson gson = gb.create();
//				LoadImageShow show = new LoadImageShow();
//				List<LoadImageShow> showlist= new ArrayList<LoadImageShow>();
//				show.setImgtxt("test");
//				show.setImgurl("http://10.167.97.14:7001/hyzx_ws/img/1");
//				for(int i= 0; i<10 ;i++){
//					showlist.add(show);
//				}
//				String tsets =gson.toJson(showlist);
						nList = gson.fromJson(result, new TypeToken<LinkedList<String>>(){}.getType());

						if(nList != null){
							imgAdapter.addAllPhoto(nList);
							mViewSwitcher.showNext();
						}else{
							if(progressDialog != null){
								progressDialog.dismiss();
							}

						}
					}

				}else{
					ByDialog.showMessage(context, "网络异常");
				}
			}catch(Exception e){
				if(progressDialog != null){
					progressDialog.dismiss();
				}
			}
		}
		
	}
	private class ImgInfoDownNormalPhotos extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			String photoName = params[0];
			return MaStation.getInstance().getMaWebService().getNormalPhotos(photoName);//helper.sendPost(json);// 使用http post
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("正在加载数据,请稍候...");
			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(progressDialog != null){
				progressDialog.dismiss();
			}
			try{
				if(result != null && !"".equals(result)){
					if("cache".equals(result)){
						ByDialog.showMessage(context, "网络断开，操作已缓存！");
					}else{
						GsonBuilder gb = new GsonBuilder();
						gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
						Gson gson = gb.create();
						nList = gson.fromJson(result, new TypeToken<LinkedList<String>>(){}.getType());

						if(nList != null){
							imgAdapter.addAllPhoto(nList);
							mViewSwitcher.showNext();
						}else{
							if(progressDialog != null){
								progressDialog.dismiss();
							}
						}
					}

				}else{
					ByDialog.showMessage(context, "网络异常");
				}
			}catch(Exception e){
				if(progressDialog != null){
					progressDialog.dismiss();
				}
			}
		}
		
	}
	
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if(keyCode == KeyEvent.KEYCODE_BACK){    //点击返回按键  
    		if(progressDialog != null){
				progressDialog.dismiss();
			}
			finish();
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    }  

    
	private View.OnTouchListener touchListener = new View.OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
//			thread.interrupt();
//			handler = null;
			finish();
			return true;
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

	if(requestCode == PictureUtil.RESULTISDEL1 && resultCode == RESULT_OK){

			if(data != null && !data.equals("")){
			String re= data.getStringExtra("ISDEL");
			if(re != null && re.equals("true")){
				String imgid = data.getStringExtra("URLimgID");
				String pn = data.getStringExtra("photoName");
//				if(imgid != null && pn == null ){ // 代码逻辑有问题
//					delteImgTask task = new delteImgTask();
//					task.execute(xh,yd,imgid);
//				}
				if(imgid != null && pn != null ){
					DelNormalPhoto dnp = new DelNormalPhoto();
					dnp.execute(pn,imgid);
				}
				}
			
			}
		

		
	}
	}
	
private class delteImgTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			String jzxNum = params[0];
			String ydNum =  params[1];
			String imgid = params[2];
			return "";
//					needcall		MaStation.getInstance().getMaWebService().delJzxPhoto(jzxNum,ydNum,imgid);//helper.sendPost(json);// 使用http post
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("正在加载数据,请稍候...");
			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(progressDialog != null){
				progressDialog.dismiss();
			}
			try{
				if(result != null && !"".equals(result)){
					if("cache".equals(result)){
						ByDialog.showMessage(context, "网络断开，操作已缓存！");
					}else{
						GsonBuilder gb = new GsonBuilder();
						gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
						Gson gson = gb.create();

						nList = gson.fromJson(result, new TypeToken<LinkedList<String>>(){}.getType());

						if(nList != null){
							imgAdapter.addAllPhoto(nList);

						}else{
							if(progressDialog != null){
								progressDialog.dismiss();
							}

						}
					}

				}else {
					ByDialog.showMessage(context, "网络异常");
				}
			}catch(Exception e){
				if(progressDialog != null){
					progressDialog.dismiss();
				}
			}
		}
		
	}
private class DelNormalPhoto extends AsyncTask<String, Void, String> {
	
	@Override
	protected String doInBackground(String... params) {
		String photoName = params[0];
		String imgid = params[1];
		return MaStation.getInstance().getMaWebService().delNormalPhoto(photoName, imgid);
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("正在加载数据,请稍候...");
		progressDialog.show();
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(progressDialog != null){
			progressDialog.dismiss();
		}
		try{
			if(result != null && !"".equals(result)){
				if("cache".equals(result)){
					ByDialog.showMessage(context, "网络断开，操作已缓存！");
				}else{
					GsonBuilder gb = new GsonBuilder();
					gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
					Gson gson = gb.create();
					nList = gson.fromJson(result, new TypeToken<LinkedList<String>>(){}.getType());
					if(nList != null){
						imgAdapter.addAllPhoto(nList);
					}else{
						if(progressDialog != null){
							progressDialog.dismiss();
						}
					}
				}
			}else{
				ByDialog.showMessage(context, "网络异常");
			}
		}catch(Exception e){
			if(progressDialog != null){
				progressDialog.dismiss();
			}
		}
	}
	
}
	
}
