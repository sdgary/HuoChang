package byxx.android.huochang.dw;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.traininfo.CarDto;

/**
 * 使用中的badw
 * @author 
 *
 */
public class BaMBL extends BaseAdapter{
	public static final int PHOTO_TYPE_IMPORTANT = 11; // 重点车
	public static final int PHOTO_TYPE_BROKEN = 12; // 坏车
	public static final int PHOTO_TYPE_MEETING = 13; // 车前会
	List<CarDto> datas;
	Context context;
	private LayoutInflater mInflater;
	LayoutInflater layoutInflater = null;
	View myLoginView  = null;
	Handler handler;
	String hfs = "<font color=#ffffff >";
	String hfe = "</font>";
	int uploadImgState = 0;
	String uploadID="";
	public List<CarDto> getDatas() {
		return datas;
	}
	public void setDatas(List<CarDto> datas) {
		this.datas = datas;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return getDatas() == null ? 0 : getDatas().size();
	}
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return getDatas().get(position);
		} catch (Exception e) {
			Log.v("ba", " "+e);
		}
		return null;
	}
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	public View getView(int position, View convertView, final ViewGroup parent) {
		this.context = parent.getContext();
		this.mInflater = LayoutInflater.from(parent.getContext());
		try {
		if (getDatas() == null || getDatas().isEmpty()) {
			return convertView;
		}
		final CarDto value = getDatas().get(position);
			  ViewHolder holder;
			if(convertView == null){
			      convertView = mInflater.inflate(R.layout.mbl_items2, null);
			      holder = new ViewHolder();
			      holder.ch = (TextView)convertView.findViewById(R.id.tv_ch);
			      holder.cz = (TextView)convertView.findViewById(R.id.tv_cz);
			      holder.kz = (TextView)convertView.findViewById(R.id.tv_kzbz);
			      holder.state = (TextView)convertView.findViewById(R.id.state);
			      holder.jzxh = (TextView)convertView.findViewById(R.id.tv_jzxh);
			      holder.pm = (TextView)convertView.findViewById(R.id.tv_pm);
			      holder.swh = (TextView)convertView.findViewById(R.id.tv_swh);
			      holder.btndw = (Button)convertView.findViewById(R.id.btndw);
			      holder.RLbg = (RelativeLayout)convertView.findViewById(R.id.RelativeLayout);
			      convertView.setTag(holder); 
			    }else{
			    	holder = (ViewHolder) convertView.getTag();
			    }
				String s = "";
				if(value.getCh() != null){
					s = value.getCh();
					holder.ch.setText(Html.fromHtml(s));
				}else{
					holder.ch.setText(Html.fromHtml(s));
				}
				s = "";
				if(value.getJzxh() != null){
					s = value.getJzxh();
					holder.jzxh.setText(Html.fromHtml(s));
				}else{
					holder.jzxh.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getCz() !=null){
					s=  value.getCz();
					holder.cz.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getKzbz() != null){
					s=  value.getKzbz();
					if(s.equals("1")){
						s = "重";
					}else if(s.equals("0")){
						s = "空";
					}
					holder.kz.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getPm() != null ){
					s=  value.getPm();
					holder.pm.setText(Html.fromHtml(s));
				}else{
					holder.pm.setText(Html.fromHtml(s));
				}
				s= "";
				if((String.valueOf(value.getSwh()) != null )){
					s= (String.valueOf(value.getSwh()));
					holder.swh.setText(Html.fromHtml(String.valueOf(position+1)));
				}

				holder.btndw.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						setLongdw(value,context);
					}
				});
				
				//更改状态
				if(value.getBrokenFlag()==1&&value.getImportantFlag()==1) {
					holder.state.setText("坏车,重点车");
				}else if (value.getImportantFlag()==1) {
					holder.state.setText("重点车");
				}else if (value.getBrokenFlag()==1) {
					holder.state.setText("坏车");
				}else{
					holder.state.setText("");
				}
			

		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
			Log.v("ba", " "+e);
		}
		return convertView;
	}
	public Handler getHandler() {

		return handler;
	}
	
	private void setLongdw(final CarDto value,Context context){
	// TODO Auto-generated method stub
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder.setMessage("对车辆"+value.getCh()+"进行操作?");
	builder.setCancelable(true);
	builder.setNegativeButton("坏车",	new android.content.DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {// which=-1
			try {
				if (getHandler() != null) {
					MaStation.getInstance().getMoDW().setDto4dw(value);
					Message tMessage = Message.obtain();
					tMessage.what =Constant.MSG_ID_MSB_CAMERA_R;
					Bundle bu = new Bundle();
					bu.putString("cid", value.getCid());
					tMessage.setData(bu);
					handler.sendMessage(tMessage);
					uploadImgState = PHOTO_TYPE_BROKEN;
					uploadID = value.getCid();
					value.setBrokenFlag(1);
				}
			} catch (Exception e) {
				Log.v("dl",e+"");
			}
			
			notifyDataSetChanged();
		}
	});
	
	builder.setNeutralButton("重点车",new android.content.DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			try {
				if (getHandler() != null) {
					MaStation.getInstance().getMoDW().setDto4dw(value);
					Message tMessage = Message.obtain();
					tMessage.what =Constant.MSG_ID_MSB_CAMERA_R;
					Bundle bu = new Bundle();
					bu.putString("cid", value.getCid());
					tMessage.setData(bu);
					handler.sendMessage(tMessage);
					uploadImgState = PHOTO_TYPE_IMPORTANT;
					uploadID = value.getCid();
					value.setImportantFlag(1);
				}
			} catch (Exception e) {
				Log.v("dl",e+"");
			}
			notifyDataSetChanged();
		}
	});
	
	builder.setPositiveButton("派班",
			new android.content.DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog, int which) {
			paiban(value);
			//重点标记变化，执行派班操作
			notifyDataSetChanged();
		}
	});


	builder.create().show();
	}

	//重点车标记，拍照
	private void paiban(final CarDto value){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("是否为车辆"+value.getSwh()+"派班");
		builder.setPositiveButton("取消",
				new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {// which=-2
				
			}
		});
		
		builder.setNegativeButton("确定派班",
				new android.content.DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {// which=-1

				try {
					if (getHandler() != null) {
						MaStation.getInstance().getMoDW().setDto4dw(value);
						Message tMessage = Message.obtain();
						tMessage.what =Constant.MSG_ID_MSB_PAIBAN;
						Bundle bu = new Bundle();
						bu.putString("cid", value.getCid());
						tMessage.setData(bu);
						handler.sendMessage(tMessage);
					}
				} catch (Exception e) {
					Log.v("dl",e+"");
				}
			}
		});


		builder.create().show();
	}
	
	private class ViewHolder{
		//车号
		TextView ch;
		TextView cz;
		TextView kz;
		TextView jzxh;
		//品名
		TextView pm;
		TextView swh;
		RelativeLayout RLbg;
		//操作按钮
		Button  btndw;
		TextView state;
	  }
	
}

