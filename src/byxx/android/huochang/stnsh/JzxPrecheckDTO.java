package byxx.android.huochang.stnsh;

public class JzxPrecheckDTO {
	
  public static final int CHECKFLAG_DATA_INCOMPLETE = 0; //资料不齐
  public static final int CHECKFLAG_DATA_COMPLETE = 1; //资料齐备
  public static final int CHECKFLAG_CHECK_FAILURE = 2; //审核失败
  public static final int CHECKFLAG_CHECK_SUCCESS = 3; //审核成功
  public static final int CHECKFLAG_RECEIVE_FAILURE = 4; //收货成功
  public static final int CHECKFLAG_RECEIVE_SUCCESS = 5; //收货失败
	  
	private String jzxnum ;//集装箱号，
	private String freightNo;//运单号，
	private String checkflag;//审核状态
	public String getJzxnum() {
		return jzxnum;
	}
	public void setJzxnum(String jzxnum) {
		this.jzxnum = jzxnum;
	}
	public String getFreightNo() {
		return freightNo;
	}
	public void setFreightNo(String freightNo) {
		this.freightNo = freightNo;
	}
	public String getCheckflag() {
		return checkflag;
	}
	public void setCheckflag(String checkflag) {
		this.checkflag = checkflag;
	}


	
	
}
