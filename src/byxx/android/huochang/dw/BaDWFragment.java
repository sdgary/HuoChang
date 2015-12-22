/**
 * 
 */
package byxx.android.huochang.dw;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import byxx.android.huochang.R;
import byxx.android.huochang.util.ByString;

/**
 * @author WGary 韦永城
 * 2015-6-5
 */
public class BaDWFragment  extends BaseAdapter {
	List<XGjhzwDto> datas;
	Context context;
	private LayoutInflater mInflater;
	String hfs = "<font color=#008B00 >";
	String hfe = "</font>";
	
	public List<XGjhzwDto> getDatas() {
		return datas;
	}
	
	public void setDatas(List<XGjhzwDto> datas) {
		this.datas = datas;
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
			final XGjhzwDto value = getDatas().get(position);
			ViewHolder holder;
			if(convertView == null){
			      convertView = mInflater.inflate(R.layout.gjh_item, null);
			      holder = new ViewHolder();
			      holder.headTitle = (TextView)convertView.findViewById(R.id.head_title);
			      holder.tvCheQh = (TextView)convertView.findViewById(R.id.tv_cheqh);
			      holder.qscolor_flag = (RelativeLayout)convertView.findViewById(R.id.qscolor_flag);
			      holder.layout_color = (LinearLayout)convertView.findViewById(R.id.layout_color);
//			      holder.trainnum = (TextView)convertView.findViewById(R.id.trainnum);
//			      holder.jhno = (TextView)convertView.findViewById(R.id.jhno);
			      holder.actualtime = (TextView)convertView.findViewById(R.id.actualtime);
			      holder.plantime = (TextView)convertView.findViewById(R.id.plantime);
			      holder.NotifyTime = (TextView)convertView.findViewById(R.id.NotifyTime);
			      convertView.setTag(holder);
			    }else{
			    	holder = (ViewHolder) convertView.getTag();
			    }
				String s = "";

				s = hfs +(value.getGdm() != null?value.getGdm():"")+(value.getZylx() != null? value.getZylx():"")+
						(value.getCs())+hfe;
				holder.headTitle.setText(Html.fromHtml(s));
				if (value.getMeetingFlag()==1) {
					holder.tvCheQh.setText("车前会");
				}

				s= "";
				
				if(value.getJhsj()!= null){
					s= hfs +"计划:"+hfe +ByString.getTimeStr(value.getJhsj(), "HH:mm");
					holder.plantime.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getFactTime() !=null ){
					s= hfs +"实际:"+hfe +ByString.getTimeStr(value.getFactTime(), "HH:mm");;
					holder.actualtime.setText(Html.fromHtml(s));
				}else{
					holder.actualtime.setText("");
				}
				s= "";
				if(value.getNotifyTime() !=null ){
					s= hfs +"通知:"+hfe +ByString.getTimeStr(value.getNotifyTime(), "HH:mm");;
					holder.NotifyTime.setText(Html.fromHtml(s));
				}else{
					holder.NotifyTime.setText("");
				}
				
//				if(value.getLayout_color() == 1){
//					holder.layout_color.setBackgroundColor(Color.rgb(98, 0Xfb, 98));
//				}else{
//					holder.layout_color.setBackgroundColor(Color.rgb(0xEE, 0xC5, 0x91));
//				}
				
				if (value.getFactTime() != null) {
//					this.setForeground(ScdsOptions.darkGreen);
					holder.layout_color.setBackgroundColor(Color.GREEN);
				    } else if (value.getConfirmTime() != null) {
//					this.setForeground(ScdsOptions.darkBlue);
				    	holder.layout_color.setBackgroundColor(Color.BLUE);
				    } else if (value.getNotifyTime() != null) {
//					this.setForeground(ScdsOptions.darkRed);
				    	holder.layout_color.setBackgroundColor(Color.RED);
				    } else {
//					this.setForeground(Color.black);
				    	holder.layout_color.setBackgroundColor(Color.BLACK);
				    }		
			
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
			Log.v("ba", " "+e);
		}
		return convertView;
	}
	
	private class ViewHolder{
		TextView headTitle;
		//车前会标记
		TextView tvCheQh;
		/**
		 * 计划号
		 */
//		TextView jhno;
		/**
		 * 车数
		 */
//		TextView trainnum;
		/**
		 * 实际时间
		 */
		TextView actualtime;
		/**
		 * 计划时间
		 */
		TextView  plantime;
		/**
		 * 通知时间
		 */
		TextView  NotifyTime;
		/**
		 * 完成未完成标志 背景颜色改变
		 */
		LinearLayout layout_color;
		
		RelativeLayout qscolor_flag;
	  }

}
