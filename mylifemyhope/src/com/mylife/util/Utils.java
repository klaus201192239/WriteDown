package com.mylife.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

	public static Date longToTime(long lon) {

		return new Date(lon);

	}

	public static long timeToLong(Date date) {

		return date.getTime();
	}

	public static long getLongTime() {

		return System.currentTimeMillis();

	}

	// public static Date getTime(){
	// return null;
	// }

	private void abc(){

		
		
	}

}
