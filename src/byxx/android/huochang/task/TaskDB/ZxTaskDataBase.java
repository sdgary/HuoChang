package byxx.android.huochang.task.TaskDB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import byxx.android.huochang.MaStation;
import byxx.android.huochang.task.GdmsZxzypbDTO;
import byxx.android.huochang.util.CSort;

/**
 * Created by wgary on 15/11/13.
 */
public class ZxTaskDataBase {
    /**
     * 缓存对象
     * @param obj
     */
    public static void saveData(GdmsZxzypbDTO obj){
        MaStation.getInstance().getAcZxTaskData().put(obj.getZypbId(), obj);
    }

    public  static List<GdmsZxzypbDTO> queryData(){
        List<GdmsZxzypbDTO> data = new ArrayList<GdmsZxzypbDTO>();
        Iterator iterator = MaStation.getInstance().getAcZxTaskData().entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, GdmsZxzypbDTO> entry = (Map.Entry<Integer, GdmsZxzypbDTO>) iterator.next();
            data.add(entry.getValue());
        }
        CSort.getInstance().sort(data, GdmsZxzypbDTO.class, "zypbId", CSort.SORT_ASC);
        return data;
    }

    /**
     * 更新数据
     * 描述：TODO
     * @param obj
     */
    public static void updateData(GdmsZxzypbDTO obj){
        MaStation.getInstance().getAcZxTaskData().put(obj.getZypbId(), obj);
    }

    /**
     *
     * 描述：根据ID查询数据
     * @param id
     * @return
     */
    public static GdmsZxzypbDTO queryDataById(int id){
        return MaStation.getInstance().getAcZxTaskData().get(id);
    }

}
