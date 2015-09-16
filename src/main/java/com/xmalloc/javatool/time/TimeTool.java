package com.xmalloc.javatool.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeTool {
	
	public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_SHORT = "yyyy-MM-dd";
	public static final ThreadLocal<DateFormat> dfLong = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	public static final ThreadLocal<DateFormat> dfShort = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * 得到N天后时间
	 * 
	 * @param days
	 *            天数
	 * @return 时间
	 */
	public static Calendar getCalendarAfterByDay(int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now;
	}

	/**
	 * 得到N天后时间
	 * 
	 * @param days
	 *            天数
	 * @return 时间
	 */
	public static Date getDateAfterByDay(int days) {
		return getCalendarAfterByDay(days).getTime();
	}

	/**
	 * 得到N天前时间
	 * 
	 * @param days
	 *            天数
	 * @return 时间
	 */
	public static Calendar getCalendarBeforeByDay(int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE, now.get(Calendar.DATE) - days);
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now;
	}

	/**
	 * 得到N天前时间
	 * 
	 * @param days
	 *            天数
	 * @return 时间
	 */
	public static Date getDateBeforeByDay(int days) {
		return getCalendarBeforeByDay(days).getTime();
	}

	/**
	 * 得到N天后时间
	 * 
	 * @param days
	 *            天数
	 * @return 时间字符串
	 */
	public static String getDateStringAfterByDay(int days) {
		return formatDate(getDateAfterByDay(days));
	}

	/**
	 * 得到N天前时间
	 * 
	 * @param days
	 *            天数
	 * @return 时间字符串
	 */
	public static String getDateStringBeforeByDay(int days) {
		return formatDate(getDateBeforeByDay(days));
	}

	/**
	 * 格式化日期, 格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return "";
		return dfLong.get().format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式字符串
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		if (date == null)
			return "";
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 解析日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		try {
			return dfLong.get().parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 解析日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式字符串
	 * @return
	 */
	public static Date parseDate(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 两个日期的天数之差
	 * 
	 * @param now
	 * @param loginDate
	 * @return
	 */
	public static long daysAfter(Date after, Date before) {
		long time = after.getTime() - before.getTime();
		return time / (1000 * 60 * 60 * 24);
	}

	
	/**
	 * 转换UTC时间
	 * @param time
	 * @return
	 */
	public static String getUtcTimeForTime(String time){
		if(time == null) return null ;
		Date data= getDateByFormatDate(time);
		
		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss ZZZZ yyyy",Locale.ENGLISH);
		return formatter.format(data);
	}
	
	
	/**
	 * 转换UTC时间
	 * @param time
	 * @return
	 */
	public static String getUtcTimeForDate(Date time){
		if(time == null) return null ;
		
		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss ZZZZ yyyy",Locale.ENGLISH);
		return formatter.format(time);
	}
	

	/**
	 * 转换UTC时间 from yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String getUtcTimeForDate(String time){
		if(time == null) return null ;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
			return getUtcTimeForDate(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 获取系统当前格式化时间
	 * @return
	 */
	public static String getCurrTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 获取当前日期值
	 * @param difference 以天位单位的差值
	 * @return yyyy-MM-dd
	 */
	public static String getCurrDate(int difference){
		if(difference == 0) return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, difference);
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}
	
	
	/**
	 * 获取系统当前的unix时间戳
	 */
	public static long getCurrentUnixTimeStamp() {
		return new Date().getTime()/1000;
	}
	

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static int getCurrYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static int getCurrMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 获取当前是一年中的第几天
	 * @return
	 */
	public static int getCurrDay(){
		return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取当前是一天中的第几个小时
	 * @return
	 */
	public static int getCurrHour(){
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	

	/**
	 * 获取当前是小时中的第几分钟
	 * @return
	 */
	public static int getCurrMinute(){
		return Calendar.getInstance().get(Calendar.MINUTE);
	}
	
	/**
	 * 获得本周星期一的日期
	 * @return
	 */
    public static String getCurrentMonday() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }
    
	/**
	 * 通过Unix时间戳来获取时间对象
	 * @param timestamp unix时间戳
	 * @return 时间对象
	 */
	public static Date getDateByUnixTimeStamp(long timestamp) {
		return new Date(timestamp * 1000);
	}
	
	/**
	 * 通过Unix时间戳来获取格式化的时间(yyyy-MM-dd HH:mm:ss)
	 * @param timestamp unix时间戳
	 * @return 格式化后的时间
	 */
	public static String getFormatDateByUnixTimeStamp(long timestamp) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getDateByUnixTimeStamp(timestamp)); 
	}
	
	/**
	 * 通过格式化的日期来获取日期对象
	 * @param timeStr 日期字符串 (yyyy-MM-dd HH:mm:ss)
	 * @return 日期对象
	 */
	public static Date getDateByFormatDate(String timeStr) {
		boolean isDate = false;
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr);
		} catch (ParseException e) {
			isDate = true;
		}
		
		if(isDate) {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return sdf.parse(timeStr);
			} catch (ParseException e) {
				return new Date();
			}
		}
		return new Date();
	}
	
	/**
	 * 通过日期对象获取unix时间戳
	 * @param date 日期对象
	 * @return unix时间戳
	 */
	public static long getUnixTimeStampByDate(Date date) {
		return date.getTime()/1000;
	}
	
	/**
	 * 通过格式化的日期字符串来获取unix时间戳
	 * @param timeStr 格式化的日期字符串
	 * @return unix时间戳
	 */
	public static long getUnixTimeStampByFormatDate(String timeStr) {
		return getUnixTimeStampByDate(getDateByFormatDate(timeStr));
	}
	
	/**
	 * 获取n天前当天日期(不含时间)的时间戳
	 * @param days 天数
	 * @return 时间戳
	 */
	public static long getTimeStampBeforeByDay(int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE, now.get(Calendar.DATE) - days);
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime().getTime()/1000;
	}
	
	/**
	 * 获取n天前的时间戳
	 * @param day 天数
	 * @return 时间戳
	 */
	public static long getTimeStampBefore(int day) {
		long dayTime = day*24l*3600l ;
		return getCurrentUnixTimeStamp() - dayTime;
	}
	
	/**
	 * 获取n天后的时间戳
	 * @param day 天数
	 * @return 时间戳
	 */
	public static long getTimeStampAfter(int day) {
		long dayTime = day*24l*3600l;
		return getCurrentUnixTimeStamp() + dayTime;
	}
	
	/**
	 * 获取n天后当天日期(不含时间)的时间戳
	 * @param days 天数
	 * @return 时间戳
	 */
	public static long getTimeStampAfterByDay(int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime().getTime()/1000;
	}
	
	/**
	 * 得到N天前时间
	 * @param day 天数
	 * @return 时间字符串
	 */
	public static String getTimeBefore(int day){
		String time="";
		long dayTime =1*24*3600*1000l;
		time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()-day*dayTime);
		return time;
	}
	
	/**
	 * 得到N天后时间
	 * @param day 天数
	 * @return 时间字符串
	 */
	public static String getTimeAfter(int day){
		String time="";
		long dayTime =1*24*3600*1000l;
		time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()+day*dayTime);
		return time;
	}
	

	/**
	 * 判断一个时间是否是过去
	 * @param inDate 比较的日期
	 * @return true 过去 false 大于或等于今天 
	 */
	public static boolean afterNow(Date inDate){
		if(inDate == null) return false;
		Calendar calendar = Calendar.getInstance();
		Calendar inCalendar = Calendar.getInstance();
		inCalendar.setTime(inDate);
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		int inYear = inCalendar.get(Calendar.YEAR);
		int inMonth = inCalendar.get(Calendar.MONTH);
		int inDay = inCalendar.get(Calendar.DAY_OF_MONTH);
		if(nowYear > inYear) return true;
		if(nowYear == inYear && nowMonth > inMonth) return true;
		if(nowYear == inYear && nowMonth == inMonth && nowDay > inDay) return true;
		return false;
	}
	
	/**
	 *  判断一个时间是否是今天
	 * @param inDate
	 * @return
	 */
	public static boolean inNow(Date inDate){
		if(inDate == null) return false;
		Calendar calendar = Calendar.getInstance();
		Calendar inCalendar = Calendar.getInstance();
		inCalendar.setTime(inDate);
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		int inYear = inCalendar.get(Calendar.YEAR);
		int inMonth = inCalendar.get(Calendar.MONTH);
		int inDay = inCalendar.get(Calendar.DAY_OF_MONTH);
		if(nowYear == inYear && nowMonth == inMonth && nowDay == inDay) return true;
		return false;
	}
	
	private static int weeks = 0;
	/**
	 * 获得当前日期与本周一相差的天数
	 * @return
	 */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        //System.out.println(dayOfWeek);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }
	
    
    /**
     * 获得相应周的周日的日期
     * @return
     */
    public static String getSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    } 
    
	/**
	 * 判断时间是否在当天
	 * @param time
	 * @return true 是  false 不在当天
	 */
    public static boolean isCurrDay(String time){
    	String currDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).replaceAll("-","");
    	String tempTime  =time.substring(0,10).replaceAll("-", "");
    	if(currDay.equals(tempTime))return true;
    	else return false;
    }
    
	/**
	 * 判断时间是否在当天
	 * @param time
	 * @return true 是  false 不在当天
	 */
    public static boolean isCurrDay(long time){
    	Date date = new Date(time*1000);
    	String loginTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
    	return isCurrDay(loginTime);
    }
    
    
	/**
	 * 计算年龄
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static int getAge(String time) throws Exception {
		  if(time == null) return 0 ;
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  Date birthDay = format.parse(time);
		  Calendar cal = Calendar.getInstance();
		  if (cal.before(birthDay)) {
		   throw new IllegalArgumentException(
		     "The birthDay is before Now.It's unbelievable!");
		  }

		  int yearNow = cal.get(Calendar.YEAR);
		  int monthNow = cal.get(Calendar.MONTH);
		  int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		  cal.setTime(birthDay);// 给时间赋值
		  int yearBirth = cal.get(Calendar.YEAR);
		  int monthBirth = cal.get(Calendar.MONTH);
		  int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		  int age = yearNow - yearBirth;
		  if (monthNow <= monthBirth) {
		   if (monthNow == monthBirth) {
		    // monthNow==monthBirth
		    if (dayOfMonthNow < dayOfMonthBirth) {
		     age--;
		    } else {
		     // do nothing
		    }
		   } else {
		    // monthNow>monthBirth
		    age--;
		   }
		  } else {
		   // monthNow<monthBirth
		   // donothing
		  }
		  return age;
	} 
	
	
	/**
	 * 计算生日！
	 * @param time
	 * @return
	 */
	public static String getBrithday(String time){
		try {
			Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(time);
			Calendar currCalendar = Calendar.getInstance();
			currCalendar.set(Calendar.YEAR, 1970);
			Calendar birthdayCalendar = Calendar.getInstance();
			birthdayCalendar.setTime(birthday);
			birthdayCalendar.set(Calendar.YEAR, 1970);
			String birthdayFormat = new SimpleDateFormat("MM-dd").format(birthday); 
			int currMonth = currCalendar.get(Calendar.MONTH);
			int birthdayMonth = birthdayCalendar.get(Calendar.MONTH);
			int currDay = currCalendar.get(Calendar.DAY_OF_MONTH);
			int birthdayDay = birthdayCalendar.get(Calendar.DAY_OF_MONTH);
			int currDays = currCalendar.get(Calendar.DAY_OF_YEAR);
			int birthdayDays = birthdayCalendar.get(Calendar.DAY_OF_YEAR);
			int difDays = birthdayDays - currDays;
			if(currMonth == birthdayMonth){
				switch (birthdayDay - currDay) {
					case -1: return "昨天(" + birthdayFormat + ")";
					case 0: return "今天(" + birthdayFormat + ")";
					case 1: return "明天(" + birthdayFormat + ")";
					case 2: return "后天(" + birthdayFormat + ")";
					case 3: return "三天后(" + birthdayFormat + ")";
					default: return null;
				}
			}else if(currMonth > birthdayMonth && difDays  == -1){
				return "昨天(" + birthdayFormat + ")";
			}else if(currMonth < birthdayMonth && difDays  > 0 && difDays < 4){
				switch (birthdayDay - currDay) {
					case 1: return "明天(" + birthdayFormat + ")";
					case 2: return "后天(" + birthdayFormat + ")";
					case 3: return "三天后(" + birthdayFormat + ")";
					default: return null;
				}
			}else if(currDays > 362 && birthdayDays < 4){
				switch (birthdayDays + 365 - currDays) {
					case 1: return "明天(" + birthdayFormat + ")";
					case 2: return "后天(" + birthdayFormat + ")";
					case 3: return "三天后(" + birthdayFormat + ")";
					default: return null;
				}
			}else if(currDays == 1 && birthdayDays == 365){
				return "昨天(" + birthdayFormat + ")";
			}else{
					return null;
			}
		} catch (ParseException e) {
			return null;
		}
	} 
	
	/**
	 * 将一个时间延后n天
	 * @param days
	 * @return
	 * @throws ParseException 
	 */
	public static Date delayTime(String time, int days) throws ParseException{
		if(time == null) return new Date();
		Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}
	
	/**
	 * 获得今天是一年的第几天
	 * @return
	 */
	public static int getDaysOfYear(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_YEAR);
	}
	

	/**
	 * 通过时间戳来获取两个时间之间的差值（单位秒）
	 * @param currTime 当前时间戳/秒
	 * @param createTime 创建时间戳/秒
	 * @return 时间差值字符串
	 */
	public static String getTimeDifferenceByUnixTimestamp(long currTime , long createTime) {
        int hours=(int) ((currTime - createTime)/3600);
        int minutes=(int) (((currTime - createTime) -hours*3600)/60);
        int second = (int)(((currTime - createTime)));
        if(second==0) second = 1 ;
        if(hours==0 && minutes==0){
           	if(second < 0){
        		second =  0 ;
        	}
        	return second+"秒前";
        }
        if(hours>=24){
        	return getFormatDateByUnixTimeStamp(createTime).substring(0 , 10);
        }
        if(hours<=0){
        	if(minutes < 0){
        		minutes = 0 ;
        	}
        	return minutes+"分钟前";
        }
        if(hours<0){
        	hours =  0 ;
        }
        return hours+"小时前";
	}

	/**
	 *  将传入的毫秒数转换为单一时间描述
	 * @param times 时间
	 * @return n天 | n小时
	 */
	public static String getSingleTimeDescByMillisecond(Long times){
		 if(times <= 0) return "1分钟";
		 times = times/1000;
		 long d=0,t=0,m=0,s=0;
		 d = times/(24*60*60);
		 if(d > 0){
			return d + "天";
		 }
		 t = (times%(24*60*60))/(60*60);
		 if(t > 0){
			return t + "小时";
		 }
		 m = (times%(60*60))/(60);
		 if(m > 0){
			return m + "分钟";
		 }
		 s = times%60;
		 if(s > 0){
			return s + "秒钟";
		 }
		 return "1分钟";   
	}
	

	/**
	 *  将传入的毫秒数转换为组合时间描述
	 * @param times 时间
	 * @return n天 & n小时
	 */
	public static String getMoreTimeDescBySecond(Long times){
		 if(times <= 0) return "1分钟";
		 String timeString = "";
		 long d=0,t=0,m=0;
		 d = times/(24*60*60);
		 if(d > 0){
			 timeString = d + "天";
		 }
		 t = (times%(24*60*60))/(60*60);
		 if(t > 0){
			 timeString = timeString + t + "小时";
		 }
		 m = (times%(60*60))/(60);
		 if(m > 0){
			 timeString = timeString + m + "分钟";
		 }
		 return timeString;   
	}
	

	/**
	 * 计算时间的剩余值
	 * @param currTime
	 * @param createTime
	 * @return
	 */
	public static String getTimeLast(String endTime) {
        long difference = 0L;
        try {
        	difference = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime).getTime() - System.currentTimeMillis();
        } catch (Exception e) {
        	return "剩余1秒";
        }
        if(difference > 1000L*60*60*24*365*10){
        	return "很久很久";
        }else if(difference > 1000L*60*60*24*365){
        	return "剩余" + difference/(1000L*60*60*24*365) + "年";
        } else if(difference > 1000L*60*60*24*30){
        	return "剩余" + difference/(1000L*60*60*24*30) + "月";
        } else if(difference > 1000L*60*60*24){
        	return "剩余" + difference/(1000L*60*60*24) + "天";
        } else if(difference > 1000L*60*60){
        	return "剩余" + difference/(1000L*60*60) + "小时";
        } else if(difference > 1000*60){
        	return "剩余" + difference/(1000*60) + "分钟";
        } else if(difference > 1000){
        	return "剩余" + difference/1000 + "秒";
        }
        return "剩余1秒";
    }

	/**
	 * 2个日期的时间差(精确到日)
	 * @param oldDate 日期
	 * @param newDate 日期
	 * @return 时间拆
	 */
	public static String getTimeDifference(Date oldDate, Date newDate){
		 long tda = newDate.getTime() - oldDate.getTime();
		 if(tda <= 0) return "1 秒前";
		 long h=tda/1000/60/60;
		 if(h >= 24){
			 return new SimpleDateFormat("yyyy-MM-dd").format(oldDate);
		 }
		 if(h >= 1){
			 return (int)h + " 小时前";
		 }
		 
		 long m=tda/1000/60;
		 if(m >= 1){
			 return (int)m + " 分钟前";
		 }
		 long s=tda/1000;  
		 return (int)s + " 秒前";   
	}
	

	/**
	 * 2个日期的时间差(精确到月)
	 * @param oldDate 日期
	 * @param newDate 日期
	 * @return  时间差
	 */
	public static String getMonthDifference(Date oldDate, Date newDate){
		long tda = newDate.getTime() - oldDate.getTime();
		 if(tda <= 0) return "1秒前";
		 long h=tda/1000/60/60;
		 if(h >= 24){
			 long d = h/24;
			 if(d >=7){
				 long week = d/7;
				 if(week > 4){
					 return  new SimpleDateFormat("yyyy-MM-dd").format(oldDate);
				 } 
				 return (int)week + "周前";
			 }
			 return (int)d + "天前";
		 }
		 if(h >= 1){
			 return (int)h + "小时前";
		 }
		 
		 long m=tda/1000/60;
		 if(m >= 1){
			 return (int)m + "分钟前";
		 }
		 long s=tda/1000;  
		 return (int)s + "秒前";   
	}
	
	/**
	 * 2个日期的时间差(精确到日)
	 * @param currTime
	 * @param createTime
	 * @return
	 */
	public static String getTimeDifference(String currTime, String lastTime) {
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldDate, newDate;
        try {
        	oldDate = timeformat.parse(lastTime);
        	newDate = timeformat.parse(currTime);
        	return getTimeDifference(oldDate, newDate);
        } catch (Exception e) {
            return "1 秒前";
        }
    }
	

	/**
	 * 计算两个时间之间的组合差值！
	 * @param currTime
	 * @param createTime
	 * @return 昨天22:08 | 06-20 15:36
	 * @throws ParseException 
	 */
	public static String getTimeDiff(String currTime, String lastTime) {
		  if(currTime == null || lastTime == null) return "1 秒前";
	      SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date lastDate = null ;
		  Date currDate = null ;
		  try {
			  lastDate = timeformat.parse(lastTime);
			  currDate  = timeformat.parse(currTime);
			  return getTimeDiff(currDate, lastDate);
		  } catch (ParseException e1) {
			  return "1秒前";
		  }
    }
	

	/**
	 * 计算两个时间之间的组合差值！
	 * @param currTime
	 * @param createTime
	 * @return 昨天22:08 | 06-20 15:36
	 * @throws ParseException 
	 */
	public static String getTimeDiff(Date currDate, Date lastDate) {
		  if(currDate == null || lastDate == null) return "1 秒前";
		  Calendar currCalendar = Calendar.getInstance();
		  currCalendar.setTime(currDate);
		  Calendar lastCalendar = Calendar.getInstance();
		  lastCalendar.setTime(lastDate);
		  if (currCalendar.before(lastCalendar)) {
			  return "1秒前";
		  }
		  
		  int currYear = currCalendar.get(Calendar.YEAR);
		  int currDay = currCalendar.get(Calendar.DAY_OF_YEAR);
		  int lastYear = lastCalendar.get(Calendar.YEAR);
		  int lastDay = lastCalendar.get(Calendar.DAY_OF_YEAR);
		  int diff = lastDay - currDay ;
		  String tmp = "";
		  
		  if(currYear == lastYear){
			switch (diff) {
				case 0:
				      long t1 = currDate.getTime();
				      long t2 = lastDate.getTime();
				      int hours=(int) ((t1 - t2)/3600000);
				      int minutes=(int) (((t1 - t2)/1000-hours*3600)/60);
				      int second = (int)(((t1 - t2)/1000));
				      if(second<=0) second=1;
				     if(hours==0 && minutes==0){
				        	tmp= second + "秒前";
				        	break;
				     }
				     if(hours<=0){
				        	if(minutes<0){
				        		minutes = 0 ;
				        	}
				        	tmp= minutes+"分钟前";
				        	break;
				     }
				     if(hours >= 1){
				    	 tmp = "今天 "+ completionSingle(lastCalendar.get(Calendar.HOUR_OF_DAY)) + ":" + completionSingle(lastCalendar.get(Calendar.MINUTE));
				    	 break;
				     }
				case -1:
					tmp = "昨天 " + completionSingle(lastCalendar.get(Calendar.HOUR_OF_DAY)) + ":" + completionSingle(lastCalendar.get(Calendar.MINUTE));
					break;
				case -2:
					tmp = "前天 " + completionSingle(lastCalendar.get(Calendar.HOUR_OF_DAY)) + ":" + completionSingle(lastCalendar.get(Calendar.MINUTE));
					break;
				default:
					tmp = completionSingle((lastCalendar.get(Calendar.MONTH) + 1)) + "-" + completionSingle(lastCalendar.get(Calendar.DAY_OF_MONTH)) + " " + completionSingle(lastCalendar.get(Calendar.HOUR_OF_DAY)) + ":" + completionSingle(lastCalendar.get(Calendar.MINUTE));
					break;
			}
		  }else{
			  tmp = lastCalendar.get(Calendar.YEAR) + "-" + completionSingle((lastCalendar.get(Calendar.MONTH) + 1)) + "-" + completionSingle(lastCalendar.get(Calendar.DAY_OF_MONTH));
		  }
		
		return tmp ;
    }
	
	/**
	 * 补全单个数字
	 * @param data
	 * @return
	 */
	public static String completionSingle(int data){
		return data < 9 ? ("0" + data) : String.valueOf(data);
	}
	
	/**
	 * 计算两个时间相差多少天(基数为秒)
	 * @param currTime 
	 * @param compareTime
	 * @return
	 */
	public static int getDaysDiff(long currTime, long compareTime){
		long diffTime = compareTime - currTime;
		Long days = diffTime / (3600 * 24L);
		return days.intValue();
	}
	
	/**
	 * 计算两个日期之间相差多少天
	 * @return
	 */
	public static int getDaysDiffByDate(Date currDate, Date compareDate){
		 return getDaysDiff(currDate.getTime()/1000L, compareDate.getTime()/1000L);
	}
	

	/**
	 * 计算游戏所有的差值时间
	 * 
	 * @param currTime
	 * @param createTime
	 * @return
	 */
	public static String getGameLvingTime(String currTime, String deadTime) {
		try {
			SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long coolDownMillions = 2 * 60 * 60 * 1000;
			long currTimeMillions = timeformat.parse(currTime).getTime();
			long deadTimeMillions = timeformat.parse(deadTime).getTime();
			// 这里精确到秒
			long leftSecond = (coolDownMillions-(currTimeMillions - deadTimeMillions)) / 1000;
			if (leftSecond <= 0) {
				return null;
			} else {
				long leftHours = leftSecond / (60 * 60);
				long leftMinutes = (leftSecond - leftHours * 60 * 60) / 60;
				leftSecond = leftSecond - leftHours * 60 * 60 - leftMinutes * 60;
				return new StringBuffer("还需<span class='text_orange font14 bold'>").append(leftHours).append(
						"</span>小时<span class='text_orange font14 bold'>").append(leftMinutes).append(
						"</span>分钟<span class='text_orange font14 bold'>").append(leftSecond).append("</span>秒复活")
						.toString();
			}
		} catch (Exception e) {
			// 这里处理有问题
			return null;
		}
	}
	
}
