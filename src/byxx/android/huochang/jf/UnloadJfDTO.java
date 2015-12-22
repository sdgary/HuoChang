package byxx.android.huochang.jf;

import java.io.Serializable;
import java.sql.Timestamp;

//import byxx.stream.station.load.dto.UlHpDetailDTO;

public class UnloadJfDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String hpid;
	private String pzbz;
	private String hpph;
	private String zprq;
	private String fpbz;
	private String ydbh;
	private String jhysh;
	private String fzdbm;
	private String fzhzm;
	private String dzdbm;
	private String dzhzm;
	private int zlc;
	private int csxs;
	private String cxxx;
	private String zch;
	private String fhrmc;
	private String shrmc;
	private String pm;
	private String pmdm;
	private int js;
	private int smzl;
	private int qrzl;
	private double bjhj;
	private double hjfy;
	private String hpfile;
	private String zmlm;
	private boolean isHaved; // 判断车号是否已经在装七甲中

	private int qjFlag = 0;
	private boolean isSelect;
	private int flag;
	private String hw;
	private double hc;
	private String hsbl;
	private String hsjl;
	private String zxdm;
	private int pbs;
	private String pbh;
	private int sfs;
	private String sfh;
	private String dj;
	private String dfj;
	private String fj;
	private int czzl;
	private int jffs;
	private int proflag;
	private Timestamp jfTime;
	private int bjzz;
	private Timestamp bhTime;
	private int jfFlag;
	private Timestamp clTime;
	private int clCs;
	private String clJbr;
	private Timestamp xbkTime;
	private Timestamp ybkrq;
	private String jfJbr;
	private Timestamp bkTime;
	private String bkJbr;
	private Timestamp zexcrq;
	private String zexcmbh;
	private String zcjbr;
	private int cyfsj;
	private int cyfsjf;
	private String modiperson;
	private Timestamp moditime;
	private int modiFlag;
	private Timestamp dzTime;
	private Timestamp unloadTime;
	private String gdm;
	private String remark;

