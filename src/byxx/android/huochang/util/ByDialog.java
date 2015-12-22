package byxx.android.huochang.util;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

public class ByDialog {
	public final static int MSG_ID_YES = 0x0001;

	public final static int MSG_ID_CANCEL = 0x0002;

	public static void showMessage(final Context ctx, String msg) {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
			builder.setMessage(msg);
			builder.setCancelable(false);
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			builder.create().show();
		} catch (Exception e) {

		}
	}

}
