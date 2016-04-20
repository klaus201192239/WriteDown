package com.mylife.myhope;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {

	private static Boolean isExit = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		
		initView();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			Toast.makeText(getApplicationContext(), "1111", Toast.LENGTH_SHORT).show();  
			return false;
		}
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Toast.makeText(getApplicationContext(), "2222222", Toast.LENGTH_SHORT).show();  
			return false;
		}
		return false;

	}

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // ׼���˳�
			Toast.makeText(this, "˫���˳�����~", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // ȡ���˳�
				}
			}, 2000); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����

		} else {
			finish();
			System.exit(0);
		}
	}
	
	public void btonclik(View view){
		
		//��ת�������¼��б�
		if(view.getId()==R.id.showMemory){
			
			Intent intent=new Intent(MyActivity.this,ListActivity.class);
			startActivity(intent);
			//finish();
			
			return ;
		}
		
		
		if(view.getId()==R.id.mybutton){
			
			SharedPreferences setting = getSharedPreferences("mylifemyhope", MODE_PRIVATE); 
			int WriteDownState=setting.getInt("WriteDownState", -1);
			
			//���¿�ʼ��ť
			if(WriteDownState==0){			
				
				Intent intent=new Intent(MyActivity.this,StartActivity.class);
				startActivity(intent);
				finish();
				
			}
			//���½�����ť
			else{
				
				Intent intent=new Intent(MyActivity.this,EndActivity.class);
				startActivity(intent);
				finish();
				
			}
			
			return ;
		}
		
	}
	
	private void initView(){
		
		//��ȡ�¼����ں���״̬����ʼ���ǽ���
		SharedPreferences setting = getSharedPreferences("mylifemyhope", MODE_PRIVATE); 
		int WriteDownState=setting.getInt("WriteDownState", -1);
		
		//������ť
		if(WriteDownState==1){	
			
			//������ͼƬ
			RelativeLayout RL=(RelativeLayout)findViewById(R.id.RLlayout2);
			RL.setBackgroundResource(R.drawable.guide2);
			
			//��������Ϣ����ťͼƬ
			TextView textview=(TextView)findViewById(R.id.mycontent);
			textview.setText("ÿһ�λ���");
			
			//����ťͼƬ
			Button button=(Button)findViewById(R.id.mybutton);
			button.setBackgroundResource(R.drawable.commentni9);
			
		}
		
	}

}
