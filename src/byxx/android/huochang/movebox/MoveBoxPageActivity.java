/**
 * 
 */
package byxx.android.huochang.movebox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.http.StandReturnInfo;

import byxx.android.huochang.task.ContainerMiddlePos;
import byxx.android.huochang.task.GdContainerPos;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.CSort;
import byxx.android.huochang.util.SortParameter;
import byxx.android.huochang.util.WjUtilTime;
import byxx.android.wj.http.cache.LoadingDialog;


/**
 * @author WGary 韦永城
 * 2015-6-6
 */
public class MoveBoxPageActivity  extends Activity {
	
	private Context context;
	private List<GdContainerPos>  gdList = new ArrayList<GdContainerPos>();
	ExecuteYX executeYX;
	Refuse refuseAction;
	ProgressDialog progressDialog;
	
	JzxHwzwModifyDTO dto;
	boolean flag = false;//执行标志，默认为取消操作。
	/**
	 * 如果=0,表示是正常状态,按钮显示为拒收,点击后提交的flag为1;
	 * 如果=1,表示在拒收状态,按钮显示为恢复,点击后提交的flag为2;
	 * 如果=2,表示是恢复状态,按钮显示为拒收,点击后提交的flag为1;
	 */
	int reflag = 0;//   拒收标志
	
	LinearLayout mainPanel;
	//计划ID
	TextView yxjhId;
	//集装箱号码
	TextView jzxnum;
	//箱主代码
	TextView jzxcode;
	//原箱位
	TextView oldHw;
	//股道
	TextView gdm;
	//新箱位
	TextView newHw;
	
	//执行状态
	TextView status;
	//执行时间
	TextView workTime;
	
	Spinner spinnerTrack;
	Spinner spinnerArea;
	Spinner spinnerPos;
	Spinner spinnerLastPos;
	
	ArrayAdapter<String> selectAdapterTrack;
	ArrayAdapter<String> selectAdapterArea;
	ArrayAdapter<String> selectAdapterPos;
	ArrayAdapter<String> selectAdapterLastPos;
	
	List<String> areaList = new ArrayList<String>();
	List<String> posList = new ArrayList<String>();
	List<String> lastPosList = new ArrayList<String>();
	
	LinearLayout spinnerLayout;
	
	Button commit;
	Button cancel;
	Button changeXW;
	Button refuse;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_move_box_page);
		
