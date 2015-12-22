package byxx.android.huochang.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import byxx.android.huochang.MaStation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtil {
	  private static final String BASE_URL = MaStation.getURL();

	  private static AsyncHttpClient client = new AsyncHttpClient();

	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(getAbsoluteUrl(url), params, responseHandler);
	  }

	  public static void post(String url, AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(url), responseHandler);
	  }
	  
	  public static void postWithOutAddress(Context context, String url,HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
	      client.post(context, url, entity, contentType, responseHandler);
	  }
	  
	  public static void post(Context context, String url,HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
	      client.post(context, getAbsoluteUrl(url), entity, contentType, responseHandler);
	  }
	  //post方法增加缓存标志值,填objcet
	  public static void postJson(Context context, String url,Object dto,boolean isCacheHttpData ,AsyncHttpResponseHandler responseHandler){
		  try {
			client.setTimeout(60000);
			client.post(context, getAbsoluteUrl(url), new StringEntity(JSON.toJSONString(dto),"utf-8"), "application/json", responseHandler);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	  }

	  public static void postJson(Context context, String url,JSONObject dto, AsyncHttpResponseHandler responseHandler){
		try {
			client.setTimeout(60000);
			client.post(context, getAbsoluteUrl(url), new StringEntity(dto.toJSONString(),"utf-8"), "application/json", responseHandler);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	  }
	  
	  public static void postJson(Context context, String url,String args, AsyncHttpResponseHandler responseHandler){
			try {
				client.post(context, getAbsoluteUrl(url), new StringEntity(args,"utf-8"), "application/json", responseHandler);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		  }
	  
	  public static void postParamWithEntirelyURL(Context context,String url,AsyncHttpResponseHandler responseHandler){
		try {
			client.setTimeout(60000);
			client.post(context, url, null, "application/json", responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  
	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }
}
