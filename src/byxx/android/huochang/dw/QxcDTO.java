package byxx.android.huochang.dw;

import java.io.Serializable;

public class QxcDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2758308328882549042L;
	/**
	 * 序号
	 */
	private String xh; 
	/**
	 * 厢号
	 */
	private String jzxnum;
	/**
	 * 计划时间
	 */
	private long plantime;
	/**
	 * 实际时间
	 */
	
	private long actualtime;
	/**
	 * 车数
	 */
	private String trainnum;
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getJzxnum() {
		return jzxnum;
	}
	public void setJzxnum(String jzxnum) {
		this.jzxnum = jzxnum;
	}


	public String getTrainnum() {
		return trainnum;
	}
	public void setTrainnum(String trainnum) {
		this.trainnum = trainnum;
	}
	public long getPlantime() {
		return plantime;
	}
	public void setPlantime(long plantime) {
		this.plantime = plantime;
	}
	public long getActualtime() {
		return actualtime;
	}
	public void setActualtime(long actualtime) {
		this.actualtime = actualtime;
	}
	
	
	
	

}
