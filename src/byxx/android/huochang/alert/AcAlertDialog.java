package byxx.android.huochang.alert;


import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.SuperApplication;
import byxx.android.huochang.task.RuTask;
import byxx.android.huochang.util.ByString;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
//byxx.android.station.action.MyDialog_ACTION
public class AcAlertDialog extends Activity {
	ProgressDialog dialog = null;
	private EditText editText = null;
	private Button button = null;
	private Handler handler = null;
	private int opType = 0;
	private ThSpeek speek;
	private String opJobId = null;
	private Boolean bspeek = true;
	private String msgids = null;
	
	
	
	
	public String getMsgids() {
		return msgids;
	}

	public void setMsgids(String msgids) {
		this.msgids = msgids;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			requestWindowFeature(Window.FEATURE_LEFT_ICON);
			setContentView(R.layout.alertdialog);
			setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
					R.drawable.ic_launcher);
			editText = (EditText) findViewById(R.id.editText1);
			button = (Button) findViewById(R.id.button1);
//			AppManager.getAppManager().addActivity(this);
			//
			MaStation.getInstance().getMoAlert().getThShowAlert().setHandlerAlert(getHandler());

			Intent intent = MaStation.getInstance().getMoAlert().getAlertIntent();
			if(intent != null){
				String alertMess = intent.getStringExtra("showText");
				int alertType = intent.getIntExtra("type", 0);
				long time = intent.getLongExtra("time", 0);
				String jobId = intent.getStringExtra("jobId");
				setMsgids(intent.getStringExtra("msgids"));
				bspeek = intent.getBooleanExtra("speek",true);
				reShowText(alertMess, alertType, time, jobId);
			}else{
				finish();
			}
			
			button.setOnClickListener(new OnClickListener() {
				// 测试
				public void onClick(View v) {
					try {
						showDialog(0);
						switch (opType) {
						case NoAlertMess.TASK_CHANGE:
						case NoAlertMess.TASK_PRVE_START_TRAIN:
						case NoAlertMess.TASK_CHANGE_START_TRAIN:
						case NoAlertMess.TASK_PIVOT_PROCEEDING:
						case NoAlertMess.TASK_RISK:
						case NoAlertMess.TASK_IMPORTION:
						case NoAlertMess.TASK_FKBZ:
						case NoAlertMess.TASK_FKBZ_ZT_JBFKTJ:
						case NoAlertMess.TASK_GBWYS_FKBZ_ZDLKKSFK:
						case NoAlertMess.TASK_GBWYS_FKBZ_ZDLKFKWB:
						case NoAlertMess.TASK_FKD_FKBZ_FK:
						case NoAlertMess.TASK_ZT_FKD_KSFK:
						case NoAlertMess.TASK_ZYZT:	
						case NoAlertMess.TASK_ADD:	
							
							// 签收任务
//							MaStation.getInstance().getMoTask().commitTaskChange(getMsgids());
						break;
						default:
							break;
						}
						if (dialog != null && dialog.isShowing()) {
							dismissDialog(0);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					//
					StopSpeek();
//					MaStation.getInstance().getMoTask().setNextMess(true);
					finish();
				}
			});
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		AppManager.getAppManager().addActivity(this);
	}

	public void onDestroy() {
		try {
			MaStation.getInstance().getMoAlert().getThShowAlert()
					.setHandlerAlert(null);
//			MaStation.getInstance().showingAlert = false;
			StopSpeek();
//			MaStation.getInstance().getMoTask().setNextMess(true);
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	private void reShowText(String alertMess, int type, long time, String jobId) {
		try {
			String soundMess = null;
			String title = "";
			RuTask ru = null;
			getString(R.string.system_name_short);
			switch (type) {
			case NoAlertMess.TASK_WORK:
				soundMess = "任务作业,请确认";
				title = title + "任务作业提醒";
				break;
			case NoAlertMess.TASK_STOP_TICKET:
				soundMess = "停检准备,请确认";
				title = title + "停检准备提醒";
				break;
			case NoAlertMess.TASK_TRAIN_FINISH:
				soundMess = "交接准备,请确认";
				title = title + "交接准备提醒";
				break;
			case NoAlertMess.TASK_CHANGE:
				soundMess = "任务变化,请确认";
				title = title + "任务变更/新增/取消提醒";
				break;
			case NoAlertMess.TASK_PRVE_START_TRAIN:
				soundMess = "列车接近,请确认";
				title = title + "列车接近提醒";
				break;
			case NoAlertMess.TASK_CHANGE_START_TRAIN:
				soundMess = "列车接近，任务变化,请确认";
				title = title + "列车接近，任务变更/新增/取消提醒";
				break;
			case NoAlertMess.TASK_END:
				soundMess = "下一任务准备,请结束当前任务";
				title = title + "作业结束提醒";
				break;
			case NoAlertMess.TASK_ALLOW_OPEN:
				soundMess = "具备放客条件,请组织放客";
				title = title + "允许放客提醒";
				break;
			case NoAlertMess.TASK_ADD:
				soundMess = "任务作业,请确认";
				title = title + "任务添加";
				break;
			case NoAlertMess.TASK_IMPORTION:
				soundMess = "任务作业,请确认";
				title = title + "作业提醒";
				break;
			default:
				break;
			}
			// 时间
			title = title + " " + ByString.getTimeStr(time, "HH:mm");
			//
			setTitle(title);
			//
			StopSpeek();
			if(bspeek){
			speek = new ThSpeek(soundMess, type, this, jobId);
			MaStation.getInstance().getMoAlert().add(speek);
			speek.start();
			}
			Bundle bundle = new Bundle();
			bundle.putString("data", alertMess);
			bundle.putInt("type", type);
			bundle.putLong("time", time);
			if (jobId != null)
				bundle.putString("jobId", jobId);
			Message msg = new Message();
			msg.setData(bundle);
			getHandler().sendMessage(msg);
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public Handler getHandler() {
		if (handler == null) {
			handler = new Handler() {
				public void handleMessage(Message msg) {
					dealHandleMessage(msg);
				}
			};
		}
		return handler;
	}

	private void dealHandleMessage(Message msg) {
		try {
			if (msg == null)
				return;
			switch (msg.what) {
			case ShowMess.TYPE_SHOW:
				String alertMess = msg.getData().getString("data");
				int type = msg.getData().getInt("type");
				long time = msg.getData().getLong("time");
				String jobId = msg.getData().getString("jobId");
				reShowText(alertMess, type, time, jobId);
				break;
			case ShowMess.TYPE_TASK_START:
				break;
			default:
				alertMess = msg.getData().getString("data");
				opType = msg.getData().getInt("type");
				opJobId = msg.getData().getString("jobId");
				editText.setText(alertMess);
				break;
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	@Override
	protected void onPause() {
		try {
			StopSpeek();
//			MaStation.getInstance().getMoTask().setNextMess(true);
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
//		MaStation.getInstance().showingAlert = true;
		super.onResume();
	}

	private void StopSpeek() {
		try {
			if (speek != null) {
				speek.stopAlert();
				MaStation.getInstance().getMoAlert().remove(speek);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	protected Dialog onCreateDialog(int id) {
		try {
			if (dialog == null) {
				dialog = new ProgressDialog(this);
				if (id == 0)
					dialog.setMessage("正在上报确认状态，请稍候...");
				else
					dialog.setMessage("正在处理，请稍候...");
				dialog.setIndeterminate(true);
				dialog.setCancelable(true);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return dialog;
	}

}
