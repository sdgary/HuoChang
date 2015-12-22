package byxx.android.huochang.dw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.util.Log;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.task.ZydDto;
import byxx.android.huochang.traininfo.CarDto;

public class MoGJH {
	List<XGjhzwDto> gjhlist= null;
	public List<XGjhzwDto> tempgList = null;
	Vector<CarDto> vclkDto;
	public XGjhzwDto xGjhzwDto;
	
	public Vector<CarDto> getVclkDto() {
		return vclkDto;
	}

	public void setVclkDto(Vector<CarDto> vclkDto) {
		this.vclkDto = vclkDto;
	}

	public List<XGjhzwDto> getGjhlist() {
		return gjhlist;
	}

	public XGjhzwDto getxGjhzwDto() {
		return xGjhzwDto;
	}

	public void setxGjhzwDto(XGjhzwDto xGjhzwDto) {
		this.xGjhzwDto = xGjhzwDto;
	}

	List<XGjhzwDto> getGJH4web(String tabnum){
		GsonBuilder gb = new GsonBuilder();
		gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = gb.create();
		List<XGjhzwDto> list = gson.fromJson(MaStation.getInstance().getMaWebService().getQsgjh(tabnum), new TypeToken<LinkedList<XGjhzwDto>>(){}.getType());
		 return list;
		
	}
	
	public List<CarDto> findbyGJHid(int gjhid,int swh){
		Log.v("dw", "gjhid "+gjhid+ "swh"+swh);
		List<CarDto> list = new ArrayList<CarDto>();
		if(getGjhlist() != null){
			Log.v("dw","getGjhlist() != null   " +tempgList.size());
		    for(int ii = 0;ii< tempgList.size();ii++){
		    	
		    	Log.v("dw",tempgList.get(ii).getGjhid() +"  "+ tempgList.get(ii).getSwh());
		    	if(tempgList.get(ii).getGjhid() == gjhid && tempgList.get(ii).getSwh() == swh){
	    			Log.v("dw","dto.getGjhid() == gjhid && dto.getSwh() == swh" );
		    		for(int i = 0;i<tempgList.get(ii).getXClkDtos().size();i++){
		    			CarDto clkDto =(CarDto) tempgList.get(ii).getXClkDtos().get(i);
		    			list.add(clkDto);
		    		}
		    		break;
		    	}
		    }
		}
		Log.v("dw","list.size() "+list.size() );
		return list;
	}
	public List<CarDto> findbyGJHid(){
		List<CarDto> list = new ArrayList<CarDto>();
		for(int i = 0 ;i<getVclkDto().size();i++){
			CarDto clkDto =(CarDto) getVclkDto().get(i);
			list.add(clkDto);
		}
		return list;
	}


 
	

	
	
	
	
}
