package byxx.android.huochang.dw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;

public class AcJzx extends BaseActivity{
	ListView listView;
	Button btndw;  //对位
	TextView jhh;  //计划号
	String strJhh;
	BaJzxStatus baJzxStatus;
	Handler handler;
	private Bitmap photo;
	String SD_CARD_TEMP_DIR;
	String pgname = null;
	public Context context;
	ProgressDialog dialog = null;
	RuJhx ruJhx;
	String hfs = "<font color=#FFFFFF >";
	String hfe = "</font>";
	static final int CAMERACODE2 = 772200;
	static final int FTP_RETURN_OK =33444;
	static final int FTP_RETURN_ERR = 4444;
	List<JzxDTO> nListShow;
	static String savePathString = Environment.getExternalStorageDirectory()+"/"+"DCIM"+"/"+"Camera/";
	String gdm = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		
		setContentView(R.layout.qxc_jh_dw);
		listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(getBaJzxStatus());
		btndw  = (Button)findViewById(R.id.btn_dw);
		jhh = (TextView)findViewById(R.id.tv_jhh);
		context = this;
		strJhh = getIntent().getStringExtra("jhh");
		gdm = getIntent().getStringExtra("gdm");
		if(strJhh!=null){
			jhh.setText(Html.fromHtml(hfs+"计划号:"+hfe+strJhh));
		}
		btndw.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(dtoischeak()){
				destoryBimap();  
	            String state = Environment.getExternalStorageState();  
	            if (state.equals(Environment.MEDIA_MOUNTED)) {  
//	            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");  
//	            startActivityForResult(intent, CAMERACODE);  
                pgname = "DL_"+ByString.getTimeStr(java.lang.System.currentTimeMillis(), "yyyyMMddHHmmss")+".jpg";
	                SD_CARD_TEMP_DIR =  savePathString+ File.separator + pgname;
	                	  Intent takePictureFromCameraIntent = new Intent(
	                	    MediaStore.ACTION_IMAGE_CAPTURE);
	                	  takePictureFromCameraIntent.putExtra(
	                	    android.provider.MediaStore.EXTRA_OUTPUT, Uri
	                	      .fromFile(new File(SD_CARD_TEMP_DIR)));
	                	  startActivityForResult(takePictureFromCameraIntent, CAMERACODE2);    
	            } else {  
	                Toast.makeText(AcJzx.this,"请插入SD卡", Toast.LENGTH_LONG).show();  
	            }  
				}else{
					 Toast.makeText(AcJzx.this,"请勾选", Toast.LENGTH_SHORT).show();  
				}
			}
		});
		
		getRuGjh().setType(RuJhx.REAL_ALL_JHX);
		getRuGjh().start();
		
	}
	
	private void destoryBimap() {    
        if (photo != null && !photo.isRecycled()) {    
            photo.recycle();    
            photo = null;    
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
	
	protected void dealHandleMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg == null){
			return;
		}
		boolean closeDialog = true;
		int dId = 0; // 对话框的标识
		try{
		Bundle bundle = msg.getData();
		switch (msg.what)
		{

		case FTP_RETURN_OK:
		   String flag = msg.getData().getString("isuploading");
		Toast.makeText(context, flag, Toast.LENGTH_SHORT).show();
//		    tvstatus.setText("上传"+flag);
			String sentType = "0";
			if(("上传新文件成功") == flag ){
//			  if(type == 3){
//				  sentType ="1";
//			  }
//			  threadStart(mfileName,etTitle.getText().toString(),sentType,etfileDescribe.getText().toString());
			}
			
			break;
		
		case FTP_RETURN_ERR:
//			tvstatus.setText("上传失败");
			Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();

		break;
		case Constant.MSG_ID_READ_JHX:
			dId = 0;
			boolean ballreal = bundle.getBoolean("realalljhx");
			if (ballreal) {
//				tvstatus.setText("");
				showData();
			}else{
				showData();
//				tvstatus.setText("暂时数据");
			}
			ruJhx.close();
			break;
		case Constant.MSG_ID_JZX_STATUE_R:
			dealR(RuJhx.SEND_X_ZK);
			break;
		case Constant.MSG_ID_JZX_STATUE:
			dId = 1;
			dealCommit(bundle, RuJhx.SEND_X_ZK);
			break;
		default:
			break;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (closeDialog && dialog != null && dialog.isShowing()) {
			dismissDialog(dId);
		}
	}

	/**
	 * 上报
	 */
	private void dealR(int type) {
		// Task tTask = (Task) getBATaskOne().getDatas().get(0);
		JzxDTO dto = MaStation.getInstance().getMoJhx().getTaskSelect();
		try {
			if (dto != null) {
				showDialogM(1);
				if (ruJhx != null) {
					ruJhx.close();
				}
				RuJhx ruJhx = new RuJhx(getHandler());
				ruJhx.setDto(dto);
				ruJhx.setType(type);
				ruJhx.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void showDialogM(int type) {
		try {
			showDialog(type);
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	BaJzxStatus getBaJzxStatus(){
		if(baJzxStatus == null){
			baJzxStatus = new BaJzxStatus();
			getBaJzxStatus().setHandler(getHandler());
		}
		return baJzxStatus;
		
	}
	
	/**
	 * 上报成功
	 * 
	 * @param bundle
	 */
	private void dealCommit(Bundle bundle, int type) {
		try {
				// 刷新
				if(bundle.getString("ok")!= null){
				if(bundle.getString("ok").toUpperCase().contentEquals("OK")){
				String id = bundle.getString("id");
				JzxDTO  dto = MaStation.getInstance().getMoJhx().finddtobyid(id);
					if (dto != null) {
						long tiCur = System.currentTimeMillis();
						if(type == RuJhx.SEND_X_ZK){
							ByDialog.showMessage(this, "保存成功");
//							task.setWaitingRoomOnboardOver(tiCur);
						}

					}
				}else if(bundle.getString("ok").toUpperCase().contentEquals("FAIL")){
					ByDialog.showMessage(this, "服务器其他原因，保存失败");
				}else{
					ByDialog.showMessage(this, " "+bundle.getString("ok").toString());
				}
			}else {
				ByDialog.showMessage(this, "保存失败，请注意");
			}
//			aList.clear();
//			if(MaStation.getInstance().getUser().isXyPack()){
//				aList.addAll(MaStation.getInstance().getMoTask().getNextTasks());
//			}else{
//				aList.addAll(MaStation.getInstance().getMoTask().getCurTasks());
//			}
//			getBATaskOne().setWidthShow(MaStation.getInstance().getWidthShow());
////			tListView.setAdapter(getBATaskOne());
//			getBATaskOne().setDatas(aList);
//			getBATaskOne().notifyDataSetChanged();
//			RefreshTime.setTextColor(Color.WHITE);
//			RefreshTime.setText("其他按钮刷新时间 "+ByString.getTimeStr(System.currentTimeMillis(),"dd日 HH:mm:ss"));
			} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
	
	private void showData() {
		try {
			nListShow = MaStation.getInstance().getMoJhx().getJzxdtos();//MaStation.getInstance().getMofault().getAlKfcFailure();
			getBaJzxStatus().setDatas(nListShow);
			
			getBaJzxStatus().notifyDataSetChanged();
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	HashMap<String, Boolean> ischeakmap = new HashMap<String, Boolean>();
	
	boolean dtoischeak(){
		boolean b =false;
		ischeakmap.clear();
		if(MaStation.getInstance().getMoJhx().getJzxdtos() != null ){
			for(JzxDTO  dto :MaStation.getInstance().getMoJhx().getJzxdtos()){
				if(dto.isChecked()){
					ischeakmap.put(dto.getCh(), true);
				}
			}
		}
		Iterator<Entry<String, Boolean>> it = ischeakmap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Boolean> entry = (Entry<String, Boolean>)it.next();
			if((Boolean)entry.getValue()){
				b = true;
				break;
//				codes = codes + entry.getKey().toString() + ",";
			}
		}
		return b;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (CAMERACODE2 == requestCode)
		{			
//			ContentResolver resolver = getContentResolver();
			  try {
			   File f = new File(SD_CARD_TEMP_DIR);
			   try {
			    Uri capturedImage = Uri
			      .parse(android.provider.MediaStore.Images.Media
			        .insertImage(getContentResolver(), f
			          .getAbsolutePath(), null, null));
			    
			    
//			    if(capturedImage != null){
//			    	Intent intent = new Intent();
//			    	intent.setClass(AcJzx.this, SeUploading.class);
//			    	Bundle bundle = new Bundle();
//			    	bundle.putString("setfilename",pgname);
//			    	intent.putExtras(bundle);
//			    	startService(intent);
//			    }
			    
			    // Log.i("camera", "Selected image: " +
			    // capturedImage.toString());
			   // f.delete();
			    
//			    lc( capturedImage.toString()+"");

			    
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
//				webview.setVisibility(View.VISIBLE);
//				imageView.setVisibility(View.GONE);
//				videoView.setVisibility(View.GONE);
//			    webview.clearView();
//			    webview.loadUrl("file:///"+SD_CARD_TEMP_DIR);
//			    if(!isfromAcERR){
//			    	btloading.setVisibility(View.VISIBLE);
//			        tvstatus.setText("请点击上传");
//			    }
//		        tvname.setText(pgname); 

//		        type = 1;
			    //
			    //
			   } catch (FileNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
//			    lc("2"+e);
			   }
			  } catch (Exception e) {
			   // TODO: handle exception
//			   System.out.println(e.getMessage());
//			   lc("2 f"+e);
			  }
			  return;
		 }
	
		

		}
	
	
	public RuJhx getRuGjh() {
		if (ruJhx == null) {
			ruJhx= new RuJhx(getHandler());
			
		}
		return ruJhx;
	}
	
	/**
	 * 收听上传结果广播
	 * @author Way
	 *
	 */
	 public class UpLoadingResult extends BroadcastReceiver{
		 @Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			 String supdate =null;
			 Bundle bundle = intent.getExtras();
			 supdate = bundle.getString("setFtpReceiverFlag");

//			 tvstatus.setText(supdate);
		 
		}
	 }
	
		/**
		 * 收听上传过程广播
		 * @author Way
		 *
		 */
		 public class UpLoadingUI extends BroadcastReceiver{
			 @Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				 long supdate = 0;
				 Bundle bundle = intent.getExtras();
				 supdate =bundle.getLong("process_data");
				 Log.v("process_data", supdate+"");
//				 tvstatus.setText("上传中 "+supdate+"%");
	 
			}
		 }
	
			public boolean onOptionsItemSelected(MenuItem item) {
				switch(item.getItemId()) {
					case android.R.id.home:
						buGoBack();
//						Toast.makeText(this, "bu", Toast.LENGTH_SHORT).show();
						return true;
						
					default:
						return super.onOptionsItemSelected(item);
				}
				
			}


			private void buGoBack() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AcJzx.this,AcDW.class);
				startActivity(intent);
				finish();
			}
	
	
}
