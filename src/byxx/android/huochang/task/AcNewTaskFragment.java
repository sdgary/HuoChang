package byxx.android.huochang.task;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.pdf417.PDF417Reader;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.jf.GdmsUnloadJfDTO;

import byxx.android.huochang.traininfo.AcTrainInfoFragment;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AcNewTaskFragment extends Fragment{
	String taskflag;
	String gdm;
	ListView listView;
	TextView tvTime;
	TextView tvState;
	TextView idcardnum;
	BaTask mbaTask;
	BaTaskTrain mBaTaskTrain;
	Handler handler;
	Context mcontext ;
	ListView lv = null;
	Button buReturnHome;
	Button refresh;
	ProgressDialog dialog = null;
	RuTask ruTask ;
	List<ZydDto> nListShow ;
	boolean intRunType = true;
	int type ;
	String valueid = null ;
//	AlertDialog.Builder  dialogshow = null;
//	ProgressDialog progressDialog;
	boolean run = false;
	boolean usenfc = false;
	int pbflag = 1; // 0未派班 1 已派班 3已完成
	 public static AcNewTaskFragment newInstance(String gdm,String content,int pbflag) {
		 AcNewTaskFragment fragment = new AcNewTaskFragment();
		 	fragment.gdm = gdm;
		 	fragment.taskflag = content;
		 	fragment.pbflag = pbflag;
	        return fragment;
	    }


	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	         return inflater.inflate(R.layout.tasktab, container, false);
	    }

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	    	// TODO Auto-generated method stub
	    	super.onActivityCreated(savedInstanceState);
	    	mcontext = getView().getContext();
	    	tvState = (TextView)getView().findViewById(R.id.tvstate);
	    	idcardnum = (TextView)getView().findViewById(R.id.idcardnum);
			tvTime = (TextView)getView().findViewById(R.id.tvtime);		
			listView = (ListView)getView().findViewById(R.id.lv_task);
			refresh = (Button)getView().findViewById(R.id.refresh);
			listView.setAdapter(getBaTask());
				
