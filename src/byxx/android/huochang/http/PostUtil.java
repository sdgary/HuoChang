/**
 * 
 */
package byxx.android.huochang.http;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import byxx.android.huochang.MaStation;
import byxx.android.huochang.ftp.SeUploading;
import byxx.android.huochang.http.cache.CacheDto2;
import byxx.android.huochang.http.util.HttpHelper;
import byxx.android.wj.http.cache.CacheDto;
import byxx.android.wj.http.cache.HttpCacheService;

import com.alibaba.fastjson.JSON;


/**
 * @author WGary Τ����
 * 2015-6-6
 */
public class PostUtil {
	
	private static PostUtil instance = null;
	static String savePathString = Environment.getExternalStorageDirectory()
			+ "/" + "DCIM" + "/" + "100ANDRO/";
	private Handler handler;
	private PostUtil() {
		// TODO Auto-generated constructor stub
		handler = MaStation.getInstance().getHandlerCache();
	}
	
	
	public String post( String url, Object dto,boolean isCacheHttpData) throws IOException {
		
		if(dto == null){
			throw new IllegalArgumentException("Post��������ʵ�岻��Ϊ�գ�");
		}
		if(url == null || url.length() <=0){
			throw new IllegalArgumentException("Post�����URL����Ϊ�գ�");
		}
		String str = "";
		try {
			HttpClient httpClient = HttpHelper.getHttpClient(false);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,8000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,8000);
			HttpPost postMethod = new HttpPost(url);

			postMethod.setEntity(new StringEntity(JSON.toJSONString(dto), "utf-8"));

			HttpResponse response = httpClient.execute(postMethod);

			str = EntityUtils.toString(response.getEntity(), "UTF-8");
			if(!"".equals(str)){
				MaStation.getInstance().setIsConnected(true);
			}
		}catch (HttpHostConnectException httpe){
			MaStation.getInstance().recordExc(httpe);
			try {
				if(isCacheHttpData && !url.contains("heartbeat")){//请求无返回结果，即代表网络不通
//				if(e.getCause().toString().contains("Network is unreachable")){//网络不通的异常
					CacheDto cacheDto = new CacheDto();
					cacheDto.setUrl(url);
					cacheDto.setDto(dto);
					cacheDto.setTime(System.currentTimeMillis());
					HttpCacheService.db.addData(cacheDto);
					MaStation.getInstance().setIsConnected(false);
					MaStation.getInstance().record("cache", JSON.toJSONString(cacheDto));
					return "cache";
//					Message msg = new Message();
//					msg.what = Constant.HTTP_CACHE_SIGN;
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("cacheDto", cacheDto);
//					msg.setData(bundle);
//					handler.sendMessage(msg);
//				}
				}
			}catch (Exception e){
				MaStation.getInstance().recordExc(e);
			}

		}catch (RuntimeException rune){
			MaStation.getInstance().setIsConnected(false);
			rune.printStackTrace();
			MaStation.getInstance().recordExc(rune);
		}catch (ConnectTimeoutException cte){
			cte.printStackTrace();
			MaStation.getInstance().setIsConnected(false);
			MaStation.getInstance().recordExc(cte);
			return str;

		}catch (Exception e){
			MaStation.getInstance().setIsConnected(false);
			e.printStackTrace();
			e.getMessage();
			e.getCause();
			e.getLocalizedMessage();
			MaStation.getInstance().recordExc(e);
		}
//		if(str ==null || str.equals("")){
//			return "{}";
//		}
		return str;
		
	}

	public String ftpFile(Context context, String fileName, String title){
		String result = "";
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		fileName = fileName.replace(savePathString, "");
		bundle.putInt("isLPorOthers", -1);
		intent.setClass(context, SeUploading.class);
		bundle.putInt("OthorLP", 0);
		bundle.putString("setfilename", fileName);
		bundle.putInt("setfiletype", 1);
		bundle.putString("setTitle", title);
		intent.putExtras(bundle);
		context.startService(intent);

		return result;
	}


	//请求本身就是异步线程
	public String postImg(String url, Object dto,boolean isCacheHttpData)  {
		String str = "";
		try{
			Properties tProperties = (Properties) dto;
			String img = (String) tProperties.get("imgData");
			String photoName = (String) tProperties.get("photoName");

			if(img!= null && img.length() > 1024){
				int len = img.length();
				int parts = len / 1024 +1;//整除加取余部分
//				System.out.println(parts);
				for(int i=0 ; i < parts; i++){
					String imgData = "";
//					System.out.println("部分：" + i);
					if(i == (parts -1)){
						imgData = img.substring(i*1024, len );
					}else{
						imgData = img.substring(i*1024, (i+1)*1024);
					}
//					System.out.println("内容：\n" + imgData);
				}

			}
			return str;
		}catch (Exception e){
			e.printStackTrace();
			MaStation.getInstance().recordExc(e);
		}
		return str;
	}

	public String postFile( String url, Object dto,boolean isCacheHttpData) throws IOException {

		if(dto == null){
			throw new IllegalArgumentException("Post��������ʵ�岻��Ϊ�գ�");
		}
		if(url == null || url.length() <=0){
			throw new IllegalArgumentException("Post�����URL����Ϊ�գ�");
		}
		String str = "";
		try {
			HttpClient httpClient = HttpHelper.getHttpClient(false);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,8000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,8000);
			HttpPost postMethod = new HttpPost(url);

			postMethod.setEntity(new StringEntity(JSON.toJSONString(dto), "utf-8"));

			HttpResponse response = httpClient.execute(postMethod);

			str = EntityUtils.toString(response.getEntity(), "UTF-8");
			if(!"".equals(str)){
				MaStation.getInstance().setIsConnected(true);
			}
		}catch (HttpHostConnectException httpe){
			MaStation.getInstance().recordExc(httpe);
			try {
				if(isCacheHttpData && !url.contains("heartbeat")){//请求无返回结果，即代表网络不通
//				if(e.getCause().toString().contains("Network is unreachable")){//网络不通的异常
					CacheDto cacheDto = new CacheDto();
					cacheDto.setUrl(url);
					cacheDto.setDto(dto);
					cacheDto.setTime(System.currentTimeMillis());
					HttpCacheService.db.addData(cacheDto);
					MaStation.getInstance().setIsConnected(false);
					MaStation.getInstance().record("cache", JSON.toJSONString(cacheDto));
					return "cache";
//					Message msg = new Message();
//					msg.what = Constant.HTTP_CACHE_SIGN;
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("cacheDto", cacheDto);
//					msg.setData(bundle);
//					handler.sendMessage(msg);
//				}
				}
			}catch (Exception e){
				MaStation.getInstance().recordExc(e);
			}

		}catch (RuntimeException rune){
			MaStation.getInstance().setIsConnected(false);
			rune.printStackTrace();
			MaStation.getInstance().recordExc(rune);
		}catch (ConnectTimeoutException cte){
			cte.printStackTrace();
			MaStation.getInstance().setIsConnected(false);
			MaStation.getInstance().recordExc(cte);
			return str;

		}catch (Exception e){
			MaStation.getInstance().setIsConnected(false);
			e.printStackTrace();
			e.getMessage();
			e.getCause();
			e.getLocalizedMessage();
			MaStation.getInstance().recordExc(e);
		}
