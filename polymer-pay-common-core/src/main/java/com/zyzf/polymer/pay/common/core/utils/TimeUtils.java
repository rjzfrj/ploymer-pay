package com.zyzf.polymer.pay.common.core.utils;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimeUtils {
	public static String getNowDateTime(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	public static String getDateTime(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前日期时间(yyyyMMddHHmmss)
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTimeToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	/**
	 * 获取当前日期(yyyyMMdd)
	 * @param date
	 * @return
	 */
	public static String getDateToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	/**
	 * 获取当前时间(HHmmss)
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return sdf.format(new Date());
	}
	/**
	 * 得到系统时间的年份
	 */
	public static String getYear(){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Date date = new Date();
		
		return format.format(date);
	}
	
	/**
	 * 得到系统时间的年份
	 */
	public static String getMonth(){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String year=format.format(date);
	String[] years=year.split("-");
	return years[1];
		 
	}
	/**
	* 取得系统时间年份，月份的最后一天
	* 用JAVA的类来实现，方法是取得下个月第一天，然后再取前一天，
	*/
	public static String getStartTimeOfMonth(String mon){ 
		 int year=Integer.parseInt(getYear());
		 GregorianCalendar d1=new GregorianCalendar(year,Integer.parseInt(mon)-1,1); 
		 GregorianCalendar d2=(GregorianCalendar)d1.clone(); 
		 d2.add(Calendar.MONTH,1); 
		  
		 int day = (int)((d2.getTimeInMillis()-d1.getTimeInMillis())/3600/1000/24);
	
	
		 return getYear()+"-"+mon+"-"+day+" 00:00:00";
		
	}
	
	public static String getEndTimeOfMonth(String mon){ 
		 int year=Integer.parseInt(getYear());
		 GregorianCalendar d1=new GregorianCalendar(year,Integer.parseInt(mon)-1,1); 
		 GregorianCalendar d2=(GregorianCalendar)d1.clone(); 
		 d2.add(Calendar.MONTH,1); 
		  
		 int day = (int)((d2.getTimeInMillis()-d1.getTimeInMillis())/3600/1000/24);
		 return year+"-"+mon+"-"+day+" 23:59:59";
		
	}
	
	//获取n位数的随机数
	public static String generateMixed(int n) {
		String chars[]  = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	    String res = "";
	    for(int i = 0; i < n ; i ++) {
	    	int id = (int) Math.ceil(Math.random()*16);
	        res += chars[id];
	    }
	    return res;
	}
	
	//获取n位数的随机数
	public static String generateNum(int n) {
		String chars[]  = {"0","1","2","3","4","5","6","7","8","9"};
	    String res = "";
	    for(int i = 0; i < n ; i ++) {
	    	int id = (int) Math.ceil(Math.random()*9);
	        res += chars[id];
	    }
	    return res;
	}
	
	/**
	 * 日期 转换 为 yyyy-MM-dd HH:mm:ss 字符串
	 * @param date
	 * @return
	 */
	public static String dateTimeString(Date date) {
		if(null == date){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 * 日期 转换 为 yyyy-MM-dd 字符串
	 * @param date
	 * @return
	 */
	public static String dateString(Date date) {
		if(null == date){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	/**
	 * 日期 转换 为 pattern格式的 字符串
	 * @param date
	 * @return
	 */
	public static String dateString(Date date, String pattern) {
		if(null == date){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	
	
	/**
	 * yyyy-MM-dd HH:mm:ss格式的字符串 转换 为 date对象
	 * @param date
	 * @return
	 */
	public static Date stringDateTime(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(str, new ParsePosition(0));
	}
	
	/**
	 * yyyy-MM-dd 格式的字符串 转换 为 date对象
	 * @param date
	 * @return
	 */
	public static Date stringDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(str, new ParsePosition(0));
	}
	
	/**
	 * pattern格式的字符串 转换 为 date对象
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date stringDate(String str, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(str, new ParsePosition(0));
	}
	
	
	public static void main(String[] args) throws ParseException {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		// log.debug(getFormatString());
//		log.debug(generateNum(16));
	}

}
