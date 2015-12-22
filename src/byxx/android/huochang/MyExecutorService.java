package byxx.android.huochang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutorService {
	private static MyExecutorService instance;
	ExecutorService pool;
	
	public MyExecutorService() {
		// TODO Auto-generated constructor stub
//		int cpuNums = Runtime.getRuntime().availableProcessors();
		pool = Executors.newCachedThreadPool();
	}

    //单例模式中获取唯一ThreadPool的实例 
    public static MyExecutorService getInstance()
    {
          if(null == instance)
            {
               instance = new MyExecutorService();
             }
        return instance;             
    }
	
	/**
	 * 	将线程放入池中进行执行
	 * @param t	创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
	 */
	public void addRunnable(Runnable r){
		pool.execute(r);
	}
	
	public void addThread(Thread t){
		pool.execute(t);
	}

	public void shutdown(){
		pool.shutdown();
	}
	
}
