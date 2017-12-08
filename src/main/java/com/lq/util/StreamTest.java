package com.lq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 测试IO流
 * lq
 * 2017年9月17日 10:49:12
 */
public class StreamTest {
	public static void ReadFileStream(){
		File file = new File("E://", "datagrid_data1.json");
		try {
			file.createNewFile(); // 创建文件
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 向文件写入内容(输出流)
		String str = "亲爱的小南瓜！";
		byte bt[] = new byte[1024];
		bt = str.getBytes();
		try {
			FileOutputStream in = new FileOutputStream(file);
			try {
				in.write(bt, 0, bt.length);
				in.close();
				// boolean success = true;
				// System.out.println("写入文件成功");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// 读取文件内容 (输入流)
			FileInputStream out = new FileInputStream(file);
			@SuppressWarnings("resource")
			InputStreamReader isr = new InputStreamReader(out);
			int ch = 0;
			while ((ch = isr.read()) != -1) {
				System.out.print((char) ch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		ReadFileStream();
	}

}
