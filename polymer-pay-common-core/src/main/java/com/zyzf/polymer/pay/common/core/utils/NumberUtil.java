package com.zyzf.polymer.pay.common.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 常用的数字操作类
 * @author 颜铃璋
 * @version v1.0
 * @created 2012-11-26
 */
public class NumberUtil {
	
	private static char[] codeSequence={
			'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z',
		    'A', 'B', 'C', 'D', 'E',
		    'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 
			'P', 'Q', 'R', 'S', 'T', 
			'U', 'V', 'W', 'X', 'Y', 'Z', 
			'0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9' }; 
	private static char[] numberSequence={
		'0', '1', '2', '3', '4',
		'5', '6', '7', '8', '9'};
	/**
	 * 获得手机注册验证码
	 * @return
	 */
	public static String getRegisterMobileCode(){
		StringBuilder sb = new StringBuilder();
		Random random=new Random(); 
		for(int i=0; i<6; i++){
			sb.append(numberSequence[random.nextInt(10)]);
		}
		return sb.toString();
	}
	
	public static String getLogCode(){
		StringBuilder sb = new StringBuilder();
		Random random=new Random(); 
		for(int i=0; i<12; i++){
			sb.append(codeSequence[random.nextInt(62)]);
		}
		return sb.toString();
	}
	
	/**
	 * 补全数字 为len 长的字符串
	 * @param userId
	 * @param len
	 * @return
	 */
	public static String supplyAll(Long userId,int len){
		StringBuilder sb = new StringBuilder();
		sb.append(userId);
	    while (sb.length() < len)
	        sb.insert(0, "0");
	    return sb.toString();
	}
	
	/**
	 * 补全数字 为len 长的字符串
	 * @param str
	 * @param len
	 * @return
	 */
	public static String supplyAll(String str,int len){
		StringBuilder sb = new StringBuilder();
		sb.append(str);
	    while (sb.length() < len)
	        sb.insert(0, "0");
	    return sb.toString();
	}
	
	/**
	 * 数字格式化  f 0.00,0.0,#.##%,00.####E0
	 * @param d
	 * @param f
	 * @return
	 */
	public static String numFormat(double d,String f){
		DecimalFormat df = new DecimalFormat(f);
		return df.format(roundF2(d));
	}
	
	/**
	 * 数字格式化  f 0.00,0.0,#.##%,00.####E0
	 * @param d
	 * @param f
	 * @return
	 */
	public static String numFormat2(double d,String f){
		DecimalFormat df = new DecimalFormat(f);
		return df.format(d);
	}
	
	/**
	 * 四舍五入  保留两位小数
	 * @param d
	 * @param f
	 * @return
	 */
	public static Double roundF2(double d){
	     return round(d,2);
	}
	
	/**
	 * 五舍六入  现在暂时设置成四舍五入  
	 * @param d
	 * @return
	 */
	public static Double round56(double d){
//		BigDecimal bg = new BigDecimal(d);
//	    double num = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//	    return num;
		return round(d,2);
	}
	
	/**
	 * 四舍五入  保留F小数
	 * @param d
	 * @param f
	 * @return
	 */
	public static Double round(double d,int f){
		double nd = Math.round(d*Math.pow(10,f))/Math.pow(10,f);
		BigDecimal bg = new BigDecimal(nd);
		//BigDecimal bg = new BigDecimal(d);
	    double num = bg.setScale(f, BigDecimal.ROUND_HALF_UP).doubleValue();
	    return num;
	}
	/**
	 * 给定一个数字字符串+1 如果不足六位 在前面补0 返回字符串
	 * @param str
	 * @return
	 */
	public static String supplySix(Integer intHao){
	    //Integer intHao = Integer.parseInt(str);
	    intHao++;
	    String strHao = intHao.toString();
	    while (strHao.length() < 6)
	        strHao = "0" + strHao;
	    return strHao;
	}
	
