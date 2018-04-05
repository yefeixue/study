package com.dongnao.demo0;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author Five老师
 * @createTime 2017年11月13日 下午3:31:45
 * 
 */
public class BioServerClient {

	public static void main(String[] args) {
		int port=8080; //服务端默认端口
		if(args != null && args.length>0){
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
			}
		}
		Socket socket = null;
		PrintWriter out = null;
		try {
			socket = new Socket("127.0.0.1", port);
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("9527");
			System.out.println("客户端向服务端发送了指令");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out !=null){
				out.close();
				out = null;
			}
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				socket = null;
			}
		}
	}
}
