//package com.lq.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.JSONAwareSerializer;
//import com.alibaba.fastjson.serializer.JSONSerializer;
//
//
///**
// * 处理集合类转换
// * @author lq
// * 2017-12-1 11:45:42
// *
// */
//public class CollectionUtils {
//
//	//java入口
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//	
//	public <T> List<T> getJavaCollection(T clazz, String json){
//		
//		List<T> objs = null;
//		com.alibaba.fastjson.JSONObject jo = new JSONObject();
//		jo.put("data", json);
//		JSONArray ja = (JSONArray) JSONAwareSerializer.toJSON(jo);
//		
//		if(ja != null){
//			objs = new ArrayList<T>();
//			List list = (List) JSONSerializer.toJava(ja);
//			if(org.apache.commons.collections.CollectionUtils.isNotEmpty(list)){
//				for (Object object : list) {
//					JSONObject jsonObj = JSONObject.fromObject(object);
//					T obj = (T) jsonObj.toBean(jsonObj, clazz.getClass());
//					objs.add(obj);
//				}
//			}
//		}
//		return objs;
//		
//	}
//
//}
