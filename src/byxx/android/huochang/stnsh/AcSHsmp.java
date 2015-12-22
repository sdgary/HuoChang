package byxx.android.huochang.stnsh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import byxx.android.huochang.AppContext;
import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.common.UIHelper;
import byxx.android.huochang.dw.AcJzx;
import byxx.android.huochang.ftp.SeUploading;
import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.picture.ImageAdapter;

import byxx.android.huochang.picture.ImageAdapter.ViewHolder;
import byxx.android.huochang.picture.AcPicture;
import byxx.android.huochang.picture.FileBean;
import byxx.android.huochang.picture.ImageDialog;
import byxx.android.huochang.picture.LoadImage;
import byxx.android.huochang.picture.MessageHelper;
import byxx.android.huochang.picture.PictureUtil;
import byxx.android.huochang.picture.ShowImage;
import byxx.android.huochang.picture.VideoUtil;
import byxx.android.huochang.picture.Zipper;
import byxx.android.huochang.task.AcTask;
import byxx.android.huochang.traininfo.CarDto;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;

public class AcSHsmp extends BaseActivity{
	EditText etjzxh1;
	Button btnUpdating1;
	String SD_CARD_TEMP_DIR;
	String pgname = null;
    private Spinner spinner;  
    private ArrayAdapter<String> adapter; 
	public Context context;
	private Bitmap photo;
	ProgressDialog dialog = null;
	static String savePathString = Environment.getExternalStorageDirectory()+"/"+"DCIM"+"/"+"100ANDRO/"; //Environment.getExternalStorageDirectory()+"/"+"DCIM"+"/"+"Camera/";
//	static final int CAMERACODE2 = 77220012;
	static final int CAMERACODE1 = 11;
	FreightDTO value;
	String jzxnum = null;
	Stack<Integer> pht1;
	int size1 = 0;
//    private static final String[] m={"CRCTQ00","CRCTQ0A"};  
    ImageButton imgbtn1,imgbtn1_del;
//    LinearLayout xh1LL;
//    LinearLayout xh2LL;
    Button btn_update1;
    Button btn_Reset;
	public ProgressDialog progressDialog;
    public List<String> getJzxnum;
    private GridView gridView1;  
    private ImageAdapter imgAdapter1;  
    private Button xh1_bt_img_query;
//    private Button xh2_bt_img_query;
//    private ImageView imgview_x1_1;
    RelativeLayout xh_show_img;
    RelativeLayout rl_jzxh1;
    TextView state;
//    private TextView seclectNumView;  
//    private Button deleteButton;  
      
