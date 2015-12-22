package byxx.android.huochang.movebox;


import android.widget.TextView;

public class ListItemMoveBoxDetail {
	//序号
	private TextView xh;
	//原货位
	private TextView oldHw;
	//集装箱号
	private TextView jzxnum;
	//类型
	private TextView workType;
	//新货位
	private TextView newHw;
	//执行状态
	private TextView status;
	private TextView id;
	public TextView getOldHw() {
		return oldHw;
	}
	public void setOldHw(TextView oldHw) {
		this.oldHw = oldHw;
	}
	public TextView getJzxnum() {
		return jzxnum;
	}
	public void setJzxnum(TextView jzxnum) {
		this.jzxnum = jzxnum;
	}
	public TextView getNewHw() {
		return newHw;
	}
	public void setNewHw(TextView newHw) {
		this.newHw = newHw;
	}
	public TextView getStatus() {
		return status;
	}
	public void setStatus(TextView status) {
		this.status = status;
	}
	public TextView getXh() {
		return xh;
	}
	public void setXh(TextView xh) {
		this.xh = xh;
	}
	public TextView getWorkType() {
		return workType;
	}
	public void setWorkType(TextView workType) {
		this.workType = workType;
	}
	public TextView getId() {
		return id;
	}
	public void setId(TextView id) {
		this.id = id;
	}
}	
