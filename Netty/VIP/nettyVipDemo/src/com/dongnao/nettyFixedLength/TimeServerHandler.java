package com.dongnao.nettyFixedLength;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����8:57:51
 * @readme ���ڶ�����ʱ����ж�д������ͨ������ֻ��Ҫ��עchannelRead��exceptionCaught������
 * 
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

	private int counter;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("The time server(Thread:"+Thread.currentThread()+") receive order : "+body+". the counter is : "+ ++counter);
		String currentTime = body;
		
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		
		//�������͵���Ϣ�ŵ����ͻ���������
		ctx.writeAndFlush(resp);
	}
}
