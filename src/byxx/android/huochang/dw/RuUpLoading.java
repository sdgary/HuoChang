package byxx.android.huochang.dw;

import java.util.Vector;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class RuUpLoading implements Runnable{
	private String type = null;
	private String filename = null;
	private String Describe = null;
	private String Title = null;
	
	String locusRegion =null;
	String locusRegionDes =null;
	String loseDescribe =null;
	String findMan =null;
	String trainNum =null;
	String trainCarSeat =null;
	String conductor =null;
	long locusTime = 0;
	long giveupTime = 0;
	String remark =null;
	String stationCode =null;
	String pictures =null;
	
	
	private Thread thread = null;
	private boolean runFlag = true;
	String fileId = null;
	Handler handler =null;
	static int switchtype = 0 ;
	
	public RuUpLoading(){
		
	}
	
	public RuUpLoading(Handler _handler){
		this.handler =_handler;
	}
	
//	public RuUpLoading(Handler _handler,int _switchtype ){
//		this.handler =_handler;
//		this.switchtype = _switchtype;
//	}
//	
	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}
	
	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDescribe() {
		return Describe;
	}

	public void setDescribe(String describe) {
		Describe = describe;
	}

	
	
	
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getLocusRegion() {
		return locusRegion;
	}

	public void setLocusRegion(String locusRegion) {
		this.locusRegion = locusRegion;
	}

	public String getLocusRegionDes() {
		return locusRegionDes;
	}

	public void setLocusRegionDes(String locusRegionDes) {
		this.locusRegionDes = locusRegionDes;
	}

	public String getLoseDescribe() {
		return loseDescribe;
	}

	public void setLoseDescribe(String loseDescribe) {
		this.loseDescribe = loseDescribe;
	}

	public String getFindMan() {
		return findMan;
	}

	public void setFindMan(String findMan) {
		this.findMan = findMan;
	}

	public String getTrainNum() {
		return trainNum;
	}

	public void setTrainNum(String trainNum) {
		this.trainNum = trainNum;
	}

	public String getTrainCarSeat() {
		return trainCarSeat;
	}

	public void setTrainCarSeat(String trainCarSeat) {
		this.trainCarSeat = trainCarSeat;
	}

	public String getConductor() {
		return conductor;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	public long getLocusTime() {
		return locusTime;
	}

	public void setLocusTime(long locusTime) {
		this.locusTime = locusTime;
	}

	public long getGiveupTime() {
		return giveupTime;
	}

	public void setGiveupTime(long giveupTime) {
		this.giveupTime = giveupTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public void close() {
		setRunFlag(false);
		try {
			if (thread != null && thread.isAlive()) {
				thread.interrupt();
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		thread = null;
	}
	
	public void start(String mfileName, String title ,String type, String etfileDescribe) {
		setRunFlag(true);
		setFilename(mfileName);
		setType(type);
		setTitle(title);
		setDescribe(etfileDescribe);
		if (thread == null || thread.isInterrupted()) {
			thread = new Thread(this, this.getClass().getName());
			thread.start();
		}
	}
	
	public void startl(String locusRegion,
			String locusRegionDes,
			String loseDescribe,
			String findMan,
			String trainNum,
			String trainCarSeat,
			String conductor,
			String remark,
			long giveupTime,
			long locusTime ,
			String Pictures,
			String type) {
		setRunFlag(true);
		setPictures(Pictures);
		setType(type);
        setLocusRegion(locusRegion);
        setLocusRegionDes(locusRegionDes);
        setLoseDescribe(loseDescribe);
        setFindMan(findMan);
        setTrainNum(trainNum);
        setTrainCarSeat(trainCarSeat);
        setConductor(conductor);
        setLocusTime(locusTime);
        setGiveupTime(giveupTime);
        setRemark(remark);
        
		if (thread == null || thread.isInterrupted()) {
			thread = new Thread(this, this.getClass().getName());
			thread.start();
		}
	}
	
	
	
	public void run() {
		// TODO Auto-generated method stub
			try {

				deal();
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}

		return;
	}

	
	
	private void deal() {
		try {
			Log.v("ftp", "switchtype "+ switchtype);
//			switch (switchtype) {
//			case 0:  //其他上报
//		    fileId = MaStation.getInstance().getMoFtp().saveFTPinfo(getFilename(), getTitle(),getType(), getDescribe());
//			if (handler != null) {
//		 		Message tMessage = Message.obtain();
//		 				tMessage.what = Constant.MSG_ID_SAVEFTPFILE;
//		 				Bundle tBundle = new Bundle();
//		 				tBundle.putString("ftpresult",fileId);
//		 				tMessage.setData(tBundle);
//		 				handler.sendMessage(tMessage);
//		 		}
//			break;
//			case 1: //失物上报
//			String s = MaStation.getInstance().getMoFtp().sendLoseGoods(
//					getLocusRegion(),
//					getLocusRegionDes(),
//					getLoseDescribe(),
//					getFindMan(),
//					getTrainNum(),
//					getTrainCarSeat(),
//					getConductor(),
//					getLocusTime(),
//					getGiveupTime(),
//					getRemark(),
//					getPictures());
//			Log.v("ftp", "case 1: //失物上报" +s+"pic "+ getPictures());
//				if (handler != null) {
//					Message tMessage = Message.obtain();
//			 				tMessage.what = Constant.MSG_ID_LOSEGOODS;
//			 				Bundle tBundle = new Bundle();
//			 				tBundle.putString("sendLoseGoods",s);
//			 				tMessage.setData(tBundle);
//			 				handler.sendMessage(tMessage);
//			 		}
//			break;
//			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		
		
}
}

