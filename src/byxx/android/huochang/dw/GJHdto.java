package byxx.android.huochang.dw;

import java.io.Serializable;



public class GJHdto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5911157661801235294L;
	/**
	 * 计划号
	 */
	String jhno;
	/**
	 * 车数
	 */
	String trainnum;
	/**
	 * 实际时间
	 */
	long actualtime;
	/**
	 * 计划时间
	 */
	long  plantime;
	/**
	 * 完成未完成标志 背景颜色改变
	 * 
	 */
	int layout_color;
	
	/**
	 * 取送标志 背景颜色改变
	 */
	int qscolor_flag;
	
	int addflag; //增加任务标志 1为服务器添加 2为人工提交 默认为0

	public String getJhno() {
		return jhno;
	}

	public void setJhno(String jhno) {
		this.jhno = jhno;
	}

	public String getTrainnum() {
		return trainnum;
	}

	public void setTrainnum(String trainnum) {
		this.trainnum = trainnum;
	}

	public long getActualtime() {
		return actualtime;
	}

	public void setActualtime(long actualtime) {
		this.actualtime = actualtime;
	}

	public long getPlantime() {
		return plantime;
	}

	public void setPlantime(long plantime) {
		this.plantime = plantime;
	}

	public int getLayout_color() {
		return layout_color;
	}

	public void setLayout_color(int layout_color) {
		this.layout_color = layout_color;
	}

	public int getQscolor_flag() {
		return qscolor_flag;
	}

	public void setQscolor_flag(int qscolor_flag) {
		this.qscolor_flag = qscolor_flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getAddflag() {
		return addflag;
	}

	public void setAddflag(int addflag) {
		this.addflag = addflag;
	}
	
	
	
	
}
