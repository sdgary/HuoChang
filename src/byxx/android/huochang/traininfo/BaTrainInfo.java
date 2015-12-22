package byxx.android.huochang.traininfo;

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
import byxx.android.huochang.R;



public class BaTrainInfo extends BaseAdapter{
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
	CarDto upLoadDto;
	
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
	public View getView(int position, View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub
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
			      holder.pm = (TextView)convertView.findViewById(R.id.tv_pm);
			      holder.swh = (TextView)convertView.findViewById(R.id.tv_swh);
			      holder.jzxh = (TextView)convertView.findViewById(R.id.tv_jzxh);
			      holder.RLbg = (RelativeLayout)convertView.findViewById(R.id.RelativeLayout);
			      holder.btnCheck = (Button)convertView.findViewById(R.id.btndw);
			      convertView.setTag(holder);
		    }else{
		    	holder = (ViewHolder) convertView.getTag();
		    }
				
			//操作按钮
			holder.btnCheck.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setLongdw(value,context);
				}
			});
		
			String s = "";
			if(value.getCh() != null){
				s = value.getCh();
				holder.ch.setText(Html.fromHtml(s));
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
			}
			s= "";
			if((String.valueOf(value.getSwh()) != null )){
				s= (String.valueOf(value.getSwh()));
				holder.swh.setText(Html.fromHtml(s));
			}
			s= "";
			if(value.getJzxh() != null){
				s = value.getJzxh();
				holder.jzxh.setText(Html.fromHtml(s));
			}else{
				holder.jzxh.setText(Html.fromHtml(s));
			}
				
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
		builder.setNegativeButton("重点车",
				new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {// which=-2
				try {
					if (getHandler() != null) {
						Message tMessage = Message.obtain();
						tMessage.what =Constant.MSG_ID_MSB_CAMERA_R;
						Bundle bu = new Bundle();
						bu.putString("cid", value.getCid());
						tMessage.setData(bu);
						handler.sendMessage(tMessage);
						uploadImgState = PHOTO_TYPE_IMPORTANT;
						uploadID = value.getCid();
						value.setImportantFlag(1);
						upLoadDto = value;
					}
				} catch (Exception e) {
					Log.v("dl",e+"");
				}
				notifyDataSetChanged();
			}
		});
		builder.setNeutralButton("坏车",	new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {// which=-1
				try {
					if (getHandler() != null) {
						Message tMessage = Message.obtain();
						tMessage.what =Constant.MSG_ID_MSB_CAMERA_R;
						Bundle bu = new Bundle();
						bu.putString("cid", value.getCid());
						tMessage.setData(bu);
						handler.sendMessage(tMessage);
						uploadImgState = PHOTO_TYPE_BROKEN;
						uploadID = value.getCid();
						value.setBrokenFlag(1);
						upLoadDto = value;
					}
				} catch (Exception e) {
					Log.v("dl",e+"");
				}
				
				notifyDataSetChanged();
//				}
			}
		});

		builder.create().show();
		}

	private class ViewHolder{
		TextView ch;
		TextView cz;
		TextView kz;
		TextView pm;
		TextView swh;
		TextView jzxh;
	
		RelativeLayout RLbg;
		Button  btnCheck;//增加查看按钮
		TextView state;
	  }
}
