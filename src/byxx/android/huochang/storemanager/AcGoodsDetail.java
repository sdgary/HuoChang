/**
 * 
 */
package byxx.android.huochang.storemanager;

import java.util.List;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.util.ByDialog;
import byxx.android.wj.http.cache.LoadingDialog;

/**
 * @author WGary 韦永城
 * 2015-6-11
 */
public class AcGoodsDetail extends Activity {
			
			private Context context;

	ExecuteYX executeYX;
	ProgressDialog progressDialog;
			WhZydDTO dto;
			boolean flag = false;//执行标志，默认为取消操作。
			
			LinearLayout mainPanel;
//			//计划ID
//			TextView id;
//			//状态
//			TextView status;
//			//品名
//			TextView pm;
//			//收货人
//			TextView shr;
//			//发货人
//			TextView fhr;
//			//货票号
//			TextView hph;
//			//运单号
//			TextView ydh;
//			//总重
//			TextView weight;
//			//总计数量
//			TextView numzg;
//			//入库量
//			TextView numyrk;
//			//出库量
//			TextView numyck;
//			//费用
//			TextView fy;
//			//实际入库
//			TextView factintime;
//			//实际出库
//			TextView factouttime;
//			//到达车
//			TextView ddcar;
//			//出发车
//			TextView cfcar;
//			//预约单号
//			TextView yydh;
//			//其他
//			TextView bz;

			//计划ID
			TextView zydid;
			//类型
			TextView zylx;
			//数量
			TextView zynum;
			//品名
			TextView zydpm;
			//前位置
			TextView prehw;
			//目标位置
			TextView facthw;
			//状态
			TextView zydzt;
			//股道码
			TextView zydgdm;
			
//			Spinner spinnerTrack;
//			Spinner spinnerArea;
//			Spinner spinnerPos;
//			Spinner spinnerLastPos;
			
//			ArrayAdapter<String> selectAdapterTrack;
//			ArrayAdapter<String> selectAdapterArea;
//			ArrayAdapter<String> selectAdapterPos;
//			ArrayAdapter<String> selectAdapterLastPos;
			
//			List<String> areaList = new ArrayList<String>();
//			List<String> posList = new ArrayList<String>();
//			List<String> lastPosList = new ArrayList<String>();
			
//			LinearLayout spinnerLayout;
			
			Button commit;
			Button cancel;
//			Button changeXW;
			
			
			@Override
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.storemanager_detail);
				
//				JzxHwzwModifyDTO dto;
				
				mainPanel = (LinearLayout) findViewById(R.id.mainPanel);
				
//				//计划ID
//				id = (TextView) findViewById(R.id.zy);
//				//状态
//				status = (TextView) findViewById(R.id.status);
//				//品名
//				pm = (TextView) findViewById(R.id.pm);
//				//收货人
//				shr = (TextView) findViewById(R.id.shr);
//				//发货人
//				fhr = (TextView) findViewById(R.id.fhr);
//				//货票号
//				hph = (TextView) findViewById(R.id.hph);
//				//运单号
//				ydh = (TextView) findViewById(R.id.ydh);
//				//总重
//				weight = (TextView) findViewById(R.id.weight);
//				//总计数量
//				numzg = (TextView) findViewById(R.id.numzg);
//				//入库量
//				numyrk = (TextView) findViewById(R.id.numyrk);
//				//出库量
//				numyck = (TextView) findViewById(R.id.numyck);
//				//费用
//				fy = (TextView) findViewById(R.id.fy);
//				//实际入库
//				factintime = (TextView) findViewById(R.id.factintime);
//				//实际出库
//				factouttime = (TextView) findViewById(R.id.factouttime);
//				//到达车
//				ddcar = (TextView) findViewById(R.id.ddcar);
//				//出发车
//				cfcar = (TextView) findViewById(R.id.cfcar);
//				//预约单号
//				yydh = (TextView) findViewById(R.id.yydh);
				//运单号
//				bz = (TextView) findViewById(R.id.bz);
				//运单号
//				status = (TextView) findViewById(R.id.status);
				
				//计划ID
				zydid = (TextView) findViewById(R.id.zydId);
				//类型
				zylx = (TextView) findViewById(R.id.zylx);
				//数量
				zynum = (TextView) findViewById(R.id.zynum);
				//品名
				zydpm = (TextView) findViewById(R.id.zydpm);
				//前位置
				prehw = (TextView) findViewById(R.id.prehw);
				//目标位置
				facthw = (TextView) findViewById(R.id.facthw);
				//状态
				zydzt = (TextView) findViewById(R.id.zydzt);
				//股道码
				zydgdm = (TextView) findViewById(R.id.zydgdm);
				
