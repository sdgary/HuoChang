package byxx.android.huochang.traininfo;

import java.io.Serializable;
import java.util.HashMap;
/**
*列车信息
*/
public class CarDto implements Serializable {
	private static final long serialVersionUID = 6695933894466298364L;

	private String zmlm;//站名列码 车站缩写 gzz gzn

	private String gdm;// 股道名 010

	private int swh;//顺位号 车厢位置

	private String ch;//车箱号 

	private String cz;//车种

	private float ziz;//

	private float hc;//换长 20尺 40尺 车厢长度

	private int zaiz;//载重

	private String dzh;//到站号 缩写

	private String dzm;//到站名

	private String pm;//品名

	private String shr;//收货人

	private String fzh;//发站号 

	private String fzm;//发站名

	private int pb;//派班？

	private String jsl;//件数量

	private String kzbz;//？

	private String cid;//车辆ID

	private String jzxh;//集装箱号
	
	private int brokenFlag;
	
	private int importantFlag;
	
	public int getBrokenFlag() {
		return brokenFlag;
	}

	public void setBrokenFlag(int brokenFlag) {
		this.brokenFlag = brokenFlag;
	}

	public int getImportantFlag() {
		return importantFlag;
	}

	public void setImportantFlag(int importantFlag) {
		this.importantFlag = importantFlag;
	}

	public String getZmlm() {
		return zmlm;
	}

	public void setZmlm(String zmlm) {
		this.zmlm = zmlm;
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

	public String getKzbz() {
		return kzbz;
	}

	public void setKzbz(String kzbz) {
		this.kzbz = kzbz;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getJzxh() {
		return jzxh;
	}

	public void setJzxh(String jzxh) {
		this.jzxh = jzxh;
	}
	

}
