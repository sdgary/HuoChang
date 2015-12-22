package byxx.android.huochang.message;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;

/**
 * 暂时定位单对单发送消息，此类留作后期若需要PDA端群发消息时再启用。
 * @author WGary 韦永城
 * 2015-6-18
 */
public class AcChooseCallMan extends Activity {

	private static final String NAME = "NAME";

	private EventAdapter mAdapter;
	private List<ReceiverDto> itemList = new ArrayList<ReceiverDto>();
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.choosecallman);
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_launcher);
		context = getApplicationContext();
		setMans();
	}

	public void setMans() {
		try {
			ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.exLVOne);
			
			mAdapter = new EventAdapter(context);
			expandableListView.setAdapter(mAdapter);
			
			
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	@Override
	protected void onDestroy() { 
		// TODO Auto-generated method stub
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}
	
	
	/*      
	 * 新建一个类继承BaseAdapter，实现视图与数据的绑定
     */
	private class EventAdapter extends BaseAdapter {
		
		public EventAdapter(Context context) { }

        @Override
        public int getCount() {
            return itemList.size();//返回数组的长度        
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
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
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
                view = inflater.inflate(R.layout.msg_receiver_item, null);
                holdr = new ViewHolder();
                holdr.tv = ((CheckBox)view.findViewById(R.id.tv_men));
                holdr.tv.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						if(holdr.tv.isChecked()){//如果被选中
							
						}
					}
				});
                view.setTag(holdr);
            }else {
            	holdr = (ViewHolder) view.getTag();
			}
            
            ReceiverDto dto = itemList.get(position);
            holdr.tv.setText(dto.getReceiverName());

            return view; 
        }

			public class ViewHolder{
				CheckBox tv;
			}
	}
}
