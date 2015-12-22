package byxx.android.huochang.picture;

import java.security.KeyStore.LoadStoreParameter;
import java.util.ArrayList;
import java.util.List;

import byxx.android.huochang.R;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {  
	  
    private List<LoadImage> picList = new ArrayList<LoadImage>();    //图片集合  
    private List<String> picNumber = new ArrayList<String>();       //选中图片的位置集合  
      
    private LayoutInflater inflater;  
      
    public ImageAdapter(Context mContext){  
        inflater = LayoutInflater.from(mContext);  
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
     * 添加选中状态的图片位置 
     * @param position 
     */  
    public void addNumber(String position){  
        picNumber.add(position);  
    }  
    /** 
     * 去除已选中状态的图片位置 
     * @param position 
     */  
    public void delNumber(String position){  
        picNumber.remove(position);  
    }  
    /** 
     * 清空已选中的图片状态 
     */  
    public void clear(){  
        picNumber.clear();  
        notifyDataSetChanged();   
    }  
    /** 
     * 添加图片 
     * @param bitmap 
     */  
    public void addPhoto(LoadImage loadImage){  
        picList.add(loadImage);  
        notifyDataSetChanged();  
    }  
    
    public void addAllPhoto(List<LoadImage> list){
    	picList.clear();
    	picList.addAll(list);
    	picNumber.clear();  
    	 notifyDataSetChanged();  
    }
    public void delAllPhoto(){
    	picList.clear();
    	picNumber.clear();  
    	notifyDataSetChanged();  
    }
    /** 
     * 删除图片 
     * @param loadimgLs 
     */  
    public void deletePhoto(List<LoadImage> loadimgLs){  
        for(LoadImage img:loadimgLs){  
            if(picList.contains(img)){  
                picList.remove(img);  

            } 
        }  
        picNumber.clear();  
        notifyDataSetChanged();  
    }  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @SuppressWarnings("deprecation")
	@Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        
		final ViewHolder holder;
//		if(convertView == null){
		      convertView = inflater.inflate(R.layout.task_item, null);
		      holder = new ViewHolder();
	            convertView = inflater.inflate(R.layout.picturescan_item, null);  
	            holder.image1 = (ImageView)convertView.findViewById(R.id.scan_img);  
	            holder.image2 = (ImageView)convertView.findViewById(R.id.scan_select);  
	            convertView.setTag(holder);
//		    }else{
//		    	holder = (ViewHolder) convertView.getTag();
//		    }

//        Drawable bit = new BitmapDrawable(picList.get(position).getBitmap());
        if(picList.get(position).getBitmap() != null){
//        	holder.image1.setBackgroundDrawable(bit);  
        	holder.image1.setImageBitmap(picList.get(position).getBitmap());
        }else{
        	holder.image1.setImageBitmap(null);
        }
//        holder.image1.setImageBitmap(picList.get(position).getBitmap());
        if(picNumber.contains(""+position)){    //如果该图片在选中状态，使其右上角的小对号图片显示，并且添加边框。  
            holder.image2.setVisibility(View.VISIBLE);  
//            holder.image2.setImageResource(R.drawable.border);  
//            holder.image2.setImageResource(R.drawable.border);  
        }else{  
            holder.image2.setVisibility(View.GONE);  
        }  
        return convertView;  
    }  
      
    public static class ViewHolder{  
        public ImageView image1;     //要显示的图片  
        public ImageView image2;     //图片右上角的小对号图片(标示选中状态的玩意)  
    }  
}  