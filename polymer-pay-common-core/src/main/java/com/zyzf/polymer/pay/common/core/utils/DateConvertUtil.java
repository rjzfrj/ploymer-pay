package com.zyzf.polymer.pay.common.core.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
/**
 * 日期操作类
 * @author 颜铃璋
 * @version v1.0
 * @created 2012-12-24
 */
public class DateConvertUtil {
	
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
	

	
	@SuppressWarnings("deprecation")
	public static String getParseDate(Date getDate){
		String returnDate = "";
		Calendar cal;
		java.text.SimpleDateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd");
		cal=Calendar.getInstance();
		if(df.format(getDate).equals(df.format(cal.getTime()))){
			returnDate = "今天"+dateString(getDate,"HH:mm");
		}else{
			returnDate = dateString(getDate,"MM月dd日 HH:mm");
		}
		return returnDate;
	}
	
	public static HashMap<String,Object> getThatDayMap(){
		HashMap<String,Object> simpleHash = new HashMap<String,Object>();
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -1);//日期减一天
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
			String parseDate = format.format(calendar.getTime());
			simpleHash.put("beginDate", parseDate+"00:00:00");
			simpleHash.put("endDate", parseDate+"23:59:59");
			
//			simpleHash.put("beginDate", "2011-05-23 00:00:00");
//			simpleHash.put("endDate","2011-05-23 23:59:59");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return simpleHash;
	}

	/**
	 * 根据生日返回年龄
	 * @param birthdate
	 * @return
	 */
	public static String getAgeByBirthday(Date birthdate){
		if(null == birthdate) return "";
		java.util.Date date=new Date();     
		long day=(date.getTime()-birthdate.getTime())/(24*60*60*1000) + 1;
		String year=new java.text.DecimalFormat("#.00").format(day/365f);
		return year ;
	}
	
	/**
	 * 判断两个日期的大小 
	 * @param dateA
	 * @param dateB
	 * @return
	 * 返回1 时  dateA >= 于 dateB   dateA为最新时间
	 * 返回0 时  dateA < 于 dateB  dateB为最新时间
	 */
	public static int compareDate(Date dateA , Date dateB){
		long dateATime = dateA.getTime() ;
		long dateBTime = dateB.getTime() ;
		
		if(dateATime >= dateBTime ){
			return 1 ;
		}else{
			return 0 ;
		}
	}
	
	/**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDayBefore(Date date) {
    	 Calendar c = Calendar.getInstance();
         String dateStr = dateString(date);
         date = stringDate(dateStr);
         c.setTime(date);
         int day = c.get(Calendar.DATE);
         c.set(Calendar.DATE, day - 1);
         return c.getTime();
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static Date getSpecifiedDayAfter(Date date) {
        Calendar c = Calendar.getInstance();
        String dateStr = dateString(date);
        date = stringDate(dateStr);
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        return c.getTime();
    }
    
    /**
     * 获得指定日期的下一月 日期
     * @param date
     * @return
     */
    public static Date getNextMonthDate(Date date){
    	Calendar c = Calendar.getInstance();
        String dateStr = dateString(date);
        date = stringDate(dateStr);
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month + 1);
        return c.getTime();
    }
    
    /**
     * 获得指定日期的下一月 日期
     * @param date
     * @return
     */
    public static Date getBeforeMonthDate(Date date){
    	Calendar c = Calendar.getInstance();
        String dateStr = dateString(date);
        date = stringDate(dateStr);
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month - 1);
        return c.getTime();
    }
    
    /**
     * 获得指定日期的下一月 日期
     * @param date
     * @return
     */
    public static Date getSpecifiedDay(Date date,int day){
    	Calendar c = Calendar.getInstance();
        String dateStr = dateString(date);
        date = stringDate(dateStr);
        c.setTime(date);
        c.set(Calendar.DATE, day);
        return c.getTime();
    }
	
	/*private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
	public static String getFullDate() {
		Date date = new Date();
		return sdf.format(date);
	}*/
	
	public static void main(String[] args) throws Exception {
		System.out.println(getThatDayMap());
		System.out.println(getParseDate(new Date()));
		System.out.println(dateString(getNextMonthDate(stringDate("2012-12-1"))));
		System.out.println(dateString (stringDate("2012-12-1 24:00:00","yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
		System.out.println(stringDate("2013-12-1 24:00:00","yyyy-MM-dd HH:mm:ss").getTime());
		
		String s =" \t6222 6000 1234 4567 234  \n\r";
		System.out.println("==="+s.replaceAll("\\s*", "")+"===");
	}
}
