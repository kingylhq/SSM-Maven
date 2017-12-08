package com.lq.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * GreetingClient 是一个客户端程序，
 * 该程序通过socket连接到服务器并发送一个请求，然后等待一个响应。
 * @author lq
 * 2017年9月17日 10:56:07
 */
public class GreetingClient {
	
	public static void main(String [] args){
	      String serverName = args[0];
	      int port = Integer.parseInt(args[1]);
	      try{
	         System.out.println("Connecting to " + serverName+ " on port " + port);
	         Socket client = new Socket(serverName, port);
	         System.out.println("Just connected to "+ client.getRemoteSocketAddress());
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         out.writeUTF("Hello from "+ client.getLocalSocketAddress());
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         System.out.println("Server says " + in.readUTF());
	         client.close();
	      }catch(IOException e){
	         e.printStackTrace();
	      }
	   }

}
