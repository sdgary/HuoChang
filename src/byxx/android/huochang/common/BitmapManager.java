package byxx.android.huochang.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import byxx.android.huochang.AppException;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
/**
 * 异步线程加载图片工具类
 * 使用说明：
 * BitmapManager bmpManager;
 * bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.loading));
 * bmpManager.loadBitmap(imageURL, imageView);
 * 
 * @version 1.0
 * @created 
 */
public class BitmapManager {  
	  
    private static HashMap<String, SoftReference<Bitmap>> cache;  
    private static ExecutorService pool;  
    private static Map<ImageView, String> imageViews;
    private Bitmap defaultBmp;  
    
    static {  
        cache = new HashMap<String, SoftReference<Bitmap>>();  
        pool =  Executors.newCachedThreadPool();//固定线程池
        imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
      
    }  
    
    public BitmapManager(){}
    
    public BitmapManager(Bitmap def) {
    	this.defaultBmp = def;
    }
    
    /**
     * 设置默认图片
     * @param bmp
     */
    public void setDefaultBmp(Bitmap bmp) {  
    	defaultBmp = bmp;  
    }   
  
    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void loadBitmap(String url, ImageView imageView,Context context) {  
    	loadBitmap(url, imageView, this.defaultBmp, 0, 0, context);
    }
	
    /**
     * 加载图片-可设置加载失败后显示的默认图片
     * @param url
     * @param imageView
     * @param defaultBmp
     */
    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp,Context context) {  
    	loadBitmap(url, imageView, defaultBmp, 0, 0,context);
    	 Log.v("show", "url ------------>>"+url+"");
    }
    
    /**
     * 加载图片-可指定显示图片的高宽
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp, int width, int height,Context context) {  
        imageViews.put(imageView, url);  
        Bitmap bitmap = getBitmapFromCache(url);  
        if (bitmap != null) {  
			//显示缓存图片
       	 Log.v("show", "//显示缓存图片"+url+"");
            imageView.setImageBitmap(bitmap);  
        } else {  
        	//加载SD卡中的图片缓存
//        	String filename = FileUtils.getFileName(url);
//        	String filepath = imageView.getContext().getFilesDir() + File.separator + filename;
//    		File file = new File(filepath);
//    		if(file.exists()){
//				//显示SD卡中的图片缓存
//    			Log.v("show", "//显示SD卡中的图片缓存"+url+"");
//    			Bitmap bmp = ImageUtils.getBitmap(imageView.getContext(), filename);
//        		imageView.setImageBitmap(bmp);
//        	}else{
				//线程加载网络图片
        		Log.v("show", "//线程加载网络图片"+url+"");
        		imageView.setImageBitmap(defaultBmp);
        		queueJob(url, imageView, width, height,context);
//        	}
        }  
    }  
  
    /**
     * 从缓存中获取图片
     * @param url
     */
    public Bitmap getBitmapFromCache(String url) {  
    	Bitmap bitmap = null;
        if (cache.containsKey(url)) {  
            bitmap = cache.get(url).get();  
        }  
        return bitmap;  
    }  
    
    /**
     * 从网络中加载图片
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void queueJob(final String url, final ImageView imageView, final int width, final int height,final Context context) {  
        /* Create handler in UI thread. */  
        final Handler handler = new Handler() {  
            public void handleMessage(Message msg) {  
                String tag = imageViews.get(imageView);  
                if (tag != null && tag.equals(url)) {  
                    if (msg.obj != null) {  
                        imageView.setImageBitmap((Bitmap) msg.obj);
       
                        try {
                        	//向SD卡中写入图片缓存
							ImageUtils.saveImage(imageView.getContext(), FileUtils.getFileName(url), (Bitmap) msg.obj);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    } 
                }  
            }  
        };  
  
        pool.execute(new Runnable() {   
            public void run() {  
                Message message = Message.obtain();  
                message.obj = downloadBitmap(url, width, height,context);
                handler.sendMessage(message);  
            }  
        });  
    } 
  
    /**
     * 下载图片-可指定显示图片的高宽
     * @param url
     * @param width
     * @param height
     */
    private Bitmap downloadBitmap(String url, int width, int height,Context context) {   
        Bitmap bitmap = null;
        try {
			//http加载图片
			bitmap =ApiClient.getNetBitmap(url,context);
			if(width > 0 && height > 0) {
				//指定显示图片的高宽
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			} 
			//放入缓存
			//cache.put(url, new SoftReference<Bitmap>(bitmap));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return bitmap;  
    }  
    

    
    
}