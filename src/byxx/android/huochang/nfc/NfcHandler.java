/**
 * 
 */
package byxx.android.huochang.nfc;

import android.os.Handler;

/**
 * @author WGary 韦永城
 * 2015-6-9
 */
public class NfcHandler {
	private static NfcHandler handle;
	private Handler handlerReturn = new Handler();
	private Handler handlerIndex = new Handler();

	public static NfcHandler getInstance() {
		if (handle == null) {
			handle = new NfcHandler();
		}
		return handle;
	}

	public static NfcHandler getHandle() {
		return handle;
	}

	public static void setHandle(NfcHandler handle) {
		handle = handle;
	}

	public Handler getHandlerReturn() {
		return this.handlerReturn;
	}

	public void setHandlerReturn(Handler handlerReturn) {
		this.handlerReturn = handlerReturn;
	}

	public Handler getHandlerIndex() {
		return this.handlerIndex;
	}

	public void setHandlerIndex(Handler handlerIndex) {
		this.handlerIndex = handlerIndex;
	}
}
