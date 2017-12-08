package com.lq.util.md5;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author Administrator
 * 2017-12-6 10:30:30
 */
public class MD5Tools  {  
	
    public final static String getMD5(String pwd) {  
        //用于加密的字符  
        char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
                'A', 'B', 'C', 'D', 'E', 'F' };  
        try {  
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中  
            byte[] btInput = pwd.getBytes();  
               
            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
               
            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要  
            mdInst.update(btInput);  
               
            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文  
            byte[] md = mdInst.digest();  
               
            // 把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {   //  i = 0  
                byte byte0 = md[i];  //95  
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5   
                str[k++] = md5String[byte0 & 0xf];   //   F  
            }  
               
            //返回经过加密后的字符串  
            return new String(str);  
               
        } catch (Exception e) {  
            return null;  
        }  
    }  
    
    public static void main(String[] args) {
		
//    	MD5Tools md5 = new MD5Tools();
//    	String result = md5.getMD5("123456");
//    	//E10ADC3949BA59ABBE56E057F20F883E
//    	System.out.println("result加密后："+result);
    	
    	int size = 15;
    	int total = 0;
    	int totalPage = 0;
    	//总条数 >= 每页显示的条数 1
    	//总条数 < 每页显示的条数 0
    	if(total == 0){
    		totalPage = 0;
    	}
    	else if(total > 0){
    		//总条数小于每页显示的条数
    		if(total <= size){
    			totalPage = 1;
    		}else{//总条数大于于每页显示的条数
	    		if(total%size == 0){ //如果取模为0（能够整除）
	    			totalPage = total/size;
	    		}else{
	    			totalPage = total/size + 1;
	    		}
    		}
    	}
    	System.out.println("总页数为："+totalPage);
	}
}  

