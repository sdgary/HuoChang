package byxx.android.huochang.updatebox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.task.ContainerMiddlePos;
import byxx.android.huochang.task.GdContainerPos;
import byxx.android.huochang.util.ByAlartDialog;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.CSort;
import byxx.android.huochang.util.SortParameter;
import byxx.android.wj.http.cache.LoadingDialog;

public class UpdateBoxActivity extends Activity {
    private Context mContext;
    private EditText editText;
    private Button btn;
    private List<GdContainerPos>  gdList = new ArrayList<GdContainerPos>();//指定股道的箱位信息

    private LinearLayout spinnerLayout;

    private Spinner spinnerTrack;
    private Spinner spinnerArea;
    private Spinner spinnerPos;
    private Spinner spinnerLastPos;

    private ArrayAdapter<String> selectAdapterTrack;
    private ArrayAdapter<String> selectAdapterArea;
    private ArrayAdapter<String> selectAdapterPos;
    private ArrayAdapter<String> selectAdapterLastPos;

    private List<String> areaList = new ArrayList<String>();
    private List<String> posList = new ArrayList<String>();
    private List<String> lastPosList = new ArrayList<String>();

    private String oldXw = "";
    private String oldNum = "";

    ProgressDialog dialogForWaiting;
    UpdateXW updateXW = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_update_box);
        editText = (EditText) findViewById(R.id.editText);
        btn = (Button) findViewById(R.id.commit);
        spinnerLayout = (LinearLayout) findViewById(R.id.spinnerLayout);
        spinnerTrack = (Spinner) findViewById(R.id.spinnerTrack);
        spinnerArea = (Spinner) findViewById(R.id.spinnerArea);
        spinnerPos = (Spinner) findViewById(R.id.spinnerPos);
        spinnerLastPos = (Spinner) findViewById(R.id.spinnerLastPos);

        spinnerWidder();
        refreshPosData();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    btn.setEnabled(true);
                }
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setEnabled(true);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = editText.getText().toString().trim().toUpperCase();
                String xw = spinnerArea.getSelectedItem().toString()+
                        spinnerPos.getSelectedItem().toString()+
                        spinnerLastPos.getSelectedItem().toString();
                if(num != null && !xw.equals("")&&!num.equals("")){
                    if(num.equals(oldNum) && xw.equals(oldXw)){
                        ByDialog.showMessage(mContext, "已提交，请勿重复操纵！");
                        Dialog loadingDialog =  LoadingDialog.create(mContext, "已提交，请勿重复操纵！", LoadingDialog.RIGHT);
                        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        });
                        btn.setEnabled(false);
                    }else{
                        updateXW = new UpdateXW();
                        updateXW.execute(num, xw) ;
                    }
                }else{
                    ByDialog.showMessage(mContext, "请输入箱号！！");
                }

            }
        });
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_box, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void spinnerWidder(){
        selectAdapterTrack = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MaStation.getInstance().getUser().getGdm());
        selectAdapterTrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrack.setAdapter(selectAdapterTrack);
//        spinnerTrack.setVisibility(View.GONE);
        spinnerTrack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                refreshPosData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        selectAdapterArea = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,areaList);
        selectAdapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(selectAdapterArea);
        spinnerArea.setVisibility(View.GONE);
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                initSecondSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        selectAdapterPos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,posList);
        selectAdapterPos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPos.setAdapter(selectAdapterPos);
        spinnerPos.setVisibility(View.GONE);
        spinnerPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                initThirdData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        selectAdapterLastPos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lastPosList);
        selectAdapterLastPos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLastPos.setAdapter(selectAdapterLastPos);
        spinnerLastPos.setVisibility(View.GONE);

    }

    private void refreshPosData(){
//        Toast.makeText(mContext, "调整箱位按钮！", Toast.LENGTH_LONG).show();
        List<GdContainerPos> nList = null;
        nList = MaStation.getInstance().getBoxPositions().get(spinnerTrack.getSelectedItem().toString().trim());
        gdList.clear();
        gdList.addAll(nList);
        initFirstSpinner();
        spinnerTrack.setVisibility(View.VISIBLE);
        spinnerLayout.setVisibility(View.VISIBLE);
    }

    //调整箱位数据加载异步线程
