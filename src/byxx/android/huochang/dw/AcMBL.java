package byxx.android.huochang.dw;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
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
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.picture.LoadImage;
import byxx.android.huochang.picture.PictureUtil;
import byxx.android.huochang.picture.ShowImage;
import byxx.android.huochang.traininfo.CarDto;
import byxx.android.huochang.traininfo.RuTrainInfo;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;

import com.google.gson.Gson;



public class AcMBL  extends Activity{
	public static final int PHOTO_TYPE_IMPORTANT = 11; // 重点车
	public static final int PHOTO_TYPE_BROKEN = 12; // 坏车
	public static final int PHOTO_TYPE_MEETING = 13; // 车前会
	ListView listView;
	Boolean cqhFlag = false;// 车前会标志，暂定
	Button cqh;
	TextView tvTime, tvState;
	String tabnum;
	String cid;
	TextView tvPlantime, tvActualtime, headtitle, notifitime;
	BaMBL baMBL;
	Handler handler;
	Context mcontext;
	ListView lv = null;
	Button buReturnHome;
	TextView IDCard, cqhMark;
	// Button refresh;
	ProgressDialog dialog = null;
	RuTrainInfo ruTrainInfo;
	boolean intRunType = true;
	ProgressDialog progressDialog;
	int type;
	String valueid = null;
	AlertDialog.Builder dialogshow = null;
	boolean run = false;
	XGjhzwDto dto;
	String SD_CARD_TEMP_DIR;
	String pgname = null;
	private Bitmap photo;
	static String savePathString = Environment.getExternalStorageDirectory()
			+ "/" + "DCIM" + "/" + "100ANDRO/"; // Environment.getExternalStorageDirectory()+"/"+"DCIM"+"/"+"Camera/";
	static final int CAMERACODE2 = 12;
	static final int CAMERACODE1 = 11;
	String hfs = "<font color=#008B00 >";
	String hfe = "</font>";
	FileUploadTask task;
//	DW dw;
	ReportQsgjhFact fact;
	PaiBanFact paiBanFact;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.traininfo_tab2);
//			   AppManager.getAppManager().addActivity(this);
		   
			final ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(true);
	    	mcontext = this;
	    	
	    	tabnum = getIntent().getStringExtra("gdm");
	    	
	    	dto = (XGjhzwDto) getIntent().getSerializableExtra("obj");
	    	
	    	headtitle = (TextView)findViewById(R.id.head_title);
	    	notifitime = (TextView)findViewById(R.id.NotifyTime);
//	    	tvjhh = (TextView)findViewById(R.id.jhno);
//	    	tvTrainnum = (TextView)findViewById(R.id.trainnum);
	    	tvActualtime = (TextView)findViewById(R.id.actualtime);
	    	tvPlantime = (TextView)findViewById(R.id.plantime);
	    	IDCard = (TextView)findViewById(R.id.tvidnum);
	    	
//	    	cqhMark = (TextView) findViewById(R.id.tv_cheqh);
//	    	if (dto.getMeetingFlag()==1) {
//	    		cqhMark.setText("车前会");
//			}else {
//				cqhMark.setText("");
//			}
	    	
//		    	if(swh > 0){
//		    		tvjhh.setText(Html.fromHtml(hfs +"顺位号:"+ hfe+swh));
//		    	}
//		    	if(Trainnum != null){
//		    		tvTrainnum.setText(Html.fromHtml(hfs +"车数:"+hfe+(Zylx !=null ?Zylx:"")+Trainnum));
//		    	}
	    	if(dto.getGJHRN() !=null){
	    		headtitle.setText(dto.getGJHRN());
	    	}
	    	if(dto.getJhsj()!=null){
	    		tvPlantime.setText(Html.fromHtml(hfs +"计划:"+hfe+ByString.getTimeStr(dto.getJhsj().getTime(), "HH:mm")));
	    	}
	    	if(dto.getFactTime()!=null){
	    		tvActualtime.setText(Html.fromHtml(hfs +"实际:"+hfe+ByString.getTimeStr(dto.getFactTime().getTime(), "HH:mm")));
	    	}
	    	if(dto.getNotifyTime()!=null){
	    		notifitime.setText(Html.fromHtml(hfs +"通知:"+hfe+ByString.getTimeStr(dto.getNotifyTime().getTime(), "HH:mm")));
	    	}
	    	
	    	
			tvState = (TextView)findViewById(R.id.tvstate);
			tvTime = (TextView)findViewById(R.id.tvtime);		
			listView = (ListView)findViewById(R.id.lv_traininfo);
