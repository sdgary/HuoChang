package byxx.android.huochang.stnsh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.R;
import byxx.android.huochang.dw.AcJzx;
import byxx.android.huochang.ftp.SeUploading;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;

public class AcSH2 extends BaseActivity{
	EditText orderNo,etjzxh1,etjzxh2;
	Button btnQuery,btnUpdating1,btnUpdating2;
	String SD_CARD_TEMP_DIR;
	String pgname = null;
    private Spinner spinner;  
    private ArrayAdapter<String> adapter; 
	public Context context;
	private Bitmap photo;
	ProgressDialog dialog = null;
	static String savePathString = Environment.getExternalStorageDirectory()+"/"+"DCIM"+"/"+"Camera/";
	static final int CAMERACODE2 = 12;
	static final int CAMERACODE1 = 11;
	FreightDTO value;
	String jzxnum = null;
	Stack<Integer> pht1,pht2;
	 int size1 = 0;
	int size2 = 0;
    private static final String[] m={"CRCTQ00","CRCTQ0A"};  
	private String mCurrentPhotoPath;// 图片路径
	private String mCurrentVideoPath;// 视频路径
	private static final int REQUEST_TAKE_PHOTO = 0;
	private static final int REQUEST_TAKE_VIDEO = 1;
	private static final String TAG = "MainActivity";
	public ProgressDialog progressDialog;
	private ImageView mImageView;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stnsh);
		value = new FreightDTO();
		//CRCTQ00
		//CRCTQ0A
		context = this;
		orderNo = (EditText)findViewById(R.id.et_orderNo);
		etjzxh1 = (EditText)findViewById(R.id.et_jzxh1);
		etjzxh2 = (EditText)findViewById(R.id.et_jzxh2);
		btnQuery = (Button)findViewById(R.id.btn_order_query);
		btnUpdating1 = (Button)findViewById(R.id.button1);
		btnUpdating2 = (Button)findViewById(R.id.button2);
		pht1 = new Stack<Integer>();
		pht2 = new Stack<Integer>();
		btnQuery.setOnClickListener(new OnClickListener() {
//			12-27 22:32:53.297: ERROR/AndroidRuntime(23225): java.lang.RuntimeException: Unable to start activity ComponentInfo{byxx.android.huochang/byxx.android.huochang.stnsh.AcSH}: java.lang.NullPointerException

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pht1.removeAllElements();
				pht2.removeAllElements();
				for(int i = 2 ;i > 0;i--){
					pht1.add(i);
					pht2.add(i);
				}
				size1 = pht1.size();
				size2 = pht2.size();
			}
		});


        btnUpdating1.setOnClickListener(new OnClickListener() {
			
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
		                pgname = pht1.lastElement().toString();//"DL_"+ByString.getTimeStr(java.lang.System.currentTimeMillis(), "yyyyMMddHHmmss")+".jpg";
			             if(pgname != null ){
			            pgname += ".jpg";
		                SD_CARD_TEMP_DIR =  savePathString+ File.separator + pgname;
			                	  Intent takePictureFromCameraIntent = new Intent(
			                	    MediaStore.ACTION_IMAGE_CAPTURE);
			                	  takePictureFromCameraIntent.putExtra(
			                	    android.provider.MediaStore.EXTRA_OUTPUT, Uri
			                	      .fromFile(new File(SD_CARD_TEMP_DIR)));
			                	  startActivityForResult(takePictureFromCameraIntent, CAMERACODE1);    
			                	  
			             }else{
			            	 ByDialog.showMessage(context, size1+"张图片已经上传完毕");	 
			             }
			            }else{
			           	 	ByDialog.showMessage(context, size1+"张图片已经上传完毕");	 
			            }
			             } else {  
			                Toast.makeText(context,"请插入SD卡", Toast.LENGTH_LONG).show();  
			            }  

				}else{
					ByDialog.showMessage(context, "请输入箱号1");
				}
				
				btnUpdating2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						jzxnum = "";
						jzxnum = etjzxh2.getText().toString().trim();
						if(jzxnum != null && !jzxnum.equals("") ){
								destoryBimap();  
					            String state = Environment.getExternalStorageState();  
					            if (state.equals(Environment.MEDIA_MOUNTED)) {  
//					            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");  
//					            startActivityForResult(intent, CAMERACODE);  
				                pgname = "DL_"+ByString.getTimeStr(java.lang.System.currentTimeMillis(), "yyyyMMddHHmmss")+".jpg";
					                SD_CARD_TEMP_DIR =  savePathString+ File.separator + pgname;
					                	  Intent takePictureFromCameraIntent = new Intent(
					                	    MediaStore.ACTION_IMAGE_CAPTURE);
					                	  takePictureFromCameraIntent.putExtra(
					                	    android.provider.MediaStore.EXTRA_OUTPUT, Uri
					                	      .fromFile(new File(SD_CARD_TEMP_DIR)));
					                	  takePictureFromCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
					                	  startActivityForResult(takePictureFromCameraIntent, CAMERACODE1);    
					            } else {  
					                Toast.makeText(context,"请插入SD卡", Toast.LENGTH_LONG).show();  
					            }  
						}else{
							ByDialog.showMessage(context, "请输入箱号2");
						}
					}
				});
			}
			
			
		});
        
        
        spinner = (Spinner) findViewById(R.id.spinner1);
        //将可选内容与ArrayAdapter连接起来  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);  
          
        //设置下拉列表的风格  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
          
        //将adapter 添加到spinner中  
        spinner.setAdapter(adapter);  
          
        //添加事件Spinner事件监听    
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());  
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
		switch (requestCode) {
		case CAMERACODE1:
		
//				ContentResolver resolver = getContentResolver();
				  try {
				   File f = new File(SD_CARD_TEMP_DIR);
				   pht1.remove(pht1.lastElement());
				   try {
				    Uri capturedImage = Uri
				      .parse(android.provider.MediaStore.Images.Media
				        .insertImage(getContentResolver(), f
				          .getAbsolutePath(), null, null));
				    String ydh = value.getOrderNo();
				    if (ydh == null || ydh.equals("")) {
						ydh = "JZX_EMPTY";
					}
					String path = "/DLQ_Ftp/" + ydh + "/" + jzxnum;
//				    if (ydh == null || ydh.equals("")) {
//						this.ydh = "JZX_EMPTY";
//					} else {
//						this.ydh = ydh;
//					}
//		String path = "/DLQ_Ftp/" + ydh + "/" + jzxnum;
				    
				    if(capturedImage != null){
				    	Intent intent = new Intent();
				    	intent.setClass(context, SeUploading.class);
				    	Bundle bundle = new Bundle();
				    	bundle.putString("setfilename",pgname);
				    	bundle.putString("remotefilename", path);
				    	intent.putExtras(bundle);
				    	startService(intent);
				    }
				    
				    // Log.i("camera", "Selected image: " +
				    // capturedImage.toString());
				   // f.delete();
				    
//				    lc( capturedImage.toString()+"");

				    
					//			    // 将图片内容解析成字节数组
					//			    mContent = readStream(resolver.openInputStream(Uri
					//			      .parse(capturedImage.toString())));
					//			    // 将字节数组转换为ImageView可调用的Bitmap对象
					//			    myBitmap = getPicFromBytes(mContent, null);
					//			    // //把得到的图片绑定在控件上显示
					////			    imageView.setImageBitmap(myBitmap);
					//
					//                if( !myBitmap.isRecycled()){
					//                	myBitmap.recycle();
					//                    }
//					webview.setVisibility(View.VISIBLE);
//					imageView.setVisibility(View.GONE);
//					videoView.setVisibility(View.GONE);
//				    webview.clearView();
//				    webview.loadUrl("file:///"+SD_CARD_TEMP_DIR);
//				    if(!isfromAcERR){
//				    	btloading.setVisibility(View.VISIBLE);
//				        tvstatus.setText("请点击上传");
//				    }
//			        tvname.setText(pgname); 

//			        type = 1;
				    //
				    //
				   } catch (FileNotFoundException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
//				    lc("2"+e);
				   }
				  } catch (Exception e) {
				   // TODO: handle exception
//				   System.out.println(e.getMessage());
//				   lc("2 f"+e);
				  }
		break;
		case CAMERACODE2:
			  //Wysie_Soh: After an image is taken and saved to the location of mImageCaptureUri, come here   
	        //and load the crop editor, with the necessary parameters (96x96, 1:1 ratio)   
			   File f = new File(SD_CARD_TEMP_DIR);
			   try {
			    Uri capturedImage = Uri.parse(android.provider.MediaStore.Images.Media
				        .insertImage(getContentResolver(), f
				          .getAbsolutePath(), null, null));
		        Intent intent = new Intent("com.android.camera.action.CROP");   
		        intent.setClassName("com.android.camera", "com.android.camera.CropImage");   
		        intent.setData(capturedImage);   
		        intent.putExtra("outputX", 96);   
		        intent.putExtra("outputY", 96);   
		        intent.putExtra("aspectX", 1);   
		        intent.putExtra("aspectY", 1);   
		        intent.putExtra("scale", true);   
		        intent.putExtra("return-data", true);               
		        startActivityForResult(intent, CAMERACODE1);   
		}catch (Exception e) {
				// TODO: handle exception
			}
			break;

		default:
			break;
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
	
}
