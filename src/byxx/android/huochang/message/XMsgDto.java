package byxx.android.huochang.message;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;


public class XMsgDto implements Serializable, Comparable {
    private static final long serialVersionUID = -7794213947163468586L;

    private int msgId;

    private String stnCode;

    private String msgSender;

    private Timestamp sendTime;

    private String msgContent;

    private String msgReceivers;

    private String msgConfirms;

    private String[] confirmAndDates = null;

    private String[] receivers = null;

    private String[] confirms = null;

    private Vector unConfirms = null;

    private boolean needPopup = true;

	private boolean reply = false;//初始设定不需要回复
//    private int msgPackageType = ScdsMessage.TYPE_UN_KNOWN;

    private Object msgPackageObj = null;

    public int compareTo(Object object) {
	XMsgDto dto = (XMsgDto) object;
	return getSendTime().compareTo(dto.getSendTime());
    }

    public int getMsgId() {
	return msgId;
    }

    public void setMsgId(int msgId) {
	this.msgId = msgId;
    }

    public String getStnCode() {
	return stnCode;
    }

    public void setStnCode(String stnCode) {
	this.stnCode = stnCode;
    }

    public String getMsgSender() {
	return msgSender;
    }

    public void setMsgSender(String msgSender) {
	this.msgSender = msgSender;
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

    public String getMsgReceivers() {
	return msgReceivers;
    }

    public void setMsgReceivers(String msgReceivers) {
	this.msgReceivers = msgReceivers;
    }

    public String getMsgConfirms() {
	return msgConfirms;
    }

    public void setMsgConfirms(String msgConfirms) {
	this.msgConfirms = msgConfirms;
    }

    public String[] getConfirmAndDates() {
	if (confirmAndDates == null) {
	    confirmAndDates = (msgConfirms == null ? "" : msgConfirms.trim())
		    .split(",");
	}
	return confirmAndDates;
    }

    public String[] getConfirms() {
	if (confirms == null) {
	    confirms = new String[getConfirmAndDates().length];
	    for (int i = 0; i < getConfirmAndDates().length; i++) {
		String[] cds = getConfirmAndDates()[i].split("@");
		confirms[i] = cds[0];
	    }
	}
	return confirms;
    }

    public String[] getReceivers() {
	if (receivers == null) {
	    receivers = (msgReceivers == null ? "" : msgReceivers.trim())
		    .split(",");
	}
	return receivers;
    }

    public Vector getUnConfirms() {
	if (unConfirms == null) {
	    unConfirms = new Vector();
	    for (int i = 0; i < getReceivers().length; i++) {
		String re = getReceivers()[i];
		boolean isOk = false;
		for (int j = 0; j < getConfirms().length; j++) {
		    if (re.equals(getConfirms()[j])) {
			isOk = true;
			break;
		    }
		}
		if (!isOk) {
		    unConfirms.addElement(re);
		}
	    }
	}
	return unConfirms;
    }

    public boolean isNeedPopup() {
	return needPopup;
    }

    public void setNeedPopup(boolean needPopup) {
	this.needPopup = needPopup;
    }

    public Object getMsgPackageObj() {
	return msgPackageObj;
    }

    public void setMsgPackageObj(Object msgPackageObj) {
	this.msgPackageObj = msgPackageObj;
    }
    
	public boolean isReply() {
		return reply;
	}

	public void setReply(boolean reply) {
		this.reply = reply;
	}

//    public int getMsgPackageType() {
//	return msgPackageType;
//    }
//
//    public void setMsgPackageType(int msgPackageType) {
//	this.msgPackageType = msgPackageType;
//    }

}
