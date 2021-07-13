package com.capstone.fsmob.utils;

public class CommonUtils {

	public static boolean isNotNull(Object dt) {
		if (dt == null || dt.equals("")) {
			return true;
		}
		return false;
	}
}
