/**
 * 
 */
package byxx.android.huochang.task;

import android.widget.TextView;

/**
 * @author WGary 韦永城
 * 2015-6-11
 */
public class ListItem {
	//序号
	private TextView xh;
	//原货位
	private TextView oldHw;
	//品名
	private TextView pm;
	//类型
	private TextView workType;
	//新货位
	private TextView newHw;
	//开始时间
	private TextView start;
	//结束时间
	private TextView end;
	
	private TextView id;
	
	public TextView getStart() {
		return start;
	}
	public void setStart(TextView start) {
		this.start = start;
	}
	public TextView getEnd() {
		return end;
	}
	public void setEnd(TextView end) {
		this.end = end;
	}
	public TextView getOldHw() {
		return oldHw;
	}
	public void setOldHw(TextView oldHw) {
		this.oldHw = oldHw;
	}
	public TextView getPm() {
		return pm;
	}
	public void setPm(TextView jzxnum) {
		this.pm = jzxnum;
	}
	public TextView getNewHw() {
		return newHw;
	}
	public void setNewHw(TextView newHw) {
		this.newHw = newHw;
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
