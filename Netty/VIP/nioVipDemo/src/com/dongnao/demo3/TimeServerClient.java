package com.dongnao.demo3;
/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����5:21:33
 * 
 */
public class TimeServerClient {
	
	public static void main(String[] args) {
		int port=8080; //�����Ĭ�϶˿�
		new Thread(new TimeClientHandler("127.0.0.1", port), "NIO-TimeServerClient-001").start();
	}
}
