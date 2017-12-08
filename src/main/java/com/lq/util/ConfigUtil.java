package com.lq.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

//import com.active.logger.LogWriter;

public class ConfigUtil {

	private static final ResourceBundle config = ResourceBundle
			.getBundle("config");

	public static void main(String[] args) {
		// System.out.println(path);
		ResourceBundle rs = ResourceBundle.getBundle("mapping");
		String s = rs.getString("/test");
		System.out.println(s);
	}

	public static String get(String key) {
		String rs = config.getString(key);
		if (rs == null) {
			// LogWriter.error("key:'" + key + "'解析配置出错'");
			// throw new ValidationException();
		}
		return rs;
	}

	public static String getConfig(String key, String path) {
		String retureValue = "";
		Properties prop = new Properties();
		InputStream in = ConfigUtil.class.getResourceAsStream(path);
		try {
			prop.load(in);
			retureValue = prop.getProperty(key);
		} catch (IOException e) {
			// LogWriter.error("解析配置出错" + e.getMessage());
		}
		return retureValue;
	}

}
