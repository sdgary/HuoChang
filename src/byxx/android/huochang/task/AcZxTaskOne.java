package byxx.android.huochang.task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.common.StringUtils;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.task.TaskDB.ZxTaskDataBase;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.CSort;
import byxx.android.huochang.util.SortParameter;
import byxx.android.huochang.util.WjUtilTime;
import byxx.android.wj.http.cache.LoadingDialog;

import com.alibaba.fastjson.JSON;

public class AcZxTaskOne extends Activity {
	private List<GdContainerPos>  gdList = new ArrayList<GdContainerPos>();//指定股道的箱位信息
	List<String> areaList = new ArrayList<String>();
	List<String> posList = new ArrayList<String>();
	List<String> lastPosList = new ArrayList<String>();
	ArrayAdapter<String> selectAdapterTrack;
	ArrayAdapter<String> selectAdapterArea;
	ArrayAdapter<String> selectAdapterPos;
	ArrayAdapter<String> selectAdapterLastPos;
	ExecuteYX executeYX;
	Start startTask;
	End endTask;
	ProgressDialog progressDialog;
	private Context context;
	GdmsZxzypbDTO dto;
	int gdmLast = 0;
	boolean flag = false;//第一次打开详情，自动选中股道。
	/**
	 * 如果=0,表示是正常状态,按钮显示为拒收,点击后提交的flag为1;
	 * 如果=1,表示在拒收状态,按钮显示为恢复,点击后提交的flag为2;
	 * 如果=2,表示是恢复状态,按钮显示为拒收,点击后提交的flag为1;
	 */
	int reflag = 0;//   拒收标志
	
	LinearLayout mainPanel;

	//计划ID
	TextView zydId;
	//类型
	TextView zylx;
	//数量
	TextView zynum;
	//品名
	TextView zydpm;
	//原箱位
	TextView prehw;
	//股道
	TextView zydgdm;
	//新箱位
	TextView facthw;
	
	//执行状态
	TextView zydzt;
	//开始时间
	TextView zydstart;
	//结束时间
	TextView zydend;
	
	Button start;
//	Button end;
	Button changeXW;
	LinearLayout spinnerLayout;
	Spinner spinnerTrack;
	Spinner spinnerArea;
	Spinner spinnerPos;
	Spinner spinnerLastPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ac_zx_task_one);
		
//		JzxHwzwModifyDTO dto;
		
		mainPanel = (LinearLayout) findViewById(R.id.mainPanel);
		//计划ID
		zydId = (TextView) findViewById(R.id.zydId);
		//类型
		zylx = (TextView) findViewById(R.id.zylx);
		//数量
		zynum = (TextView) findViewById(R.id.zynum);
		
//		zydpm = (TextView) findViewById(R.id.zydpm);
		//原箱位
		prehw = (TextView) findViewById(R.id.prehw);
		//股道
		zydgdm = (TextView) findViewById(R.id.zydgdm);
		//新箱位
		facthw = (TextView) findViewById(R.id.facthw);
		//执行状态
		zydzt = (TextView) findViewById(R.id.zydzt);
		//开始时间
		zydstart = (TextView) findViewById(R.id.zydstart);
		//结束时间
		zydend = (TextView) findViewById(R.id.zydend);
		spinnerLayout = (LinearLayout) findViewById(R.id.spinnerLayout);
		spinnerTrack = (Spinner) findViewById(R.id.spinnerTrack);
		spinnerArea = (Spinner) findViewById(R.id.spinnerArea);
		spinnerPos = (Spinner) findViewById(R.id.spinnerPos);
		spinnerLastPos = (Spinner) findViewById(R.id.spinnerLastPos);
		changeXW = (Button) findViewById(R.id.changeXW);
		changeXW.setVisibility(View.GONE);
		changeXW.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(context, "调整箱位按钮！", Toast.LENGTH_LONG).show();
				changeXW();
			}
		});
		start = (Button) findViewById(R.id.commit);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(dto != null && changeXW.getVisibility() == View.VISIBLE){
					if(spinnerLayout.getVisibility()==View.GONE && dto.getNewHw() == null){
						ByDialog.showMessage(context, "箱位不能空！");
					}else{
						changeFlag(0);
					}
				}else{
					changeFlag(0);
				}
			}
		});

