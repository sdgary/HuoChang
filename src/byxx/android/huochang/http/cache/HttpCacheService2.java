package byxx.android.huochang.http.cache;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.ab.db.storage.AbSqliteStorage;
import com.ab.db.storage.AbSqliteStorageListener;
import com.ab.db.storage.AbStorageQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;

public class HttpCacheService2 extends Service {
	
    private int TIME = 10000;
	//数据库操作类
	private AbSqliteStorage mAbSqliteStorage = null;
	//定义数据库操作实现类
	private CacheDAO cacheDAO = null;
	private List<CacheDto2> list = new ArrayList<CacheDto2>();
    private Context context;

	public static HttpCacheService2 getCacheService() {
		return cacheService;
	}

	private static HttpCacheService2 cacheService = null;

	public Handler getCacheHandler() {
		if(cacheHandler == null){
			cacheHandler = new Handler(){
				public void handleMessage(Message msg) {
					dealHandleMessage(msg);
				}
			};
		}
		return cacheHandler;
	}

	Handler cacheHandler = null;
	Handler handler = new Handler();
    Runnable runnable = new Runnable() {  
  
        @Override  
        public void run() {  
            // handler自带方法实现定时器  
            try {
                handler.postDelayed(this, TIME); 
                System.out.println("do...");
                CacheDto2 dto;
                if (isNetworkConnected()) {
					queryData();
                	for (int i = 0; i < list.size(); i++) {
                    	dto = list.get(i);
						String result = "";
						if(Math.abs(System.currentTimeMillis() - dto.getTime()) <= 12*60*60*1000){//十二小时内的才处理
							Properties tProperties = (Properties) dto.getDto();
							result = MaStation.getInstance().getMaWebService().cacheCommonLink(dto.getUrl(), tProperties);
							if(result != null  && !"".equals(result)){
								if(result.contains("[") || result.equals("true")||"上传图片成功!".equals(result)){
									delData(dto.getUuid());
								}
							}
						}else{
							delData(dto.getUuid());
						}
    				}
				}
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };

    /**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		cacheService = this;
		//初始化AbSqliteStorage
		mAbSqliteStorage = AbSqliteStorage.getInstance(this);
		//初始化数据库操作实现类
		cacheDAO = new CacheDAO(HttpCacheService2.this);
		handler.postDelayed(runnable, TIME); //每隔30s执行
		MaStation.getInstance().setHandlerCache(getCacheHandler());

	}

	public void saveData(CacheDto2 u){

		//无sql存储的插入
		mAbSqliteStorage.insertData(u, cacheDAO, new AbSqliteStorageListener.AbDataInsertListener() {

			@Override
			public void onSuccess(long id) {
				//showToast("插入数据成功id="+id);
//				queryData();
				Toast.makeText(context, "插入数据成功!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int errorCode, String errorMessage) {
				Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
			}

		});
	}

	public void queryData(){
		//查询数据
		AbStorageQuery mAbStorageQuery = new AbStorageQuery();
		//无sql存储的查询
		mAbSqliteStorage.findData(mAbStorageQuery, cacheDAO, new AbSqliteStorageListener.AbDataInfoListener() {

			@Override
			public void onFailure(int errorCode, String errorMessage) {
//				showToast(errorMessage);
			}

			@Override
			public void onSuccess(List<?> paramList) {
				list = (List<CacheDto2>) paramList;
			}

		});

	}

	/**
	 *
	 * 描述：删除数据
	 * @param id
	 */
	public void delData(String id){

		//条件
		AbStorageQuery mAbStorageQuery = new AbStorageQuery();
		mAbStorageQuery.equals("uuid", id);

		//无sql存储的删除
		mAbSqliteStorage.deleteData(mAbStorageQuery, cacheDAO, new AbSqliteStorageListener.AbDataOperationListener() {

			@Override
			public void onSuccess(long paramLong) {
//				queryData();
			}

			@Override
			public void onFailure(int errorCode, String errorMessage) {
//				showToast(errorMessage);
			}

		});

	}
	//装卸作业消息处理机制
	private void dealHandleMessage(Message msg) {
		try {
			if (msg == null)
				return;
			try {
				// 读取完后设置数据
				switch (msg.what) {
					case Constant.HTTP_CACHE_SIGN: // nfc返回
						CacheDto2 cacheDto = (CacheDto2) msg.getData().getSerializable("cacheDto");
						saveData(cacheDto);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	 
}
