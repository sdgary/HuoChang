package byxx.android.huochang.dw;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.traininfo.CarDto;
import byxx.android.huochang.util.ByString;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class BaMBL2 
//extends BaseAdapter
{
//	List<XClkDto> datas;
//	Context context;
//	private LayoutInflater mInflater;
//	LayoutInflater layoutInflater = null;
//	View myLoginView  = null;
//	Handler handler;
//	String hfs = "<font color=#ffffff >";
//	String hfe = "</font>";
//	
//
//
//	
//	
//	
//	
//	public List<XClkDto> getDatas() {
//		return datas;
//	}
//	public void setDatas(List<XClkDto> datas) {
//		this.datas = datas;
//	}
//	
//	public BaMBL2() {
//		// TODO Auto-generated constructor stub
//
//	}
//public void setHandler(Handler handler) {
//		this.handler = handler;
//	}
//	//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return getDatas() == null ? 0 : getDatas().size();
//	}
////	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		try {
//			return getDatas().get(position);
//		} catch (Exception e) {
////			MaStation.getInstance().recordExc(e);
//			Log.v("ba", " "+e);
//		}
//		return null;
//	}
////	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	public View getView(int position, View convertView, final ViewGroup parent) {
//		// TODO Auto-generated method stub
//		this.context = parent.getContext();
//		this.mInflater = LayoutInflater.from(parent.getContext());
//		try {
//		if (getDatas() == null || getDatas().isEmpty()) {
//			return convertView;
//		}
//		final XClkDto value = getDatas().get(position);
//			  ViewHolder holder;
//			if(convertView == null){
//			      convertView = mInflater.inflate(R.layout.mbl_items2, null);
//			      holder = new ViewHolder();
//			      holder.ch = (TextView)convertView.findViewById(R.id.tv_ch);
////			      holder.cz = (TextView)convertView.findViewById(R.id.tv_cz);
////			      holder.kz = (TextView)convertView.findViewById(R.id.tv_kzbz);
//			      holder.jzxh = (TextView)convertView.findViewById(R.id.tv_jzxh);
//			      holder.pm = (TextView)convertView.findViewById(R.id.tv_pm);
//			      holder.swh = (TextView)convertView.findViewById(R.id.tv_swh);
//			      holder.btndw = (Button)convertView.findViewById(R.id.btndw);
//			      holder.RLbg = (RelativeLayout)convertView.findViewById(R.id.RelativeLayout);
//			      convertView.setTag(holder);
//			    }else{
//			    	holder = (ViewHolder) convertView.getTag();
//			    }
//				String s = "";
//				if(value.getCh() != null){
//					s = value.getCh();
//					holder.ch.setText(Html.fromHtml(s));
//				}else{
//					holder.ch.setText(Html.fromHtml(s));
//				}
//				s = "";
//				if(value.getJzxh() != null){
//					s = value.getJzxh();
//					holder.jzxh.setText(Html.fromHtml(s));
//				}else{
//					holder.jzxh.setText(Html.fromHtml(s));
//				}
////				s= "";
////				if(value.getCz() !=null){
////					s=  value.getCz();
////					holder.cz.setText(Html.fromHtml(s));
////				}
////				s= "";
////				if(value.getKzbz() != null){
////					s=  value.getKzbz();
////					if(s.equals("1")){
////						s = "重";
////					}else if(s.equals("0")){
////						s = "空";
////					}
////					holder.kz.setText(Html.fromHtml(s));
////				}
//				s= "";
//				if(value.getPm() != null ){
//					s=  value.getPm();
//					holder.pm.setText(Html.fromHtml(s));
//				}else{
//					holder.pm.setText(Html.fromHtml(s));
//				}
////				s= "";
////				if((String.valueOf(value.getSwh()) != null )){
////					s= (String.valueOf(value.getSwh()));
//					holder.swh.setText(Html.fromHtml(position+1+""));
////				}
//
//				holder.btndw.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						value.hashMap.put(value.getCid(), 2);
//						notifyDataSetChanged();
//					}
//				});
//				holder.btndw.setOnLongClickListener(new OnLongClickListener() {
//					
//					@Override
//					public boolean onLongClick(View v) {
//						// TODO Auto-generated method stub
//						setLongdw(value,context);
//						return false;
//					}
//				});
//				
//				if(value.hashMap.get(value.getCid())!= null && value.hashMap.get(value.getCid())== 2){
//					holder.btndw.setBackgroundResource(R.drawable.btn_style_green);
//				}else if(value.hashMap.get(value.getCid())!= null && value.hashMap.get(value.getCid())== 1){
//					holder.btndw.setBackgroundResource(R.drawable.btn_style_red);
//				}else{
//					holder.btndw.setBackgroundResource(R.drawable.btn_style_white);
//				}
//				
//			
//
//		} catch (Exception e) {
////			MaStation.getInstance().recordExc(e);
//			Log.v("ba", " "+e);
//		}
//		return convertView;
//	}
//	public Handler getHandler() {
//
//		return handler;
//	}
//
//	
//	
//	
//	
//	private void setLongdw(final XClkDto value,Context context){
//	// TODO Auto-generated method stub
//	AlertDialog.Builder builder = new AlertDialog.Builder(context);
//	builder.setMessage(value.getSwh()+"对位，确认?");
//	builder.setCancelable(false);
//	builder.setNegativeButton("取消",
//			new android.content.DialogInterface.OnClickListener() {
//		public void onClick(DialogInterface dialog, int which) {// which=-2
//			value.hashMap.put(value.getCid(), 0);
//			notifyDataSetChanged();
//		}
//	});
//	builder.setNeutralButton("错误",	new android.content.DialogInterface.OnClickListener() {
//		public void onClick(DialogInterface dialog, int which) {// which=-1
////			if((MoDW.hashMap.get(value.getCid()) == null )||(MoDW.hashMap.get(value.getCid()) != null && MoDW.hashMap.get(value.getCid()) == 1)){
//			value.hashMap.put(value.getCid(), 1);
//			try {
//				if (getHandler() != null) {
//					MaStation.getInstance().getMoDW().setDto4dw(value);
//					Message tMessage = Message.obtain();
//					tMessage.what =Constant.MSG_ID_MSB_CAMERA_R;
//					Bundle bu = new Bundle();
//					bu.putString("cid", value.getCid());
//					tMessage.setData(bu);
//					handler.sendMessage(tMessage);
//				}
//			} catch (Exception e) {
//				Log.v("dl",e+"");
//			}
//			
//			notifyDataSetChanged();
////			}
//		}
//	});
//	builder.setPositiveButton("查看",
//			new android.content.DialogInterface.OnClickListener(){
//		public void onClick(DialogInterface dialog, int which) {// which=-1
////			if((MoDW.hashMap.get(value.getCid()) == null )||(MoDW.hashMap.get(value.getCid()) != null 
////					&& MoDW.hashMap.get(value.getCid()) == false)){
//			value.hashMap.put(value.getCid(), 2);
//				notifyDataSetChanged();
////			}
//		}
//	});
//
//
//	builder.create().show();
//	}
//	
//
//	
//	
//	
//	private class ViewHolder{
//		TextView ch;
////		TextView cz;
////		TextView kz;
//		TextView jzxh;
//		TextView pm;
//		TextView swh;
//		RelativeLayout RLbg;
//		Button  btndw;
//	  }
	
	
	
}

