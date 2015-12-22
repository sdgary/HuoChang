package byxx.android.huochang.task;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "GdmsZxzypb")
public class GdmsZxzypbDTO implements Serializable  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String[] pbStates = {"未派班", "已派班", "已核查"};
    public static final String[] ZXDM_STATE = {"装(火)", "卸(火)", "直装","直卸","装(汽)", "卸(汽)"};
    public static final String[] states = {"已入线", "已开始", "已完成"};

    public static final int PB_WPB = 0; // 未派班
    public static final int PB_YPB = 1;	// 已派班
    public static final int PB_YHC = 2; // 已核查
    public static final int PB_YWC = 3;	// 已完成

    public static final String ZXDM_ZZ = "11";	// 装火车
    public static final String ZXDM_ZX = "22";	// 卸火车
    public static final String ZXDM_XQZH = "33";	// 直装
    public static final String ZXDM_XHZQ = "44";	// 直卸
    public static final String ZXDM_ZQ = "55";	// 装汽车
    public static final String ZXDM_XQ = "66";	//	卸汽车
    public static final String ZXDM_ALL = "77";	// 全部类型（未知）

    public static final int STATE_JH = 0; // 计划
    public static final int STATE_SJ = 1; // 实际
    
    public static final int CREATE_TYPE_UNLOAD = 0; // 卸车计划
    public static final int CREATE_TYPE_LOAD = 1; // 装车计划
    public static final int CREATE_TYPE_YXJH = 2; // 移箱计划
    public static final int CREATE_TYPE_YCJH = 3; // 移仓计划

	@Id
	@Column(name = "zypbId")
     private int zypbId;		// 作业派班ID
	@Column(name = "hyzx")
	 private String hyzx;		// 运货中心
	@Column(name = "hycj")
	 private String hycj;		// 货运车间
	@Column(name = "stnCode")
	 private String stnCode;	// 站名码
	@Column(name = "stnName")
	 private String stnName;	// 站名
	@Column(name = "stnHc")
	 private String stnHc;		// 货场
	@Column(name = "pbr")
	 private String pbr;		// 派班人
	@Column(name = "pbrq")
	 private String pbrq;		// 派班日期
	@Column(name = "ch")
	 private String ch;			// 车号
	@Column(name = "cz")
	 private String cz;			// 车种
	@Column(name = "hph")
	 private String hph;		// 货票号
	@Column(name = "hwpm")
	 private String hwpm;		// 货物品名
	@Column(name = "hwpl")
	 private String hwpl;		// 货物品类
	@Column(name = "zyds")
	 private double zyds;		// 作业吨数
	@Column(name = "zxdm")
	 private String zxdm;		// 装卸代码（装火车、卸火车、直装、直卸等）
	@Column(name = "zyfs")
	private String zyfs; 		// 作业方式（如：人力、人机、机械、叉机等）
	@Column(name = "zydd")
	private String zydd;		// 作业地点
	@Column(name = "zygb")
	private String zygb; 		// 作业工班
	@Column(name = "pbTime")
	private Timestamp pbTime;	// 派班时间
	@Column(name = "remark")
	private String remark;		// 备注
	@Column(name = "pbFlag")
	private int pbFlag;		// 派班标志
	@Column(name = "zydwId")
	private int zydwId;		// 作业单位ID
	@Column(name = "zydw")
	private String zydw;		// 作业单位
	@Column(name = "hpId")
	private String hpId;		// 货票ID
	@Column(name = "pbgw")
	private String pbgw;		// 派班岗位
	@Column(name = "xh")
	private String xh;			// 箱号
	@Column(name = "xw")
	private String xw;			// 箱位
	@Column(name = "gdm")
	private String gdm;		// 股道码
	@Column(name = "sbgw")
	private String sbgw;		// 上报岗位（或者创建岗位）
	@Column(name = "sbrq")
	private String sbrq;		// 上报日期
	@Column(name = "ksTime")
	private Timestamp ksTime;	// 开始作业时间
	@Column(name = "zzTime")
	private Timestamp zzTime;	// 结束时间
	@Column(name = "rxtime")
	private Timestamp rxtime;		// 入线时间
	@Column(name = "personName")
	private String personName;	//
	@Column(name = "zxr")
	private String zxr;		// 装卸作业上报人（工班上报人）
	@Column(name = "emptyFlag")
	private int emptyFlag;		// 空重标志（如空箱还是重箱）
	@Column(name = "cphm")
	private String cphm;		// 车牌号码
	@Column(name = "uniqueCode")
	private String uniqueCode;	// 唯一标识（与其他表的关联）
	@Column(name = "xx")
	private String xx;			// 箱型
	@Column(name = "xxds")
	private double xxds;		// 箱型吨数
	@Column(name = "planKsTime")
	private Timestamp planKsTime;	// 计划开始时间
	@Column(name = "planJsTime")
	private Timestamp planJsTime;	// 计划结束时间
	@Column(name = "zyjj")
	private String zyjj;		// 作业机具
	@Column(name = "state")
	private int state;			// 状态标志
	@Column(name = "zyjs")
	private int zyjs;			// 作业件数
	@Column(name = "zycid")
	private int zycid;			// 作业车
	@Column(name = "bc")
	private int bc;			// 班次
	@Column(name = "createType")
	private int createType;	// 创建类型
	@Column(name = "createPerson")
	private String createPerson;	// 创建人
	@Column(name = "createTime")
	private Timestamp createTime;	// 创建时间
	@Column(name = "updatePerson")
	private String updatePerson;	// 更新人
	@Column(name = "updateTime")
	private Timestamp updateTime;	// 更新时间
	@Column(name = "swh")
	private int swh;				// 顺位号
	@Column(name = "qsId")
	private int qsId;				// 清算ID
	@Column(name = "zxfy")
	private double zxfy;			// 装卸费用
	@Column(name = "lsh")
	private String lsh;			// 流水号（用来做打印的）
	@Column(name = "zydwType")
	private String zydwType;  // 作业工班类型（比如：路工，委外）
	@Column(name = "oldHw")
	private String oldHw;	// 旧货位
	@Column(name = "newHw")
	private String newHw;  // 新货位

	@Column(name = "cardId")
	 private String cardId;	  // 一卡通ID
	@Column(name = "bz")
	private String bz;	// 作业班组

	@Column(name = "zydwDto")
	private GdmsZydwDTO zydwDto;

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
 	 public void setStnHc(String stnHc){ 
		 this.stnHc=stnHc; 
	 } 
 
	 public String getStnHc(){ 
		 return stnHc; 
	 } 
 	 public void setPbr(String pbr){ 
		 this.pbr=pbr; 
	 } 
 
	 public String getPbr(){ 
		 return pbr; 
	 } 
 	 public void setPbrq(String pbrq){ 
		 this.pbrq=pbrq; 
	 } 
 
	 public String getPbrq(){ 
		 return pbrq; 
	 } 
 	 public void setCh(String ch){ 
		 this.ch=ch; 
	 } 
 
	 public String getCh(){ 
		 return ch; 
	 } 
 	 public void setCz(String cz){ 
		 this.cz=cz; 
	 } 
 
	 public String getCz(){ 
		 return cz; 
	 } 
 	 public void setHph(String hph){ 
		 this.hph=hph; 
	 } 
 
	 public String getHph(){ 
		 return hph; 
	 } 
 	 public void setHwpm(String hwpm){ 
		 this.hwpm=hwpm; 
	 } 
 
	 public String getHwpm(){ 
		 return hwpm; 
	 } 
 	 public void setHwpl(String hwpl){ 
		 this.hwpl=hwpl; 
	 } 
 
	 public String getHwpl(){ 
		 return hwpl; 
	 } 
 	 public void setZyds(double zyds){ 
		 this.zyds=zyds; 
	 } 
 
	 public double getZyds(){ 
		 return zyds; 
	 } 
	 
 	 public void setZyfs(String zyfs){ 
		 this.zyfs=zyfs; 
	 } 
 
	 public String getZyfs(){ 
		 return zyfs; 
	 } 
 	
	 public void setPbTime(Timestamp pbTime){ 
		 this.pbTime=pbTime; 
	 } 
 
	 public Timestamp getPbTime(){ 
		 return pbTime; 
	 } 
 	 public void setRemark(String remark){ 
		 this.remark=remark; 
	 } 
 
	 public String getRemark(){ 
		 return remark; 
	 } 
 	 public void setZypbId(int zypbId){ 
		 this.zypbId=zypbId; 
	 } 
 
	 public int getZypbId(){ 
		 return zypbId; 
	 } 
 	 public void setPbFlag(int pbFlag){ 
		 this.pbFlag=pbFlag; 
	 } 
 
	 public int getPbFlag(){ 
		 return pbFlag; 
	 } 
 	 public void setZydwId(int zydwId){ 
		 this.zydwId=zydwId; 
	 } 
 
	 public int getZydwId(){ 
		 return zydwId; 
	 } 

	 public void setHpId(String hpId){ 
		 this.hpId=hpId; 
	 } 
 
	 public String getHpId(){ 
		 return hpId; 
	 } 
 	 public void setPbgw(String pbgw){ 
		 this.pbgw=pbgw; 
	 } 
 
	 public String getPbgw(){ 
		 return pbgw; 
	 } 
 	 public void setXh(String xh){ 
		 this.xh=xh; 
	 } 
 
	 public String getXh(){ 
		 return xh; 
	 } 
 	 public void setXw(String xw){ 
		 this.xw=xw; 
	 } 
 
	 public String getXw(){ 
		 return xw; 
	 } 
 	 public void setGdm(String gdm){ 
		 this.gdm=gdm; 
	 } 
 
	 public String getGdm(){ 
		 return gdm; 
	 } 
 	 public void setSbgw(String sbgw){ 
		 this.sbgw=sbgw; 
	 } 
 
	 public String getSbgw(){ 
		 return sbgw; 
	 } 
 	 public void setKsTime(Timestamp ksTime){ 
		 this.ksTime=ksTime; 
	 } 
 
	 public Timestamp getKsTime(){ 
		 return ksTime; 
	 } 
 	 public void setZzTime(Timestamp zzTime){ 
		 this.zzTime=zzTime; 
	 } 
 
	 public Timestamp getZzTime(){ 
		 return zzTime; 
	 } 
 	 public void setPersonName(String personName){ 
		 this.personName=personName; 
	 } 
 
	 public String getPersonName(){ 
		 return personName; 
	 } 

	 public void setZxr(String zxr){ 
		 this.zxr=zxr; 
	 } 
 
	 public String getZxr(){ 
		 return zxr; 
	 } 
 	 public void setCphm(String cphm){ 
		 this.cphm=cphm; 
	 } 
 
	 public String getCphm(){ 
		 return cphm; 
	 } 
 	 public void setUniqueCode(String uniqueCode){ 
		 this.uniqueCode=uniqueCode; 
	 } 
 
	 public String getUniqueCode(){ 
		 return uniqueCode; 
	 } 
 	 public void setXx(String xx){ 
		 this.xx=xx; 
	 } 
 
	 public String getXx(){ 
		 return xx; 
	 } 
 	 public void setXxds(double xxds){ 
		 this.xxds=xxds; 
	 } 
 
	 public double getXxds(){ 
		 return xxds; 
	 } 
 	 public void setPlanKsTime(Timestamp planKsTime){ 
		 this.planKsTime=planKsTime; 
	 } 
 
	 public Timestamp getPlanKsTime(){ 
		 return planKsTime; 
	 } 
 	 public void setPlanJsTime(Timestamp planJsTime){ 
		 this.planJsTime=planJsTime; 
	 } 
 
	 public Timestamp getPlanJsTime(){ 
		 return planJsTime; 
	 } 
 	 public void setRxtime(Timestamp rxtime){ 
		 this.rxtime=rxtime; 
	 } 
 
	 public Timestamp getRxtime(){ 
		 return rxtime; 
	 } 
 	 public void setZyjj(String zyjj){ 
		 this.zyjj=zyjj; 
	 } 
 
	 public String getZyjj(){ 
		 return zyjj; 
	 } 
 	 public void setState(int state){ 
		 this.state=state; 
	 } 
 
	 public int getState(){ 
		 return state; 
	 } 
 	 public void setZyjs(int zyjs){ 
		 this.zyjs=zyjs; 
	 } 
 
	 public int getZyjs(){ 
		 return zyjs; 
	 } 

	public GdmsZydwDTO getZydwDto() {
		return zydwDto;
	}

	public void setZydwDto(GdmsZydwDTO zydwDto) {
		this.zydwDto = zydwDto;
	}

	public int getZycid() {
		return zycid;
	}

	public void setZycid(int zycid) {
		this.zycid = zycid;
	}

	public String getStnName() {
		return stnName;
	}

	public void setStnName(String stnName) {
		this.stnName = stnName;
	}

	public String getZydwType() {
		return zydwType;
	}

	public void setZydwType(String zydwType) {
		this.zydwType = zydwType;
	}
	
	public String getSbrq() {
		return sbrq;
	}

	public void setSbrq(String sbrq) {
		this.sbrq = sbrq;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public int getBc() {
		return bc;
	}

	public void setBc(int bc) {
		this.bc = bc;
	}

	public String getBz() {
		return bz;
	}
	
	public void setBz(String bz) {
		this.bz = bz;
	}

	public int getCreateType() {
		return createType;
	}

	public void setCreateType(int createType) {
		this.createType = createType;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public int getSwh() {
		return swh;
	}

	public void setSwh(int swh) {
		this.swh = swh;
	}

	public int getQsId() {
		return qsId;
	}

	public void setQsId(int qsId) {
		this.qsId = qsId;
	}

	public double getZxfy() {
		return zxfy;
	}

	public void setZxfy(double zxfy) {
		this.zxfy = zxfy;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getZxdm() {
		return zxdm;
	}

	public String getZygb() {
		return zygb;
	}

	public void setZygb(String zygb) {
		this.zygb = zygb;
	}

	public String getZydw() {
		return zydw;
	}

	public void setZydw(String zydw) {
		this.zydw = zydw;
	}

	public void setZxdm(String zxdm) {
		this.zxdm = zxdm;
	}

	public String getZydd() {
		return zydd;
	}

	public void setZydd(String zydd) {
		this.zydd = zydd;
	}

	public int getEmptyFlag() {
		return emptyFlag;
	}

	public void setEmptyFlag(int emptyFlag) {
		this.emptyFlag = emptyFlag;
	}
	
	public String getOldHw() {
		return oldHw;
	}

	public void setOldHw(String oldHw) {
		this.oldHw = oldHw;
	}

	public String getNewHw() {
		return newHw;
	}

	public void setNewHw(String newHw) {
		this.newHw = newHw;
	}
	
	/**
     * 根据作业类型得到类型名称
     * @param zxdm
     */
    public static String getZxdmName(String zxdm) {
        if (ZXDM_ZZ.equals(zxdm)) {
            return ZXDM_STATE[0];
        } else if (ZXDM_ZX.equals(zxdm)) {
            return ZXDM_STATE[1];
        } else if (ZXDM_XQZH.equals(zxdm)) {
            return ZXDM_STATE[2];
        } else if (ZXDM_XHZQ.equals(zxdm)) {
            return ZXDM_STATE[3];
        } else if (ZXDM_ZQ.equals(zxdm)) {
            return ZXDM_STATE[4];
        } else if (ZXDM_XQ.equals(zxdm)) {
            return ZXDM_STATE[5];
        }
        return "未知";
    }

    /**
     * 根据作业类型名称得到类型
     * @param zyTypeName
     */
    public static String getZxdm(String zyTypeName) {
        if (zyTypeName.equals(ZXDM_STATE[0])) {
            return ZXDM_ZZ;
        } else if (zyTypeName.equals(ZXDM_STATE[1])) {
            return ZXDM_ZX;
        } else if (zyTypeName.equals(ZXDM_STATE[2])) {
            return ZXDM_XQZH;
        } else if (zyTypeName.equals(ZXDM_STATE[3])) {
            return ZXDM_XHZQ;
        } else if (zyTypeName.equals(ZXDM_STATE[4])) {
            return ZXDM_ZQ;
        } else if (zyTypeName.equals(ZXDM_STATE[5])) {
            return ZXDM_XQ;
        }
        return ZXDM_ALL;
    }
    
    public String getPbFlagStr(){
		 
		 if(getPbFlag() == PB_WPB){
			 return "未派班";
		 }else if(getPbFlag() == PB_YPB){
			 return "已派班";
		 }else if(getPbFlag() == PB_YHC){
			 return "已开始";
		 }else if(getPbFlag() == PB_YWC){
			 return "已完成";
		 }
		 return "未知状态";
	 }

}
