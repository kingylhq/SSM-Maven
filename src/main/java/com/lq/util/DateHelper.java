package com.lq.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 *
 */
public class DateHelper {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static String GetNowDate() {
		return sdf.format(new Date());
	}

	public static String GetNowDate(int filed, int count) {
		Calendar c = Calendar.getInstance();
		c.add(filed, count);
		return sdf.format(c.getTime());
	}

	public static Date GetAfterDate(Date date, int filed, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(filed, count);
		return c.getTime();
	}

	public static String DateToString(Date date, String formater) {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(formater);
			return sdf1.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
		}
		return date;
	}

	public static Date CalcDate(Date date, int filed, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(filed, count);
		return c.getTime();
	}

	public static Boolean IsDate(String date) {
		Date date1 = DateHelper.StringToDate(date, "yyyy-MM-dd");
		return date1 == null ? false : true;
	}

	public static long getNextDayTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date d = cal.getTime();
		return d.getTime();
	}

	public static String getNextDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date d = cal.getTime();
		return sd.format(d.getTime());
	}

	/**
	 * 获取每天�?��时间
	 * 
	 * @return
	 */
	public static Date getDawnDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取每天凌晨时间
	 * 
	 * @return
	 */
	public static Date getBeforeDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

}
