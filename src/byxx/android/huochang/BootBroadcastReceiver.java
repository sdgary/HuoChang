package byxx.android.huochang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/**
 * 开机启动广播监听
 * @author Way
 *
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String action_boot = "android.intent.action.BOOT_COMPLETED";

	public void onReceive(Context context, Intent intent) {
		 try {
			 if (intent.getAction().equals(action_boot)) {
				 Intent ootStartIntent = new Intent(context, AcMain.class);
				 ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 context.startActivity(ootStartIntent);
			 }
		 } catch (Exception e) {
//			 MaStation.getInstance().recordExc(e, false);
		 	}
		 
	}
	
}
