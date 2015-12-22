package byxx.android.huochang.task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.util.ByString;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Sampler.Value;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BaTaskTrain extends  BaseTask{
	List<ZydDto> datas;
	Context context;
	private LayoutInflater mInflater;
	LayoutInflater layoutInflater = null;
	View myLoginView  = null;
	Handler handler;
	String hfs = "<font color=#027bc8 >";
	String hfs1 ="<font color=#ff1197>"; // 
	String hfsB ="<font color=#1E90FF>"; // 
	String hfe = "</font>";
	String taskflag = null;
//	ZydDto CurTask = null;
	String gdm;
	
	
	public BaTaskTrain() {
		// TODO Auto-generated constructor stub
	}
	public BaTaskTrain(Handler handler) {
		// TODO Auto-generated constructor stub
		setHandler(handler);
	}
	
	public String getTaskflag() {
		return taskflag;
	}
	public void setTaskflag(String taskflag) {
		this.taskflag = taskflag;
	}
	public List<ZydDto> getDatas() {
		return datas;
	}
	public void setDatas(List<ZydDto> datas) {
		this.datas = datas;
	}
	
	
	
	
public void setHandler(Handler handler) {
		this.handler = handler;
	}
	//	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getDatas() == null ? 0 : getDatas().size();
	}
//	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return getDatas().get(position);
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
			Log.v("ba", " "+e);
		}
		return null;
	}