//		JzxHwzwModifyDTO dto;
		
		mainPanel = (LinearLayout) findViewById(R.id.mainPanel);
		//计划ID
		yxjhId = (TextView) findViewById(R.id.yxjhId);
		//集装箱号码
		jzxnum = (TextView) findViewById(R.id.jzxnum);
		//箱主代码
		jzxcode = (TextView) findViewById(R.id.jzxcode);
		//原箱位
		oldHw = (TextView) findViewById(R.id.oldHw);
		//股道
		gdm = (TextView) findViewById(R.id.gdm);
		//新箱位
		newHw = (TextView) findViewById(R.id.newHw);
		
		//执行状态
		status = (TextView) findViewById(R.id.status);
		//执行时间
		workTime = (TextView) findViewById(R.id.workTime);
		
		spinnerTrack = (Spinner) findViewById(R.id.spinnerTrack);
		spinnerArea = (Spinner) findViewById(R.id.spinnerArea);
		spinnerPos = (Spinner) findViewById(R.id.spinnerPos);
		spinnerLastPos = (Spinner) findViewById(R.id.spinnerLastPos);
		
		spinnerLayout = (LinearLayout) findViewById(R.id.spinnerLayout);
		
		commit = (Button) findViewById(R.id.commit);
		commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				commit();
			}
		});
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancel();
			}
		});
		changeXW = (Button) findViewById(R.id.changeXW);
		changeXW.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(context, "调整箱位按钮！", Toast.LENGTH_LONG).show();
				changeXW();
			}
		});
		refuse = (Button) findViewById(R.id.refuse);
		refuse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refuse();
			}
		});
		
		context = this;
		mainPanel.getBackground().setAlpha(210);
		spinnerLayout.setVisibility(View.GONE);
		changeXW.setVisibility(View.GONE);
		dto = (JzxHwzwModifyDTO) getIntent().getSerializableExtra("obj");
		spinnerWidder();
		initDate();
		initWidget();
		AppManager.getAppManager().addActivity(this);
	}
	
	private void initWidget(){
		/*
		 * 如果类型是10,20,30的,需要显示箱位选择控件
		 * 如果类型是11,21的,不需要显示箱位选择控件
		 * 任何类型的,都需要显示执行或者撤销的按钮(执行和撤销的按钮不同时显示,是否显示根据计划的workTime字段判断,如果字段为null,显示执行按钮,否则显示撤销按钮)
		 */
		if (dto.getWorkTime()==null) {//无执行时间，则未执行
			workTime.setVisibility(View.GONE);
			commit.setVisibility(View.VISIBLE);
			cancel.setVisibility(View.GONE);
			if (dto.getWorkType()==10||
					dto.getWorkType()==20||
					dto.getWorkType()==30) {
					changeXW.setVisibility(View.VISIBLE);
				}else {
					changeXW.setVisibility(View.GONE);
				}
			//当作业类型为直装或集装箱汽车到达
			if	(dto.getWorkType() == dto.CAR_IN || dto.getWorkType() == dto.CAR_TO_TRAIN){
				refuse.setVisibility(View.VISIBLE);//未执行，可拒收
			}else{
				refuse.setVisibility(View.GONE);
			}
			
		}else {
			commit.setVisibility(View.GONE);
			cancel.setVisibility(View.VISIBLE);
			refuse.setVisibility(View.GONE);//已执行，不可拒收
		}
		
		if(reflag == 1){//拒收状态，不可执行或撤销计划
//			refuse.setBackground();
			refuse.setBackgroundResource(R.drawable.button_yellow);
			refuse.setText("恢复");
			commit.setVisibility(View.GONE);
			cancel.setVisibility(View.GONE);
		}else{//正常或恢复状态
			refuse.setBackgroundResource(R.drawable.button_red);
			refuse.setText("拒收");	
		}
	}
	
	private void spinnerWidder(){
		selectAdapterTrack = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,MaStation.getInstance().getUser().getGdm());
		selectAdapterTrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTrack.setAdapter(selectAdapterTrack);
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
	
	private void initDate(){
		yxjhId.setText("计划ID:"+dto.getPk());
		jzxnum.setText("集装箱号码:"+dto.getJzxnum());
		jzxcode.setText("箱主代码:"+dto.getJzxcode());
		gdm.setText("股道码"+dto.getGdm());
		oldHw.setText("原箱位:"+dto.getOldHw());
		newHw.setText("新箱位:"+dto.getNewHw());
		status.setText(MoveBoxUtils.getZWType(dto.getGdm(), dto.getWorkType()));
		if(dto.getWorkTime() == null){
			workTime.setText("");
		}else{
			workTime.setText(WjUtilTime.HHmm.format(dto.getWorkTime()));
		}
		reflag = dto.getIsRefuse();
	}
	

	void changeXW() {
		if (spinnerLayout.getVisibility()==View.GONE) {
			refreshPosData();
		}else {
			spinnerLayout.setVisibility(View.GONE);
		}
	}
	
	void refuse() {
		if(reflag == 1){//已拒收，恢复操作
			commit.setVisibility(View.VISIBLE);
			cancel.setVisibility(View.VISIBLE);
//			dto.setIsRefuse(2);
			reflag = 2;
			refuse.setBackgroundResource(R.drawable.button_yellow);
			refuse.setText("恢复");
			//执行恢复线程
			refuseAction = new Refuse();
			refuseAction.execute(dto.getPk(), "2") ;
		}else{
			commit.setVisibility(View.GONE);
			cancel.setVisibility(View.GONE);
//			dto.setIsRefuse(1);
			reflag = 1;
			refuse.setBackgroundResource(R.drawable.button_red);
			refuse.setText("拒收");	
			//执行拒收
			refuseAction = new Refuse();
			refuseAction.execute(dto.getPk(), "1") ;
		}
		
	}
	
	private void commonCommitFunc(int flag){

		String pk = dto.getPk();
		String xw = "";
		String gdm = "";
		if (spinnerLayout.getVisibility()==View.VISIBLE) {
			gdm = spinnerTrack.getSelectedItem().toString();
			xw = spinnerArea.getSelectedItem().toString()+
								  spinnerPos.getSelectedItem().toString()+
								  spinnerLastPos.getSelectedItem().toString();
		}
//		MaStation.getInstance().getMaWebService().reportYxjh(dto.getPk(), flag, xw, gdm);
		//按顺序传入线程中，懒的编格式了！ @WGary 2015/06/09
		executeYX = new ExecuteYX();
		executeYX.execute(pk, String.valueOf(flag), xw, gdm);

	}
	

	void commit(){//移交计划提交
		commonCommitFunc(1);
		refuse.setVisibility(View.VISIBLE);//未执行，可拒收
	}
	

	void cancel(){
		commonCommitFunc(0);
		refuse.setVisibility(View.GONE);//未执行，可拒收
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
//	  private class Refreshdate extends AsyncTask<String, Void, String> {
//			@Override
//			protected String doInBackground(String... params) {
//				return MaStation.getInstance().getMaWebService().getContainerPos(spinnerTrack.getSelectedItem().toString());
//			}
//
//			@Override
//			protected void onPreExecute() {
//
//			}
//
//			@Override
//			protected void onPostExecute(String result) {
//				if(result != null && !result.equals("[]")){
//					List<GdContainerPos> nList = null;
//					StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
//					nList = JSON.parseArray(info.getData().toString(), GdContainerPos.class);
//					if(nList != null && nList.size()>0){
//						gdList.clear();
//						gdList.addAll(nList);
//						initFirstSpinner();
//					}
//
//				}
//			}
//	}
	  
	  private class ExecuteYX extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... params) {
				return MaStation.getInstance().getMaWebService()
						.reportYxjh(params[0], Integer.valueOf(params[1]), params[2], params[3]);
			}
			
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(MoveBoxPageActivity.this);
				progressDialog.setMessage("正在提交,请稍候...");
				progressDialog.setIndeterminate(true);
				progressDialog.setCancelable(true);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialogInterface) {
						if (executeYX != null) {
							executeYX.cancel(true);
						}
					}
				});
				progressDialog.show();
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
				}
				if(progressDialog != null){
					progressDialog.cancel();
				}
			}
	  }
	
	//拒绝移箱
	  private class Refuse extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... params) {
				return MaStation.getInstance().getMaWebService()
						.refuseYxjh(params[0], Integer.valueOf(params[1]));
			}
			
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(MoveBoxPageActivity.this);
				progressDialog.setMessage("正在提交,请稍候...");
				progressDialog.setIndeterminate(true);
				progressDialog.setCancelable(true);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialogInterface) {
						if (executeYX != null) {
							executeYX.cancel(true);
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
							JzxHwzwModifyDTO obj;
							StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
							obj = JSON.parseObject(info.getData().toString(), JzxHwzwModifyDTO.class);
							if(obj != null){
								dto = obj;
								spinnerWidder();
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
										spinnerWidder();
										initDate();
										initWidget();
									}
								});
								dialog.create().show();
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
//				if (spinnerArea.getSelectedItem()==null) {
					spinnerArea.setSelection(0);
//				}
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
	
}
