package byxx.android.huochang.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import android.util.Log;


public class ByString {
	static public String fill(String source, String ch, int lenth) {
		// 定长补齐处理
		String sReturn = "";
		try {
			String tStr = "";
			int oLen = 0;
			if (source != null)
				oLen = source.getBytes().length;

			int iCount = lenth - oLen;
			for (int i = 0; i < iCount; i++)
				tStr = tStr + ch;
			if (source != null) {
				tStr = tStr + source;
				sReturn = tStr.substring(oLen - lenth);
			} else {
				sReturn = tStr;
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
		return sReturn;
	}

	static public String fillRight(String source, String ch, int lenth) {
		// 定长补齐处理
		String sReturn = "";
		try {
			String tStr = "";
			int oLen = 0;
			if (source != null)
				oLen = source.getBytes().length;

			int iCount = lenth - oLen;
			for (int i = 0; i < iCount; i++)
				tStr = tStr + ch;
			if (source != null) {
				tStr = source + tStr;
				sReturn = tStr.substring(0, lenth);
			} else {
				sReturn = tStr;
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
		return sReturn;
	}



	/**
	 * 
	 * @param value
	 * @param format "yyyy年MM月dd日 HH:mm:ss SS" 默认"HH:mm"
	 * @return
	 */
	//SimpleDateFormat函数语法： G 年代标志符 y 年 M 月 d 日 
	//h 时 在上午或下午 （1~12） H 时 在一天中 （0~23） m 分 s 秒 S 毫秒 E 星期  D 一年中的第几天 F 一月中第几个星期几 w 一年中第几个星期 W 一月中第几个星期 a 上午 / 下午 标记符 k 时 在一天中 （1~24） K 时 在上午或下午 （0~11） z 时区
	public static String getTimeStr(long value, String format) {
		String tValue = null;
		if (value > 0) {
			if (format == null)
				format = "HH:mm";
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				tValue = sdf.format(new Timestamp(value));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			tValue = "";
		}
		Log.v("time", "tiem value"+ tValue +"============" + value);
		return tValue;
	}
	/**
	 * 
	 * @param value
	 * @param format "yyyy年MM月dd日 HH:mm:ss SS" 默认"HH:mm"
	 * @return
	 */
	//SimpleDateFormat函数语法： G 年代标志符 y 年 M 月 d 日 
	//h 时 在上午或下午 （1~12） H 时 在一天中 （0~23） m 分 s 秒 S 毫秒 E 星期  D 一年中的第几天 F 一月中第几个星期几 w 一年中第几个星期 W 一月中第几个星期 a 上午 / 下午 标记符 k 时 在一天中 （1~24） K 时 在上午或下午 （0~11） z 时区
	public static String getTimeStr(Timestamp value, String format) {
		String tValue = null;
		if (value != null) {
			if (format == null)
				format = "HH:mm";
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				tValue = sdf.format(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			tValue = "";
		}
		Log.v("time", "tiem value"+ tValue +"============" + value);
		return tValue;
	}

	public static int getInt(String value) {
		int tValue = -1;
		if (value == null)
			return tValue;
		if (value != null && !value.equals("")) {
			try {
				tValue = Integer.parseInt(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tValue;
	}

	public static long getLong(String value) {
		long tValue = 0;
		if (value == null)
			return tValue;
		if (value != null && !value.equals("")) {
			try {
				tValue = Long.parseLong(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tValue;
	}
	
	public static boolean getBoolean(String value) {
		boolean tValue = false;
		if (value == null)
			return tValue;
		if (value != null && !value.equals("")) {
			try {
				tValue ="true".equalsIgnoreCase(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tValue;
	}
	
	
}
