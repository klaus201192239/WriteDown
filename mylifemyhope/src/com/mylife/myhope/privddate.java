package com.mylife.myhope;

import com.mylife.util.DBHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class privddate extends ContentProvider {  
	
    private final static String TABLE_NAME = "mything";  
    private final static String TABLE_COLUMN_ID = "id";  
    private final static String TABLE_COLUMN_NAME = "name";  
      
    private final static String AUTHORITY = "com.mylife.myhope.privddate";  
    private final static String PERSONS_PATH = "mything";  
    private final static String PERSON_PATH = "mything/#";//#表示任意的数字  
  
    private final static int PERSONS = 1;  
    private final static int PERSON = 2;  
      
    private final static UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);  
    static{  
        //UriMatcher类的一个方法  
        sMatcher.addURI(AUTHORITY, PERSONS_PATH, PERSONS);  
        sMatcher.addURI(AUTHORITY, PERSON_PATH, PERSON);  
    }  
      
    private DBHelper mHelper = null;  
  
    @Override  
    public boolean onCreate() {  
        
        return true;  
    }  
  
    /* 
     * 查询数据库中的信息 
     */  
    @Override  
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
    	
    	mHelper = new DBHelper(this.getContext());  
    	
    	mHelper.CreatOrOpen("mylifemyhope");
    	
    	Cursor cor=mHelper.selectInfo("select * from mything;");
    	
    	mHelper.closeDB();
    	
    	return cor;
    }  
  
    /* 
     * 向数据库中插入一条信息 
     */  
    @Override  
    public Uri insert(Uri uri, ContentValues values) {  
        return ContentUris.withAppendedId(uri, 10);  
    }  
      
    /* 
     * 删除数据库中的信息 
     */  
    @Override  
    public int delete(Uri uri, String selection, String[] selectionArgs) {  
        //此方法同query方法  
      return 1;
    }  
      
    /* 
     *更新数据库中的信息 
     */  
    @Override  
    public int update(Uri uri, ContentValues values, String selection,  
            String[] selectionArgs) {  
       return 1;
    }  
      
    /* 
     * 取得Uri所对应的值的类型，是集合型或者非集合型 
     * 集合型需要在其返回值前添加“vnd.android.cursor.dir/” 
     * 非集合型需要在其返回值前添加"vnd.android.cursor.item/" 
     */  
    @Override  
    public String getType(Uri uri) {  
        switch(sMatcher.match(uri)){  
        case PERSONS:  
            //集合类型，返回值前面一般是固定的，后面的值是自己添加的，也可以加上包路径  
            return "vnd.android.cursor.dir/" + TABLE_NAME;  
        case PERSON:  
            //非集合类型数据，返回值前面一般是固定的，后面的值是自己添加的，也可以加上包路径  
            return "vnd.android.cursor.item/" + TABLE_NAME;  
        default:  
            throw new IllegalArgumentException("Unknown Uri : " + uri);  
    }  
    }

	
}  