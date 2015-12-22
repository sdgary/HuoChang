package byxx.android.record;

import java.util.Vector;

import byxx.android.huochang.MaStation;
import byxx.android.huochang.user.User;
import byxx.android.huochang.util.ByString;

import android.util.Log;


public class MaRecord {
	private static MaRecord instance = null;

	private Vector<NoRecord> recordData = new Vector<NoRecord>();

	RuRecord ruRecord;
	private static int ejbi = 0;
	private static int oprti = 0;

	public RuRecord getRuRecord() {
		if (ruRecord == null) {
			ruRecord = new RuRecord();
		}
		return ruRecord;
	}

	public Vector<NoRecord> getRecordData() {
		return recordData;
	}

	/**
	 */
	public static MaRecord getInstance() {
		if (instance == null) {
			instance = new MaRecord();

		}
		return instance;

	}

	/**
	 * 增加PDA动作记录
	 * 
	 * 
	 * @date 20121009
	 */
	private void addPdaAtnRcd(NoRecord noRecord) {
		if (noRecord != null) {
			try {
				if (getRecordData().size() <= 0) {
					Vector<NoRecord> recordDataRead = loadPdaAtnRcd();
					if (recordDataRead != null) {
						recordData = recordDataRead;
					}
				}
				getRecordData().addElement(noRecord);
				savePdaAtnRcd(getRecordData());
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
		}
	}

	public Vector<NoRecord> getPdaAtnRcd() {
		if (getRecordData().size() == 0) {
			Vector<NoRecord> recordDataRead = loadPdaAtnRcd();
			if (recordDataRead != null) {
				recordData = recordDataRead;
			}
		}
		return recordData;
	}

	/**
	 * 
	 * 写文件保存PDA动作记录
	 * 
	 */
	private void savePdaAtnRcd(Vector<NoRecord> datas) {
		try {
			String fileName = "stationActionRecording";
			RecordFile.writeFile(fileName, datas);
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	private Vector<NoRecord> loadPdaAtnRcd() {
		Vector<NoRecord> ha = null;
		try {
			String fileName = "stationActionRecording";
			Object obj = RecordFile.readFile(fileName);
			if (obj != null) {
				if (obj instanceof Vector<?>) {
					ha = (Vector<NoRecord>) obj;
				}
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return ha;
	}

	public void saveRecordData() {
		if (getRecordData().size() > 0) {
			savePdaAtnRcd(getRecordData());
		}
	}

	public void addEmptyNode() {
		if (getRecordData().size() == 0) {
			NoRecord noRecord = new NoRecord(NoRecord.TYPE_EMPTY, "");
			getRecordData().add(noRecord);
		}
	}

	/**
	 * 任务线程启动
	 */
	public void threadStart() {
		getRuRecord().start();
	}

	public void stopThread() {
		getRuRecord().setRunFlag(false);
	}

	public void init() {
		// try {
		// threadStart();
		// } catch (Exception e) {
		// MaStation.getInstance().recordExc(e);
		// }
	}

	public void addMessEjb(NoMess mess) {
		addMess(NoRecord.TYPE_SOAP, mess);
	}

	public void addMessEjbBack(NoMess mess) {
		if (!getRuRecord().isNetErr()) {
			addMess(NoRecord.TYPE_SOAP_BACK, mess);
		}
	}

	public void addMessOperate(NoMess mess) {
		addMess(NoRecord.TYPE_OPERATE, mess);
	}

	public void addMessErr(NoMess mess) {
//		if ((!MaStation.getInstance().netErr && !getRuRecord().isNetErr())
//				|| isErrMessRecord(mess.mess)) {
//			addMess(NoRecord.TYPE_ERR, mess);
//		}
	}

	private boolean isErrMessRecord(String mess) {
		boolean b1 = mess.indexOf("RuntimeException") == -1
				&& mess.indexOf("ConnectException") == -1
				&& mess.indexOf("SocketException") == -1;
		return b1;
	}

	public void addMessBegin(NoMess mess) {
		addMess(NoRecord.TYPE_BEGIN, mess);
	}

	public void addMessSavelogErr(NoMess mess) {
		addMess(NoRecord.TYPE_SAVELOG_ERR, mess);
	}

	public void addMessNet(NoMess mess) {
		addMess(NoRecord.TYPE_NET, mess);
	}

	private void addMess(int type, NoMess noMess) {
		User user = MaStation.getInstance().getUser();
		String ctmMsg = null;
		String time = ByString.getTimeStr(noMess.time, "MM-dd HH:mm:ss ");
		if (noMess.isrecord > 0) { // 2：默认记录；1：配置记录 ； 0：配置不记录
			if (user != null) {
				/*
				 * ctmMsg :发送时间+顺序号+内容+工号+姓名+MAC地址
				 */
//				ctmMsg = time + "@T" + oprti + "@" + noMess.mess + "@"
//						+ user.getCode() + "@" + user.getName() + "@"
//						+ GetMAC.getPDAMAC();
			} else {
//				ctmMsg = time + "@T" + oprti + "@" + noMess.mess + "@"
//						+ GetMAC.getPDAMAC();
			}
			Log.v("test1", "msg  -->" + ctmMsg);
			NoRecord noRecord = new NoRecord(type, ctmMsg);
			noRecord.setTime(noMess.time);
			if (user != null) {
				noRecord.setIdcard(user.getCode());
				noRecord.setTeams(user.getTeams());
				noRecord.setName(user.getName());
			}
			addPdaAtnRcd(noRecord);
			nextOprti();
		}
	}

	private void nextOprti() {
		oprti++;
		if (oprti > 50000) {
			oprti = 0;
		}
	}
}
