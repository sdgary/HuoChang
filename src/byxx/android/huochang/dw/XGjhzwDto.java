package byxx.android.huochang.dw;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;

import byxx.android.huochang.traininfo.CarDto;

public class XGjhzwDto implements Serializable, Comparable, Cloneable {
    private static final long serialVersionUID = 8868477651139230802L;

    private Integer gjhid;// 钩计划ID

    private int swh;// 顺位号

    private String cb;// 场别

    private String gdm;// 股道码

    private String jy;// 经由

    private String zylx;// 作业类型 // + -/x

    private int cs;// 车数

    private String swbz;// 首尾标志

    private String ajsl;// 自动生成的记事栏

    private String mjsl;// 人工编写的记事栏，可续行

    private String ch;// 车号

    private String cc;// 车次

    private Timestamp jhsj;// 计划时间

    private Timestamp zxsj;// 执行时间

    private Timestamp notifyTime;// 通知其他岗位(货运)时间

    private Timestamp confirmTime;// 确认接收时间

    private Timestamp factTime;// 实际完成时间
    private String icCard;// 调机对位刷卡id
    private String dj;// 调机

    private int errorType;// 错误类型
    private int meetingFlag;// 车前会标记
    private String errorStr;//

    private Vector<CarDto> xClkDtos = new Vector<CarDto>();

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
    
    
    public int compareTo(Object object) {
	XGjhzwDto dto = (XGjhzwDto) object;
	return getSwh() - dto.getSwh();
    }

    public Object clone() {
	XGjhzwDto o = null;
	try {
	    o = (XGjhzwDto) super.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	}
	return o;
    }

    public Integer getGjhid() {
	return gjhid;
    }

    public void setGjhid(Integer gjhid) {
	this.gjhid = gjhid;
    }

    public int getSwh() {
	return swh;
    }

    public void setSwh(int swh) {
	this.swh = swh;
    }

    public String getCb() {
	return cb;
    }

    public void setCb(String cb) {
	this.cb = cb;
    }

    public String getGdm() {
	return gdm;
    }

    public void setGdm(String gdm) {
	this.gdm = gdm;
    }

    public String getJy() {
	return jy;
    }

    public void setJy(String jy) {
	this.jy = jy;
    }

    public String getZylx() {
	return zylx;
    }

    public void setZylx(String zylx) {
	this.zylx = zylx;
    }

    public int getCs() {
	return cs;
    }

    public void setCs(int cs) {
	this.cs = cs;
    }

    public String getSwbz() {
	return swbz;
    }

    public void setSwbz(String swbz) {
	this.swbz = swbz;
    }

    public String getAjsl() {
	return ajsl;
    }

    public void setAjsl(String ajsl) {
	this.ajsl = ajsl;
    }

    public String getMjsl() {
	return mjsl;
    }

    public void setMjsl(String mjsl) {
	this.mjsl = mjsl;
    }

    public String getCh() {
	return ch;
    }

    public void setCh(String ch) {
	this.ch = ch;
    }

    public String getCc() {
	return cc;
    }

    public void setCc(String cc) {
	this.cc = cc;
    }

    public Timestamp getJhsj() {
	return jhsj;
    }

    public void setJhsj(Timestamp jhsj) {
	this.jhsj = jhsj;
    }

    public Timestamp getZxsj() {
	return zxsj;
    }

    public void setZxsj(Timestamp zxsj) {
	this.zxsj = zxsj;
    }

    public String toString() {
	StringBuffer sb = new StringBuffer();
	if (gdm != null) {
	    sb.append(gdm);
	    sb.append(" ");
	}
	if (zylx != null) {
	    sb.append(zylx);
	    sb.append(" ");
	    if (zylx.equals("＋") || zylx.equals("－") || zylx.equals("×")) {
		sb.append(cs);
	    }
	}

	return sb.toString();
    }

    public int getErrorType() {
	return errorType;
    }

    public void setErrorType(int errorType) {
	this.errorType = errorType;
    }

    public String getErrorStr() {
	return errorStr;
    }

    public void setErrorStr(String errorStr) {
	this.errorStr = errorStr;
    }

    public String getDj() {
	return dj;
    }

    public void setDj(String dj) {
	this.dj = dj;
    }

    public String getStepContent() {
	StringBuffer sb = new StringBuffer();
	sb.append("gdm:").append(getGdm() == null ? "" : getGdm().trim());
	sb.append(",zylx:").append(getZylx() == null ? "" : getZylx().trim());
	sb.append(",cs:").append(getCs());
	sb.append(",swbz:").append(getSwbz() == null ? "" : getSwbz().trim());
	sb.append(",ajsl:").append(getAjsl() == null ? "" : getAjsl().trim());
	sb.append(",mjsl:").append(getMjsl() == null ? "" : getMjsl().trim());
	sb.append(",cc:").append(getCc() == null ? "" : getCc().trim());
	return sb.toString();
    }

    public Vector getXClkDtos() {
	return xClkDtos;
    }

    public void setXClkDtos(Vector clkDtos) {
	xClkDtos = clkDtos;
    }

    public Timestamp getConfirmTime() {
	return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
	this.confirmTime = confirmTime;
    }

    public Timestamp getFactTime() {
	return factTime;
    }

    public void setFactTime(Timestamp factTime) {
	this.factTime = factTime;
    }

    public Timestamp getNotifyTime() {
	return notifyTime;
    }

    public void setNotifyTime(Timestamp notifyTime) {
	this.notifyTime = notifyTime;
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

	public int getAddflag() {
		return addflag;
	}

	public void setAddflag(int addflag) {
		this.addflag = addflag;
	}

    public String getGJHRN(){
    	return this.gdm+this.zylx+this.cs;
    }

    public String getIcCard() {
	return icCard;
    }

    public void setIcCard(String icCard) {
	this.icCard = icCard;
    }

	public int getMeetingFlag() {
		return meetingFlag;
	}

	public void setMeetingFlag(int meetingFlag) {
		this.meetingFlag = meetingFlag;
	}
    
}
