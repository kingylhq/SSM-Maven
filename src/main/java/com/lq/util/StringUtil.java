package com.lq.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.Sequence;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static final String CH_PATTERN = "[\\u4e00-\\u9fa5]+";

	/**
	 * 判断字符串是否为null或空白字符或空字符串
	 */
	public static boolean isEmpty(Object str) {
		return str == null || str.toString().matches("\\s*");
	}

	/**
	 * 判断两个对象是否相等
	 */
	public static boolean isEqual(Object o1, Object o2) {
		return HashUtil.isEqual(o1, o2);
	}

	/**
	 * 空格.Null返回null
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 判断字符为空
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符不为空白?
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isNotBlank(CharSequence cs) {
		return !StringUtil.isBlank(cs);
	}

	/**
	 * 产生随机字符?
	 * @param length
	 *  随机字符串的长度
	 * @return
	 */
	public static String randomKey(int length) {
		final String buffer = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		Random r = new Random();
		final int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}

	/**
	 * 如果str为空，则返回空字符串，否则trim之后返回
	 */
	public static String killNull(Object str) {
		return killNull(str, "");
	}

	/**
	 * 如果str为空，则返回字符串defaultStr，否则trim之后返回
	 * 
	 * @param str
	 * @param defaultStr
	 */
	public static String killNull(Object str, String defaultStr) {
		if (isEmpty(str)) {
			return defaultStr;
		}
		return str.toString().trim();
	}

	/**
	 * 文件长度友好显示
	 */
	public static String fomatFileSize(long size) {
		if (size < 1024) {
			return size + "B";
		} else if (size < 1024 * 1024) {
			return size / 1024 + "K";
		} else {
			return size / 1024 / 1024 + "M";
		}
	}

	/**
	 * 判断字符串str可否转化成实型数据�?
	 */
	public static boolean isNumber(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		return str.length() > 0 && str.matches("\\d*\\.{0,1}\\d*")
				&& !".".equals(str);
	}

	/**
	 * 判断字符串是否是纯数字组�?
	 * 
	 * @param str
	 */
	public static boolean isInteger(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		return str.matches("\\d+");
	}

	/**
	 * 删除str�?���?��字符如果是ch则删除这个字符，通常在根据list拼装字符串的时�?使用
	 * 
	 * @param str
	 * @param ch
	 */
	public static StringBuilder delLastChar(StringBuilder str, char ch) {
		if (str.length() < 1) {
			return str;
		}
		if (str.charAt(str.length() - 1) == ch) {
			str.deleteCharAt(str.length() - 1);
		}
		return str;
	}

	public static Class getVoClass(Object vo) {
		final Class clazz = vo.getClass();
		return clazz.getName().indexOf("$") > 0 ? clazz.getSuperclass() : clazz;
	}

	/**
	 * 字符串的补位操作 如果�?��长度�?位不足的添加某个字符
	 * 
	 * @param length
	 * @param addChar
	 * 填充的字符串
	 * @param origin
	 * 源字符串
	 */
	public static String addChar2Full(int length, String addChar, String origin) {
		if (origin.length() < length) {
			String temp = "";
			for (int i = 0; i < length - origin.length(); i++) {
				temp = addChar + temp;
			}
			origin = temp + origin;
		}
		return origin;
	}

	/**
	 * 获取自定sequence name
	 * 
	 * @param sequence
	 * @param clazz
	 * @return
	 * @since Fan Houjun 2009-8-14
	 */
	public static String getSeqName(Sequence sequence, Class clazz) {
		// return isEmpty(sequence.name()) ? (clazz.getSimpleName() + "_Seq")
		// : sequence.name();
		return null;
	}

	/**
	 * 第一个字母小写?
	 */
	public static String unCapFirst(String str) {
		final char c = str.charAt(0);
		if (c > 'Z' || c < 'A') {
			return str;
		}
		String strSub = str.substring(1);
		return (char) (c + 32) + strSub;
	}

	/**
	 * 第一个单词大写?
	 */
	public static String capFirst(String str) {
		final char c = str.charAt(0);
		if (c > 'z' || c < 'a') {
			return str;
		}
		String strSub = str.substring(1);
		return (char) (c - 32) + strSub;
	}

	/**
	 * 判断是否为正确的IP地址
	 */
	public static boolean isIpAddress(String ip) {
		if (isEmpty(ip)) {
			return false;
		}
		return ip
				.matches("((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]\\d|[1-9])");
	}

	private static List<Pattern> ptns = null;

	private static List<Pattern> getPtns() {
		if (ptns == null) {
			ptns = new ArrayList<Pattern>();
			ptns.add(Pattern.compile(".+\\.\\w+\\.\\w+\\..+"));
		}
		return ptns;
	}

	/**
	 * @return 将java对象转化为json字符�?
	 */
	public static String toJson(Object data) {
		return toJson(data, getPtns());
	}

	private static String toJson(Object data, List<Pattern> excludePtns) {
		// try {
		// if (data instanceof Date) {
		// return DateUtil.format((Date) data,
		// DateUtil.TRIM_SECOND_PATTERN);
		// }
		// if (excludePtns == null || excludePtns.isEmpty()) {
		// return JSONUtil.serialize(data);
		// }
		// return JSONUtil.serialize(data, excludePtns, true);
		// } catch (JSONException e) {
		// throw new IllegalArgumentException("将java对象转化为json字符串出错：" + data, e);
		// }
		return null;
	}

	public static String toJson(Object data, String excludeRegex) {
		List<Pattern> list = new ArrayList<Pattern>(1);
		if (excludeRegex != null) {
			list.add(Pattern.compile(excludeRegex));
		}
		return toJson(data, list);
	}

	/**
	 * @功能描述 ：判断字符串中是否包含中文字�?
	 * @输入参数 ：需要判断的字符串
	 * @�?�?�?：判断结�?
	 * @version ?1.0
	 * @author ?曾俊
	 * @更新时间 �?2011-7-25 上午10:37:10
	 */
	public static boolean containsChinese(String str) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile(CH_PATTERN);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
	/**
	 * 比较两个对象
	 * @param O1
	 * @param O2
	 * @return
	 */
	public static boolean compareString(String O1, String O2){
		
		boolean flag = false;
		if(StringUtils.isNotBlank(O1) && StringUtils.isNotBlank(O2) ){
			if(O1.equals(O2)){
				flag = true;
			}else{
				flag = false;
			}
		}
		return flag;
	}
	
	//java入口
	public static void main(String[] args) {
		
		//1、第一个字母小写
		String unCapFirst = unCapFirst("LIQIAN");
		System.out.println("第一个字母小写："+unCapFirst);
		
		//2、第一个字母大写
		String capFirst = capFirst("yanghong");
		System.out.println("第一个字母大写："+capFirst);
		
		//3、判断是否为正确的IP地址
	    boolean isIpAddressFlag = isIpAddress("192.168.1.111");
	    if(isIpAddressFlag){
	    	System.out.println("IP地址有效");
	    }else{
	    	System.out.println("IP地址无效");
	    }
	    
	    //4、文件长度友好显示
	    String strLength = fomatFileSize(480000000);
	    System.out.println("文件长度："+strLength);
	    
	    //5、字符串长度截取
	    String str1 = "abcdef";
	    Random rm = new Random();
	    int rmInt = rm.nextInt(5);	
	    System.out.println("产生的随机整数："+rmInt);
	    //substring传递一个参数就代表从索引为几开始截取（注：索引从0开始）
	    System.out.println("截取结果："+str1.substring(rmInt));
	    
	    //6、System
	    InputStream is = System.in;
	    System.out.println("System.in："+is);
	    
	}
	
}
