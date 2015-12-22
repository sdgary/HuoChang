/**
 * 
 */
package byxx.android.huochang.movebox;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author WGary 韦永城
 * 2015-6-6
 */
public class JzxXwDTO implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = -4760370786984684590L;

    //卸车类型
    public final static String XCTYPE_TRAIN = "0";
    public final static String XCTYPE_CAR = "1";
    //装车类型
    public final static String ZCTYPE_TRAIN = "0";
    public final static String ZCTYPE_CAR = "1";
    //箱主
    public final static String XZ_TB = "TB";
    public final static String XZ_ZB = "ZB";
    //箱态
    public final static String XT_Y = "Y";
    public final static String XT_N = "N";
    //箱况
    public final static String XK_Y = "Y";
    public final static String XK_N = "N";
    //站内标志
    public final static String ZNFLAG_Y = "Y";
    public final static String ZNFLAG_N = "N";
    //集装箱种类
    public final static String JZXTYPE_TB = "TBTY";
    public final static String JZXTYPE_ZB = "ZBTY";
    //运用状态
    public final static String USEFLAG_T = "T"; // "运用箱";
    public final static String USEFLAG_C = "C"; // "待确认箱";
    public final static String USEFLAG_X = "X"; // "修理箱";
    public final static String USEFLAG_D = "D"; // "待报废箱";
    public final static String USEFLAG_B = "B"; // "备用箱";
    public final static String USEFLAG_L = "L"; // "";
    public final static String USEFLAG_F = "F"; // "";

    private String zmlm; //站名略码
    private String jzxnum; //集装箱号
    private String jzxcode; //箱主代码
    private String hph; //货票号
    
    private Timestamp xcrq; //卸车日期
    private Timestamp zcrq; //装车日期
    
    private String xw; //箱位
    private String zctype; //装车类型
    private String xctype; //卸车类型
    private String xz; //箱主
    private String xt; //箱态
    private String xk; //箱况
    private String znflag; //站内标志
    private String useflag; //运用标志
    private String model; //箱型（尺寸）
    private String jzxtype; //箱种类
    private String ydh; //运单号
    private String xwNum; //箱位层数
    private String gdm; //股道码


    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public String getZmlm() {
        return zmlm;
    }

    public void setZmlm(String zmlm) {
        this.zmlm = zmlm;
    }

    public String getJzxnum() {
        return jzxnum;
    }

    public void setJzxnum(String jzxnum) {
        this.jzxnum = jzxnum;
    }

    public String getJzxcode() {
        return jzxcode;
    }

    public void setJzxcode(String jzxcode) {
        this.jzxcode = jzxcode;
    }

    public String getHph() {
        return hph;
    }

    public void setHph(String hph) {
        this.hph = hph;
    }

    public Timestamp getXcrq() {
        return xcrq;
    }

    public void setXcrq(Timestamp xcrq) {
        this.xcrq = xcrq;
    }

    public Timestamp getZcrq() {
        return zcrq;
    }

    public void setZcrq(Timestamp zcrq) {
        this.zcrq = zcrq;
    }

    public String getXw() {
        return xw;
    }

    public void setXw(String xw) {
        this.xw = xw;
    }

    public String getZctype() {
        return zctype;
    }

    public void setZctype(String zctype) {
        this.zctype = zctype;
    }

    public String getXctype() {
        return xctype;
    }

    public void setXctype(String xctype) {
        this.xctype = xctype;
    }

    public String getXz() {
        return xz;
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    public String getXt() {
        return xt;
    }

    public void setXt(String xt) {
        this.xt = xt;
    }

    public String getXk() {
        return xk;
    }

    public void setXk(String xk) {
        this.xk = xk;
    }

    public String getZnflag() {
        return znflag;
    }

    public void setZnflag(String znflag) {
        this.znflag = znflag;
    }

    public String getUseflag() {
        return useflag;
    }

    public String getJzxtype() {
        return jzxtype;
    }

    public String getModel() {
        return model;
    }

    public String getYdh() {
        return ydh;
    }

    public String getGdm() {
        return gdm;
    }

    public String getXwNum() {
        return xwNum;
    }

    public void setUseflag(String useflag) {
        this.useflag = useflag;
    }

    public void setJzxtype(String jzxtype) {
        this.jzxtype = jzxtype;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYdh(String ydh) {
        this.ydh = ydh;
    }

    public void setGdm(String gdm) {
        this.gdm = gdm;
    }

    public void setXwNum(String xwNum) {
        this.xwNum = xwNum;
    }


}

