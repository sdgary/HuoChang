package byxx.android.huochang.storemanager;

import java.io.Serializable;

/**
 * @date 2015-5-20
 * @author Administrator
 */

public class WhZydDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5451577733135237312L;

	public static final String[] ZYLX = { "入库", "出库", "移库", "直装", "直卸" };
	public static final String[] YSFS = { "", "火车", "汽车" };
	public static final String[] ZT = { "未执行", "已执行" };

	public static final int ZYLX_RK = 0;
	public static final int ZYLX_CK = 1;
	public static final int ZYLX_YK = 2;
	public static final int ZYLX_ZZ = 3;
	public static final int ZYLX_ZX = 4;

	public static final int ZT_NO = 0;
	public static final int ZT_YES = 1;

	public static final int YSFS_TL_1 = 1;
	public static final int YSFS_QC_2 = 2;

	public static String Rc = "入仓";
	public static String Cc = "出仓";

	private int zyid;// 作业单ID，自动生成
	private int zylx;// 作业类型
	private String stnname;// 车站名
	private String stncode;// 电报码
	private String createtime;// 创建时间
	private String checker;// 创建人
	private int zt;// 状态，是否执行
	private double num;// 计划包含的件数
	private int ysfs;// 运输方式，铁路或者汽车
	private String prehw;// 前一货位
	private String facthw;// 目标货位
	private String pm;// 品名
	private int goodsid;// 物品ID
	private String plantime;// 计划执行时间
	private String facttime;// 实际执行时间
	private String bz;// 备注
	private WhGoodsDTO goods;// 对应物品DTO，可能为空
	private String bc;// 班次
	private boolean isRight = true;// 推算计划是否正确
	private String gdm;// 股道码
	private String jobId; // 预约流水号

	public int getZyId() {
		return zyid;
	}

	public void setZyId(int zyid) {
		this.zyid = zyid;
	}

	public int getZylx() {
		return zylx;
	}

	public void setZylx(int zylx) {
		this.zylx = zylx;
	}

	public String getStnName() {
		return stnname;
	}

	public void setStnName(String stnname) {
		this.stnname = stnname;
	}

	public String getStnCode() {
		return stncode;
	}

	public void setStnCode(String stncode) {
		this.stncode = stncode;
	}

	public String getCreateTime() {
		return createtime;
	}

	public void setCreateTime(String createtime) {
		this.createtime = createtime;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public int getZt() {
		return zt;
	}

	public void setZt(int zt) {
		this.zt = zt;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public int getYsfs() {
		return ysfs;
	}

	public void setYsfs(int ysfs) {
		this.ysfs = ysfs;
	}

	public String getPreHw() {
		return prehw;
	}

	public void setPreHw(String prehw) {
		this.prehw = prehw;
	}

	public String getFactHw() {
		return facthw;
	}

	public void setFactHw(String facthw) {
		this.facthw = facthw;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public int getGoodsId() {
		return goodsid;
	}

	public void setGoodsId(int goodsid) {
		this.goodsid = goodsid;
	}

	public String getPlanTime() {
		return plantime;
	}

	public void setPlanTime(String plantime) {
		this.plantime = plantime;
	}

	public String getFactTime() {
		return facttime;
	}

	public void setFactTime(String facttime) {
		this.facttime = facttime;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setGoods(WhGoodsDTO goods) {
		this.goods = goods;
	}

	public WhGoodsDTO getGoods() {
		return goods;
	}

	public void setBc(String bc) {
		this.bc = bc;
	}

	public String getBc() {
		return bc;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setGdm(String gdm) {
		this.gdm = gdm;
	}

	public String getGdm() {
		return gdm;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobId() {
		return jobId;
	}
	
	/**
	 * 用于界面显示标志
	 * @return
	 */
	public String getZylxStr(){
		
		if (getZylx()==ZYLX_RK) {
			return "入";
		}else if (getZylx()==ZYLX_CK) {
			return "出";
		}else if (getZylx()==ZYLX_YK) {
			return "移";
		}else if (getZylx()==ZYLX_ZX){
			return "卸";
		}else if (getZylx()==ZYLX_ZZ){
			return "装";
		}
		return "";
		
	}
	
	public String getYsfsStr(){
		
	if (getYsfs()==YSFS_TL_1) {
		return "铁路";
	}else if (getYsfs()==YSFS_QC_2) {
		return "汽车";
	}
	return "";
	
}
}
