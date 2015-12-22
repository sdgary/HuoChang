package byxx.android.huochang.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.gdcarcheck.GdCarCheckDTO;

public class MoTask {
	
	public List<ZydDto> HzydDtos = null;
	public List<ZydDto> QzydDtos = null;
	public List<GdContainerPos> containerPos = null;
	public static Hashtable<String, List<GdContainerPos>> HMcontainerPos = null;
	public static Hashtable<String, List<ZydDto>> HMHzydDtos = null;
	public ZydDto taskSelectH;
	public ZydDto taskSelectQ;
	public String HW;
	public ZydDto getTaskSelectH() {
		return taskSelectH;
	}
	public void setTaskSelectH(ZydDto taskSelectH) {
		this.taskSelectH = taskSelectH;
	}

	public ZydDto getTaskSelectQ() {
		return taskSelectQ;
	}
	public void setTaskSelectQ(ZydDto taskSelectQ) {
		this.taskSelectQ = taskSelectQ;
	}
	public List<ZydDto> getHZydDtos(){
		return HzydDtos;
	}
	
	public List<ZydDto> getQZydDtos(){
		return QzydDtos;
	}
	
	public List<ZydDto> getTaskH(){
		List<ZydDto> nlist = null;
		nlist = null;
//		needcall		MaStation.getInstance().getMaWebService().getZyds(MaStation.stationCode,
//				MaStation.getInstance().getUser().getPostName()!= null ? MaStation.getInstance().getUser().getPostName():"",
//				MaStation.getInstance().getUser().getTeams() != null ? MaStation.getInstance().getUser().getTeams():"",
//				MaStation.getInstance().getUser().getWorkDay() != null? MaStation.getInstance().getUser().getWorkDay():"");
		return nlist;
	}
	public List<ZydDto> getTaskQ(){
		List<ZydDto> nlist = null;
		nlist = null;
//		needcall		MaStation.getInstance().getMaWebService().getZydTrucks(MaStation.stationCode,
//				MaStation.getInstance().getUser().getPostName()!= null ? MaStation.getInstance().getUser().getPostName():"",
//						MaStation.getInstance().getUser().getTeams() != null ? MaStation.getInstance().getUser().getTeams():"",
//								MaStation.getInstance().getUser().getWorkDay() != null? MaStation.getInstance().getUser().getWorkDay():"");
		return nlist;
	}
	
	public String reportZydStart(ZydDto task){
		String s = "";
//		int zydID,long startT, String zyr,boolean isStart
		s = "";
//		needcall		MaStation.getInstance().getMaWebService().reportZydStart(
//				task.getZypbId(),
//				System.currentTimeMillis(),
//				MaStation.getInstance().getUser().getName(), 
//				!task.isKszyflag());
		return s;
	}
	public String reportZydOver(ZydDto task){
		String s = "";
//		int zydID,long startT, String zyr,boolean isStart
		s = "";
//		needcall		MaStation.getInstance().getMaWebService().reportZydOver(
//				task.getZypbId(),
//				System.currentTimeMillis(),
//				MaStation.getInstance().getUser().getName(), 
//				!task.isJszyflag(),
//				task.getXw());
		return s;
	}
	
	public ZydDto findbyidH(int id){
		ZydDto value= null;
		for(ZydDto zDto :HzydDtos){
			if(zDto.getZypbId() == id){
				value = zDto;
				break;
			}
		}
		return value;
	}
	public ZydDto findbyidHM(int id,String gdm){
		ZydDto value= null;
        Iterator<Entry<String, List<ZydDto>>> it = getHMHzydDtos().entrySet().iterator();
        while(it.hasNext()){
            Entry<String, List<ZydDto>> entry = it.next();
            if(entry.getKey().equals(gdm)){
            	for(int i = 0 ;i<entry.getValue().size();i++ ){
            		if(entry.getValue().get(i).getZypbId()== id){
            			value = entry.getValue().get(i);
            			return value;
            		}
            	}
            }
        }

		return value;
	}
	
	public void setfindbyidHM(int id,String gdm,ZydDto task){
		Iterator<Entry<String, List<ZydDto>>> it = getHMHzydDtos().entrySet().iterator();
		while(it.hasNext()){
			Entry<String, List<ZydDto>> entry = it.next();
			if(entry.getKey().equals(gdm)){
				for(int i = 0 ;i<entry.getValue().size();i++ ){
					if(entry.getValue().get(i).getZypbId()== id){
						entry.getValue().remove(i);
						entry.getValue().add(i,task);
						break;
					}
				}
			}
		}
		

	}
	
	
	
	
	
	public ZydDto findbyidQ(int id){
		ZydDto value= null;
		for(ZydDto zDto :QzydDtos){
			if(zDto.getZypbId() == id){
				value = zDto;
				break;
			}
		}
		return value;
	}
	
	
	
	public static Hashtable<String, List<GdContainerPos>> getHMcontainerPos() {
		if(HMcontainerPos == null){
			HMcontainerPos = new Hashtable<String, List<GdContainerPos>>();
		}
		return HMcontainerPos;
	}
	public void setHMcontainerPos(
			Hashtable<String, List<GdContainerPos>> hMcontainerPos) {
		HMcontainerPos = hMcontainerPos;
	}
	
	
	
	public static Hashtable<String, List<ZydDto>> getHMHzydDtos() {
		if(HMHzydDtos == null){
			HMHzydDtos = new Hashtable<String, List<ZydDto>>();
		}
		return HMHzydDtos;
	}

	public String getHW() {
		return HW;
	}
	public void setHW(String hW) {
		HW = hW;
	}
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
    
}
