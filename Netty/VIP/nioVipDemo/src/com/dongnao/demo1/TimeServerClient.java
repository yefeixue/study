package com.dongnao.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����3:31:45
 * 
 */
public class TimeServerClient {

	public static void main(String[] args) {
		int port=8080; //�����Ĭ�϶˿�
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			socket = new Socket("127.0.0.1", port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			//��������˵�ָ��
			out.println("QUERY TIME ORDER");
			System.out.println("Send order to server succeed.");
			String resp = in.readLine();
			System.out.println("Now is : "+resp);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out !=null){
				out.close();
				out = null;
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
