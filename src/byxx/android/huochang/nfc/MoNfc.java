package byxx.android.huochang.nfc;

import android.os.Handler;

public class MoNfc {

	private Handler handler = null; //返回页
	
	private Handler handlerFunc=null;//首页

	private boolean idOrData = true;// true：代表检测卡ID，false：代表检测卡内容
	
	public Handler getHandlerFunc() {
		return handlerFunc;
	}

	public void setHandlerFunc(Handler handlerFunc, boolean flag) {
		idOrData = flag;//每次除移箱计划外的功能线程都只读卡ID
		this.handlerFunc = handlerFunc;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		idOrData = true;//每次除移箱计划外的功能线程都只读卡ID
		this.handler = handler;
	}

	public boolean isIdOrData() {
		return idOrData;
	}

	public void setIdOrData(boolean idOrData) {
		this.idOrData = idOrData;
	}
	
}
