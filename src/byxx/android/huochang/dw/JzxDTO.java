package byxx.android.huochang.dw;

import java.io.Serializable;

public class JzxDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3203030139577840132L;
	/**
	 * 计划号
	 */
	private String jhh;
	/**
	 * 车号
	 */
	private String ch;
	/**
	 * 车型
	 */
	private String cz;
	/**
	 * 到达类型
	 */
	private String ddlx;
	/**
	 * 箱型1
	 */
	private String xx1;
	/**
	 * 箱型
	 */
	private String xx2;
	/**
	 * 箱号1
	 */
	private String xh1;
	/**
	 * 箱号2
	 */
	private String xh2;
	/**
	 * 箱一问题一
	 */
	private String problem1;
	private String problem2;
	/**
	 * 是否选择
	 */
	private boolean isChecked;
	public String getJhh() {
		return jhh;
	}
	public void setJhh(String jhh) {
		this.jhh = jhh;
	}
	public String getCh() {
		return ch;
	}
	public void setCh(String ch) {
		this.ch = ch;
	}
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public String getDdlx() {
		return ddlx;
	}
	public void setDdlx(String ddlx) {
		this.ddlx = ddlx;
	}
	public String getXx1() {
		return xx1;
	}
	public void setXx1(String xx1) {
		this.xx1 = xx1;
	}
	public String getXx2() {
		return xx2;
	}
	public void setXx2(String xx2) {
		this.xx2 = xx2;
	}
	public String getXh1() {
		return xh1;
	}
	public void setXh1(String xh1) {
		this.xh1 = xh1;
	}
	public String getXh2() {
		return xh2;
	}
	public void setXh2(String xh2) {
		this.xh2 = xh2;
	}
	public String getProblem1() {
		return problem1;
	}
	public void setProblem1(String problem1) {
		this.problem1 = problem1;
	}
	public String getProblem2() {
		return problem2;
	}
	public void setProblem2(String problem2) {
		this.problem2 = problem2;
	}

	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