//    private class Refreshdate extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            return MaStation.getInstance().getMaWebService().getContainerPos(spinnerTrack.getSelectedItem().toString());
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if(result != null && !result.equals("[]")){
//                List<GdContainerPos> nList = null;
//                StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
//                if(info != null && info.getData() != null){
//                    nList = JSON.parseArray(info.getData().toString(), GdContainerPos.class);
//                    if(nList != null && nList.size()>0){
//                        gdList.clear();
//                        gdList.addAll(nList);
//                        initFirstSpinner();
//                    }
//                }else{
//                    Dialog loadingDialog = LoadingDialog.create(mContext, "当前用户没有股道码信息！", LoadingDialog.RIGHT);
//                    loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            finish();
//                        }
//                    });
//                    loadingDialog.show();
//                }
//
//            }
//        }
//    }

    //下拉单组合数据加载
    private void initFirstSpinner(){
        spinnerTrack.setVisibility(View.VISIBLE);
        spinnerLayout.setVisibility(View.VISIBLE);
        //first:clear data
        areaList.clear();
        if(gdList != null && gdList.size()>0){

            for (int i = 0; i < gdList.size(); i++) {
                areaList.add(gdList.get(i).getGdm());
            }

            if (!isEmpty(areaList)) {
                //give init data
//				if (spinnerArea.getSelectedItem()==null) {
                spinnerArea.setSelection(0);
//				}
                spinnerArea.setVisibility(View.VISIBLE);
                initSecondSpinner();
            }
            selectAdapterArea.notifyDataSetChanged();
        }

    }

    private void initSecondSpinner(){
        //first:clear data
        posList.clear();
        String area = spinnerArea.getSelectedItem().toString();
        GdContainerPos dto =null;
        List<ContainerMiddlePos> list = null;
        if(gdList != null && gdList.size()>0){

            for (int i = 0; i < gdList.size(); i++) {
                dto = gdList.get(i);
                if (dto.getGdm().equals(area)) {
                    list = dto.getMiddlePos();
                }
            }

            if(list != null && list.size()>0){
                CSort.getInstance().sort(list, new SortParameter("", ContainerMiddlePos.class, new String[]{"middlePosName"}, new Integer[]{CSort.SORT_ASC}));

                for (int i = 0; i < list.size(); i++) {
                    posList.add(list.get(i).getMiddlePosName());
                }
                if (!isEmpty(posList)) {
                    //give init data
//					if (spinnerPos.getSelectedItem()==null) {
                    spinnerPos.setSelection(0);
//					}
                    spinnerPos.setVisibility(View.VISIBLE);
                    initThirdData();
                }else {
                    spinnerLastPos.setVisibility(View.GONE);
                }

                selectAdapterPos.notifyDataSetChanged();
            }
        }
    }

    private void initThirdData(){
        //first:clear data
        lastPosList.clear();
        String area = spinnerArea.getSelectedItem().toString();
        String pos = spinnerPos.getSelectedItem().toString();

        GdContainerPos dto =null;
        List<ContainerMiddlePos> list = null;
        List<String> list2 = null;
        if(gdList != null && gdList.size()>0){

            for (int i = 0; i < gdList.size(); i++) {
                dto = gdList.get(i);
                if (dto.getGdm().equals(area)) {
                    list = dto.getMiddlePos();
                }
            }

            if(list != null && list.size()>0){

                for (int i = 0; i < list.size(); i++) {
                    if (pos.equals(list.get(i).getMiddlePosName())) {
                        list2 = list.get(i).getPos();
                        Collections.sort(lastPosList);
                    }
                }

                if(list2 != null && list2.size()>0){
                    for (int i = 0; i < list2.size(); i++) {
                        lastPosList.add(list2.get(i));
                    }
                    if (!isEmpty(lastPosList)) {
                        //give init data
                        spinnerLastPos.setSelection(0);
                        spinnerLastPos.setVisibility(View.VISIBLE);
                    }
                    selectAdapterLastPos.notifyDataSetChanged();
                }

            }

        }

    }

    /**
     * 判断是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (str==null) {
            return true;
        }else if(str.equals("")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断是否为空
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        if (list!=null && list.size()<=0 ) {
            return true;
        }
        return false;
    }

    private class UpdateXW extends AsyncTask<String, Void, String> {
        String _num;
        String _xw;
        @Override
        protected String doInBackground(String... params) {
            _num = params[0];
            _xw = params[1];
            return MaStation.getInstance().getMaWebService()
                    .updateJzxXw(params[0], params[1]);
        }

        @Override
        protected void onPreExecute() {
            try{
                super.onPreExecute();
                dialogForWaiting = new ProgressDialog(UpdateBoxActivity.this);
                dialogForWaiting.setIndeterminate(true);
                dialogForWaiting.setCancelable(true);
                dialogForWaiting.setCanceledOnTouchOutside(false);
                dialogForWaiting.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialogForWaiting.setMessage("正在提交修改...");
                dialogForWaiting.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (updateXW != null) {
                            updateXW.cancel(true);
                        }

                    }
                });
                dialogForWaiting.show();

            }catch (Exception e){
                MaStation.getInstance().recordExc(e);
            }
        }

        @Override
        protected void onPostExecute(String result) {

            if(result != null && !result.equals("[]") && !"".equals(result)){
                if("false".equals(result)) {
                    ByDialog.showMessage(mContext, "失败！！");
                    Toast.makeText(UpdateBoxActivity.this.getApplicationContext(), "失败！！",
                            Toast.LENGTH_LONG).show();

                }else if("true".equals(result)){
                    ByDialog.showMessage(mContext, "修改成功！");
                    Toast.makeText(UpdateBoxActivity.this.getApplicationContext(), "修改成功！",
                            Toast.LENGTH_LONG).show();
                    oldNum = _num;
                    oldXw = _xw;
                    finish();

                }else if("".contains(result)){
                    ByDialog.showMessage(mContext, "网络异常，操作已缓存！！");
                    Toast.makeText(UpdateBoxActivity.this.getApplicationContext(), "网络异常，操作已缓存！！",
                            Toast.LENGTH_LONG).show();
                }else if("cache".equals(result)){
                    ByDialog.showMessage(mContext, "网络断开，操作已缓存！");
                }
            }else{
                ByDialog.showMessage(mContext, "网络异常");
            }
            if(dialogForWaiting != null){
                dialogForWaiting.cancel();
            }
        }
    }

}