//		end = (Button) findViewById(R.id.cancel);
//		end.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				changeFlag(1);
//			}
//		});
		
		context = this;
		mainPanel.getBackground().setAlpha(210);
		dto = (GdmsZxzypbDTO) getIntent().getSerializableExtra("obj");
//		dto.setGdm("012");//测试
		spinnerWidder();
		initDate();
		initWidget();
		AppManager.getAppManager().addActivity(this);
	}
	
	void changeXW() {

		if (spinnerLayout.getVisibility()==View.GONE) {
			refreshPosData();
		}else {
			spinnerLayout.setVisibility(View.GONE);
		}

	}
	
	private void refreshPosData(){
//		Toast.makeText(context, "调整箱位按钮！", Toast.LENGTH_LONG).show();
//		new Refreshdate().execute();
		List<GdContainerPos> nList = null;
		nList = MaStation.getInstance().getBoxPositions().get(spinnerTrack.getSelectedItem().toString().trim());
		gdList.clear();
		gdList.addAll(nList);
		initFirstSpinner();
		spinnerTrack.setVisibility(View.VISIBLE);
		spinnerLayout.setVisibility(View.VISIBLE);
		
	}
	
	//调整箱位数据加载异步线程
//	private class Refreshdate extends AsyncTask<String, Void, String> {
//		@Override
//		protected String doInBackground(String... params) {
//			return MaStation.getInstance().getMaWebService().getContainerPos(spinnerTrack.getSelectedItem().toString());
//		}
//
//		@Override
//		protected void onPreExecute() {
//
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			if(result != null && !result.equals("[]")){
//				List<GdContainerPos> nList = null;
//				StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
//				nList = JSON.parseArray(info.getData().toString(), GdContainerPos.class);
//				if(nList != null && nList.size()>0){
//					gdList.clear();
//					gdList.addAll(nList);
//					initFirstSpinner();
//				}
//
//			}
//		}
//	}
	
	private class ExecuteYX extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			return MaStation.getInstance().getMaWebService()
					.reportYxjh(params[0], Integer.valueOf(params[1]), params[2], params[3]);
		}
		
		@Override
		protected void onPreExecute() {

		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result != null && !result.equals("[]") && !"".equals(result)){
				if("cache".equals(result)){
					ByDialog.showMessage(context, "网络断开，操作已缓存！");
				}else{
					Dialog loadingDialog =  LoadingDialog.create(context, "成功执行", LoadingDialog.RIGHT);
					loadingDialog.setOnDismissListener(new OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
							finish();
						}
					});

				}

			}else{
				ByDialog.showMessage(context, "网络异常");
			}
		}
	}
	
	private void spinnerWidder(){
		selectAdapterTrack = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,MaStation.getInstance().getUser().getGdm());
		selectAdapterTrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTrack.setAdapter(selectAdapterTrack);
		if(dto != null && dto.getGdm()!= null){
			String [] arr = MaStation.getInstance().getUser().getGdm();
			for(int i=0; i<arr.length;i++){
				if(arr[i].equals(dto.getGdm())){
					gdmLast = i;
				}
			}
		}
		spinnerTrack.setVisibility(View.GONE);
		spinnerTrack.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				refreshPosData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

		});

		spinnerTrack.setSelection(gdmLast);

		selectAdapterArea = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,areaList);
		selectAdapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArea.setAdapter(selectAdapterArea);
		spinnerArea.setVisibility(View.GONE);
		spinnerArea.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				initSecondSpinner(); 
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
			
		});
		
		selectAdapterPos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,posList);
		selectAdapterPos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPos.setAdapter(selectAdapterPos);
		spinnerPos.setVisibility(View.GONE);
		spinnerPos.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				initThirdData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		selectAdapterLastPos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lastPosList);
		selectAdapterLastPos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLastPos.setAdapter(selectAdapterLastPos);
		spinnerLastPos.setVisibility(View.GONE);
		
	}
	
    //下拉单组合数据加载
	private void initFirstSpinner(){
		//first:clear data
		areaList.clear();
		if(gdList != null && gdList.size()>0){
			
			for (int i = 0; i < gdList.size(); i++) {
				areaList.add(gdList.get(i).getGdm());
			}
			
			if (!isEmpty(areaList)) {
				//give init data
//					if (spinnerArea.getSelectedItem()==null) {
					spinnerArea.setSelection(0);
//					}
				spinnerArea.setVisibility(View.VISIBLE);
				initSecondSpinner();
			}
			selectAdapterArea.notifyDataSetChanged();
		}
	}
	
	private void initSecondSpinner(){
		//first:clear data
		posList.clear();
		String area = spinnerArea.getSelectedItem().toString();
		GdContainerPos dto =null;
		List<ContainerMiddlePos> list = null;
		if(gdList != null && gdList.size()>0){
			
			for (int i = 0; i < gdList.size(); i++) {
				dto = gdList.get(i);
				if (dto.getGdm().equals(area)) {
					list = dto.getMiddlePos();
				}
			}
			
			if(list != null && list.size()>0){
				CSort.getInstance().sort(list, new SortParameter("", ContainerMiddlePos.class, new String[]{"middlePosName"}, new Integer[]{CSort.SORT_ASC}));
				for (int i = 0; i < list.size(); i++) {
					posList.add(list.get(i).getMiddlePosName());
				}
				if (!isEmpty(posList)) {
					//give init data
//					if (spinnerPos.getSelectedItem()==null) {
						spinnerPos.setSelection(0);
//					}
					spinnerPos.setVisibility(View.VISIBLE);
					initThirdData();
				}else {
					spinnerLastPos.setVisibility(View.GONE);
				}
				selectAdapterPos.notifyDataSetChanged();
			}
		}
	}
	
	private void initThirdData(){
		//first:clear data
		lastPosList.clear();
		String area = spinnerArea.getSelectedItem().toString();
		String pos = spinnerPos.getSelectedItem().toString();
		
		GdContainerPos dto =null;
		List<ContainerMiddlePos> list = null;
		List<String> list2 = null;
		if(gdList != null && gdList.size()>0){
			
			for (int i = 0; i < gdList.size(); i++) {
				dto = gdList.get(i);
				if (dto.getGdm().equals(area)) {
					list = dto.getMiddlePos();
				}
			}
			
			if(list != null && list.size()>0){
				
				for (int i = 0; i < list.size(); i++) {
					if (pos.equals(list.get(i).getMiddlePosName())) {
						list2 = list.get(i).getPos();
						Collections.sort(lastPosList);
					}
				}
				
				if(list2 != null && list2.size()>0){
					for (int i = 0; i < list2.size(); i++) {
						lastPosList.add(list2.get(i));
					}
					if (!isEmpty(lastPosList)) {
						//give init data
						spinnerLastPos.setSelection(0);
						spinnerLastPos.setVisibility(View.VISIBLE);
					}
					selectAdapterLastPos.notifyDataSetChanged(); 
				}
				
			}
			
		}

	}
	
	private void initWidget(){
	  if (dto.getKsTime()==null) {//未开始
			start.setVisibility(View.VISIBLE);
			start.setText("结束作业");
		  start.setBackgroundResource(R.drawable.button_yellow);
//			end.setVisibility(View.GONE);

		  //集装箱类型才显示
		  if(dto != null && dto.getCreateType() == GdmsZxzypbDTO.CREATE_TYPE_YXJH
				  && (GdmsZxzypbDTO.ZXDM_ZX.equals(StringUtils.format(dto.getZxdm())))
				  || GdmsZxzypbDTO.ZXDM_XQ.equals(StringUtils.format(dto.getZxdm()))
				  && dto.getKsTime() == null){
			  changeXW.setVisibility(View.VISIBLE);
		  }else {
			  changeXW.setVisibility(View.GONE);
		  }

		}else if(dto.getKsTime() != null && dto.getZzTime() != null ){//已开始，未结束。（@WGary 2015/11/19 已开始则已结束）
			start.setVisibility(View.VISIBLE);
			start.setText("取消结束");
		  start.setBackgroundResource(R.drawable.button_red);
//			end.setVisibility(View.VISIBLE);
//			end.setText("结束作业");
			changeXW.setVisibility(View.GONE);
			spinnerLayout.setVisibility(View.GONE);
		}else if(dto.getZzTime() != null ){//已结束。
			start.setVisibility(View.VISIBLE);// 结束可见原取消开始按钮（@WGary 2015/11/19 已结束则已开始）
			start.setText("取消结束");
		  start.setBackgroundResource(R.drawable.button_red);
//			end.setVisibility(View.VISIBLE);
//			end.setText("取消结束");
			changeXW.setVisibility(View.GONE);
			spinnerLayout.setVisibility(View.GONE);
		}else{
			start.setVisibility(View.VISIBLE);
			start.setText("结束作业");
		  start.setBackgroundResource(R.drawable.button_yellow);
//			end.setVisibility(View.GONE);
		  //集装箱类型才显示
		  if(dto != null && dto.getCreateType() == GdmsZxzypbDTO.CREATE_TYPE_YXJH
				  && (GdmsZxzypbDTO.ZXDM_ZX.equals(StringUtils.format(dto.getZxdm())))
				  || GdmsZxzypbDTO.ZXDM_XQ.equals(StringUtils.format(dto.getZxdm()))
				  && dto.getKsTime() == null){
			  changeXW.setVisibility(View.VISIBLE);
		  }else {
			  changeXW.setVisibility(View.GONE);
		  }
		}

	}
	
	private void initDate(){
		zydId.setText("派班ID:"+dto.getZypbId());
		
		if(dto.getCreateType() == GdmsZxzypbDTO.CREATE_TYPE_YXJH){
			zynum.setText("集装箱号码:"+dto.getXh());
		}else{
			zynum.setText("品名:"+dto.getHwpm());
		}
		if(dto.getKsTime() == null){
			zydstart.setText("开始时间:未开始");
		}else{
			zydstart.setText("开始时间:" + WjUtilTime.HHmm.format(dto.getKsTime()) );
		}
		
		zylx.setText("类型：" + GdmsZxzypbDTO.getZxdmName(dto.getZxdm()));
		zydgdm.setText("股道码："+dto.getGdm());
		prehw.setText("原位:"+dto.getOldHw());
		if(dto.getNewHw() == null){
			facthw.setText("新位:待定");
		}else{
			facthw.setText("新位:" +dto.getNewHw());
		}

		zydzt.setText("派班状态："+dto.getPbFlagStr());
		
		if(dto.getZzTime() == null){
			zydend.setText("结束时间:未结束");
		}else{
			zydend.setText("结束时间:" + WjUtilTime.HHmm.format(dto.getZzTime()));
		}
	}
	
	private void commonStartFunc(int flag){

		String id = String.valueOf(dto.getZypbId());
		
//		MaStation.getInstance().getMaWebService().reportYxjh(dto.getPk(), flag, xw, gdm);
		//按顺序传入线程中，懒的编格式了！ @WGary 2015/06/09
		startTask = new Start();
		startTask.execute(id, String.valueOf(flag));

	}
	
	private void commonEndFunc(int flag){

		String id = String.valueOf(dto.getZypbId());
		
//		MaStation.getInstance().getMaWebService().reportYxjh(dto.getPk(), flag, xw, gdm);
		//按顺序传入线程中，懒的编格式了！ @WGary 2015/06/09
		endTask = new End();
		endTask.execute(id, String.valueOf(flag));

	}
	

	void start(int f){//移交计划提交
		commonStartFunc(f);
	}
	

	void end(int f){
		commonEndFunc(f);
	}
 
	  private class Start extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... params) {
				String result = "";
				if (spinnerArea.getSelectedItem()!=null&&start.getText().equals("结束作业")) {
					String xw = spinnerArea.getSelectedItem().toString()+
							  spinnerPos.getSelectedItem().toString()+
							  spinnerLastPos.getSelectedItem().toString();

					if(MaStation.getInstance().isConnected()){
						result = MaStation.getInstance().getMaWebService().reportZxzyStart(params[0], Integer.valueOf(params[1]),xw);
					}else{
						MaStation.getInstance().getMaWebService().reportZxzyStart(params[0], Integer.valueOf(params[1]), xw);//将请求加入缓存
						StandReturnInfo info = new StandReturnInfo();
						info.setSuccess(true);
//						dto.setKsTime(new Timestamp(System.currentTimeMillis()));//记录当前时间
						if(Integer.valueOf(params[1]) == 0){
							dto.setPbFlag(1);//未开始作业标识
							dto.setKsTime(null);
							dto.setZzTime(null);//记录当前时间
							dto.setNewHw(null);//填充新箱位信息
						}else if(Integer.valueOf(params[1]) == 1){
							dto.setPbFlag(3);//已开始作业标识
							dto.setKsTime(new Timestamp(System.currentTimeMillis() - 5 * 60 * 1000));
							dto.setZzTime(new Timestamp(System.currentTimeMillis()));//记录当前时间
							dto.setNewHw(xw);//填充新箱位信息
						}
						ZxTaskDataBase.updateData(dto);
						info.setData(ZxTaskDataBase.queryDataById(dto.getZypbId()));
						result = JSON.toJSONString(info);
					}
//					return MaStation.getInstance().getMaWebService().reportZxzyStart(params[0], Integer.valueOf(params[1]),xw);
					return result;
				}else {
					if(MaStation.getInstance().isConnected()){
						result = MaStation.getInstance().getMaWebService().reportZxzyStart(params[0], Integer.valueOf(params[1]),"");
					}else{
						MaStation.getInstance().getMaWebService().reportZxzyStart(params[0], Integer.valueOf(params[1]), "");;//将请求加入缓存
						StandReturnInfo info = new StandReturnInfo();
						info.setSuccess(true);

						if(Integer.valueOf(params[1]) == 0){
							dto.setPbFlag(1);//未开始作业标识
							dto.setKsTime(null);//记录当前时间
							dto.setZzTime(null);
						}else if(Integer.valueOf(params[1]) == 1){
							dto.setPbFlag(3);//已开始作业标识(@WGary 2015/11/19 开始即结束，开始时间为现在时间的前五分钟)
							dto.setKsTime(new Timestamp(System.currentTimeMillis() - 5 * 60 * 1000));
							dto.setZzTime(new Timestamp(System.currentTimeMillis()));//记录当前时间
						}
						ZxTaskDataBase.updateData(dto);
						info.setData(ZxTaskDataBase.queryDataById(dto.getZypbId()));
						result = JSON.toJSONString(info);
					}
//					return MaStation.getInstance().getMaWebService().reportZxzyStart(params[0], Integer.valueOf(params[1]),"");
					return result;
				}
				
			}
			
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(AcZxTaskOne.this);
				progressDialog.setMessage("正在提交,请稍候...");
				progressDialog.setIndeterminate(true);
				progressDialog.setCancelable(true);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialogInterface) {
						if (startTask != null) {
							startTask.cancel(true);
						}
					}
				});
				progressDialog.show();
			}
			
			@Override
			protected void onPostExecute(String result) {
				try{
					if(result != null && !result.equals("[]") && !"".equals(result)){
						if("cache".equals(result)){
							ByDialog.showMessage(context, "网络断开，操作已缓存！");
						}else{
							GdmsZxzypbDTO obj;
							StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
//					if(info != null && !info.equals("[]") && info.getData() != null){
							if(info.isSuccess()){
								obj = JSON.parseObject(info.getData().toString(), GdmsZxzypbDTO.class);
								if(obj != null){
									dto = obj;
									initDate();
									initWidget();
									Dialog loadingDialog =  LoadingDialog.create(context, "成功执行", LoadingDialog.RIGHT);
									loadingDialog.setOnDismissListener(new OnDismissListener() {
										@Override
										public void onDismiss(DialogInterface dialog) {

										}
									});
								}else{
									AlertDialog.Builder dialog = new AlertDialog.Builder(context);
									dialog.setTitle("失败！");
									dialog.setMessage("当前操作未上报成功！");

									dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which)
										{
											initDate();
											initWidget();
										}
									});
									dialog.create().show();
								}

							}
						}


					}else{
						ByDialog.showMessage(context, "网络异常");
					}
					if(progressDialog != null){
						progressDialog.cancel();
					}
				}catch (Exception e){
					MaStation.getInstance().recordExc(e);
				}
			}
	}
	
	//拒绝移箱
	  private class End extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... params) {
				String result = "";
				if(MaStation.getInstance().isConnected()){
					result = MaStation.getInstance().getMaWebService().reportZxzyEnd(params[0], Integer.valueOf(params[1]));
				}else{
					MaStation.getInstance().getMaWebService().reportZxzyEnd(params[0], Integer.valueOf(params[1]));//将请求加入缓存
					StandReturnInfo info = new StandReturnInfo();
					info.setSuccess(true);

					if(Integer.valueOf(params[1]) == 0){
						dto.setPbFlag(2);//取消结束作业标识
						dto.setZzTime(null);//记录当前时间
					}else if(Integer.valueOf(params[1]) == 1){
						dto.setPbFlag(3);//已结束作业标识
						dto.setZzTime(new Timestamp(System.currentTimeMillis()));//记录当前时间
					}
					ZxTaskDataBase.updateData(dto);
					info.setData(ZxTaskDataBase.queryDataById(dto.getZypbId()));
					result = JSON.toJSONString(info);
				}
				return result;

//				return MaStation.getInstance().getMaWebService()
//						.reportZxzyEnd(params[0], Integer.valueOf(params[1]));
			}
			
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(AcZxTaskOne.this);
				progressDialog.setMessage("正在提交,请稍候...");
				progressDialog.setIndeterminate(true);
				progressDialog.setCancelable(true);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialogInterface) {
						if (endTask != null) {
							endTask.cancel(true);
						}
					}
				});
				progressDialog.show();
			}
			
			@Override
			protected void onPostExecute(String result) {
				try {
					if(result != null && !result.equals("[]") && !"".equals(result)){
						if("cache".equals(result)){
							ByDialog.showMessage(context, "网络断开，操作已缓存！");
						}else{
							GdmsZxzypbDTO obj;
							StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
							if(info.isSuccess()){
								obj = JSON.parseObject(info.getData().toString(), GdmsZxzypbDTO.class);
								if(obj != null){
									dto = obj;
									initDate();
									initWidget();

									Dialog loadingDialog =  LoadingDialog.create(context, "成功执行", LoadingDialog.RIGHT);
									loadingDialog.setOnDismissListener(new OnDismissListener() {

										@Override
										public void onDismiss(DialogInterface dialog) {

										}
									});
								}else{
									AlertDialog.Builder dialog = new AlertDialog.Builder(context);
									dialog.setTitle("失败！");
									dialog.setMessage("当前操作未上报成功！");

									dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											initDate();
											initWidget();
										}
									});
									dialog.create().show();
								}
							}
						}

					}else{
						ByDialog.showMessage(context, "网络异常");
					}
					if(progressDialog != null){
						progressDialog.cancel();
					}
				}catch (Exception e){
					MaStation.getInstance().recordExc(e);
				}
			}
	}

	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if (str==null) {
			return true;
		}else if(str.equals("")){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 判断是否为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		if (list!=null && list.size()<=0 ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 标志值变更
	 * @param op
	 * 0:开始按钮时间
	 * 1：结束按钮时间
	 */
	private void changeFlag(int op){
		if(op == 0){
			if(dto.getPbFlag() == GdmsZxzypbDTO.PB_YPB){//未开始
				start(1);// 执行开始上报
				start.setBackgroundResource(R.drawable.button_red);
//			}else if(dto.getPbFlag() == GdmsZxzypbDTO.PB_YHC){// 已开始（@WGary 2015/11/19 已开始则已结束）
			}else if(dto.getPbFlag() == GdmsZxzypbDTO.PB_YWC){// 已开始（@WGary 2015/11/19 已开始则已结束）
				start(0);// 执行取消上报
				start.setBackgroundResource(R.drawable.button_yellow);
			}
		}else if(op == 1){
			if(dto.getPbFlag() == GdmsZxzypbDTO.PB_YHC){//未结束
				end(1);//执行结束上报
			}else if(dto.getPbFlag() == GdmsZxzypbDTO.PB_YWC){//已结束
				end(0);//执行取消结束上报
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac_zx_task_one, menu);
		return true;
	}



}
