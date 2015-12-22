package byxx.android.huochang.task;

import java.io.Serializable;
import java.sql.Timestamp;

import android.util.Log;

/**
 * @author Bluray
 * 装运单
 */
public class ZydDto implements Serializable {

	private static final long serialVersionUID = 7415259756826540272L;
	public static final String[] pbStates = {"未派班", "已派班", "已核查", "已完成"};
	public static final String[] zyTypeStates = {"直装", "直卸", "卸汽车装火车","卸火车装汽车","装汽车","卸汽车"};
    public static final int PB_WPB = 0;
    public static final int PB_YPB = 1;
    public static final int PB_YHC = 2;
    public static final int PB_YWC = 3;

    public static final int ZY_TYPE_ZZ = 11;
    public static final int ZY_TYPE_ZX = 22;
    public static final int ZY_TYPE_XQZH = 33;
    public static final int ZY_TYPE_XHZQ = 44;
    public static final int ZY_TYPE_ZQ = 55;
    public static final int ZY_TYPE_XQ = 66;
    public static final int ZY_TYPE_ALL = 77;

    private String hyzx;//货运中心名称
    private String hycj;//车间
    private String stnCode;
    private String stnName;
    private String stnHc;
    private String pbr;//派班人
    private String pbrq;//派班日期
    private String ch;//车号
    private String cz;//车种
    private String hph;//货票号
    private String hwpm;//品名
    private String hwpl;//品类 //整车/集装箱  ：
    private double zyds;//吨数
    private int zyType;//作业类型
    private String zyTypeHz;//作业类型汉字
    private String zyfs;//作业方式
    private String zyAddr;//作业地点
    private Timestamp pbTime;//派班时间
    private String remark;//备注
    private int zypbId;//作业单ID(上报时使用)
    private int pbFlag;//派班标记(常量pbStates)
    private String zydwmc;//作业单位名
    private String zydwGb;//作业工班
    private String hpId;//货票id
    private String pbgw;//派单岗位
    private String xh;//集装箱号
    private String xw;//箱位
    private String gdm;//股道码
    private String zxZyr;//装卸人
    private Timestamp ksTime;//装卸开始时间
    private Timestamp zzTime;//装卸结束时间
    private String zybc;//作业班次
    private String truckNum;//车牌号码
	private String cardId;//一卡通ID
	private int state;
    // way 定义的字段
	public boolean isKS = true;
	public boolean isJS = true;
    //
    
    public String getZybc(){
        return zybc;
    }

    public void setZybc(String zybc){
        this.zybc=zybc;
    }
    public String getHyzx() {
        return hyzx;
    }

    public void setHyzx(String hyzx) {
        this.hyzx = hyzx;
    }

    public String getHycj() {
        return hycj;
    }

    public void setHycj(String hycj) {
        this.hycj = hycj;
    }

    public String getStnCode() {
        return stnCode;
    }

    public void setStnCode(String stnCode) {
        this.stnCode = stnCode;
    }

    public String getStnName() {
        return stnName;
    }

    public void setStnName(String stnName) {
        this.stnName = stnName;
    }

    public String getStnHc() {
        return stnHc;
    }

    public void setStnHc(String stnHc) {
        this.stnHc = stnHc;
    }

    public String getPbr() {
        return pbr;
    }

    public void setPbr(String pbr) {
        this.pbr = pbr;
    }

    public String getPbrq() {
        return pbrq;
    }