//	public void setHpDetailDto(UlHpDetailDTO hpDto) {
//		setHpid(hpDto.getHpid());
//		setPzbz(hpDto.getPzbz());
//		setHpph(hpDto.getHpph());
//		setZprq(hpDto.getZprq());
//		setFpbz(hpDto.getFpbz());
//		setYdbh(hpDto.getYdbh());
//		setJhysh(hpDto.getJhysh());
//		setFzdbm(hpDto.getFzdbm());
//		setFzhzm(hpDto.getFzhzm());
//		setDzdbm(hpDto.getDzdbm());
//		setDzhzm(hpDto.getDzhzm());
//		setZlc(hpDto.getZlc());
//		setCsxs(hpDto.getCsxs());
//		setCxxx(hpDto.getCxxx());
//		setZch(hpDto.getZch());
//		setFhrmc(hpDto.getFhrmc());
//		setShrmc(hpDto.getShrmc());
//		setPm(hpDto.getPm());
//		setPmdm(hpDto.getPmdm());
//		setJs(hpDto.getJs());
//		setSmzl(hpDto.getSmzl());
//		setQrzl(hpDto.getQrzl());
//		setBjhj(hpDto.getBjhj());
//		setHjfy(hpDto.getHjfy());
//		setHpfile(hpDto.getHpfile());
//		setZmlm(hpDto.getZmlm());
//		setQjFlag(hpDto.getQjFlag());
//		setHw(hpDto.getHw());
//		setHc(hpDto.getHc());
//		setZxdm(hpDto.getZxdm());
//		setPbs(hpDto.getPbs());
//		setPbh(hpDto.getPbh());
//		setSfs(hpDto.getSfs());
//		setSfh(hpDto.getSfh());
//		setDj(hpDto.getDj());
//		setFj(hpDto.getFj());
//		setDfj(hpDto.getDfj());
//		setCzzl(hpDto.getUnloadJfDto().getCzzl());
//		setJffs(hpDto.getUnloadJfDto().getJffs());
//		setProflag(hpDto.getUnloadJfDto().getProflag());
//		setJfTime(hpDto.getUnloadJfDto().getJfTime());
//		setBjzz(hpDto.getUnloadJfDto().getBjzz());
//		setBhTime(hpDto.getUnloadJfDto().getBhTime());
//		setJfFlag(hpDto.getUnloadJfDto().getJfFlag());
//		setClTime(hpDto.getUnloadJfDto().getClTime());
//		setClCs(hpDto.getUnloadJfDto().getClCs());
//		setClJbr(hpDto.getUnloadJfDto().getClJbr());
//		setXbkTime(hpDto.getUnloadJfDto().getXbkTime());
//		setYbkrq(hpDto.getUnloadJfDto().getYbkrq());
//		setJfJbr(hpDto.getUnloadJfDto().getJfJbr());
//		setBkTime(hpDto.getUnloadJfDto().getBkTime());
//		setBkJbr(hpDto.getUnloadJfDto().getBkJbr());
//		setZexcrq(hpDto.getUnloadJfDto().getZexcrq());
//		setZexcmbh(hpDto.getUnloadJfDto().getZexcmbh());
//		setZcjbr(hpDto.getUnloadJfDto().getZcjbr());
//		setCyfsj(hpDto.getUnloadJfDto().getCyfsj());
//		setCyfsjf(hpDto.getUnloadJfDto().getCyfsjf());
//		setModiperson(hpDto.getUnloadJfDto().getModiperson());
//		setModitime(hpDto.getUnloadJfDto().getModitime());
//		setModiFlag(hpDto.getUnloadJfDto().getModiFlag());
//		setDzTime(hpDto.getUnloadJfDto().getDzTime());
//		setUnloadTime(hpDto.getUnloadJfDto().getUnloadTime());
//		setGdm(hpDto.getUnloadJfDto().getGdm());
//	}

	public String getHpid() {
		return hpid;
	}

	public void setHpid(String hpid) {
		this.hpid = hpid;
	}

	public String getZch() {
		return zch;
	}

	public void setZch(String zch) {
		this.zch = zch;
	}

	public int getCzzl() {
		return czzl;
	}

	public void setCzzl(int czzl) {
		this.czzl = czzl;
	}

	public int getJffs() {
		return jffs;
	}

	public void setJffs(int jffs) {
		this.jffs = jffs;
	}

	public int getProflag() {
		return proflag;
	}

	public void setProflag(int proflag) {
		this.proflag = proflag;
	}

	public Timestamp getJfTime() {
		return jfTime;
	}

	public void setJfTime(Timestamp jfTime) {
		this.jfTime = jfTime;
	}

	public int getBjzz() {
		return bjzz;
	}

	public void setBjzz(int bjzz) {
		this.bjzz = bjzz;
	}

	public Timestamp getBhTime() {
		return bhTime;
	}

	public void setBhTime(Timestamp bhTime) {
		this.bhTime = bhTime;
	}

	public int getJfFlag() {
		return jfFlag;
	}

	public void setJfFlag(int jfFlag) {
		this.jfFlag = jfFlag;
	}

	public Timestamp getClTime() {
		return clTime;
	}

	public void setClTime(Timestamp clTime) {
		this.clTime = clTime;
	}

	public int getClCs() {
		return clCs;
	}

	public void setClCs(int clCs) {
		this.clCs = clCs;
	}

	public String getClJbr() {
		return clJbr;
	}

	public void setClJbr(String clJbr) {
		this.clJbr = clJbr;
	}

	public Timestamp getXbkTime() {
		return xbkTime;
	}

	public void setXbkTime(Timestamp xbkTime) {
		this.xbkTime = xbkTime;
	}

	public Timestamp getYbkrq() {
		return ybkrq;
	}

	public void setYbkrq(Timestamp ybkrq) {
		this.ybkrq = ybkrq;
	}

	public String getJfJbr() {
		return jfJbr;
	}

	public void setJfJbr(String jfJbr) {
		this.jfJbr = jfJbr;
	}

	public Timestamp getBkTime() {
		return bkTime;
	}

	public void setBkTime(Timestamp bkTime) {
		this.bkTime = bkTime;
	}

	public String getBkJbr() {
		return bkJbr;
	}

	public void setBkJbr(String bkJbr) {
		this.bkJbr = bkJbr;
	}

	public Timestamp getZexcrq() {
		return zexcrq;
	}

	public void setZexcrq(Timestamp zexcrq) {
		this.zexcrq = zexcrq;
	}

	public String getZexcmbh() {
		return zexcmbh;
	}

	public void setZexcmbh(String zexcmbh) {
		this.zexcmbh = zexcmbh;
	}

	public String getZcjbr() {
		return zcjbr;
	}

	public void setZcjbr(String zcjbr) {
		this.zcjbr = zcjbr;
	}

	public int getCyfsj() {
		return cyfsj;
	}

	public void setCyfsj(int cyfsj) {
		this.cyfsj = cyfsj;
	}

	public int getCyfsjf() {
		return cyfsjf;
	}

	public void setCyfsjf(int cyfsjf) {
		this.cyfsjf = cyfsjf;
	}

	public String getModiperson() {
		return modiperson;
	}

	public void setModiperson(String modiperson) {
		this.modiperson = modiperson;
	}

	public Timestamp getModitime() {
		return moditime;
	}

	public void setModitime(Timestamp moditime) {
		this.moditime = moditime;
	}

	public int getModiFlag() {
		return modiFlag;
	}

	public String getHpfile() {
		return hpfile;
	}

	public Timestamp getDzTime() {
		return dzTime;
	}

	public Timestamp getUnloadTime() {
		return unloadTime;
	}

	public void setModiFlag(int modiFlag) {
		this.modiFlag = modiFlag;
	}

	public void setHpfile(String hpfile) {
		this.hpfile = hpfile;
	}

	public void setDzTime(Timestamp dzTime) {
		this.dzTime = dzTime;
	}

	public void setUnloadTime(Timestamp unloadTime) {
		this.unloadTime = unloadTime;
	}

	public String getPzbz() {
		return pzbz;
	}

	public void setPzbz(String pzbz) {
		this.pzbz = pzbz;
	}

	public String getHpph() {
		return hpph;
	}

	public void setHpph(String hpph) {
		this.hpph = hpph;
	}

	public String getZprq() {
		return zprq;
	}

	public void setZprq(String zprq) {
		this.zprq = zprq;
	}

	public String getFpbz() {
		return fpbz;
	}

	public void setFpbz(String fpbz) {
		this.fpbz = fpbz;
	}

	public String getYdbh() {
		return ydbh;
	}

	public void setYdbh(String ydbh) {
		this.ydbh = ydbh;
	}

	public String getJhysh() {
		return jhysh;
	}

	public void setJhysh(String jhysh) {
		this.jhysh = jhysh;
	}

	public String getFzdbm() {
		return fzdbm;
	}

	public void setFzdbm(String fzdbm) {
		this.fzdbm = fzdbm;
	}

	public String getFzhzm() {
		return fzhzm;
	}

	public void setFzhzm(String fzhzm) {
		this.fzhzm = fzhzm;
	}

	public String getDzdbm() {
		return dzdbm;
	}

	public void setDzdbm(String dzdbm) {
		this.dzdbm = dzdbm;
	}

	public String getDzhzm() {
		return dzhzm;
	}

	public void setDzhzm(String dzhzm) {
		this.dzhzm = dzhzm;
	}

	public int getZlc() {
		return zlc;
	}

	public void setZlc(int zlc) {
		this.zlc = zlc;
	}

	public int getCsxs() {
		return csxs;
	}

	public void setCsxs(int csxs) {
		this.csxs = csxs;
	}

	public String getCxxx() {
		return cxxx;
	}

	public void setCxxx(String cxxx) {
		this.cxxx = cxxx;
	}

	public String getFhrmc() {
		return fhrmc;
	}

	public void setFhrmc(String fhrmc) {
		this.fhrmc = fhrmc;
	}

	public String getShrmc() {
		return shrmc;
	}

	public void setShrmc(String shrmc) {
		this.shrmc = shrmc;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getPmdm() {
		return pmdm;
	}

	public void setPmdm(String pmdm) {
		this.pmdm = pmdm;
	}

	public int getJs() {
		return js;
	}

	public void setJs(int js) {
		this.js = js;
	}

	public int getSmzl() {
		return smzl;
	}

	public void setSmzl(int smzl) {
		this.smzl = smzl;
	}

	public int getQrzl() {
		return qrzl;
	}

	public void setQrzl(int qrzl) {
		this.qrzl = qrzl;
	}

	public double getBjhj() {
		return bjhj;
	}

	public void setBjhj(double bjhj) {
		this.bjhj = bjhj;
	}

	public double getHjfy() {
		return hjfy;
	}

	public void setHjfy(double hjfy) {
		this.hjfy = hjfy;
	}

	public String getZmlm() {
		return zmlm;
	}

	public void setZmlm(String zmlm) {
		this.zmlm = zmlm;
	}

	public boolean isHaved() {
		return isHaved;
	}

	public void setHaved(boolean isHaved) {
		this.isHaved = isHaved;
	}

	public int getQjFlag() {
		return qjFlag;
	}

	public void setQjFlag(int qjFlag) {
		this.qjFlag = qjFlag;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getHw() {
		return hw;
	}

	public void setHw(String hw) {
		this.hw = hw;
	}

	public double getHc() {
		return hc;
	}

	public void setHc(double hc) {
		this.hc = hc;
	}

	public String getHsbl() {
		return hsbl;
	}

	public void setHsbl(String hsbl) {
		this.hsbl = hsbl;
	}

	public String getHsjl() {
		return hsjl;
	}

	public void setHsjl(String hsjl) {
		this.hsjl = hsjl;
	}

	public String getZxdm() {
		return zxdm;
	}

	public void setZxdm(String zxdm) {
		this.zxdm = zxdm;
	}

	public int getPbs() {
		return pbs;
	}

	public void setPbs(int pbs) {
		this.pbs = pbs;
	}

	public String getPbh() {
		return pbh;
	}

	public void setPbh(String pbh) {
		this.pbh = pbh;
	}

	public int getSfs() {
		return sfs;
	}

	public void setSfs(int sfs) {
		this.sfs = sfs;
	}

	public String getSfh() {
		return sfh;
	}

	public void setSfh(String sfh) {
		this.sfh = sfh;
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

	public String getFj() {
		return fj;
	}

	public void setFj(String fj) {
		this.fj = fj;
	}

	public String getGdm() {
		return gdm;
	}

	public void setGdm(String gdm) {
		this.gdm = gdm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
