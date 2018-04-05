package com.dongnao.demo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * 
 * @author Five老师
 * @createTime 2017年11月13日 下午3:23:30
 * 
 */
public class TimeServerHandler implements Runnable {

	private Socket socket;
	public TimeServerHandler(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String currentTime = null;
			String body = null;
			while(true){
				body = in.readLine();
				if(body == null){
					break;
				}
				System.out.println("The time server(Thread:"+Thread.currentThread()+") receive order:"+body);
				currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
				out.println(currentTime);
			}
		} catch (Exception e) {
			if(in != null){
				try {
					in.close();
					in = null;//
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
					out = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				this.socket = null;
			}
		}
	}

}