//				refresh = (Button)getView().findViewById(R.id.refresh);
			listView.setAdapter(getBaMBL());
			progressDialog = new ProgressDialog(AcMBL.this);
			progressDialog.setMessage("正在加载初始数据,请稍候...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialogInterface) {
//					if (refreshdate != null) {
//						refreshdate.cancel(true);
//					}
				}
			});
			progressDialog.show();
//				run = true;
			showData();
			if(progressDialog != null){
				progressDialog.cancel();
			}
//				getHandler().postDelayed(task,0);
			
			
//				refresh.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//			        	getRuTrainInfo().setType(RuTrainInfo.REAL_ALL_TRAININFO);
//			        	getRuTrainInfo().setGDM(tabnum);
//			    		Log.v("dl", tabnum+"");
//			        	getRuTrainInfo().start();
//						progressDialog.show();
//					}
//				});
			AppManager.getAppManager().addActivity(this);
		}
		
		@Override
		protected void onStart() {
			try {
				MaStation.getInstance().getMoNfc().setHandler(getHandler());
				MaStation.getInstance().handlerTask=getHandler();
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
			super.onStart();
		}
		
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
//				run = true;
//				getHandler().postDelayed(task,0);
		} 
		
		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			run =false;
//				getHandler().post(task);
			if(progressDialog != null){
				progressDialog.cancel();
			}
