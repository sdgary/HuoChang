package byxx.android.huochang.gdcarcheck;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import byxx.android.huochang.R;
import byxx.android.huochang.util.ByString;


public class BaCarCheckFragment extends BaseAdapter{
	List<GdCarCheckDTO> datas;
	Context context;
	private LayoutInflater mInflater;
	LayoutInflater layoutInflater = null;
	View myLoginView  = null;
	Handler handler;
	String hfs = "<font color=#027bc8 >";
	String hfe = "</font>";

	public List<GdCarCheckDTO> getDatas() {
		return datas;
	}
	public void setDatas(List<GdCarCheckDTO> datas) {
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
		GdCarCheckDTO value = getDatas().get(position);
			ViewHolder holder;
			if(convertView == null){
			      convertView = mInflater.inflate(R.layout.gdcarcheck_item, null);
			      holder = new ViewHolder();
			      holder.checkId = (TextView)convertView.findViewById(R.id.checkId);
			      holder.checkType = (TextView)convertView.findViewById(R.id.checkType);
			      holder.planEndTime = (TextView)convertView.findViewById(R.id.planEndTime);
			      holder.ratingBar = (RatingBar)convertView.findViewById(R.id.ratingBar1);
			      holder.icCardTime1 = (TextView)convertView.findViewById(R.id.icCardTime1);
			      holder.icCardTime2 = (TextView)convertView.findViewById(R.id.icCardTime2);
//			      holder.icCardTime3 = (TextView)convertView.findViewById(R.id.icCardTime3);
//			      holder.icCardTime4 = (TextView)convertView.findViewById(R.id.icCardTime4);
//			      holder.icCardTime5 = (TextView)convertView.findViewById(R.id.icCardTime5);
  
			      convertView.setTag(holder);
			    }else{
			    	holder = (ViewHolder) convertView.getTag();
			    }
				String s = "";
				if(value.getCheckId() != null){
					s = value.getCheckId()+"";
					
//					s = s.substring(s.indexOf(" ")+1); //空2格
					holder.checkId.setText(Html.fromHtml(hfs+hfe+s));
				}
				s = "";

					s = value.getCheckTypeName();
					holder.checkType.setText(Html.fromHtml(hfs+""+hfe+s));
				
				s = "";
				if(value.getPlanEndTime() != null){
					s = ByString.getTimeStr(value.getPlanEndTime(), "dd日HH:mm");
					holder.planEndTime.setText(Html.fromHtml(hfs+"检查截止  "+hfe+s));
				}else{
					holder.planEndTime.setText("");
				}

				s = "";
				if(value.getIcCardTime1() != null){
					s = ByString.getTimeStr(value.getIcCardTime1(), "dd日HH:mm");
					holder.icCardTime1.setText(Html.fromHtml(hfs+"刷卡1  "+hfe+s));
				}else{
					holder.icCardTime1.setText("");
				}
				s = "";
				if(value.getIcCardTime2() != null){
					s = ByString.getTimeStr(value.getIcCardTime2(), "dd日HH:mm");
					holder.icCardTime2.setText(Html.fromHtml(hfs+"刷卡2  "+hfe+s));
				}else{
					holder.icCardTime2.setText("");
				}
				
				holder.ratingBar.setNumStars(value.getCheckCs());
				holder.ratingBar.setRating(value.getNumStars());
				
//				s = "";
//				if(value.getIcCardTime3() != null){
//					s = ByString.getTimeStr(value.getIcCardTime3(), "dd日hh:mm");
//					holder.icCardTime3.setText(Html.fromHtml(hfs+""+hfe+s));
//				}else{
//					holder.icCardTime3.setText("");
//				}
//				s = "";
//				if(value.getIcCardTime4() != null){
//					s = ByString.getTimeStr(value.getIcCardTime4(), "dd日hh:mm");
//					holder.icCardTime4.setText(Html.fromHtml(hfs+""+hfe+s));
//				}else{
//					holder.icCardTime4.setText("");
//				}
//				s = "";
//				if(value.getIcCardTime5() != null){
//					s = ByString.getTimeStr(value.getIcCardTime5(), "dd日hh:mm");
//					holder.icCardTime5.setText(Html.fromHtml(hfs+""+hfe+s));
//				}else{
//					holder.icCardTime5.setText("");
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
		TextView checkId;
		TextView planEndTime;
		TextView checkType;
		TextView icCardTime1;
		TextView icCardTime2;
		RatingBar ratingBar;
//		TextView icCardTime3;
//		TextView icCardTime4;
//		TextView icCardTime5;
		

	  }
}
