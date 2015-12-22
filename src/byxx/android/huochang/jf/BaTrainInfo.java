package byxx.android.huochang.jf;

import java.util.List;

import org.apache.commons.net.bsd.RLoginClient;


import byxx.android.huochang.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BaTrainInfo extends BaseAdapter{
	List<CarDto> datas;
	Context context;
	private LayoutInflater mInflater;
	LayoutInflater layoutInflater = null;
	View myLoginView  = null;
	Handler handler;
	String hfs = "<font color=#ffffff >";
	String hfe = "</font>";
	CarDto value;
	public List<CarDto> getDatas() {
		return datas;
	}
	public void setDatas(List<CarDto> datas) {
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
			      convertView = mInflater.inflate(R.layout.traininfo_item, null);
			      holder = new ViewHolder();
			      holder.ch = (TextView)convertView.findViewById(R.id.tv_ch);
			      holder.cz = (TextView)convertView.findViewById(R.id.tv_cz);
			      holder.kz = (TextView)convertView.findViewById(R.id.tv_kzbz);
			      holder.pm = (TextView)convertView.findViewById(R.id.tv_pm);
			      holder.swh = (TextView)convertView.findViewById(R.id.tv_swh);
			      holder.RLbg = (RelativeLayout)convertView.findViewById(R.id.RelativeLayout);
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
					s=  value.getCz();
					holder.cz.setText(Html.fromHtml(s));
				}
				s= "";
				if(value.getKzbz() != null){
					s=  value.getKzbz();
					if(s.equals("1")){
						s = "жи";
					}else if(s.equals("0")){
						s = "Пе";
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
				
//				if(position%2 == 0){
////					#BFEFFF
//					holder.RLbg.setBackgroundColor(Color.rgb(0xbf, 0xef, 0xff));
//				}else{
//					holder.RLbg.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
//				}
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
		TextView ch;
		TextView cz;
		TextView kz;
		TextView pm;
		TextView swh;
		RelativeLayout RLbg;
	  }
}