//				getHandler().removeCallbacks(task);
		}
		
		@Override
		protected void onDestroy() {
			super.onDestroy();

			if (MaStation.getInstance().handlerTask != null) {
				MaStation.getInstance().getMoNfc().setHandler(
						MaStation.getInstance().handlerTask);
			}
			AppManager.getAppManager().finishActivity(this);
		}
		public BaMBL getBaMBL() {
			if(baMBL == null){
				baMBL = new BaMBL();
				baMBL.setHandler(getHandler());
			}
			return baMBL;
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
					case Constant.MSG_ID_NFC: // nfc返回
						String regionId = msg.getData().getString("regionId");
						getNFC(regionId);
						break;
					case Constant.MSG_ID_MSB_PAIBAN:
						paiBanFact = new PaiBanFact();
						paiBanFact.execute(dto.getGjhid()+"",tabnum);
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (closeDialog && dialog != null && dialog.isShowing()) {
//						dismissDialog(dId);
				}
			} catch (Exception e) {
//					MaStation.getInstance().recordExc(e);
			}
			return;
		}
		
		private void showData() {
			try {
				if(dto.getGjhid() > 0 && dto.getSwh() > 0){
				List<CarDto> nListShow = MaStation.getInstance().getMoGJH().findbyGJHid();
				//MaStation.getInstance().getMoGJH().findbyGJHid(gjhid,swh);//MaStation.getInstance().getMofault().getAlKfcFailure();
				//测试数据
//					sXClkDto obj = new XClkDto();
					getBaMBL().setDatas(nListShow);
				getBaMBL().notifyDataSetChanged();
				}
			} catch (Exception e) {
//					MaStation.getInstance().recordExc(e);
			}
		}
		
		public RuTrainInfo getRuTrainInfo() {
			if (ruTrainInfo == null) {
				ruTrainInfo= new RuTrainInfo(getHandler());
			}
			return ruTrainInfo;
		}
		
		private void getNFC(String regionId) {
			try {
				IDCard.setText(regionId);
				tvState.setText("");
//					edcontext.setText("");
//					edcontext.setText(MaStation.getInstance().getMoRegionPatrol().findRFIDbyid(regionId));

				if (regionId == null || regionId.equals("")) {
					return;
				}
				}catch (Exception e) {
				e.printStackTrace();
			 }
		}

			/**
			 * 检测网络是否可用
			 * @return
			 */
			public boolean isNetworkConnected() {
				ConnectivityManager cm = (ConnectivityManager)mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				return ni != null && ni.isConnectedOrConnecting();
			}
		    
			private void buHomeOnClick() {
				boolean b = false;
				if(MaStation.getInstance().getMoDW().getCarDto4dws()!= null){
					for(CarDto dto4dw:MaStation.getInstance().getMoDW().getCarDto4dws()){
//						if(dto4dw.hashMap.get(dto4dw.getCid())!= null && dto4dw.hashMap.get(dto4dw.getCid())>0){
//							b = true;
//							break;
//						}
					}
				
				}
				if(!b){
					if(MaStation.getInstance().getMoDW().getCarDto4dws()!= null){
//						for(int i = 0 ;i<MaStation.getInstance().getMoDW().getCarDto4dws().size();i++){
//								MaStation.getInstance().getMoDW().getCarDto4dws().get(i).hashMap.clear();
//						}
					
					}
					Intent intent = new Intent();
					intent.setClass(AcMBL.this,AcNewDw.class);
					intent.putExtra("MBLgdm", tabnum);
					startActivity(intent);
					finish();
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
					builder.setMessage( "还有未上报的对位，确认退出?");
					builder.setCancelable(false);
					builder.setPositiveButton("确定",
							new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {// which=-1
								if(MaStation.getInstance().getMoDW().getCarDto4dws()!= null){
									for(int i = 0 ;i<MaStation.getInstance().getMoDW().getCarDto4dws().size();i++){
//											MaStation.getInstance().getMoDW().getCarDto4dws().get(i).hashMap.clear();
									}
								}
								Intent intent = new Intent();
								intent.setClass(AcMBL.this,AcNewDw.class);
								startActivity(intent);
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
			}
			

			/**
			 * 对位操作
			 */
			private void upatecheak(){
				if(dto.getNotifyTime()!=null){
					
					cid = IDCard.getText().toString().trim();
					if(MaStation.getInstance().getMoGJH().xGjhzwDto.getFactTime() == null){
						if(cid != null && !cid.equals("")){
							AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
							builder.setMessage( "股道"+tabnum+"对位，确认?");
							builder.setCancelable(false);
							builder.setPositiveButton("确定",
							new android.content.DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {// which=-1
			//							camra();
									/**
									 * 功能由拍照转为刷nfc卡
									 * 20140728
									 * Way
									 */
			//							123gjhid+"",swh+"",tabnum
									
									fact = new ReportQsgjhFact();
									fact.execute(cid,dto.getGjhid()+"",dto.getSwh()+"",tabnum);
								}
							});
							builder.setNegativeButton("取消",
									new android.content.DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {// which=-2
								}
							});
							builder.create().show();
						}else{
							AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
	//							builder.setMessage( "股道"+tabnum+"对位，通知时间未到?");
							builder.setMessage( "卡号为空，请到指定地点刷卡");
							builder.setCancelable(false);
							builder.setPositiveButton("确定",
								new android.content.DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {// which=-1
									
								}
							});
							builder.create().show();
						}
					}else{
						AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
//							builder.setMessage( "股道"+tabnum+"对位，通知时间未到?");
						builder.setMessage( "已经完成对位");
						builder.setCancelable(false);
						builder.setPositiveButton("确定",
							new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {// which=-1

							}
						});
						builder.create().show();
					}

				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
//						builder.setMessage( "股道"+tabnum+"对位，通知时间未到?");
					builder.setMessage( "货配还未通知取送,不能进行确认!\n请先让货配执行'通知取送'操作！");
					builder.setCancelable(false);
					builder.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {// which=-1
							
						}
					});
					builder.create().show();
				}
			}
			
			
			/**
			 * 车前会操作
			 */
			private void mbl_meeting(){
				//wj:改变BaMBL2的uploImg值 好让camra上传时候知道是车前会的照片
				baMBL.uploadImgState = PHOTO_TYPE_MEETING;
				cid = IDCard.getText().toString().trim();
				AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
				builder.setMessage( "股道"+tabnum+"车前会拍照上报，确认?");
				builder.setCancelable(false);
				builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {// which=-1
						camra();
//						cqhMark.setText("车前会");
					}
				});
				builder.setNegativeButton("取消",
						new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {// which=-2
					}
				});
				builder.create().show();
			}
			
			public boolean onOptionsItemSelected(MenuItem item) {
				switch(item.getItemId()) {
					case android.R.id.home://返回首页
						buHomeOnClick();
						return true;
					case R.id.mbl_update://对位
						upatecheak();
						return true;
					case R.id.mbl_cheqianhui://触发车前会
						mbl_meeting();
						return true;
					default:
						return super.onOptionsItemSelected(item);
				}
				
			}
			
			private void cheakimg() {
				// TODO Auto-generated method stub
        		Intent intent = new Intent(AcMBL.this,ShowImage.class);
        		intent.putExtra("photoName",dto.getGjhid()+"@"+dto.getSwh());
        		startActivity(intent);
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
		            	  Intent takePictureFromCameraIntent = new Intent(  MediaStore.ACTION_IMAGE_CAPTURE);
		            	  takePictureFromCameraIntent.putExtra( android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SD_CARD_TEMP_DIR)));
		            	  startActivityForResult(takePictureFromCameraIntent, CAMERACODE1);    
		            	  cqhFlag = true;
		         } else {
					ByDialog.showMessage(this, "请插入SD卡");
//		            Toast.makeText(this,"请插入SD卡", Toast.LENGTH_LONG).show();
		        }  
			}
			
			/**
			 * 上传到服务器
			 */
			private void upload(String mCurrentPhotoPath,String photoName) {

				if (mCurrentPhotoPath != null) {
					task = new FileUploadTask();
					task.execute(mCurrentPhotoPath,photoName);
				}else {
					Toast.makeText(this, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
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
//					public static final int PHOTO_TYPE_IMPORTANT = 11; // 重点车
//					public static final int PHOTO_TYPE_BROKEN = 12; // 坏车
//					public static final int PHOTO_TYPE_MEETING = 13; // 车前会
					if (baMBL.uploadImgState == PHOTO_TYPE_MEETING) {
						photoName =  dto.getGjhid()+"@"+dto.getSwh();
					}else if (baMBL.uploadImgState == PHOTO_TYPE_BROKEN||baMBL.uploadImgState == PHOTO_TYPE_IMPORTANT) {
						photoName =  baMBL.uploadID;
					} 
					return MaStation.getInstance().getMaWebService().saveTypeImg(photoName,baMBL.uploadImgState,json);//helper.sendPost(json);// 使用http post
				}

				@Override
				protected void onPreExecute() {
					progressDialog = new ProgressDialog(AcMBL.this);
					progressDialog.setMessage("正在提交,请稍候...");
					progressDialog.setIndeterminate(true);
					progressDialog.setCancelable(true);
					progressDialog.setCanceledOnTouchOutside(false);
					progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialogInterface) {
							if (task != null) {
								task.cancel(true);
							}
						}
					});
					progressDialog.show();
				}

				@Override
				protected void onPostExecute(String result) {
					
					if("上传图片成功!".equals(result)){
//						DW dw = new DW();
//						dw.execute(dto.getGjhid()+"",dto.getSwh()+"",tabnum);
						return;
					}
					if(result != null && result.length() >0 && !"".equals(result)){
						if("cache".equals(result)){
							ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
						}else{
//							Toast.makeText(AcMBL.this, result, Toast.LENGTH_SHORT).show();
						}

					}else{
						ByDialog.showMessage(mcontext, "网络信号不好！");
					}

					if(progressDialog != null){
						progressDialog.cancel();
					}
				}
				
