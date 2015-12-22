package byxx.android.huochang.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSON;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.R.color;
import byxx.android.huochang.SuperApplication;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.task.GdmsZxzypbDTO;
import byxx.android.huochang.util.ByAlartDialog;
import byxx.android.huochang.util.ByDialog;
import byxx.android.wj.http.cache.LoadingDialog;


public class AcMessageSend extends Activity {

	SendMSG sendMsg;
	ProgressDialog dialog = null;
	private MenAdapter menAdapter;
	private MsgAdapter msgAdapter;
	private List<ReceiverDto> menList = new ArrayList<ReceiverDto>();//候选人列表
//	private String receiverID;
//	private String receiverName;
	private Hashtable<String, String> menTable = new Hashtable<String, String>();
	ReceiverDto men = new ReceiverDto();//目标人对象
	int pos = 0;//目标人下标
	private int msgId = 0;//消息id
	private boolean bSend = false; // 发送
    private EditText eTSend;//发送文本
    private TextView et_men;//目标人
    private Button btn_men;// 当前为单选，选人按钮改为下拉菜单
    private Button buSend;//和发送按钮
    private ListView listView1;//对话内容显示列表
    private List<XMsgDto> msgList = new ArrayList<XMsgDto>();//候选人列表
    XMsgDto dto = new XMsgDto();
    ProgressDialog progressDialog;
    private Context _context;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message_send);
//		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
//				R.drawable.ic_launcher);
		SuperApplication.getInstance().addActivity(this);
		
		dto = (XMsgDto) getIntent().getSerializableExtra("obj");
		
		this._context=this;
		
		eTSend = (EditText) findViewById(R.id.eTSend);
		et_men = (TextView)findViewById(R.id.et_men);
		btn_men = (Button) findViewById(R.id.btn_men);
