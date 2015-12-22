package byxx.android.record;

import java.util.Vector;


/**
 * 定时检查是否有通知
 * 
 * 
 */
public class RuRecord implements Runnable {

	private boolean runFlag = true;

	private boolean netErr = false;

	private boolean userNull = false;

	public boolean isNetErr() {
		return netErr;
	}

	public void setNetErr(boolean netErr) {
		this.netErr = netErr;
	}

	public RuRecord() {
	}

	private Thread thread = null;

	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}

	public void start() {
		setRunFlag(true);
		if (thread == null || thread.isInterrupted()) {
			thread = new Thread(this, this.getClass().getName());
			thread.start();
//			MyExecutorService.getInstance().addThread(thread);
		}
	}

	public void close() {
		setRunFlag(false);
		try {
			if (thread != null && thread.isAlive()) {
				thread.interrupt();
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
		thread = null;
	}

	public void run() {
		while (isRunFlag()) {
			try {
				for (int i = 0; i < 30; i++) {
					Thread.sleep(1000);
					if (!isRunFlag()) {
						break;
					}
				}
				deal();
			} catch (Exception e) {
//				MaStation.getInstance().recordExc(e);
			}
		}
		return;
	}

	private void deal() {
//		try {
//			if (!MaStation.getInstance().netErr) {
//				Vector<NoRecord> veTemp = new Vector<NoRecord>();
//				veTemp.addAll(getMaRecord().getPdaAtnRcd());
//				getMaRecord().addEmptyNode();
//				NoRecord noRecord = null;
//				int saveFlag = 0;
//				long time = MaStation.getInstance().getCurrentTimeMillis();
//				long time11 = time;
//				boolean isNetErr = false;
//				for (int i = 0; i < veTemp.size(); i++) {
//					saveFlag = 0;
//					noRecord = veTemp.get(i);
//					if (noRecord.getType() != NoRecord.TYPE_EMPTY) {
//						if (!MaStation.getInstance().netErr) {
//							long time22 = MaStation.getInstance()
//									.getCurrentTimeMillis();
//							saveFlag = saveMess(noRecord, noRecord.getType(),
//									noRecord.getMess() + "##"
//											+ (time22 - time11), time);
//							if (saveFlag == -1) {
//								if (!netErr) {
//									// 网络异常,记录出错
//									String mess = "##saveLog异常:"
//											+ noRecord.getMess() + "##"
//											+ (time22 - time11);
//									NoMess noMess = new NoMess();
//									noMess.time = MaStation.getInstance()
//											.getCurrentTimeMillis();
//									noMess.mess = mess;
//									MaStation.getInstance().getMaRecord()
//											.addMessNet(noMess);
//								}
//								isNetErr = true;
//								break;
//							} else {
//								getMaRecord().getRecordData().remove(noRecord);
//								if (netErr) {
//									String mess = "##saveLog恢复";
//									NoMess noMess = new NoMess();
//									noMess.time = MaStation.getInstance()
//											.getCurrentTimeMillis();
//									noMess.mess = mess;
//									MaStation.getInstance().getMaRecord()
//											.addMessNet(noMess);
//									netErr = false;
//								}
//							}
//							time11 = time22;
//						}
//					}
//					Thread.sleep(10);
//				}
//				netErr = isNetErr;
//				//
//				getMaRecord().saveRecordData();
//				// 记录耗时
//				if (!MaStation.getInstance().netErr) {
//					long timef = MaStation.getInstance().getCurrentTimeMillis();
//					String timeS = ByString.getTimeStr(time, "MM-dd HH:mm:ss ");
//					String mess = timeS + "##saveLog time:" + (timef - time)
//							+ "--" + veTemp.size();
//					saveFlag = saveMess(null, NoRecord.TYPE_OPERATE, mess, time);
//					if (saveFlag == -1) {
//						if (!netErr) {
//							netErr =true;
//							// 网络异常,记录出错
//							NoMess noMess = new NoMess();
//							noMess.time = time;
//							noMess.mess = "##saveLog异常:" + mess;
//							MaStation.getInstance().getMaRecord()
//									.addMessNet(noMess);
//						}
//					}
//				}
//				//
//				getMaRecord().addEmptyNode();
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
	}

//	private MaRecord getMaRecord() {
//		return MaStation.getInstance().getMaRecord();
//	}

//	private int saveMess(NoRecord noRecord, int type, String content, long time) {
//		int saveFalg = 0;
//		try {
//			String stationCode = MaStation.getInstance().getStationCode();
//			String teams = "";
//			String idcard = "";
//			String name = "";
//			if (noRecord != null && noRecord.getIdcard() != null) {
//				teams = noRecord.getTeams();
//				idcard = noRecord.getIdcard();
//				name = noRecord.getName();
//				// time = noRecord.getTime();
//			} else {
//				if (MaStation.getInstance().getUser() != null) {
//					teams = MaStation.getInstance().getUser().getTeams();
//					idcard = MaStation.getInstance().getUser().getCode();
//					name = MaStation.getInstance().getUser().getName();
//				}
//			}
//			if (content == null)
//				content = "";
//			if (name != null && !name.equals("")
//					&& (teams != null && !teams.equals(""))) {
//				userNull = false;
//				// content = ByString.getTimeStr(time, "HH:mm:ss") + "-" +
//				// content;
//				saveFalg = MaStation.getInstance().getMaWebService().saveLog(
//						stationCode, teams, idcard, name, time, type, content);
//			} else {
//				if (!userNull) {
//					userNull = true;
//					String mess = "##saveMess false:" + stationCode + ":"
//							+ teams + ":" + idcard + ":" + name + ":" + time
//							+ ":" + type + ":" + content;
//					NoMess noMess = new NoMess();
//					noMess.mess = mess;
//					noMess.time = time;
//					MaStation.getInstance().getMaRecord().addMessErr(noMess);
//				}
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//		return saveFalg;
//	}
}
