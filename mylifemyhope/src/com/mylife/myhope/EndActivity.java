package com.mylife.myhope;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mylife.util.DBHelper;
import com.mylife.util.EmojiFilter;
import com.mylife.util.Utils;

public class EndActivity extends Activity {

	private EditText edit;
	private TextView textview;
	private CheckBox checkPride,checkImportant;
	private String tmp = "";
    // �Ƿ�������EditText������
    private boolean resetText; 
    
    private int thisId,checkpride=1,checkimportant=0;
    private String content;
    private Date stime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);
		
		initViewData();
		
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.end, menu);
		return true;
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Toast.makeText(getApplicationContext(), "�밴��ȡ���򱣴�", Toast.LENGTH_SHORT).show();  
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
	
	@SuppressLint("SimpleDateFormat")
	private void initView(){
	
		edit=(EditText)findViewById(R.id.ething_content);
		textview=(TextView)findViewById(R.id.ething_time);
		checkPride=(CheckBox)findViewById(R.id.ething_pride);
		checkImportant=(CheckBox)findViewById(R.id.ething_important);
		
		edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int arg2, int arg3) {
                if (!resetText) {
                    if ((arg3 == 2) && (!EmojiFilter.containsEmoji(s.toString().substring(start, start + 2)))) {
                        resetText = true;
                        edit.setText(tmp);
                        edit.invalidate();
                        if (edit.getText().length() > 1){
                        	Selection.setSelection(edit.getText(), edit.getText().length());
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
		
		checkPride.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
				if(arg1==true){
					checkpride=1;
				}else{
					checkpride=0;
				}
				
			}
		});

		checkImportant.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1==true){
					checkimportant=1;
				}else{
					checkimportant=0;
				}
			}
		});
		
		//��ʼ����ֵ��
		
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:mm");		
		edit.setText(content);
		textview.setText("��ʼʱ�䣺"+sdf.format(stime));
		
		
	}
	
	private void initViewData(){
		
		DBHelper dbhelper = new DBHelper(this);
		dbhelper.CreatOrOpen("mylifemyhope");
		Cursor cur = dbhelper.selectInfo("select * from mything where endtime='0000';");
		while (cur.moveToNext()) {

			thisId=cur.getInt(0);
			content=cur.getString(1);
			stime=Utils.longToTime(Long.parseLong(cur.getString(2)));
			
		}
		dbhelper.closeDB();
		
	}

	
	public void btonclick(View view){
		
		if(view.getId()==R.id.ething_ok){			
			
			//�����¼�������Ϊ��
			String str=edit.getText().toString();
			if(str.length()==0){
				Toast.makeText(this, "���������ݡ�", Toast.LENGTH_SHORT).show();	
				return ;
			}
			
			
			String sr=Utils.getLongTime()+"";
			//�������ݿ⣬�����¼������У�������ʱ����Ϊ0000
			DBHelper dbhelper = new DBHelper(this);
			dbhelper.CreatOrOpen("mylifemyhope");			
			//(id INTEGER PRIMARY KEY,title text,starttime text,endtime text,pride int,important int);");	
			dbhelper.excuteInfo("update mything set endtime='"+sr+"',title='"+str+"',pride='"+checkpride+"',important='"+checkimportant+"' where id='"+thisId+"'");
			dbhelper.closeDB();
			
			//����¼״̬����Ϊ��ʼ��¼��״̬
			SharedPreferences setting = getSharedPreferences("mylifemyhope", MODE_PRIVATE); 
			SharedPreferences.Editor editor = setting.edit();	
			editor.putInt("WriteDownState", 0);
			editor.commit();	
			
			jump();
			
		}
		
		if(view.getId()==R.id.ething_no){
			
			
			DBHelper dbhelper = new DBHelper(this);
			dbhelper.CreatOrOpen("mylifemyhope");			
			//(id INTEGER PRIMARY KEY,title text,starttime text,endtime text,pride int,important int);");	
			dbhelper.excuteInfo("delete from mything where id='"+thisId+"';");
			dbhelper.closeDB();
			
			//����¼״̬����Ϊ��ʼ��¼��״̬
			SharedPreferences setting = getSharedPreferences("mylifemyhope", MODE_PRIVATE); 
			SharedPreferences.Editor editor = setting.edit();	
			editor.putInt("WriteDownState", 0);
			editor.commit();	
			
			jump();
			
		}
		
	}
	
	//������ת
		private void jump(){
			
			Intent intent=new Intent(EndActivity.this,MyActivity.class);
			startActivity(intent);
			finish();
			
		}
	
}