//				private class DW2 extends AsyncTask<String, Void, String> {
//					@Override
//					protected String doInBackground(String... params) {
//						String gjhID = params[0];
//						String swh =   params[1];
//						String gdm =   params[2];
//						return MaStation.getInstance().getMaWebService().reportQsgjhFact(gjhID,swh,gdm);
//					}
//
//					@Override
//					protected void onPreExecute() {
//						progressDialog = new ProgressDialog(mcontext);
//						progressDialog.setMessage("正在提交,请稍候...");
//						progressDialog.setIndeterminate(true);
//						progressDialog.setCancelable(true);
//						progressDialog.setCanceledOnTouchOutside(false);
//						progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//						progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//							@Override
//							public void onCancel(DialogInterface dialogInterface) {
//								if (task != null) {
//									task.cancel(true);
//								}
//							}
//						});
//						progressDialog.show();
//					}
//
//					@Override
//					protected void onPostExecute(String result) {
////						progressDialog.dismiss();
//						if(result != null && result.length()>0 && "".equals(result)){
//							if(result.equals("true")){
//								Toast.makeText(AcMBL.this, "对位成功", Toast.LENGTH_SHORT).show();
//								MaStation.getInstance().getMoGJH().xGjhzwDto.setFactTime(new Timestamp(System.currentTimeMillis()));
//								tvActualtime.setText(Html.fromHtml(hfs +"实际:"+hfe+ByString.getTimeStr(MaStation.getInstance().getMoGJH().xGjhzwDto.getFactTime(), "HH:mm")));
//							}else if("cache".equals(result)){
//								ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
//							}else{
//								Toast.makeText(AcMBL.this, result, Toast.LENGTH_SHORT).show();
//							}
//						}else{
//							ByDialog.showMessage(mcontext, "网络信号不好！");
//						}
//
//					}
//				}
			}
			
