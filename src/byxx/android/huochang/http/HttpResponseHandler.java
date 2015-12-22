package byxx.android.huochang.http;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.util.Log;
import byxx.android.wj.http.cache.LoadingDialog;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;

public abstract class HttpResponseHandler extends AsyncHttpResponseHandler{
	
	private static final String LOG_TAG = "JsonHttpResponseHandler";
	
	private Context context;
	private String startText;
	private boolean autoFinshLoading = true;
	
	public HttpResponseHandler(Context context,String startText,boolean autoFinshLoading){
		this.context = context;
		if (isEmpty(startText)) {
			this.startText = "正在获取数据,请稍等";
		}else {
			this.startText = startText;
		}
		this.autoFinshLoading = autoFinshLoading;
	}
	
	public HttpResponseHandler(Context context,String startText){
		this.context = context;
		if (isEmpty(startText)) {
			this.startText = "正在获取数据,请稍等";
		}else {
			this.startText = startText;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		LoadingDialog.create(context, startText, LoadingDialog.LOADDING);
	}
	
	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
		Log.w(LOG_TAG, "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", error);
		LoadingDialog.create(context, error.toString(), LoadingDialog.WRONG);
	}
	
	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
		String json;
		try {
			json = new String(responseBytes, "UTF-8");
			StandReturnInfo info = JSON.parseObject(json,StandReturnInfo.class);
			if (info.isSuccess()) {
				 LoadingDialog.close();
				 onSuccess(statusCode, headers, (StandReturnInfo) info);
			}else {
				LoadingDialog.create(context, info.getError(), LoadingDialog.WRONG);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void onSuccess(int statusCode, Header[] headers, StandReturnInfo info);
	
	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if (str==null) {
			return true;
		}else if(str.equals("")){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		if (list!=null && list.size()<=0 ) {
			return true;
		}
		return false;
	}
}
