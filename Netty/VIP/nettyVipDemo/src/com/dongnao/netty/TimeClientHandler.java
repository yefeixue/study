package com.dongnao.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����9:16:37
 * 
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

	@Override
	//�����������ָ��
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 5; i++) {
			byte[] req = "QUERY TIME ORDER".getBytes();
			ByteBuf firstMessage = Unpooled.buffer(req.length);
			firstMessage.writeBytes(req);
			ctx.writeAndFlush(firstMessage);
		}
	}

	@Override
	//���շ���������Ӧ
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		//buf.readableBytes():��ȡ�������пɶ����ֽ�����
		//���ݿɶ��ֽ�����������
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("Now is : "+body);
	}

	@Override
	//�쳣����
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//�ͷ���Դ
		ctx.close();
	}
	
}
