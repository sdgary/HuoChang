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
public class JzxHwzwModifyDTO implements Serializable, Cloneable {
	/**
     *
     */
	private static final long serialVersionUID = 1338378265962885835L;
	public static final int STATUS_PLAN = 0;
	public static final int STATUS_FINISH = 1;
	public static String[] STATUS_STR = { "未执行", "已执行" };
	public static final String JZX_HW_OUT = "出场";
	public static final String JZX_HW_IN = "入场";
	public static final String JZXSTR_OUT = "OUT";
	public static final String JZXSTR_IN = "IN";
	public static final int CAR_IN = 20; // 集装箱汽车到达
	public static final int CAR_OUT = 21; // 集装箱汽车出发
	public static final int TRAIN_IN = 10; // 集装箱火车到达
	public static final int TRAIN_OUT = 11; // 集装箱火车发送
	public static final int MOVE_IN_STATION = 30;// 站内调整
	public static final int TRAIN_TO_CAR = 40;//直卸
	public static final int CAR_TO_TRAIN = 41;//直装
	public static final int IS_REFUSE_Y = 1;//拒收
	public static final int IS_REFUSE_N = 2;//允许收货
	public static final int IS_ERROR_Y = -1;
	public static final int IS_ERROR_N = 0;
	public static final int IS_ERROR_NULL = 1;
	
	private String pk;
	private int bc; // 班次
	private int swh; // 顺位号
	private String jzxnum; // 集装箱号码
	private String jzxcode; // 箱主代码
	private String oldHw; // 原箱位
	private String newHw; // 新箱位
	private int status; // 执行状态
	private Timestamp workTime; // 执行时间
	private JzxXwDTO dto = null; // 集装箱dto
	private String jobId; // 预约流水号
	private int workType; // 作业类型
	private String gdm;// 股道码
	private String zmlm;// 站名略码
	private String originalNewHw;//原新箱位
	private String errorInfoStr;//错误信息提示
	private int isRefuse;//拒收标志
	private int isError = IS_ERROR_N;

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	public int getSwh() {
		return swh;
	}

	public void setSwh(int swh) {
		this.swh = swh;
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

	public JzxXwDTO getJzxXwDTO() {
		return dto;
	}

	public void setJzxXwDTO(JzxXwDTO dto) {
		this.dto = dto;
	}

	public int getStatus() {
		return status;
	}

	public Timestamp getWorkTime() {
		return workTime;
	}

	public String getJobId() {
		return jobId;
	}

	public int getWorkType() {
		return workType;
	}

	public String getGdm() {
		return gdm;
	}

	public String getZmlm() {
		return zmlm;
	}

	public int getBc() {
		return bc;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setWorkTime(Timestamp workTime) {
		this.workTime = workTime;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public void setWorkType(int workType) {
		this.workType = workType;
	}

	public void setGdm(String gdm) {
		this.gdm = gdm;
	}

	public void setZmlm(String zmlm) {
		this.zmlm = zmlm;
	}

	public void setBc(int bc) {
		this.bc = bc;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}
	public void setIsError(int isError){
		this.isError = isError;
	}
	
	public int getIsError(){
		return isError;
	}

	public String getOriginalNewHw() {
		return originalNewHw;
	}

	public void setOriginalNewHw(String originalNewHw) {
		this.originalNewHw = originalNewHw;
	}

	public String getErrorInfoStr() {
		return errorInfoStr;
	}

	public void setErrorInfoStr(String errorInfoStr) {
		this.errorInfoStr = errorInfoStr;
	}

	public int getIsRefuse() {
		return isRefuse;
	}

	public void setIsRefuse(int isRefuse) {
		this.isRefuse = isRefuse;
	}
}

