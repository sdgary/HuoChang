package byxx.android.wj.http.cache;

import java.util.List;
import java.util.Properties;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.util.ByDialog;

//DL_20151114_(集装箱号的)
public class HttpCacheService extends Service {
	
    private int TIME = 10000;
    
    private Context context;
    public static CacheDB db;
	
	Handler handler = new Handler();  
    Runnable runnable = new Runnable() {  
  
        @Override  
        public void run() {  
            // handler
            try {  
                handler.postDelayed(this, TIME); 
                System.out.println("do...");
                CacheDto dto;

                if (isNetworkConnected()) {
                	List<CacheDto> list = db.queryData();

                	for (int i = 0; i < list.size(); i++) {
                    	dto = list.get(i);
						if(Math.abs(System.currentTimeMillis() - dto.getTime()) <= 12*60*60*1000){//十二小时内的才处理
							commonInvoke invoke = new commonInvoke();
							invoke.execute(dto.getUuid());
						}else{
							db.deleteData(dto.getUuid());
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
	 * ��������Ƿ����
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
		handler.postDelayed(runnable, TIME); //ÿ��30sִ��  
		context = this;
		db = new CacheDB(context);
	}
	
	private class commonInvoke extends AsyncTask<String, Void, String> {
		private String uuid;
		@Override
		protected String doInBackground(String... params) {
			String invoke = null;
			try {
				uuid = params[0];
//				CacheDto dto = Cache.getInstance().getCacheDtoByUUID(uuid);
				CacheDto dto = db.queryData(uuid).get(0);
				Properties tProperties = (Properties) dto.getDto();
				invoke = MaStation.getInstance().getMaWebService().cacheCommonLink(dto.getUrl(), tProperties);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return invoke;
		}
		
		@Override
		protected void onPreExecute() {
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result != null  && !"".equals(result)){
				if(result.contains("{") || result.equals("true")||"上传图片成功!".equals(result)){
					db.deleteData(uuid);
				}
			}else {
				ByDialog.showMessage(context, "网络异常");
			}
		}

	}
	 
}
