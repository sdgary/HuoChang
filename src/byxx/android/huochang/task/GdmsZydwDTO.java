package byxx.android.huochang.task;
import java.io.Serializable;
import java.util.Vector;

public class GdmsZydwDTO implements Serializable  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String[] ZYDW_TYPES = {"路工", "委外"};
	
    public static final int BZ_TYPE_LG = 0; //路工
    public static final int BZ_TYPE_WW = 1; //委外

    public static final int SHOW_COLUMN = 2; //列数

	 private int zydwId;
	 private String hyzx;
	 private String hycj;
	 private String stnCode;
	 private String stnName;
	 private String stnHc;
	 private String zydwmc;
	 private int zydwRs;
	 private String zydwFzr;
	 private String zydwFzrPhone;
	 private int zydwType;
	 private String bz;
	 private String area;
	 
	 
 	 public void setZydwId(int zydwId){ 
		 this.zydwId=zydwId; 
	 } 
 
	 public int getZydwId(){ 
		 return zydwId; 
	 } 
 	 public void setHyzx(String hyzx){ 
		 this.hyzx=hyzx; 
	 } 
 
	 public String getHyzx(){ 
		 return hyzx; 
	 } 
 	 public void setHycj(String hycj){ 
		 this.hycj=hycj; 
	 } 
 
	 public String getHycj(){ 
		 return hycj; 
	 } 
 	 public void setStnCode(String stnCode){ 
		 this.stnCode=stnCode; 
	 } 
 
	 public String getStnCode(){ 
		 return stnCode; 
	 } 
 	 public void setStnName(String stnName){ 
		 this.stnName=stnName; 
	 } 
 
	 public String getStnName(){ 
		 return stnName; 
	 } 
 	 public void setStnHc(String stnHc){ 
		 this.stnHc=stnHc; 
	 } 
 
	 public String getStnHc(){ 
		 return stnHc; 
	 } 
 	 public void setZydwmc(String zydwmc){ 
		 this.zydwmc=zydwmc; 
	 } 
 
	 public String getZydwmc(){ 
		 return zydwmc; 
	 } 
 	 public void setZydwRs(int zydwRs){ 
		 this.zydwRs=zydwRs; 
	 } 
 
	 public int getZydwRs(){ 
		 return zydwRs; 
	 } 
 	 public void setZydwFzr(String zydwFzr){ 
		 this.zydwFzr=zydwFzr; 
	 } 
 
	 public String getZydwFzr(){ 
		 return zydwFzr; 
	 } 
 	 public void setZydwFzrPhone(String zydwFzrPhone){ 
		 this.zydwFzrPhone=zydwFzrPhone; 
	 } 
 
	 public String getZydwFzrPhone(){ 
		 return zydwFzrPhone; 
	 } 
 	 public void setZydwType(int zydwType){ 
		 this.zydwType=zydwType; 
	 } 
 
	 public int getZydwType(){ 
		 return zydwType; 
	 } 
 	 public void setBz(String bz){ 
		 this.bz=bz; 
	 } 
 
	 public String getBz(){ 
		 return bz; 
	 } 
 	 public void setArea(String area){ 
		 this.area=area; 
	 } 

	public String getArea(){ 
		 return area; 
	 } 
	
	public String toString() {
        return zydwmc;
    }
}