    private List<LoadImage> fileNameList1 = new ArrayList<LoadImage>();   //保存Adapter中显示的图片详情(要跟adapter里面的List要对应)  
//    private List<LoadImage> fileNameList2 = new ArrayList<LoadImage>();     //保存Adapter中显示的图片详情(要跟adapter里面的List要对应)  
    private List<LoadImage> selectFileLs1 = new ArrayList<LoadImage>();      //保存选中的图片信息  
//    private List<LoadImage> selectFileLs2 = new ArrayList<LoadImage>();      //保存选中的图片信息  
    private boolean isDbClick1= false;   //是否正在长按状态  
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stnshsmp);
		setTitle("大朗综合指挥系统 - 收货");
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		value = new FreightDTO();
		//CRCTQ00
		//CRCTQ0A
		context = this;

		etjzxh1 = (EditText)findViewById(R.id.et_jzxh1);

		state = (TextView)findViewById(R.id.state);

		btnUpdating1 = (Button)findViewById(R.id.button1);
		btn_Reset = (Button)findViewById(R.id.btnreset);
		imgbtn1 = (ImageButton)findViewById(R.id.imgbtn1_add);
		imgbtn1_del = (ImageButton)findViewById(R.id.imgbtn1_del);

		
	    xh1_bt_img_query = (Button)findViewById(R.id.img_query_1);

	    xh_show_img = (RelativeLayout)findViewById(R.id.xh1_img_show);

		gridView1 = (GridView)findViewById(R.id.picture_grid_1); 
 
		rl_jzxh1 = (RelativeLayout)findViewById(R.id.rl_jzxh1);

		pht1 = new Stack<Integer>();

		imgAdapter1 = new ImageAdapter(context);

		gridView1.setAdapter(imgAdapter1);  

		imgbtn1_del.setOnClickListener(delClickListener1);

	    gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
	            @Override  
	            public void onItemClick(AdapterView<?> parent, View view,  
	                    int position, long id) {
	            	
	                LoadImage loadimg = fileNameList1.get(position);  
	                ViewHolder holder = (ViewHolder)view.getTag();  
	                if(isDbClick1){  
	                    if(selectFileLs1.contains(loadimg)){  
	                        holder.image1.setImageDrawable(null);   
	                        holder.image2.setVisibility(View.GONE);  
	                        imgAdapter1.delNumber(position+"");
	                        
	                        selectFileLs1.remove(loadimg);  
	                    }else{  
//	                        holder.image1.setImageResource(R.drawable.border);    //添加图片(带边框的透明图片)[主要目的就是让该图片带边框]  
	                        holder.image2.setVisibility(View.VISIBLE);  //设置图片右上角的对号显示  
	                        imgAdapter1.addNumber(position+"");    //把该图片添加到adapter的选中状态，防止滚动后就没有在选中状态了。  
	                        selectFileLs1.add(loadimg);  
	                    }  
//	                    seclectNumView.setText("选中"+selectFileLs.size()+"张图片");  
	                }else{  
//	                    startActivity(new Intent(PictureScanAct.this, PictureViewAct.class).putExtra("flag","upload").putExtra("imagePath",loadimg.getFileName()));  
//	                	UIHelper.showImageDialog(context, loadimg.getFileName());
	            		Intent intent = new Intent();
	            		intent.setClass(context, ImageDialog.class);
	            		intent.putExtra("local_img", loadimg.getFileName());
	            		startActivityForResult(intent,PictureUtil.RESULTISDEL1);
//	            		java.lang.RuntimeException: Failure delivering result ResultInfo{who=null, request=131098, result=0, data=null} to activity {byxx.android.huochang/byxx.android.huochang.stnsh.AcSH}: java.lang.NullPointerException

	                }  
	            }  
	            
	        });  

	      gridView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {  
	            @Override  
	            public boolean onItemLongClick(AdapterView<?> parent, View view,  
	                    int position, long id) {  
	                LoadImage loadimg = fileNameList1.get(position);  
	                ViewHolder holder = (ViewHolder)view.getTag();  
	                if(!isDbClick1){  
	                    isDbClick1 = true;  
//	                    gridView.setPadding(0, 50, 0, 50);            //长按后，让gridview上下都分出点空间，显示删除按钮之类的。看效果图就知道了。  
//	                    seclectNumView.setVisibility(View.VISIBLE);  
	                    imgbtn1_del.setVisibility(View.VISIBLE);  
//	                    holder.image1.setImageResource(R.drawable.border);  
	                    holder.image2.setVisibility(View.VISIBLE);  
	                    imgAdapter1.addNumber(position+"");  
	                    selectFileLs1.add(loadimg);  
//	                    seclectNumView.setText("选中1张图片");  
	                    return true;  
	                }  
	                return false;  
	            }  
	        });  

	      btn_Reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				etjzxh1.setText("");
				pht1.removeAllElements();

				for(int i = 6 ;i > 0;i--){
					pht1.add(i);
				}
				size1 = pht1.size();	
			}
		});
	      
