/**
 * 
 */
package byxx.android.huochang.http.util;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author WGary 韦永城
 * 2015-6-6
 */
public class HttpHelper {

	//获取httpclient对象
	public static HttpClient getHttpClient(boolean isHttps){
		HttpClient httpClient=new DefaultHttpClient();
		if(isHttps){
			return (DefaultHttpClient)HttpClientConnectionManager.getSSLInstance(httpClient);
		}else{
			return httpClient;
		}
	}
	
	public static HttpGet getHttpsGetMethod(String url)throws Exception{
		return HttpClientConnectionManager.getGetMethod(url);
	}
	
	public static HttpPost getHttpsPostMethod(String url)throws Exception{
		return HttpClientConnectionManager.getPostMethod(url);
	}
	
	public static HttpPost getHttpPostMethod(String url)throws Exception{
		return new HttpPost(url);
	}
	
	public static HttpGet getHttpGetMethod(String url)throws Exception{
		return new HttpGet(url);
	}
	
	
}
