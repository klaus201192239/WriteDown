package com.mylife.myhope;

import com.mylife.util.DBHelper;
import com.mylife.util.EmojiFilter;
import com.mylife.util.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends Activity {

	private EditText input;
	private String tmp = "";
    // �Ƿ�������EditText������
    private boolean resetText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		initView();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		Toast.makeText(getApplicationContext(), "�밴��ȷ����ȡ��", Toast.LENGTH_SHORT).show();
		return false;

	}
	
	private void initView(){
		
		
		//��ֹ���������Emoji�������
		input=(EditText)findViewById(R.id.sthing_content);
		input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int arg2, int arg3) {
                if (!resetText) {
                    if ((arg3 == 2) && (!EmojiFilter.containsEmoji(s.toString().substring(start, start + 2)))) {
                        resetText = true;
                        input.setText(tmp);
                        input.invalidate();
                        if (input.getText().length() > 1){
                        	Selection.setSelection(input.getText(), input.getText().length());
                        }
                        Toast.makeText(getApplicationContext(), "��֧�ֱ�������",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if (!resetText)
                    tmp = arg0.toString();

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
		
	}
	
	public void btonclick(View view){		
		
		//����ȷ����ť
		if(view.getId()==R.id.sthing_ok){
			
			//�����¼�������Ϊ��
			String str=input.getText().toString();
			if(str.length()==0){
				Toast.makeText(this, "���������ݡ�", Toast.LENGTH_SHORT).show();	
				return ;
			}
			
			String sr=Utils.getLongTime()+"";
			//�������ݿ⣬�����¼������У�������ʱ����Ϊ0000
			DBHelper dbhelper = new DBHelper(this);
			dbhelper.CreatOrOpen("mylifemyhope");			
			//(id INTEGER PRIMARY KEY,title text,starttime text,endtime text,pride int,important int);");	
			dbhelper.excuteInfo("insert into mything values(null,'"+str+"','" +sr+ "','0000',0,0)");
			dbhelper.closeDB();
			
			//����¼״̬����Ϊ������¼��״̬
			SharedPreferences setting = getSharedPreferences("mylifemyhope", MODE_PRIVATE); 
			SharedPreferences.Editor editor = setting.edit();	
			editor.putInt("WriteDownState", 1);
			editor.commit();	
			
			jump();
			
		}
		
		
		//ȡ����ʼ�¼�
		if(view.getId()==R.id.sthing_no){
			
			jump();
			
		}
		
	}
	
	//������ת
	private void jump(){
		
		Intent intent=new Intent(StartActivity.this,MyActivity.class);
		startActivity(intent);
		finish();
		
	}
	
}
