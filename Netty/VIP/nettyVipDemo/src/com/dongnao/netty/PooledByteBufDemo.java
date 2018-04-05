package com.dongnao.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * 
 * @author Five老师
 * @createTime 2018年3月9日 下午9:40:31
 * 
 */
public class PooledByteBufDemo {

	public static void main(String[] args) {
		byte[] content = new byte[1024];
		int loop = 3000000;
		long startTime = System.currentTimeMillis();
		
		ByteBuf poolBuffer = null;
		for (int i = 0; i < loop; i++) {
			//通过内存池的方式构建直接缓冲区
			poolBuffer = PooledByteBufAllocator.DEFAULT.directBuffer(1024);
			poolBuffer.writeBytes(content);
			//释放buffer
			poolBuffer.release();
		}
		long startTime2 = System.currentTimeMillis();
		ByteBuf buffer = null;
		for (int i = 0; i < loop; i++) {
			//通过非内存池的方式构建直接缓冲区
			buffer = Unpooled.directBuffer(1024);
			buffer.writeBytes(content);
			buffer.release();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("The PooledByteBuf use time :"+(startTime2-startTime));
		System.out.println("The UnpooledByteBuf use time :"+(endTime-startTime2));
	}
}
