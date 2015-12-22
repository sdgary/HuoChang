package byxx.android.huochang.jf;

import java.util.List;

public class MoDelivery {
	public static final String[] proflagNames = {"未处理", "已到站", "已卸车", "已交付", "已出站"};
	  public static final int PROFLAG_WCL = 0; //未处理
	  public static final int PROFLAG_YZD = 1; //已到站
	  public static final int PROFLAG_YXC = 2; //已卸车
	  public static final int PROFLAG_YJF = 3; //已交付
	  public static final int PROFLAG_YCZ = 4; //已出站
	List<GdmsUnloadJfDTO> gdmsUnloadJfDTOs;
	GdmsUnloadJfDTO value;
	private boolean scanflag = false;
	public boolean isScanflag() {
		return scanflag;
	}

	public void setScanflag(boolean scanflag) {
		this.scanflag = scanflag;
	}

	private String scanResult = null;

	public String getScanResult() {
		return scanResult;
	}

	public void setScanResult(String scanResult) {
		this.scanResult = scanResult;
	}
	public List<GdmsUnloadJfDTO> getGdmsUnloadJfDTOs() {
		return gdmsUnloadJfDTOs;
	}
	
	



	public GdmsUnloadJfDTO getValue() {
		return value;
	}





	public void setValue(GdmsUnloadJfDTO value) {
		this.value = value;
	}





	public GdmsUnloadJfDTO findid(String id){
		GdmsUnloadJfDTO dto = null;
		for(int i = 0;i< gdmsUnloadJfDTOs.size();i++){
			if(gdmsUnloadJfDTOs.get(i).getHpid().equals(id)){
				dto = getGdmsUnloadJfDTOs().get(i);
				break;
			}
		}
		return dto;
		
	}
	
}
