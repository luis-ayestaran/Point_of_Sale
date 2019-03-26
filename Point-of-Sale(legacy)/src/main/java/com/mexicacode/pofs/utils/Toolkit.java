package com.mexicacode.pofs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.mexicacode.pofs.exceptions.ToolkitException;
import java.lang.String;

public class Toolkit{

	public static String strToMD5(String target) throws ToolkitException {
		String strMD5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(target.getBytes());
			byte[] strBytes = md.digest();
			StringBuffer sb  = new StringBuffer();
			for (byte b : strBytes) {
				sb.append(String.format("%02x", b & 0xff));
			}
			strMD5 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ToolkitException("Unable to convert the string to MD5");
		}
		return strMD5;
	}
}
