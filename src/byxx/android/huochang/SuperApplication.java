package byxx.android.huochang;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.util.Log;
/**
 * SuperApplication类（储存每一个Activity，并实现关闭所有Activity的操作）
 * 在每一个Activity中的onCreate方法里添加该Activity到MyApplication对象实例容器中
 * SuperApplication.getInstance().addActivity(this);
 * 在需要结束所有Activity的时候调用exit方法
 * SuperApplication.getInstance().exit();
 * @author Way
 */
public class SuperApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>(); 
	private List<Service> serviceList = new LinkedList<Service>();
	private static SuperApplication instance;
    private SuperApplication()
    {
    }
     //单例模式中获取唯一的MyApplication实例 
     public static SuperApplication getInstance()
     {
                    if(null == instance)
                  {
                     instance = new SuperApplication();
                  }
         return instance;             
     }
     
     //添加Activity到容器中
     public void addActivity(Activity activity){
//    	 Log.v("load", " ------add activity( ) "+activity.getLocalClassName());
    		 activityList.add(activity);
     }
     
     
     public void addService(Service service){
//    	 Log.v("load", " ------service  add ");
    	 serviceList.add(service);
     }
     //遍历所有Activity并finish
     public void exit()
     {
//    	 Log.v("load", " ------ exit() ");
    	 for(Activity activity:activityList)
    	 {		
//                Log.v("load", " ------ "+activity.getLocalClassName()+" finish ");
                activity.finish();
                }
    	 for(Service service:serviceList){
//    		 Log.v("load", " ------service  finish ");
    		 service.stopSelf();
    	 }
    	 System.exit(0);
    }

}
