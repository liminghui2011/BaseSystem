package com.ocean.lmh.base.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	
	private static Logger log	= LoggerFactory.getLogger(DateUtils.class);
	
	public static final String	FORMAT_DAFAULT	= "yyyy-MM-dd HH:mm:ss";
	public static final String	FORMAT_DATE		= "yyyy-MM-dd";
	public static final String	FORMAT_TIME		= "HH:mm:ss";	
	public static final String	FORMAT_yyyyMMdd		= "yyyyMMdd";
	
	/**
	 * 格式化时间 , yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String format(Date date){
		return format(FORMAT_DAFAULT, date);
	}
	
	/**
	 * 格式化时间 , yyyy-MM-dd HH:mm:ss
	 * @param timestamp
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String format(Timestamp timestamp){
		return format(FORMAT_DAFAULT, timestamp);
	}
	
	/**
	 * 格式化时间
	 * @param formater 用户期望的时间格式
	 * @param date 需要格式化的时间
	 * @return 
	 */
	public static String format(String formater, Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		return sdf.format(date);
	}
	
	/**
	 * 格式化时间
	 * @param formater 用户期望的时间格式
	 * @param timestamp java.sql.timestamp对象
	 * @return
	 */
	public static String format(String formater, Timestamp timestamp){
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		return sdf.format(timestamp);
	}
	
	
	/**
	 * 获取当前时间
	 * @param formater  期望转换的格式
	 * @return String
	 */
	public static String getCurrentDateTime(String formater){
		
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前日期值然后减去一天返回上一天的时间字符串
	 * 如当前日期为2013-09-25则返回2013-09-24
	 * @param currentDate 时间字符串，如果不为空则将当前时间设置为此时间
	 */
	public static String getDateStringByCurrentDate(String currentDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar  = Calendar.getInstance();
		if (AssertHelper.isNotEmptyString(currentDate))
		{
			Date date = null;
			try 
			{
				date = sdf.parse(currentDate);
			} 
			catch (ParseException e) 
			{
				log.error("string format to date error",e);
				return sdf.format(new Date());
			}
			calendar.setTime(date); 
		}
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取当前日期的上一个月的日期字符串，如当前日期为2013-09-26
	 * 则返回的是字符串"2013-08"
	 * @return 日期字符串如"2013-08"
	 */
	public static String getYearMonth()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取当前时间戳
	 * @return Timestamp
	 */
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 获取今天的起始时间
	 * eg：2013-05-18 00:00:00.000
	 * @return Timestamp
	 */
	public static Timestamp getTodayBeginTimestamp(){
		
		Calendar calendar  = Calendar.getInstance();
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);		
		return new Timestamp(calendar.getTimeInMillis());
	}
	/**
	 * 获取今天的结束时间
	 * eg：2013-05-18 23:59:59.999
	 * @return Timestamp
	 */
	public static Timestamp getTodayEndTimestamp(){		
		Calendar calendar  = Calendar.getInstance();
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND, 999);		
		return new Timestamp(calendar.getTimeInMillis());
	}
	
	/**
     * org.joda.time.DateTime转换为java.sql.Date
     * @param time     被转换时间
     * @return  java.sql.Date
     * @exception/throws [异常类型] [异常说明]
     */
    public static Date transDate(DateTime time)
    {
        if(null != time)
        {
            return new Date(time.getMillis());
        }
        return null;
    }
    
    /**
     * 将字符串日期值转换为java.util.Date的日期对象
     * @param dateString 字符串日期值
     * @return  Date日期对象
     */
    public static java.util.Date strToDate(String dateString)
    {
        java.util.Date date = null;
        try
        {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString.replace("/", "-"));
        }
        catch (ParseException e)
        {
            date = null;
            log.error(e.getMessage());
        }
        return date;
    }
	
}
