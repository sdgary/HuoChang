package byxx.android.huochang.task;

import java.io.Serializable;
import java.util.List;

public class GdContainerPos implements Serializable {

	private static final long serialVersionUID = -3617089155199602965L;

	private String gdm;

	private List<ContainerMiddlePos> middlePos;

	public String getGdm() {
		return gdm;
	}

	public void setGdm(String gdm) {
		this.gdm = gdm;
	}

	public List<ContainerMiddlePos> getMiddlePos() {
		return middlePos;
	}

	public void setMiddlePos(List<ContainerMiddlePos> middlePos) {
		this.middlePos = middlePos;
	}



}
