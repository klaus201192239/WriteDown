package com.mylife.util;

import com.wilddog.client.Wilddog;

import android.content.Context;

public class UploadData  {
	
	private Wilddog ref;
	
	public UploadData(Context context) {
		
		 Wilddog.setAndroidContext(context);
		
		 ref = new Wilddog("https://wild-snake-96493.wilddogio.com/");
		 
	}
	
	public void start(){
		
		//ref.child("writedown").setValue("helloworld");
		ref.setValue("helloworld");
		
	}

}
