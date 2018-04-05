package com.dongnao.demo0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����3:23:30
 * 
 */
public class BioServerHandler implements Runnable {

	private Socket socket;
	public BioServerHandler(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			String body = null;
			while(true){
				body = in.readLine(); //�����ȴ����ݿ��Ա���ȡ
				if(body == null){
					break;
				}
				System.out.println("���������յ�ָ��:"+body);
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