//		btn_men.set
		buSend = (Button) findViewById(R.id.buSend);
		listView1 = (ListView) findViewById(R.id.listView1);
		
		
	
		
		try {

			btn_men.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// 选择人员
					buChoose();
				
				}
			});
	

			buSend.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// 
					if(eTSend.getText().toString().isEmpty())
					{
						Log.v("test", "isempty");
					Toast.makeText(_context, "请输入发送内容", Toast.LENGTH_SHORT).show();
					}else if(et_men.getText().toString().isEmpty()){
						Log.v("test", "isempty");
						Toast.makeText(_context, "请选择联系人", Toast.LENGTH_SHORT).show();
					}else{
						buSend();
						Log.v("test","busend()");
					}
				}
			});
			
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		
		initData();
		initView();
		AppManager.getAppManager().addActivity(this);
	}

	/**
	 * 初始化基础数据 第一步
	 */
	public void initData(){
		// 待选人员数据
		menList = MaStation.getInstance().getMenList();
		for(ReceiverDto o : menList){
			menTable.put(o.getReceiverName(), o.getReceiverID());
		}
		//消息展示
		HashMap<Integer, XMsgDto> msg = new HashMap<Integer, XMsgDto>();
		
		if(dto !=null){
//			msg = MaStation.getInstance().getMsgMap().get(dto.getMsgSender());
//
//			Iterator<Integer> iterator = msg.keySet().iterator();
//			while(iterator.hasNext()) {
//				if(msg.get(iterator.next()) != null){
//					msgList.add(msg.get(iterator.next()));
//				}
//			}
			msgList.add(dto);
		}
	}
	
	/**
	 * 初始化显示内容 第二部
	 *  第一步为异步操作，此处不可以第一步的处理结果为依据。
	 */
	public void initView(){
		//如果是回复则不可选择目标人物，且目标人物设定为传来的人物
		if(dto !=null && dto.isReply()){
			btn_men.setEnabled(false);
			// 以传入值为依据，初始设定人名，在提交时提供检索条件。
			et_men.setText(dto.getMsgSender());
		}
		
		msgAdapter = new MsgAdapter(_context);
		listView1.setAdapter(msgAdapter);
	}
	
	private void buSend() {
		try {
			
			String receiver = et_men.getText().toString();
			
			
			if (!receiver.trim().equals("")) {
				String cont = eTSend.getText().toString();
				sendMsg = new SendMSG();
				sendMsg.execute(receiver, cont);
				
			} else {
				ByDialog.showMessage(this, "请选择人员!");
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

	/**
	 * 人员选择
	 */
	private void buChoose() {
		Dialog d;
		AlertDialog.Builder dialog = new AlertDialog.Builder(_context);
		dialog.setTitle("请选择联系人！");
		LinearLayout layout = new LinearLayout(_context);
		ListView mens = new ListView(_context);
		menAdapter = new MenAdapter(_context, dialog);
		mens.setAdapter(menAdapter);
		mens.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				men =  menList.get(arg2-1);
				et_men.setText(men.getReceiverName());
				arg1.setBackgroundColor(color.orange);
				pos = arg2;
				menAdapter.notifyDataSetChanged();
			}
		});
		layout.addView(mens);
		dialog.setView(layout);
		dialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which){
				
			}
		});
		dialog.create().show();
	}

	@Override
	protected void onDestroy() {
//		if (isNewFlag() && !bSend) {
//			MaStation.getInstance().getMoMessage().clearNewMess();
//		}
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void showText(String id) {
//		int ii=0;
//		Log.v("test","showtext()"+(ii++));
//		try {
//			setMessageId(id);
//			KfcTopicMsg kfcTopicMsg = MaStation.getInstance().getMoMessage()
//					.getKfcTopicMsg(id);
//			if (kfcTopicMsg != null) {
////				EditText et1 = (EditText) findViewById(R.id.eTTitle);
//				
//				ettitle.setText(kfcTopicMsg.getTTopic());
//				String supId = kfcTopicMsg.getSuperTid();
//				if (supId == null || supId.equals("")) {
//					supId = kfcTopicMsg.getTId();
//				}
//				String content = MaStation.getInstance().getMoMessage()
//						.getSupIdMessRead(supId);
//				showContent(content);
////				EditText et2 = (EditText) findViewById(R.id.eTMans);
//				if (kfcTopicMsg.getSender() != null
//						&& kfcTopicMsg.getSender().equals(
//								MaStation.getInstance().getUser().getName())) {
//					etmans.setText("");
//				} else {
//					etmans.setText(kfcTopicMsg.getSender());
//				}
//				//
//				if (kfcTopicMsg.getSignTime() <= 0) {
//					RuMessage ruMessage = new RuMessage(getHandler(),
//							Constant.MSG_ID_MESSAGE_COMMIT_R);
//					ruMessage.setId(id);
//					ruMessage.setContent("");
//					ruMessage.start();
//				}
//			}
			// StopSpeek();
			// String soundMess = "重点消息,注意签收";
			// speek = new ThSpeek(soundMess, -1, this);
			// MaStation.getInstance().getMoAlert().add(speek);
			// speek.start();
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
	}

	private void showContent(String s) {
//		EditText et = (EditText) findViewById(R.id.eTContent);
//		etcontent.setText(s);
//		int pos = 0;
//		if (s != null) {
//			pos = s.length();
//		}
//		Log.v("test","showcontent pos :"+pos);
//		etcontent.setSelection(pos);
	}

	@Override
	protected void onStart() {
//		try {
//			Bundle bundle = getIntent().getExtras();
//			Intent intent = getIntent();
			
//			boolean build = intent.getBooleanExtra("new", false);
			
//			boolean build = bundle.getBoolean("new");
//			Log.v("test", "build boolean "+build);
////			String id = intent.getStringExtra("id");
//			String id = bundle.getString("id");
			
//			setNewFlag(build);
//			setMessageId(id);
//			if (build) {
//			} else {
//				showText(id);
//			}
//		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
//		}
		super.onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/*      
	 * 新建一个类继承BaseAdapter，实现视图与数据的绑定
	 * 人员选择功能适配器
     */
	private class MenAdapter extends BaseAdapter {
		AlertDialog.Builder dialog;
		public MenAdapter(Context context, AlertDialog.Builder dialog) {this.dialog = dialog; }

        @Override
        public int getCount() {
            return menList.size();//返回数组的长度        
        }

        @Override
        public Object getItem(int position) {
            return menList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        
        /*书中详细解释该方法*/
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
        	final ViewHolder holdr;
            if(view == null){
                LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
                view = inflater.inflate(R.layout.listitem, null);
                holdr = new ViewHolder();
                holdr.tv = ((TextView)view.findViewById(R.id.tv_men));
                
                view.setTag(holdr);
            }else {
            	holdr = (ViewHolder) view.getTag();
			}
            
            ReceiverDto dto = menList.get(position);
            holdr.tv.setText(dto.getReceiverName());
            if(pos == position){
            	holdr.tv.setTextColor(color.orange);
            }else{
            	holdr.tv.setTextColor(Color.BLACK);
            }
            	

            return view; 
        }

			public class ViewHolder{
				TextView tv;
			}
	}
	
	/*      
	 * 新建一个类继承BaseAdapter，实现视图与数据的绑定
	 * 消息发送界面适配器
     */
	private class MsgAdapter extends BaseAdapter {
		AlertDialog.Builder dialog;
		public MsgAdapter(Context context) {}

        @Override
        public int getCount() {
            return msgList.size();//返回数组的长度        
        }

        @Override
        public Object getItem(int position) {
            return msgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        
        /*书中详细解释该方法*/
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
        	final ViewHolder holdr;
            if(view == null){
                LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
                view = inflater.inflate(R.layout.message_send_item, null);
                holdr = new ViewHolder();
                holdr.in_msg = ((TextView)view.findViewById(R.id.in_msg));
                holdr.sender = ((TextView)view.findViewById(R.id.sender));
                holdr.out_msg = ((TextView)view.findViewById(R.id.out_msg));
                holdr.out_user = ((TextView)view.findViewById(R.id.out_user));
                holdr.incoming = ((RelativeLayout)view.findViewById(R.id.incoming));
                holdr.outgoing = ((RelativeLayout)view.findViewById(R.id.outgoing));
                holdr.tv_time = ((TextView)view.findViewById(R.id.tv_time));
                
                view.setTag(holdr);
            }else {
            	holdr = (ViewHolder) view.getTag();
			}
            
            XMsgDto dto = msgList.get(position);
            if(dto != null){
            	holdr.tv_time.setText(dto.getSendTime().toString());
            	
            	//自己发送的，即为outgoingMSG
                if(dto.getMsgSender() != null && dto.getMsgSender().equals(MaStation.getInstance().getUser().getPostId())){
               	 holdr.incoming.setVisibility(View.GONE);
               	 holdr.outgoing.setVisibility(View.VISIBLE);
               	 holdr.out_user.setText(dto.getMsgSender());
               	 holdr.out_msg.setText(dto.getMsgContent());
                }else{
               	 holdr.incoming.setVisibility(View.VISIBLE);
               	 holdr.outgoing.setVisibility(View.GONE);
               	 holdr.in_msg.setText(dto.getMsgContent());
                 holdr.sender.setText(dto.getMsgSender());
                }
            }
            
 


            return view; 
        }

			public class ViewHolder{
				TextView in_msg;
				TextView sender;
				TextView out_msg;
				TextView out_user;
				TextView tv_time;
				RelativeLayout incoming;
				RelativeLayout outgoing;
			}
	}
	
private class SendMSG extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			return MaStation.getInstance().getMaWebService().sendMsg(params[0],params[1]);
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(AcMessageSend.this);
			progressDialog.setMessage("正在发送,请稍候...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialogInterface) {
					if (sendMsg != null) {
						sendMsg.cancel(true);
					}
				}
			});
			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {

			try{
				if(isNetworkConnected()||MaStation.getInstance().isTest()){
					
					if(result != null && !result.equals("[]") && !"".equals(result)){
						if("cache".equals(result)){
							ByDialog.showMessage(_context, "网络断开，操作已缓存！");
						}else {
							StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
							XMsgDto res = new XMsgDto();
							if(info != null && !info.equals("[]") && info.getData() != null  && !"".equals(info)){
								res = JSON.parseObject(info.getData().toString(), XMsgDto.class);
								if(res!= null){
									MaStation.getInstance().addMsg(res);
									initData();
									msgAdapter.notifyDataSetChanged();
									Dialog loadingDialog =  LoadingDialog.create(_context, "发送成功！！", LoadingDialog.RIGHT);
									loadingDialog.setOnDismissListener(new OnDismissListener() {

										@Override
										public void onDismiss(DialogInterface dialog) {
											finish();
										}
									});

								}
							}
							if(progressDialog != null){
								progressDialog.cancel();
							}
						}

					}else{
						if(progressDialog != null){
							progressDialog.cancel();
						}
						ByDialog.showMessage(_context, "网络信号不好！");
					}
				}else{
					if(progressDialog != null){
						progressDialog.cancel();
					}
					
				}
			}catch(Exception e){
				if(progressDialog != null){
					progressDialog.cancel();
				}
				MaStation.getInstance().recordExc(e);
			}
			if(progressDialog != null){
				progressDialog.cancel();
			}
		}
		
	}	
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
}

