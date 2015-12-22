package byxx.android.huochang.checkBox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ab.util.AbStrUtil;
import com.ab.util.AbViewUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.picture.LoadImage;
import byxx.android.huochang.picture.PictureUtil;
import byxx.android.huochang.util.ByDialog;
import byxx.android.huochang.util.ByString;

public class CheckBoxActivity extends Activity {
//    private Button upload;
    private LinearLayout takePhotoLayout;
//    private Button takePhoto;
    private EditText mEditText;
    private Context mContext;
    private Bitmap photo;
    private String pgname = null;
    static String savePathString = Environment.getExternalStorageDirectory()
            + "/" + "DCIM" + "/" + "100ANDRO/"; // Environment.getExternalStorageDirectory()+"/"+"DCIM"+"/"+"Camera/";
    /* 用来标识请求gallery的activity */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;
    private String SD_CARD_TEMP_DIR;
    private static final int CAMERACODE1 = 11;
    Update update = null;
    ProgressDialog dialog = null;
    /**
     * 设置msg和cancel listener
     * @return
     */
    public ProgressDialog getProgressDialog() {
        if(dialog == null){
            dialog = new ProgressDialog(CheckBoxActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("正在加载数据...");
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if(update != null){
                        update.cancel(true);
                    }
                }
            });
        }
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        mContext = this;
//        upload = (Button) findViewById(R.id.upload);
        takePhotoLayout = (LinearLayout) findViewById(R.id.takePhotoLayout);
//        takePhoto = (Button) findViewById(R.id.take);
        mEditText = (EditText) findViewById(R.id.editText);

        /**
         * 拍照按钮
         */
        takePhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditText.getText().toString().trim() != null && !mEditText.getText().toString().trim().equals("")){
                    camra();
                }else{
                    ByDialog.showMessage(mContext,"请输入需要验证的箱号！");
                }
            }
        });

        //打开相册选择图片上传
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 从相册中去获取
//                try {
//                    if(mEditText.getText().toString().trim() != null && mEditText.getText().toString().trim() != ""){
//                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//                        intent.setType("image/*");
////                    intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
//                        startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
//                    }
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(mContext,"没有找到照片", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_box, menu);
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

    /**
     * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况,
     * 他们启动时是这样的startActivityForResult
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
        if (resultCode != RESULT_OK){
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA:
                Uri uri = mIntent.getData();
                String fileName = "DL_"+new Timestamp(System.currentTimeMillis()) + mEditText.getText().toString().trim();
                String currentFilePath = getPath(uri);
                String content;

                content = PictureUtil.bitmapToString(currentFilePath);

                Gson gson = new Gson();
                String json = gson.toJson(content);
                new Update().execute(fileName, json);
//                return MaStation.getInstance().getMaWebService().saveNormalImg(fileName,json);//helper.sendPost(json);// 使用http post
//            Toast.makeText(mContext,"相片文件路径：" + currentFilePath, Toast.LENGTH_SHORT).show();
                break;
            case CAMERACODE1:
                try {
                    File f = new File(SD_CARD_TEMP_DIR);
                    save(SD_CARD_TEMP_DIR);
                }catch (Exception e) {

                }
                break;

        }
    }

    private void save(String mCurrentPhotoPath) {

        if (mCurrentPhotoPath != null) {

            try {
                File f = new File(mCurrentPhotoPath);
                Bitmap bm = PictureUtil.getSmallBitmap(mCurrentPhotoPath);
                LoadImage loadImage = new LoadImage(f.getPath(),bm);
//							 imgAdapter1.addPhoto(loadImage);
//			                 fileNameList1.add(loadImage);
                FileOutputStream fos = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.JPEG, 20, fos);
//                ByDialog.showMessage(mContext, "图片保存成功!");
//                Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();
                if(bm!=null&&bm.isRecycled())
                {
                    bm.recycle();
                }
//						 photoName的产生方法是勾计划正文的主键,gjhzwDto.getGjhId+"@"+gjhzwDto.getSwh
//                PostUtil.getInstance().ftpFile(CheckBoxActivity.this, mCurrentPhotoPath, "image");
                update = new Update();
                update.execute(loadImage.getFileName());
            } catch (Exception e) {

            }

        } else {
            ByDialog.showMessage(mContext, "请先点击拍照按钮拍摄照片!");
//            Toast.makeText(this, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从相册得到的url转换为SD卡中图片路径
     */
    public String getPath(Uri uri) {
        if(AbStrUtil.isEmpty(uri.getAuthority())){
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }

    //拒绝移箱
    private class Update extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String filePath = params[0];
            String photoName ="DL_"+ ByString.getTimeStr(java.lang.System.currentTimeMillis(), "yyyyMMdd")+ mEditText.getText().toString().trim();;;
            String content;

            content = PictureUtil.bitmapToString(filePath);
            Gson gson = new Gson();
            String json = gson.toJson(content);
            System.out.println(json);
            Log.e("imgData" , json);
//					public static final int PHOTO_TYPE_IMPORTANT = 11; // 重点车
//					public static final int PHOTO_TYPE_BROKEN = 12; // 坏车
//					public static final int PHOTO_TYPE_MEETING = 13; // 车前会
            return MaStation.getInstance().getMaWebService().saveNormalImg(photoName, json);
        }

        @Override
        protected void onPreExecute() {
            getProgressDialog().show();
        }

        @Override
        protected void onPostExecute(String result) {
            try{
                if(result != null && !result.equals("[]") && !"".equals(result)){
                    if("cache".equals(result)){
                        ByDialog.showMessage(mContext, "网络断开，操作已缓存！");
                    }else{
                        StandReturnInfo info = JSON.parseObject(result, StandReturnInfo.class);
                        if(info != null && info.isSuccess()){
//                    Toast.makeText(mContext,"上传成功！", Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                            dialog.setTitle("成功！");
                            dialog.setMessage("当前上传成功！");

                            dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.create().show();

                        }else {
//                    Toast.makeText(mContext,"上传失败！", Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                            dialog.setTitle("失败！");
                            dialog.setMessage("当前上传失败！");

                            dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.create().show();
                        }
                    }

                }else{
                    ByDialog.showMessage(mContext, "网络信号不好！");
//                Toast.makeText(mContext,"网络无反馈！", Toast.LENGTH_SHORT).show();
                }

                getProgressDialog().cancel();
            }catch (Exception e){
                MaStation.getInstance().recordExc(e);
            }
        }
    }
    private void destoryBimap() {
        if (photo != null && !photo.isRecycled()) {
            photo.recycle();
            photo = null;
        }
    }

    //?拍照界面
    public void camra(){
        destoryBimap();
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            pgname = null;
            pgname = "DL_"+ ByString.getTimeStr(java.lang.System.currentTimeMillis(), "yyyyMMddHHmmss")+ mEditText.getText().toString().trim();;
            pgname += ".jpg";
            SD_CARD_TEMP_DIR =  savePathString+ File.separator + pgname;
            Intent takePictureFromCameraIntent = new Intent(  MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureFromCameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
            takePictureFromCameraIntent.putExtra( android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SD_CARD_TEMP_DIR)));
            startActivityForResult(takePictureFromCameraIntent, CAMERACODE1);
        } else {
            ByDialog.showMessage(mContext, "请插入SD卡");
//            Toast.makeText(mContext,"请插入SD卡", Toast.LENGTH_LONG).show();
        }
    }
}
