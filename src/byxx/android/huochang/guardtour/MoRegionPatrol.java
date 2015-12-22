package byxx.android.huochang.guardtour;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import byxx.android.huochang.MaStation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


public class MoRegionPatrol {
	public static final String DTOsname = "KfcDicRegionPatrolDTOs";
	public static final String DTOKey = "KfcDicRegionPatrolDTO";
	
	public List<KfcDicRegionPatrolDTO> kfcDicRegionPatrolDTOs = null;
	
	public List<KfcDicRegionPatrolDTO> getKfcDicRegionPatrolDTOs() {
		return kfcDicRegionPatrolDTOs;
	}
	
	
	
/**
 * 取用于PDA查询本站所有的id卡，并落地SP
 * @return
 */
	public List<KfcDicRegionPatrolDTO> getDicRegionPatrolDTOs(Context context) {
		List<KfcDicRegionPatrolDTO> nList = null;
		try {
			String StationCode = "GZZ";//MaStation.getInstance().getStationCode();
			String s = "" ; //MaStation.getInstance().getMaWebService().getRegionPatrol_S(StationCode);
			setString2SP(context,s,MoRegionPatrol.DTOsname,MoRegionPatrol.DTOKey);
//			String s1 = getString4SP(context, "KfcDicRegionPatrolDTOs", "KfcDicRegionPatrolDTO");
			nList = MoRegionPatrol.deepCopy(getDTOs4Str(s));
//			  nList = MaStation.getInstance().getMaWebService().getRegionPatrol(StationCode);;
//			  Log.v("testid", "show "+nList.size());
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return nList;
	}
	
	
	public String findRFIDbyid(String id){
		String s = "未找到对应的卡号";
		try{
//		if(MaStation.getInstance().getMoRegionPatrol().getKfcDicRegionPatrolDTOs() != null){
//			int n = MaStation.getInstance().getMoRegionPatrol().getKfcDicRegionPatrolDTOs().size();
//			for(int i= 0;i < n;i++){
//				KfcDicRegionPatrolDTO dto = MaStation.getInstance().getMoRegionPatrol().getKfcDicRegionPatrolDTOs().get(i);
//				if(dto.getPatrolDot().equalsIgnoreCase(id)){
//					s= "位置："+(dto.getRegion() != null? dto.getRegion():"无")+" ；方向: "+(dto.getDirection() != null ?dto.getDirection():"无")+"; \n详细详细 : "+(dto.getDetails() != null?dto.getDetails():"无"+"。");
//					break;
//				}
//
//			}
			
//			for(Iterator<KfcDicRegionPatrolDTO> it = MaStation.getInstance().getMoRegionPatrol().kfcDicRegionPatrolDTOs.iterator(); it.hasNext();){
				   
//				KfcDicRegionPatrolDTO dto = (KfcDicRegionPatrolDTO)it.next();
				  //obj的操作
//					if(dto.getPatrolDot().equalsIgnoreCase(id)){
//						s= "位置："+(dto.getRegion() != null? dto.getRegion():"无")+" ；方向: "+(dto.getDirection() != null ?dto.getDirection():"无")+"; \n详细详细 : "+(dto.getDetails() != null?dto.getDetails():"无"+"。");
//						break;
//					}
//				  }

//		}		
		}catch (Exception e) {
			s = "出错："+e;
			e.printStackTrace();
		 }
		return s;
		
	}
/**
 * 把String对象存储为SharedPreferences
 * @param context
 * @param s  //editor.putString(key, s);
 * @param name //context.getSharedPreferences(name,0);
 * @param key //editor.putString(key, s);
 */
	public void setString2SP(Context context,String s,String name,String key){
		SharedPreferences preferences = context.getSharedPreferences(name,0);
		Editor editor = preferences.edit();
		editor.putString(key, s);
		editor.commit();	
	}
	/**
	 * 从SharedPreferences转为String对象
	 * @param context
	 * @param name
	 * @param key
	 * @return
	 */
	public String getString4SP(Context context,String name,String key){
		String s= null;
		SharedPreferences preferences = context.getSharedPreferences(name,0);
		s = preferences.getString(key, "");
		return s;
	}
	/**
	 * Stirng对象转为<T>List<T> 泛类对象 
	 * @param s
	 * @return
	 */
	public static List<KfcDicRegionPatrolDTO> getDTOs4Str(String s){
		List<KfcDicRegionPatrolDTO> dtos = null;
		GsonBuilder gb = new GsonBuilder();
		gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = gb.create();
		if(s != null){
			dtos = gson.fromJson(s, new TypeToken<LinkedList<KfcDicRegionPatrolDTO>>(){}.getType());
		}
		return dtos;
	}
	
	
//	public List<KfcDicRegionPatrolDTO> getDicRegionPatrolDTOs4String(String s){
//		List<KfcDicRegionPatrolDTO> dtos = null;
//		dtos =getDTOs4Str(s,dtos);
//		return dtos;
//	}
	
	
	
	/**
	 * 深复制
	 * @param src
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * List<Person> destList=deepCopy(srcList);  
	 */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream out = new ObjectOutputStream(byteOut);  
        out.writeObject(src);  
      
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
        ObjectInputStream in = new ObjectInputStream(byteIn);  
        @SuppressWarnings("unchecked")  
        List<T> dest = (List<T>) in.readObject();  
        return dest;  
    } 
	
    


//    // Iterator遍历一
//    Iterator<Integer> iterator = lstint.iterator();
//    while (iterator.hasNext())
//    {
//     int i = (Integer) iterator.next();
//     System.out.println(i);
//    }
//
//   
//
//    // Iterator遍历二
//    for (Iterator<Integer> it = lstint.iterator(); it.hasNext();)
//    {
//     int i = (Integer) it.next();
//     System.out.println(i);
//    }
//
//   
//
//    // for循环
//    for (int i = 0; i < lstint.size(); i++)
//    {
//     System.out.println(lstint.get(i));
//    }
//
//   
//
//    // for循环加强版
//    for (Integer i : lstint)
//    {
//     System.out.println(i);
//    }
//
//
//   }

	
}
