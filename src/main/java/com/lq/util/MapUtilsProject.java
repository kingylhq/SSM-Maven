package com.lq.util;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.MapUtils;

public class MapUtilsProject {

	/**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
	@SuppressWarnings("unused")
	private static Map<String, String> sortMapByKey(Map<String, String> params) {
		
		if (MapUtils.isEmpty(params)) {
	            return null;
	    }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(params);
        return sortMap;
	}
}