//				spinnerTrack = (Spinner) findViewById(R.id.spinnerTrack);
//				spinnerArea = (Spinner) findViewById(R.id.spinnerArea);
//				spinnerPos = (Spinner) findViewById(R.id.spinnerPos);
//				spinnerLastPos = (Spinner) findViewById(R.id.spinnerLastPos);
//				
//				spinnerLayout = (LinearLayout) findViewById(R.id.spinnerLayout);
				
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
//				changeXW = (Button) findViewById(R.id.changeXW);
//				changeXW.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Toast.makeText(context, "调整箱位按钮！", Toast.LENGTH_LONG).show();
//						changeXW();
//					}
//				});
				
				context = this;
				mainPanel.getBackground().setAlpha(210);
//				spinnerLayout.setVisibility(View.GONE);
//				changeXW.setVisibility(View.GONE);
				dto = (WhZydDTO) getIntent().getSerializableExtra("obj");
//				spinnerWidder();
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
				if (dto.getFactTime()==null) {
//					workTime.setVisibility(View.GONE);
					commit.setVisibility(View.VISIBLE);
					cancel.setVisibility(View.GONE);
//					if (dto.getWorkType()==10||
//							dto.getWorkType()==20||
//							dto.getWorkType()==30) {
//							changeXW.setVisibility(View.VISIBLE);
//						}else {
//							changeXW.setVisibility(View.GONE);
//						}
				}else {
					commit.setVisibility(View.GONE);
					cancel.setVisibility(View.VISIBLE);
				}
			}
			
