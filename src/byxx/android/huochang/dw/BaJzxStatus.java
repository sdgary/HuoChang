package byxx.android.huochang.dw;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.util.ByString;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BaJzxStatus extends BaseAdapter{

	List<JzxDTO> datas;
	Context context;
	private LayoutInflater mInflater;
	LayoutInflater layoutInflater = null;
	View myLoginView  = null;
	Handler handler;
	String hfs = "<font color=#00FFFF >";
	String hfe = "</font>";
	 JzxDTO value;
	public List<JzxDTO> getDatas() {
		return datas;
	}
	public void setDatas(List<JzxDTO> datas) {
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
	public View getView(int position, View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub
		this.context = parent.getContext();
		this.mInflater = LayoutInflater.from(parent.getContext());
		try {
		if (getDatas() == null || getDatas().isEmpty()) {
			return convertView;
		}
			value = getDatas().get(position);
			ViewHolder holder;
			if(convertView == null){
			      convertView = mInflater.inflate(R.layout.qxc_jh_content_item, null);
			      holder = new ViewHolder();
			      holder.ch = (TextView)convertView.findViewById(R.id.tv_ch_content);
			      holder.cz = (TextView)convertView.findViewById(R.id.tv_cz_content);
			      holder.ddlx = (TextView)convertView.findViewById(R.id.tv_ddlx_content);
			      holder.xh1 = (TextView)convertView.findViewById(R.id.tv_xh1_content);
			      holder.xx1 = (TextView)convertView.findViewById(R.id.tv_xx1_content);
			      holder.xh2 = (TextView)convertView.findViewById(R.id.tv_xh2_content);
			      holder.xx2 = (TextView)convertView.findViewById(R.id.tv_xx2_content);
			      holder.CheckBox = (CheckBox)convertView.findViewById(R.id.checkBox1);
			      holder.button1 = (Button)convertView.findViewById(R.id.button1);
			      holder.button2 = (Button)convertView.findViewById(R.id.button2);
			      holder.tableRow4 = (TableRow)convertView.findViewById(R.id.tableRow4);
			      convertView.setTag(holder);
			    }else{
			    	holder = (ViewHolder) convertView.getTag();
			    }
				String s = "";

				if(value.getCh() != null){
					s = value.getCh();
					holder.ch.setText(Html.fromHtml(s));
				}
				
				s= "";
				if(value.getCz() !=null){
					s= value.getCz();
					holder.cz.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getDdlx() != null){
					s= value.getDdlx();
					holder.ddlx.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getXx1() != null ){
					s= value.getXx1();
					holder.xx1.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getXh1() != null ){
					s= value.getXh1();
					holder.xh1.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getXx2() != null ){
					s= value.getXx1();
					holder.xx2.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getXh2() != null ){
					s= value.getXh1();
					holder.xh2.setText(Html.fromHtml(s));
				}
				if(value.getXx2() == null){
					holder.tableRow4.setVisibility(8);
				}else{
					holder.tableRow4.setVisibility(0);
				}
			holder.button1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					bustatus(value,context,"箱"+value.getXh1());
				}
			});
			
			holder.button2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					bustatus(value,context,"箱"+value.getXh2());
				}
			});
			
			holder.CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					 value.setChecked(isChecked);
				}
			});
			
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
			Log.v("ba", " "+e);
		}
		return convertView;
	}
	public Handler getHandler() {

		return handler;
	}
	EditText etjzxstatus ;
	void bustatus(final JzxDTO vDto, Context ctx,String title){
		layoutInflater = LayoutInflater.from(ctx);
		myLoginView = layoutInflater.inflate(R.layout.jzx_zt_context, null);
		etjzxstatus =(EditText)myLoginView.findViewById(R.id.editText1);
		Dialog alertDialog = new AlertDialog.Builder(ctx).
				
				setTitle(title).
				//setIcon(R.drawable.ic_launcher).
				setView(myLoginView).
				setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
							try {
								if (getHandler() != null) {
									MaStation.getInstance().getMoJhx().setTaskSelect(vDto);
									Message tMessage = Message.obtain();
									tMessage.what =Constant.MSG_ID_JZX_STATUE_R;
									Bundle bu = new Bundle();
									bu.putString("id", vDto.getCh());
									tMessage.setData(bu);
									handler.sendMessage(tMessage);
								}
							} catch (Exception e) {
								Log.v("dl",e+"");
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
		alertDialog.show();	
	}

	
	private class ViewHolder{
		/**
		 * 车号
		 */
		TextView ch;
		/**
		 * 车种
		 */
		TextView cz;
		/**
		 * 到达类型
		 */
		TextView ddlx;
		/**
		 * 箱型1
		 */
		TextView  xx1;
		/**
		 * 箱型2
		 */
		TextView  xx2;
		/**
		 * 箱号1
		 */
		TextView  xh1;
		/**
		 * 箱型2
		 */
		TextView  xh2;

		CheckBox CheckBox;
		
		Button button1;
		Button button2;
		TableRow tableRow4;

	  }
}
