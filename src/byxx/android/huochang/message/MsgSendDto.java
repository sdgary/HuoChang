/**
 * 
 */
package byxx.android.huochang.message;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * 用于在msgSend界面
 * @author WGary 韦永城
 * 2015-6-18
 */
public class MsgSendDto {
	
	private HashMap<String, HashMap<Integer, XMsgDto>> msgMap = new HashMap<String, HashMap<Integer,XMsgDto>>();

	public HashMap<String, HashMap<Integer, XMsgDto>> getMsgMap() {
		return msgMap;
	}

	public void setMsgMap(HashMap<String, HashMap<Integer, XMsgDto>> msgMap) {
		this.msgMap = msgMap;
	}
	
//	private HashMap<Timestamp sendTime, MsgDto> msg
}
