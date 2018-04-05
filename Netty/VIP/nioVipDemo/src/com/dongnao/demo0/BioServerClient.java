package com.dongnao.demo0;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����3:31:45
 * 
 */
public class BioServerClient {

	public static void main(String[] args) {
		int port=8080; //�����Ĭ�϶˿�
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
			System.out.println("�ͻ��������˷�����ָ��");
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
