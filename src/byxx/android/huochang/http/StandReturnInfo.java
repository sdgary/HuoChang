/**
 * 
 */
package byxx.android.huochang.http;

/**
 * @author WGary 韦永城
 * 2015-6-6
 */
public class StandReturnInfo {
	private boolean success = false;
	private String error = "";
	private Object data = null;
	
	public StandReturnInfo (boolean success,String error,Object data) {
		this.success = success;
		this.error = error;
		this.data = data;
	}
	
	public StandReturnInfo () {
		super();
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object datas) {
		this.data = datas;
	}
	
}
