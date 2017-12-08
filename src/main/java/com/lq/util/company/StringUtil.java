/**
 * Project Name:fam-core
 * File Name:StringUtils.java
 * Package Name:com.linc.fam.util
 * Date:2015年12月24日下午9:03:33
 * Copyright @ 2010-2015 上海企垠信息科技有限公司  All Rights Reserved.
 *
 */

package com.lq.util.company;

/**
 * ClassName:StringUtils <br/>
 * Description: TODO Description. <br/>
 * Date: 2015年12月24日 下午9:03:33 <br/>
 *
 * @author Shipeng Tang
 * @version
 * @since JDK 1.7
 * @see
 */

public class StringUtil { // 字符串判断是否为空和null
	public static boolean checkIsEmptyOrNull(String str) {
		if (!str.trim().isEmpty() && str != null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 *
	 * StringIsEqual:(判断两个字符串是否相等). <br/>
	 *
	 * @author:Shipeng Tang Date: 2015年12月25日 下午3:42:09
	 * @param str
	 * @param str2
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean StringIsEqual(String str, String str2) {
		if (str == null || str2 == null) { // 判断是否为null
			if (str == null && str2 == null) {
				return true;
			} else {
				return false;
			}
		}
		if (str.isEmpty() || str2.isEmpty()) { // 判断是否为空
			if (str.isEmpty() && str.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
		if (str.equals(str2)) { // 直接对比
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * StringIsEqualIgnoreSpace:(判断两个字符串是否相等). <br/>
	 * 适用于忽略空格的比较
	 *
	 * @author:Shipeng Tang Date: 2015年12月25日 下午3:42:09
	 * @param str
	 * @param str2
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean StringIsEqualIgnoreSpace(String str, String str2) {
		if (str == null || str2 == null) { // 判断是否为null
			if (str == null && str2 == null) {
				return true;
			} else {
				return false;
			}
		}
		if (str.trim().isEmpty() || str2.trim().isEmpty()) { // 判断是否为空，忽略空格数量
			if (str.trim().isEmpty() && str.trim().isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
		if (str.trim().equals(str2.trim())) { // 直接对比
			return true;
		} else {
			return false;
		}
	}
}
