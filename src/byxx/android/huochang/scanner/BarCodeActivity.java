package byxx.android.huochang.scanner;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.R;
import byxx.android.huochang.R.id;
import byxx.android.huochang.R.layout;
import byxx.android.huochang.R.menu;
import byxx.android.huochang.function.AcFunction;
import byxx.android.huochang.storemanager.AcGoodsDetail;
import byxx.android.huochang.storemanager.WhGoodsDTO;
import byxx.android.huochang.util.ByDialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BarCodeActivity extends Activity {

	private Context context;
	
	WhGoodsDTO dto = null;
	LinearLayout mainPanel;
	//计划ID
	TextView id;
	//状态
	TextView status;
	//品名
	TextView pm;
	//收货人
	TextView shr;
	//发货人
	TextView fhr;
	//货票号
	TextView hph;
	//运单号
	TextView ydh;
	//总重
	TextView weight;
	//总计数量
	TextView numzg;
	//入库量
	TextView numyrk;
	//出库量
	TextView numyck;
	//费用
	TextView fy;
	//实际入库
	TextView factintime;
	//实际出库
	TextView factouttime;
	//到达车
	TextView ddcar;
	//出发车
	TextView cfcar;
	//预约单号
	TextView yydh;
	//其他
	TextView bz;
	Button scaner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bar_code);
		context = getApplicationContext();

//		AppManager.getAppManager().addActivity(this);
		
		//初始化界面
		mainPanel = (LinearLayout) findViewById(R.id.mainPanel);
		
		//计划ID
		id = (TextView) findViewById(R.id.id);
		//状态
		status = (TextView) findViewById(R.id.status);
		//品名
		pm = (TextView) findViewById(R.id.pm);
		//收货人
		shr = (TextView) findViewById(R.id.shr);
		//发货人
		fhr = (TextView) findViewById(R.id.fhr);
		//货票号
		hph = (TextView) findViewById(R.id.hph);
		//运单号
		ydh = (TextView) findViewById(R.id.ydh);
		//总重
		weight = (TextView) findViewById(R.id.weight);
		//总计数量
		numzg = (TextView) findViewById(R.id.numzg);
		//入库量
		numyrk = (TextView) findViewById(R.id.numyrk);
		//出库量
		numyck = (TextView) findViewById(R.id.numyck);
		//费用
		fy = (TextView) findViewById(R.id.fy);
		//实际入库
		factintime = (TextView) findViewById(R.id.factintime);
		//实际出库
		factouttime = (TextView) findViewById(R.id.factouttime);
		//到达车
		ddcar = (TextView) findViewById(R.id.ddcar);
		//出发车
		cfcar = (TextView) findViewById(R.id.cfcar);
		//预约单号
		yydh = (TextView) findViewById(R.id.yydh);
		
		scaner = (Button) findViewById(R.id.scaner);
		scaner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				scaner();
			}
		});

		//初始化数据
		
		dto = (WhGoodsDTO) getIntent().getSerializableExtra("obj");
		if( dto == null ){
			ByDialog.showMessage(BarCodeActivity.this, "当前没有货物信息，请点击扫码按钮！");
		}else{
			initDate();
		}
		AppManager.getAppManager().addActivity(this);
	}
	/**
	 * 初始化数据
	 */
	private void initDate(){

		id.setText("ID:"+dto.getId());
		status.setText("状态:"+dto.getState());
		pm.setText("品名:"+dto.getPm());
		shr.setText("收货人"+dto.getShr());
		fhr.setText("发货人:"+dto.getFhr());
		hph.setText("货票号:"+dto.getHph());
		ydh.setText("运单号:"+dto.getYdh());
		weight.setText("总重:"+dto.getWeight()+"kg");
		numzg.setText("总计:"+dto.getNumZg()+dto.getJldw());
		numyrk.setText("入库量:"+dto.getNumYrk()+dto.getJldw());
		numyck.setText("出库量:"+dto.getNumYck()+dto.getJldw());
		fy.setText("费用:"+dto.getFy());
		factintime.setText("实际入库:"+dto.getFactintime());
		factouttime.setText("实际出库:"+dto.getYdh());
		ddcar.setText("到达车:"+dto.getYSLX(dto.getDdlx())+dto.getDdcarnum());
		cfcar.setText("出发车:"+dto.getYSLX(dto.getCflx())+dto.getCfcarnum());
		yydh.setText("预约单号:"+dto.getYydh());
	}
	
	private void scaner(){//移交计划提交
		Intent intent = new Intent();
		intent.setClass(context, CaptureActivity.class);
//		Bundle bundle = new Bundle();
//		intent.putExtra("obj", dto);
//		bundle.put
//		intent.putExtras(bundle);
		dto = null;
		startActivity(intent);
		finish();
//		AppManager.getAppManager().finishActivity(AcStoreManager.this);
//		AcStoreManager.this.finish();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bar_code, menu);
		return true;
	}

}