//		btnQuery.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				String xgh = "";
//				if(!orderNo.getText().toString().trim().equals("")){
//					xgh = getFormatYdnum(orderNo.getText().toString().trim(),7);
//				}else{
//
//					xgh = "0286891";
//				}
//				orderNo.setText(xgh);
//				findJZXH jzxh = new findJZXH();
//				jzxh.execute(spinner.getSelectedItem().toString().trim() + orderNo.getText().toString().trim());
//				pht1.removeAllElements();
//
//				for(int i = 6 ;i > 0;i--){
//					pht1.add(i);
//
//				}
//				size1 = pht1.size();
//
//			}
//		});


		imgbtn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jzxnum = "";
				jzxnum = etjzxh1.getText().toString().trim();
				if(jzxnum != null && !jzxnum.equals("") ){
						destoryBimap();  
			            String state = Environment.getExternalStorageState();  
			            if (state.equals(Environment.MEDIA_MOUNTED)) {  
//			            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");  
//			            startActivityForResult(intent, CAMERACODE);
			            pgname = null;
			            if(!pht1.isEmpty()){
		                pgname = 
//		                		pht1.lastElement().toString();
		                		"DL_"+ByString.getTimeStr(java.lang.System.currentTimeMillis(), "yyyyMMddHHmmss");
			             if(pgname != null ){
			            pgname += ".jpg";
		                SD_CARD_TEMP_DIR =  savePathString+ File.separator + pgname;
			                	  Intent takePictureFromCameraIntent = new Intent(  MediaStore.ACTION_IMAGE_CAPTURE);
			                	  takePictureFromCameraIntent.putExtra( android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SD_CARD_TEMP_DIR)));
			                	  startActivityForResult(takePictureFromCameraIntent, CAMERACODE1);    

			             }else{
			            	 ByDialog.showMessage(context, "已有"+size1+"张图片");	 
			             }
			            }else{
			           	 	ByDialog.showMessage(context, "已有"+size1+"张图片");	 
			            }
			             } else {  
			                Toast.makeText(context,"请插入SD卡", Toast.LENGTH_LONG).show();  
			            }  

				}else{
					ByDialog.showMessage(context, "请输入箱号1");
				}
				
			
			}
			
			
		});

        
        
//        spinner = (Spinner) findViewById(R.id.spinner1);
        //将可选内容与ArrayAdapter连接起来  

          


          
//        将adapter 添加到spinner中  
//        spinner.setAdapter(adapter);  
          
        //添加事件Spinner事件监听    
//        spinner.setOnItemSelectedListener(new SpinnerSelectedListener()); 
        
        btnUpdating1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!etjzxh1.getText().toString().trim().equals("")){
					if(fileNameList1.size()>=1){
				for(int i = 0;i<fileNameList1.size();i++){
				uploadNor(fileNameList1.get(i).getFileName(),etjzxh1.getText().toString().trim());
				}
					}else{
						Toast.makeText(AcSHsmp.this, "请拍照", Toast.LENGTH_SHORT).show();
						
					}
				}else{
					Toast.makeText(AcSHsmp.this, "箱号不能为空", Toast.LENGTH_SHORT).show();
				}

			}
		});

        
        xh1_bt_img_query.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AcSHsmp.this,ShowImage.class);
				intent.putExtra("photoName", etjzxh1.getText().toString().trim());
				startActivity(intent);

			}
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(AcSHsmp.this,ShowImage.class);
//				intent.putExtra("xh", etjzxh1.getText().toString().trim());
//				intent.putExtra("yd", spinner.getSelectedItem().toString().trim() + orderNo.getText().toString().trim());
//				startActivity(intent);
//				
//			}
		});
        
        //初始化照片数量
		pht1.removeAllElements();
		for(int i = 6 ;i > 0;i--){
			pht1.add(i);
		}
		size1 = pht1.size();	

 	}
	
	private void destoryBimap() {    
        if (photo != null && !photo.isRecycled()) {    
            photo.recycle();    
            photo = null;    
        }    
      }  
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == CAMERACODE1 

				){
					

				  try {
				   File f = new File(SD_CARD_TEMP_DIR);
				   pht1.remove(pht1.lastElement());
				   save1(SD_CARD_TEMP_DIR);



				  } catch (Exception e) {
				   // TODO: handle exception
//				   System.out.println(e.getMessage());
//				   lc("2 f"+e);
				  }

	
		}else if(requestCode == PictureUtil.RESULTISDEL1){

			if(resultCode == RESULT_OK){
			if(data != null && !data.equals("")){
			String re= data.getStringExtra("ISDEL");
			if(re != null && re.equals("true")){
				String localImg = data.getStringExtra("localImg");
				if(localImg != null ){
		            for(int i= 0 ;i<fileNameList1.size();i++){
		            		if(fileNameList1.get(i).getFileName().equals(localImg)){
		            			fileNameList1.remove(i);
		            			pht1.add(i);
		            			break;

		            	}
		            }
		            imgAdapter1.addAllPhoto(fileNameList1);
				}
			}
			}
			}



		}
		
