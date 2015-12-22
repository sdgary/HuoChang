package byxx.android.huochang.message;

import java.util.List;

import byxx.android.huochang.R;
import byxx.android.huochang.traininfo.CarDto;
import byxx.android.huochang.util.ByString;
import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaMessage extends BaseAdapter{
	List<XMsgDto> datas;
	Context context;
	private LayoutInflater mInflater;
	LayoutInflater layoutInflater = null;
	View myLoginView  = null;
	Handler handler;
	String hfs = "<font color=#ffffff >";
	String hfe = "</font>";
	XMsgDto value;
	public List<XMsgDto> getDatas() {
		return datas;
	}
	public void setDatas(List<XMsgDto> datas) {
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
			      convertView = mInflater.inflate(R.layout.message_item, null);
			      holder = new ViewHolder();
			      holder.senter = (TextView)convertView.findViewById(R.id.tv_sender);
			      holder.sendTime = (TextView)convertView.findViewById(R.id.tv_sendTime);
			      holder.msgcontext = (TextView)convertView.findViewById(R.id.tv_context);
			     
			      convertView.setTag(holder);
			    }else{
			    	holder = (ViewHolder) convertView.getTag();
			    }
				String s = "";
				if(value.getMsgSender() != null){
					s = value.getMsgSender();
					holder.senter.setText(Html.fromHtml("发送者："+s));
				}
				s= "";
				if(value.getSendTime() !=null){
					s=  ByString.getTimeStr(value.getSendTime(),"HH:mm");
					holder.sendTime.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getMsgContent() != null){
					s=  value.getMsgContent();
					holder.msgcontext.setText(Html.fromHtml(s));
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

	private class ViewHolder{
		TextView senter;
		TextView sendTime;
		TextView msgcontext;
	  }
}