	/**
	 * 给定一个数字字符串+1 如果不足三位 在前面补0 返回字符串
	 * @param str
	 * @return
	 */
	public static String supplyThree(Integer intHao){
	    //Integer intHao = Integer.parseInt(str);
	    intHao++;
	    String strHao = intHao.toString();
	    while (strHao.length() < 3)
	        strHao = "0" + strHao;
	    return strHao;
	}
	
	/**
	 * 给定一个数字字符串+1 补齐到所有字符长
	 * @param str
	 * @return
	 */
	public static String supplyAll(String str){
	    Integer intHao = Integer.parseInt(str);
	    intHao++;
	    String strHao = intHao.toString();
	    while (strHao.length() < str.length())
	        strHao = "0" + strHao;
	    return strHao;
	}
	
	/**
	 * 返回层级  将最后三个字符分开  
	 * @param str
	 * @return
	 */
	public static String supplyLayer(String str){
		String end = str.substring(str.length()-3);
		String star = str.substring(0,str.length()-3);
		end = supplyAll(end);
		return star+end;
	}

	/**
	 * 生成随机字符串
	 * @param length
	 * @return
	 */
	public static String generateRandomString(int length){
//		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		//去掉01IO
		String base = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());
	        sb.append(base.charAt(number));   
	    }
	    return sb.toString();
	}
	
	/**
	 * 生成终端激活码（18位：YYYYMMDDHHmmss+4位大小写字母数字随机数）
	 * 规则更新,暂不再使用此规则,直接采用生成18位随机数
	 * @return
	 */
	public static String generateActivationCode(){
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");     
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		sb.append(formatter.format(curDate));
		sb.append(generateRandomString(4));
		return sb.toString();
	}
	
	/**
	 * 图片路径转缩略图路径
	 * 规则：图片名称_thumb.扩展名
	 * @param realPath
	 * @return
	 */
	public static String realPathToThumbPath(String realPath){
		if(null == realPath){
			return null;
		}
		String thumbPath;
		String[] realPathArr = realPath.split("\\.");
		if(2 == realPathArr.length){
			thumbPath = realPathArr[0] + "_thumb." + realPathArr[1];
		}else{
			String fileType = realPathArr[realPathArr.length - 1];
			thumbPath = realPath.substring(0, realPath.length() - fileType.length() - 1)+"_thumb." + fileType;
		}
		return thumbPath;
	}
	
	/**
	 * 将卡号已星号显示 622260×××××××××1234
	 * @param str
	 * @return
	 */
	public static String cardXing(String str){
		return xing(str,6,4);
	}
	public static String xing(String str,int start,int jend){
		if(null != str && str.length() > 10){
			int end = str.length() - jend;//后四个
			String result = "";
			result = str.substring(0,start);
			for(int i = start;i<end;i++){
				result += "*";
			}
			result += str.substring(end,str.length());
			return result;
		}else{
			return "";
		}
	}
	public static String invitationCodeXing(String str,int start,int jend){
		if(null != str && str.length() >= 10){
			int end = str.length() - jend;
			String result = "";
			result = str.substring(0,start);
			for(int i = start;i<end;i++){
				result += "*";
			}
			result += str.substring(end,str.length());
			return result;
		}else{
			return "";
		}
	}
	
	public static String mobileXing(String str){
		return xing(str,3,4);
	}
	
	public static String replaceAllXing(Integer length){
		String result = "";
		if(length == 0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++){
			sb.append("*");
		}
		result = sb.toString();
		return result;
	}
	
	//测试
	public static void main(String [] args){
//		System.out.println(getRegisterMobileCode());
//		
//		String s = "年月日userId10位随机";
//		System.out.println(s.length());
//		
//		System.out.println(supplyAll(1061l,20));
//		System.out.println(2289721.17-10990.66-59605.51+117697.45+1659.02-7.96+700-3.36-2339169.86);
		
		//四舍五入
		System.out.println("0.015四舍五入"+round(0.015,2));
		//
		System.out.println("0.015四舍五入"+roundF2(0.015));
		//
		System.out.println("0.015五舍六入"+round56(0.015));
		
		System.out.println("13521856049="+mobileXing("13521856049"));
		
	}
}