//http://blog.csdn.net/mengliluohua_151/article/details/9951163
//原因是在AndroidManifest.xml 中跳转到的页面我自己设置了android:launchMode="singleTask"，因为需要传值的 Activity 不容许设置该属性或者 singleInstance，或只能设为标准模式，不然将在 startActivityForResult()后直接调用 onActivityResult()。
//解决办法：去掉跳转到的页面的lanchMode属性即可。
//另外，requestCode值必须要大于等于0，不然，startActivityForResult就变成了 startactivity。
//
//
//找到的一些资料：（摘抄自http://aijiawang-126-com.iteye.com/blog/1717326）
//
//从SDK我们可以看到如下深奥的解释：http://developer.android.com/guide/topics/manifest/activity-element.html#lmode 
//The other modes — singleTask and singleInstance — are not appropriate for most applications, since they result in an interaction model that is likely to be unfamiliar to users and is very different from most other applications. 
//从柯元旦的《Android 内核剖析》的第十章“Ams内部原理“10.1.3中有这样的一段话：请注意：SINGLE_TASK标识以及SINGLE_INSTANCE两个标识必须在r.result==0的条件中，即这两个标识只能用在startActivity()的方法中，而不能使用在startActivityForResult方法中。因为从Task的角度看，Android认为不同Task之间的Activity是不能传递数据的，所以不能使用NEW_TASK标识，但还是要调用forResult方法。

		
	
	
		

		}
	
	
	private void save1(String mCurrentPhotoPath) {

		if (mCurrentPhotoPath != null) {

			try {
				File f = new File(mCurrentPhotoPath);

				Bitmap bm = PictureUtil.getSmallBitmap(mCurrentPhotoPath);

				 LoadImage loadImage = new LoadImage(f.getPath(),bm);
				 imgAdapter1.addPhoto(loadImage);

                 fileNameList1.add(loadImage);  		
				FileOutputStream fos = new FileOutputStream(f);

				bm.compress(Bitmap.CompressFormat.JPEG, 20, fos);
                
				Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();
				 if(bm!=null&&bm.isRecycled())
			       {
			          bm.recycle();
			       }
			} catch (Exception e) {
//				Log.e(TAG, "error", e);
			}

		} else {
			Toast.makeText(this, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
		}
	}

	
    //使用数组形式操作  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
//            view.setText("你的血型是："+m[arg2]);  
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }  
    
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if(isDbClick1 && keyCode == KeyEvent.KEYCODE_BACK){    //点击返回按键  
            isDbClick1 = false;
//          gridView.setPadding(0, 0, 0, 0);            //退出编辑转台时候，使gridview全屏显示  
//          seclectNumView.setVisibility(View.GONE);  
            imgbtn1_del.setVisibility(View.GONE);  
            selectFileLs1.clear();  
            imgAdapter1.clear();  
            return false;  
        }  

        return super.onKeyDown(keyCode, event);  
    }  
	
    /** 
     * 删除监听器事件 
     */  
    private android.view.View.OnClickListener delClickListener1 = new View.OnClickListener() {  
        @Override  
        public void onClick(View v) {  
        	
        	
			AlertDialog.Builder builder = new AlertDialog.Builder(AcSHsmp.this);
			builder.setMessage("确认删除打X照片吗?");
			builder.setCancelable(false);
			builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {// which=-1
//				
		            if(selectFileLs1.isEmpty()) {  
		                Toast.makeText(AcSHsmp.this, "请选择图片", Toast.LENGTH_SHORT).show();  
		                return ;  
		            }  

		            for(int i= 0 ;i<fileNameList1.size();i++){
		            	for(int j= 0 ;j<selectFileLs1.size();j++){
		            		if(fileNameList1.get(i).getFileName().equals(selectFileLs1.get(j).getFileName())){
		            			fileNameList1.remove(i);
		            			pht1.add(i);
		            			break;
		            		}
		            	}
		            }

		            imgAdapter1.addAllPhoto(fileNameList1);

					

				}
			});
			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {// which=-2
				    isDbClick1 = false;
		            imgbtn1_del.setVisibility(View.GONE);  
		            selectFileLs1.clear();  
		            imgAdapter1.clear();  
				}
			});
			builder.create().show();
        	

        }     
    }; 

    
    
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				buHomeOnClick();
//				Toast.makeText(this, "bu", Toast.LENGTH_SHORT).show();
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}


	private void buHomeOnClick() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(AcSHsmp.this,AcFunction.class);
		startActivity(intent);
		finish();
	} 
	
	
	/**
	 * 上传到服务器
	 */
