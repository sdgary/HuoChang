package byxx.android.huochang.http.cache;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;

import byxx.android.huochang.task.TaskDB.DBInsideHelper;

/**
 * Created by wgary on 15/11/13.
 */
public class CacheDAO extends AbDBDaoImpl<CacheDto2> {
    public CacheDAO(Context context) {
        super(new DBInsideHelper(context), CacheDto2.class);
    }
}
