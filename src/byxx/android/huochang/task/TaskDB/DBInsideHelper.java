package byxx.android.huochang.task.TaskDB;

import android.content.Context;

import com.ab.db.orm.AbDBHelper;

import byxx.android.huochang.http.cache.CacheDto2;
import byxx.android.huochang.task.GdmsZxzypbDTO;

/**
 * 
 * 名称：DBInsideHelper.java
 * 描述：手机data/data下面的数据库
 */
public class DBInsideHelper extends AbDBHelper {
	// 数据库名
	private static final String DBNAME = "huochang.db";
    
    // 当前数据库的版本
	private static final int DBVERSION = 1;
	// 要初始化的表
	private static final Class<?>[] clazz = {GdmsZxzypbDTO.class,CacheDto2.class};

	public DBInsideHelper(Context context) {
		super(context, DBNAME, null, DBVERSION, clazz);
	}

}