//			private void spinnerWidder(){
//				selectAdapterTrack = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,MaStation.getInstance().getUser().getGdm());
//				selectAdapterTrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				spinnerTrack.setAdapter(selectAdapterTrack);
//				spinnerTrack.setVisibility(View.GONE);
//				spinnerTrack.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> parent, View view,
//							int position, long id) {
//						refreshPosData();
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> parent) {
//					}
//					
//				});
//				
//				selectAdapterArea = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,areaList);
//				selectAdapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				spinnerArea.setAdapter(selectAdapterArea);
//				spinnerArea.setVisibility(View.GONE);
//				spinnerArea.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> parent, View view,
//							int position, long id) {
//						initSecondSpinner(); 
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> parent) {
//					}
//					
//				});
//				
//				selectAdapterPos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,posList);
//				selectAdapterPos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				spinnerPos.setAdapter(selectAdapterPos);
//				spinnerPos.setVisibility(View.GONE);
//				spinnerPos.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> parent, View view,
//							int position, long id) {
//						initThirdData();
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> parent) {
//						
//					}
//				});
//				selectAdapterLastPos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lastPosList);
//				selectAdapterLastPos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				spinnerLastPos.setAdapter(selectAdapterLastPos);
//				spinnerLastPos.setVisibility(View.GONE);
//				
//			}
			
			private void initDate(){

//				id.setText("物品ID:"+dto.getId());
//				status.setText("状态:"+dto.getState());
//				pm.setText("品名:"+dto.getPm());
//				shr.setText("收货人"+dto.getShr());
//				fhr.setText("发货人:"+dto.getFhr());
//				hph.setText("货票号:"+dto.getHph());
//				ydh.setText("运单号:"+dto.getYdh());
//				weight.setText("总重:"+dto.getWeight()+"kg");
//				numzg.setText("总计:"+dto.getNumZg()+dto.getJldw());
//				numyrk.setText("入库量:"+dto.getNumYrk()+dto.getJldw());
//				numyck.setText("出库量:"+dto.getNumYck()+dto.getJldw());
//				fy.setText("费用:"+dto.getFy());
//				factintime.setText("实际入库:"+dto.getFactintime());
//				factouttime.setText("实际出库:"+dto.getYdh());
//				ddcar.setText("到达车:"+dto.getYSLX(dto.getDdlx())+dto.getDdcarnum());
//				cfcar.setText("出发车:"+dto.getYSLX(dto.getCflx())+dto.getCfcarnum());
//				yydh.setText("预约单号:"+dto.getYydh());
				
				//计划ID
				 zydid.setText("计划ID：" + dto.getZyId());
				//类型
				 zylx.setText("类型：" + dto.getZylx());
				//数量
				 zynum.setText("数量：" + String.valueOf(dto.getNum()));
				//品名
				 zydpm.setText("品名：" + dto.getPm());
				//前位置
				 prehw.setText("前位置：" + dto.getPreHw());
				//目标位置
				 facthw.setText("目标位置：" + dto.getFactHw());
				//状态
				 zydzt.setText("状态：" + dto.getZt());
				//股道码
				 zydgdm.setText("股道码：" + dto.getGdm());
				
			}
			

//			void changeXW() {
//				if (spinnerLayout.getVisibility()==View.GONE) {
//					refreshPosData();
//				}else {
//					spinnerLayout.setVisibility(View.GONE);
//				}
//			}
			
			private void commonCommitFunc(int flag){

				String zydId = String.valueOf(dto.getZyId());

//				if (spinnerLayout.getVisibility()==View.VISIBLE) {
//					gdm = spinnerTrack.getSelectedItem().toString();
//					xw = spinnerArea.getSelectedItem().toString()+
//										  spinnerPos.getSelectedItem().toString()+
//										  spinnerLastPos.getSelectedItem().toString();
//				}
//				MaStation.getInstance().getMaWebService().reportYxjh(dto.getPk(), flag, xw, gdm);
				//按顺序传入线程中，懒的编格式了！ @WGary 2015/06/09
				executeYX = new ExecuteYX();
				executeYX.execute(zydId, String.valueOf(flag));

			}
			

			void commit(){//移交计划提交
				commonCommitFunc(1);
			}
			

			void cancel(){
				commonCommitFunc(0);
			}
			
//			private void refreshPosData(){
//				Toast.makeText(context, "调整箱位按钮！", Toast.LENGTH_LONG).show();
//				new Refreshdate().execute();
//				initFirstSpinner();
////				spinnerTrack.setVisibility(View.VISIBLE);
////				spinnerLayout.setVisibility(View.VISIBLE);
//
//			}
//			
//			//调整箱位数据加载异步线程
//			  private class Refreshdate extends AsyncTask<String, Void, String> {
//					@Override
//					protected String doInBackground(String... params) {
//						return MaStation.getInstance().getMaWebService().getContainerPos(spinnerTrack.getSelectedItem().toString());
//					}
//					
//					@Override
//					protected void onPreExecute() {
//
//					}
//					
//					@Override
//					protected void onPostExecute(String result) {
//						if(result != null && !result.equals("[]")){
//							List<GdContainerPos> nList = null;
//							StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
//							nList = JSON.parseArray(info.getData().toString(), GdContainerPos.class);
//							if(nList != null && nList.size()>0){
//								gdList.clear();
//								gdList.addAll(nList);
//							}
//						
//						}
//					}
//			}
			  
			  private class ExecuteYX extends AsyncTask<String, Void, String> {
					@Override
					protected String doInBackground(String... params) {
						return MaStation.getInstance().getMaWebService()
								.reportWhjh(params[0], Integer.valueOf(params[1]));
					}
					
					@Override
					protected void onPreExecute() {
						progressDialog = new ProgressDialog(AcGoodsDetail.this);
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
							}else {
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
						if(progressDialog != null){
							progressDialog.cancel();
						}
					}
			}
			
			  //下拉单组合数据加载
//			private void initFirstSpinner(){
//				//first:clear data
//				areaList.clear();
//				for (int i = 0; i < gdList.size(); i++) {
//					areaList.add(gdList.get(i).getGdm());
//				}
//				
//				if (!isEmpty(areaList)) {
//					//give init data
//					if (spinnerArea.getSelectedItem()==null) {
//						spinnerArea.setSelection(0);
//					}
//					spinnerArea.setVisibility(View.VISIBLE);
//					initSecondSpinner();
//				}
//				selectAdapterArea.notifyDataSetChanged();
//			}
//			
//			private void initSecondSpinner(){
//				//first:clear data
//				posList.clear();
//				String area = spinnerArea.getSelectedItem().toString();
//				GdContainerPos dto =null;
//				List<ContainerMiddlePos> list = null;
//				for (int i = 0; i < gdList.size(); i++) {
//					dto = gdList.get(i);
//					if (dto.getGdm().equals(area)) {
//						list = dto.getMiddlePos();
//					}
//				}
//				CSort.getInstance().sort(list, new SortParameter("", ContainerMiddlePos.class, new String[]{"middlePosName"}, new Integer[]{CSort.SORT_ASC}));
//
//				for (int i = 0; i < list.size(); i++) {
//					posList.add(list.get(i).getMiddlePosName());
//				}
//				
//				if (!isEmpty(posList)) {
//					//give init data
//					if (spinnerPos.getSelectedItem()==null) {
//						spinnerPos.setSelection(0);
//					}
//					spinnerPos.setVisibility(View.VISIBLE);
//					initThirdData();
//				}else {
//					spinnerLastPos.setVisibility(View.GONE);
//				}
//				
//				selectAdapterPos.notifyDataSetChanged();
//			}
//			
//			private void initThirdData(){
//				//first:clear data
//				lastPosList.clear();
//				String area = spinnerArea.getSelectedItem().toString();
//				String pos = spinnerPos.getSelectedItem().toString();
//				
//				GdContainerPos dto =null;
//				List<ContainerMiddlePos> list = null;
//				 List<String> list2 = null;
//				for (int i = 0; i < gdList.size(); i++) {
//					dto = gdList.get(i);
//					if (dto.getGdm().equals(area)) {
//						list = dto.getMiddlePos();
//					}
//				}
//				
//				for (int i = 0; i < list.size(); i++) {
//					if (pos.equals(list.get(i).getMiddlePosName())) {
//						list2 = list.get(i).getPos();
//						Collections.sort(lastPosList);
//					}
//				}
//				for (int i = 0; i < list2.size(); i++) {
//					lastPosList.add(list2.get(i));
//				}
//				if (!isEmpty(lastPosList)) {
//					//give init data
//					spinnerLastPos.setSelection(0);
//					spinnerLastPos.setVisibility(View.VISIBLE);
//				}
//				selectAdapterLastPos.notifyDataSetChanged();
//			}
			
			
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