//	private void upload(String mCurrentPhotoPath,String ydNum,String jzxNum) {
//
//		if (mCurrentPhotoPath != null) {
//			FileUploadTask task = new FileUploadTask();
//			task.execute(mCurrentPhotoPath,ydNum,jzxNum);
//		}else {
//			Toast.makeText(this, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
//		}
//	}
	/**
	 * 上传到服务器
	 */
	private void uploadNor(String mCurrentPhotoPath,String jzxNum) {
		
		if (mCurrentPhotoPath != null) {
			FileUploadNorTask task = new FileUploadNorTask();
			task.execute(mCurrentPhotoPath,jzxNum);
		}else {
			Toast.makeText(this, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
		}
	}
	
	
//	private class FileUploadTask extends AsyncTask<String, Void, String> {
//		String jzxNum = null;
//		String ydNum = null;
//		@Override
//		protected String doInBackground(String... params) {
//			String filePath = params[0];
//			ydNum =   params[1];
//			jzxNum =  params[2];
//
//			String content;
//			
//			content = PictureUtil.bitmapToString(filePath);
//
//			Gson gson = new Gson();
//			String json = gson.toJson(content);
//			System.out.println(json);
//
//			return MaStation.getInstance().getMaWebService().saveImg(ydNum,jzxNum,json);//helper.sendPost(json);// 使用http post
//		}
//
//		@Override
//		protected void onPreExecute() {
////			progressDialog = new ProgressDialog(AcSH.this);
////			progressDialog.setMessage("正在提交,请稍候...");
////			progressDialog.show();
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
////			progressDialog.dismiss();
//			if(result.equals("上传图片成功!")){
//			if(jzxNum.equals(etjzxh1.getText().toString().trim())){
//				fileNameList1.clear();
//				imgAdapter1.delAllPhoto();
//			}
//			}
//			Toast.makeText(AcSHsmp.this, result, Toast.LENGTH_SHORT).show();
//		}
//
//	}

	private class FileUploadNorTask extends AsyncTask<String, Void, String> {
		String jzxNum = null;
		@Override
		protected String doInBackground(String... params) {
			String filePath = params[0];
			jzxNum =  params[1];
			
			String content;
			
			content = PictureUtil.bitmapToString(filePath);
			
			Gson gson = new Gson();
			String json = gson.toJson(content);
//			System.out.println(json);
			
			return MaStation.getInstance().getMaWebService().saveNormalImg(jzxNum,json);//helper.sendPost(json);// 使用http post
		}
		
		@Override
		protected void onPreExecute() {
//			progressDialog = new ProgressDialog(AcSH.this);
//			progressDialog.setMessage("正在提交,请稍候...");
//			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result != null && !"".equals(result)){
//			progressDialog.dismiss();
			if(result.equals("上传图片成功!")){
				if(jzxNum.equals(etjzxh1.getText().toString().trim())){
					fileNameList1.clear();
					imgAdapter1.delAllPhoto();
				}
			}
			Toast.makeText(AcSHsmp.this, result, Toast.LENGTH_SHORT).show();
		}
		}
		
	}
	private class FileDownTask extends AsyncTask<String, Void, Bitmap> {
		
		@Override
		protected Bitmap doInBackground(String... params) {
			String imgid = params[0];
			return null;
//			needcall		MaStation.getInstance().getMaWebService().getIBitmap(imgid);//helper.sendPost(json);// 使用http post
		}
		
		@Override
		protected void onPreExecute() {
//			progressDialog = new ProgressDialog(AcSH.this);
//			progressDialog.setMessage("正在提交,请稍候...");
//			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {

			try{
			ImageView iView = new ImageView(context);
			if(result != null && !"".equals(result)){

					iView.setImageBitmap(result);
		            xh_show_img.addView(iView);

		 
			}
			}catch(Exception e){
				
			}
		}
		
	}