//		if(str ==null || str.equals("")){
//			return "{}";
//		}
		return str;

	}

	/**
	 * inputStream转换byte数组
	 * @param input
	 * @return 流是从当前位置开始读取的
	 * @throws Exception
	 */
	private static byte[] transformInputstream(InputStream input)throws Exception
	{
		byte[] byt= null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b=0;
		b = input.read();
		while( b != -1)
		{
			baos.write(b);
			b = input.read();
		}
		byt = baos.toByteArray();
		return byt;
	}

	public static PostUtil getInstance() {
		if(instance == null){
			instance = new PostUtil();
		}
		return instance;
	}
	
	
//	public static String toString(HttpEntity entity, Charset defaultCharset)
//			throws IOException, ParseException {
//		Args.notNull(entity, "Entity");
////		InputStream instream = entity.getContent();
////		if (instream == null)
////			return null;
//		try {
////			Asrgs.check(entity.getContentLength() <= 2147483647L,
////					"HTTP entity too large to be buffered in memory");
////
////			int i = (int) entity.getContentLength();
////			if (i < 0) {
////				i = 4096;
////			}
////			Charset charset = null;
////			try {
////				ContentType contentType = ContentType.get(entity);
////				if (contentType != null)
////					charset = contentType.getCharset();
////			} catch (UnsupportedCharsetException ex) {
////				throw new UnsupportedEncodingException(ex.getMessage());
////			}
////			if (charset == null) {
////				charset = defaultCharset;
////			}
////			Reader reader = new InputStreamReader(instream, charset);
////			CharArrayBuffer buffer = new CharArrayBuffer(0);
////			char[] tmp = new char[1024];
////
////			int l;
////			while ((l = reader.read(tmp)) != -1) {
////				buffer.append(tmp, 0, l);
////			}
////			String str = buffer.toString();
//			String str = EntityUtils.toString(entity);
//			return str;
////		} finally {
////			instream.close();
//		}catch (Exception e){
//			e.printStackTrace();
//			return "";
//		}
//	}

}

