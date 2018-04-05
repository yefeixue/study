package com.dongnao.demo0;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Five老师
 * @createTime 2017年11月13日 下午3:17:06
 * 
 */
public class BioServer {
	public static void main(String[] args) {
		int port=8080; //服务端默认端口
		if(args != null && args.length>0){
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
			}
		}
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("启动了服务端，端口:"+port);
			Socket socket = null;
			while(true){
				socket = server.accept();
				new Thread(new BioServerHandler(socket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(server!=null){
				System.out.println("关闭了服务.");
				try {
					server.close();
					server = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
