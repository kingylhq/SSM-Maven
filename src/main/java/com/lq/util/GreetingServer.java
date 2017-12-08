package com.lq.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * GreetingServer 程序是一个服务器端应用程序，使用Socket来监听一个指定的端口。
 * @author lq
 * ����ʱ�䣺2016��2��19��10:46:40
 */
public class GreetingServer extends Thread {
	
	private ServerSocket serverSocket;
	   
	   public GreetingServer(int port) throws IOException{
	      serverSocket = new ServerSocket(port);
	      serverSocket.setSoTimeout(10000);
	   }

	   public void run()
	   {
	      while(true){
	         try{
	            System.out.println("Waiting for client on port " +
	            serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            System.out.println("Just connected to "+ server.getRemoteSocketAddress());
	            DataInputStream in = new DataInputStream(server.getInputStream());
	            System.out.println(in.readUTF());
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
	            out.writeUTF("Thank you for connecting to "+ server.getLocalSocketAddress() 
	            		+ "\nGoodbye!");
	            server.close();
	         }catch(SocketTimeoutException s){
	            System.out.println("Socket timed out!");
	            break;
	         }catch(IOException e){
	            e.printStackTrace();
	            break;
	         }
	      }
	   }
	   //java 入口
	   public static void main(String [] args){
	      int port = Integer.parseInt(args[0]);
	      try{
	         Thread t = new GreetingServer(port);
	         t.start();
	      }catch(IOException e){
	         e.printStackTrace();
	      }
	   }
}
