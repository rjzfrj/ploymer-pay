package com.zyzf.polymer.pay.common.core.utils;
import java.util.Base64;

public class Base64Util {
	/**
	 * 加密
	 */
	
	 public static String getEncoder(String str) {  
		String encStr=null;
		try {
			encStr = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
            System.out.println("Base64加密后的结果为"+encStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encStr;
			
	 }
	 
	 /**
	  * 解密
	  */
	 public static String getResolveDecoder(String str) {
		 
		 byte[] asBytes = Base64.getDecoder().decode(str);
		 String sr=null;
		 try {
		 sr=new String(asBytes, "utf-8");
		  System.out.println("Base64解密后的结果为"+sr);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return sr;
	 }
	


}