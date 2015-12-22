package byxx.android.huochang.dw;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;

public class XClkDto implements Serializable, Comparable, Cloneable {
    private static final long serialVersionUID = 6695933894466298364L;

    private String zmlm;

    private int cjbc;

    private String gdm;

    private int swh;

    private String ch;

    private String cz;

    private String czdm;

    private String czjm;

    private String czlb;

    private String yz;

    private int bjzz;

    private float ziz;

    private float hc;

    private String clsz;

    private String bqbz;

    private int zaiz;

    private String dzh;

    private String dzm;

    private String dj;

    private String dfj;

    private String zdzh;

    private String zdzm;

    private String zdj;

    private String zdfj;

    private String fxh;

    private String fxh2;

    private String jyzh;

    private String pm;

    private String shr;

    private String fzh;

    private String fzm;

    private int pb;

    private String jsl;

    private String czbj;

    private String kzbz;

    private String zlbz;

    private String jybz;

    private String hsbz;

    private String fyym;

    private String fybz;

    private String sysx;

    private String zubz;

    private String cltz;

    private String clzt;

    private String hpid;

    private String hph;

    private String sfh;

    private String gldh;

    private String pbh1;

    private String pbh2;

    private String pbh3;

    private String pbh4;

    private String pbsz;

    private String pmdm;

    private String pldm;

    private String wpdm;

    private String shrjc;

    private int dbid;

    private String ddcc;

    private Timestamp ddrq;

    private int fbid;

    private String cfcc;

    private Timestamp cfrq;

    private String xslg;

    private String zybz; // 作业标志，到站为本站的时候=1，不为本站的时候=0

    private String ct; // 到站为本站的时候，重车=9，空车=0；到站不为本站的时候，重车=X，空车=0;

    private int zycs;

    private String xgd;

    private String zxdm;

    private String zzlx;

    private String cid;

    private String jzxh;

    public int compareTo(Object object) {
	XClkDto dto = (XClkDto) object;
	return swh - dto.getSwh();
    }

    public Object clone() {
	XClkDto o = null;
	try {
	    o = (XClkDto) super.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	}
	return o;
    }

    public String getZmlm() {
	return zmlm;
    }

    public void setZmlm(String zmlm) {
	this.zmlm = zmlm;
    }

    public int getCjbc() {
	return cjbc;
    }

    public void setCjbc(int cjbc) {
	this.cjbc = cjbc;
    }

    public String getGdm() {
	return gdm;
    }

    public void setGdm(String gdm) {
	this.gdm = gdm;
    }

    public int getSwh() {
	return swh;
    }