//	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	public View getView(int position,  View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub
		this.context = parent.getContext();
		this.mInflater = LayoutInflater.from(parent.getContext());
		try { 
		if (getDatas() == null || getDatas().isEmpty()) {
			return convertView;
		}
		final ZydDto value = getDatas().get(position);
		
		ZydDto oValue = null;
			oValue= MaStation.getInstance().getMoTask().findbyidH(value.getZypbId());

			final ViewHolder holder;
			if(convertView == null){
			      convertView = mInflater.inflate(R.layout.task_item3, null);
			      holder = new ViewHolder();
			      holder.xuhao = (TextView)convertView.findViewById(R.id.rwh);
//			      holder.gdm = (TextView)convertView.findViewById(R.id.gdm);
			      holder.ch = (TextView)convertView.findViewById(R.id.ch);
//			      holder.cz = (TextView)convertView.findViewById(R.id.cz);
//			      holder.hph = (TextView)convertView.findViewById(R.id.hph);
//			      holder.hwpm = (TextView)convertView.findViewById(R.id.hwpm);
//			      holder.hwpl = (TextView)convertView.findViewById(R.id.hwpl);
//			      holder.zyds = (TextView)convertView.findViewById(R.id.zyds);
			      holder.zyTypeHz = (TextView)convertView.findViewById(R.id.zyTypeHz);
//			      holder.zyfs = (TextView)convertView.findViewById(R.id.zyfs);
//			      holder.zyAddr = (TextView)convertView.findViewById(R.id.zyAddr);
			      holder.pbTime = (TextView)convertView.findViewById(R.id.pbTime);
//			      holder.remark = (TextView)convertView.findViewById(R.id.remark);
//			      holder.zydwmc = (TextView)convertView.findViewById(R.id.zydwmc);
//			      holder.hpId = (TextView)convertView.findViewById(R.id.hpId);
//			      holder.pbgw = (TextView)convertView.findViewById(R.id.pbgw);
			      holder.xh1 = (TextView)convertView.findViewById(R.id.xh1);
			      holder.xh2 = (TextView)convertView.findViewById(R.id.xh2);
//			      holder.xh2 = (TextView)convertView.findViewById(R.id.xh2);
			      holder.xw1 = (TextView)convertView.findViewById(R.id.xw1);
			      holder.xw2 = (TextView)convertView.findViewById(R.id.xw2);
			      holder.btnxw1 = (Button)convertView.findViewById(R.id.btn_xw1);
			      holder.btnxw2 = (Button)convertView.findViewById(R.id.btn_xw2);
//			      holder.zydwGb = (TextView)convertView.findViewById(R.id.zy)
//			      holder.zxZyr = (TextView)convertView.findViewById(R.id.zxZyr);
//			      holder.ksTime = (TextView)convertView.findViewById(R.id.startTime);
//			      holder.zzTime = (TextView)convertView.findViewById(R.id.zzTime);
//			      holder.cp = (TextView)convertView.findViewById(R.id.cp);
//			      holder.zybc = (TextView)convertView.findViewById(R.id.zybc);
//			      holder.btnzyks = (Button)convertView.findViewById(R.id.bu_zyks);
			      holder.btnzyjs = (Button)convertView.findViewById(R.id.bu_zyjs);
			      holder.btnxwlb = (Button)convertView.findViewById(R.id.bu_xwlb);
			      holder.jsbz = (TextView)convertView.findViewById(R.id.jsbz);
			      holder.llxh1 = (LinearLayout)convertView.findViewById(R.id.llxh1);
			      holder.llxh2 = (LinearLayout)convertView.findViewById(R.id.llxh2);
			      convertView.setTag(holder);
			    }else{
			    	holder = (ViewHolder) convertView.getTag();
			    }
			String s = "";
			if(value.getState() == 0){
				holder.xuhao.setText(Html.fromHtml(hfs1+"预派班"+hfe));
				
			}else{
				holder.xuhao.setText("任务"+(position+1));
			}
			if(isTrain()){
				
				if(value.getCh() != null){
					s = value.getCh();
					holder.ch.setText(Html.fromHtml(hfs+"车号  "+hfe+s));
				}
			}else{
				holder.ch.setText("");
			}
			
			
//				s = "";
//				if(value.getGdm() != null){
//					s = value.getGdm();
////					if(isTruck()){
////						if(value.getCardId() != null)
////						s+= "  "+value.getCardId();
////					}
//					holder.gdm.setText(Html.fromHtml(s));
//				}
//				if(isTrain()){
//				s= "";
//				if(value.getCz() !=null){
//					s=  value.getCz();
//					holder.cz.setText(Html.fromHtml(hfs+"车种:"+hfe+s));
//				}
//				}else if(isTruck()){
//					if(value.getTruckNum() != null){
//						s= value.getTruckNum();
//						holder.cz.setText(Html.fromHtml(hfs+"车种:"+hfe+s));
//					}
//				}
//				s= "";
//				hph
//				if(value.getHph() != null){
//					s=  value.getHph();
//					holder.hph.setText(Html.fromHtml(s));
//				}
//				s= "";
//				hwpm;//品名
//				if(value.getHwpm() != null ){
//					s=  value.getHwpm();
//					holder.hwpm.setText(Html.fromHtml(hfs+"品名  "+hfe+s));
//				}else{
//					holder.hwpm.setText(Html.fromHtml(""));
//				}
//				s= "";
//				if(value.getTruckNum() != null ){
//					s=  value.getTruckNum();
//					holder.cp.setText(Html.fromHtml(hfs+"车牌  "+hfe+s));
//				}else{
//					holder.cp.setText("");
//				}
//				s= "";
////				hwpl;//品类
//				if(value.getHwpl() != null){
//					s=  value.getHwpl();
//					holder.hwpl.setText(Html.fromHtml(s));
//				}

//				s= "";
//				zyds;//吨数
//			
//				if(String.valueOf(value.getZyds()) != null){
//					s=  value.getZyds()+"";
//					holder.zyds.setText(Html.fromHtml(hfs+"吨数  "+hfe+s));
//				}

				s= "";
//				 zyType;//作业类型zw
				if(value.getZyTypeHz()!= null){
					s=  value.getZyTypeHz()+"";
					if(value.getState() == 1){
						if(s.equals("卸火车")){
							holder.zyTypeHz.setText(Html.fromHtml(hfs1+s+hfe));
							holder.btnzyjs.setVisibility(8);
							holder.btnxwlb.setVisibility(0);
							holder.btnxw1.setVisibility(0);
							holder.btnxw2.setVisibility(0);
						}else if(s.equals("装火车")){
							holder.zyTypeHz.setText(Html.fromHtml(hfsB+s+hfe));
							holder.btnzyjs.setVisibility(0);
							holder.btnxwlb.setVisibility(8);
							holder.btnxw1.setVisibility(8);
							holder.btnxw2.setVisibility(8);
						}else{
							holder.btnzyjs.setVisibility(0);
							holder.btnxwlb.setVisibility(0);
						}
					}else{
						holder.zyTypeHz.setText(s);
					}
					
				}
//				s= "";
//				zyfs;//作业方式
//				if(value.getZyfs()!= null){
//					s=  value.getZyfs()+"";
//					holder.zyfs.setText(Html.fromHtml(s));
//				}
//				s= "";
//				zyAddr;//作业地点
//				if(value.getZyAddr()!= null){
//					s=  value.getZyAddr()+"";
//					holder.zyAddr.setText(Html.fromHtml(s));
//				}
				s= "";
//				 pbTime;//派班时间
				if(value.getPbTime()!= null){
					s=  ByString.getTimeStr(value.getPbTime(),"dd/HH:mm");
					holder.pbTime.setText(Html.fromHtml(hfs+"派班  "+hfe+s));
				}
//				s= "";
////				 remark;//备注;
//				if(value.getRemark()!= null){
//					s=  value.getRemark()+"";
//					holder.remark.setText(Html.fromHtml(s));
//				}
//				if(value.getZypbId() > 0){
//					holder.remark.setText(""+value.getZypbId());
//				}
//				Log.v("task", value.getZypbId()+"  ----");
//				s= "";
//				zydwmc;//作业单位名
//				if(value.getZydwmc()!= null){
//					s=  value.getZydwmc()+"";
//					holder.zydwmc.setText(Html.fromHtml(s));
//				}
//				s= "";
////				 zydwGb;//作业工班
//				if(value.getZydwGb()!= null){
//					s=  value.getZydwGb()+"";
//					holder.zydwGb.setText(Html.fromHtml(s));
//				}
//				s= "";
//				 hpId;//货票id
//				if(value.getHpId()!= null){
//					s=  value.getHpId()+"";
//					holder.hpId.setText(Html.fromHtml(s));
//				}
//				s= "";
//				 pbgw;//派单岗位
//				if(value.getPbgw()!= null){
//					s=  value.getPbgw()+"";
//					holder.pbgw.setText(Html.fromHtml(s));
//				}
//				s= "";
//				xh;//集装箱号
//				if(value.getXh()!= null){
//					s=  value.getXh()+"";
//					holder.xh2.setText(Html.fromHtml(hfs+"箱号  "+hfe+s));
//				}else{
//				if(value.getHwpl()!= null){
//					s=  value.getHwpl()+"";
//					holder.xh2.setText(Html.fromHtml(hfs+"品类  "+hfe+s));
//				}
//				}
				s= "";
				
				
//				if (value.getHwpl()!= null&&value.getHwpl().equals("集装箱")) {
//					显示集装箱号
					if(value.getXh1() != null){
					holder.llxh1.setVisibility(View.VISIBLE);
					s=  value.getXh1()+"";
					if(value.getState() == 1){
						holder.xh1.setText(Html.fromHtml(hfs+"箱1 "+hfe+s));
					}else{
						holder.xh1.setText(Html.fromHtml(s));
					}
						
					s= "";
					if(value.getXw1()!= null){
						s=  value.getXw1()+"";
						holder.xw1.setText(Html.fromHtml(hfs+" "+hfe+s));
					}else{
						holder.xw1.setText(Html.fromHtml(""));
					}
					
					}else{
					
					holder.xh1.setText("");
					holder.llxh1.setVisibility(View.GONE);
					}
					
					if(value.getXh2() != null){
						holder.llxh2.setVisibility(View.VISIBLE);
						s=  value.getXh2()+"";
						if(value.getState() == 1){
							holder.xh2.setText(Html.fromHtml(hfs+"箱2 "+hfe+s));
						}else{
							holder.xh2.setText(Html.fromHtml(s));
						}
						
						s= "";
						if(value.getXw2()!= null){
							s=  value.getXw2()+"";
							holder.xw2.setText(Html.fromHtml(hfs+" "+hfe+s));
						}else{
							holder.xw2.setText(Html.fromHtml(""));
						}
						
					}else{
						
						holder.xh2.setText("");
						holder.llxh2.setVisibility(View.GONE);
					}
					
			
					
//				}else{
//					if(value.getTruckNum() != null){
//					s=  value.getTruckNum()+"";
//					if(value.getState() == 1){
//						holder.xh.setText(Html.fromHtml(hfs+"箱号 "+hfe+s));
//					}else{
//						holder.xh.setText(Html.fromHtml(s));
//					}
//					
//					//显示value.getCphm();
//					}else{
//						holder.xh.setText("");
//					}
//				}
				
//				s= "";
////				xw;//箱位
//				if(value.getXw()!= null){
//					s=  value.getXw()+"";
//					holder.xw1.setText(Html.fromHtml(hfs+"箱位 "+hfe+s));
//				}else{
//					holder.xw1.setText(Html.fromHtml(""));
//				}
//				s= "";
//				 zxZyr;//装卸人
//				if(value.getZxZyr()!= null){
//					s=  value.getZxZyr()+"";
//					holder.zxZyr.setText(Html.fromHtml(s));
//				}
//				s= "";
//				 ksTime;//装卸开始时间
//				if(value.getKsTime()!= null){
//					s=  ByString.getTimeStr(value.getKsTime(),"HH:mm");
//					holder.ksTime.setText(Html.fromHtml(hfs+"开始  "+hfe+s));
//				}else{
//					holder.ksTime.setText(Html.fromHtml(s));
//				}
//				s= "";
//				 zzTime;//装卸结束时间
//				if(value.getZzTime()!= null){
//					s=  ByString.getTimeStr(value.getZzTime(),"HH:mm");
//					holder.zzTime.setText(Html.fromHtml(hfs+"结束  "+hfe+s));
//				}else{
//					holder.zzTime.setText(Html.fromHtml(s));
//				}
				s  ="";
				if(value.getZzTime()!= null){
					holder.jsbz.setText("完成");
				}else{
					holder.jsbz.setText("");
				}

//				s= "";
////				zybc;//作业班次
//				if(value.getZybc()!= null){
//					s=  value.getZybc()+"";
//					holder.zybc.setText(Html.fromHtml(s));
//				}
				
//				if(value.isKszyflag()){
//					holder.btnzyks.setText("取消开始");
//					oValue.isKS = false;		
//				}else{
//					holder.btnzyks.setText("开始作业");
//					oValue.isKS = true;
//				}
				if(value.isJszyflag()){
					holder.btnzyjs.setText("取消");
					holder.btnxwlb.setText("取消");
					holder.btnxw1.setEnabled(false);
					holder.btnxw2.setEnabled(false);
					value.isJS = false;
				}else{
					holder.btnzyjs.setText("完成");
					holder.btnxwlb.setText("确认箱位");
					holder.btnxw1.setEnabled(true);
					holder.btnxw2.setEnabled(true);
					value.isJS = true;
				}
				
				
				if(value.getState() == 0){
					holder.btnzyjs.setVisibility(8);
//					holder.btnzyks.setVisibility(8);
					holder.btnxwlb.setVisibility(8);
				}else{

//					holder.btnzyjs.setVisibility(0);
//					holder.btnxwlb.setVisibility(0);
					
				}
				
//				holder.btnzyks.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						if(isTrain()){
//							MaStation.getInstance().getMoTask().setTaskSelectH(value);
//						}else if(isTruck()){
//							MaStation.getInstance().getMoTask().setTaskSelectQ(value);
//						}
//						Log.v("task", value.getZypbId()+"");
//						onDialogClickTaskStart(value,context,holder.btnzyks.getText().toString().trim());
//					}
//				});
				holder.btnzyjs.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						if(isTrain()){
						MaStation.getInstance().getMoTask().setTaskSelectH(value);
						MaStation.getInstance().getMoTask().setfindbyidHM(value.getZypbId(), value.getGdm(), value);
//						}else if(isTruck()){
//							MaStation.getInstance().getMoTask().setTaskSelectQ(value);
//						}
						Log.v("task", value.getZypbId()+"");
						onDialogClickTaskOver(value,context,holder.btnzyjs.getText().toString().trim(),holder.btnzyjs.getText().toString().trim());
					}
				});
				holder.btnxwlb.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(value.getXw() != null && !value.getXw().equals("取消")){
						MaStation.getInstance().getMoTask().setTaskSelectH(value);
						MaStation.getInstance().getMoTask().setfindbyidHM(value.getZypbId(), value.getGdm(), value);
						XWOKonDialogClick(value,context,MaStation.getInstance().getMoTask().getHMcontainerPos().get(value.getGdm()),holder.btnxwlb.getText().toString().trim());
						}else{
							Toast.makeText(context, "箱位不能为空", Toast.LENGTH_SHORT).show();
						}
						}
				});
				holder.btnxw1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MaStation.getInstance().getMoTask().setTaskSelectH(value);
						MaStation.getInstance().getMoTask().setfindbyidHM(value.getZypbId(), value.getGdm(), value);
						XWonDialogClick(value,context,MaStation.getInstance().getMoTask().getHMcontainerPos().get(value.getGdm()),holder.btnxwlb.getText().toString().trim(),1);
					}
				});
				holder.btnxw2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MaStation.getInstance().getMoTask().setTaskSelectH(value);
						MaStation.getInstance().getMoTask().setfindbyidHM(value.getZypbId(), value.getGdm(), value);
						XWonDialogClick(value,context,MaStation.getInstance().getMoTask().getHMcontainerPos().get(value.getGdm()),holder.btnxwlb.getText().toString().trim(),2);
					}
				});
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
			Log.e("ba", " "+e);
		}
		return convertView;
	}
	

	private void onDialogClickTaskStart(final ZydDto task, Context ctx,String title){
		try {
//			CurTask = task;
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
			builder.setMessage(title + "确认?");
			builder.setCancelable(false);
			builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {// which=-1
					onDialogClickReport(task,Constant.MSG_ID_SENT_TASK_START_R);
				}
			});
			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {// which=-2
				}
			});
			builder.create().show();
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	private void onDialogClickTaskOver(final ZydDto task, Context ctx,String title,String text){
		try {
			if(!text.equals("取消")){
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
			builder.setMessage(title + "确认?");
			builder.setCancelable(false);
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {// which=-1
					onDialogClickReport(task,Constant.MSG_ID_SENT_TASK_OVER_R);
				}
			});
			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {// which=-2
				}
			});
			builder.create().show();
			}else{
				AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
				builder.setMessage(title + "确认?");
				builder.setCancelable(false);
				builder.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {// which=-1
						onDialogClickReport(task,Constant.MSG_ID_SENT_TASK_OVER_R);
					}
				});
				builder.setNegativeButton("取消",
						new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {// which=-2
					}
				});
				builder.create().show();
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	
	

	
	private void onDialogClickReport(ZydDto task,int what) {
		try {
			if (getHandler() != null) {
				Message tMessage = Message.obtain();
				tMessage.what = what;
				Bundle bu = new Bundle();
				bu.putInt("id", task.getZypbId());
				tMessage.setData(bu);
				handler.sendMessage(tMessage);
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}
	

	
	
	/**
	 * 箱位
	 * 
	 * @param ctx
	 * @param kind
	 */
	AutoCmpAdapter  adapter;
	Spinner pos;
	Spinner middlePosName ;
	Spinner spgdm ;
	List<String> gdmItems;
	List<String> midpos;
	List<String> lpos;
	ContainerMiddlePos mpos;
	
    List<ContainerMiddlePos> containerMiddlePos;
	Hashtable<String, List<ContainerMiddlePos>> hmidposname ;
	Hashtable<String, List<String>> hposname ;
	private void XWonDialogClick(final ZydDto task,final Context ctx,final List<GdContainerPos> mlist,String text,final int xhnum) {
		try {


//			 String trainNo = Train.showTrainNo(task.getArriveTrainNo(), task.getLeaveTrainNo());
			 layoutInflater = LayoutInflater.from(this.context);
			 myLoginView = layoutInflater.inflate(R.layout.taskxh, null);
			 pos =(Spinner)myLoginView.findViewById(R.id.sppos);
			 middlePosName =(Spinner)myLoginView.findViewById(R.id.spmidpos);
			 spgdm =(Spinner)myLoginView.findViewById(R.id.spigdm);
			// 建立数据源
			gdmItems= new ArrayList<String>();
			hmidposname = new Hashtable<String, List<ContainerMiddlePos>>();
			hposname = new Hashtable<String, List<String>>();
			 if(mlist != null ){
			 for(int i = 0 ;i < mlist.size();i++){
				 gdmItems.add(mlist.get(i).getGdm());
				 hmidposname.put(mlist.get(i).getGdm(), mlist.get(i).getMiddlePos());
			 }

//			  Collections.sort(gdmItems);
			ArrayAdapter<String> gdmAdapter=new ArrayAdapter<String>(this.context,R.layout.spinnerme, gdmItems);
			spgdm.setAdapter(gdmAdapter);
			spgdm.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, View view,
		                int position, long id) {// 选项改变的时候触发
						midpos = new  ArrayList<String>();
                      	for(ContainerMiddlePos pos:hmidposname.get(parent.getItemAtPosition(position).toString())){
                      		midpos.add(pos.getMiddlePosName());
                      		
                      	}
                    containerMiddlePos = hmidposname.get(parent.getItemAtPosition(position).toString());
//                    Collections.sort(midpos);
                    ArrayAdapter<String> midposAdapter=new ArrayAdapter<String>(ctx,R.layout.spinnerme,midpos);
					middlePosName.setAdapter(midposAdapter);
					      
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				}
			);

			middlePosName.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
		                int position, long id) {
					// TODO Auto-generated method stub
					lpos = new ArrayList<String>();
                        for(int i= 0;i< mlist.size();i++){
                        	for(int ii = 0;ii< mlist.get(i).getMiddlePos().size();ii++){
                        		if(parent.getItemAtPosition(position).toString().equals(mlist.get(i).getMiddlePos().get(ii).getMiddlePosName())){
                        			lpos = mlist.get(i).getMiddlePos().get(ii).getPos();
                        			break;
                        		}
                        	}
                        }
//                        Collections.sort(lpos);
        			ArrayAdapter<String> posAdapter=new ArrayAdapter<String>(ctx,R.layout.spinnerme,lpos);
                	pos.setAdapter(posAdapter);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});

			 }
	
		
			 Dialog alertDialog = new AlertDialog.Builder(this.context).
					 
					 setTitle("确认箱位?").
					    setIcon(R.drawable.ic_launcher).
					    setView(myLoginView).
					    setPositiveButton("确认", new DialogInterface.OnClickListener() {
					    	
					    	//@Override
					     public void onClick(DialogInterface dialog, int which) {
					      // TODO Auto-generated method stub
                                  String s = spgdm.getSelectedItem().toString()+middlePosName.getSelectedItem().toString()+pos.getSelectedItem().toString();
//                                  MaStation.getInstance().getMoTask().getTaskSelectH().setXw(s);
//                                  MaStation.getInstance().getMoTask().setHW(s);
  
//                          		try {
//                        			if (getHandler() != null) {
//                        				Message tMessage = Message.obtain();
//                        				tMessage.what = Constant.MSG_ID_SENT_TASK_OVER_HW_R;
//                        				Bundle bu = new Bundle();
//                        				bu.putInt("id", task.getZypbId());
//                        				tMessage.setData(bu);
//                        				handler.sendMessage(tMessage);
//                        			}
//                        		} catch (Exception e) {
////                        			MaStation.getInstance().recordExc(e);
//                        		}
                                  
                                  switch (xhnum) {
								case 1:
									String t = null;
									  t = task.getXw2();
									  if(task.getXhs().length > 1){
									  s = s+(t!= null ?
											  ","+t:","+s);
									  }
	                                  MaStation.getInstance().getMoTask().getTaskSelectH().setXw(s);
	                                  MaStation.getInstance().getMoTask().setHW(s);
	                                  task.setXw(s);
									break;
								case 2:
									String tt = null;
									tt = task.getXw1();
									
//									 s = (tt!= null &&!tt.equals("取消")? 
//											 tt+",":s+",")+s;
									 
									  if(tt!= null && !tt.equals("取消")){
										  s= tt+","+s;
									  }else{
										  s =s+","+s;
									  }
	                                  MaStation.getInstance().getMoTask().getTaskSelectH().setXw(s);
	                                  MaStation.getInstance().getMoTask().setHW(s);
	                                  task.setXw(s);
									break;

								default:
									break;
								}
                                  notifyDataSetChanged();
                                  
					    	 	}
					    }).
					    setNegativeButton("取消", new DialogInterface.OnClickListener() {

					    	//@Override
					     public void onClick(DialogInterface dialog, int which) {
					      // TODO Auto-generated method stub
					     }
					    }).
					    create();
					  alertDialog.show();
			 
			
			 
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	
	
	
	/**
	 * 箱位确认
	 * 
	 * @param ctx
	 * @param kind
	 */
	private void XWOKonDialogClick(final ZydDto task,final Context ctx,final List<GdContainerPos> mlist,String text) {
		try {
			
			if(!text.equals("取消")){


				AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
				builder.setMessage("箱位确认?");
				builder.setCancelable(false);
				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
							
							//@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
//								String s = "";
//								MaStation.getInstance().getMoTask().getTaskSelectH().setXw(s);
//								MaStation.getInstance().getMoTask().setHW(s);
                                 
								try {
									if (getHandler() != null) {
										Message tMessage = Message.obtain();
										tMessage.what = Constant.MSG_ID_SENT_TASK_OVER_HW_R;
										Bundle bu = new Bundle();
										bu.putInt("id", task.getZypbId());
										tMessage.setData(bu);
										handler.sendMessage(tMessage);
									}
								} catch (Exception e) {
//                        			MaStation.getInstance().recordExc(e);
								}
							}
						}).
						setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
							//@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						}).
						create();
				builder.show();

			}else{
				

						AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
						builder.setMessage("箱位取消?");
						builder.setCancelable(false);
						builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
							
							//@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								String s = "取消";//spgdm.getSelectedItem().toString()+middlePosName.getSelectedItem().toString()+pos.getSelectedItem().toString();
								MaStation.getInstance().getMoTask().getTaskSelectH().setXw(s);
								MaStation.getInstance().getMoTask().setHW(s);
								
								try {
									if (getHandler() != null) {
										Message tMessage = Message.obtain();
										tMessage.what = Constant.MSG_ID_SENT_TASK_OVER_HW_R;
										Bundle bu = new Bundle();
										bu.putInt("id", task.getZypbId());
										tMessage.setData(bu);
										handler.sendMessage(tMessage);
									}
								} catch (Exception e) {
//                        			MaStation.getInstance().recordExc(e);
								}
							}
						}).
						setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
							//@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						}).
						create();
						builder.show();
			}
			
			
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	
	public Handler getHandler() {

		return handler;
	}

	private class ViewHolder{
		TextView xuhao ;
		TextView jsbz;
		 TextView ch;//车号
//		 TextView cz;//车种
//		 TextView hph;//货票号
//		 TextView hwpm;//品名
//		 TextView hwpl;//品类
//		 TextView zyds;//吨数
//		 TextView zyType;//作业类型
		 TextView zyTypeHz;//作业类型汉字
//		 TextView zyfs;//作业方式
//		 TextView zyAddr;//作业地点      x
		 TextView pbTime;//派班时间
//		 TextView remark;//备注
//		 TextView zypbId;//作业单ID(上报时使用)
//		 TextView pbFlag;//派班标记(常量pbStates)
//		 TextView zydwmc;//作业单位名
//		 TextView zydwGb;//作业工班  
//		 TextView hpId;//货票id
//		 TextView pbgw;//派单岗位
		 TextView xh1;//集装箱号
		 TextView xh2;//集装箱号
//		 TextView xh2;//集装箱号
		 TextView xw1;//箱位
		 TextView xw2;//箱位
//		 TextView gdm;//股道码   1
//		 TextView zxZyr;//装卸人
//		 TextView ksTime;//装卸开始时间
//		 TextView zzTime;//装卸结束时间
//		 TextView zybc;//作业班次
//		 TextView cp ; //车牌
//		 Button btnzyks;
		 Button  btnxw1;
		 Button  btnxw2;

		 Button btnzyjs;
		 Button btnxwlb;
		 LinearLayout llxh1;
		 LinearLayout llxh2;
		 
	  }
	
    boolean isTruck(){
    	return taskflag.equals("truck");
    }
    boolean isTrain(){
    	return taskflag.equals("train");
    }
    

	
	
  
}
