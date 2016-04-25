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
    private final static String PERSON_PATH = "mything/#";//#��ʾ���������  
  
    private final static int PERSONS = 1;  
    private final static int PERSON = 2;  
      
    private final static UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);  
    static{  
        //UriMatcher���һ������  
        sMatcher.addURI(AUTHORITY, PERSONS_PATH, PERSONS);  
        sMatcher.addURI(AUTHORITY, PERSON_PATH, PERSON);  
    }  
      
    private DBHelper mHelper = null;  
  
    @Override  
    public boolean onCreate() {  
        
        return true;  
    }  
  
    /* 
     * ��ѯ���ݿ��е���Ϣ 
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
     * �����ݿ��в���һ����Ϣ 
     */  
    @Override  
    public Uri insert(Uri uri, ContentValues values) {  
        return ContentUris.withAppendedId(uri, 10);  
    }  
      
    /* 
     * ɾ�����ݿ��е���Ϣ 
     */  
    @Override  
    public int delete(Uri uri, String selection, String[] selectionArgs) {  
        //�˷���ͬquery����  
      return 1;
    }  
      
    /* 
     *�������ݿ��е���Ϣ 
     */  
    @Override  
    public int update(Uri uri, ContentValues values, String selection,  
            String[] selectionArgs) {  
       return 1;
    }  
      
    /* 
     * ȡ��Uri����Ӧ��ֵ�����ͣ��Ǽ����ͻ��߷Ǽ����� 
     * ��������Ҫ���䷵��ֵǰ��ӡ�vnd.android.cursor.dir/�� 
     * �Ǽ�������Ҫ���䷵��ֵǰ���"vnd.android.cursor.item/" 
     */  
    @Override  
    public String getType(Uri uri) {  
        switch(sMatcher.match(uri)){  
        case PERSONS:  
            //�������ͣ�����ֵǰ��һ���ǹ̶��ģ������ֵ���Լ���ӵģ�Ҳ���Լ��ϰ�·��  
            return "vnd.android.cursor.dir/" + TABLE_NAME;  
        case PERSON:  
            //�Ǽ����������ݣ�����ֵǰ��һ���ǹ̶��ģ������ֵ���Լ���ӵģ�Ҳ���Լ��ϰ�·��  
            return "vnd.android.cursor.item/" + TABLE_NAME;  
        default:  
            throw new IllegalArgumentException("Unknown Uri : " + uri);  
    }  
    }

	
}  