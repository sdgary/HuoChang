package byxx.android.wj.http.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.alibaba.fastjson.JSON;

import byxx.android.huochang.MaStation;

public class CacheDB{
	private static DatabaseHelper databaseHelper;
	private Context context;
	
	public CacheDB (Context context) {
		this.context = context;
		databaseHelper = new DatabaseHelper(context);
	}

	/**
     * ������
     */
    public void addData(CacheDto dto){
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();//
            ContentValues contentValues = new ContentValues();
            contentValues.put("url", dto.getUrl());
            contentValues.put("uuid", dto.getUuid());
            contentValues.put("dto",JSON.toJSON(dto.getDto()).toString());
            contentValues.put("time", String.valueOf(dto.getTime()));

            db.insert("cache", null, contentValues);
        }catch (Exception e){
            MaStation.getInstance().recordExc(e);
        }
    }
    
    /**
     * ɾ�����
     *
     * @param id
     */
    public void deleteData(String uuid){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();//
        db.delete("cache", "uuid=?", new String[]{
            uuid
        });
    }
    
    /**
     * ��ѯ���
     */
    public List<CacheDto> queryData(String uuid){
    	List<CacheDto> list = new ArrayList<CacheDto>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();//
        /**
         * ��һ�������ʾ��?/�ڶ��������ʾ������Ҫ���ص��ֶ�/����������ʾSQL�������������ֵ���?
         * /���ĸ�����ռλ���?/����������ʾ��?
         * ����Ϊnull/����������ʾSQL����е�having������Ϊnull/���߸������ʾ�������򣬿���Ϊnull
         */
        Cursor cursor = db.query("cache", new String[]{
                "uuid", "url", "dto","time"
        }, "uuid=?", new String[]{
            uuid
        }, null, null, null);
        // ����¼?
        if (cursor.moveToNext()){
        	CacheDto cacheDto = new CacheDto();
        	cacheDto.setUrl(cursor.getString(cursor.getColumnIndex("url")));
        	String proJson = cursor.getString(cursor.getColumnIndex("dto"));
        	cacheDto.setDto(JSON.parseObject(proJson, Properties.class));
        	cacheDto.setUuid(cursor.getString(cursor.getColumnIndex("uuid"))); // ���鵽���ֶΣ�����person
            cacheDto.setTime(Long.parseLong(cursor.getString(cursor.getColumnIndex("time"))));
            list.add(cacheDto);
        }
        cursor.close();// �α�ر�
        return list;
    }
	
    /**
     * ��ѯ���
     */
    public List<CacheDto> queryData(){
    	List<CacheDto> list = new ArrayList<CacheDto>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();// ������?��?��ѯ���?
        /**
         * ��һ�������ʾ��?/�ڶ��������ʾ������Ҫ���ص��ֶ�/����������ʾSQL�������������ֵ���?
         * /���ĸ�����ռλ���?/����������ʾ��?
         * ����Ϊnull/����������ʾSQL����е�having������Ϊnull/���߸������ʾ�������򣬿���Ϊnull
         */
        Cursor cursor = db.query("cache", new String[]{
                "uuid", "url", "dto", "time"
        }, null,null, null, null, null);

        // ����¼?
        if (cursor.moveToNext()){
        	CacheDto cacheDto = new CacheDto();
        	cacheDto.setUrl(cursor.getString(cursor.getColumnIndex("url")));
        	String proJson = cursor.getString(cursor.getColumnIndex("dto"));
        	cacheDto.setDto(JSON.parseObject(proJson, Properties.class));
        	cacheDto.setUuid(cursor.getString(cursor.getColumnIndex("uuid"))); // ���鵽���ֶΣ�����person
            cacheDto.setTime(Long.parseLong(cursor.getString(cursor.getColumnIndex("time"))));
        	list.add(cacheDto);
        }
        cursor.close();// �α�ر�
        return list;
    }
    
    /**
     * ��ȡ��¼����
     *
     * @return
     */
    public long countData(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("person", new String[]
        {
            "*"
        }, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();// �α�ر�
        return count;
    }
}