    public void setPbrq(String pbrq) {
        this.pbrq = pbrq;
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

    public String getHph() {
        return hph;
    }

    public void setHph(String hph) {
        this.hph = hph;
    }

    public String getHwpm() {
        return hwpm;
    }

	public void setHwpm(String hwpm) {
        this.hwpm = hwpm;
    }

    public String getZydwGb() {
		return zydwGb;
	}

	public void setZydwGb(String zydwGb) {
		this.zydwGb = zydwGb;
	}

	public String getZydwmc() {
		return zydwmc;
	}

	public void setZydwmc(String zydwmc) {
		this.zydwmc = zydwmc;
	}

	public Timestamp getKsTime() {
		return ksTime;
	}

	public void setKsTime(Timestamp ksTime) {
		this.ksTime = ksTime;
	}

	public Timestamp getZzTime() {
		return zzTime;
	}

	public void setZzTime(Timestamp zzTime) {
		this.zzTime = zzTime;
	}

	public String getGdm() {
		return gdm;
	}

	public void setGdm(String gdm) {
		this.gdm = gdm;
	}

	public String getHwpl() {
        return hwpl;
    }

    public String getXw() {
		return xw;
	}

	public void setXw(String xw) {
		this.xw = xw;
	}

	public String getXh() {
		return xh;
	}

	public String[] getXhs(){
		String[] s  = null;
		if(getXh() != null){
			s = getXh().split(",");
		}
		return s;
	}
	
	public String getXh1(){
		String s = null;
		try {
			if(getXhs() != null && getXhs()[0] != null){

				s = getXhs()[0];

			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("xh", e+"");
		}

		return s;
	}
	public String getXh2(){
		String s = null;
		try {
			if(getXhs() != null && getXhs().length  > 1 && getXhs()[1] != null){
				
				s = getXhs()[1];
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("xh", e+"");
		}

		return s;
	}
	public String[] getXws(){
		String[] s  = null;
		if(getXw() != null){
			s = getXw().split(",");
		}
		return s;
	}
	
	public String getXw1(){
		String s = null;
		try {
			if(getXws() != null && getXws()[0] != null){
				
				s = getXws()[0];
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("xh", e+"");
		}

		return s;
	}
	
	public String getXw2(){
		String s = null;
		try {
			if(getXws() != null && getXws().length  > 1 && getXws()[1] != null){
				
				s = getXws()[1];
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("xh", e+"");
		}

		return s;
	}
	
 
	
	public void setXh(String xh) {
		this.xh = xh;
	}

	public void setHwpl(String hwpl) {
        this.hwpl = hwpl;
    }

    public double getZyds() {
        return zyds;
    }

	public void setZyds(double zyds) {
        this.zyds = zyds;
    }

    public int getZyType() {
        return zyType;
    }

	public void setZyType(int zyType) {
        this.zyType = zyType;
    }

    public String getZyTypeHz() {
        return zyTypeHz;
    }

	public void setZyTypeHz(String zyTypeHz) {
        this.zyTypeHz = zyTypeHz;
    }

    public String getZyfs() {
        return zyfs;
    }

    public void setZyfs(String zyfs) {
        this.zyfs = zyfs;
    }

    public String getZyAddr() {
        return zyAddr;
    }

    public void setZyAddr(String zyAddr) {
        this.zyAddr = zyAddr;
    }

    public Timestamp getPbTime() {
        return pbTime;
    }

    public void setPbTime(Timestamp pbTime) {
        this.pbTime = pbTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getZypbId() {
        return zypbId;
    }

    public int getPbFlag() {
        return pbFlag;
    }

    public void setZypbId(int zypbId) {
        this.zypbId = zypbId;
    }

    public void setPbFlag(int pbFlag) {
        this.pbFlag = pbFlag;
    }

	public String getHpId() {
		return hpId;
	}

	public void setHpId(String hpId) {
		this.hpId = hpId;
	}

	public String getPbgw() {
		return pbgw;
	}

	public void setPbgw(String pbgw) {
		this.pbgw = pbgw;
	}

	public String getZxZyr() {
		return zxZyr;
	}

	public void setZxZyr(String zxZyr) {
		this.zxZyr = zxZyr;
	}

	public boolean isKszyflag(){
		return (getKsTime() != null);
	}
	public boolean isJszyflag(){
		return (getZzTime() != null);
	}

	public String getTruckNum() {
		return truckNum;
	}

	public void setTruckNum(String truckNum) {
		this.truckNum = truckNum;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	

	
	
}
