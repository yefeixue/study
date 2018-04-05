package com.dongnao.nettyDelimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 
 * @author Five��ʦ
 * @createTime 2017��11��13�� ����8:42:39
 * 
 */
public class TimeServer {
	public static void main(String[] args) throws Exception {
		int port=8080; //�����Ĭ�϶˿�
		new TimeServer().bind(port);
	}

	public void bind(int port) throws Exception{
		//Reactor�߳���
		//1���ڷ���˽��ܿͻ��˵�����
		EventLoopGroup acceptorGroup = new NioEventLoopGroup();
		//2���ڽ���SocketChannel�������д
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			//Netty��������NIO�������ĸ���������
			ServerBootstrap sb = new ServerBootstrap();
			//������NIO�߳��鴫�븨����������
			sb.group(acceptorGroup, workerGroup)
				//���ô�����ChannelΪNioServerSocketChannel����
				.channel(NioServerSocketChannel.class)
				//����NioServerSocketChannel��TCP����
				.option(ChannelOption.SO_BACKLOG, 1024)
				//���ð�IO�¼��Ĵ�����
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel arg0) throws Exception {
						//����ճ��/�������
						ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
						arg0.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
						arg0.pipeline().addLast(new StringDecoder());
						arg0.pipeline().addLast(new TimeServerHandler());
					}
				});
			//�󶨶˿ڣ�ͬ���ȴ��ɹ���sync()��ͬ������������
			//ChannelFuture��Ҫ�����첽������֪ͨ�ص�
			ChannelFuture cf = sb.bind(port).sync();
				
			//�ȴ�����˼����˿ڹر�
			cf.channel().closeFuture().sync();
		} finally {
			//�����˳����ͷ��̳߳���Դ
			acceptorGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}

