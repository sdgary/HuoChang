package byxx.android.wj.http.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	 /**
     * 
     */
    public static final String DATABASE_NAME = "httpcache4.db";

    /**
     *
     */
    public static final int DATABASE_VERSION = 1;

    /**
     *
     *
     * @param context
     */
    public DatabaseHelper(Context context)
    {
        // CursorFactory
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 
     *
     * @param context
     *           
     * @param name
     *            
     * @param factory
     *           
     * @param version
     *          
     */
	public DatabaseHelper(Context context, String databaseName, CursorFactory object,
			int databaseVersion) {
		 // 
        super(context, databaseName, object, databaseVersion);
	}
	
	/**
     * 
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE cache (uuid VARCHAR(20),url VARCHAR(20), dto VARCHAR(20), time VARCHAR(30))");
	}


	/**
     * version
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 
        db.execSQL("ALTER TABLE cache ADD COLUMN other STRING");
	}
}
