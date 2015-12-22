package byxx.android.wj.http.cache;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import byxx.android.huochang.R;


public class LoadingDialog {
	public static int LOADDING = 1;
	public static int RIGHT = 2;
	public static int WRONG = 3;
	private static Dialog loadingDialog = null;
	private static TextView tipTextView;
	private static int count = 0;
	public static Dialog create(Context context, String msg,int flag){
		close();
		count = 0;
		LayoutInflater inflater = LayoutInflater.from(context);  
	    View v = inflater.inflate(R.layout.loading_base, null);// �õ�����view  
	    LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// ���ز���  
	    // main.xml�е�ImageView  
	    ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);  
	    tipTextView = (TextView) v.findViewById(R.id.tipTextView);// ��ʾ���� 
	    loadingDialog = new Dialog(context, R.style.loading_dialog);// �����Զ�����ʽdialog  
	    loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
	            LinearLayout.LayoutParams.MATCH_PARENT,  
	            LinearLayout.LayoutParams.MATCH_PARENT));// ���ò���  
	    tipTextView.setText(msg);// ���ü�����Ϣ  
	   
	    if (flag==LOADDING) {
	    	// ���ض���
		    Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);  
		    // ʹ��ImageView��ʾ����  
		    spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
		    loadingDialog.setCancelable(false);// �������á����ؼ�ȡ��  
		}else if (flag == RIGHT) {
			spaceshipImage.setImageResource(R.drawable.ok);
			loadingDialog.setCancelable(true); 
		}else if (flag == WRONG) {
			spaceshipImage.setImageResource(R.drawable.no);
			loadingDialog.setCancelable(true);
		}
	    loadingDialog.show();
	    loadingDialog.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) { 
					count++;
					if (count==3) {
						count = 0;
						loadingDialog.dismiss();
					}
				}
				return false;
			}
		});
		return loadingDialog;
	}
	
	
	
	public static void close(){
		if (loadingDialog!=null) {
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}
	
}
