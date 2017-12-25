/**
 * 
 */
package com.trams.joonggu_nubigo.utils;

import com.trams.joonggu_nubigo.common.Constant;

/**
 * Custom Log class write log to file using for testing purpose<br>
 * <br>
 * Will remove when release
 */
public class LogUtils {

	public static void v(String tag, String msg) {
		if (Constant.DEBUG) {
			android.util.Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (Constant.DEBUG) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (Constant.DEBUG) {
			android.util.Log.i(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (Constant.DEBUG) {
			android.util.Log.e(tag, msg);
		}
	}
}
