/**
 * 
 */
package byxx.android.huochang.movebox;

/**
 * @author WGary 韦永城
 * 2015-6-6
 */
public class MoveBoxUtils {
	
	public static String getZWType(String gdm, int type){
		String result = null;
		switch (type) {
		case 20:
			result = "货主进站";
			break;
		case 21:
			result = "货主提箱";
			break;
		case 10:
			result = "火车到达";
			break;
		case 11:
			result = "火车出发";
			break;
		case 30:
			result = "移箱";
			break;
		case 40:
			result = "直卸";
			break;
		case 41:
			result = "直装";
			break;
		default:
			break;
		}
		return result;
	}
	
	/*
	 * 10,20 落
	 *	11,21 起
	 *	30 移
	 */
	public static String getFlag(int type){
		if (type==20||type==10) {
			return "落";
		}else if (type==11||type==21) {
			return "起";
		}else if (type==30) {
			return "移";
		}else if (type==40){
			return "卸";
		}else if (type==41){
			return "装";
		}
		return "";
	}
	
	
}
