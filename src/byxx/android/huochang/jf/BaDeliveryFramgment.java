package byxx.android.huochang.jf;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import byxx.android.huochang.R;


import byxx.android.huochang.util.ByString;

public class BaDeliveryFramgment extends BaseAdapter{
	List<GdmsUnloadJfDTO> datas;
	Context context;
	private LayoutInflater mInflater;
	LayoutInflater layoutInflater = null;
	View myLoginView  = null;
	Handler handler;
	String hfs = "<font color=#027bc8 >";
	String hfe = "</font>";

	public List<GdmsUnloadJfDTO> getDatas() {
		return datas;
	}
	public void setDatas(List<GdmsUnloadJfDTO> datas) {
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
		GdmsUnloadJfDTO value = getDatas().get(position);
			ViewHolder holder;
			if(convertView == null){
			      convertView = mInflater.inflate(R.layout.alldelivery_item, null);
			      holder = new ViewHolder();
			      holder.hpid = (TextView)convertView.findViewById(R.id.tv_hpid);
			      holder.js = (TextView)convertView.findViewById(R.id.tv_js);
			      holder.pm = (TextView)convertView.findViewById(R.id.tv_pm);
			      holder.zt = (TextView)convertView.findViewById(R.id.tv_jfzt);
			     
			      convertView.setTag(holder);
			    }else{
			    	holder = (ViewHolder) convertView.getTag();
			    }
				String s = "";
				if(value.getHpid() != null){
					s = value.getHpid();
					
					s = s.substring(s.indexOf(" ")+1); //空2格
					holder.hpid.setText(Html.fromHtml(hfs+"ID:"+hfe+s));
				}
				s = "";
				if(value.getPm() != null){
					s = value.getPm();
					holder.pm.setText(Html.fromHtml(hfs+"品名:"+hfe+s));
				}
				s = "";
//				if(value.getJs()){
					s = value.getJs()+"";
					holder.js.setText(Html.fromHtml(hfs+"件数:"+hfe+s));
//				}
				s = "";
//				if(value.getProflag() != null){
				s = GdmsUnloadJfDTO.proflagNames[value.getProflag()];
					holder.zt.setText(Html.fromHtml(hfs+"状态:"+hfe+s));
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
		TextView hpid;
		TextView pm;
		TextView js;
		TextView zt;
		

	  }
}