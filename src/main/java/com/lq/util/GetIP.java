package com.lq.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 得到本机的IP地址ַ
 * @author lq
 * 2017年9月17日 10:58:09
 */
public class GetIP {
	
	public static void main(String[] args) throws Exception {  
		GetIP t = new GetIP();  
		t.getLocalIP();  
		   
	    for(int i=1; i<10; i++){
		   for(int j=1; j<=i; j++){
			   System.out.print(j + "x" + i + "=" + (j*i));
			   System.out.print(" ");
		   }
		   System.out.println("");
		}
			
	}  
		  
		public void getLocalIP() throws Exception {  
		   Enumeration e1 = (Enumeration) NetworkInterface.getNetworkInterfaces();  
		   while (e1.hasMoreElements()) { 
			    NetworkInterface ni = (NetworkInterface) e1.nextElement();  
			    System.out.print(ni.getName()+":");  
			    Enumeration e2 = ni.getInetAddresses();  
			    while (e2.hasMoreElements()) {  
				     InetAddress ia = (InetAddress) e2.nextElement();  
				     if (ia instanceof Inet6Address)
				    	 continue; // omit IPv6 address  
				     	 System.out.print(ia.getHostAddress());  
			    if (e2.hasMoreElements()) {  
			         System.out.print(", ");  
			     }  
			    }  
		    System.out.print("\n");  
		   }  
		}
		
		
}
