package byxx.android.huochang.storemanager;

import java.io.Serializable;
import java.util.Vector;

/**
 * @date 2015-5-19
 * @author Administrator
 */

public class WhGoodsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245090153335112036L;

	public static final String[] ZT = { "", "待入库", "已入库", "待出库", "已出库" };
	public static final String[] YSLX = { "", "火车", "汽车" };

	public static final int ZT_NEW = 0;
	public static final int ZT_DRk = 1;
	public static final int ZT_YRK = 2;
	public static final int ZT_DCK = 3;
	public static final int ZT_YCK = 4;

	public static final int ZN_IN = 1;
	public static final int ZN_OUT = 0;

	public static final int YSLX_TARIN = 1;
	public static final int YSLX_CAR = 2;

	private int id;
	private String stncode;
	private String stnname;
	private int ztflag;//状态标志
	private int znflag;//站内状态
	private String pm;
	private String shr;//收货人
	private String fhr;//发货人
	private String hph;//货票号
	private String ydh;//运单号
	private double weight;
	private double numzg;//总计数量
	private double numyrk;//要出库量
	private double numyck;//要入库量
	private String jldw;//计量单位
	private int fy;//费用
	private String planintime;//计划入库
	private String factintime;//实际入库
	private String planouttime;//计划出库
	private String factouttime;//实际出库
	private String ddcarnum;//到达车号
	private int ddlx;//类型
	private String cfcarnum;//出发车号
	private int cflx;//触发类型
	private String yydh;// 预约单号
	private String bz;//条形码
	private Vector wzs;
	private Vector zyds;

	public int getId() {

		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStncode() {
		if(stncode == null){
			stncode = "";
		}
		return stncode;
	}

	public void setStncode(String stncode) {
		this.stncode = stncode;
	}

	public String getStnname() {
		if(stnname == null){
			stnname = "";
		}
		return stnname;
	}

	public void setStnname(String stnname) {
		this.stnname = stnname;
	}

	public int getZt_flag() {
		
		return ztflag;
	}

	public void setZt_flag(int ztflag) {
		this.ztflag = ztflag;
	}

	public int getZn_flag() {
		return znflag;
	}

	public void setZn_flag(int znflag) {
		this.znflag = znflag;
	}

	public String getPm() {
		if(pm == null){
			pm = "";
		}
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getShr() {
		if(shr == null){
			shr = "";
		}
		return shr;
	}

	public void setShr(String shr) {
		this.shr = shr;
	}

	public String getFhr() {
		if(fhr == null){
			fhr = "";
		}
		return fhr;
	}

	public void setFhr(String fhr) {
		this.fhr = fhr;
	}

	public String getHph() {
		if(hph == null){
			hph = "";
		}
		return hph;
	}

	public void setHph(String hph) {
		this.hph = hph;
	}

	public String getYdh() {
		if(ydh == null){
			ydh = "";
		}
		return ydh;
	}

	public void setYdh(String ydh) {
		this.ydh = ydh;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getNumZg() {
		return numzg;
	}

	public void setNumZg(double numzg) {
		this.numzg = numzg;
	}

	public double getNumYrk() {
		return numyrk;
	}

	public void setNumYrk(double numyrk) {
		this.numyrk = numyrk;
	}

	public double getNumYck() {
		return numyck;
	}

	public void setNuYck(double numyck) {
		this.numyck = numyck;
	}

	public String getJldw() {
		if(jldw == null){
			jldw = "";
		}

		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	public int getFy() {
		return fy;
	}

	public void setFy(int fy) {
		this.fy = fy;
	}

	public String getPlanintime() {
		if(planintime == null){
			planintime = "";
		}
		return planintime;
	}

	public void setPlanintime(String planintime) {
		this.planintime = planintime;
	}

	public String getFactintime() {
		if(factintime == null){
			factintime = "";
		}
		return factintime;
	}

	public void setFactintime(String factintime) {
		this.factintime = factintime;
	}

	public String getPlanouttime() {
		if(planouttime == null){
			planouttime = "";
		}
		return planouttime;
	}

	public void setPlanouttime(String planouttime) {
		this.planouttime = planouttime;
	}

	public String getFactouttime() {
		if(factouttime == null){
			factouttime = "";
		}
		return factouttime;
	}

	public void setFactouttime(String factouttime) {
		this.factouttime = factouttime;
	}

	public String getDdcarnum() {
		if(ddcarnum == null){
			ddcarnum = "";
		}
		return ddcarnum;
	}

	public void setDdcarnum(String ddcarnum) {
		this.ddcarnum = ddcarnum;
	}

	public int getDdlx() {
		return ddlx;
	}

	public void setDdlx(int ddlx) {
		this.ddlx = ddlx;
	}

	public String getCfcarnum() {
		if(cfcarnum == null){
			cfcarnum = "";
		}
		return cfcarnum;
	}

	public void setCfcarnum(String cfcarnum) {
		this.cfcarnum = cfcarnum;
	}

	public int getCflx() {
		return cflx;
	}

	public void setCflx(int cflx) {
		this.cflx = cflx;
	}

	public String getYydh() {
		if(yydh == null){
			yydh = "";
		}
		return yydh;
	}

	public void setYydh(String yydh) {
		this.yydh = yydh;
	}

	public String getBz() {
		if(bz == null){
			bz = "";
		}
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setZyds(Vector zyds) {
		this.zyds = zyds;
	}

	public Vector getZyds() {
		return zyds;
	}

	public void setWzs(Vector wzs) {
		this.wzs = wzs;
	}

	public Vector getWzs() {
		return wzs;
	}

	/**
	 * @return
	 */
	public String getCkm() {
		return null;
	}

	public String getZtStr(){
		if (getZt_flag() == ZT_NEW){
			return "";
		}else if(getZt_flag() == ZT_DRk){
			return "待入库";
		}else if(getZt_flag() == ZT_YRK){
			return "已入库";
		}else if(getZt_flag() == ZT_DCK){
			return "待出库";
		}else if(getZt_flag() == ZT_YCK){
			return "已出库";
		}else{
			return "";
		}
	}
	
	public String getState(){
		String state = "";
		if(getZn_flag() == 0){
			state = state + "站外";
		}else if(getZn_flag() == 1){
			state = state + "站内";
		}
//		if(state != ""){
			state = state + getZtStr();
//		}
		return state;
	}
	
	public String getYSLX( int lx){
		if(lx == 1){
			return "火车";
		}else if(lx == 2){
			return "汽车";
		}else{
			return "";
		}
	}
}
