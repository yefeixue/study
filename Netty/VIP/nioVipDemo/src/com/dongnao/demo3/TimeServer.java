package com.dongnao.demo3;
/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����4:54:54
 * 
 */
public class TimeServer {

	public static void main(String[] args) {
		int port=8080; //�����Ĭ�϶˿�
		MultiplexerTimeServer timeServer=new MultiplexerTimeServer(port);
		new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
	}
}
