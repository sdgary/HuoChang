package byxx.android.huochang.user;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import byxx.android.huochang.MaStation;



public class MoUser {


	private Hashtable<String, Vector<String>> haWatchs = null;

	private Vector<String> veRegionGroup = null;

	public Vector<String> getVeRegionGroup() {
		if (veRegionGroup == null) {
			veRegionGroup = new Vector<String>();
		}
		// veRegionGroup.clear();
		// veRegionGroup.addElement("11");
		// veRegionGroup.addElement("22");
		return veRegionGroup;
	}

	public Hashtable<String, Vector<String>> getHaWatchs() {
		if (haWatchs == null) {
			haWatchs = new Hashtable<String, Vector<String>>();
		}
		return haWatchs;
	}

	public Vector<String> getRegionGroupMan(String region) {
		Vector<String> v1 = getHaWatchs().get(region);
		// v1=new Vector<String>();
		// v1.addElement("1");
		// v1.addElement("2");
		return v1;
	}

	public void load() {
		try {
//			readUsers();
			// readUsersTree();
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
	}

//	public void readUsersTree() {
//		try {
//			Hashtable<String, ArrayList<KfcStaffWatch>> ha = null;
//			ArrayList<KfcStaffWatch> nList = null;
//			if (MaStation.getInstance().isTest()) {
//			} else {
//				String stationCode = MaStation.getInstance().getStationCode();
//				String teams = MaStation.getInstance().getUser().getTeams();
//				ha = MaStation.getInstance().getMaWebService()
//						.getStaffWatchTree(stationCode, teams);
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//	}

//	public void readUsers() {
//		try {
//			ArrayList<KfcStaffWatch> nList = null;
//			if (MaStation.getInstance().isTest()) {
//			} else {
//				String stationCode = MaStation.getInstance().getStationCode();
//				String rq = MaStation.getInstance().getUser().getWorkDay();
//				String teams = MaStation.getInstance().getUser().getTeams();
//				nList = MaStation.getInstance().getMaWebService().getStaffWatch(stationCode, rq, teams);
//			}
//			setKfcStaffWatchs(nList);
//			// 分组人员
//			getHaWatchs().clear();
//			getVeRegionGroup().clear();
//			Vector<String> value;
//			if (nList != null) {
//				KfcStaffWatch kfcStaffWatch = null;
//				String key, name;
//				String nameLocal = MaStation.getInstance().getUser().getName();
//				for (int i = 0; i < nList.size(); i++) {
//					kfcStaffWatch = nList.get(i);
//					name = kfcStaffWatch.getName();
//					if (nameLocal == null
//							|| (nameLocal != null && !nameLocal.equals(name))) {
//						key = kfcStaffWatch.getRegionGroup();
//						if (key != null) {
//							value = getHaWatchs().get(key);
//							if (value == null) {
//								value = new Vector<String>();
//								getHaWatchs().put(key, value);
//								value.addElement(name);
//							} else {
//								value.addElement(name);
//							}
//							if (getVeRegionGroup().indexOf(key) == -1) {
//								getVeRegionGroup().addElement(key);
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//		return;
//	}

	/**
	 * 根据标识（工号、姓名）获取对讲标号
	 * 
	 * @param mark
	 * @return
	 */
//	public String getCallCode(String mark) {
//		String s = null;
//		try {
//			KfcStaffWatch kfcStaffWatch = null;
//			for (int i = 0; i < getKfcStaffWatchs().size(); i++) {
//				kfcStaffWatch = getKfcStaffWatchs().get(i);
//				if (kfcStaffWatch.getName() != null
//						&& kfcStaffWatch.getName().equals(mark)) {
//					s = kfcStaffWatch.getTalkBackNum();
//				} else if (kfcStaffWatch.getIdcard() != null
//						&& kfcStaffWatch.getIdcard().equals(mark)) {
//					s = kfcStaffWatch.getTalkBackNum();
//				}
//				if (s != null) {
//					break;
//				}
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//		return s;
//	}

//	public String getCallName(String code) {
//		String s = null;
//		try {
//			if (getKfcStaffWatchs() != null) {
//				KfcStaffWatch kfcStaffWatch = null;
//				for (int i = 0; i < getKfcStaffWatchs().size(); i++) {
//					kfcStaffWatch = getKfcStaffWatchs().get(i);
//					if (kfcStaffWatch.getTalkBackNum() != null
//							&& kfcStaffWatch.getTalkBackNum().equals(code)) {
//						s = kfcStaffWatch.getName();
//						break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//		return s;
//	}

//	public String getName(String code) {
//		String s = null;
//		try {
//			KfcStaffWatch kfcStaffWatch = null;
//			for (int i = 0; i < getKfcStaffWatchs().size(); i++) {
//				kfcStaffWatch = getKfcStaffWatchs().get(i);
//				if (kfcStaffWatch.getIdcard() != null
//						&& kfcStaffWatch.getIdcard().equals(code)) {
//					s = kfcStaffWatch.getName();
//					break;
//				}
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//		return s;
//	}

//	public String getIdCard(String name) {
//		String s = null;
//		try {
//			KfcStaffWatch kfcStaffWatch = null;
//			for (int i = 0; i < getKfcStaffWatchs().size(); i++) {
//				kfcStaffWatch = getKfcStaffWatchs().get(i);
//				if (kfcStaffWatch.getName() != null
//						&& kfcStaffWatch.getName().equals(name)) {
//					s = kfcStaffWatch.getIdcard();
//					break;
//				}
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
//		return s;
//	}

//	public String getMessIdCards(String nameString) {
//		String s = "";
//		String[] names = nameString.split(",");
//		for (int i = 0; i < names.length; i++) {
//			s = s + getIdCard(names[i]);
//			if (i != names.length - 1) {
//				s = s + ",";
//			}
//		}
//		return s;
//	}
}
