package byxx.android.record;

import java.io.Serializable;

public class NoRecord implements Serializable {

	public static final int TYPE_EMPTY = -1; // 空记录

	public static final int TYPE_ERR = 0; // 出错记录

	public static final int TYPE_BEGIN = 1; // 到岗信息记录

	public static final int TYPE_OPERATE = 2; // 操作记录

	public static final int TYPE_SOAP = 3; // 前台EJB调用记录

	public static final int TYPE_SOAP_BACK = 4; // 后台EJB调用记录
	
	public static final int TYPE_SAVELOG_ERR = 5; // SAVELOG表写入出错
	
	public static final int TYPE_NET = 6; // 网络切换状态

	public NoRecord(int type, String mess) {
		super();
		this.type = type;
		this.mess = mess;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4961197540641060260L;

	private int type;
	private String mess;
	private String teams;
	private String idcard;
	private long time;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeams() {
		return teams;
	}

	public void setTeams(String teams) {
		this.teams = teams;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}
}
