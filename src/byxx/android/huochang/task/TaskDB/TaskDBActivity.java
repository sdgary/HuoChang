package byxx.android.huochang.task.TaskDB;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.db.storage.AbSqliteStorage;
import com.ab.db.storage.AbSqliteStorageListener;
import com.ab.db.storage.AbStorageQuery;
import com.ab.view.titlebar.AbTitleBar;

import java.util.ArrayList;
import java.util.List;

import byxx.android.huochang.R;
import byxx.android.huochang.task.GdmsZxzypbDTO;

public class TaskDBActivity extends AbActivity {

    //定义数据库操作实现类
    private TaskDao taskDao = null;

    //数据库操作类
    private AbSqliteStorage mAbSqliteStorage = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化AbSqliteStorage
        mAbSqliteStorage = AbSqliteStorage.getInstance(this);

        //初始化数据库操作实现类
        taskDao = new TaskDao(TaskDBActivity.this);

//        userList = new ArrayList<LocalUser>();

        //增加一条数据到数据库
//                    LocalUser u = new LocalUser();
//                    u.setName(name);
//                    saveData(u);
    }

    public void saveData(GdmsZxzypbDTO u){

        //无sql存储的插入
        mAbSqliteStorage.insertData(u, taskDao, new AbSqliteStorageListener.AbDataInsertListener(){

            @Override
            public void onSuccess(long id) {
                //showToast("插入数据成功id="+id);
                queryData();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                showToast(errorMessage);
            }

        });
    }

    public void queryData(){
        //查询数据
        AbStorageQuery mAbStorageQuery = new AbStorageQuery();
        //无sql存储的查询
        mAbSqliteStorage.findData(mAbStorageQuery, taskDao, new AbSqliteStorageListener.AbDataInfoListener(){

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                showToast(errorMessage);
            }

            @Override
            public void onSuccess(List<?> paramList) {
//                userList.clear();
//                if(paramList!=null){
//                    userList.addAll((List<LocalUser>)paramList);
//                }
//                myListViewAdapter.notifyDataSetChanged();
//                checkPageBar();
            }

        });

    }

    public void queryDataCount(){
        //查询数据
        AbStorageQuery mAbStorageQuery = new AbStorageQuery();

        //无sql存储的查询
        mAbSqliteStorage.findData(mAbStorageQuery, taskDao, new AbSqliteStorageListener.AbDataInfoListener(){

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                showToast(errorMessage);
            }

            @Override
            public void onSuccess(List<?> paramList) {
//                if(paramList!=null){
//                    totalCount = paramList.size();
//                }

//                total.setText("总条数:" +String.valueOf(totalCount));
//                current.setText("当前页:" + String.valueOf(pageNum));
//                checkView();
//                mListViewForPage.setVisibility(View.VISIBLE);
            }

        });

    }


    /**
     * 更新数据
     * 描述：TODO
     * @param u
     */
    public void updateData(GdmsZxzypbDTO u){

        //无sql存储的更新
        mAbSqliteStorage.updateData(u, taskDao, new AbSqliteStorageListener.AbDataOperationListener(){

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                showToast(errorMessage);
            }

            @Override
            public void onSuccess(long paramLong) {
                queryData();
            }

        });


    }

    /**
     *
     * 描述：根据ID查询数据
     * @param id
     * @return
     */
    public void queryDataById(int id){

        //条件
        AbStorageQuery mAbStorageQuery = new AbStorageQuery();
        mAbStorageQuery.equals("_id", String.valueOf(id));

        //无sql存储的查询
        mAbSqliteStorage.findData(mAbStorageQuery, taskDao, new AbSqliteStorageListener.AbDataInfoListener(){

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                showToast(errorMessage);
            }

            @Override
            public void onSuccess(List<?> paramList) {
//                if(paramList!=null && paramList.size()>0){
//                    LocalUser u = (LocalUser)paramList.get(0);
//                    showToast("结果：_id:"+u.get_id()+",name:"+u.getName());
//                }
            }

        });

    }

    /**
     *
     * 描述：删除数据
     * @param id
     */
    public void delData(int id){

        //条件
        AbStorageQuery mAbStorageQuery = new AbStorageQuery();
        mAbStorageQuery.equals("_id", String.valueOf(id));

        //无sql存储的删除
        mAbSqliteStorage.deleteData(mAbStorageQuery, taskDao, new AbSqliteStorageListener.AbDataOperationListener(){

            @Override
            public void onSuccess(long paramLong) {
                queryData();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                showToast(errorMessage);
            }

        });

    }


    @Override
    public void finish() {
        //必须要释放
        mAbSqliteStorage.release();
        super.finish();
    }


}
