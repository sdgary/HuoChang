package byxx.android.huochang.task.TaskDB;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import byxx.android.huochang.task.GdmsZxzypbDTO;

/**
 * 名称：GdmsZxzypbDTO.java
 * 描述：本地数据库 在data下面
 */
public class TaskDao extends AbDBDaoImpl<GdmsZxzypbDTO> {
	public TaskDao(Context context) {
		super(new DBInsideHelper(context),GdmsZxzypbDTO.class);
	}
}
