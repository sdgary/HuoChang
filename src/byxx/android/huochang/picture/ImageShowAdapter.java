package byxx.android.huochang.picture;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore.LoadStoreParameter;
import java.util.ArrayList;
import java.util.List;





import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import byxx.android.huochang.AppException;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.common.BitmapManager;
import byxx.android.huochang.common.URLs;
import byxx.android.huochang.picture.ImageAdapter.ViewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageShowAdapter extends BaseAdapter {  
	  
    private List<String> picList = new ArrayList<String>();    //图片集合  
    Context context;
    private LayoutInflater inflater;  
	private BitmapManager 	bmpManager;
    public ImageShowAdapter(Context mContext){  
        inflater = LayoutInflater.from(mContext);  
        this.context = mContext;
    	this.bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
    	
    }  
      
    @Override  
    public int getCount() {  
        return picList.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        return picList.get(position);  
    }  



    /** 
     * 添加图片 
     * @param bitmap 
     */  
    public void addPhoto(String loadImage){  
        picList.add(loadImage);  
        notifyDataSetChanged();  
    }  
    
    public void addAllPhoto(List<String> list){
    	picList.clear();
    	picList.addAll(list);
    	notifyDataSetChanged();  
    }
 
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  

	@Override  
    public View getView(int position, View convertView, ViewGroup parent) {  

		 ViewHolder holder;
//		if(convertView == null){
		      	holder = new ViewHolder();
	            convertView = inflater.inflate(R.layout.showimg_item, null);  
	            holder.image = (ImageView)convertView.findViewById(R.id.ItemImage);  
	            convertView.setTag(holder);
//		    }else{
//		    	holder = (ViewHolder) convertView.getTag();
//		    }
	     Log.v("show", "position ------------>>"+position+"");
        if(picList.get(position).toString() != null){
        	bmpManager.loadBitmap(MaStation.getInstance().getUser().getImgUrlMini()+picList.get(position).toString(), holder.image, BitmapFactory.decodeResource(context.getResources(), R.drawable.shuaxin2),context);
        }

        return convertView;  
    }  
      
    public static class ViewHolder{  
        public ImageView image;     //要显示的图片  
    }
    

	
}  