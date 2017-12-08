//package com.lq.util.company;
//
//import java.text.ParseException;
//import java.util.Date;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.DateUtils;
//
//public class DateParseUtil {
//
//	private static String[] dateFormat = new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMdd", "yyyy/MM/dd" };
//
//	public static Date parse(String strDate) {
//		if (StringUtils.isEmpty(strDate)) {
//			return null;
//		}
//		try {
//			return DateUtils.parseDate(strDate, dateFormat);
//		} catch (ParseException e) {
//			return null;
//		}
//
//	}
//
//}
