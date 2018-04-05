package com.dongnao.nettyLine;

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

	private int counter;
	private byte[] req;
	
	@Override
	//�����������ָ��
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message=null;
		//ģ��һ�ٴ����󣬷����ظ�����
		for (int i = 0; i < 200; i++) {
			req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
			message=Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
		
	}

	@Override
	//���շ���������Ӧ
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ByteBuf buf = (ByteBuf) msg;
//		//buf.readableBytes():��ȡ�������пɶ����ֽ�����
//		//���ݿɶ��ֽ�����������
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req, "UTF-8");
		String body = (String) msg;
		System.out.println("Now is : "+body+". the counter is : "+ ++counter);
	}

	@Override
	//�쳣����
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//�ͷ���Դ
		ctx.close();
	}
	
}