//			progressDialog = new ProgressDialog(mcontext);
//			progressDialog.setMessage("正在加载数据,请稍候...");
			refresh.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
	    			ReadTask readtask = new ReadTask();
	    			readtask.execute(gdm);
					ReadPos readPos = new ReadPos();
					readPos.execute(gdm);
	    			tvState.setTextSize(30);
	    			tvState.setText(gdm+"读取中...");
				}
			});

	    }

	    
	    @Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			try{
//			if(isTrain()){
//				nListShow = MoTask.deepCopy(MaStation.getInstance().getMoTask().getHZydDtos());
//				if(nListShow != null){
//				List<ZydDto> fList = new ArrayList<ZydDto>();
//				List<ZydDto> unfList = new ArrayList<ZydDto>();
//				int size = nListShow.size();
//			for(int i = 0 ;i<size;i++){
//				if(nListShow.get(i).getPbFlag() == 3){
//					fList.add(nListShow.get(i));
//				}else{
//					unfList.add(nListShow.get(i));
//				}
//			}
//			if(pbflag == 3){
//				getBaTask().setDatas(nListShow);
//			}else{
//				getBaTask().setDatas(unfList);
//			}
//				}
//		}else if(isTruck()){
//				nListShow = MoTask.deepCopy(MaStation.getInstance().getMoTask().getQZydDtos());
//				if(nListShow != null){
//				List<ZydDto> fList = new ArrayList<ZydDto>();
//				List<ZydDto> unfList = new ArrayList<ZydDto>();
//				int size = nListShow.size();
//			for(int i = 0 ;i<size;i++){
//				if(nListShow.get(i).getPbFlag() == 3){
//					fList.add(nListShow.get(i));
//				}else{
//					unfList.add(nListShow.get(i));
//				}
//			}
//			if(pbflag == 3){
//				getBaTask().setDatas(nListShow);
//			}else{
//				getBaTask().setDatas(unfList);
//			}
//		}
	
//		}
//				getBaTask().notifyDataSetChanged();
	    		
					ReadPos readPos = new ReadPos();
					readPos.execute(gdm);
			}catch(Exception e){
				Log.v("ba",e+"");
			}
		}
		
	    @Override
	    public void onStart() {
	    	// TODO Auto-generated method stub
			try {
				if(pbflag == 1 && isTruck()){
					MaStation.getInstance().getMoNfc().setHandler(getHandler());
					MaStation.getInstance().handlerTask=getHandler();
				}
				run = true;
				if(!usenfc){
					getHandler().postDelayed(Runnabletask,0);
				}else{
					usenfc = false;
				}

			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
	    	super.onStart();
	    }
		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();

//			progressDialog.dismiss();

		}
		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			run =false;
			getHandler().removeCallbacks(Runnabletask);
		}
		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if(pbflag == 1 && isTruck() ){
				if (MaStation.getInstance().handlerTask != null) {
					MaStation.getInstance().getMoNfc().setHandler(
							MaStation.getInstance().handlerTask);
				}
			}

		}
		public BaseTask getBaTask() {
			if(isTruck()){
			if(mbaTask == null){
				mbaTask = new BaTask(getHandler());
				mbaTask.setTaskflag(taskflag);
			}
			return mbaTask;
			}else{
				if(mBaTaskTrain == null){
					mBaTaskTrain = new BaTaskTrain(getHandler());
					mBaTaskTrain.setTaskflag(taskflag);
				}
				return mBaTaskTrain;
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

		private void dealHandleMessage(Message msg) {
			try {
				if (msg == null)
					return;
				int dId = 0; // 对话框的标识
				boolean closeDialog = true;
				try {
					// 读取完后设置数据
					Bundle bundle = msg.getData();
					boolean ballreal =false;
					switch (msg.what) {
					case Constant.MSG_ID_NFC: // nfc返回
						String regionId = msg.getData().getString("regionId");
						getNFC(regionId);
						sort(regionId);
						break;
//					case Constant.MSG_ID_READ_TASK_TRAIN:
//						ballreal = bundle.getBoolean("realalltask");
//
//						if(isNetworkConnected()|| MaStation.getInstance().isTest()){
//						if (ballreal) {
//
//							tvState.setTextColor(Color.WHITE);
//							tvState.setText("");
//							tvState.setTextSize(24);
//							showData(ballreal);
//						}else{
//							showData(ballreal);
//							tvState.setTextColor(Color.RED);
//							tvState.setTextSize(30);
//							tvState.setText("暂无数据");
//						}}else{
//							tvState.setTextSize(30);
//							tvState.setTextColor(Color.RED);
//							tvState.setText("网络错误，请注意");
//						}
//						tvTime.setText(ByString.getTimeStr(System.currentTimeMillis(), "dd日 HH:mm"));
//
//						ruTask.close();
//						break;
//					case Constant.MSG_ID_READ_TASK_TRUCK:
//						ballreal = bundle.getBoolean("realalltask");
//
//						if(isNetworkConnected() || MaStation.getInstance().isTest()){
//						if (ballreal) {
//							
//							tvState.setTextColor(Color.WHITE);
//							tvState.setText("");
//							tvState.setTextSize(24);
//							showData(ballreal);
//						}else{
//							showData(ballreal);
//							tvState.setTextColor(Color.RED);
//							tvState.setTextSize(30);
//							tvState.setText("暂无数据");
//						}
//						}else{
//							tvState.setTextSize(30);
//							tvState.setTextColor(Color.RED);
//							tvState.setText("网络错误，请注意");
//						}
//						tvTime.setText(ByString.getTimeStr(System.currentTimeMillis(), "dd日 HH:mm"));
//
//						ruTask.close();
//					
//						break;
					case Constant.MSG_ID_SENT_TASK_START_R:
						dealR(RuTask.SENT_TASK_START);
					break;
					case Constant.MSG_ID_SENT_TASK_START:

						dealCommit(msg.getData(), RuTask.SENT_TASK_START);
					break;
					case Constant.MSG_ID_SENT_TASK_OVER_R:
						dealR(RuTask.SENT_TASK_OVER);
					break;
					case Constant.MSG_ID_SENT_TASK_OVER:

						dealCommit(msg.getData(), RuTask.SENT_TASK_OVER);
					break;
					case Constant.MSG_ID_SENT_TASK_OVER_HW_R:
						dealR(RuTask.SENT_TASK_OVER_HW);
						break;
					case Constant.MSG_ID_SENT_TASK_OVER_HW:
						dealCommit(msg.getData(), RuTask.SENT_TASK_OVER_HW);
						break;
					default:
					
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
//				MaStation.getInstance().recordExc(e);
			}
			return;
		}
		
		private void sort(String idcard) {
			// TODO Auto-generated method stub
			
			
			try {
				List<ZydDto> dtos = new ArrayList<ZydDto>();
				dtos = MoTask.deepCopy(MaStation.getInstance().getMoTask().getQZydDtos());
				List<ZydDto> unfList = new ArrayList<ZydDto>();
//				int size = dtos.size();
				for(int i = 0 ;i<dtos.size();i++){
					if(dtos.get(i).getPbFlag() == 1){
						if(dtos.get(i).getCardId().equals(idcard)){
							unfList.add(dtos.get(i));
							dtos.remove(i);
						}
					}
				}
				for(int i = 0 ;i<dtos.size();i++){
					 if(dtos.get(i).getPbFlag() == 1){
						 unfList.add(dtos.get(i));
					 }
				}
				if(pbflag == 1){
					
					listView.setAdapter(getBaTask());
					getBaTask().setDatas(unfList);
					getBaTask().notifyDataSetChanged();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}


		private void dealCommit(Bundle bundle, int type) {
			// TODO Auto-generated method stub

			try {
				// 刷新

			if(bundle.getString("ok")!= null){
					if(bundle.getString("ok").toUpperCase().contentEquals("TRUE")){
					int id = bundle.getInt("jobId");
					ZydDto task = null;
					if(isTrain()){
					task = MaStation.getInstance().getMoTask().findbyidHM(id,gdm);
					}else if(isTruck()){
						task = MaStation.getInstance().getMoTask().findbyidQ(id);
					}
						if (task != null) {
							Log.v("task","ac  刷新"+task.getZypbId()+"  "+task.getCh());
							Timestamp tiCur = new Timestamp(System.currentTimeMillis());
							if(type == RuTask.SENT_TASK_START){
								if(task.isKS){
									task.setKsTime(tiCur);
								}else{
									task.setKsTime(null); 
								}
							}
							else if(type == RuTask.SENT_TASK_OVER){
								if(task.isJS){
									task.setZzTime(tiCur);
									task.setPbFlag(3);
								}else{
									task.setZzTime(null);
									task.setPbFlag(0);
								}
							}
							else if(type == RuTask.SENT_TASK_OVER_HW){
								if(task.isJS){
									task.setZzTime(tiCur);
									task.setXw(MaStation.getInstance().getMoTask().getHW());
									task.setPbFlag(3);
								}else{
									task.setZzTime(null);
									task.setXw(null);
									task.setPbFlag(0);
								}
							}
							MaStation.getInstance().getMoTask().setfindbyidHM(id, gdm, task);
						}
					}else if(bundle.getString("ok").toUpperCase().contentEquals("FAIL")){
						ByDialog.showMessage(getView().getContext(), "服务器其他原因，保存失败");
					}else{
						ByDialog.showMessage(getView().getContext(), " "+bundle.getString("ok").toString());
					}
				}else {
					ByDialog.showMessage(getView().getContext(), "保存失败，请注意");
				}
				
				if(isTrain()){
					MaStation.getInstance().getMoTask();
						//nListShow = MoTask.deepCopy(MaStation.getInstance().getMoTask().getHZydDtos());
	//					getBaTask().setDatas(nListShow);
					   if(MoTask.getHMHzydDtos().get(gdm) != null){  
//						nListShow.clear();
						nListShow = MoTask.deepCopy(MoTask.getHMHzydDtos().get(gdm));
//						List<ZydDto> fList = new ArrayList<ZydDto>();
//						List<ZydDto> unfList = new ArrayList<ZydDto>();
//						int size = nListShow.size();
//					for(int i = 0 ;i<size;i++){
//						if(nListShow.get(i).getPbFlag() == 3){
//							fList.add(nListShow.get(i));
//						}else{
//							unfList.add(nListShow.get(i));
//						}
//					}
//					if(pbflag == 3){
//						getBaTask().setDatas(fList);
//					}else{
//						getBaTask().setDatas(unfList);
//					}
					getBaTask().setDatas(nListShow);
					}
				}else if(isTruck()){
//					nListShow = MoTask.deepCopy(MaStation.getInstance().getMoTask().getQZydDtos());
//					getBaTask().setDatas(nListShow);
						nListShow = MoTask.deepCopy(MaStation.getInstance().getMoTask().getHMHzydDtos().get(gdm));
//						List<ZydDto> fList = new ArrayList<ZydDto>();
//						List<ZydDto> unfList = new ArrayList<ZydDto>();
//						int size = nListShow.size();
//					for(int i = 0 ;i<size;i++){
//						if(nListShow.get(i).getPbFlag() == 3){
//							fList.add(nListShow.get(i));
//						}else{
//							unfList.add(nListShow.get(i));
//						}
//					}
//					if(pbflag == 3){
//						getBaTask().setDatas(fList);
//					}else{
//						getBaTask().setDatas(unfList);
//					}
						getBaTask().setDatas(nListShow);
				}
			
				
				getBaTask().notifyDataSetChanged();
				tvState.setText("");
//				progressDialog.dismiss();
				tvTime.setText("按钮刷新时间 "+ByString.getTimeStr(System.currentTimeMillis(),"dd日 HH:mm:ss"));
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
		
		}
		

		private void dealR(int type) {
			// TODO Auto-generated method stub
			showDialogM(1);
			if(ruTask != null){
				ruTask.close();
			}
			ZydDto ttask = null;
			if(isTrain()){
				ttask= MaStation.getInstance().getMoTask().getTaskSelectH();
			}else if(isTruck()){
				ttask= MaStation.getInstance().getMoTask().getTaskSelectQ();
			}
			ruTask = new RuTask(getHandler());
			ruTask.setType(type);
			ruTask.setTask(ttask);
			ruTask.start();
		}

		private void showDialogM(int type) {
			try {
//				showDialog(type);
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
		}
		

		private void showData(boolean havedate) {
			try {
				if(isTrain()){
					if(havedate){
						nListShow = MoTask.deepCopy(MaStation.getInstance().getMoTask().getHZydDtos());
						MaStation.getInstance().getMoTask().getHMHzydDtos().put(gdm, nListShow);
//						List<ZydDto> fList = new ArrayList<ZydDto>();
//						List<ZydDto> unfList = new ArrayList<ZydDto>();
//						int size = nListShow.size();
//					for(int i = 0 ;i<size;i++){
//						if(nListShow.get(i).getPbFlag() == 3){
//							if(nListShow.get(i).getGdm().equals(gdm)){
//								fList.add(nListShow.get(i));
//							}
//						}else{
//							unfList.add(nListShow.get(i));
//						}
//					}
//					if(pbflag == 3){
//						
//						getBaTask().setDatas(fList);
//					}else{
//						getBaTask().setDatas(unfList);
//					}
//					List<ZydDto> List = new ArrayList<ZydDto>();
//					for(int i = 0;i<nListShow.size();i++){
//						if(nListShow.get(i).getGdm() != null && nListShow.get(i).getGdm().equals(gdm)){
//							List.add(nListShow.get(i));
//						}
//					}
					getBaTask().setDatas(nListShow);
				}else{
//					nListShow.clear();
//					getBaTask().setDatas(nListShow);
					}
				getBaTask().notifyDataSetChanged();

			}else if(isTruck()){
				if(havedate){
						nListShow = MoTask.deepCopy(MaStation.getInstance().getMoTask().getQZydDtos());
//						List<ZydDto> fList = new ArrayList<ZydDto>();
//						List<ZydDto> unfList = new ArrayList<ZydDto>();
//						int size = nListShow.size();
//					for(int i = 0 ;i<size;i++){
//						if(nListShow.get(i).getPbFlag() == 3){
//							if(nListShow.get(i).getGdm().equals(gdm)){
//								fList.add(nListShow.get(i));
//							}
//						}else{
//							unfList.add(nListShow.get(i));
//						}
//					}
//					if(pbflag == 3){
//						getBaTask().setDatas(fList);
//					}else{
//						getBaTask().setDatas(unfList);
//					}
//						List<ZydDto> List = new ArrayList<ZydDto>();
//						for(int i = 0;i<nListShow.size();i++){
//							if(nListShow.get(i).getGdm() != null && nListShow.get(i).getGdm().equals(gdm)){
//								List.add(nListShow.get(i));
//							}
//						}
						getBaTask().setDatas(nListShow);
				}else{
//					nListShow.clear();
//					getBaTask().setDatas(nListShow);
				}
				getBaTask().notifyDataSetChanged();
			}
				
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
		}
		
		public RuTask getTask() {
			if (ruTask == null) {
				ruTask= new RuTask(getHandler());
				
			}
			return ruTask;
		}
		
		  private Runnable Runnabletask = new Runnable() {  
//		      int count = 0;
		        public void run() {  
		            // TODO Auto-generated method stub  
//		            Log.v("runTF", "run "+count ++);
//					setType(Rufault.REAL_ALL_TICKETOFFICE_ERR);
//		        	if(isTrain()){
//		        		getTask().setType(RuTask.REAL_ALL_TASK_TRAIN);
//		        		getTask().setGDM(gdm);
		        		
		    			ReadTask readtask = new ReadTask();
		    			readtask.execute(gdm);

						tvState.setTextSize(30);
		    			tvState.setText(gdm+"读取中...");

//		        	}else if(isTruck()){
//		        		getTask().setType(RuTask.REAL_ALL_TASK_TRUCK);
//		        		getTask().setGDM(gdm);
		        		
		        		
//		        		ReadTask readtask = new ReadTask();
//		        		readtask.execute(gdm);
//		        	}
//		        	Log.v("dl", (count++) + "");
//		    		getTask().start();
//		    		progressDialog.show();
		            if (run) {
		            	getHandler().postDelayed(this, 1000*5*60);  
		            }  
		        }  
		    };  

		    boolean isTruck(){
		    	return taskflag.equals("truck");
		    }
		    boolean isTrain(){
		    	return taskflag.equals("train");
		    }
		    

			private void getNFC(String regionId) {
				try {

					idcardnum.setText(regionId);

					if (regionId == null || regionId.equals("")) {
						
						return;
					}
					}catch (Exception e) {
					
				 }
			}

 
			 private class ReadTask extends AsyncTask<String, Void, String> {
				 
					@Override
					protected String doInBackground(String... params) {
						String tgdm = params[0];
						String s = null;
						
						if(isTrain()){
//						MaStation.getInstance().getMoTask().HzydDtos 
						s =  "";
//						needcall		MaStation.getInstance().getMaWebService().getZydsSTRNew(tgdm);
//						(MaStation.stationCode,
//								MaStation.getInstance().getUser().getPostName()!= null ? MaStation.getInstance().getUser().getPostName():"",
//										MaStation.getInstance().getUser().getTeams() != null ? MaStation.getInstance().getUser().getTeams():"",
//										MaStation.getInstance().getUser().getWorkDay() != null? MaStation.getInstance().getUser().getWorkDay():"");// MaStation.getInstance().ge);
						}
						if(isTruck()){
//						MaStation.getInstance().getMoTask().QzydDtos 
						s = "";
//						needcall		MaStation.getInstance().getMaWebService().getZydTrucksSTRNew(tgdm);
//								(
//								MaStation.stationCode,
//								MaStation.getInstance().getUser().getPostName()!= null ? MaStation.getInstance().getUser().getPostName():"",
//										MaStation.getInstance().getUser().getTeams() != null ? MaStation.getInstance().getUser().getTeams():"",
//												MaStation.getInstance().getUser().getWorkDay() != null? MaStation.getInstance().getUser().getWorkDay():"");
						}
					
						return s;
					}
					
					@Override
					protected void onPreExecute() {
					}
					
					@Override
					protected void onPostExecute(String result) {

						try{
							if(isNetworkConnected()||MaStation.getInstance().isTest()){
							if(result != null && !result.equals("[]") && !"".equals(result)){
								if("cache".equals(result)){
									tvState.setTextColor(Color.RED);
									tvState.setTextSize(30);
									tvState.setText("暂无数据");
									showData(false);
									ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
								}else{
									GsonBuilder gb = new GsonBuilder();
									gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
									Gson gson = gb.create();
									if(isTrain()){
										MaStation.getInstance().getMoTask().HzydDtos  = gson.fromJson(result, new TypeToken<LinkedList<ZydDto>>(){}.getType());
									}
									if(isTruck()){
										MaStation.getInstance().getMoTask().QzydDtos  = gson.fromJson(result, new TypeToken<LinkedList<ZydDto>>(){}.getType());;
									}
									tvState.setTextColor(Color.WHITE);
									tvState.setText("");
									tvState.setTextSize(24);
									showData(true);
									tvTime.setText(ByString.getTimeStr(System.currentTimeMillis(), "dd日 HH:mm"));
								}

							}else{
								tvState.setTextColor(Color.RED);
								tvState.setTextSize(30);
								tvState.setText("暂无数据");
								showData(false);
								ByDialog.showMessage(mcontext, "网络异常");
							}

							}else{
								tvState.setTextSize(30);
								tvState.setTextColor(Color.RED);
								tvState.setText("网络错误，请注意");
							}
						}catch(Exception e){

						}
					}
					
				}	
			 String tgdm ;
			 private class ReadPos extends AsyncTask<String, Void, String> {
				 
				 @Override
				 protected String doInBackground(String... params) {
					 tgdm = params[0];
					 String s = null; 
					 if(isTrain()){
						 s =  MaStation.getInstance().getMaWebService().getContainerPos(tgdm);
					 }				 
					 return s;
				 }
				 
				 @Override
				 protected void onPreExecute() {
				 }
				 
				 @Override
				 protected void onPostExecute(String result) {
					 
					 try{
						 if(isNetworkConnected()||MaStation.getInstance().isTest()){
							 if(result != null && !result.equals("[]") && !"".equals(result)){
								 if("cache".equals(result)){
									 ByDialog.showMessage(mcontext, "网络断开，操作已缓存！");
								 }else{
									 GsonBuilder gb = new GsonBuilder();
									 gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
									 Gson gson = gb.create();
									 if(isTrain()){
										 MaStation.getInstance().getMoTask().containerPos  = gson.fromJson(result,  new TypeToken<LinkedList<GdContainerPos>>(){}.getType());
										 MaStation.getInstance().getMoTask().getHMcontainerPos().put(tgdm, MaStation.getInstance().getMoTask().containerPos);
									 }
								 }
							 }else{
								 ByDialog.showMessage(mcontext, "网络异常");
							 }
							 
						 }else{

						 }
					 }catch(Exception e){
						 
					 }
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
}
