package com.lq.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.ValidationException;

/**
 * http请求操作类
 * 
 * @author tanbin
 * 
 */
public class HttpUtil {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "b");
		String s = null;
		try {
			s = HttpUtil.post("http://www.baidu.com", map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s);
	}

	/**
	 * @author tanbin http请求
	 * @param url
	 *            请求url
	 * @return
	 * @throws Exception
	 * @throws NetworkExcepttion
	 * @throws ValidationException
	 */
	public static String post(String url) throws Exception {
		return post(url, null, null);
	}

	/**
	 * @author tanbin http请求
	 * @param url
	 *            请求url
	 * @param params
	 *            将参数封装成map
	 * @return
	 * @throws Exception
	 * @throws NetworkExcepttion
	 * @throws ValidationException
	 */
	public static String post(String url, Map<String, String> params)
			throws Exception {
		return post(url, params, null);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            地址
	 * @param params
	 *            参数
	 * @param timeout
	 *            超时
	 * @return
	 * @throws Excepttion
	 */
	public static String post(String url, Map<String, String> params,
			Integer timeout) throws Exception {
		if (StringUtil.isEmpty(url)) {
			throw new Exception("url不能为空");
		}
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			Set<Map.Entry<String, String>> key = params.entrySet();
			for (Iterator<Map.Entry<String, String>> it = key.iterator(); it
					.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				sb.append(entry.getKey());
				sb.append("=");
				try {
					sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (it.hasNext()) {
					sb.append("&");
				}
			}
		}
		OutputStreamWriter osw = null;
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			if (timeout != null) {
				con.setConnectTimeout(1000 * timeout);
				con.setReadTimeout(1000 * timeout);
			} else {
				con.setConnectTimeout(1000 * 60);
				con.setReadTimeout(1000 * 60);
			}
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("url网络异常:" + url + "\r\n" + "参数：" + sb);
		} finally {
			if (osw != null) {
				try {
					osw.flush();
					osw.close();
				} catch (IOException e) {
				}
			}
			if (con != null) {
				con.disconnect();
			}
		}
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			// if(HttpURLConnection.HTTP_OK==con.getResponseCode()){
			is = new InputStreamReader(con.getInputStream(), "UTF-8");
			br = new BufferedReader(is);
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("url网络异常:" + url + "\r\n" + "参数：" + sb);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
			}
		}
		String result = null;
		try {
			result = URLDecoder.decode(buffer.toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String toHTMLString(String in) {
		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'')
				out.append("'");
			else if (c == '<')
				out.append("<");
			else if (c == '>')
				out.append(">");
			else if (c == '&')
				out.append("&");
			else if (c == '\n')
				out.append("");
			else
				out.append(c);
		}
		return out.toString();
	}
}