//	private class findJZXH extends AsyncTask<String, Void, String> {
//		
//		@Override
//		protected String doInBackground(String... params) {
//			String ydNum = params[0];
////			ydNum = "CRCTQ000285761";
//			return MaStation.getInstance().getMaWebService().getJzxnum(ydNum);
//		}
//		
//		@Override
//		protected void onPreExecute() {
//			progressDialog = new ProgressDialog(AcSHsmp.this);
//			progressDialog.setMessage("正在查询箱号,请稍候...");
//			progressDialog.show();
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//
//			try{
//				if(isNetworkConnected()||MaStation.getInstance().isTest()){
//					
//				if(result != null && !result.equals("[]")){
//					state.setVisibility(8);
//					progressDialog.dismiss();
//
//					Gson gson = new Gson();
//					getJzxnum = gson.fromJson(result, new TypeToken<LinkedList<String>>(){}.getType());
//					if(getJzxnum.get(0) != null){
//						rl_jzxh1.setVisibility(0);
//						etjzxh1.setText(getJzxnum.get(0).toString());
//					}else{
//						rl_jzxh1.setVisibility(View.GONE);
//					}
//					
//					
//				}else{
//					progressDialog.dismiss();
//					state.setTextColor(Color.RED);
//					state.setText("无箱号数据");
//				}
//				}else{
//					progressDialog.dismiss();
//					state.setTextColor(Color.RED);
//					state.setText("网络异常");
//					
//				}
//			}catch(Exception e){
//				progressDialog.dismiss();
//			}
//		}
//		
//	}

		// 生成运单号时补0
	private  String getYdh(String num, int size) {
			String zero = "";
			if (num.length() < size) {
				int length = size - num.length();
				for (int i = 0; i < length; i++) {
					zero = zero + "0";
				}
			}
			return zero + num;
		}
	
//	0246128
	private String getFormatYdnum(String num,int size) {
	
		String ydnum = num;//getJTextFieldYdnum().getText().trim();
		if(num.length() <= size){
			if (!ydnum.equals("")) {
			    char[] temps = ydnum.toCharArray();
			    char[] ys = new char[size];
			    for (int i = 0; i < ys.length; i++) {
				ys[i] = '0';
			    }
			    int offset = size - temps.length;
			    for (int i = offset; i < temps.length + offset; i++) {	
			    	ys[i] = temps[i - offset];
			    }
			    ydnum = new String(ys);
			}
		}else{
			Toast.makeText(context, "运单号超过"+size+"位，请重试输入", Toast.LENGTH_SHORT).show();
		}
		return ydnum;
	    }

	
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
}
