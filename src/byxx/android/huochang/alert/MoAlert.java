package byxx.android.huochang.alert;

import java.util.Vector;

import android.content.Intent;
import byxx.android.huochang.MaStation;



public class MoAlert {

	private Vector<ThSpeek> alertThread = null;
	
	private ShowMess thShowAlert=null;

	public ShowMess getThShowAlert() {
		if (thShowAlert==null){
			thShowAlert=new ShowMess();
		}
		return thShowAlert;
	}

	private Intent alertIntent = null;
    /**
     * 在AcAlertDialog使用
     * @return
     */
	public Intent getAlertIntent() {  
		return alertIntent;
	}
    /**
     * 在AcAlertDialog使用
     * @return
     */
	public void setAlertIntent(Intent alertIntent) {
		this.alertIntent = alertIntent;
	}
	
	
	public Vector<ThSpeek> getAlertThread() {
		if (alertThread == null) {
			alertThread = new Vector<ThSpeek>();
		}
		return alertThread;
	}

	public void add(ThSpeek thSpeek) {
		getAlertThread().add(thSpeek);
	}

	public void remove(ThSpeek thSpeek) {
		getAlertThread().remove(thSpeek);
	}

	public void clear() {
		try {
			ThSpeek thSpeek = null;
			for (int i = 0; i < getAlertThread().size(); i++) {
				thSpeek = getAlertThread().get(i);
				thSpeek.stopAlert();
			}
			getAlertThread().clear();
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
	
	public void init() {
		try {
			getThShowAlert().start();
//			MyExecutorService.getInstance().addThread(getThShowAlert());
			
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}
	
	public void stopThread() {
		getThShowAlert().setRunFlag(false);
	}
}
