package byxx.android.huochang.message;

import java.io.Serializable;
import java.sql.Timestamp;

public class MsgDto2 implements Serializable {
	private static final long serialVersionUID = -2579366721026810717L;
	
	private int msgId;
	
	private String sender;

	private Timestamp sendTime;

	private String msgContent;

	private boolean reply = false;//初始设定不需要回复
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public boolean isReply() {
		return reply;
	}

	public void setReply(boolean reply) {
		this.reply = reply;
	}

}
