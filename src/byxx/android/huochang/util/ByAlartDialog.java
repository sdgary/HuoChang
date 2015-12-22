/**
 * 
 */
package byxx.android.huochang.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MotionEvent;

/**
 * @author WGary 韦永城
 * 2015-6-5
 */
public class ByAlartDialog extends AlertDialog{

	protected ByAlartDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
	
}
