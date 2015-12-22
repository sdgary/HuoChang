package byxx.android.huochang;

import android.util.Log;
/**
 * 单例模式
 * @author Way
 *
 */
public  class StaticClass {
	
	public static boolean isload = false; // 登录标志
    public static int CallNum = 0;
	public static boolean isIsload() {
		Log.v("load", "is "+isload);
		return isload;
	}

	public static void setIsload(boolean isload) {
		StaticClass.isload = isload;
		Log.v("load", "set "+StaticClass.isload);
	}

	public static int getCallNum() {
		return CallNum;
	}

	public static void setCallNum(int callNum) {
		CallNum = callNum;
	}
	
    

}
