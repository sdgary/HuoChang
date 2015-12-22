package byxx.android.huochang.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WjUtilTime {
	public final static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public final static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	public final static SimpleDateFormat MMddHHmm = new SimpleDateFormat("MM-dd HH:mm");
	public final static SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
	public final static SimpleDateFormat MMdd = new SimpleDateFormat("MM-dd");
	
	public static String[] departTime(String paramString)
	  {
	    String[] arrayOfString = null;
	    try
	    {
	      if (trimString(paramString).length() == 7)
	      {
	        arrayOfString = new String[2];
	        arrayOfString[0] = paramString.substring(0, 6);
	        arrayOfString[1] = paramString.substring(6);
	        return arrayOfString;
	      }
	      int i = trimString(paramString).length();
	      arrayOfString = null;
	      if (i == 6)
	      {
	        arrayOfString = new String[1];
	        arrayOfString[0] = paramString.trim();
	        return arrayOfString;
	      }
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }
	    return arrayOfString;
	  }
	
	
	/*
	 * 得到TimeStamp
	 * date:20140412 
	 * time:23:12
	 * 
	 */
	public static Timestamp getTimestamp(String date, String time) {
		Timestamp nTime = null;
		try {
			if (trimString(date).length() == 8 && trimString(time).length() == 4) {
				StringBuffer arrSb = new StringBuffer(trimString(date));
				arrSb.insert(4, "-");
				arrSb.insert(7, "-");
				arrSb.append(" ");
				arrSb.append(trimString(time.substring(0,2)));
				arrSb.append(":");
				arrSb.append(trimString(time.substring(2)));
				arrSb.append(":00.000");
				nTime = Timestamp.valueOf(arrSb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nTime;
	}
	
	/*
	 * 得到TimeStamp
	 * time:20140412 23:12
	 * 
	 */
	public static Timestamp getTimestamp(String time) {
		Timestamp nTime = null;
		try {
			if (trimString(time).length() == 14) {
				StringBuffer arrSb = new StringBuffer(trimString(time));
				arrSb.insert(4, "-");
				arrSb.insert(7, "-");
				arrSb.append(":00.000");
				nTime = Timestamp.valueOf(arrSb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nTime;
	}
	
	/*
	 * 得到两个TimeStamp时间差 
	 */
	public static String howManyTimeBetweenTwoTimeStamp(Timestamp arrTimestamp,Timestamp leaveTimestamp){
		long time;
		if (arrTimestamp.getTime()>=leaveTimestamp.getTime()) {
			time = arrTimestamp.getTime()-leaveTimestamp.getTime();
		}else {
			time = leaveTimestamp.getTime()-arrTimestamp.getTime();
		}
		return formatDuring(time);
	}
	
	/*
	 * 得到天 小时 分钟
	 * 
	 */
	public static String formatDuring(long mss) {
	   long days = mss / (1000 * 60 * 60 * 24);
	   long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
	   long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
//		   long seconds = (mss % (1000 * 60)) / 1000;
	   if (days!=0) {
		   return days + "天" + hours + "小时" + minutes + "分";
	   }else if (days==0&&hours!=0) {
		   return  hours + "小时" + minutes + "分";
	   }else if (days==0&&hours==0) {
		   return  minutes + "分";
	   }
	return null;
	}
	
	/**
	 * 明天这个时间点
	 * @param rq
	 * @return
	 */
	public static Timestamp getTomorrow(Timestamp rq){
		return new Timestamp(rq.getTime() + getTime(60 * 24));
	}
	
	/**
	 * 去空
	 * 
	 */
	public static String trimString(String string){
		return string.trim();
	}
	
	/**
	 * 将分钟数转成毫秒数
	 * 
	 * @param minute
	 * @return
	 */
	public static long getTime(int minute) {
		return Math.abs(minute) * 60 * 1000;
	}
	
	/**
	 * 将1134 将这种格式的时间转换成11:34
	 * 
	 * @param minute
	 * @return
	 */
	public static String getTimeWithColon(String time) {
		if (trimString(time).length() == 4) {
			StringBuffer arrSb = new StringBuffer(trimString(time));
			arrSb.insert(2, ":");
			return arrSb.toString();
		}
		return time;
	}
	
	/** 一天的毫秒数
	 * @return
	 */
	public static long oneDayTimeStamp() {
		return 1000*60*60*24;
	}
	
	/**
	 * 计算两个TimeStamp之间有没有跨天
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static String howManyDataBetweenTwoTimeStamp(Timestamp time1, Timestamp time2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time1.getTime());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(time2.getTime());
		int days = getDaysBetween(cal1,cal2);
		if (days==0) {
			return "当天";
		}else if (days==1) {
			return "次日";
		}else if (days==2){
			return "后天";
		}else if (days==3){
			return "大后天";
		}
		return null;
	}
	
	/**
	 * 计算两天的间隔数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDaysBetween (Calendar d1, Calendar d2){
		if (d1.after(d2)){ 
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2){
			d1 = (Calendar) d1.clone();
			do{
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
				d1.add(Calendar.YEAR, 1);
				} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
		}
}