    public void setSwh(int swh) {
	this.swh = swh;
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

    public String getCzdm() {
	return czdm;
    }

    public void setCzdm(String czdm) {
	this.czdm = czdm;
    }

    public String getCzjm() {
	return czjm;
    }

    public void setCzjm(String czjm) {
	this.czjm = czjm;
    }

    public String getCzlb() {
	return czlb;
    }

    public void setCzlb(String czlb) {
	this.czlb = czlb;
    }

    public String getYz() {
	return yz;
    }

    public void setYz(String yz) {
	this.yz = yz;
    }

    public int getBjzz() {
	return bjzz;
    }

    public void setBjzz(int bjzz) {
	this.bjzz = bjzz;
    }

    public float getZiz() {
	return ziz;
    }

    public void setZiz(float ziz) {
	this.ziz = ziz;
    }

    public float getHc() {
	return hc;
    }

    public void setHc(float hc) {
	this.hc = hc;
    }

    public String getClsz() {
	return clsz;
    }

    public void setClsz(String clsz) {
	this.clsz = clsz;
    }

    public String getBqbz() {
	return bqbz;
    }

    public void setBqbz(String bqbz) {
	this.bqbz = bqbz;
    }

    public int getZaiz() {
	return zaiz;
    }

    public void setZaiz(int zaiz) {
	this.zaiz = zaiz;
    }

    public String getDzh() {
	return dzh;
    }

    public void setDzh(String dzh) {
	this.dzh = dzh;
    }

    public String getDzm() {
	return dzm;
    }

    public void setDzm(String dzm) {
	this.dzm = dzm;
    }

    public String getDj() {
	return dj;
    }

    public void setDj(String dj) {
	this.dj = dj;
    }

    public String getDfj() {
	return dfj;
    }

    public void setDfj(String dfj) {
	this.dfj = dfj;
    }

    public String getZdzh() {
	return zdzh;
    }

    public void setZdzh(String zdzh) {
	this.zdzh = zdzh;
    }

    public String getZdzm() {
	return zdzm;
    }

    public void setZdzm(String zdzm) {
	this.zdzm = zdzm;
    }

    public String getZdj() {
	return zdj;
    }

    public void setZdj(String zdj) {
	this.zdj = zdj;
    }

    public String getZdfj() {
	return zdfj;
    }

    public void setZdfj(String zdfj) {
	this.zdfj = zdfj;
    }

    public String getFxh() {
	return fxh;
    }

    public void setFxh(String fxh) {
	this.fxh = fxh;
    }

    public String getFxh2() {
	return fxh2;
    }

    public void setFxh2(String fxh2) {
	this.fxh2 = fxh2;
    }

    public String getJyzh() {
	return jyzh;
    }

    public void setJyzh(String jyzh) {
	this.jyzh = jyzh;
    }

    public String getPm() {
	return pm;
    }

    public void setPm(String pm) {
	this.pm = pm;
    }

    public String getShr() {
	return shr;
    }

    public void setShr(String shr) {
	this.shr = shr;
    }

    public String getFzh() {
	return fzh;
    }

    public void setFzh(String fzh) {
	this.fzh = fzh;
    }

    public String getFzm() {
	return fzm;
    }

    public void setFzm(String fzm) {
	this.fzm = fzm;
    }

    public int getPb() {
	return pb;
    }

    public void setPb(int pb) {
	this.pb = pb;
    }

    public String getJsl() {
	return jsl;
    }

    public void setJsl(String jsl) {
	this.jsl = jsl;
    }

    public String getCzbj() {
	return czbj;
    }

    public void setCzbj(String czbj) {
	this.czbj = czbj;
    }

    public String getKzbz() {
	return kzbz;
    }

    public void setKzbz(String kzbz) {
	this.kzbz = kzbz;
    }

    public String getZlbz() {
	return zlbz;
    }

    public void setZlbz(String zlbz) {
	this.zlbz = zlbz;
    }

    public String getJybz() {
	return jybz;
    }

    public void setJybz(String jybz) {
	this.jybz = jybz;
    }

    public String getHsbz() {
	return hsbz;
    }

    public void setHsbz(String hsbz) {
	this.hsbz = hsbz;
    }

    public String getFyym() {
	return fyym;
    }

    public void setFyym(String fyym) {
	this.fyym = fyym;
    }

    public String getFybz() {
	return fybz;
    }

    public void setFybz(String fybz) {
	this.fybz = fybz;
    }

    public String getSysx() {
	return sysx;
    }

    public void setSysx(String sysx) {
	this.sysx = sysx;
    }

    public String getZubz() {
	return zubz;
    }

    public void setZubz(String zubz) {
	this.zubz = zubz;
    }

    public String getCltz() {
	return cltz;
    }

    public void setCltz(String cltz) {
	this.cltz = cltz;
    }

    public String getClzt() {
	return clzt;
    }

    public void setClzt(String clzt) {
	this.clzt = clzt;
    }

    public String getHpid() {
	return hpid;
    }

    public void setHpid(String hpid) {
	this.hpid = hpid;
    }

    public String getHph() {
	return hph;
    }

    public void setHph(String hph) {
	this.hph = hph;
    }

    public String getSfh() {
	return sfh;
    }

    public void setSfh(String sfh) {
	this.sfh = sfh;
    }

    public String getGldh() {
	return gldh;
    }

    public void setGldh(String gldh) {
	this.gldh = gldh;
    }

    public String getPbh1() {
	return pbh1;
    }

    public void setPbh1(String pbh1) {
	this.pbh1 = pbh1;
    }

    public String getPbh2() {
	return pbh2;
    }

    public void setPbh2(String pbh2) {
	this.pbh2 = pbh2;
    }

    public String getPbh3() {
	return pbh3;
    }

    public void setPbh3(String pbh3) {
	this.pbh3 = pbh3;
    }

    public String getPbh4() {
	return pbh4;
    }

    public void setPbh4(String pbh4) {
	this.pbh4 = pbh4;
    }

    public String getPbsz() {
	return pbsz;
    }

    public void setPbsz(String pbsz) {
	this.pbsz = pbsz;
    }

    public String getPmdm() {
	return pmdm;
    }

    public void setPmdm(String pmdm) {
	this.pmdm = pmdm;
    }

    public String getPldm() {
	return pldm;
    }

    public void setPldm(String pldm) {
	this.pldm = pldm;
    }

    public String getWpdm() {
	return wpdm;
    }

    public void setWpdm(String wpdm) {
	this.wpdm = wpdm;
    }

    public String getShrjc() {
	return shrjc;
    }

    public void setShrjc(String shrjc) {
	this.shrjc = shrjc;
    }

    public int getDbid() {
	return dbid;
    }

    public void setDbid(int dbid) {
	this.dbid = dbid;
    }

    public String getDdcc() {
	return ddcc;
    }

    public void setDdcc(String ddcc) {
	this.ddcc = ddcc;
    }

    public Timestamp getDdrq() {
	return ddrq;
    }

    public void setDdrq(Timestamp ddrq) {
	this.ddrq = ddrq;
    }

    public int getFbid() {
	return fbid;
    }

    public void setFbid(int fbid) {
	this.fbid = fbid;
    }

    public String getCfcc() {
	return cfcc;
    }

    public void setCfcc(String cfcc) {
	this.cfcc = cfcc;
    }

    public Timestamp getCfrq() {
	return cfrq;
    }

    public void setCfrq(Timestamp cfrq) {
	this.cfrq = cfrq;
    }

    public String getXslg() {
	return xslg;
    }

    public void setXslg(String xslg) {
	this.xslg = xslg;
    }

    public String getZybz() {
	return zybz;
    }

    public void setZybz(String zybz) {
	this.zybz = zybz;
    }

    public String getCt() {
	return ct;
    }

    public void setCt(String ct) {
	this.ct = ct;
    }

    public int getZycs() {
	return zycs;
    }

    public void setZycs(int zycs) {
	this.zycs = zycs;
    }

    public String getXgd() {
	return xgd;
    }

    public void setXgd(String xgd) {
	this.xgd = xgd;
    }

    public String getZxdm() {
	return zxdm;
    }

    public void setZxdm(String zxdm) {
	this.zxdm = zxdm;
    }

    public String getZzlx() {
	return zzlx;
    }

    public void setZzlx(String zzlx) {
	this.zzlx = zzlx;
    }

    public String toString() {
	return ch;
    }

    public void setCid(String cid) {
	this.cid = cid;
    }

    public String getCid() {
	return cid;
    }

    public String getJzxh() {
	return jzxh;
    }

    public void setJzxh(String jzxh) {
	this.jzxh = jzxh;
    }
	HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
}
