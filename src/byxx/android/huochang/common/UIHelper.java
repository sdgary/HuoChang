package byxx.android.huochang.common;









import byxx.android.huochang.picture.ImageDialog;
import byxx.android.huochang.picture.ImageZoomDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * 
 * @version 1.0
 * @created 2012-3-21
 */
public class UIHelper {
	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}

	

	
	
	/**
	 * 显示图片对话框
	 * 
	 * @param context
	 * @param imgUrl
	 */
	public static void showImageDialog(Context context, String imgUrl) {
		Intent intent = new Intent(context, ImageDialog.class);
		intent.putExtra("local_img", imgUrl);
		context.startActivity(intent);
	}
//	public static void showImageDialog(Context context, String imgUrl) {
//		Intent intent = new Intent(context, ImageDialog.class);
//		intent.putExtra("img_url", imgUrl);
//		context.startActivity(intent);
//	}

	public static void showImageZoomDialog(Context context, String imgUrl) {
		Intent intent = new Intent(context, ImageZoomDialog.class);
		intent.putExtra("img_url", imgUrl);
		context.startActivity(intent);
	}
}
