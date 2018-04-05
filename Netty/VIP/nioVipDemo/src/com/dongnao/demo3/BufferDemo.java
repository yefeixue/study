package com.dongnao.demo3;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.RandomAccess;

/**
 * 
 * @author Five��ʦ
 * @createTime 2018��3��20�� ����10:29:27
 * 
 */
public class BufferDemo {
	public static void main(String[] args) {
		try {
			RandomAccessFile file = new RandomAccessFile("E:/dongnao/VIP_v2.0/netty/nio.txt","r");
			FileChannel channel = file.getChannel();
			
			//����һ��Buffer�����ҳ�ʼ����С
			ByteBuffer buf = ByteBuffer.allocate(10);
			System.out.println("position:"+buf.position()+"; limit:"+buf.limit()+"; capacity:"+buf.capacity());
			//��Channel�е����ݶ���buffer��
			channel.read(buf);
			System.out.println("position:"+buf.position()+"; limit:"+buf.limit()+"; capacity:"+buf.capacity());
			
			//��bufferдģʽ�л�Ϊ��ģʽ
			buf.flip();
			System.out.println("position:"+buf.position()+"; limit:"+buf.limit()+"; capacity:"+buf.capacity());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
