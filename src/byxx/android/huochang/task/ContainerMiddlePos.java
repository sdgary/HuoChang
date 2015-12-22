package byxx.android.huochang.task;

import java.io.Serializable;
import java.util.List;

public class ContainerMiddlePos implements Serializable {

	private static final long serialVersionUID = -3468620155678591333L;
	private String middlePosName;

	private List<String> pos;

	public String getMiddlePosName() {
		return middlePosName;
	}

	public void setMiddlePosName(String middlePosName) {
		this.middlePosName = middlePosName;
	}

	public List<String> getPos() {
		return pos;
	}

	public void setPos(List<String> pos) {
		this.pos = pos;
	}

//	public String[] getPos() {
//		return pos;
//	}
//
//	public void setPos(String[] pos) {
//		this.pos = pos;
//	}
	
	
}