//				@WebParam(name = "gjhID") String gjhID,
//				@WebParam(name = "swh") String swh,
//				@WebParam(name = "gdm") String gdm,
//				@WebParam(name = "icCard") String icCard);
//				String reportQsgjhFact(String icCardNo,String gjhID,String swh,String gdm)
			 private class ReportQsgjhFact extends AsyncTask<String, Void, String> {
					@Override
					protected String doInBackground(String... params) {
						String idcard = params[0];
						String gjhID = params[1];
						String swh = params[2];
						String gdm = params[3];
						return MaStation.getInstance().getMaWebService().reportQsgjhFact(idcard,gjhID,swh,gdm);
					}
					
					@Override
					protected void onPreExecute() {
						progressDialog = new ProgressDialog(AcMBL.this);
						progressDialog.setMessage("正在提交,请稍候...");
						progressDialog.setIndeterminate(true);
						progressDialog.setCancelable(true);
						progressDialog.setCanceledOnTouchOutside(false);
						progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialogInterface) {
								if (fact != null) {
									fact.cancel(true);
								}
							}
						});
						progressDialog.show();
					}
					
					@Override
					protected void onPostExecute(String result) {
						if(isNetworkConnected()||MaStation.getInstance().isTest()){
							if(result != null  && !"".equals(result)){
								if(result.equals("true")){
									IDCard.setText("");
									Toast.makeText(AcMBL.this, "对位成功", Toast.LENGTH_SHORT).show();
									MaStation.getInstance().getMoGJH().xGjhzwDto.setFactTime(new Timestamp(System.currentTimeMillis()));
									tvActualtime.setText(Html.fromHtml(hfs +"实际:"+hfe+ByString.getTimeStr(MaStation.getInstance().getMoGJH().xGjhzwDto.getFactTime(), "HH:mm")));
								}else if("cache".equals(result)){
									ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
								}else{
									Toast.makeText(AcMBL.this, result, Toast.LENGTH_SHORT).show();
								}

							}else{
								ByDialog.showMessage(mcontext, "网络信号不好！");
							}
						}else{
							ByDialog.showMessage(mcontext, "wifi未开启！");
						}
						if(progressDialog != null){
							progressDialog.cancel();
						}
					}
					
				}	
			 
			 private class PaiBanFact extends AsyncTask<String, Void, String> {
				@Override
				protected String doInBackground(String... params) {
					String gjhID = params[0];
					String gdm = params[1];
					return "";
//						needcall	MaStation.getInstance().getMaWebService().paiBanFact(gjhID, gdm);
				}
				
				@Override
				protected void onPreExecute() {
					progressDialog = new ProgressDialog(AcMBL.this);
					progressDialog.setMessage("正在提交,请稍候...");
					progressDialog.setIndeterminate(true);
					progressDialog.setCancelable(true);
					progressDialog.setCanceledOnTouchOutside(false);
					progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialogInterface) {
							if (paiBanFact != null) {
								paiBanFact.cancel(true);
							}
						}
					});
					progressDialog.show();
				}
				
				@Override
				protected void onPostExecute(String result) {
					if(result != null  && !"".equals(result)){
						if(result.equals("true")){
							ByDialog.showMessage(AcMBL.this, "派班成功");
//							Toast.makeText(AcMBL.this, "派班成功", Toast.LENGTH_SHORT).show();
						}else if("cache".equals(result)){
							ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
						}else{
							ByDialog.showMessage(AcMBL.this,result);
//							Toast.makeText(AcMBL.this, result, Toast.LENGTH_SHORT).show();
						}
					}else{
						ByDialog.showMessage(mcontext, "网络信号不好！");
//						Toast.makeText(mcontext, result, Toast.LENGTH_SHORT).show();
					}
					if(progressDialog != null){
						progressDialog.cancel();
					}
				}
			}
			 
			@Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				super.onActivityResult(requestCode, resultCode, data);
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
			
			private void save(String mCurrentPhotoPath) {

				if (mCurrentPhotoPath != null) {

					try {
						File f = new File(mCurrentPhotoPath);
						Bitmap bm = PictureUtil.getSmallBitmap(mCurrentPhotoPath);
						 LoadImage loadImage = new LoadImage(f.getPath(),bm);
//							 imgAdapter1.addPhoto(loadImage);
//			                 fileNameList1.add(loadImage);  		
						FileOutputStream fos = new FileOutputStream(f);
						bm.compress(Bitmap.CompressFormat.JPEG, 20, fos);
						Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();
						 if(bm!=null&&bm.isRecycled())
					       {
					          bm.recycle();
					       }
//						 photoName的产生方法是勾计划正文的主键,gjhzwDto.getGjhId+"@"+gjhzwDto.getSwh
						 upload(loadImage.getFileName(),cid);
					} catch (Exception e) {

					}

				} else {
					Toast.makeText(this, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
				}
			}
			// 设置菜单
			@Override
			public boolean onCreateOptionsMenu(Menu menu) {
				getMenuInflater().inflate(R.menu.mbl_menu2, menu);
				return true;
			}		
		    @Override  
		    public boolean onKeyDown(int keyCode, KeyEvent event) {  
		        if(keyCode == KeyEvent.KEYCODE_BACK){    //点击返回按键  
		        	buHomeOnClick();
		            return true;  
		        }  
		        return super.onKeyDown(keyCode, event);  
		    }  	
		    

}
